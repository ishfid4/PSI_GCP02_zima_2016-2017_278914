package pl.ishfid.psi;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ishfi on 02.12.2016.
 */

public class Network {
    public DataManager dataManager;

    private double learningRate;
    private Layer inputLayer;
    private Layer outputLayer;
    private ArrayList<Layer> hiddenLayers;

    public Network(int inputs, int hiddenLayers, int neuronInEachHiddenLayer, int outputs, double learningRate, NeuronFactory factory) throws IOException {
        this.hiddenLayers = new ArrayList<>();
        this.learningRate = learningRate;
        this.inputLayer = new Layer(factory);
        this.outputLayer = new Layer(factory);
        this.dataManager = new DataManager();
        this.dataManager.importData("DataSet.prn");

        this.inputLayer.fillInputLayer(inputs);

        for (int i = 0; i < hiddenLayers; ++i)
        {
            if (i == 0) // first hidden layer
            {
                this.hiddenLayers.add(new Layer(factory));
                this.hiddenLayers.get(this.hiddenLayers.size() - 1).fillLayer
                        (neuronInEachHiddenLayer, this.inputLayer.getSize());
            }
            else
            {
                Layer previouslyLastLayer = this.hiddenLayers.get(this.hiddenLayers.size() - 1);
                this.hiddenLayers.add(new Layer(factory)); // inserting new layer
                this.hiddenLayers.get(this.hiddenLayers.size() - 1).fillLayer
                        (neuronInEachHiddenLayer, previouslyLastLayer.getSize());
            }
        }

        this.outputLayer.fillLayer(outputs, this.hiddenLayers.get(this.hiddenLayers.size() - 1).getSize());
    }

    public Layer getInputLayer() {
        return inputLayer;
    }

    public Layer getOutputLayer() {
        return outputLayer;
    }

    public ArrayList<Layer> getHiddenLayers() {
        return hiddenLayers;
    }

    public void setInputValues(int record, boolean isLearning){
        ArrayList<ArrayList<Double>> data = dataManager.inputDataSet;

        if (!isLearning) data = dataManager.validationInputDataSet;

        double inputsAmount = dataManager.inputCount;
        for (int i = 0; i < inputsAmount; ++i)
        {
            this.inputLayer.getNeurons().get(i).getInputs().get(0).setInputVal(data.get(record).get(i));
            this.inputLayer.getNeurons().get(i).setOutputVal(data.get(record).get(i));
        }
    }

    public void setTargetValues(int record, boolean isLearning){
        ArrayList<ArrayList<Double>> data = dataManager.outputDataSet;

        if (!isLearning) data = dataManager.validationOutputDataSet;

        double outputCount = dataManager.outputCount;
        for (int i = 0; i < outputCount; ++i)
        {
            this.outputLayer.getNeurons().get(i).setTargetVal(data.get(record).get(i));
        }
    }

    public void feedForward(){
        int layersAmount = this.hiddenLayers.size();

        for (int layer = 0; layer < layersAmount; ++layer){
            int neuronsAmount = this.hiddenLayers.get(layer).getSize();
            for (int neuron = 0; neuron < neuronsAmount; ++neuron) {
                ArrayList<NeuronInput> neuronInputs = this.hiddenLayers.get(layer).getNeurons().get(neuron).getInputs();

                int inputsAmount = neuronInputs.size();
                if (layer == 0){
                    for (int input = 0; input < inputsAmount; ++input) {
                        neuronInputs.get(input).setInputVal
                                (this.inputLayer.getNeurons().get(input).getInputs().get(0).getInputVal());
                    }
                }else {
                    for (int input = 0; input < inputsAmount; ++input) {
                        neuronInputs.get(input).setInputVal
                                (hiddenLayers.get(layer - 1).getNeurons().get(input).getOutputVal());
                    }
                }

                this.hiddenLayers.get(layer).getNeurons().get(neuron).calcOutput();
            }
        }

        ArrayList<Neuron> lastHiddenLayerNeurons = this.hiddenLayers.get
                (this.hiddenLayers.size() - 1).getNeurons();
        for (Neuron neuron : this.outputLayer.getNeurons()){
            for (int input = 0; input < neuron.getInputs().size(); ++input){
                neuron.getInputs().get(input).setInputVal(lastHiddenLayerNeurons.get(input).getOutputVal());
            }
            neuron.calcOutput();
        }
    }

    // Updating signal error Backpropagation
    public void updateSignalErrors(){
        for (Neuron output : this.outputLayer.getNeurons()){
            output.calcSignalError();
        }
        int lastHiddenLayer = this.hiddenLayers.size() - 1;

        for (int layer = lastHiddenLayer; layer >= 0; --layer){
            ArrayList<Neuron> actualHiddenLayerNeurons = this.hiddenLayers.get(layer).getNeurons();
            for (int neuron = 0; neuron < actualHiddenLayerNeurons.size(); ++neuron){
                double signalError = 0;

                if (layer == lastHiddenLayer){
                    ArrayList<Neuron> outputLayerNeurons = this.outputLayer.getNeurons();
                    for (int i = 0; i < outputLayerNeurons.size(); ++i){
                        signalError += outputLayerNeurons.get(i).getInputs().get(neuron).getWeight()
                                * outputLayerNeurons.get(i).getSignalError();
                    }
                }else{
                    ArrayList<Neuron> nextHiddenLayerNeurons = this.hiddenLayers.get(layer + 1).getNeurons();
                    for (int i = 0; i < nextHiddenLayerNeurons.size(); ++i){
                        signalError += nextHiddenLayerNeurons.get(i).getInputs().get(neuron).getWeight()
                                * nextHiddenLayerNeurons.get(i).getSignalError();
                    }
                }
                actualHiddenLayerNeurons.get(neuron).setSignalError(signalError);
            }
        }
    }

    //Updating weights in each neuron, using pre-calculated signal error
    public void updateWeights(){
        this.updateSignalErrors();

        for (int i = 0; i < this.hiddenLayers.size(); ++i){
            int layerSize = this.hiddenLayers.get(i).getSize();
            for (int j = 0; j < layerSize; ++j){
                ArrayList<NeuronInput> inputs = this.hiddenLayers.get(i).getNeurons().get(j).getInputs();
                ArrayList<Neuron> neurons = this.hiddenLayers.get(i).getNeurons();
                for (int k = 0; k < inputs.size(); ++k){
                    double newWeight = inputs.get(k).getWeight() + learningRate // learning factor
                            * neurons.get(j).getSignalError() * neurons.get(j).derivativeFunction()
                            * inputs.get(k).getInputVal();
                    neurons.get(j).getInputs().get(k).setWeight(newWeight);
                }
            }
        }

        for (int i = 0; i < this.outputLayer.getSize(); ++i){
            ArrayList<NeuronInput> inputs = this.outputLayer.getNeurons().get(i).getInputs();
            ArrayList<Neuron> neurons = this.outputLayer.getNeurons();
            for (int j = 0; j < inputs.size(); ++j){
                double newWeight = inputs.get(j).getWeight() + learningRate // learning factor
                        * neurons.get(i).getSignalError() * neurons.get(i).derivativeFunction()
                        * inputs.get(j).getInputVal();
                neurons.get(i).getInputs().get(j).setWeight(newWeight);
            }
        }
    }
}

package pl.ishfid.psi;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ishfi on 11.12.2016.
 */
public class SingleLayerNetwork {
    public DataManager dataManager;

    private double learningRate;
    private Layer inputLayer;
    private Layer outputLayer;

    public SingleLayerNetwork(int inputs, int outputs, double learningRate, NeuronFactory factory) throws IOException {
        this.learningRate = learningRate;
        this.inputLayer = new Layer(factory);
        this.outputLayer = new Layer(factory);
        this.dataManager = new DataManager();
        this.dataManager.importData("DataSet.prn");

        this.inputLayer.fillInputLayer(inputs);
        this.outputLayer.fillLayer(outputs, this.inputLayer.getSize());
    }

    public Layer getOutputLayer() {
        return outputLayer;
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
        for (Neuron neuron : this.outputLayer.getNeurons()){
            for (int input = 0; input < neuron.getInputs().size(); ++input){
                neuron.getInputs().get(input).setInputVal(inputLayer.getNeurons().get(input).getOutputVal());
            }
            neuron.calcOutput();
        }
    }

    public void updateSignalErrors() {
        for (Neuron output : this.outputLayer.getNeurons()) {
            output.calcSignalError();
        }
    }

    public void updateWeights(){
        this.updateSignalErrors();

        ArrayList<Neuron> neurons = this.outputLayer.getNeurons();
        for (int i = 0; i < this.outputLayer.getSize(); ++i){
            ArrayList<NeuronInput> inputs = this.outputLayer.getNeurons().get(i).getInputs();
            for (int j = 0; j < inputs.size(); ++j){
                double newWeight = inputs.get(j).getWeight() + learningRate // learning factor
                        * neurons.get(i).getSignalError() * neurons.get(i).derivativeFunction()
                        * inputs.get(j).getInputVal();
                neurons.get(i).getInputs().get(j).setWeight(newWeight);
            }
        }
    }

    public void updateWeightsNoTeacher(){
        ArrayList<Neuron> neurons = this.outputLayer.getNeurons();
        for (int i = 0; i < this.outputLayer.getSize(); ++i){
            neurons.get(i).updateWeights(learningRate);
        }
    }

    public void updateWeightsHebbRuleWithTeacher(){
        this.updateSignalErrors();

        ArrayList<Neuron> neurons = this.outputLayer.getNeurons();
        for (int i = 0; i < this.outputLayer.getSize(); ++i){
            ArrayList<NeuronInput> inputs = this.outputLayer.getNeurons().get(i).getInputs();
            for (int j = 0; j < inputs.size(); ++j){
                double newWeight = inputs.get(j).getWeight() + learningRate // learning factor
                        * neurons.get(i).getTargetVal()
                        * inputs.get(j).getInputVal();
                neurons.get(i).getInputs().get(j).setWeight(newWeight);
            }
        }
    }

    public void updateWeightsOjasRuleWithTeacher(){
        ArrayList<Neuron> neurons = this.outputLayer.getNeurons();
        for (int i = 0; i < this.outputLayer.getSize(); ++i){
            ArrayList<NeuronInput> inputs = this.outputLayer.getNeurons().get(i).getInputs();
            for (int j = 0; j < inputs.size(); ++j){
                double newWeight = inputs.get(j).getWeight() + learningRate // learning factor
                        * neurons.get(i).getTargetVal()
                        * (inputs.get(j).getInputVal() - neurons.get(i).getTargetVal() * inputs.get(j).getWeight());
                neurons.get(i).getInputs().get(j).setWeight(newWeight);
            }
        }
    }
}

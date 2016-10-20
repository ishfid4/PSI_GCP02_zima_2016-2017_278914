package pl.ishfid.psi;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

/**
 * Created by ishfid on 20.10.2016.
 */
public class ExampleNeuralNetwork {
    private double[][] input;
    private double[][] idealOutput;
    private BasicMLDataSet trainingSet;
    private BasicNetwork network;

    public ExampleNeuralNetwork(double[][] input, double[][] idealOutput) {
        this.input = input;
        this.idealOutput = idealOutput;
    }

    public void setUpNeuralNetwork() {
        // Creating training data set
        trainingSet = new BasicMLDataSet(input, idealOutput);

        // Creating basic network
        network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, 2)); // Input layer
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 2)); // Hiden layer
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1)); // Output layer
        network.getStructure().finalizeStructure(); // Ends setting/adding layers
        network.reset(); // Initialize randomly connections weight
    }

    public void trainNeuralNetwork() {
        // Training
        //ResilientPropagation train = new ResilientPropagation(network, trainingSet);
        Backpropagation train = new Backpropagation(network, trainingSet, 0.7, 0.2); // learning rate, momentum

        int epoch = 0; // epoch = iteration
        do {
            train.iteration();
            epoch++;
            System.out.println(" Epoch No: " + epoch + ", Error: " + train.getError());
        } while (train.getError() > 0.001);
    }

    public void testNeuralNetwork() {
        // Evaluation / Testing
        for (MLDataPair item : trainingSet) {
            MLData output = network.compute(item.getInput());
            System.out.println("Input: " + item.getInput().getData(0) + ", " + item.getInput().getData(1) + " Ideal: "
                    + item.getIdeal().getData(0) + "  Actual: " + output.getData(0));
        }
    }
}

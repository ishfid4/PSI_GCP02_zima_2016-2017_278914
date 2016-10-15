package pl.ishfid.psi;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.simple.TrainAdaline;
import org.encog.neural.pattern.ADALINEPattern;

/**
 * Created by ishfid on 09.10.16.
 */
public class Main {
    public static void main(String[] args) {

        // ------ Single Neuron Perceptor ------
        // OR problem
/*        Neuron perceptron = new Neuron(0.5);
        double[][] inputs = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };
        // OR
        int[] outputs = {0, 1, 1, 1};

        perceptron.learn(inputs, outputs, .01);

        System.out.println("For Input: 0, 0 -> Output: "+ perceptron.output(new double[]{0, 0}));
        System.out.println("For Input: 0, 1 -> Output: "+ perceptron.output(new double[]{0, 1}));
        System.out.println("For Input: 1, 0 -> Output: "+ perceptron.output(new double[]{1, 0}));
        System.out.println("For Input: 1, 1 -> Output: "+ perceptron.output(new double[]{1, 1}));
*/

        // -----------XOR Proplem -------------
        double[][] xorInput = {
                {0.0, 0.0},
                {0.0, 1.0},
                {1.0, 0.0},
                {1.0, 1.0}
        };

        double[][] xorIdealOutput = {
                {0.0},
                {1.0},
                {1.0},
                {0.0}
        };

        // Creating training data set
        BasicMLDataSet trainingSet = new BasicMLDataSet(xorInput, xorIdealOutput);

        // Creating basic network
      /*  BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, 2)); // Input layer
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 2)); // Hiden layer
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1)); // Output layer
        network.getStructure().finalizeStructure(); // Ends setting/adding layers
        network.reset(); // Initialize randomly connections weight

        // Training
        //ResilientPropagation train = new ResilientPropagation(network, trainingSet);
        Backpropagation train = new Backpropagation(network, trainingSet, 0.7, 0.2); // learning rate, momentum

        int epoch = 0; // epoch = iteration
        do {
            train.iteration();
            epoch++;
            System.out.println(" Epoch No: " + epoch + ", Error: " + train.getError());
        } while (train.getError() > 0.001);

        // Evaluation / Testing
        for (MLDataPair item : trainingSet) {
            MLData output = network.compute(item.getInput());
            System.out.println("Input: " + item.getInput().getData(0) + ", " + item.getInput().getData(1) + " Ideal: "
                    + item.getIdeal().getData(0) + "  Actual: " + output.getData(0));
        }*/

        //--------- ADALINE pattern --------
        //--------- XOR ptoblem ------------
        // Setting up
        ADALINEPattern pattern = new ADALINEPattern();
        pattern.setInputNeurons(2);
        pattern.setOutputNeurons(1);
        BasicNetwork network_Adaline = (BasicNetwork) pattern.generate();

        // Training it
        MLTrain train_Adaline = new TrainAdaline(network_Adaline, trainingSet, 0.01);

        int epoch_Adaline = 1;
        do {
            train_Adaline.iteration();
            System.out.println("Epoch #" + epoch_Adaline + " Error:" + train_Adaline.getError());
            epoch_Adaline++;
        } while (train_Adaline.getError() > 0.26);

        // Testing it
        for (MLDataPair item : trainingSet) {
            MLData output = network_Adaline.compute(item.getInput());;
            System.out.println("Input: " + item.getInput().getData(0) + ", " + item.getInput().getData(1) + " Ideal: "
                    + item.getIdeal().getData(0) + "  Actual: " + output.getData(0));
        }
    }
}

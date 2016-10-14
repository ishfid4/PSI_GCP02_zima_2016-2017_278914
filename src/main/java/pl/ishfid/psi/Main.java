package pl.ishfid.psi;

import javafx.util.Pair;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import java.util.ArrayList;

/**
 * Created by ishfid on 09.10.16.
 */
public class Main {
    public static void main(String[] args){

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
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, 2)); // Input layer
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 2)); // Hiden layer
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1)); // Output layer
        network.getStructure().finalizeStructure(); // Ends setting/adding layers
        network.reset(); // Initialize randomly connections weight

        // Training
        ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        int epoch = 0; // epoch = iteration
        do {
            train.iteration();
            epoch++;
            System.out.println(" Epoch No: " + epoch + ", Error: " + train.getError());
        }while (train.getError() > 0.001);

        // Evaluation / Testing
        for (MLDataPair item: trainingSet) {
            MLData output = network.compute(item.getInput());
            System.out.println("Input: " + item.getInput().getData(0) + ", " + item.getInput().getData(1) + " Ideal: "
                    + item.getIdeal().getData(0) + "  Actual: " + output.getData(0));
        }
    }
}

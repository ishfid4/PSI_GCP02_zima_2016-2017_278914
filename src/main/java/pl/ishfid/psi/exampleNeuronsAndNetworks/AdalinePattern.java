package pl.ishfid.psi.exampleNeuronsAndNetworks;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.simple.TrainAdaline;
import org.encog.neural.pattern.ADALINEPattern;

/**
 * Created by ishfid on 20.10.2016.
 */
public class AdalinePattern {
    private double[][] input;
    private double[][] idealOutput;
    private int inputNeurons, outputNeurons;
    private BasicMLDataSet trainingSet;
    private ADALINEPattern pattern;
    private BasicNetwork network_Adaline;

    public AdalinePattern(double[][] input, double[][] idealOutput, int inputNeurons, int outputNeurons) {
        this.input = input;
        this.idealOutput = idealOutput;
        this.inputNeurons = inputNeurons;
        this.outputNeurons = outputNeurons;
    }

    public void setUpAdalinePattern() {
        // Creating training data set
        trainingSet = new BasicMLDataSet(input, idealOutput);

        // Setting up
        pattern = new ADALINEPattern();
        pattern.setInputNeurons(inputNeurons);
        pattern.setOutputNeurons(outputNeurons);
        network_Adaline = (BasicNetwork) pattern.generate();
    }

    // Training it
    public void trainAdalinePattern() {
        MLTrain train_Adaline = new TrainAdaline(network_Adaline, trainingSet, 0.01);

        int epoch_Adaline = 1;
        do {
            train_Adaline.iteration();
            System.out.println("Epoch #" + epoch_Adaline + " Error:" + train_Adaline.getError());
            epoch_Adaline++;
        } while (train_Adaline.getError() > 0.26);
    }

    // Testing it
    public void testAdalinePattern() {
        for (MLDataPair item : trainingSet) {
            MLData output = network_Adaline.compute(item.getInput());

            System.out.println("Input: " + item.getInput().getData(0) + ", " + item.getInput().getData(1) + " Ideal: "
                    + item.getIdeal().getData(0) + "  Actual: " + output.getData(0));
        }
    }

}

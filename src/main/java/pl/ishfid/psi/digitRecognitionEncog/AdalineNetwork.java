package pl.ishfid.psi.digitRecognitionEncog;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.simple.TrainAdaline;
import org.encog.neural.pattern.ADALINEPattern;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ishfi on 27.11.2016.
 */
public class AdalineNetwork {
    private static int inputNeurons = 256;
    private static int outputNeurons = 10;
    private double learningRate;
    private double minError;
    private static double[][] trainingDigits;
    private static double[][] trainingDigitsValidiation;
    private static double[][] testDigits;
    private static double[][] testDigitsValidiation;
    private static Data data = new Data();

    public AdalineNetwork(double learningRate, double minError) throws IOException {
        this.learningRate = learningRate;
        this.minError = minError;
        data.importData("segregatedSmallSet.prn");
        trainingDigits = data.getInputDataSet();
        testDigits = data.getValidationDataSet();
        trainingDigitsValidiation = data.getInputVerificationSet();
        testDigitsValidiation = data.getValidationOutputDataSet();
    }

    private static MLData convertToMLData(double digit[]) {
        MLData result = new BasicMLData(digit.length);

        for(int i = 0; i < digit.length; i++)
        {
            result.setData(i,digit[i]);
        }

        return result;
    }

    private static MLDataSet generateTraining()
    {
        MLDataSet result = new BasicMLDataSet();
        for(int i = 0; i < trainingDigits.length; i++)
        {
            // setup input
            MLData input = convertToMLData(trainingDigits[i]);

            // setup ideal
            BasicMLData ideal = (BasicMLData)convertToMLData(trainingDigitsValidiation[i]);

            // add training element
            result.add(input,ideal);
        }
        return result;
    }

    public void execute() throws FileNotFoundException {
        PrintWriter printWriterMSE = null;
        PrintWriter printWriterValidation = null;
        int networkAnswers[] = new int[10];

        ADALINEPattern pattern = new ADALINEPattern();
        pattern.setInputNeurons(inputNeurons);
        pattern.setOutputNeurons(outputNeurons);
        BasicNetwork network = (BasicNetwork)pattern.generate();

        // Setting training
        MLDataSet training = generateTraining();
        MLTrain train = new TrainAdaline(network,training,learningRate);

        try{
            printWriterMSE = new PrintWriter("MSE.prn");
            printWriterValidation = new PrintWriter("validationResult.prn");

            // train it
            int epoch = 1;
            do {
                train.iteration();
                //       System.out.println(train.getError());
                System.out.println("Epoch #" + epoch + " Error:" + train.getError());
                printWriterMSE.println(epoch + "\t" + train.getError());
                epoch++;
            } while(train.getError() > minError);

            System.out.println("Network Error:" + network.calculateError(training));

            double correctAnswers = 0;
            double falseAnswers = 0;

            double[] digitCorrectAnswers = new double[10];
            double[] digitFalseAnswers = new double[10];

            // testing it
            for(int i = 0; i < testDigits.length; i++)
            {
                int output = network.winner(convertToMLData(testDigits[i]));

                for (int j = 0; j < outputNeurons; j++){
                    if (testDigitsValidiation[i][j] != 0){
                        if (output == j){
                            digitCorrectAnswers[output]++;
                            correctAnswers++;
                        }else{
                            digitFalseAnswers[j]++;
                            falseAnswers++;
                        }
                    }
                }
            }

            System.out.println("Correct: " + correctAnswers);
            System.out.println("False: " + falseAnswers);
            System.out.println("Net effectivness: " + (correctAnswers / (correctAnswers + falseAnswers)) * 100 + "%");

            System.out.println("Digit : correct|false");
            for (int i = 0; i < 10; ++i){
                System.out.println(i + " : \t" + digitCorrectAnswers[i] + "  \t" + digitFalseAnswers[i]);
                //Write to file:
                printWriterValidation.println(i + "\t" + digitCorrectAnswers[i] + "\t" + digitFalseAnswers[i]);
            }

        }finally {
            if (printWriterMSE != null) {
                printWriterMSE.close();
            }
            if (printWriterValidation != null) {
                printWriterValidation.close();
            }
        }
    }

}

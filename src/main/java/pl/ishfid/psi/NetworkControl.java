package pl.ishfid.psi;

import pl.ishfid.psi.Neuron.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by ishfi on 07.01.2017.
 */
public class NetworkControl {
    boolean multiLayerNetwork;
    int inputs, hinndenLayers, neuronsInHiddenLayer, outputs, neuronType, epochCount, learningFuction;
    double learningRate;

    public NetworkControl(boolean multiLayerNetwork, int inputs, int hinndenLayers, int neuronsInHiddenLayer,
                           int outputs, int neuronType, int learningFuction, int epochCount, double learningRate) {
        this.multiLayerNetwork = multiLayerNetwork;
        this.inputs = inputs;
        this.hinndenLayers = hinndenLayers;
        this.neuronsInHiddenLayer = neuronsInHiddenLayer;
        this.outputs = outputs;
        this.neuronType = neuronType;
        this.epochCount = epochCount;
        this.learningRate = learningRate;
    }

    public NetworkControl(boolean multiLayerNetwork, int inputs, int outputs, int neuronType, int learningFuction,
                          int epochCount, double learningRate) {
        this.multiLayerNetwork = multiLayerNetwork;
        this.inputs = inputs;
        this.outputs = outputs;
        this.neuronType = neuronType;
        this.epochCount = epochCount;
        this.learningRate = learningRate;
    }

    public void execute() throws IOException {
        PrintWriter printWriterMSE = null;
        PrintWriter printWriterValidation = null;
        Network multiNetwork = null;
        SingleLayerNetwork singleNetwork = null;
        ArrayList<Neuron> outputNeurons;
        double records;
        int outputCount;

        if (multiLayerNetwork){
//          Netowrk(inputsCount, hiddenLayerCount, hiddenLayerInputs, outputCount, learningRate, neuronFactory)
            switch (neuronType){
                case 0:
                    multiNetwork = new Network(inputs, hinndenLayers, neuronsInHiddenLayer,
                            outputs, learningRate, new PerceptronFactory());
                    break;
                case 1:
                    multiNetwork = new Network(inputs, hinndenLayers, neuronsInHiddenLayer,
                            outputs, learningRate, new McCullohPittsFactory());
                    break;
                case 2:
                    multiNetwork = new Network(inputs, hinndenLayers, neuronsInHiddenLayer,
                            outputs, learningRate, new McCPforHebbFactory());
                    break;
                case 3:
                    multiNetwork = new Network(inputs, hinndenLayers, neuronsInHiddenLayer,
                            outputs, learningRate, new McCPforOjaFactory());
                    break;
            }

            outputCount = multiNetwork.dataManager.outputCount;
            records = multiNetwork.dataManager.learningRecordCount;
            outputNeurons = multiNetwork.getOutputLayer().getNeurons();
        }else{
            switch (neuronType){
                case 0:
                    singleNetwork = new SingleLayerNetwork(inputs, outputs, learningRate, new PerceptronFactory());
                    break;
                case 1:
                    singleNetwork = new SingleLayerNetwork(inputs, outputs, learningRate, new McCullohPittsFactory());
                    break;
                case 2:
                    singleNetwork = new SingleLayerNetwork(inputs, outputs, learningRate, new McCPforHebbFactory());
                    break;
                case 3:
                    singleNetwork = new SingleLayerNetwork(inputs, outputs, learningRate, new McCPforOjaFactory());
                    break;
            }

            outputCount = SingleLayerNetwork.dataManager.outputCount;
            records = SingleLayerNetwork.dataManager.learningRecordCount;
            outputNeurons = SingleLayerNetwork.getOutputLayer().getNeurons();
        }

        try {
            printWriterMSE = new PrintWriter("MSE.prn");
            printWriterValidation = new PrintWriter("validationResult.prn");

            // Get current time
            long start = System.currentTimeMillis();

            for (int epoch = 1; epoch <= epochCount; ++epoch) {
                double MSE = 0;

                for (int i = 0; i < records; i++) {
                    if (multiLayerNetwork){
                        multiNetwork.setInputValues(i, true);
                        multiNetwork.setTargetValues(i, true);

                        multiNetwork.feedForward();
                        switch (learningFuction){
                            case 0:
                                multiNetwork.updateWeights(); //By default Back propagation
                                break;
                            case 1:
                                multiNetwork.updateWeightsHebbRuleNoTeacher(); // Still don't work ¯\_(ツ)_/¯
                                break;
                            case 2:
                                multiNetwork.updateWeightsHebbRuleWithTeacher(); // ~~30% efficiency, (256, 1, 32, 10, 0.01
                                break;
                            case 3:
                                multiNetwork.updateWeightsOjasRuleNoTeacher(); // Don't work ¯\_(ツ)_/¯
                                break;
                            case 4:
                                multiNetwork.updateWeightsOjasRuleWithTeacher(); // ~~45% efficiency, (256, 1, 32, 10, 0.005
                                break;
                            case 5:
//                                multiNetwork.updateWeightsWinnerTakesAll(); // learning factor between 0.8 <-> 0.1 it was deleted???
                                break;
                        }
                    }else{
                        singleNetwork.setInputValues(i, true);
                        singleNetwork.setTargetValues(i, true);

                        singleNetwork.feedForward();
                        switch (learningFuction){
                            case 0:
                                singleNetwork.updateWeights(); //By default Back propagation
                                break;
                            case 1:
//                                singleNetwork.updateWeightsHebbRuleNoTeacher(); //Not implemented
                                break;
                            case 2:
                                singleNetwork.updateWeightsHebbRuleWithTeacher(); // ~~30% efficiency, (256, 1, 32, 10, 0.01
                                break;
                            case 3:
//                                singleNetwork.updateWeightsOjasRuleNoTeacher(); // //Not implemented
                                break;
                            case 4:
                                singleNetwork.updateWeightsOjasRuleWithTeacher(); // ~~45% efficiency, (256, 1, 32, 10, 0.005
                                break;
                            case 5:
                                singleNetwork.updateWeightsWinnerTakesAll(); // learning factor between 0.8 <-> 0.1 it was deleted???
                                break;
                            case 6:
                                singleNetwork.updateWeightsNoTeacher();  // (╯°□°）╯︵ ┻━┻
                                break;
                        }
                    }



                    double uniqueMSEerror;
                    for (int j = 0; j < outputCount; ++j) {
                        double difference = outputNeurons.get(j).getTargetVal() - outputNeurons.get(j).getOutputVal();
                        uniqueMSEerror = Math.pow(difference, 2);
                        MSE += uniqueMSEerror;
                    }
                }
                MSE /= (records);
                System.out.println("Epoch: " + epoch + ", MSE: " + MSE);
                //Write to file:
                printWriterMSE.println(epoch + "\t" + MSE);
            }
            // Get elapsed time in milliseconds
            long elapsedTimeMillis = System.currentTimeMillis() - start;
            float elapsedTimeSec = elapsedTimeMillis / 1000F;

            int validationRecords;
            if (multiLayerNetwork) {
                validationRecords = multiNetwork.dataManager.validationRecordCount;
            }else{
                validationRecords = singleNetwork.dataManager.validationRecordCount;

            }
            System.out.println("Validation");

            double correctAnswers = 0;
            double falseAnswers = 0;

            double[] digitCorrectAnswers = new double[10];
            double[] digitFalseAnswers = new double[10];

            for (int i = 0; i < validationRecords; i++) {
                ArrayList<Double> targetOutputs;
                if (multiLayerNetwork) {
                    targetOutputs = multiNetwork.dataManager.validationOutputDataSet.get(i);

                    multiNetwork.setInputValues(i, false);
                    multiNetwork.setTargetValues(i, false); // validation

                    multiNetwork.feedForward();
                }else{
                    targetOutputs = singleNetwork.dataManager.validationOutputDataSet.get(i);

                    singleNetwork.setInputValues(i, false);
                    singleNetwork.setTargetValues(i, false); // validation

                    singleNetwork.feedForward();
                }
                int answerDigit = networkAnswer(outputNeurons);
                int targetDigit = targetAnswer(targetOutputs);
                if (answerDigit == targetDigit) {
                    digitCorrectAnswers[answerDigit]++;
                    correctAnswers++;
                } else {
                    digitFalseAnswers[targetDigit]++;
                    falseAnswers++;
                }
            }

            System.out.println("Correct: " + correctAnswers);
            System.out.println("False: " + falseAnswers);
            System.out.println("Net effectivness: " + (correctAnswers / (correctAnswers + falseAnswers)) * 100 + "%");
            //Write to file:
            printWriterValidation.println(correctAnswers + "\t" + falseAnswers + "\t" +
                    (correctAnswers / (correctAnswers + falseAnswers)) * 100 + "\t" + elapsedTimeSec);

            System.out.println("Digit : correct|false");
            for (int i = 0; i < 10; ++i) {
                System.out.println(i + " : \t" + digitCorrectAnswers[i] + "  \t" + digitFalseAnswers[i]);
                //Write to file:
                printWriterValidation.println(i + "\t" + digitCorrectAnswers[i] + "\t" + digitFalseAnswers[i]);
            }
        } finally {
            if (printWriterMSE != null) {
                printWriterMSE.close();
            }
            if (printWriterValidation != null) {
                printWriterValidation.close();
            }
        }
    }

    private static int networkAnswer(ArrayList<Neuron> outputNeurons){
        int answer = 0;
        for (int i = 0; i < 10; ++i){
            if (outputNeurons.get(answer).getOutputVal() <= outputNeurons.get(i).getOutputVal()){
                answer = i;
            }
        }
        return answer;
    }

    private static int targetAnswer(ArrayList<Double> targetOutputs){
        int answer = 0;
        for (int i = 0; i < 10; ++i) {
            if (targetOutputs.get(i) == 1){
                answer = i;
            }
        }
        return answer;
    }
}

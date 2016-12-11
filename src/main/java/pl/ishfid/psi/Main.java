package pl.ishfid.psi;

import pl.ishfid.psi.digitRecognitionEncog.Data;
import pl.ishfid.psi.digitRecognitionEncog.Hebb;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ishfid on 09.10.16.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        int epochCount = 100;

//      Netowrk(inputsCount, hiddenLayerCount, hiddenLayerInputs, outputCount, learningRate, neuronFactory)
        Network network = new Network(256, 2, 32, 10, 0.01, new McCullohPittsFactory());
        int outputCount = network.dataManager.outputCount;
        double records = network.dataManager.learningRecordCount;
        ArrayList<Neuron> outputNeurons = network.getOutputLayer().getNeurons();

        for (int epoch = 1;  epoch <= epochCount; ++epoch){
            double MSE = 0;

            for (int i = 0; i < records; i++){
                network.setInputValues(i, true);
                network.setTargetValues(i, true);

                network.feedForward();
                network.updateWeights();
//                network.updateWeightsHebbRuleNoTeacher(); //Still don't work ¯\_(ツ)_/¯
//                network.updateWeightsHebbRuleWithTeacher(); // ~~30% efficiency
                double uniqueMSEerror;
                for (int j = 0; j < outputCount; ++j){
                    double difference = outputNeurons.get(j).getTargetVal() - outputNeurons.get(j).getOutputVal();
                    uniqueMSEerror = Math.pow(difference, 2);
                    MSE += uniqueMSEerror;
                }
            }
            MSE /= (records);
            System.out.println("Epoch: " + epoch + ", MSE: " + MSE);
        }

        int validationRecords = network.dataManager.validationRecordCount;
        System.out.println("Validation");

        double correctAnswers = 0;
        double falseAnswers = 0;

        double[] digitCorrectAnswers = new double[10];
        double[] digitFalseAnswers = new double[10];

        for (int i = 0; i < validationRecords; i++){
            ArrayList<Double> targetOutputs = network.dataManager.validationOutputDataSet.get(i);

            network.setInputValues(i, false);
            network.setTargetValues(i, false); // validation

            network.feedForward();

            int answerDigit = (int)networkAnswer(outputNeurons);
            int targetDigit = (int)targetAnswer(targetOutputs);
            if (answerDigit == targetDigit){
                digitCorrectAnswers[answerDigit]++;
                correctAnswers++;
            }else{
                digitFalseAnswers[targetDigit]++;
                falseAnswers++;
            }
        }

        System.out.println("Correct: " + correctAnswers);
        System.out.println("False: " + falseAnswers);
        System.out.println("Net effectivness: " + (correctAnswers / (correctAnswers + falseAnswers)) * 100 + "%");

        System.out.println("Digit : correct|false");
        for (int i = 0; i < 10; ++i){
            System.out.println(i + " : \t" + digitCorrectAnswers[i] + "  \t" + digitFalseAnswers[i]);
        }



        //----------Random trash---------------
//        Data data = new Data();
//        Hebb hebbNetwork = new Hebb(35, 0.7);
//        hebbNetwork.run(data.getDigits());

        //------------Digits recognition using Adaline------------
//        Data data = new Data();
//        AdalineNetwork adalineNetwork = new AdalineNetwork(data);
//        adalineNetwork.execute();

        //-----------------Old implemetations---------------------
//        LogicalFunctions logicalFunctions = new LogicalFunctions();
//        logicalFunctions.orProblemPerceptron();;
//        logicalFunctions.xorProblem3LayerNetwork();
//        logicalFunctions.xorProblemAdalinePattern();
    }

    static int networkAnswer(ArrayList<Neuron> outputNeurons){
        int answer = 0;
        for (int i = 0; i < 10; ++i){
            if (outputNeurons.get(answer).getOutputVal() <= outputNeurons.get(i).getOutputVal()){
                answer = i;
            }
        }
        return answer;
    }

    static int targetAnswer(ArrayList<Double> targetOutputs){
        int answer = 0;
        for (int i = 0; i < 10; ++i) {
            if (targetOutputs.get(i) == 1){
                answer = i;
            }
        }
        return answer;
    }
}

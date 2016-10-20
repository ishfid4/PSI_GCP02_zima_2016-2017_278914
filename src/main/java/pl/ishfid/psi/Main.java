package pl.ishfid.psi;

/**
 * Created by ishfid on 09.10.16.
 */
public class Main {
    public static void main(String[] args) {

        // ------ Single Neuron Perceptor ------
        // OR problem
        Neuron perceptron = new Neuron(0.5);
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

        //----- Simple 3-layer network -----
        ExampleNeuralNetwork exampleNeuralNetwork = new ExampleNeuralNetwork(xorInput, xorIdealOutput);
        exampleNeuralNetwork.setUpNeuralNetwork();
        exampleNeuralNetwork.trainNeuralNetwork();
        exampleNeuralNetwork.testNeuralNetwork();

        //--------- ADALINE pattern --------
        AdalinePattern adalinePattern = new AdalinePattern(xorInput, xorIdealOutput, 2, 1);
        adalinePattern.setUpAdalinePattern();
        adalinePattern.trainAdalinePattern();
        adalinePattern.testAdalinePattern();


    }
}

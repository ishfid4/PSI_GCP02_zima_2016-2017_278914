package pl.ishfid.psi.exampleNeuronsAndNetworks;

/**
 * Created by ishfi on 27.11.2016.
 */
public class LogicalFunctions {
    // -----------XOR Proplem Data-------------
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

    // -----------OR Proplem Data-------------
    double[][] inputs = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };
    int[] outputs = {0, 1, 1, 1};


    public LogicalFunctions() {
    }

    // -----------XOR Proplem -------------
    //----- Simple 3-layer network -----
    public void xorProblem3LayerNetwork(){
        ExampleNeuralNetwork exampleNeuralNetwork = new ExampleNeuralNetwork(xorInput, xorIdealOutput);
        exampleNeuralNetwork.setUpNeuralNetwork();
        exampleNeuralNetwork.trainNeuralNetwork();
        exampleNeuralNetwork.testNeuralNetwork();
    }

    //--------- ADALINE pattern --------
    public void xorProblemAdalinePattern(){
        AdalinePattern adalinePattern = new AdalinePattern(xorInput, xorIdealOutput, 2, 1);
        adalinePattern.setUpAdalinePattern();
        adalinePattern.trainAdalinePattern();
        adalinePattern.testAdalinePattern();
    }

    // ------ Single Neuron Perceptor ------
    public void orProblemPerceptron(){
        Neuron perceptron = new Neuron(0.5);
        perceptron.learn(inputs, outputs, .01);

        System.out.println("For Input: 0, 0 -> Output: "+ perceptron.output(new double[]{0, 0}));
        System.out.println("For Input: 0, 1 -> Output: "+ perceptron.output(new double[]{0, 1}));
        System.out.println("For Input: 1, 0 -> Output: "+ perceptron.output(new double[]{1, 0}));
        System.out.println("For Input: 1, 1 -> Output: "+ perceptron.output(new double[]{1, 1}));
    }

}

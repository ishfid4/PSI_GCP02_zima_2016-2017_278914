package pl.ishfid.psi.digitRecognition;

import java.util.Random;

/**
 * Created by ishfi on 27.11.2016.
 */
public class Hebb {
    private double[] weights;
    private double learningRate;
    private int epoch;
    private int[][] digits;

    public Hebb(int neuronsCount, double learningRate) {
        this.weights = new double[neuronsCount];
        this.learningRate = learningRate;
        this.epoch = 1;
        this.digits = new int[10][35];
        initWeights(neuronsCount);
    }

    //This method loops through 200 epochs.
    public void run(String[][] input) {
        generateDataSet(input);
        for (int j = 0; j < 20; ++j){
            System.out.println("Runn of all digits");
            for (int i = 0; i < digits.length; ++i) {
                epoch(this.digits[i]);
            }
        }
    }

    protected void epoch(int[] digit) {
        System.out.println("***Beginning Epoch #" + this.epoch + "***");
        presentPattern(digit);
        this.epoch++;
    }

    protected void presentPattern(int[] digit) {
        double result;
        double delta;

        // run the net as is on training data
        // and get the error
//        for (int i = 0; i < this.weights.length; ++i){
//            for (int j = 0; i < 5; ++j){
//                System.out.print(digit[i]);
//            }
//            System.out.println();
//        }

        result = recognize(digit);
        System.out.println(" result=" + result);

        // adjust weights
        for (int i = 0; i < this.weights.length; ++i) {
            delta = trainingFunction(this.learningRate, digit[i], result);
            this.weights[i] += delta;
            //System.out.print(",delta w1=" + delta);
        }
    }

    protected double recognize(int[] digit) {
        double a = 0;
        for (int i = 0; i < this.weights.length; ++i){
            a += (double) digit[i]*weights[i];
        }
        return (a * .5);
    }

    double trainingFunction(double rate, int input, double output) {
        return rate * (double)input * output;
    }

    private void initWeights(int length){
        Random random = new Random();

        for (int i = 0; i < this.weights.length; i++){
            this.weights[i] = random.nextDouble();
        }
    }

    private void generateDataSet(String[][] input){
        int isFieldNotEmpty;

        for (int digitCount = 0; digitCount < 10; ++digitCount) {
            for (int row = 0; row < 7; row++) {
                for (int col = 0; col < 5; col++) {
                    int index = (row * 5) + col;
                    char ch = input[digitCount][row].charAt(col);
                    if (ch == 'O')
                        isFieldNotEmpty = 1;
                    else
                        isFieldNotEmpty = -1;

                    this.digits[digitCount][index] = isFieldNotEmpty;
                }
            }
        }
    }
}

package pl.ishfid.psi;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ishfi on 10.10.2016.
 */
public class Neuron {
    private double[] weights;
    private double treshhold;

    public Neuron(double treshhold){
        this.treshhold = treshhold;
    }

    public void learn(double[][] inputs, int[] outputs, double learnFactor){
        initWeights(inputs[0].length);
        int errorCount = 0;

        for (;;) {
            boolean wasMistake = false;

            for (int i = 0; i < outputs.length; i++) {
                int output = output(inputs[i]);
                int mistake = outputs[i] - output;

                if (mistake != 0) {
                    for (int j = 0; j < weights.length - 1; j++) {
                        weights[j] += learnFactor * mistake * inputs[i][j];
                    }
                    weights[weights.length - 1] += learnFactor * mistake;

                    wasMistake = true;
                    errorCount++;
                }

            }

            if (!wasMistake) {
                System.out.println("Mistakes: " + errorCount);
                break;
            }
        }
    }

    public int output(double[] input){
        double sum = 0.0;
        for (int i = 0; i < input.length; i++){
            sum += weights[i] * input[i];
        }

        sum += weights[weights.length - 1];

        if ( sum <= treshhold){
            return 0;
        } else {
            return 1;
        }
    }

    private void initWeights(int length){
        Random random = new Random();
        length++;
        weights = new double[length];
        for (int i = 0; i < weights.length; i++){
            weights[i] = random.nextDouble();
        }
    }
}

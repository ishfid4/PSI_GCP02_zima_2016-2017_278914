package pl.ishfid.psi;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by ishfid on 09.10.16.
 */
public class Main {
    public static void main(String[] args){
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

        System.out.println(perceptron.output(new double[]{0, 0}));
        System.out.println(perceptron.output(new double[]{0, 1}));
        System.out.println(perceptron.output(new double[]{1, 0}));
        System.out.println(perceptron.output(new double[]{1, 1}));
    }
}

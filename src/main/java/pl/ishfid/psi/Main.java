package pl.ishfid.psi;

import pl.ishfid.psi.Neuron.McCullohPittsFactory;
import pl.ishfid.psi.Neuron.Neuron;
import pl.ishfid.psi.digitRecognitionEncog.AdalineNetwork;
import pl.ishfid.psi.digitRecognitionEncog.Data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by ishfid on 09.10.16.
 */
public class Main {
    // TODO: Hebb's rule -> forgetting factor | but Heb's rule is probably broken
    // TODO: make DataManager more flexible (size of learningSet and validSet should be dependant on variables)

    public static void main(String[] args) throws IOException {
        int typeOfNetwork = 0;
        int typeOfNeuron = 0;
        int typeOfLearningFuction = 0;
        double learningRate = 0.0;
        int epochCount = 0;
        int hiddenLayer = 0;
        int neuronsInHiddenLayers = 0;
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.ENGLISH);


        System.out.println("DIGITS RECOGNITION: ");
        System.out.println("Chose type of netwoerk: ");
        System.out.println("0 - Adaline (ENCOG) \n1 - Multilayer \n2 - Singlelayer");
        typeOfNetwork = scanner.nextInt();

        System.out.print("\nSet learning rate: ");
        learningRate = scanner.nextDouble();

        if (typeOfNetwork == 0){
            //------------Digits recognition using Adaline------------
            Data data = new Data();
            AdalineNetwork adalineNetwork = new AdalineNetwork(learningRate, 0.01);
            adalineNetwork.execute();
        }

        if (typeOfNetwork != 0){
            System.out.println("Chose type of neuron:");
            System.out.println("0 - Perceptron \n1 - McCullohPitts" +
                    " \n2 - MCP different activation function (Better in Hebb rule) " +
                    "\n3 - MCP different activation function (Better in Hebb rule)");
            typeOfNeuron = scanner.nextInt();

            System.out.print("\nSet epoch couint: ");
            epochCount = scanner.nextInt();
        }

        if (typeOfNetwork == 1){
            System.out.println("Chose type of learning function:");
            System.out.println("0 - Back propagation \n1 - Hebb Rule No Teacher" +
                    " \n2 - Hebb Rule With Teacher \n3 - Ojas Rule No Teacher");
            typeOfLearningFuction = scanner.nextInt();

            System.out.print("\nSet hidden layers count: ");
            hiddenLayer = scanner.nextInt();

            System.out.print("\nSet neurons in hidden layers: ");
            neuronsInHiddenLayers = scanner.nextInt();

            //Multilayer
            NetworkControl networkControl = new NetworkControl(true,256,hiddenLayer,
                    neuronsInHiddenLayers,10,typeOfNeuron,typeOfLearningFuction,epochCount,learningRate);
            networkControl.execute();
        }

        if (typeOfNetwork == 2){
            System.out.println("Chose type of learning function:");
            System.out.println("0 - Back propagation \n2 - Hebb Rule With Teacher" +
                    "\n3 - Ojas Rule With Teacher \n4 - Winner Takes All");
            typeOfLearningFuction = scanner.nextInt();
            //SingleLayer
            NetworkControl networkControl = new NetworkControl(false,256,10,typeOfNeuron,
                    typeOfLearningFuction,epochCount,learningRate);
            networkControl.execute();
        }

        //-----------------Old implemetations---------------------
//        LogicalFunctions logicalFunctions = new LogicalFunctions();
//        logicalFunctions.orProblemPerceptron();;
//        logicalFunctions.xorProblem3LayerNetwork();
//        logicalFunctions.xorProblemAdalinePattern();
    }
}

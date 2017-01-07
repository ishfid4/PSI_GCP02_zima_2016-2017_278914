package pl.ishfid.psi;

import pl.ishfid.psi.Neuron.McCullohPittsFactory;
import pl.ishfid.psi.Neuron.Neuron;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by ishfid on 09.10.16.
 */
public class Main {
    // TODO: Hebb's rule -> forgetting factor | but Heb's rule is probably broken
    // TODO: make DataManager more flexible (size of learningSet and validSet should be dependant on variables)

    public static void main(String[] args) throws IOException {

        //Multilayer
        NetworkControl networkControl = new NetworkControl(true,256,2,32,10,1,0,150,0.01);
        //SingleLayer
        //NetworkControl networkControl = new NetworkControl(false,256,10,1,0,150,0.01);
        networkControl.execute();


        //------------Digits recognition using Adaline------------
//        Data data = new Data();
//        AdalineNetwork adalineNetwork = new AdalineNetwork(0.001, 0.01);
//        adalineNetwork.execute();

        //-----------------Old implemetations---------------------
//        LogicalFunctions logicalFunctions = new LogicalFunctions();
//        logicalFunctions.orProblemPerceptron();;
//        logicalFunctions.xorProblem3LayerNetwork();
//        logicalFunctions.xorProblemAdalinePattern();
    }
}

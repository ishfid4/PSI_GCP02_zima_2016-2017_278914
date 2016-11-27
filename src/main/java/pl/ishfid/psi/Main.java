package pl.ishfid.psi;

import pl.ishfid.psi.digitRecognition.AdalineNetwork;
import pl.ishfid.psi.digitRecognition.Data;
import pl.ishfid.psi.exampleNeuronsAndNetworks.LogicalFunctions;

/**
 * Created by ishfid on 09.10.16.
 */
public class Main {



    public static void main(String[] args) {
        //------------Digits recognition using Adaline------------
        Data data = new Data();
        AdalineNetwork adalineNetwork = new AdalineNetwork(data);
        adalineNetwork.execute();

        //-----------------Old implemetations---------------------
//        LogicalFunctions logicalFunctions = new LogicalFunctions();
//        logicalFunctions.orProblemPerceptron();;
//        logicalFunctions.xorProblem3LayerNetwork();
//        logicalFunctions.xorProblemAdalinePattern();
    }

}

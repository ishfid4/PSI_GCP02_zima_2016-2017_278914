package pl.ishfid.psi.SOMEncog;

import org.encog.Encog;
import org.encog.mathutil.rbf.RBFEnum;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.som.SOM;
import org.encog.neural.som.training.basic.BasicTrainSOM;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodRBF;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by ishfi on 18.12.2016.
 */
public class SimpleSOM {

    public static void main(String args[]) throws IOException {
        PrintWriter MSE = null;
        PrintWriter validation = null;

        DataSet dataSet = new DataSet();
        dataSet.importData("segregatedSmallSet.prn");

        // create the training set
        ArrayList<MLDataSet> training = new ArrayList<>();
        for (int i = 0; i < 10; ++i){
            training.add(new BasicMLDataSet(dataSet.inputDataSet[i],null));
        }

        // Create the neural network.
        SOM network = new SOM(256,10);
        network.reset();

        ArrayList<BasicTrainSOM> train = new ArrayList<>();
        for (int i = 0; i < 10; ++i){
            train.add(new BasicTrainSOM(network,0.01, training.get(i), new NeighborhoodRBF(RBFEnum.Gaussian, 16,16)));
        }

        try{
            MSE = new PrintWriter("MSE.prn");
            validation = new PrintWriter("validationResult.prn");

            int iteration = 0;
            for(iteration = 0;iteration<=100;iteration++)
            {
                double error = 0.0;
                for (int i = 0; i < 10; ++i) {
                    train.get(i).iteration();

                    error += train.get(i).getError();
                }
                System.out.println("Iteration: " + iteration + ", Error:" + error/3);
                MSE.println(iteration + "\t" + error/3);
            }

            for (int i = 0; i < 10; ++i) {
                for (int j = 0; j < 3; ++j){
                    MLData data = new BasicMLData(dataSet.validationDataSet[i][j]);
                    int winner =  network.classify(data);
                    System.out.println("Digit " + i + " output neuron winner: " + winner);
                    validation.println(i + "\t" + winner);
                }
            }
        }finally {
            if (MSE != null) {
                MSE.close();
            }
            if (validation != null) {
                validation.close();
            }
        }

        Encog.getInstance().shutdown();
    }
}

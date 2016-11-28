package pl.ishfid.psi.digitRecognitionEncog;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.simple.TrainAdaline;
import org.encog.neural.pattern.ADALINEPattern;

/**
 * Created by ishfi on 27.11.2016.
 */
public class AdalineNetwork {
    private int inputNeurons;
    private int outputNeurons;
    public final static int char_width = 5;
    public final static int char_height = 7;
    public static String[][] digits;

    public static String[][] digitstest;

    public AdalineNetwork(Data data) {
        this.digits = data.getDigits();
        this.digitstest = data.getDigitstest();
    }

    public static MLData image2data(String[] image)
    {
        MLData result = new BasicMLData(char_width * char_height);
        int isFieldNotEmpty;

        for(int row = 0; row < char_height; row++)
        {
            for(int col = 0; col < char_width; col++)
            {
                int index = (row* char_width) + col;
                char ch = image[row].charAt(col);
                if(ch=='O')
                    isFieldNotEmpty = 1;
                else
                    isFieldNotEmpty = -1;

                result.setData(index,isFieldNotEmpty);
            }
        }

        return result;
    }

    public static MLDataSet generateTraining()
    {
        MLDataSet result = new BasicMLDataSet();
        for(int i = 0; i < digits.length; i++)
        {
            BasicMLData ideal = new BasicMLData(digits.length);

            // setup input
            MLData input = image2data(digits[i]);

            // setup ideal
            for(int j = 0; j < digits.length; j++)
            {
                if( j==i )
                    ideal.setData(j,1);
                else
                    ideal.setData(j,-1);
            }

            // add training element
            result.add(input,ideal);
        }
        return result;
    }

    public void execute(){
        int inputNeurons = char_width * char_height;
        int outputNeurons = digits.length;

        ADALINEPattern pattern = new ADALINEPattern();
        pattern.setInputNeurons(inputNeurons);
        pattern.setOutputNeurons(outputNeurons);
        BasicNetwork network = (BasicNetwork)pattern.generate();

        // train it
        MLDataSet training = generateTraining();
        MLTrain train = new TrainAdaline(network,training,0.01);

        int epoch = 0;
        do {
            train.iteration();
            //       System.out.println(train.getError());
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while(train.getError() > 0.01);

        //
        System.out.println("Error:" + network.calculateError(training));

        // test it
        for(int i = 0; i < digitstest.length; i++)
        {
            int output = network.winner(image2data(digitstest[i]));

            for(int j = 0; j < char_height; j++)
            {
                if( j == char_height - 1 )
                    System.out.println(digitstest[i][j] + " -> " + output);
                else
                    System.out.println(digitstest[i][j]);

            }

            System.out.println();
        }
    }

}

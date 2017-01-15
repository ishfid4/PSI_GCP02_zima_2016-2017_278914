package pl.ishfid.psi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

/**
 * Created by ishfi on 02.12.2016.
 */
public class DataManager {
    public int learningRecordCount;
    public int validationRecordCount;
    public int inputCount;
    public int outputCount;
    public ArrayList<ArrayList<Double>> inputDataSet;
    public ArrayList<ArrayList<Double>> outputDataSet;
    public ArrayList<ArrayList<Double>> validationInputDataSet;
    public ArrayList<ArrayList<Double>> validationOutputDataSet;

    public DataManager() {
        this.inputDataSet = new ArrayList<>(1200);
        this.outputDataSet = new ArrayList<>(1200);
        this.validationInputDataSet = new ArrayList<>(393);
        this.validationOutputDataSet = new ArrayList<>(393);
        this.learningRecordCount = 1200;//558
        this.validationRecordCount = 393;//239
        this.inputCount = 256;
        this.outputCount = 10;
    }

    public void importData(String path) throws IOException {
        FileInputStream inputStream = null;
        Scanner scanner;

        try {
            inputStream = new FileInputStream(path);
            scanner = new Scanner(inputStream);
            scanner.useLocale(Locale.ENGLISH);

            for (int i = 0; i < learningRecordCount; ++i){
                inputDataSet.add(new ArrayList<>());
                outputDataSet.add(new ArrayList<>());

                for (int j = 0; j < inputCount; ++j){
                    inputDataSet.get(i).add(scanner.nextDouble());
                }
                for (int j = 0; j < outputCount; ++j){
                    outputDataSet.get(i).add(scanner.nextDouble());
                }
            }

            for (int i = 0; i < validationRecordCount; ++i){
                validationInputDataSet.add(new ArrayList<>());
                validationOutputDataSet.add(new ArrayList<>());

                for (int j = 0; j < inputCount; ++j){
                    validationInputDataSet.get(i).add(scanner.nextDouble());
                }
                for (int j = 0; j < outputCount; ++j){
                    validationOutputDataSet.get(i).add(scanner.nextDouble());
                }
            }
        }finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}

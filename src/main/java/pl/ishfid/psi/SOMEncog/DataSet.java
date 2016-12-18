package pl.ishfid.psi.SOMEncog;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by ishfi on 18.12.2016.
 */
public class DataSet {
    public int learningRecordCount;
    public int validationRecordCount;
    public int digitsCount;
    public int inputCount;
    public int outputCount;
    public double inputDataSet[][][];
    public double validationDataSet[][][];
    public double validationOutputDataSet[][][];

    public DataSet() {
        this.inputDataSet = new double[10][20][256];
        this.validationDataSet = new double[10][3][256];
        this.validationOutputDataSet = new double[10][3][10];
        this.digitsCount = 10;
        this.learningRecordCount = 20;
        this.validationRecordCount = 3;
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

            for (int k = 0; k < digitsCount; ++k){
                for (int i = 0; i < learningRecordCount; ++i){
                    for (int j = 0; j < inputCount; ++j){
                        inputDataSet[k][i][j] = scanner.nextDouble();
                    }
                    for (int j = 0; j < outputCount; ++j){}
                }
            }


            for (int k = 0; k < digitsCount; ++k) {
                for (int i = 0; i < validationRecordCount; ++i) {
                    for (int j = 0; j < inputCount; ++j) {
                        validationDataSet[k][i][j] = scanner.nextDouble();
                    }
                    for (int j = 0; j < outputCount; ++j) {
                        validationOutputDataSet[k][i][j] = scanner.nextDouble();
                    }
                }
            }
        }finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}

package pl.ishfid.psi.digitRecognitionEncog;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by ishfi on 27.11.2016.
 */
public class Data {
//    private final static int char_width = 5;
//    private final static int char_height = 7;
//
//    private static String[][] digits = {
//            { " OOO ",
//            "O   O",
//            "O   O",
//            "O   O",
//            "O   O",
//            "O   O",
//            " OOO "  },
//
//            { "  O  ",
//            " OO  ",
//            "O O  ",
//            "  O  ",
//            "  O  ",
//            "  O  ",
//            "  O  "  },
//
//            { " OOO ",
//            "O   O",
//            "    O",
//            "   O ",
//            "  O  ",
//            " O   ",
//            "OOOOO"  },
//
//            { " OOO ",
//            "O   O",
//            "    O",
//            " OOO ",
//            "    O",
//            "O   O",
//            " OOO "  },
//
//            { "   O ",
//            "  OO ",
//            " O O ",
//            "O  O ",
//            "OOOOO",
//            "   O ",
//            "   O "  },
//
//            { "OOOOO",
//            "O    ",
//            "O    ",
//            "OOOO ",
//            "    O",
//            "O   O",
//            " OOO "  },
//
//            { " OOO ",
//            "O   O",
//            "O    ",
//            "OOOO ",
//            "O   O",
//            "O   O",
//            " OOO "  },
//
//            { "OOOOO",
//            "    O",
//            "    O",
//            "   O ",
//            "  O  ",
//            " O   ",
//            "O    "  },
//
//            { " OOO ",
//            "O   O",
//            "O   O",
//            " OOO ",
//            "O   O",
//            "O   O",
//            " OOO "  },
//
//            { " OOO ",
//            "O   O",
//            "O   O",
//            " OOOO",
//            "    O",
//            "O   O",
//            " OOO "  }};
//
//
//    private static String[][] digitstest = {
//            { " OOO ",
//              "O   O",
//              "O   O",
//              "O   O",
//              "O   O",
//              "O   O",
//              "0OOO "  },
//
//            { "  O  ",
//              " OO  ",
//              "  O  ",
//              "  O  ",
//              "  O  ",
//              "  O  ",
//              "  O  "  },
//
//            { " OOO ",
//              "    O",
//              "    O",
//              "   O ",
//              "  O  ",
//              " O   ",
//              "OOOO0"  },
//
//            { " OOO ",
//              "0   O",
//              "    O",
//              "  OO ",
//              "    O",
//              "0   O",
//              " OOO "  },
//
//            { "  OOO",
//              "    O",
//              "    O",
//              "   O ",
//              "  O  ",
//              " O   ",
//              "O    "  },
//
//            { "0OOO0",
//              "O   O",
//              "O   O",
//              " OOO ",
//              "O   O",
//              "O   O",
//              " OOO "  },
//
//            { " OOO ",
//              "O   O",
//              "O   O",
//              " OOOO",
//              "    O",
//              "    O",
//              "  OO "  }
//    };

    private int learningRecordCount;
    private int validationRecordCount;
    private int inputCount;
    private int outputCount;
    private double inputDataSet[][];
    private double inputVerificationSet[][];
    private double validationDataSet[][];
    private double validationOutputDataSet[][];

    public Data() {
        this.inputDataSet = new double[200][256];
        this.inputVerificationSet = new double[200][10];
        this.validationDataSet = new double[30][256];
        this.validationOutputDataSet = new double[30][10];
        this.learningRecordCount = 200;
        this.validationRecordCount = 30;
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
                for (int j = 0; j < inputCount; ++j){
                    inputDataSet[i][j] = scanner.nextDouble();
                }
                for (int j = 0; j < outputCount; ++j){
                    inputVerificationSet[i][j] = scanner.nextDouble();
                }
            }

            for (int i = 0; i < validationRecordCount; ++i) {
                for (int j = 0; j < inputCount; ++j) {
                    validationDataSet[i][j] = scanner.nextDouble();
                }
                for (int j = 0; j < outputCount; ++j) {
                    validationOutputDataSet[i][j] = scanner.nextDouble();
                }
            }
        }finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public int getInputCount() {
        return inputCount;
    }

    double[][] getInputDataSet() {
        return inputDataSet;
    }

    double[][] getInputVerificationSet() {
        return inputVerificationSet;
    }

    double[][] getValidationDataSet() {
        return validationDataSet;
    }

    public double[][] getValidationOutputDataSet() {
        return validationOutputDataSet;
    }

    //    public static int getChar_width() {
//        return char_width;
//    }
//
//    public static int getChar_height() {
//        return char_height;
//    }
//
//    public static String[][] getDigits() {
//        return digits;
//    }
//
//    public static String[][] getDigitstest() {
//        return digitstest;
//    }
}

package pl.ishfid.psi.digitRecognitionEncog;

/**
 * Created by ishfi on 27.11.2016.
 */
public class Data {
    private final static int char_width = 5;
    private final static int char_height = 7;

    private static String[][] digits = {
            { " OOO ",
            "O   O",
            "O   O",
            "O   O",
            "O   O",
            "O   O",
            " OOO "  },

            { "  O  ",
            " OO  ",
            "O O  ",
            "  O  ",
            "  O  ",
            "  O  ",
            "  O  "  },

            { " OOO ",
            "O   O",
            "    O",
            "   O ",
            "  O  ",
            " O   ",
            "OOOOO"  },

            { " OOO ",
            "O   O",
            "    O",
            " OOO ",
            "    O",
            "O   O",
            " OOO "  },

            { "   O ",
            "  OO ",
            " O O ",
            "O  O ",
            "OOOOO",
            "   O ",
            "   O "  },

            { "OOOOO",
            "O    ",
            "O    ",
            "OOOO ",
            "    O",
            "O   O",
            " OOO "  },

            { " OOO ",
            "O   O",
            "O    ",
            "OOOO ",
            "O   O",
            "O   O",
            " OOO "  },

            { "OOOOO",
            "    O",
            "    O",
            "   O ",
            "  O  ",
            " O   ",
            "O    "  },

            { " OOO ",
            "O   O",
            "O   O",
            " OOO ",
            "O   O",
            "O   O",
            " OOO "  },

            { " OOO ",
            "O   O",
            "O   O",
            " OOOO",
            "    O",
            "O   O",
            " OOO "  }};


    private static String[][] digitstest = {
            { " OOO ",
              "O   O",
              "O   O",
              "O   O",
              "O   O",
              "O   O",
              "0OOO "  },

            { "  O  ",
              " OO  ",
              "  O  ",
              "  O  ",
              "  O  ",
              "  O  ",
              "  O  "  },

            { " OOO ",
              "    O",
              "    O",
              "   O ",
              "  O  ",
              " O   ",
              "OOOO0"  },

            { " OOO ",
              "0   O",
              "    O",
              "  OO ",
              "    O",
              "0   O",
              " OOO "  },

            { "  OOO",
              "    O",
              "    O",
              "   O ",
              "  O  ",
              " O   ",
              "O    "  },

            { "0OOO0",
              "O   O",
              "O   O",
              " OOO ",
              "O   O",
              "O   O",
              " OOO "  },

            { " OOO ",
              "O   O",
              "O   O",
              " OOOO",
              "    O",
              "    O",
              "  OO "  }
    };

    public Data() {
    }

    public static int getChar_width() {
        return char_width;
    }

    public static int getChar_height() {
        return char_height;
    }

    public static String[][] getDigits() {
        return digits;
    }

    public static String[][] getDigitstest() {
        return digitstest;
    }
}

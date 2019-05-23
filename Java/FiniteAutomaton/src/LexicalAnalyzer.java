package finiteautomaton;

import java.util.LinkedList;

/**
 *
 * @author Deco
 */
public class LexicalAnalyzer {

    private String setence;
    private final int[][] transitionTab
            = {{2, 10, 8, 7, 10, 10, 11, 12},
            {10, 10, 8, 7, 10, 10, 11, 12},
            {10, 3, 10, 10, 10, 10, 11, 12},
            {5, 10, 10, 10, 10, 10, 11, 12},
            {10, 10, 10, 10, 6, 10, 11, 12},
            {10, 0, 10, 10, 10, 10, 11, 12},
            {10, 10, 10, 7, 10, 10, 11, 12},
            {10, 10, 10, 10, 9, 10, 11, 12},
            {10, 10, 1, 4, 10, 10, 11, 12},
            {10, 10, 10, 4, 10, 10, 11, 12},
            {10, 10, 10, 10, 10, 10, 10, 10},
            {11, 11, 11, 11, 11, 11, 11, 11},
            {12, 12, 12, 12, 12, 12, 12, 12}};
    private final int[] finalStatus = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 2, 3};
    private final char[] alphabet = {'a', 'b', 'c', 'd', 'e'};
    private final char[] operator = {'+', '-', '/', '*'};
    private final char[] control = {'\n', '\t', ' '};
    private LinkedList<Token> tokens = new LinkedList<>();

    public LexicalAnalyzer(String setence) {
        this.setence = setence;
        gernericAlgorithm();
    }

    public LinkedList<Token> getTokens() {
        return tokens;
    }

    public void addToken(LinkedList<Token> tokens) {
        this.tokens = tokens;
    }

    private boolean isOperator(char symbol) {
        for (char c : operator) {
            if (c == symbol) {
                return true;
            }
        }
        return false;
    }

    private boolean isAlphabet(char symbol) {
        for (char c : alphabet) {
            if (c == symbol) {
                return true;
            }
        }
        return false;
    }

    private int getIndexAlphabet(char symbol) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == symbol) {
                return i;
            }
        }
        return 0;
    }

    private int getIndexSymbol(char symbol) {
        if (isAlphabet(symbol)) {
            return getIndexAlphabet(symbol);
        }
        if (isOperator(symbol)) {
            return alphabet.length + 1;
        }
        if (!isAlphabet(symbol)) {
            return alphabet.length + 2;
        }
        return alphabet.length;
    }

    private void adjustControl() {
        for (char c : control) {
            setence = setence.replace(c, '$');
        }
        for (char c : operator) {
            setence = setence.replace(String.valueOf(c), "$" + String.valueOf(c) + "$");
        }
        setence = setence.concat("$");
    }

    private void gernericAlgorithm() {
        adjustControl();
        int status = 0;
        String value = "";
        for (char symbol : setence.toCharArray()) {
            if (symbol != '$') {
                status = transitionTab[status][getIndexSymbol(symbol)];
                value += String.valueOf(symbol);
            } else {
                createToken(value, status);
                value = "";
                status = 0;
            }
        }
    }

    private void createToken(String setence, int status) {
        if (!setence.equals("")) {
            for (FinalStatus fs : FinalStatus.values()) {
                if (fs.getStatus() == finalStatus[status]) {
                    tokens.add(new Token(setence, fs));
                    return;
                }
            }
        }
    }
}

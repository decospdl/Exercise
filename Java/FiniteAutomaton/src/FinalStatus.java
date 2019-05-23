package finiteautomaton;

/**
 *
 * @author Deco
 */
public enum FinalStatus {
    ERROR_SETENCE(0, "ERRO: sentença inválida"),
    VALID_SETENCE(1, "sentença válida"),
    OPERATOR(2, "operador aritmético"),
    ERROR_SYMBOL(3, "ERRO: símbolo(s) inválido(s)");

    private int status;
    private String value;

    private FinalStatus(int status, String value) {
        this.value = value;
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public int getStatus() {
        return status;
    }
}

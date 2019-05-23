package finiteautomaton;

/**
 *
 * @author Administrator
 */
public class Token {

    private String value;
    private FinalStatus finalStatus;

    public Token(String value) {
        setValue(value);
    }

    public Token(String value, FinalStatus finalStatus) {
        setValue(value);
        setFinalStatus(finalStatus);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return finalStatus.getValue();
    }

    public void setFinalStatus(FinalStatus finalStatus) {
        this.finalStatus = finalStatus;
    }

}

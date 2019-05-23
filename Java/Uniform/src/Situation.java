package uniform;

/**
 * Enum para identificação da situação que se encontra o histórico da retirada
 * do uniforme.
 *
 * @author d3c0de <decospdl@gmail.com>
 */
public enum Situation {
    CLOSED("Encerrado"),
    CANCELED("Cancelado"),
    IN_PROGRESS("Em Andamento");

    private String description;

    private Situation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Retorna a situação conforme a descrição passada por parâmetro, em caso de
     * falha retorna null.
     *
     * @param description
     * @return a situação conforme a descrição.
     */
    public static Situation getSituacao(String description) {
        for (Situation sit : Situation.values()) {
            if (sit.getDescription().equals(description)) {
                return sit;
            }
        }
        return null;
    }
}

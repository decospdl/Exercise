package uniform;

/**
 * Enum para definição do Tipo de Tamanho, com o detalhamento de cada tipo
 */
public enum SizeType {
    NUMBER("NUMERO", new String[]{"34", "36", "38", "40", "42", "44", "48", "50", "52", "54", "56", "58", "58", "60"}),
    LETTER("LETRA", new String[]{"XPP", "XP", "PP", "P", "M", "G", "GG", "XG", "XGG", "XXGG", "XXXGG"});

    private String description;
    
    private String[] size;

    private SizeType(String description, String[] size) {
        this.description = description;
        this.size = size;
    }

    public String[] getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    public static SizeType getSizeType(String description) {
        for (SizeType sizeType : SizeType.values()) {
            if (sizeType.getDescription().equals(description)) {
                return sizeType;
            }
        }
        return null;
    }
    
    public static Object[] getAllDescription(){
        Object[] values = new Object[SizeType.values().length];
        for(int i = 0; i < SizeType.values().length; i++){
            values[i] = SizeType.values()[i].getDescription();
        }
        return values;
    }
}

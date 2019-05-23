package br.sc.reading_xml;

import java.math.BigDecimal;

public class Purchase {
    private String food;
    private int qtd;
    private BigDecimal value;
    private int calories;

    public int getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        int value = Integer.parseInt(calories) * qtd;
        this.calories = value;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getQtd() {
        return String.valueOf(qtd);
    }

    public void setQtd(String qtd) {
        this.qtd = Integer.parseInt(qtd);
    }

    public String getValue() {
        return value.toString();
    }

    public void setValue(String price) {
        price = price.replace("$","");
        BigDecimal value = new BigDecimal(price).multiply(new BigDecimal(qtd));
        this.value = value;
    }
}

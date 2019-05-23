package uniform;

import java.math.BigDecimal;

/**
 * Classe para controle de nota fiscais do Uniforme.
 * 
 * @author d3c0de <decospdl@gmail.com>
 */
public class Invoice {
     
    private Integer id;
    private String numberInvoice;  
    private String referentMonth;
    private BigDecimal value;

    public Integer getId() {
        return id;
    }

    public String getNumberInvoice() {
        return numberInvoice;
    }

    public String getReferentMonth() {
        return referentMonth;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumberInvoice(String numberInvoice) {
        this.numberInvoice = numberInvoice;
    }

    public void setReferentMonth(String referentMonth) {
        this.referentMonth = referentMonth;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }  
}
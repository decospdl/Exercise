package uniform;

import java.math.BigDecimal;

/**
 * Classe para controle das peças que estão cadastradas na nota fiscais.
 * @author d3c0de <decospdl@gmail.com>
 */
public class InvoiceUniform {
    private  Integer id;
    private Uniform uniform;
    private Invoice invoice;
    private String size;
    private int quantity;
    private BigDecimal value;

    public Integer getId() {
        return id;
    }

    public Uniform getUniform() {
        return uniform;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUniform(Uniform uniform) {
        this.uniform = uniform;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }    

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}

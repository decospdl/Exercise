package uniform;

import br.sc.d3c0de.date.Date;
import java.math.BigDecimal;
import java.util.Arrays;


/**
 * Classe para Cadastro de peças de uniforme.
 * @author André Spindola
 */
public class Uniform {
    
    private Integer id;
    private String descreption;
    private SizeType sizeType;
    private Date startValidity;
    private Date endValidity;
    private BigDecimal value;

    public Integer getId() {
        return id;
    }

    public String getDescreption() {
        return descreption;
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public Date getStartValidity() {
        return startValidity;
    }

    public Date getEndValidity() {
        return endValidity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public void setStartValidity(Date startValidity) {
        this.startValidity = startValidity;
    }

    public void setEndValidity(Date endValidity) {
        this.endValidity = endValidity;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setSizeType(SizeType sizeType) {
        this.sizeType = sizeType;
    }
}
    
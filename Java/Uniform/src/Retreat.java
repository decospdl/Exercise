package uniform;

import java.util.LinkedList;

/**
 * Classe para controle da retirada dos uniformes feito.
 *
 * @author d3c0de <decospdl@gmail.com>
 */
public class Retreat {

    private Integer id;
    private Integer quantity;
    private Uniform uniform;
    private String size;
    private Historic historic;

    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Uniform getUniform() {
        return uniform;
    }

    public String getSize() {
        return size;
    }

    public Historic getHistoric() {
        return historic;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUniform(Uniform uniform) {
        this.uniform = uniform;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setHistoric(Historic historic) {
        this.historic = historic;
    }
    
    public static int sumAllQuantity(LinkedList<Retreat> retreats){
        int sum = 0;       
        for(Retreat retreat : retreats){
           sum += retreat.getQuantity();
        }
        return sum;
    }
}

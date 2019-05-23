package uniform;

import br.sc.d3c0de.date.Date;
import java.util.LinkedList;

/**
 * Classe para controle da movimentação das retiradas de uniforme.
 *
 * @author d3c0de <decospdl@gmail.com>
 */
public class Historic {

    private Integer id;
    private Situation situation;
    private String enrollmentEmployee;
    private String nameEmployee;
    private Invoice invoice;
    private String note;

    private Date retreatedDate;
    private Date canceledDate;
    private Date closedDate;
    private Date changedDate;

    private String retreadedBy;
    private String canceledBy;
    private String closedBy;
    private String changedBy;

    public Integer getId() {
        return id;
    }

    public Situation getSituation() {
        return situation;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public String getNote() {
        return note;
    }

    public Date getRetreatedDate() {
        return retreatedDate;
    }

    public Date getCanceledDate() {
        return canceledDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public Date getChangedDate() {
        return changedDate;
    }

    public String getRetreadedBy() {
        return retreadedBy;
    }

    public String getCanceledBy() {
        return canceledBy;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setRetreatedDate(Date retreatedDate) {
        this.retreatedDate = retreatedDate;
    }

    public void setCanceledDate(Date canceledDate) {
        this.canceledDate = canceledDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public void setChangedDate(Date changedDate) {
        this.changedDate = changedDate;
    }

    public void setRetreadedBy(String retreadedBy) {
        this.retreadedBy = retreadedBy;
    }

    public void setCanceledBy(String canceledBy) {
        this.canceledBy = canceledBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public String getEnrollmentEmployee() {
        return enrollmentEmployee;
    }

    public void setEnrollmentEmployee(String enrollmentEmployee) {
        this.enrollmentEmployee = enrollmentEmployee;
    }

    public static boolean isSituationDiferentCanceled(LinkedList<Historic> historics) {
        for (Historic historic : historics) {
            if (!historic.getSituation().equals(Situation.CANCELED)) {
                return false;
            }
        }
        return true;
    }
}

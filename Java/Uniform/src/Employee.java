package uniform;

import br.sc.d3c0de.date.Date;

/**
 * Classe mockup, utilizada para teste do programa de uniforme.
 *
 * @author d3c0de <decospdl@gmail.com>
 */
public class Employee {

    private Integer id;
    private String name;
    private String function;
    private String department;
    private Date hireDate;
    private String company;
    private byte [] photo;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFunction() {
        return function;
    }

    public String getDepartment() {
        return department;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public String getCompany() {
        return company;
    }

    public byte [] getPathPhoto() {
        return photo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPathPhoto(byte [] pathPhoto) {
        this.photo = pathPhoto;
    }
}

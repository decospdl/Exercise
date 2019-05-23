package uniform.reports;

import br.sc.d3c0de.file.File;
import br.sc.d3c0de.reports.Reports;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.ConsultaSql;

/**
 *
 * @author Andre
 */
public class ReportUniform {

    private String historicId, company, name, enrollment, function, hireDate,
            departament, note, pathLogo, text1_P1, text2_P1, textP2, company1, company2;

    private Map paramtersP1, paramtersP2;

    public ReportUniform(String historicId, String company) {
        this.historicId = historicId;
        this.company = company;
        paramtersP1 = new HashMap();
        paramtersP2 = new HashMap();
    }

    private void selectCompany() {
        switch (company) {
            case "Estrela":
                pathLogo = File.PATH_PROJECT + "imagens\\Logo_estrela.gif";
                company1 = "Transporte Coletivo Estrela Ltda.";
                company2 = company1;
                break;
            case "Insular":
                pathLogo = File.PATH_PROJECT + "imagens\\logo_insular_wide.gif";
                company1 = "Insular Transportes Coletivos Ltda.";
                company2 = company1;
                break;
            default:
                pathLogo = File.PATH_PROJECT + "\\imagens\\logo_consorcio_fenix.jpg";
                company1 = "Consórcio Fênix Ltda.";
                company2 = (Integer.parseInt(enrollment) > 10000)
                        ? "Transporte Coletivo Estrela Ltda." : "Insular Transportes Coletivos Ltda.";
                break;
        }
    }

    private void initParamtersValues() {
        selectCompany();
        text1_P1 = "A " + company1 + " autoriza o fornecimento"
                + " dos seguintes uniformes profissionais ao seu funcionário abaixo"
                + " descriminado, sendo proibido a personalização do uniforme, mantendo o"
                + " padrão definido pela empresa.";

        text2_P1 = "Esta compra deverá ser faturada para " + company2 + ".";

        textP2 = "Eu " + name + " matriculado com o número " + enrollment + " na função de "
                + function + " desde " + hireDate + " funcionário desta empresa, declaro haver recebido"
                + " gratuitamente o uniforme exigido pela empresa (Calça, bermuda, camisa pólo, etc)"
                + " devidamente confeccionada, conforme estabelecido pela convenção coletiva de"
                + " trabalho, sendo proibido a personalização do uniforme, mantendo o padrão definido"
                + " pela empresa.";
    }

    private void createParamtersP1() {
        paramtersP1.put("MATRICULA", enrollment);
        paramtersP1.put("NOME", name);
        paramtersP1.put("LOCAL", departament);
        paramtersP1.put("CARGO", function);
        paramtersP1.put("OBSERVACAO", note);
        paramtersP1.put("TEXTO1_P1", text1_P1);
        paramtersP1.put("TEXTO2_P1", text2_P1);
        paramtersP1.put("LOGO", pathLogo);
    }

    private void createParamtersP2() {
        paramtersP2.put("TEXTO_P2", textP2);
        paramtersP2.put("LOGO", pathLogo);
        paramtersP2.put("OBSERVACAO", note);
    }

    private ResultSet getResultSet() {
        try {
            String query = "SELECT ur.quantidade, up.descricao, ur.tamanho"
                    + " FROM db_mun.unif_retirado ur, db_mun.unif_peca up"
                    + " WHERE fk_historico_uniforme_id =" + historicId
                    + " AND ur.fk_peca_id = up.id";
            return ConsultaSql.getInstancia().getConsulta(query);
        } catch (SQLException ex) {
            Logger.getLogger(ReportUniform.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void show(){
        Reports reports = new Reports();
        initParamtersValues();
        createParamtersP1();
        createParamtersP2();
        reports.addJasperPrint("./relatorios/uniforme/uniform_request_p1.jrxml", paramtersP1, getResultSet());
        reports.addJasperPrint("./relatorios/uniforme/uniform_request_p2.jrxml", paramtersP2, getResultSet());
        reports.showReports();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

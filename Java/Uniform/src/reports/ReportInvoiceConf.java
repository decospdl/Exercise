package uniform.reports;

import br.sc.d3c0de.file.File;
import br.sc.d3c0de.reports.Reports;
import java.sql.Connection;
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
public class ReportInvoiceConf {

    private String invoiceId;
    private Connection connection;

    private Map paramters;

    public ReportInvoiceConf(String invoiceId) {
        try {
            this.connection = ConsultaSql.getInstancia().conectar();
            this.invoiceId = invoiceId;
            paramters = new HashMap();
        } catch (SQLException ex) {
            Logger.getLogger(ReportInvoiceConf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createParamters() {
        paramters.put("CONNECTION_SYSTEM", connection);
        paramters.put("CONNECTION_NOTA", connection);
        paramters.put("SUBREPORT_DIR", File.PATH_PROJECT + "relatorios\\uniforme\\");
        paramters.put("NOTA_ID", invoiceId);
    }

    public void show() {
        try {
            Reports reports = new Reports();
            createParamters();
            reports.addJasperPrint("./relatorios/uniforme/check_uniform.jrxml", paramters);
            reports.showReports();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportInvoiceConf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

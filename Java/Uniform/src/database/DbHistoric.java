package uniform.database;

import br.sc.d3c0de.PersonTab;
import br.sc.d3c0de.date.Date;
import br.sc.d3c0de.formatter.DbFormatter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import uniform.Historic;
import uniform.Situation;
import util.ConsultaSql;

/**
 * Classe abstrata para controle do banco de dados da classe histórico.
 *
 * @author d3c0de <decospdl@gmail.com>
 */
public abstract class DbHistoric {

    public static final String SCHEMA = "db_mun.unif_historico";
    public static final String ALL = "SELECT * FROM " + SCHEMA;
    public static final String ID = ALL + " WHERE id = ";
    public static final String EMPLOYEE = ALL + " WHERE fk_funcionario_id = ";
    public static final String CONF_INVOICE = ALL + " WHERE situacao = 'Em Andamento' OR data_retirada > '"
            + DbFormatter.toDateTimeDb(new Date().addDay(-30))+ "' AND situacao = 'Encerrado'";

    public static LinkedList<Historic> selectFromDb(String query) {
        LinkedList<Historic> historics = new LinkedList<>();
        try {
            ResultSet rs = ConsultaSql.getInstancia().getConsulta(query);
            while (rs.next()) {
                Historic historic = new Historic();
                historic.setNameEmployee(rs.getString("name_employee"));
                historic.setCanceledBy(rs.getString("cancelado_por"));
                historic.setCanceledDate(DbFormatter.dateTimeDbTo(rs.getString("data_cancelado")));
                historic.setChangedBy(rs.getString("alterado_por"));
                historic.setChangedDate(DbFormatter.dateTimeDbTo(rs.getString("data_alterado")));
                historic.setClosedBy(rs.getString("encerrado_por"));
                historic.setClosedDate(DbFormatter.dateTimeDbTo(rs.getString("data_encerrado")));
                historic.setEnrollmentEmployee(rs.getString("fk_funcionario_id"));
                historic.setId(rs.getInt("id"));
                historic.setInvoice(DbInvoice.getInvoiceBy(DbInvoice.ID, rs.getString("nota_fiscal_id")));
                historic.setNote(rs.getString("observacao"));
                historic.setRetreadedBy(rs.getString("entregue_por"));
                historic.setRetreatedDate(DbFormatter.dateTimeDbTo(rs.getString("data_retirada")));
                historic.setSituation(Situation.getSituacao(rs.getString("situacao")));
                historics.add(historic);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersonTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historics;
    }

    public static void updadteHistoricChangeDb(Historic historic) {
        try {
            String query = "UPDATE " + SCHEMA + " SET situacao = '" + historic.getSituation().getDescription()
                    + "', data_alterado = '" + DbFormatter.toDateTimeDb(historic.getChangedDate()) + "', alterado_por = '"
                    + historic.getChangedBy() + "', nota_fiscal_id = null WHERE id = " + historic.getId();
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbHistoric.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateHistoricCloseDb(Historic historic) {
        try {
            String query = "UPDATE " + SCHEMA + " SET situacao = '" + historic.getSituation().getDescription()
                    + "', data_encerrado = '" + DbFormatter.toDateTimeDb(historic.getClosedDate()) + "', encerrado_por = '"
                    + historic.getClosedBy() + "', nota_fiscal_id = '" + historic.getInvoice().getId()
                    + "' WHERE id = " + historic.getId();
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbHistoric.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateHistoricDb(String id, String column, String value) {
        try {
            String query = "UPDATE " + SCHEMA + " SET " + column + " = '" + value + "' WHERE id = '" + id + "'";
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbHistoric.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertHistoricDb(Historic historic) {
        try {
            String query = "INSERT INTO " + SCHEMA + "(situacao, name_employee, data_retirada, entregue_por, observacao, fk_funcionario_id, id_entidade) "
                    + "VALUES ('" + historic.getSituation().getDescription() + "','" + historic.getNameEmployee() + "','"
                    + DbFormatter.toDateTimeDb(historic.getRetreatedDate()) + "','" + historic.getRetreadedBy() + "','" 
                    + historic.getNote() + "','" + historic.getEnrollmentEmployee()
                    + "', '"+ ((Integer.parseInt(historic.getEnrollmentEmployee()) > 10000) ? 2 : 5) + "')";
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbHistoric.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Historic getHistoricBy(String query, String value) {
        LinkedList<Historic> historics = DbHistoric.selectFromDb(query + "'" + value + "'");
        if (historics.isEmpty()) {
            return null;
        } else {
            return historics.getFirst();
        }
    }

    public static LinkedList<Historic> getListHistoricBy(String query, String value) {
        LinkedList<Historic> historics = DbHistoric.selectFromDb(query + "'" + value + "' ORDER BY data_retirada DESC");
        if (historics.isEmpty()) {
            return null;
        } else {
            return historics;
        }
    }
    
        public static LinkedList<Historic> getListHistoricBy(String query) {
       System.out.println(query);
        LinkedList<Historic> historics = DbHistoric.selectFromDb(query + " ORDER BY data_retirada DESC");
        if (historics.isEmpty()) {
            return null;
        } else {
            return historics;
        }
    }
}

package uniform.database;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import uniform.InvoiceUniform;
import util.ConsultaSql;

/**
 *
 * @author Andre
 */
public abstract class DbInvoiceUniform {

    public static final String SCHEMA = "db_mun.unif_peca_nota_fiscal";
    public static final String ALL = "SELECT * FROM " + SCHEMA;
    public static final String INVOICE = ALL + " WHERE fk_nota_fiscal = ";

    public static LinkedList<InvoiceUniform> selectFromDb(String query) {
        LinkedList<InvoiceUniform> iUniforms = new LinkedList<>();
        try {
            ResultSet rs = ConsultaSql.getInstancia().getConsulta(query);
            while (rs.next()) {
                InvoiceUniform iUniform = new InvoiceUniform();
                iUniform.setId(rs.getInt("id"));
                iUniform.setQuantity(rs.getInt("qtde"));
                iUniform.setSize(rs.getString("tamanho"));
                iUniform.setUniform(DbUniform.getUniformBy(DbUniform.ID, rs.getString("fk_peca_id")));
                iUniform.setValue(new BigDecimal(rs.getString("valor")));
                iUniform.setInvoice(DbInvoice.getInvoiceBy(DbInvoice.ID, rs.getString("fk_nota_fiscal")));
                iUniforms.add(iUniform);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbInvoiceUniform.class.getName()).log(Level.SEVERE, null, ex);
        }
        return iUniforms;
    }
    
    public static void insertInvoiceUniformDb(InvoiceUniform invoiceUniform) {
        try {
            String query = "INSERT INTO " + SCHEMA + " (tamanho, qtde, valor, fk_nota_fiscal, fk_peca_id) VALUES ('"
                    + invoiceUniform.getSize() + "','" + invoiceUniform.getQuantity() + "','"
                    + invoiceUniform.getValue().toString() + "','" + invoiceUniform.getInvoice().getId()
                    + "','" + invoiceUniform.getUniform().getId() + "')";
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbInvoiceUniform.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteInvoiceUniformDb(String id) {
        try {
            String query = "DELETE FROM " + SCHEMA + " WHERE id = " + id;
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbInvoiceUniform.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteInvoiceUniformDbByInvoice(String id) {
        try {
            String query = "DELETE FROM " + SCHEMA + " WHERE fk_nota_fiscal = " + id;
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbInvoiceUniform.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package uniform.database;

import br.sc.d3c0de.PersonTab;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import uniform.Invoice;
import util.ConsultaSql;

/**
 *
 * @author Andre
 */
public abstract class DbInvoice {
    
    public static final String SCHEMA = "db_mun.unif_nota_fiscal";
    public static final String ALL = "SELECT * FROM " + SCHEMA;
    public static final String ID = ALL + " WHERE id = ";
    public static final String DESCRIPTION = ALL + " WHERE nota_fiscal = '";

    public static LinkedList<Invoice> selectFromDb(String query) {
        LinkedList<Invoice> invoices = new LinkedList<>();
        try {
            ResultSet rs = ConsultaSql.getInstancia().getConsulta(query);
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setNumberInvoice(rs.getString("nota_fiscal"));
                invoice.setReferentMonth(rs.getString("mes_referente"));
                invoice.setValue((rs.getString("valor") == null) ? null : new BigDecimal(rs.getString("valor")));
                invoices.add(invoice);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersonTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoices;
    }
    
    public static void deleteInvoiceDb(String id){
        try {
            String query = "DELETE FROM "+ SCHEMA +" WHERE id = "+ id;
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbInvoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void insertInvoiceDb(Invoice invoice){
        try {
            String query = "INSERT INTO "+ SCHEMA +" (nota_fiscal, mes_referente) VALUES ('"
                    + invoice.getNumberInvoice() + "','"+ invoice.getReferentMonth()+"')";
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbInvoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Invoice getInvoiceBy(String query, String value) {
        LinkedList<Invoice> invoices = DbInvoice.selectFromDb(query + value);
        if (invoices.isEmpty()) {
            return null;
        } else {
            return invoices.getFirst();
        }
    }
    
    public static Object[] getListInvoiceByName(){
        LinkedList<Invoice> invoices = DbInvoice.selectFromDb(ALL);
        Object[] values = new Object[invoices.size()];
        for(int i = 0; i < invoices.size(); i++){
            values[i] = invoices.get(i).getNumberInvoice();
        }
        return values;
    }
}

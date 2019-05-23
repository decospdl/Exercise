package uniform.database;

import br.sc.d3c0de.PersonTab;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import uniform.Retreat;
import util.ConsultaSql;

/**
 *
 * @author Andre
 */
public abstract class DbRetreat {

    public static final String SCHEMA = "db_mun.unif_retirado";
    public static final String ALL = "SELECT * FROM " + SCHEMA;
    public static final String HISTORIC = ALL + " WHERE fk_historico_uniforme_id = ";

    public static LinkedList<Retreat> selectFromDb(String query) {
        LinkedList<Retreat> retreats = new LinkedList<>();
        try {
            ResultSet rs = ConsultaSql.getInstancia().getConsulta(query);
            while (rs.next()) {
                Retreat retreat = new Retreat();
                retreat.setId(rs.getInt("id"));
                retreat.setQuantity(rs.getInt("quantidade"));
                retreat.setSize(rs.getString("tamanho"));
                retreat.setUniform(DbUniform.getUniformBy(DbUniform.ID, rs.getString("fk_peca_id")));
                retreat.setHistoric(DbHistoric.getHistoricBy(DbHistoric.ID, rs.getString("fk_historico_uniforme_id")));
                retreats.add(retreat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersonTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retreats;
    }

    public static void deleteRetreatDb(Retreat retreat) {
        try {
            String query = "DELETE FROM " + SCHEMA + " WHERE id = " + retreat.getId();
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbRetreat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertRetreatDb(Retreat retreat, String historicId) {
        try {
            String query = "INSERT INTO " + SCHEMA + " (tamanho, quantidade, fk_peca_id, fk_historico_uniforme_id) "
                    + "VALUES ('" + retreat.getSize() + "','" + retreat.getQuantity() + "','"
                    + retreat.getUniform().getId() + "','" + historicId + "')";
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbRetreat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertRetreatsDB(LinkedList<Retreat> retreats, int historicId) {
        for (Retreat retreat : retreats) {
            try {
                String query = "INSERT INTO " + SCHEMA + " (tamanho, quantidade, fk_peca_id, fk_historico_uniforme_id) "
                        + "VALUES ('" + retreat.getSize() + "','" + retreat.getQuantity() + "','"
                        + retreat.getUniform().getId() + "','" + historicId + "')";
                ConsultaSql.getInstancia().getUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(DbRetreat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static LinkedList<Retreat> getListRetreatBy(String query, String id) {
        LinkedList<Retreat> retreats = selectFromDb(query + id + " ORDER BY id DESC");
        if (retreats.isEmpty()) {
            return null;
        } else {
            return retreats;
        }
    }

    public static Retreat getRetreatBy(String query, int value) {
        LinkedList<Retreat> retreats = DbRetreat.selectFromDb(query + value);
        if (retreats.isEmpty()) {
            return null;
        } else {
            return retreats.getFirst();
        }
    }
}

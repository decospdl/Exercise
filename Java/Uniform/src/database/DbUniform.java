package uniform.database;

import br.sc.d3c0de.date.Date;
import br.sc.d3c0de.formatter.DbFormatter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import uniform.SizeType;
import uniform.Uniform;
import util.ConsultaSql;

/**
 *
 * @author Andre
 */
public abstract class DbUniform {

    public static final String SCHEMA = "db_mun.unif_peca";
    public static final String ALL = "SELECT * FROM " + SCHEMA;
    public static final String ACTUAL = ALL + " WHERE fim_vigencia IS NULL OR fim_vigencia < '"
            + DbFormatter.toDateDb(new Date()) + "' ORDER BY descricao";
    public static final String ID = ALL + " WHERE id = ";
    public static final String DESCRIPTION = ALL + " WHERE descricao = ";

    public static LinkedList<Uniform> selectFromDb(String query) {
        LinkedList<Uniform> uniforms = new LinkedList<>();
        try {
            ResultSet rs = ConsultaSql.getInstancia().getConsulta(query);
            while (rs.next()) {
                Uniform uniform = new Uniform();
                uniform.setDescreption(rs.getString("descricao"));
                uniform.setEndValidity(DbFormatter.dateTimeDbTo(rs.getString("fim_vigencia")));
                uniform.setId(rs.getInt("id"));
                uniform.setSizeType(SizeType.getSizeType(rs.getString("tipo_tamanho")));
                uniform.setStartValidity(DbFormatter.dateTimeDbTo(rs.getString("inicio_vigencia")));
                uniform.setValue(new BigDecimal(rs.getString("valor")));
                uniforms.add(uniform);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbUniform.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uniforms;
    }
    
    public static void insertUniformDb(Uniform uniform){
        try {
            String query = "INSERT INTO "+ SCHEMA +" (descricao, tipo_tamanho, valor, inicio_vigencia) "
                    + "VALUES ('"+ uniform.getDescreption() +"','"+ uniform.getSizeType().getDescription()
                    + "','"+ uniform.getValue().toString() + "','"
                    + DbFormatter.toDateTimeDb(uniform.getStartValidity())+"')";
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbUniform.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateUniformDb(String id, String column, String value) {
        try {
            String query = "UPDATE " + SCHEMA + " SET " + column + " = '" + value + "' WHERE id = '" + id + "'";
            ConsultaSql.getInstancia().getUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DbUniform.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Uniform getUniformBy(String query, String value) {
        LinkedList<Uniform> uniforms = DbUniform.selectFromDb(query + "'" + value + "'");
        if (uniforms.isEmpty()) {
            return new Uniform();
        } else {
            return uniforms.getFirst();
        }
    }

    public static Object[] getUniformeNames(String query) {
        LinkedList<Uniform> uniforms = DbUniform.selectFromDb(query);
        Object[] values = new Object[uniforms.size()];
        for (int i = 0; i < uniforms.size(); i++) {
            values[i] = uniforms.get(i).getDescreption();
        }
        return values;
    }
}

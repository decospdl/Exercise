package uniform.database;

import br.sc.d3c0de.PersonTab;
import br.sc.d3c0de.formatter.DbFormatter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import uniform.Employee;
import util.ConsultaSqlServer;

/**
 *
 * @author Andre
 */
public abstract class DbEmployee {

    public static Employee selectFromDb(String enrollment) {
        try {
            ConsultaSqlServer connection = new ConsultaSqlServer();
            ResultSet rs = connection.getConsulta(createQuery(enrollment));
            if (rs.next()) {
               return consultCompany(rs);
            } else {
                connection.trocarEmpresa();
                rs = connection.getConsulta(createQuery(enrollment));
                if (rs.next()) {
                    return consultCompany(rs);
                }
            }
            }catch (SQLException ex) {
            Logger.getLogger(PersonTab.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
        }

    

    private static Employee consultCompany(ResultSet rs) {
        Employee employee = new Employee();
        try {
            employee.setId(rs.getInt("id"));
            employee.setHireDate(DbFormatter.dateDbTo(rs.getString("data_admissao")));
            employee.setCompany(rs.getString("empresa"));
            employee.setDepartment(rs.getString("local"));
            employee.setFunction(rs.getString("cargo"));
            employee.setName(rs.getString("nome"));
            employee.setPathPhoto(rs.getBytes("imagem_path"));
        } catch (SQLException ex) {
            Logger.getLogger(DbEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employee;
    }

    private static String createQuery(String enrollment) {
        return "SELECT fun.NumCad id, fun.NomFun nome, fun.DatAdm data_admissao, "
                + "emp.NomEmp empresa, loc.NomLoc local, car.TitCar cargo, fot.FotEmp imagem_path "
                + "FROM R034FUN fun "
                + "JOIN R030EMP emp ON emp.NumEmp = fun.NumEmp "
                + "JOIN R038HLO hloc ON hloc.NumCad = fun.NumCad "
                + "JOIN R038HCA hcar ON hcar.NumCad = fun.NumCad "
                + "LEFT JOIN R034FOT fot ON fot.NumCad = fun.NumCad "
                + "JOIN R016ORN loc ON loc.NumLoc = hloc.NumLoc "
                + "JOIN R024CAR car ON car.CodCar = hcar.CodCar "
                + "WHERE fun.numcad = " + enrollment
                + " AND hloc.datalt = (SELECT MAX(datalt) FROM R038HLO WHERE numcad = fun.numcad) "
                + "AND hcar.datalt = (SELECT MAX(datalt) FROM R038HCA WHERE numcad = fun.numcad)";
    }
}

package uniform.tabelas;

import br.sc.d3c0de.swing.component.TableStyle;
import br.sc.d3c0de.swing.table.TableModel;
import java.util.LinkedList;
import javax.swing.JTable;
import uniform.Retreat;

/**
 *
 * @author Andre
 */
public class TableRetreat {

    private JTable table;
    private Object[][] valuesTable;
    private TableModel model;
    private TableStyle style;
    private LinkedList<Retreat> retreats;

    public TableRetreat(JTable table, LinkedList<Retreat> retreats) {
        retreats = new LinkedList<>();
        this.table = table;
        updateTable(retreats);
    }

    public void addElement(Retreat retreat) {
        retreats.add(retreat);
        updateTable(retreats);
    }

    public void removeLastElement() {
        retreats.removeLast();
        updateTable(retreats);
    }

    /**
     * Configuração da tabela modelo.
     */
    public void configTableModel() {
        model = new TableModel(table, valuesTable);
        model.setColumnNames(new String[]{"qtde", "descricao", "tamanho"});
        model.setClassColumn(new Class[]{Integer.class, String.class, String.class});
        model.setPrimaryKey(0);
        model.setRowCount((valuesTable == null) ? 0 : valuesTable.length);
        model.start();
    }

    private void createTableValues() {
        if (retreats != null) {
            Object[][] values = new Object[retreats.size()][3];
            for (int i = 0; i < retreats.size(); i++) {
                values[i][0] = retreats.get(i).getQuantity();
                values[i][1] = retreats.get(i).getUniform().getDescreption();
                values[i][2] = retreats.get(i).getSize();
            }
            this.valuesTable = values;
        } else {
            this.valuesTable = null;
        }
    }
    
    public void cleanTable(){
        retreats.clear();
        updateTable(retreats);
    }

    public void updateTable(LinkedList<Retreat> retreats) {
        this.retreats = retreats;
        createTableValues();
        configTableModel();
        configTableStyle();
    }

    /**
     * Configuração da tabela de estilo.
     */
    public void configTableStyle() {
        style = new TableStyle(null, null, null);
        table.setDefaultRenderer(Integer.class, style);
        table.setDefaultRenderer(String.class, style);
    }

    /**
     * Getter.
     *
     * @return componente JTable utilizado para população da tabela.
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Getter.
     *
     * @return modelo que utilzado pela jTable.
     */
    public TableModel getTableModel() {
        return model;
    }

    /**
     * Getter.
     *
     * @return tabela de estilo que redenriza a JTable.
     */
    public TableStyle getTableStyle() {
        return style;
    }
    
    public LinkedList<Retreat> getListRetreat(){
        return retreats;
    }
}

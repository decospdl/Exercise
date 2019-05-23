package uniform.tabelas;

import br.sc.d3c0de.swing.component.TableStyle;
import br.sc.d3c0de.swing.table.TableModel;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import uniform.Retreat;

/**
 * Tabela para controla das alterações de retirada.
 *
 * @author d3c0de <decospdl@gmail.com>
 */
public class TableRetreatChanged {

    private JTable table;
    private Object[][] valuesTable;
    private TableModel model;
    private TableStyle style;
    private LinkedList<Retreat> retreats;
    private ListSelectionListener lss;
    private String selectedRow;

    public TableRetreatChanged(JTable table, LinkedList<Retreat> retreats) {
        this.table = table;
        updateTable(retreats);

    }

    /**
     * Configuração da tabela modelo.
     */
    public void configTableModel() {
        model = new TableModel(table, valuesTable);
        model.setClassColumn(new Class[]{Integer.class, Integer.class, String.class, String.class});
        model.setColumnNames(new String[]{"id", "qtde", "descricao", "tamanho"});
        model.setPrimaryKey(0);
        model.setRowCount((valuesTable == null) ? 0 : valuesTable.length);
        model.start();
    }

    private void createTableValues() {
        if (retreats != null) {
            Object[][] values = new Object[retreats.size()][4];
            for (int i = 0; i < retreats.size(); i++) {
                values[i][0] = retreats.get(i).getId();
                values[i][1] = retreats.get(i).getQuantity();
                values[i][2] = retreats.get(i).getUniform().getDescreption();
                values[i][3] = retreats.get(i).getSize();
            }
            this.valuesTable = values;
        } else {
            this.valuesTable = null;
        }
    }

    public void updateTable(LinkedList<Retreat> retreats) {
        this.retreats = retreats;
        createTableValues();
        configTableModel();
        configTableStyle();
        listennerSelectedRow();
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

    public void addElement(Retreat retreat) {
        retreats.add(retreat);
        updateTable(retreats);
    }

    public void removerElement(String id) {
        for (int i = 0; i < retreats.size(); i++) {
            if (retreats.get(i).getId() == Integer.parseInt(id)) {
                retreats.remove(retreats.get(i));
            }
        }
        updateTable(retreats);
    }

    public String getSelectedRow() {
        return selectedRow;
    }

    public LinkedList<Retreat> getRetreats() {
        return retreats;
    }

    /**
     * Inicia o listener para identificadação de qual célula esta sendo
     * selecionada.
     *
     * @param tabela a tabela que esta sendo atribuido o listener.
     */
    private void listennerSelectedRow() {
        table.getSelectionModel().removeListSelectionListener(lss);
        lss = (ListSelectionEvent e) -> {
            if (table.getSelectedRow() < valuesTable.length && table.getSelectedRow() >= 0) {
                selectedRow = table.getValueAt(table.getSelectedRow(), 0).toString();
            }
        };
        table.getSelectionModel().addListSelectionListener(lss);
    }
}

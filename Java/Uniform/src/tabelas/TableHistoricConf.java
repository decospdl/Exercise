package uniform.tabelas;

import br.sc.d3c0de.date.Date;
import br.sc.d3c0de.swing.component.TableStyle;
import br.sc.d3c0de.swing.table.TableModel;
import java.util.LinkedList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import uniform.Historic;
import uniform.database.DbInvoice;

/**
 *
 * @author Andre
 */
public class TableHistoricConf {

    private JTable table;
    private Object[][] valuesTable;
    private TableModel model;
    private TableStyle style;
    private TableFilterHeader filter;
    private LinkedList<Historic> historics;
    private ListSelectionListener lss;
    private String selectedRow;

    public TableHistoricConf(JTable table, LinkedList<Historic> historic) {
        filter = new TableFilterHeader();
        this.table = table;
        updateTable(historic);
    }

    /**
     * Configuração da tabela modelo.
     */
    public void configTableModel() {
        model = new TableModel(table, valuesTable);
        model.setColumnNames(new String[]{"id", "matricula", "nome", "data_retirada", "situacao", "nota_fiscal"});
        model.setClassColumn(new Class[]{Integer.class, Integer.class, String.class, Date.class, String.class, String.class});
        model.setPrimaryKey(0);
        model.setEditableColumns(new int[]{5});
        model.setRowCount((valuesTable == null) ? 0 : valuesTable.length);
        model.start();
    }

    private void createTableValues() {
        if (historics != null) {
            Object[][] values = new Object[historics.size()][6];
            for (int i = 0; i < historics.size(); i++) {
                values[i][0] = historics.get(i).getId();
                values[i][1] = historics.get(i).getEnrollmentEmployee();
                values[i][2] = historics.get(i).getNameEmployee();
                values[i][3] = historics.get(i).getRetreatedDate().getDate();
                values[i][4] = historics.get(i).getSituation().getDescription();
                values[i][5] = (historics.get(i).getInvoice() == null) ? null
                        : historics.get(i).getInvoice().getNumberInvoice();
            }
            this.valuesTable = values;
        } else {
            this.valuesTable = null;
        }
    }

    public void updateTable(LinkedList<Historic> historics) {
        this.historics = historics;
        createTableValues();
        configTableModel();
        createComboBoxInTable();
        configTableStyle();
        configTableFilter();
        listennerSelectedRow();
    }

    /**
     * Configuração da tabela de estilo.
     */
    public void configTableStyle() {
        LinkedList<String> green = new LinkedList<>();
        green.add("Em Andamento");
        style = new TableStyle(null, green, null);
        table.setDefaultRenderer(Integer.class, style);
        table.setDefaultRenderer(String.class, style);
        table.setDefaultRenderer(Date.class, style);
    }

    /**
     * Configuração da tabela de filtro.
     */
    public void configTableFilter() {
        filter.setTable(table);
        filter.setAutoChoices(AutoChoices.ENABLED);
        filter.setPosition(TableFilterHeader.Position.TOP);
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

    public LinkedList<Historic> getHistorics() {
        return historics;
    }

    public String getSelectedRow() {
        return selectedRow;
    }

    /**
     * Getter.
     *
     * @return lista de objetos que s~rão listado na comboBox.
     */
    private void createComboBoxInTable() {
        TableColumn combValues = table.getColumnModel().getColumn(5);
        JComboBox comboBox = new JComboBox(DbInvoice.getListInvoiceByName());
        combValues.setCellEditor(new DefaultCellEditor(comboBox));
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

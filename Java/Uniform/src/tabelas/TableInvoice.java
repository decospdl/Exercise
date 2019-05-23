package uniform.tabelas;

import br.sc.d3c0de.swing.component.TableStyle;
import br.sc.d3c0de.swing.table.TableModel;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import uniform.Invoice;

/**
 *
 * @author Andre
 */
public class TableInvoice {

    private JTable table;
    private Object[][] valuesTable;
    private TableModel model;
    private TableStyle style;
    private TableFilterHeader filter;
    private LinkedList<Invoice> invoices;
    private ListSelectionListener lss;
    private String selectedRow;

    public TableInvoice(JTable table, LinkedList<Invoice> invoices) {
        filter = new TableFilterHeader();
        this.table = table;
        updateTable(invoices);
    }

    /**
     * Configuração da tabela modelo.
     */
    public void configTableModel() {
        model = new TableModel(table, valuesTable);
        model.setColumnNames(new String[]{"id", "nota_fiscal", "mes_referente"});
        model.setClassColumn(new Class[]{Integer.class, String.class, String.class});
        model.setPrimaryKey(0);
        model.setRowCount((valuesTable == null) ? 0 : valuesTable.length);
        model.start();
    }

    private void createTableValues() {
        if (invoices != null) {
            Object[][] values = new Object[invoices.size()][3];
            for (int i = 0; i < invoices.size(); i++) {
                values[i][0] = invoices.get(i).getId();
                values[i][1] = invoices.get(i).getNumberInvoice();
                values[i][2] = invoices.get(i).getReferentMonth();
            }
            this.valuesTable = values;
        } else {
            this.valuesTable = null;
        }
    }

    public void updateTable(LinkedList<Invoice> invoices) {
        this.invoices = invoices;
        createTableValues();
        configTableModel();
        configTableStyle();
        configTableFilter();
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

    /**
     * Getter.
     *
     * @return o filtro parecido com excel, vindo com a lib.
     */
    public TableFilterHeader getTableFilter() {
        return filter;
    }

    public String getSelectedRow() {
        return selectedRow;
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

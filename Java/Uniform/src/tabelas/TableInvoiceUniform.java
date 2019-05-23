package uniform.tabelas;

import br.sc.d3c0de.swing.component.TableStyle;
import br.sc.d3c0de.swing.table.TableModel;
import java.math.BigDecimal;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import uniform.InvoiceUniform;

/**
 *
 * @author Andre
 */
public class TableInvoiceUniform {

    private JTable table;
    private Object[][] valuesTable;
    private TableModel model;
    private TableStyle style;
    private TableFilterHeader filter;
    private LinkedList<InvoiceUniform> invoiceUniforms;
    private ListSelectionListener lss;
    private String selectedRow;

    public TableInvoiceUniform(JTable table, LinkedList<InvoiceUniform> invoiceUniforms) {
        filter = new TableFilterHeader();
        this.table = table;
        updateTable(invoiceUniforms);
    }

    /**
     * Configuração da tabela modelo.
     */
    public void configTableModel() {
        model = new TableModel(table, valuesTable);
        model.setColumnNames(new String[]{"id", "peca", "tamanho", "qtde", "valor"});
        model.setClassColumn(new Class[]{Integer.class, String.class, String.class, Integer.class, BigDecimal.class});
        model.setPrimaryKey(0);
        model.setRowCount((valuesTable == null) ? 0 : valuesTable.length);
        model.start();
    }

    private void createTableValues() {
        if (invoiceUniforms != null) {
            Object[][] values = new Object[invoiceUniforms.size()][5];
            for (int i = 0; i < invoiceUniforms.size(); i++) {
                values[i][0] = invoiceUniforms.get(i).getId();
                values[i][1] = invoiceUniforms.get(i).getUniform().getDescreption();
                values[i][2] = invoiceUniforms.get(i).getSize();
                values[i][3] = invoiceUniforms.get(i).getQuantity();
                values[i][4] = invoiceUniforms.get(i).getValue();
            }
            this.valuesTable = values;
        } else {
            this.valuesTable = null;
        }
    }

    public void updateTable(LinkedList<InvoiceUniform> invoiceUniforms) {
        this.invoiceUniforms = invoiceUniforms;
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
        table.setDefaultRenderer(BigDecimal.class, style);
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

    public LinkedList<InvoiceUniform> getInvoiceUniforms() {
        return invoiceUniforms;
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

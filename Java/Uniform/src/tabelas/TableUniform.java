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
import uniform.Uniform;

/**
 *
 * @author Andre
 */
public class TableUniform {

    private JTable table;
    private Object[][] valuesTable;
    private TableModel model;
    private TableStyle style;
    private TableFilterHeader filter;
    private LinkedList<Uniform> uniforms;
    private ListSelectionListener lss;
    private String selectedRow;

    public TableUniform(JTable table, LinkedList<Uniform> uniforms) {
        filter = new TableFilterHeader();
        this.table = table;
        updateTable(uniforms);
    }

    /**
     * Configuração da tabela modelo.
     */
    public void configTableModel() {
        model = new TableModel(table, valuesTable);
        model.setColumnNames(new String[]{"id", "descricao", "tipo_tamanho", "valor"});
        model.setClassColumn(new Class[]{Integer.class, String.class, String.class, BigDecimal.class});
        model.setPrimaryKey(0);
        model.setRowCount((valuesTable == null) ? 0 : valuesTable.length);
        model.start();
    }

    private void createTableValues() {
        if (uniforms != null) {
            Object[][] values = new Object[uniforms.size()][4];
            for (int i = 0; i < uniforms.size(); i++) {
                values[i][0] = uniforms.get(i).getId();
                values[i][1] = uniforms.get(i).getDescreption();
                values[i][2] = uniforms.get(i).getSizeType().getDescription();
                values[i][3] = uniforms.get(i).getValue();
            }
            this.valuesTable = values;
        } else {
            this.valuesTable = null;
        }
    }

    public void updateTable(LinkedList<Uniform> uniforms) {
        this.uniforms = uniforms;
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

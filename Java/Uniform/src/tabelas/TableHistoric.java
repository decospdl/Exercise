package uniform.tabelas;

import br.sc.d3c0de.swing.component.TableStyle;
import br.sc.d3c0de.swing.table.TableModel;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import uniform.Historic;

/**
 *
 * @author Andre
 */
public class TableHistoric {

    private JTable table;
    private Object[][] valuesTable;
    private TableModel model;
    private TableStyle style;
    private TableFilterHeader filter;
    private LinkedList<Historic> historics;
    private ListSelectionListener lss;
    private String selectedRow;

    public TableHistoric(JTable table, LinkedList<Historic> historic) {
        filter = new TableFilterHeader();
        this.table = table;
        updateTable(historic);
    }

    /**
     * Configuração da tabela modelo.
     */
    public void configTableModel() {
        model = new TableModel(table, valuesTable);
        model.setColumnNames(new String[]{"id", "situacao", "entregue", "cancelado", "alterado", "encerrado"});
        model.setClassColumn(new Class[]{Integer.class, String.class, String.class, String.class, String.class, String.class});
        model.setPrimaryKey(0);
        model.setRowCount((valuesTable == null) ? 0 : valuesTable.length);
        model.start();
    }

    private void createTableValues() {
        if (historics != null) {
            Object[][] values = new Object[historics.size()][6];
            for (int i = 0; i < historics.size(); i++) {
                values[i][0] = historics.get(i).getId();
                values[i][1] = historics.get(i).getSituation().getDescription();
                values[i][2] = (historics.get(i).getRetreatedDate() == null) ? null
                        : historics.get(i).getRetreadedBy() + " - " + historics.get(i).getRetreatedDate().getDateTime();
                values[i][3] = (historics.get(i).getCanceledDate() == null) ? null
                        : historics.get(i).getCanceledBy() + " - " + historics.get(i).getCanceledDate().getDateTime();
                values[i][4] = (historics.get(i).getChangedDate() == null) ? null
                        : historics.get(i).getChangedBy() + " - " + historics.get(i).getChangedDate().getDateTime();
                values[i][5] = (historics.get(i).getClosedDate() == null) ? null
                        : historics.get(i).getClosedBy() + " - " + historics.get(i).getClosedDate().getDateTime();
            }
            this.valuesTable = values;
        } else {
            this.valuesTable = null;
        }
    }

    public void addElement(Historic historic) {
        historics.add(historic);
    }

    public void removeLastElement() {
        historics.removeLast();
    }

    public void updateTable(LinkedList<Historic> historics) {
        this.historics = historics;
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
        LinkedList<String> red = new LinkedList<>();
        LinkedList<String> green = new LinkedList<>();
        red.add("Cancelado");
        green.add("Em Andamento");
        style = new TableStyle(red, green, null);
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

    public String getSelectedRow() {
        return selectedRow;
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

    public LinkedList<Historic> getHistorics() {
        return historics;
    }
}

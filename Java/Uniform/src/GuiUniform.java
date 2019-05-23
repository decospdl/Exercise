package uniform;

import br.sc.d3c0de.date.Date;
import br.sc.d3c0de.formatter.DbFormatter;
import br.sc.d3c0de.swing.listenner.EmptyListener;
import br.sc.d3c0de.swing.listenner.ValidateTextField;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import uniform.database.DbEmployee;
import uniform.database.DbHistoric;
import uniform.database.DbInvoice;
import uniform.database.DbInvoiceUniform;
import uniform.database.DbRetreat;
import uniform.database.DbUniform;
import uniform.reports.ReportInvoiceConf;
import uniform.reports.ReportUniform;
import uniform.tabelas.TableRetreatChanged;
import uniform.tabelas.TableHistoricConf;
import uniform.tabelas.TableHistoric;
import uniform.tabelas.TableInvoice;
import uniform.tabelas.TableUniform;
import uniform.tabelas.TableInvoiceUniform;
import uniform.tabelas.TableRetreat;
import util.Constantes;

/**
 * Classe tela desenvolvida em Java Swing, foi utilizado o método layout card,
 * com isso todas as telas em uma única class.
 *
 * @author Andre
 */
public class GuiUniform extends javax.swing.JFrame {

    private static int idUniform = 0;
    private TableUniform tableUniform;
    private TableInvoice tableInvoice;
    private TableInvoiceUniform tableInvoiceUniform;
    private TableRetreat tableRetreat, tableRetreatResum;
    private TableHistoric tableHistoric;
    private TableHistoricConf tableHistoricConf;
    private TableRetreatChanged tableRetreatChanged;
    private Employee employee;

    public GuiUniform() {
        initComponents();
        initTables();
        guiUniformRetreat();
        addListenerValidateInsertUniform();
        addListenerValidateInsertInvoice();
        addListenerValidateInsertInvoiceUniform();
    }

    private void initTables() {
        tableUniform = new TableUniform(jTableUniform, null);
        tableInvoice = new TableInvoice(jTableInvoice, null);
        tableInvoiceUniform = new TableInvoiceUniform(jTableInvoiceUniform, null);
        tableRetreat = new TableRetreat(jTableRetreat, null);
        tableRetreatResum = new TableRetreat(jTableRetreatResum, null);
        tableHistoric = new TableHistoric(jTableHistoric, null);
        tableHistoricConf = new TableHistoricConf(jTableHistoricConf, null);
        tableRetreatChanged = new TableRetreatChanged(jTableRetreatChanged, null);
    }

    private void guiUniformRetreat() {
        jPanUniformRetreat.setVisible(true);
        jPanUniform.setVisible(false);
        jPanInvoice.setVisible(false);
        jPanInvoiceConf.setVisible(false);
        comboBoxUniformModel(jCBoxUniformeModelo);
        comboBoxUniformSize(jCBoxUniformeModelo, jCBoxUniformeTamanho);
        disableButtonsUniform();
    }

    private void comboBoxUniformModel(JComboBox model) {
        model.setModel(new DefaultComboBoxModel(DbUniform.getUniformeNames(DbUniform.ACTUAL)));
    }

    private void comboBoxUniformSize(JComboBox model, JComboBox size) {
        Uniform startUniform = DbUniform.getUniformBy(DbUniform.DESCRIPTION,
                (model.getItemAt(0) == null) ? null : model.getItemAt(0).toString());
        size.setModel((startUniform.getSizeType() == null) ? new DefaultComboBoxModel()
                : new DefaultComboBoxModel(startUniform.getSizeType().getSize()));
        ItemListener itemListener = (e) -> {
            Uniform uniform = DbUniform.getUniformBy(DbUniform.DESCRIPTION,
                    model.getSelectedItem().toString());
            size.setModel(new DefaultComboBoxModel(uniform.getSizeType().getSize()));
        };
        model.addItemListener(itemListener);
    }

    private void comboBoxQuantitaty(int retreat) {
        LinkedList<Historic> historics = DbHistoric.getListHistoricBy(DbHistoric.EMPLOYEE,
                jTxtUniformEnrollment.getText());
        if (Historic.isSituationDiferentCanceled(historics)) {
            updateComboBoxQuantity(retreat);
        } else {
            updateComboBoxQuantity(retreat + 1);
        }
    }

    private void updateComboBoxQuantity(int retreat) {
        int nQuantity = 5 - retreat;
        jCBoxUniformeQtde.removeAllItems();
        if (nQuantity != 0) {
            for (int i = 1; i <= nQuantity; i++) {
                jCBoxUniformeQtde.addItem(String.valueOf(i));
            }
        } else {
            jButUniformeInsRet.setEnabled(false);
            jButUniformeRemRet.setEnabled(true);
        }
    }

    private void guiUniform() {
        jPanUniformRetreat.setVisible(false);
        jPanUniform.setVisible(true);
        jPanInvoice.setVisible(false);
        jPanInvoiceConf.setVisible(false);
        jButPecaDes.setEnabled(false);
    }

    private void addListenerValidateInsertUniform() {
        LinkedList<JTextField> textFields = new LinkedList<>();
        textFields.add(jTxtInsPecaDescricao);
        textFields.add(jTxtInsPecaValor);
        EmptyListener vel = new EmptyListener(textFields, jButInsPecaOk);
        jTxtInsPecaDescricao.addKeyListener(vel);
        jTxtInsPecaValor.addKeyListener(vel);
    }

    private void guiInvoice() {
        jPanUniformRetreat.setVisible(false);
        jPanUniform.setVisible(false);
        jPanInvoice.setVisible(true);
        jPanInvoiceConf.setVisible(false);
        jButNotaExcPeca.setEnabled(false);
        jButNotaExc.setEnabled(false);
        comboBoxUniformModel(jCBoxInsPecaNota);
        comboBoxUniformSize(jCBoxInsPecaNota, jCBoxInsPecaNotaTam);
    }

    private void addListenerValidateInsertInvoice() {
        LinkedList<JTextField> textFields = new LinkedList<>();
        textFields.add(jTxtInsNotaDescNota);
        textFields.add(jTxtInsNotaMesReferente);
        EmptyListener vel = new EmptyListener(textFields, jButInsNotaOk);
        jTxtInsNotaDescNota.addKeyListener(vel);
        jTxtInsNotaMesReferente.addKeyListener(vel);
    }

    private void addListenerValidateInsertInvoiceUniform() {
        LinkedList<JTextField> textFields = new LinkedList<>();
        textFields.add(jTxtInsPecaNotaQtde);
        EmptyListener vel = new EmptyListener(textFields, jButInsPecaNotaOk);
        jTxtInsPecaNotaQtde.addKeyListener(vel);
    }

    private void guiInvoiceConf() {
        jPanUniformRetreat.setVisible(false);
        jPanUniform.setVisible(false);
        jPanInvoice.setVisible(false);
        jPanInvoiceConf.setVisible(true);
        jCBoxNota.setModel(new DefaultComboBoxModel(DbInvoice.getListInvoiceByName()));
    }

    private void setPhotoEmployee(byte[] pathPhoto) {
        Image img = Toolkit.getDefaultToolkit().createImage(pathPhoto);
        ImageIcon foto = new ImageIcon(img);
        foto.setImage((foto.getImage().getScaledInstance(108, 134, java.awt.Image.SCALE_DEFAULT)));
        jLabUniformPhoto.setText("");
        jLabUniformPhoto.setIcon(foto);
    }

    private void showEmployee(String enrollment) {
        employee = DbEmployee.selectFromDb(enrollment);
        if (employee != null) {
            jLabUniformCompany.setText(employee.getCompany());
            jLabUniformDepartament.setText(employee.getDepartment());
            jLabUniformFunction.setText(employee.getFunction());
            jLabUniformHireDate.setText(employee.getHireDate().getDate());
            jLabUniformName.setText(employee.getName());
            setPhotoEmployee(employee.getPathPhoto());
        } else {
            removeEmployee();
            tableHistoric.updateTable(null);
        }
    }

    private void showHistoric(Historic historics) {
        if (historics != null) {
            Date dateRetreat = historics.getRetreatedDate();
            Date dateNext = dateRetreat.addDay(180);
            jLabUniformRetreatDate.setText(dateRetreat.getDate());
            jLabUniformNextDate.setText(dateNext.getDate());
        }
    }

    private void removeEmployee() {
        jLabUniformName.setText("");
        jLabUniformHireDate.setText("");
        jLabUniformFunction.setText("");
        jLabUniformDepartament.setText("");
        jLabUniformCompany.setText("");
        jLabUniformRetreatDate.setText("");
        jLabUniformNextDate.setText("");
        jLabUniformPhoto.setIcon(null);
        jLabUniformPhoto.setText("Foto");
    }

    private void disableButtonsUniform() {
        jButUniformCad.setEnabled(false);
        jButUniformeInsRet.setEnabled(false);
        jButUniformeRemRet.setEnabled(false);
        jButUniformCancel.setEnabled(false);
        jButUniformPrint.setEnabled(false);
    }

    private void checkRetreatDate(String situation) {
        Date today = new Date();
        Date dateNext = new Date(jLabUniformNextDate.getText(), "00:00:00");
        if (dateNext.isAfterOrEqual(today) && !situation.equals("Cancelado")) {
            long days = today.subDates(dateNext) + 1;
            JOptionPane.showMessageDialog(rootPane, "Falta(m) " + days + " dia(s)", "Aviso!", JOptionPane.ERROR_MESSAGE);
            jButUniformeInsRet.setEnabled(false);
        } else {
            jButUniformeInsRet.setEnabled(true);
        }
    }

    private void initTableResumo(String note) {
        LinkedList<Retreat> retreats = DbRetreat.getListRetreatBy(DbRetreat.HISTORIC, tableHistoric.getSelectedRow());
        tableRetreatResum.updateTable(retreats);
        jTxtResumoObs.setText(note);
    }

    private Historic createHistoric() {
        Historic historic = new Historic();
        historic.setSituation(Situation.IN_PROGRESS);
        historic.setEnrollmentEmployee(jTxtUniformEnrollment.getText());
        historic.setNameEmployee(jLabUniformName.getText());
        historic.setNote(jTxtUniformeObservacao.getText());
        historic.setRetreadedBy(Constantes.abreviaNome(Constantes.user_Nome));
        historic.setRetreatedDate(new Date());
        return historic;
    }

    private void updateTotalValue() {
        LinkedList<InvoiceUniform> invoiceUniforms = tableInvoiceUniform.getInvoiceUniforms();
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        for (InvoiceUniform invoiceUniform : invoiceUniforms) {
            sum = sum.add(invoiceUniform.getValue());
        }
        jLabNotaValor.setText(sum.toString());
    }

    private void deleteRetreatsChanged(LinkedList<Retreat> changeds, LinkedList<Retreat> retreats) {
        for (int i = 0; i < retreats.size(); i++) {
            boolean delete = true;
            for (int j = 0; j < changeds.size(); j++) {
                if (Objects.equals(retreats.get(i).getId(), changeds.get(j).getId())) {
                    delete = false;
                    break;
                }
            }
            if (delete) {
                DbRetreat.deleteRetreatDb(retreats.get(i));
            }
        }
    }

    /**
     * Código Gerado pelo JavaSwing. This method is called from within the
     * constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFraInsPecas = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabDescricao = new javax.swing.JLabel();
        jTxtInsPecaDescricao = new javax.swing.JTextField();
        jLabDesTipoTamanho = new javax.swing.JLabel();
        jLabDesValor = new javax.swing.JLabel();
        jTxtInsPecaValor = new javax.swing.JTextField();
        jCBoxInsPecaTipoTamanho = new javax.swing.JComboBox<>();
        jButInsPecaOk = new javax.swing.JButton();
        jFraInsNota = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        jTxtInsNotaDescNota = new javax.swing.JTextField();
        jButInsNotaOk = new javax.swing.JButton();
        jTxtInsNotaMesReferente = new javax.swing.JTextField();
        jFraInsPecaNota = new javax.swing.JFrame();
        jPanel8 = new javax.swing.JPanel();
        jButInsPecaNotaOk = new javax.swing.JButton();
        jCBoxInsPecaNota = new javax.swing.JComboBox<>();
        jCBoxInsPecaNotaTam = new javax.swing.JComboBox<>();
        jTxtInsPecaNotaQtde = new javax.swing.JTextField();
        jFraResumo = new javax.swing.JFrame();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableRetreatResum = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTxtResumoObs = new javax.swing.JTextArea();
        jFraAlteraRetirada = new javax.swing.JFrame();
        jPanel6 = new javax.swing.JPanel();
        jTxtRetiradaObs = new javax.swing.JTextArea();
        jButRetiradaRemover = new javax.swing.JButton();
        jButRetiradaInserir = new javax.swing.JButton();
        jButRetiradaConfirma = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableRetreatChanged = new javax.swing.JTable();
        jFraInsRetirada = new javax.swing.JFrame();
        jPanel9 = new javax.swing.JPanel();
        jButRetiradaOK = new javax.swing.JButton();
        jCBoxRetiradaPeca = new javax.swing.JComboBox<>();
        jCBoxRetiradaTamanho = new javax.swing.JComboBox<>();
        jCBoxRetiradaQtde = new javax.swing.JComboBox<>();
        jPanMenus = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButTelaUniforme = new javax.swing.JButton();
        jButTelaPeças = new javax.swing.JButton();
        jButTelaNota = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButTelaConfNota = new javax.swing.JButton();
        jPanelTelas = new javax.swing.JPanel();
        jPanUniformRetreat = new javax.swing.JPanel();
        jTxtUniformEnrollment = new javax.swing.JTextField();
        jLabUniformName = new javax.swing.JLabel();
        jLabUniformHireDate = new javax.swing.JLabel();
        jLabUniformFunction = new javax.swing.JLabel();
        jLabUniformDepartament = new javax.swing.JLabel();
        jLabUniformCompany = new javax.swing.JLabel();
        jLabUniformRetreatDate = new javax.swing.JLabel();
        jLabUniformNextDate = new javax.swing.JLabel();
        jLabUniformPhoto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableHistoric = new javax.swing.JTable();
        jButUniformCad = new javax.swing.JButton();
        jButUniformPrint = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jCBoxUniformeQtde = new javax.swing.JComboBox<>();
        jCBoxUniformeModelo = new javax.swing.JComboBox<>();
        jCBoxUniformeTamanho = new javax.swing.JComboBox<>();
        jButUniformeInsRet = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRetreat = new javax.swing.JTable();
        jButUniformeRemRet = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTxtUniformeObservacao = new javax.swing.JTextArea();
        jButUniformCancel = new javax.swing.JButton();
        jCBoxCompany = new javax.swing.JComboBox<>();
        jPanUniform = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableUniform = new javax.swing.JTable();
        jButPecaIns = new javax.swing.JButton();
        jButPecaDes = new javax.swing.JButton();
        jPanInvoice = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableInvoiceUniform = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableInvoice = new javax.swing.JTable();
        jLab16 = new javax.swing.JLabel();
        jLab24 = new javax.swing.JLabel();
        jButNotaIns = new javax.swing.JButton();
        jButNotaExc = new javax.swing.JButton();
        jButNotaInsPeca = new javax.swing.JButton();
        jButNotaExcPeca = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLab21 = new javax.swing.JLabel();
        jLabNotaValor = new javax.swing.JLabel();
        jPanInvoiceConf = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableHistoricConf = new javax.swing.JTable();
        jButConfNotaAlt = new javax.swing.JButton();
        jButConfNotaImp = new javax.swing.JButton();
        jCBoxNota = new javax.swing.JComboBox<>();
        jButConfNotaCancel = new javax.swing.JButton();

        jFraInsPecas.setTitle("Cadastro de Peças");
        jFraInsPecas.setLocationByPlatform(true);
        jFraInsPecas.setMinimumSize(new java.awt.Dimension(342, 206));
        jFraInsPecas.setResizable(false);
        jFraInsPecas.setSize(new java.awt.Dimension(342, 206));
        jFraInsPecas.setVisible(false);

        jLabDescricao.setText("Descricao");

        jLabDesTipoTamanho.setText("Tipo Tamanho");

        jLabDesValor.setText("Valor");

        jCBoxInsPecaTipoTamanho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButInsPecaOk.setBackground(new java.awt.Color(204, 204, 204));
        jButInsPecaOk.setText("OK");
        jButInsPecaOk.setEnabled(false);
        jButInsPecaOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButInsPecaOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtInsPecaDescricao)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jButInsPecaOk, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                            .addComponent(jCBoxInsPecaTipoTamanho, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabDescricao)
                                    .addComponent(jLabDesTipoTamanho))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabDesValor)
                            .addComponent(jTxtInsPecaValor, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabDescricao)
                .addGap(0, 0, 0)
                .addComponent(jTxtInsPecaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabDesTipoTamanho)
                    .addComponent(jLabDesValor))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtInsPecaValor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBoxInsPecaTipoTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButInsPecaOk)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFraInsPecasLayout = new javax.swing.GroupLayout(jFraInsPecas.getContentPane());
        jFraInsPecas.getContentPane().setLayout(jFraInsPecasLayout);
        jFraInsPecasLayout.setHorizontalGroup(
            jFraInsPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraInsPecasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jFraInsPecasLayout.setVerticalGroup(
            jFraInsPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraInsPecasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jFraInsNota.setTitle("Cadastro de Peças");
        jFraInsNota.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jFraInsNota.setLocationByPlatform(true);
        jFraInsNota.setMinimumSize(new java.awt.Dimension(342, 206));
        jFraInsNota.setResizable(false);
        jFraInsNota.setSize(new java.awt.Dimension(342, 206));
        jFraInsPecas.setVisible(false);

        jTxtInsNotaDescNota.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nota Fiscal", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jButInsNotaOk.setBackground(new java.awt.Color(204, 204, 204));
        jButInsNotaOk.setText("OK");
        jButInsNotaOk.setEnabled(false);
        jButInsNotaOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButInsNotaOkActionPerformed(evt);
            }
        });

        jTxtInsNotaMesReferente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mês Referente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtInsNotaDescNota)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jButInsNotaOk, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addGap(108, 108, 108))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTxtInsNotaMesReferente, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTxtInsNotaDescNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTxtInsNotaMesReferente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jButInsNotaOk)
                .addContainerGap())
        );

        javax.swing.GroupLayout jFraInsNotaLayout = new javax.swing.GroupLayout(jFraInsNota.getContentPane());
        jFraInsNota.getContentPane().setLayout(jFraInsNotaLayout);
        jFraInsNotaLayout.setHorizontalGroup(
            jFraInsNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraInsNotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jFraInsNotaLayout.setVerticalGroup(
            jFraInsNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraInsNotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jFraInsPecaNota.setTitle("Cadastro de Peças Nota Fiscal");
        jFraInsPecaNota.setMinimumSize(new java.awt.Dimension(400, 250));

        jPanel8.setMaximumSize(new java.awt.Dimension(400, 250));
        jPanel8.setMinimumSize(new java.awt.Dimension(328, 196));
        jPanel8.setPreferredSize(new java.awt.Dimension(400, 250));

        jButInsPecaNotaOk.setBackground(new java.awt.Color(204, 204, 204));
        jButInsPecaNotaOk.setText("OK");
        jButInsPecaNotaOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButInsPecaNotaOkActionPerformed(evt);
            }
        });

        jCBoxInsPecaNota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBoxInsPecaNota.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Peça", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jCBoxInsPecaNotaTam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBoxInsPecaNotaTam.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tamanho", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jTxtInsPecaNotaQtde.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Qtde", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBoxInsPecaNota, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCBoxInsPecaNotaTam, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTxtInsPecaNotaQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(146, Short.MAX_VALUE)
                .addComponent(jButInsPecaNotaOk, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCBoxInsPecaNota, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBoxInsPecaNotaTam, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtInsPecaNotaQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButInsPecaNotaOk)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout jFraInsPecaNotaLayout = new javax.swing.GroupLayout(jFraInsPecaNota.getContentPane());
        jFraInsPecaNota.getContentPane().setLayout(jFraInsPecaNotaLayout);
        jFraInsPecaNotaLayout.setHorizontalGroup(
            jFraInsPecaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraInsPecaNotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        jFraInsPecaNotaLayout.setVerticalGroup(
            jFraInsPecaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraInsPecaNotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jFraResumo.setTitle("Cadastro de Peças");
        jFraResumo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jFraResumo.setLocationByPlatform(true);
        jFraResumo.setSize(new java.awt.Dimension(600, 280));
        jFraInsPecas.setVisible(false);

        jPanel5.setPreferredSize(new java.awt.Dimension(491, 167));

        jTableRetreatResum.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"2", "Bermuda", "M"},
                {"2", "Camisa Polo", "M"},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Qtde", "Modelo", "Tamanho"
            }
        ));
        jScrollPane7.setViewportView(jTableRetreatResum);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Observação:");

        jTxtResumoObs.setColumns(20);
        jTxtResumoObs.setForeground(new java.awt.Color(153, 153, 153));
        jTxtResumoObs.setLineWrap(true);
        jTxtResumoObs.setRows(5);
        jTxtResumoObs.setBorder(null);
        jTxtResumoObs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTxtResumoObs.setEnabled(false);
        jTxtResumoObs.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtResumoObs)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtResumoObs, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFraResumoLayout = new javax.swing.GroupLayout(jFraResumo.getContentPane());
        jFraResumo.getContentPane().setLayout(jFraResumoLayout);
        jFraResumoLayout.setHorizontalGroup(
            jFraResumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraResumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                .addContainerGap())
        );
        jFraResumoLayout.setVerticalGroup(
            jFraResumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraResumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        jFraAlteraRetirada.setTitle("Cadastro de Peças");
        jFraAlteraRetirada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jFraAlteraRetirada.setLocationByPlatform(true);
        jFraAlteraRetirada.setMinimumSize(null);
        jFraAlteraRetirada.setSize(new java.awt.Dimension(600, 300));
        jFraInsPecas.setVisible(false);

        jPanel6.setPreferredSize(new java.awt.Dimension(491, 167));

        jTxtRetiradaObs.setColumns(20);
        jTxtRetiradaObs.setForeground(new java.awt.Color(153, 153, 153));
        jTxtRetiradaObs.setLineWrap(true);
        jTxtRetiradaObs.setRows(5);
        jTxtRetiradaObs.setBorder(null);
        jTxtRetiradaObs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTxtRetiradaObs.setEnabled(false);
        jTxtRetiradaObs.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        jButRetiradaRemover.setBackground(new java.awt.Color(204, 204, 204));
        jButRetiradaRemover.setText("Remover");
        jButRetiradaRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButRetiradaRemoverActionPerformed(evt);
            }
        });

        jButRetiradaInserir.setBackground(new java.awt.Color(204, 204, 204));
        jButRetiradaInserir.setText("Inserir");
        jButRetiradaInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButRetiradaInserirActionPerformed(evt);
            }
        });

        jButRetiradaConfirma.setBackground(new java.awt.Color(204, 204, 204));
        jButRetiradaConfirma.setText("Confirmar");
        jButRetiradaConfirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButRetiradaConfirmaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Observação:");

        jTableRetreatChanged.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"2", "Bermuda", "M"},
                {"2", "Camisa Polo", "M"},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Qtde", "Modelo", "Tamanho"
            }
        ));
        jTableRetreatChanged.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableRetreatChangedMouseReleased(evt);
            }
        });
        jScrollPane8.setViewportView(jTableRetreatChanged);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButRetiradaConfirma, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButRetiradaInserir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButRetiradaRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtRetiradaObs)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtRetiradaObs, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButRetiradaConfirma, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButRetiradaInserir, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButRetiradaRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFraAlteraRetiradaLayout = new javax.swing.GroupLayout(jFraAlteraRetirada.getContentPane());
        jFraAlteraRetirada.getContentPane().setLayout(jFraAlteraRetiradaLayout);
        jFraAlteraRetiradaLayout.setHorizontalGroup(
            jFraAlteraRetiradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraAlteraRetiradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        jFraAlteraRetiradaLayout.setVerticalGroup(
            jFraAlteraRetiradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraAlteraRetiradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        jFraInsRetirada.setTitle("Cadastro de Peças Nota Fiscal");
        jFraInsRetirada.setMinimumSize(new java.awt.Dimension(400, 230));
        jFraInsRetirada.setSize(new java.awt.Dimension(400, 230));

        jPanel9.setMaximumSize(new java.awt.Dimension(400, 250));
        jPanel9.setMinimumSize(new java.awt.Dimension(400, 250));
        jPanel9.setPreferredSize(new java.awt.Dimension(400, 250));

        jButRetiradaOK.setBackground(new java.awt.Color(204, 204, 204));
        jButRetiradaOK.setText("OK");
        jButRetiradaOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButRetiradaOKActionPerformed(evt);
            }
        });

        jCBoxRetiradaPeca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBoxRetiradaPeca.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Peça", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jCBoxRetiradaTamanho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBoxRetiradaTamanho.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tamanho", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jCBoxRetiradaQtde.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBoxRetiradaQtde.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Qtde", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButRetiradaOK, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCBoxRetiradaPeca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jCBoxRetiradaTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCBoxRetiradaQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCBoxRetiradaPeca, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBoxRetiradaQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBoxRetiradaTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButRetiradaOK)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFraInsRetiradaLayout = new javax.swing.GroupLayout(jFraInsRetirada.getContentPane());
        jFraInsRetirada.getContentPane().setLayout(jFraInsRetiradaLayout);
        jFraInsRetiradaLayout.setHorizontalGroup(
            jFraInsRetiradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraInsRetiradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jFraInsRetiradaLayout.setVerticalGroup(
            jFraInsRetiradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFraInsRetiradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Controle de Uniforme");

        jPanMenus.setBackground(new java.awt.Color(231, 231, 231));
        jPanMenus.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jButTelaUniforme.setBackground(new java.awt.Color(153, 204, 255));
        jButTelaUniforme.setText("Uniforme (+)");
        jButTelaUniforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButTelaUniformeActionPerformed(evt);
            }
        });

        jButTelaPeças.setBackground(new java.awt.Color(204, 204, 204));
        jButTelaPeças.setText("Peças");
        jButTelaPeças.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButTelaPeçasActionPerformed(evt);
            }
        });

        jButTelaNota.setBackground(new java.awt.Color(204, 204, 204));
        jButTelaNota.setText("Nota Fiscal");
        jButTelaNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButTelaNotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButTelaUniforme, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButTelaPeças, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButTelaNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButTelaUniforme, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButTelaPeças)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButTelaNota)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Conferência", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jButTelaConfNota.setBackground(new java.awt.Color(204, 204, 204));
        jButTelaConfNota.setText("Nota Fiscal");
        jButTelaConfNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButTelaConfNotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButTelaConfNota, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButTelaConfNota)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanMenusLayout = new javax.swing.GroupLayout(jPanMenus);
        jPanMenus.setLayout(jPanMenusLayout);
        jPanMenusLayout.setHorizontalGroup(
            jPanMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanMenusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanMenusLayout.setVerticalGroup(
            jPanMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanMenusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(369, Short.MAX_VALUE))
        );

        jPanelTelas.setLayout(new java.awt.CardLayout());

        jPanUniformRetreat.setAlignmentX(0.0F);
        jPanUniformRetreat.setAlignmentY(0.0F);

        jTxtUniformEnrollment.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Matricula", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jTxtUniformEnrollment.setMargin(new java.awt.Insets(0, 2, 2, 2));
        jTxtUniformEnrollment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtUniformEnrollmentKeyReleased(evt);
            }
        });

        jLabUniformName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabUniformName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nome", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabUniformHireDate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabUniformHireDate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Admissão", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabUniformFunction.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabUniformFunction.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cargo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabUniformDepartament.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabUniformDepartament.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Local", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabUniformCompany.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabUniformCompany.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabUniformRetreatDate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabUniformRetreatDate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Última Retirada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabUniformNextDate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabUniformNextDate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Próxima Retirada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabUniformPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabUniformPhoto.setText("Foto");
        jLabUniformPhoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabUniformPhoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTableHistoric.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"6 mes", "DD/MM/YYYY", "Em Andamento", "Bianca"},
                {"8 mes", "DD/MM/YYYY", "Encerrado", "Aline"},
                {"6 mes", "DD/MM/YYYY", "Cancelado", "Rose"}
            },
            new String [] {
                "Tempo", "Data Retirada", "Situação", "Entregue por"
            }
        ));
        jTableHistoric.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableHistoricMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableHistoric);

        jButUniformCad.setBackground(new java.awt.Color(204, 204, 204));
        jButUniformCad.setText("Cadastrar");
        jButUniformCad.setEnabled(false);
        jButUniformCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButUniformCadActionPerformed(evt);
            }
        });

        jButUniformPrint.setBackground(new java.awt.Color(204, 204, 204));
        jButUniformPrint.setText("Imprimir");
        jButUniformPrint.setEnabled(false);
        jButUniformPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButUniformPrintActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Retirar Uniforme"));

        jCBoxUniformeQtde.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));
        jCBoxUniformeQtde.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Qtde", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jCBoxUniformeModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Camisa Manga Longa", "Calça", "Bermuda", " " }));
        jCBoxUniformeModelo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modelo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jCBoxUniformeTamanho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "P", "PP", "M", "G", "GG" }));
        jCBoxUniformeTamanho.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tam", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jButUniformeInsRet.setBackground(new java.awt.Color(204, 204, 204));
        jButUniformeInsRet.setText("Inserir");
        jButUniformeInsRet.setEnabled(false);
        jButUniformeInsRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButUniformeInsRetActionPerformed(evt);
            }
        });

        jTableRetreat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"2", "Bermuda", "M"},
                {"2", "Camisa Polo", "M"},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Qtde", "Modelo", "Tamanho"
            }
        ));
        jTableRetreat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(jTableRetreat);

        jButUniformeRemRet.setBackground(new java.awt.Color(204, 204, 204));
        jButUniformeRemRet.setText("Remover");
        jButUniformeRemRet.setEnabled(false);
        jButUniformeRemRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButUniformeRemRetActionPerformed(evt);
            }
        });

        jTxtUniformeObservacao.setColumns(20);
        jTxtUniformeObservacao.setRows(5);
        jTxtUniformeObservacao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Observação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jScrollPane10.setViewportView(jTxtUniformeObservacao);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jCBoxUniformeQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBoxUniformeModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBoxUniformeTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButUniformeInsRet, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButUniformeRemRet, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane10)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCBoxUniformeQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBoxUniformeModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBoxUniformeTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jButUniformeInsRet, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButUniformeRemRet, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 25, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jButUniformCancel.setBackground(new java.awt.Color(204, 204, 204));
        jButUniformCancel.setText("Cancelar");
        jButUniformCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButUniformCancelActionPerformed(evt);
            }
        });

        jCBoxCompany.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estrela", "Insular", "Consórcio Fênix" }));

        javax.swing.GroupLayout jPanUniformRetreatLayout = new javax.swing.GroupLayout(jPanUniformRetreat);
        jPanUniformRetreat.setLayout(jPanUniformRetreatLayout);
        jPanUniformRetreatLayout.setHorizontalGroup(
            jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanUniformRetreatLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanUniformRetreatLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButUniformCad, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButUniformCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButUniformPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBoxCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanUniformRetreatLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanUniformRetreatLayout.createSequentialGroup()
                        .addGroup(jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanUniformRetreatLayout.createSequentialGroup()
                                .addComponent(jTxtUniformEnrollment, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(jLabUniformRetreatDate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabUniformNextDate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanUniformRetreatLayout.createSequentialGroup()
                                .addComponent(jLabUniformName, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabUniformHireDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanUniformRetreatLayout.createSequentialGroup()
                                .addComponent(jLabUniformFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabUniformDepartament, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabUniformCompany, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabUniformPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)))
        );
        jPanUniformRetreatLayout.setVerticalGroup(
            jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanUniformRetreatLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanUniformRetreatLayout.createSequentialGroup()
                        .addGroup(jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtUniformEnrollment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabUniformRetreatDate, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                .addComponent(jLabUniformNextDate, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabUniformName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabUniformHireDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabUniformFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabUniformDepartament, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabUniformCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabUniformPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanUniformRetreatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButUniformCad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButUniformPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButUniformCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBoxCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
        );

        jPanelTelas.add(jPanUniformRetreat, "card2");

        jPanUniform.setMinimumSize(new java.awt.Dimension(590, 559));
        jPanUniform.setName(""); // NOI18N
        jPanUniform.setPreferredSize(new java.awt.Dimension(590, 559));
        jPanUniform.setVisible(false);

        jTableUniform.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Seq", "Descrição", "Tipo Tamanho", "Valor"
            }
        ));
        jTableUniform.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableUniformMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableUniform);

        jButPecaIns.setBackground(new java.awt.Color(204, 204, 204));
        jButPecaIns.setText("Inserir");
        jButPecaIns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButPecaInsActionPerformed(evt);
            }
        });

        jButPecaDes.setBackground(new java.awt.Color(204, 204, 204));
        jButPecaDes.setText("Desativar");
        jButPecaDes.setToolTipText("");
        jButPecaDes.setEnabled(false);
        jButPecaDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButPecaDesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanUniformLayout = new javax.swing.GroupLayout(jPanUniform);
        jPanUniform.setLayout(jPanUniformLayout);
        jPanUniformLayout.setHorizontalGroup(
            jPanUniformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanUniformLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanUniformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                    .addGroup(jPanUniformLayout.createSequentialGroup()
                        .addComponent(jButPecaIns, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButPecaDes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanUniformLayout.setVerticalGroup(
            jPanUniformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanUniformLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanUniformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButPecaIns)
                    .addComponent(jButPecaDes))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE))
        );

        jPanelTelas.add(jPanUniform, "card3");

        jPanInvoice.setMinimumSize(new java.awt.Dimension(590, 559));
        jPanInvoice.setName(""); // NOI18N
        jPanInvoice.setPreferredSize(new java.awt.Dimension(590, 559));
        jPanInvoice.setVisible(false);

        jTableInvoiceUniform.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "Camisa Manga Longa", "G", "10", "350,25"},
                {"2", "Comisa Manga Curta", "M", "11", "512,23"},
                {"3", "Bermuda", "P", "15", "423,50"},
                {"4", "Calça", "PP", "13", "458,25"}
            },
            new String [] {
                "Seq", "Descrição", "Tamanho", "Qtde", " Valor"
            }
        ));
        jTableInvoiceUniform.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableInvoiceUniformMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTableInvoiceUniform);

        jTableInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "NF-01533578", "03/2019"},
                {"2", "NF-46056879", "03/2019"},
                {"3", "NF-85132798", "03/2019"},
                {"4", "NF-49801965", "03/2019"}
            },
            new String [] {
                "Seq", "Nº Nota Fiscal", "Mês/Ano"
            }
        ));
        jTableInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableInvoiceMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(jTableInvoice);

        jLab16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLab16.setText("Nota Fiscal");

        jLab24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLab24.setText("Peças");

        jButNotaIns.setBackground(new java.awt.Color(204, 204, 204));
        jButNotaIns.setText("Inserir");
        jButNotaIns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButNotaInsActionPerformed(evt);
            }
        });

        jButNotaExc.setBackground(new java.awt.Color(204, 204, 204));
        jButNotaExc.setText("Excluir");
        jButNotaExc.setEnabled(false);
        jButNotaExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButNotaExcActionPerformed(evt);
            }
        });

        jButNotaInsPeca.setBackground(new java.awt.Color(204, 204, 204));
        jButNotaInsPeca.setText("Inserir");
        jButNotaInsPeca.setEnabled(false);
        jButNotaInsPeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButNotaInsPecaActionPerformed(evt);
            }
        });

        jButNotaExcPeca.setBackground(new java.awt.Color(204, 204, 204));
        jButNotaExcPeca.setText("Excluir");
        jButNotaExcPeca.setEnabled(false);
        jButNotaExcPeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButNotaExcPecaActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLab21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLab21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLab21.setText("Valor Total");

        jLabNotaValor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabNotaValor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabNotaValor.setText("-");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLab21, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabNotaValor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLab21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabNotaValor)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanInvoiceLayout = new javax.swing.GroupLayout(jPanInvoice);
        jPanInvoice.setLayout(jPanInvoiceLayout);
        jPanInvoiceLayout.setHorizontalGroup(
            jPanInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanInvoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanInvoiceLayout.createSequentialGroup()
                        .addGroup(jPanInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanInvoiceLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButNotaIns, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButNotaExc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanInvoiceLayout.createSequentialGroup()
                                .addComponent(jLab16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanInvoiceLayout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(30, 30, 30)))
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanInvoiceLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButNotaInsPeca, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButNotaExcPeca, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(360, Short.MAX_VALUE))
                    .addGroup(jPanInvoiceLayout.createSequentialGroup()
                        .addComponent(jLab24)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanInvoiceLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())))
        );
        jPanInvoiceLayout.setVerticalGroup(
            jPanInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanInvoiceLayout.createSequentialGroup()
                .addComponent(jLab16)
                .addGap(8, 8, 8)
                .addGroup(jPanInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButNotaIns)
                    .addComponent(jButNotaExc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanInvoiceLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)))
                .addComponent(jLab24)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButNotaExcPeca)
                    .addComponent(jButNotaInsPeca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelTelas.add(jPanInvoice, "card4");

        jPanInvoiceConf.setMinimumSize(new java.awt.Dimension(607, 566));
        jPanInvoiceConf.setName(""); // NOI18N
        jPanInvoiceConf.setVisible(false);

        jTableHistoricConf.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"50320", "João dos Frangos", "10/02/2018", "Em Andamento", "NF-105612"},
                {"25025", "Pedro dos Peixes", "10/03/2011", "Encerrado", "NF-105612"},
                {"33548", "João dos Couves", "05/04/2019", "Em andamento", "NF-105612"},
                {"4351", "José do Ovo Mexido", "13/01/2018", "Em andamento", "NF-105612"}
            },
            new String [] {
                "Matricula", "Nome", "Data Retirada", "Situação", "Nota Fiscal"
            }
        ));
        jTableHistoricConf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableHistoricConfMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(jTableHistoricConf);

        jButConfNotaAlt.setBackground(new java.awt.Color(204, 204, 204));
        jButConfNotaAlt.setText("Encerrar");
        jButConfNotaAlt.setEnabled(false);
        jButConfNotaAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButConfNotaAltActionPerformed(evt);
            }
        });

        jButConfNotaImp.setBackground(new java.awt.Color(204, 204, 204));
        jButConfNotaImp.setText("Imprimir");
        jButConfNotaImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButConfNotaImpActionPerformed(evt);
            }
        });

        jCBoxNota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButConfNotaCancel.setBackground(new java.awt.Color(204, 204, 204));
        jButConfNotaCancel.setText("Em Progresso");
        jButConfNotaCancel.setEnabled(false);
        jButConfNotaCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButConfNotaCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanInvoiceConfLayout = new javax.swing.GroupLayout(jPanInvoiceConf);
        jPanInvoiceConf.setLayout(jPanInvoiceConfLayout);
        jPanInvoiceConfLayout.setHorizontalGroup(
            jPanInvoiceConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanInvoiceConfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanInvoiceConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                    .addGroup(jPanInvoiceConfLayout.createSequentialGroup()
                        .addComponent(jButConfNotaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButConfNotaCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButConfNotaImp, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBoxNota, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanInvoiceConfLayout.setVerticalGroup(
            jPanInvoiceConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanInvoiceConfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanInvoiceConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButConfNotaImp)
                    .addComponent(jCBoxNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButConfNotaAlt)
                    .addComponent(jButConfNotaCancel))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
        );

        jPanelTelas.add(jPanInvoiceConf, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanMenus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 644, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(200, 200, 200)
                    .addComponent(jPanelTelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanMenus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanelTelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtUniformEnrollmentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtUniformEnrollmentKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            removeEmployee();
            String enrollment = (jTxtUniformEnrollment.getText().equals("")) ? null
                    : jTxtUniformEnrollment.getText();
            LinkedList<Historic> historics = DbHistoric.getListHistoricBy(DbHistoric.EMPLOYEE, enrollment);
            showEmployee(enrollment);
            tableHistoric.updateTable(new LinkedList<>());
            if (historics != null) {
                showHistoric(historics.getFirst());
                tableHistoric.updateTable(historics);
                checkRetreatDate(historics.getFirst().getSituation().getDescription());
                comboBoxQuantitaty(0);
            } else {
                jButUniformeInsRet.setEnabled(true);
                updateComboBoxQuantity(0);
            }
        }
    }//GEN-LAST:event_jTxtUniformEnrollmentKeyReleased

    private void jButPecaInsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButPecaInsActionPerformed
        jFraInsPecas.setVisible(true);
        jButInsPecaOk.setEnabled(false);
        HashMap<Integer, String> mask = new HashMap();
        jCBoxInsPecaTipoTamanho.setModel(new DefaultComboBoxModel(SizeType.getAllDescription()));
        jTxtInsPecaValor.setDocument(new ValidateTextField(10, "[0-9]+(.|,)?([0-9]?){2}", mask));
    }//GEN-LAST:event_jButPecaInsActionPerformed

    private void jButPecaDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButPecaDesActionPerformed
        jButPecaDes.setEnabled(false);
        DbUniform.updateUniformDb(tableUniform.getSelectedRow(),
                "fim_vigencia", DbFormatter.toDateTimeDb(new Date()));
        tableUniform.updateTable(DbUniform.selectFromDb(DbUniform.ACTUAL));
    }//GEN-LAST:event_jButPecaDesActionPerformed

    private void jButInsPecaOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButInsPecaOkActionPerformed
        Uniform uniform = new Uniform();
        uniform.setDescreption(jTxtInsPecaDescricao.getText());
        uniform.setSizeType(SizeType.getSizeType(jCBoxInsPecaTipoTamanho.getSelectedItem().toString()));
        uniform.setValue(new BigDecimal(jTxtInsPecaValor.getText().replace(",", ".")));
        uniform.setStartValidity(new Date());
        DbUniform.insertUniformDb(uniform);
        tableUniform.updateTable(DbUniform.selectFromDb(DbUniform.ACTUAL));
    }//GEN-LAST:event_jButInsPecaOkActionPerformed

    private void jButTelaPeçasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButTelaPeçasActionPerformed
        guiUniform();
        tableUniform.updateTable(DbUniform.selectFromDb(DbUniform.ACTUAL));
    }//GEN-LAST:event_jButTelaPeçasActionPerformed

    private void jButTelaNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButTelaNotaActionPerformed
        guiInvoice();
        tableInvoice.updateTable(DbInvoice.selectFromDb(DbInvoice.ALL));
    }//GEN-LAST:event_jButTelaNotaActionPerformed

    private void jButTelaConfNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButTelaConfNotaActionPerformed
        guiInvoiceConf();
        tableHistoricConf.updateTable(DbHistoric.getListHistoricBy(DbHistoric.CONF_INVOICE));

    }//GEN-LAST:event_jButTelaConfNotaActionPerformed

    private void jButTelaUniformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButTelaUniformeActionPerformed
        guiUniformRetreat();
    }//GEN-LAST:event_jButTelaUniformeActionPerformed

    private void jButNotaInsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButNotaInsActionPerformed
        jFraInsNota.setVisible(true);
        jButInsNotaOk.setEnabled(false);
        HashMap<Integer, String> mask = new HashMap();
        mask.put(1, "/");
        jTxtInsNotaMesReferente.setDocument(new ValidateTextField(8, "[0-9]+(/)?([0-9]?){4}", mask));
    }//GEN-LAST:event_jButNotaInsActionPerformed

    private void jButInsNotaOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButInsNotaOkActionPerformed
        Invoice invoice = new Invoice();
        invoice.setNumberInvoice(jTxtInsNotaDescNota.getText());
        invoice.setReferentMonth(jTxtInsNotaMesReferente.getText());
        DbInvoice.insertInvoiceDb(invoice);
        tableInvoice.updateTable(DbInvoice.selectFromDb(DbInvoice.ALL + " ORDER BY id DESC"));
    }//GEN-LAST:event_jButInsNotaOkActionPerformed

    private void jButNotaInsPecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButNotaInsPecaActionPerformed
        jFraInsPecaNota.setVisible(true);
        jButInsPecaNotaOk.setEnabled(false);
        HashMap<Integer, String> mask = new HashMap();
        jTxtInsPecaNotaQtde.setDocument(new ValidateTextField(3, "[0-9]+", mask));
    }//GEN-LAST:event_jButNotaInsPecaActionPerformed

    private void jButNotaExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButNotaExcActionPerformed
        int option = JOptionPane.showConfirmDialog(rootPane,
                "Deseja realmente excluir a nota fiscal?", "Aviso!", JOptionPane.YES_NO_OPTION);
        if (option == 0) {
            DbInvoiceUniform.deleteInvoiceUniformDbByInvoice(tableInvoice.getSelectedRow());
            tableInvoiceUniform.updateTable(null);
            DbInvoice.deleteInvoiceDb(tableInvoice.getSelectedRow());
            tableInvoice.updateTable(DbInvoice.selectFromDb(DbInvoice.ALL + " ORDER BY id DESC"));

            jLabNotaValor.setText("-");
            jButNotaExc.setEnabled(false);
        }
    }//GEN-LAST:event_jButNotaExcActionPerformed

    private void jButInsPecaNotaOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButInsPecaNotaOkActionPerformed
        InvoiceUniform invoiceUniform = new InvoiceUniform();
        Uniform uniform = DbUniform.getUniformBy(DbUniform.DESCRIPTION, jCBoxInsPecaNota.getSelectedItem().toString());
        Invoice invoice = DbInvoice.getInvoiceBy(DbInvoice.ID, tableInvoice.getSelectedRow());

        invoiceUniform.setQuantity(Integer.parseInt(jTxtInsPecaNotaQtde.getText()));
        invoiceUniform.setSize(jCBoxInsPecaNotaTam.getSelectedItem().toString());
        invoiceUniform.setUniform(uniform);
        invoiceUniform.setValue(uniform.getValue().multiply(new BigDecimal(jTxtInsPecaNotaQtde.getText())));
        invoiceUniform.setInvoice(invoice);

        DbInvoiceUniform.insertInvoiceUniformDb(invoiceUniform);
        tableInvoiceUniform.updateTable(DbInvoiceUniform.selectFromDb(DbInvoiceUniform.INVOICE + invoice.getId()));
        updateTotalValue();
    }//GEN-LAST:event_jButInsPecaNotaOkActionPerformed

    private void jTableInvoiceMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableInvoiceMouseReleased
        jButNotaInsPeca.setEnabled(true);
        jButNotaExc.setEnabled(true);
        tableInvoiceUniform.updateTable(DbInvoiceUniform.selectFromDb(
                DbInvoiceUniform.INVOICE + tableInvoice.getSelectedRow()));
        updateTotalValue();
    }//GEN-LAST:event_jTableInvoiceMouseReleased

    private void jButNotaExcPecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButNotaExcPecaActionPerformed
        int option = JOptionPane.showConfirmDialog(rootPane,
                "Deseja realmente excluir a peça da nota fiscal?", "Aviso!", JOptionPane.YES_NO_OPTION);
        if (option == 0) {
            DbInvoiceUniform.deleteInvoiceUniformDb(tableInvoiceUniform.getSelectedRow());
            tableInvoiceUniform.updateTable(DbInvoiceUniform.selectFromDb(
                    DbInvoiceUniform.INVOICE + tableInvoice.getSelectedRow()));
            updateTotalValue();
            jButNotaExcPeca.setEnabled(false);
        }
    }//GEN-LAST:event_jButNotaExcPecaActionPerformed

    private void jButUniformeInsRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButUniformeInsRetActionPerformed
        Retreat retreat = new Retreat();
        retreat.setQuantity(Integer.parseInt(jCBoxUniformeQtde.getSelectedItem().toString()));
        retreat.setUniform(DbUniform.getUniformBy(DbUniform.DESCRIPTION, (String) jCBoxUniformeModelo.getSelectedItem()));
        retreat.setSize((String) jCBoxUniformeTamanho.getSelectedItem());
        tableRetreat.addElement(retreat);
        updateComboBoxQuantity(Retreat.sumAllQuantity(tableRetreat.getListRetreat()));
        jButUniformeRemRet.setEnabled(true);
        jButUniformCad.setEnabled(true);
    }//GEN-LAST:event_jButUniformeInsRetActionPerformed

    private void jButUniformeRemRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButUniformeRemRetActionPerformed
        tableRetreat.removeLastElement();
        int sumQtd = Retreat.sumAllQuantity(tableRetreat.getListRetreat());
        comboBoxQuantitaty(sumQtd);
        jButUniformeInsRet.setEnabled(true);
        if (sumQtd == 0) {
            jButUniformeRemRet.setEnabled(false);
            jButUniformCad.setEnabled(false);
        }
    }//GEN-LAST:event_jButUniformeRemRetActionPerformed

    private void jButUniformCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButUniformCadActionPerformed
        Historic historic = createHistoric();
        DbHistoric.insertHistoricDb(historic);
        tableHistoric.updateTable(DbHistoric.getListHistoricBy(DbHistoric.EMPLOYEE, jTxtUniformEnrollment.getText()));
        showHistoric(historic);

        int historicId = tableHistoric.getHistorics().getFirst().getId();
        LinkedList<Retreat> retreats = tableRetreat.getListRetreat();
        DbRetreat.insertRetreatsDB(retreats, historicId);

        comboBoxQuantitaty(0);
        tableHistoric.updateTable(DbHistoric.getListHistoricBy(DbHistoric.EMPLOYEE, jTxtUniformEnrollment.getText()));
        checkRetreatDate(historic.getSituation().getDescription());
        disableButtonsUniform();
        tableRetreat.cleanTable();
    }//GEN-LAST:event_jButUniformCadActionPerformed

    private void jButConfNotaAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButConfNotaAltActionPerformed
        LinkedList<Object> indexes = tableHistoricConf.getTableModel().getChangedIndexex();
        LinkedList<Object> values = tableHistoricConf.getTableModel().getChangedObjects();
        if (values.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "É necessário atribuir uma nota fiscal ao histórico!",
                    "Aviso!", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 0; i < indexes.size(); i++) {
                Historic historic = new Historic();
                historic.setClosedBy(Constantes.abreviaNome(Constantes.user_Nome));
                historic.setClosedDate(new Date());
                historic.setSituation(Situation.CLOSED);
                historic.setId(Integer.parseInt(indexes.get(i).toString()));
                historic.setInvoice(DbInvoice.getInvoiceBy(DbInvoice.DESCRIPTION, values.get(i).toString() + "'"));
                DbHistoric.updateHistoricCloseDb(historic);
            }
            tableHistoricConf.updateTable(DbHistoric.getListHistoricBy(DbHistoric.CONF_INVOICE));
        }
    }//GEN-LAST:event_jButConfNotaAltActionPerformed

    private void jButUniformCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButUniformCancelActionPerformed
        DbHistoric.updateHistoricDb(tableHistoric.getSelectedRow(), "situacao", Situation.CANCELED.getDescription());
        DbHistoric.updateHistoricDb(tableHistoric.getSelectedRow(), "cancelado_por", Constantes.abreviaNome(Constantes.user_Nome));
        LinkedList<Historic> historics = DbHistoric.getListHistoricBy(DbHistoric.EMPLOYEE, jTxtUniformEnrollment.getText());
        tableHistoric.updateTable(historics);
        showHistoric(historics.getFirst());
        checkRetreatDate(historics.getFirst().getSituation().getDescription());
        jButUniformCancel.setEnabled(false);
    }//GEN-LAST:event_jButUniformCancelActionPerformed

    private void jButConfNotaImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButConfNotaImpActionPerformed
        ReportInvoiceConf report = new ReportInvoiceConf(DbInvoice.getInvoiceBy(DbInvoice.DESCRIPTION,
                jCBoxNota.getSelectedItem().toString() + "'").getId().toString());
        report.show();
    }//GEN-LAST:event_jButConfNotaImpActionPerformed

    private void jButUniformPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButUniformPrintActionPerformed
        jButUniformPrint.setEnabled(false);
        Historic historic = DbHistoric.getHistoricBy(DbHistoric.ID, tableHistoric.getSelectedRow());
        ReportUniform report = new ReportUniform(tableHistoric.getSelectedRow(), jCBoxCompany.getSelectedItem().toString());
        report.setDepartament(jLabUniformDepartament.getText());
        report.setEnrollment(jTxtUniformEnrollment.getText());
        report.setFunction(jLabUniformFunction.getText());
        report.setHireDate(jLabUniformHireDate.getText());
        report.setName(jLabUniformName.getText());
        report.setNote(historic.getNote());
        report.show();
    }//GEN-LAST:event_jButUniformPrintActionPerformed

    private void jTableInvoiceUniformMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableInvoiceUniformMouseReleased
        jButNotaExcPeca.setEnabled(true);
    }//GEN-LAST:event_jTableInvoiceUniformMouseReleased

    private void jButRetiradaConfirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButRetiradaConfirmaActionPerformed
        LinkedList<Retreat> changeds = tableRetreatChanged.getRetreats();
        LinkedList<Retreat> retreats = DbRetreat.getListRetreatBy(DbRetreat.HISTORIC, tableHistoricConf.getSelectedRow());
        deleteRetreatsChanged(changeds, retreats);
        for (Retreat changed : changeds) {
            if (changed.getId() < 0) {
                DbRetreat.insertRetreatDb(changed, tableHistoricConf.getSelectedRow());
            }
        }
        DbHistoric.updateHistoricDb(tableHistoricConf.getSelectedRow(), "alterado_por", Constantes.abreviaNome(Constantes.user_Nome));
        DbHistoric.updateHistoricDb(tableHistoricConf.getSelectedRow(), "data_alterado", DbFormatter.toDateTimeDb(new Date()));
        jFraAlteraRetirada.setVisible(false);
    }//GEN-LAST:event_jButRetiradaConfirmaActionPerformed

    private void jButRetiradaInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButRetiradaInserirActionPerformed
        int max = Retreat.sumAllQuantity(DbRetreat.getListRetreatBy(DbRetreat.HISTORIC, tableHistoricConf.getSelectedRow()));
        int qtd = Retreat.sumAllQuantity(tableRetreatChanged.getRetreats());
        jFraInsRetirada.setVisible(true);
        jCBoxRetiradaQtde.removeAllItems();
        comboBoxUniformModel(jCBoxRetiradaPeca);
        comboBoxUniformSize(jCBoxRetiradaPeca, jCBoxRetiradaTamanho);
        for (int i = 1; i <= max - qtd; i++) {
            jCBoxRetiradaQtde.addItem(String.valueOf(i));
        }
    }//GEN-LAST:event_jButRetiradaInserirActionPerformed

    private void jButRetiradaRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButRetiradaRemoverActionPerformed
        tableRetreatChanged.removerElement(tableRetreatChanged.getSelectedRow());
        jButRetiradaInserir.setEnabled(true);
        jButRetiradaConfirma.setEnabled(false);
        if (tableRetreatChanged.getRetreats().isEmpty()) {
            jButRetiradaRemover.setEnabled(false);
        }
    }//GEN-LAST:event_jButRetiradaRemoverActionPerformed

    private void jTableHistoricMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableHistoricMouseClicked
        Historic historic = DbHistoric.getHistoricBy(DbHistoric.ID, tableHistoric.getSelectedRow());
        jButUniformPrint.setEnabled(true);
        if (historic.getSituation().equals(Situation.IN_PROGRESS)) {
            jButUniformCancel.setEnabled(true);
        }
        if (evt.getClickCount() == 2) {
            jFraResumo.setVisible(true);
            initTableResumo(historic.getNote());
        }
    }//GEN-LAST:event_jTableHistoricMouseClicked

    private void jButRetiradaOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButRetiradaOKActionPerformed
        Retreat retreat = new Retreat();
        retreat.setId(--idUniform);
        retreat.setQuantity(Integer.parseInt(jCBoxRetiradaQtde.getSelectedItem().toString()));
        retreat.setSize(jCBoxRetiradaTamanho.getSelectedItem().toString());
        retreat.setUniform(DbUniform.getUniformBy(DbUniform.DESCRIPTION, jCBoxRetiradaPeca.getSelectedItem().toString()));

        tableRetreatChanged.addElement(retreat);
        jFraInsRetirada.setVisible(false);
        int max = Retreat.sumAllQuantity(DbRetreat.getListRetreatBy(DbRetreat.HISTORIC, tableHistoricConf.getSelectedRow()));
        int qtd = Retreat.sumAllQuantity(tableRetreatChanged.getRetreats());

        if (max == qtd) {
            jButRetiradaInserir.setEnabled(false);
            jButRetiradaConfirma.setEnabled(true);
        }
    }//GEN-LAST:event_jButRetiradaOKActionPerformed

    private void jTableUniformMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableUniformMouseClicked
        jButPecaDes.setEnabled(true);
    }//GEN-LAST:event_jTableUniformMouseClicked

    private void jButConfNotaCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButConfNotaCancelActionPerformed
        Historic historic = new Historic();
        historic.setId(Integer.parseInt(tableHistoricConf.getSelectedRow()));
        historic.setChangedDate(new Date());
        historic.setChangedBy(Constantes.abreviaNome(Constantes.user_Nome));
        historic.setSituation(Situation.IN_PROGRESS);

        DbHistoric.updadteHistoricChangeDb(historic);
        jButConfNotaCancel.setEnabled(false);
        tableHistoricConf.updateTable(DbHistoric.getListHistoricBy(DbHistoric.CONF_INVOICE));
    }//GEN-LAST:event_jButConfNotaCancelActionPerformed

    private void jTableHistoricConfMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableHistoricConfMouseReleased
        jButConfNotaAlt.setEnabled(true);
        jButConfNotaCancel.setEnabled(true);
        if (evt.getClickCount() == 2) {
            jFraAlteraRetirada.setVisible(true);
            tableRetreatChanged.updateTable(DbRetreat.getListRetreatBy(DbRetreat.HISTORIC, tableHistoricConf.getSelectedRow()));
            jButRetiradaConfirma.setEnabled(false);
            jButRetiradaInserir.setEnabled(false);
            jButRetiradaRemover.setEnabled(false);
        }
    }//GEN-LAST:event_jTableHistoricConfMouseReleased

    private void jTableRetreatChangedMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRetreatChangedMouseReleased
        jButRetiradaRemover.setEnabled(true);
    }//GEN-LAST:event_jTableRetreatChangedMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButConfNotaAlt;
    private javax.swing.JButton jButConfNotaCancel;
    private javax.swing.JButton jButConfNotaImp;
    private javax.swing.JButton jButInsNotaOk;
    private javax.swing.JButton jButInsPecaNotaOk;
    private javax.swing.JButton jButInsPecaOk;
    private javax.swing.JButton jButNotaExc;
    private javax.swing.JButton jButNotaExcPeca;
    private javax.swing.JButton jButNotaIns;
    private javax.swing.JButton jButNotaInsPeca;
    private javax.swing.JButton jButPecaDes;
    private javax.swing.JButton jButPecaIns;
    private javax.swing.JButton jButRetiradaConfirma;
    private javax.swing.JButton jButRetiradaInserir;
    private javax.swing.JButton jButRetiradaOK;
    private javax.swing.JButton jButRetiradaRemover;
    private javax.swing.JButton jButTelaConfNota;
    private javax.swing.JButton jButTelaNota;
    private javax.swing.JButton jButTelaPeças;
    private javax.swing.JButton jButTelaUniforme;
    private javax.swing.JButton jButUniformCad;
    private javax.swing.JButton jButUniformCancel;
    private javax.swing.JButton jButUniformPrint;
    private javax.swing.JButton jButUniformeInsRet;
    private javax.swing.JButton jButUniformeRemRet;
    private javax.swing.JComboBox<String> jCBoxCompany;
    private javax.swing.JComboBox<String> jCBoxInsPecaNota;
    private javax.swing.JComboBox<String> jCBoxInsPecaNotaTam;
    private javax.swing.JComboBox<String> jCBoxInsPecaTipoTamanho;
    private javax.swing.JComboBox<String> jCBoxNota;
    private javax.swing.JComboBox<String> jCBoxRetiradaPeca;
    private javax.swing.JComboBox<String> jCBoxRetiradaQtde;
    private javax.swing.JComboBox<String> jCBoxRetiradaTamanho;
    private javax.swing.JComboBox<String> jCBoxUniformeModelo;
    private javax.swing.JComboBox<String> jCBoxUniformeQtde;
    private javax.swing.JComboBox<String> jCBoxUniformeTamanho;
    private javax.swing.JFrame jFraAlteraRetirada;
    private javax.swing.JFrame jFraInsNota;
    private javax.swing.JFrame jFraInsPecaNota;
    private javax.swing.JFrame jFraInsPecas;
    private javax.swing.JFrame jFraInsRetirada;
    private javax.swing.JFrame jFraResumo;
    private javax.swing.JLabel jLab16;
    private javax.swing.JLabel jLab21;
    private javax.swing.JLabel jLab24;
    private javax.swing.JLabel jLabDesTipoTamanho;
    private javax.swing.JLabel jLabDesValor;
    private javax.swing.JLabel jLabDescricao;
    private javax.swing.JLabel jLabNotaValor;
    private javax.swing.JLabel jLabUniformCompany;
    private javax.swing.JLabel jLabUniformDepartament;
    private javax.swing.JLabel jLabUniformFunction;
    private javax.swing.JLabel jLabUniformHireDate;
    private javax.swing.JLabel jLabUniformName;
    private javax.swing.JLabel jLabUniformNextDate;
    private javax.swing.JLabel jLabUniformPhoto;
    private javax.swing.JLabel jLabUniformRetreatDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanInvoice;
    private javax.swing.JPanel jPanInvoiceConf;
    private javax.swing.JPanel jPanMenus;
    private javax.swing.JPanel jPanUniform;
    private javax.swing.JPanel jPanUniformRetreat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelTelas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTableHistoric;
    private javax.swing.JTable jTableHistoricConf;
    private javax.swing.JTable jTableInvoice;
    private javax.swing.JTable jTableInvoiceUniform;
    private javax.swing.JTable jTableRetreat;
    private javax.swing.JTable jTableRetreatChanged;
    private javax.swing.JTable jTableRetreatResum;
    private javax.swing.JTable jTableUniform;
    private javax.swing.JTextField jTxtInsNotaDescNota;
    private javax.swing.JTextField jTxtInsNotaMesReferente;
    private javax.swing.JTextField jTxtInsPecaDescricao;
    private javax.swing.JTextField jTxtInsPecaNotaQtde;
    private javax.swing.JTextField jTxtInsPecaValor;
    private javax.swing.JTextArea jTxtResumoObs;
    private javax.swing.JTextArea jTxtRetiradaObs;
    private javax.swing.JTextField jTxtUniformEnrollment;
    private javax.swing.JTextArea jTxtUniformeObservacao;
    // End of variables declaration//GEN-END:variables

}

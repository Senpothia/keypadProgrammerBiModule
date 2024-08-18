/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keypadprogrammer;

import java.awt.Color;
import java.awt.Font;
import java.awt.color.ColorSpace;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;

/**
 *
 * @author Michel
 */
public class Interface extends javax.swing.JFrame implements Observer {

    private File programmerLocation = null;
    private File binaryLocation = null;
    private File BLELocation = null;
    private String progLocation = null;
    private String hexLocation = null;
    private String bleLocation = null;
    private boolean envVariable = false;
    private boolean confirmationParams = false;

    Connecteur connecteur = getConnecteur();            // gére la connexion RS232

    private int baudeRate = 9600;
    private int numDatabits = 8;
    private int parity = 0;
    private int stopBits = 1;
    private int newReadTimeout = 1000;
    private int newWriteTimeout = 0;

    private boolean connexionRS232Active = false;       // état de la connexion RS-232

    private boolean testActif = false;
    private boolean testActifSlave = false;
    private boolean programmationActive = false;
    private boolean auto = true;
    private boolean AttenteReponseOperateur = false;
    private boolean AttenteReponseOperateurSlave = false;

    public static Initializer initializer = new Initializer();  // Charge les propriétés du fichier properties contenant les paramètres de programmation
    public static Initialisation initialisation;          // Centralise les données rapportées par l'initializer

    private List<String> listePortString = new ArrayList<>();
    private List<JRadioButtonMenuItem> listePorts = new ArrayList<JRadioButtonMenuItem>();

    private ProgController progController = new ProgController();

    /**
     * Creates new form Interface
     */
    public Interface() throws IOException {

        initComponents();
        statutRs232.setBackground(Color.RED);
        statutRs232.setForeground(Color.RED);
        statutRs232.setOpaque(true);

        statutPGRM.setBackground(Color.RED);
        statutPGRM.setForeground(Color.RED);
        statutPGRM.setOpaque(true);

        this.getContentPane().setBackground(new Color(83, 141, 163));
        voyant.setBackground(new Color(204, 136, 53));
        voyant.setForeground(Color.GRAY);
        voyant.setOpaque(true);
        console.setBackground(new Color(247, 242, 208));
        console.setOpaque(true);
        console.setForeground(Color.red);
        console.setFont(new Font("Serif", Font.PLAIN, 20));

        this.getContentPane().setBackground(new Color(83, 141, 163));
        voyantSlave.setBackground(new Color(204, 136, 53));
        voyantSlave.setForeground(Color.GRAY);
        voyantSlave.setOpaque(true);
        consoleSlave.setBackground(new Color(247, 242, 208));
        consoleSlave.setOpaque(true);
        consoleSlave.setForeground(Color.red);
        consoleSlave.setFont(new Font("Serif", Font.PLAIN, 20));

        paramsWin.getContentPane().setBackground(new Color(83, 141, 163));
        progLocLabel.setBackground(new Color(247, 242, 208));
        binaryLocLabel.setBackground(new Color(247, 242, 208));
        bleLocLabel.setBackground(new Color(247, 242, 208));
        progLocLabel.setOpaque(true);
        binaryLocLabel.setOpaque(true);
        bleLocLabel.setOpaque(true);
        inhibBtn();
        inhibBtnSlave();

        aide.getContentPane().setBackground(new Color(247, 242, 208));

        rechercherPortsComms();

        initialisation = initializer.getInit();
        if (initialisation.getBinaryLocation().equals("na")) {

            System.out.println("BinaryLocation = " + initialisation.getBinaryLocation());

        } else {

            System.out.println("BinaryLocation = " + initialisation.getBinaryLocation());
            hexLocation = initialisation.getBinaryLocation();
        }

        if (initialisation.getBleLocation().equals("na")) {

            System.out.println("BleLocation = " + initialisation.getBleLocation());

        } else {

            System.out.println("BleLocation = " + initialisation.getBleLocation());
            bleLocation = initialisation.getBleLocation();
        }

        if (initialisation.getProgrammerDirectory().equals("na")) {

            System.out.println("programmerDirectory = " + initialisation.getProgrammerDirectory());

        } else {

            System.out.println("programmerDirectory = " + initialisation.getProgrammerDirectory());
            progLocation = initialisation.getProgrammerDirectory();

        }

        if (initialisation.getVarEnv().equals("na")) {

            System.out.println("varEnv = " + initialisation.getVarEnv());

        } else {

            System.out.println("varEnv = " + initialisation.getVarEnv());
            if (initialisation.getVarEnv().equals("true")) {

                envVariable = true;
            }

            if (initialisation.getVarEnv().equals("false")) {

                envVariable = false;
            }

        }

        int dirCreation = progController.createLogFolder(Constants.LOG_DIRECTORY);
        if (dirCreation != 1) {

            montrerError("Echec à la création du repertoire de logs", "Erreur d'initialisation");
            System.exit(0);

        }

        progBarre.setStringPainted(true);
        progBarre.setForeground(Color.blue);
        progBarre.setOpaque(true);
        progBarre.setVisible(true);
        testBarre.setStringPainted(true);
        testBarre.setForeground(Color.blue);
        testBarre.setOpaque(true);
        testBarre.setVisible(true);

        progBarreSlave.setStringPainted(true);
        progBarreSlave.setForeground(Color.blue);
        progBarreSlave.setOpaque(true);
        progBarreSlave.setVisible(true);
        testBarreSlave.setStringPainted(true);
        testBarreSlave.setForeground(Color.blue);
        testBarreSlave.setOpaque(true);
        testBarreSlave.setVisible(true);

        testParamsProg();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        programmerLoc = new javax.swing.JFileChooser();
        bleLoc = new javax.swing.JFileChooser();
        btnSelectLocationProg = new javax.swing.JButton();
        btnSelectBinaryLoc = new javax.swing.JButton();
        binaryLoc = new javax.swing.JFileChooser();
        groupPorts = new javax.swing.ButtonGroup();
        groupBits = new javax.swing.ButtonGroup();
        groupBaud = new javax.swing.ButtonGroup();
        groupParity = new javax.swing.ButtonGroup();
        groupStop = new javax.swing.ButtonGroup();
        paramsWin = new javax.swing.JFrame();
        titreParamsWin = new javax.swing.JLabel();
        progLocLabel = new javax.swing.JLabel();
        binaryLocLabel = new javax.swing.JLabel();
        btnFermerParams = new javax.swing.JButton();
        titreLabProg = new javax.swing.JLabel();
        EnvVarBox = new javax.swing.JCheckBox();
        titreLabHex = new javax.swing.JLabel();
        titreLabBLE = new javax.swing.JLabel();
        bleLocLabel = new javax.swing.JLabel();
        aide = new javax.swing.JFrame();
        btnFermerAide = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jFrame1 = new javax.swing.JFrame();
        titre = new javax.swing.JLabel();
        btnProg = new javax.swing.JButton();
        btnEffacer = new javax.swing.JButton();
        voyant = new javax.swing.JLabel();
        statutRs232 = new javax.swing.JLabel();
        console = new javax.swing.JLabel();
        btnTester = new javax.swing.JButton();
        version = new javax.swing.JLabel();
        StatutRS232Lab = new javax.swing.JLabel();
        statutPRGLabel = new javax.swing.JLabel();
        statutPGRM = new javax.swing.JLabel();
        progBarre = new javax.swing.JProgressBar();
        testBarre = new javax.swing.JProgressBar();
        btnLancer = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        btnNOK = new javax.swing.JButton();
        btnACQ = new javax.swing.JButton();
        voyantSlave = new javax.swing.JLabel();
        progBarreSlave = new javax.swing.JProgressBar();
        testBarreSlave = new javax.swing.JProgressBar();
        consoleSlave = new javax.swing.JLabel();
        btnLancerSlave = new javax.swing.JButton();
        btnOKSlave = new javax.swing.JButton();
        btnNOKSlave = new javax.swing.JButton();
        btnACQSlave = new javax.swing.JButton();
        btnProgSlave = new javax.swing.JButton();
        btnEffacerSlave = new javax.swing.JButton();
        btnTesterSlave = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        moduleLab = new javax.swing.JLabel();
        moduleLabSlave = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuParametres = new javax.swing.JMenu();
        menuVoir = new javax.swing.JMenuItem();
        paramsProg = new javax.swing.JMenuItem();
        paramBinaire = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        btnFermer = new javax.swing.JMenuItem();
        menuConnexion = new javax.swing.JMenu();
        menuPort = new javax.swing.JMenu();
        menuBaud = new javax.swing.JMenu();
        baud9600 = new javax.swing.JRadioButtonMenuItem();
        baud19200 = new javax.swing.JRadioButtonMenuItem();
        baud38400 = new javax.swing.JRadioButtonMenuItem();
        baud115200 = new javax.swing.JRadioButtonMenuItem();
        menuBits = new javax.swing.JMenu();
        bits6 = new javax.swing.JRadioButtonMenuItem();
        bits7 = new javax.swing.JRadioButtonMenuItem();
        bits8 = new javax.swing.JRadioButtonMenuItem();
        bits9 = new javax.swing.JRadioButtonMenuItem();
        menuStop = new javax.swing.JMenu();
        stop1 = new javax.swing.JRadioButtonMenuItem();
        stop2 = new javax.swing.JRadioButtonMenuItem();
        menuParity = new javax.swing.JMenu();
        parityNone = new javax.swing.JRadioButtonMenuItem();
        parityOdd = new javax.swing.JRadioButtonMenuItem();
        parityEven = new javax.swing.JRadioButtonMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        btnConnexion = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        btnDeconnexion = new javax.swing.JMenuItem();
        menuAide = new javax.swing.JMenu();
        voirAide = new javax.swing.JMenuItem();

        programmerLoc.setFileFilter(null);
        programmerLoc.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        btnSelectLocationProg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSelectLocationProg.setText("Programmer location");
        btnSelectLocationProg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectLocationProgActionPerformed(evt);
            }
        });

        btnSelectBinaryLoc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSelectBinaryLoc.setText("Binary location");
        btnSelectBinaryLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectBinaryLocActionPerformed(evt);
            }
        });

        paramsWin.setTitle("Programmateur keypad - Paramètres système");
        paramsWin.setMinimumSize(new java.awt.Dimension(1321, 481));

        titreParamsWin.setBackground(new java.awt.Color(153, 153, 255));
        titreParamsWin.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titreParamsWin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titreParamsWin.setText("PARAMETRES SYSTEMES");
        titreParamsWin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        progLocLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        progLocLabel.setForeground(new java.awt.Color(255, 0, 0));
        progLocLabel.setText("jLabel2");
        progLocLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        binaryLocLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        binaryLocLabel.setForeground(new java.awt.Color(255, 0, 51));
        binaryLocLabel.setText("jLabel3");
        binaryLocLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnFermerParams.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFermerParams.setForeground(new java.awt.Color(255, 0, 51));
        btnFermerParams.setText("Fermer");
        btnFermerParams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFermerParamsActionPerformed(evt);
            }
        });

        titreLabProg.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        titreLabProg.setText("Répertoire programmateur STM");

        EnvVarBox.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        EnvVarBox.setText("Variable d'environnement");
        EnvVarBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                EnvVarBoxStateChanged(evt);
            }
        });
        EnvVarBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnvVarBoxActionPerformed(evt);
            }
        });

        titreLabHex.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        titreLabHex.setText("Fichier binaire");

        titreLabBLE.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        titreLabBLE.setText("Fichier BLE");

        bleLocLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bleLocLabel.setForeground(new java.awt.Color(255, 0, 51));
        bleLocLabel.setText("Jlabel");
        bleLocLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout paramsWinLayout = new javax.swing.GroupLayout(paramsWin.getContentPane());
        paramsWin.getContentPane().setLayout(paramsWinLayout);
        paramsWinLayout.setHorizontalGroup(
            paramsWinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paramsWinLayout.createSequentialGroup()
                .addGroup(paramsWinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paramsWinLayout.createSequentialGroup()
                        .addGroup(paramsWinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paramsWinLayout.createSequentialGroup()
                                .addGap(392, 392, 392)
                                .addComponent(titreParamsWin, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(paramsWinLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titreLabHex, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(paramsWinLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titreLabBLE, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 551, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paramsWinLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFermerParams)))
                .addGap(56, 56, 56))
            .addGroup(paramsWinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paramsWinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paramsWinLayout.createSequentialGroup()
                        .addGroup(paramsWinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EnvVarBox)
                            .addComponent(titreLabProg, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paramsWinLayout.createSequentialGroup()
                        .addGroup(paramsWinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(binaryLocLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bleLocLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(progLocLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        paramsWinLayout.setVerticalGroup(
            paramsWinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paramsWinLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(titreParamsWin)
                .addGap(16, 16, 16)
                .addComponent(titreLabProg)
                .addGap(18, 18, 18)
                .addComponent(EnvVarBox)
                .addGap(18, 18, 18)
                .addComponent(progLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(titreLabHex)
                .addGap(18, 18, 18)
                .addComponent(binaryLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(titreLabBLE)
                .addGap(18, 18, 18)
                .addComponent(bleLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(btnFermerParams)
                .addGap(21, 21, 21))
        );

        aide.setTitle("Programmateur keypad - Aide");
        aide.setMinimumSize(new java.awt.Dimension(900, 900));

        btnFermerAide.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFermerAide.setForeground(new java.awt.Color(255, 0, 0));
        btnFermerAide.setText("Fermer");
        btnFermerAide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFermerAideActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("AIDE DU PROGRAMMATEUR GALEO");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("1- PARAMETRES");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("2- FONCTIONNALITES DE PROGRAMMATION");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("4- TESTS FONCTIONNELS");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("3- MATERIEL ET CONNEXION");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Pour que le système puisse fonctionner, il est nécessaire que:\n\n- le programmateur STM32CubeProgrammer soit installer sur votre poste de travail. Il est \ntéléchargeable à partir du site de ST Microelectronics. \n- que le version 8 de Java soit installer sur votre poste\n\nEnsuite, il est nécessaire de spécifier le repertoire d'installation du programmateur. Vous devrez identifier le repertoire bin dans \nce répertoire d'installation. Pour plus de confort, il est recommandé dedéfinir cette emplacement des les variables d'environnement de \nvotre système.\n\nPour finir, il vous sera aussi nécessaire de préciser l'emplacement de votre fichier binaire (extension .hex).\n\nCes paramètres sont indispenables au foncionnement du programamteur GALEO. Il est possible de les définir à partir du menu \nparamètres de l'application.\n\n");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout aideLayout = new javax.swing.GroupLayout(aide.getContentPane());
        aide.getContentPane().setLayout(aideLayout);
        aideLayout.setHorizontalGroup(
            aideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aideLayout.createSequentialGroup()
                .addContainerGap(1118, Short.MAX_VALUE)
                .addComponent(btnFermerAide)
                .addGap(22, 22, 22))
            .addGroup(aideLayout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(aideLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(aideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        aideLayout.setVerticalGroup(
            aideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aideLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addGap(71, 71, 71)
                .addComponent(jLabel4)
                .addGap(45, 45, 45)
                .addComponent(btnFermerAide)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Programmateur keypad");

        titre.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titre.setText("PROGRAMMATION & TEST GALEO");
        titre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnProg.setBackground(new java.awt.Color(255, 255, 255));
        btnProg.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnProg.setForeground(new java.awt.Color(51, 153, 0));
        btnProg.setText("PROGRAMMER");
        btnProg.setBorderPainted(false);
        btnProg.setContentAreaFilled(false);
        btnProg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgActionPerformed(evt);
            }
        });

        btnEffacer.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnEffacer.setForeground(new java.awt.Color(255, 51, 51));
        btnEffacer.setText("EFFACER");
        btnEffacer.setBorderPainted(false);
        btnEffacer.setContentAreaFilled(false);
        btnEffacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEffacerActionPerformed(evt);
            }
        });

        voyant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        statutRs232.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statutRs232.setText("rs-232");

        console.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        console.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        console.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        console.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnTester.setBackground(new java.awt.Color(255, 255, 255));
        btnTester.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTester.setForeground(new java.awt.Color(51, 0, 255));
        btnTester.setText("TESTER");
        btnTester.setBorderPainted(false);
        btnTester.setContentAreaFilled(false);
        btnTester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTesterActionPerformed(evt);
            }
        });

        version.setText("V3.0");

        StatutRS232Lab.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        StatutRS232Lab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        StatutRS232Lab.setText("RS-232");

        statutPRGLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        statutPRGLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statutPRGLabel.setText("PGRM");

        statutPGRM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statutPGRM.setText("prgm");

        progBarre.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        progBarre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        progBarre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        progBarre.setOpaque(true);
        progBarre.setStringPainted(true);

        testBarre.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        testBarre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        testBarre.setStringPainted(true);

        btnLancer.setBackground(new java.awt.Color(255, 255, 255));
        btnLancer.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLancer.setForeground(new java.awt.Color(51, 153, 0));
        btnLancer.setText("LANCER");
        btnLancer.setBorderPainted(false);
        btnLancer.setContentAreaFilled(false);
        btnLancer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLancerActionPerformed(evt);
            }
        });

        btnOK.setBackground(new java.awt.Color(255, 255, 255));
        btnOK.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnOK.setForeground(new java.awt.Color(51, 0, 255));
        btnOK.setText("OK");
        btnOK.setBorderPainted(false);
        btnOK.setContentAreaFilled(false);
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnNOK.setBackground(new java.awt.Color(255, 255, 255));
        btnNOK.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnNOK.setForeground(new java.awt.Color(255, 0, 0));
        btnNOK.setText("NOK");
        btnNOK.setBorderPainted(false);
        btnNOK.setContentAreaFilled(false);
        btnNOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNOKActionPerformed(evt);
            }
        });

        btnACQ.setBackground(new java.awt.Color(255, 255, 255));
        btnACQ.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnACQ.setForeground(new java.awt.Color(51, 0, 255));
        btnACQ.setText("ACQ");
        btnACQ.setBorderPainted(false);
        btnACQ.setContentAreaFilled(false);
        btnACQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnACQActionPerformed(evt);
            }
        });

        voyantSlave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        progBarreSlave.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        progBarreSlave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        progBarreSlave.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        progBarreSlave.setOpaque(true);
        progBarreSlave.setStringPainted(true);

        testBarreSlave.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        testBarreSlave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        testBarreSlave.setStringPainted(true);

        consoleSlave.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        consoleSlave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        consoleSlave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        consoleSlave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnLancerSlave.setBackground(new java.awt.Color(255, 255, 255));
        btnLancerSlave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLancerSlave.setForeground(new java.awt.Color(51, 153, 0));
        btnLancerSlave.setText("LANCER");
        btnLancerSlave.setBorderPainted(false);
        btnLancerSlave.setContentAreaFilled(false);
        btnLancerSlave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLancerSlaveActionPerformed(evt);
            }
        });

        btnOKSlave.setBackground(new java.awt.Color(255, 255, 255));
        btnOKSlave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnOKSlave.setForeground(new java.awt.Color(51, 0, 255));
        btnOKSlave.setText("OK");
        btnOKSlave.setBorderPainted(false);
        btnOKSlave.setContentAreaFilled(false);
        btnOKSlave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKSlaveActionPerformed(evt);
            }
        });

        btnNOKSlave.setBackground(new java.awt.Color(255, 255, 255));
        btnNOKSlave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnNOKSlave.setForeground(new java.awt.Color(255, 0, 0));
        btnNOKSlave.setText("NOK");
        btnNOKSlave.setBorderPainted(false);
        btnNOKSlave.setContentAreaFilled(false);
        btnNOKSlave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNOKSlaveActionPerformed(evt);
            }
        });

        btnACQSlave.setBackground(new java.awt.Color(255, 255, 255));
        btnACQSlave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnACQSlave.setForeground(new java.awt.Color(51, 0, 255));
        btnACQSlave.setText("ACQ");
        btnACQSlave.setBorderPainted(false);
        btnACQSlave.setContentAreaFilled(false);
        btnACQSlave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnACQSlaveActionPerformed(evt);
            }
        });

        btnProgSlave.setBackground(new java.awt.Color(255, 255, 255));
        btnProgSlave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnProgSlave.setForeground(new java.awt.Color(51, 153, 0));
        btnProgSlave.setText("PROGRAMMER");
        btnProgSlave.setBorderPainted(false);
        btnProgSlave.setContentAreaFilled(false);
        btnProgSlave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgSlaveActionPerformed(evt);
            }
        });

        btnEffacerSlave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnEffacerSlave.setForeground(new java.awt.Color(255, 51, 51));
        btnEffacerSlave.setText("EFFACER");
        btnEffacerSlave.setBorderPainted(false);
        btnEffacerSlave.setContentAreaFilled(false);
        btnEffacerSlave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEffacerSlaveActionPerformed(evt);
            }
        });

        btnTesterSlave.setBackground(new java.awt.Color(255, 255, 255));
        btnTesterSlave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTesterSlave.setForeground(new java.awt.Color(51, 0, 255));
        btnTesterSlave.setText("TESTER");
        btnTesterSlave.setBorderPainted(false);
        btnTesterSlave.setContentAreaFilled(false);
        btnTesterSlave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTesterSlaveActionPerformed(evt);
            }
        });

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        moduleLab.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        moduleLab.setText("Module 1");

        moduleLabSlave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        moduleLabSlave.setText("Module 2");

        menuParametres.setText("Paramètres");
        menuParametres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuParametresActionPerformed(evt);
            }
        });

        menuVoir.setText("Voir");
        menuVoir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVoirActionPerformed(evt);
            }
        });
        menuParametres.add(menuVoir);

        paramsProg.setText("Programmateur");
        paramsProg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paramsProgActionPerformed(evt);
            }
        });
        menuParametres.add(paramsProg);

        paramBinaire.setText("Binaire");
        paramBinaire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paramBinaireActionPerformed(evt);
            }
        });
        menuParametres.add(paramBinaire);

        jMenuItem1.setText("BLE");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuParametres.add(jMenuItem1);
        menuParametres.add(jSeparator1);

        btnFermer.setText("Fermer");
        btnFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFermerActionPerformed(evt);
            }
        });
        menuParametres.add(btnFermer);

        jMenuBar1.add(menuParametres);

        menuConnexion.setText("Communication");
        menuConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConnexionActionPerformed(evt);
            }
        });

        menuPort.setText("Ports");
        menuPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPortActionPerformed(evt);
            }
        });
        menuConnexion.add(menuPort);

        menuBaud.setText("Baud");

        groupBaud.add(baud9600);
        baud9600.setSelected(true);
        baud9600.setText("9600");
        menuBaud.add(baud9600);

        groupBaud.add(baud19200);
        baud19200.setText("19200");
        menuBaud.add(baud19200);

        groupBaud.add(baud38400);
        baud38400.setText("38400");
        menuBaud.add(baud38400);

        groupBaud.add(baud115200);
        baud115200.setText("115200");
        menuBaud.add(baud115200);

        menuConnexion.add(menuBaud);

        menuBits.setText("Bits");

        groupBits.add(bits6);
        bits6.setSelected(true);
        bits6.setText("6");
        menuBits.add(bits6);

        groupBits.add(bits7);
        bits7.setText("7");
        menuBits.add(bits7);

        groupBits.add(bits8);
        bits8.setText("8");
        menuBits.add(bits8);

        groupBits.add(bits9);
        bits9.setText("9");
        menuBits.add(bits9);

        menuConnexion.add(menuBits);

        menuStop.setText("Stop");

        groupStop.add(stop1);
        stop1.setSelected(true);
        stop1.setText("1");
        menuStop.add(stop1);

        groupStop.add(stop2);
        stop2.setText("2");
        menuStop.add(stop2);

        menuConnexion.add(menuStop);

        menuParity.setText("Parity");

        groupParity.add(parityNone);
        parityNone.setSelected(true);
        parityNone.setText("None");
        menuParity.add(parityNone);

        groupParity.add(parityOdd);
        parityOdd.setText("Odd");
        menuParity.add(parityOdd);

        groupParity.add(parityEven);
        parityEven.setText("Even");
        menuParity.add(parityEven);

        menuConnexion.add(menuParity);
        menuConnexion.add(jSeparator2);

        btnConnexion.setText("Connexion");
        btnConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnexionActionPerformed(evt);
            }
        });
        menuConnexion.add(btnConnexion);
        menuConnexion.add(jSeparator3);

        btnDeconnexion.setText("Déconnexion");
        btnDeconnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeconnexionActionPerformed(evt);
            }
        });
        menuConnexion.add(btnDeconnexion);

        jMenuBar1.add(menuConnexion);

        menuAide.setText("Autres");
        menuAide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAideActionPerformed(evt);
            }
        });

        voirAide.setText("Aide");
        voirAide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voirAideActionPerformed(evt);
            }
        });
        menuAide.add(voirAide);

        jMenuBar1.add(menuAide);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(618, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(titre, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(467, 467, 467)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(StatutRS232Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(statutRs232, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(statutPRGLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(consoleSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statutPGRM, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(version)
                        .addGap(26, 26, 26))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(voyant, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(339, 339, 339))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(testBarre, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(46, 46, 46)
                                        .addComponent(btnEffacer, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(62, 62, 62)
                                        .addComponent(btnProg, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(btnTester, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnNOK, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(52, 52, 52)
                                        .addComponent(btnLancer, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45)
                                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(btnACQ, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(progBarre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(console, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(76, 76, 76))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(moduleLab)
                        .addGap(386, 386, 386)))
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(btnEffacerSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(btnProgSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(btnTesterSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNOKSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(btnLancerSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(btnOKSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(btnACQSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(progBarreSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(testBarreSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(73, 73, 73))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(voyantSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(308, 308, 308))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(moduleLabSlave)
                                .addGap(357, 357, 357))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(StatutRS232Lab)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statutRs232, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statutPRGLabel))
                    .addComponent(titre, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statutPGRM, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(moduleLabSlave)
                                .addGap(18, 18, 18)
                                .addComponent(voyantSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(moduleLab)
                                .addGap(18, 18, 18)
                                .addComponent(voyant, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(progBarre, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(testBarre, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(progBarreSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(testBarreSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(console, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(consoleSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnNOK, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLancer, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnACQ, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEffacer, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnProg, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTester, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnNOKSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLancerSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnOKSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnACQSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEffacerSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnProgSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTesterSlave, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)))
                .addComponent(version)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgActionPerformed

        programmer(true);
        /*
        if (!testActif) {

            if (!confirmationParams) {

                boolean confirmation = confirmeParams();
                if (!confirmation) {

                    return;

                } else {

                    confirmationParams = true;
                }
            }

            console.setText("Programmation en cours");
            programmationActive = true;
            progBarre.setVisible(true);
            testBarre.setVisible(true);
            voyant.setBackground(Color.YELLOW);

            Thread t = new Thread() {
                public void run() {

                    try {
                        int comm = connecteur.program(hexLocation, bleLocation, envVariable, progLocation);
                        System.out.println("Retour programmation. Code reçu: " + comm);
                        if (comm == -1) {

                            alerteRS232();

                        }

                        if (comm == -2) {

                            console.setText("Erreur de programmation");
                            voyant.setBackground(Color.red);
                            connecteur.envoyerData(Constants.ERR_PROG);
                            programmationActive = true;

                        }

                        if (comm == 1) {

                            console.setText("Programmation terminée!");
                            voyant.setBackground(Color.GREEN);
                            connecteur.envoyerData(Constants.END_PROG);
                            programmationActive = true;

                        }

                    } catch (IOException ex) {
                        Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            t.start();

        } else {

            int comm = connecteur.envoyerData(Constants.OK);

            if (comm == -1) {

                alerteRS232();

            }

            console.setText("Réponse OK");

        }
         */
    }//GEN-LAST:event_btnProgActionPerformed

    private void btnEffacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEffacerActionPerformed
        if (!testActif) {
            System.out.println("fonction Effacement");
            if (!confirmationParams) {

                boolean confirmation = confirmeParams();

                if (!confirmation) {

                    System.out.println("return");
                    return;
                }
            }

            console.setText("Effacement en cours");
            voyant.setBackground(Color.YELLOW);

            Thread t = new Thread() {
                public void run() {

                    connecteur.erase(envVariable, progLocation);
                }
            };
            t.start();

        } else {

            int comm = connecteur.envoyerData(Constants.KO);
            if (comm == -1) {

                alerteRS232();

            }
            console.setText("Résultat non conforme. Test terminé!");
            voyant.setBackground(Color.RED);

        }

    }//GEN-LAST:event_btnEffacerActionPerformed

    private void btnSelectLocationProgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectLocationProgActionPerformed


    }//GEN-LAST:event_btnSelectLocationProgActionPerformed

    private void btnSelectBinaryLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectBinaryLocActionPerformed


    }//GEN-LAST:event_btnSelectBinaryLocActionPerformed

    private void btnFermerParamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFermerParamsActionPerformed

        testParamsProg();
        if (EnvVarBox.isSelected()) {

            envVariable = true;
        } else {

            envVariable = false;
        }
        paramsWin.setVisible(false);
        testParamsProg();
    }//GEN-LAST:event_btnFermerParamsActionPerformed

    private void btnFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFermerActionPerformed

        System.exit(0);
    }//GEN-LAST:event_btnFermerActionPerformed

    private void paramsProgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paramsProgActionPerformed

        programmerLoc.showOpenDialog(this);
        programmerLocation = programmerLoc.getSelectedFile();
        if (programmerLoc.getSelectedFile() != null) {

            console.setText("Repertoire programmateur: " + programmerLocation.getPath());
            progLocation = programmerLocation.getPath();

        }

        testParamsProg();
    }//GEN-LAST:event_paramsProgActionPerformed

    private void paramBinaireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paramBinaireActionPerformed

        binaryLoc.showOpenDialog(this);
        binaryLocation = binaryLoc.getSelectedFile();
        if (binaryLocation != null) {

            String binPath = binaryLocation.getPath();
            String[] extension = binPath.split("\\.");
            if (!extension[1].equals("hex") && !extension[1].equals("bin")) {

                montrerError("Le fichier binaire n'est pas valide", "Fichier binaire invalide");
                return;
            }
            console.setText("Binaire location: " + binaryLocation.getPath());
            hexLocation = binaryLocation.getPath();

        }

        testParamsProg();
    }//GEN-LAST:event_paramBinaireActionPerformed

    private void menuVoirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVoirActionPerformed

        paramsWin.setVisible(true);
        EnvVarBox.setSelected(envVariable);
        if (progLocation != null) {
            progLocLabel.setText("Repertoire programmateur: " + progLocation);
        } else {

            progLocLabel.setText("Repertoire programmateur non défini!");
        }

        if (hexLocation != null) {

            binaryLocLabel.setText("Fichier binaire: " + hexLocation);
        } else {

            binaryLocLabel.setText("Fichier binaire non défini!");
        }

        if (bleLocation != null) {

            bleLocLabel.setText("Fichier ble: " + bleLocation);
        } else {

            bleLocLabel.setText("Fichier ble non défini!");
        }


    }//GEN-LAST:event_menuVoirActionPerformed

    private void btnTesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTesterActionPerformed

        System.out.println("Démarrage");
        progBarre.setVisible(true);
        if (!confirmationParams) {

            boolean confirmation = confirmeParams();
            if (!confirmation) {

                return;
            } else {

                confirmationParams = true;
            }
        }
        int comm = connecteur.envoyerData(Constants.START);
        if (comm == -1) {

            alerteRS232();
            return;

        }
        testActif = true;
        auto = true;
        voyantTestEnCours(true);
        // activerBtnReponseOp(testActif);
        activerBtnTestEnCours();


    }//GEN-LAST:event_btnTesterActionPerformed

    private void menuAideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAideActionPerformed


    }//GEN-LAST:event_menuAideActionPerformed

    private void btnFermerAideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFermerAideActionPerformed

        aide.setVisible(false);
    }//GEN-LAST:event_btnFermerAideActionPerformed

    private void voirAideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voirAideActionPerformed

        aide.setVisible(true);
    }//GEN-LAST:event_voirAideActionPerformed

    private void menuParametresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuParametresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuParametresActionPerformed

    private void EnvVarBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnvVarBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EnvVarBoxActionPerformed

    private void EnvVarBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_EnvVarBoxStateChanged

        if (EnvVarBox.isSelected()) {

            progLocLabel.setText("Variable d'environnement définie!");

        } else {

            if (progLocation == null) {

                progLocLabel.setText("Repertoire programmateur non défini!");
            } else {

                progLocLabel.setText(progLocation);
            }

        }
    }//GEN-LAST:event_EnvVarBoxStateChanged

    private void btnConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnexionActionPerformed

        int i = connecteur.makeConnection(Connecteur.portName, baudeRate, numDatabits, parity, stopBits);
        if (i == 99) {

            setStatusRS232(true);
            console.setForeground(Color.BLUE);
            console.setText("Connexion réussie");
            consoleSlave.setForeground(Color.BLUE);
            consoleSlave.setText("Connexion réussie");

            btnConnexion.setEnabled(false);
            btnDeconnexion.setEnabled(true);

            connexionRS232Active = true;

            activerBtnTester(true);
            activerBtnProgrammer(auto);

            activerBtnTesterSlave(true);
            activerBtnProgrammerSlave(auto);

        } else {

            console.setForeground(Color.red);
            console.setText("Tentative de connexion échouée");
            setStatusRS232(false);

        }

        setEnabledMenusConfiguration();

    }//GEN-LAST:event_btnConnexionActionPerformed

    private void btnDeconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeconnexionActionPerformed

        int i = connecteur.disconnect();
        if (i == 0) {
            console.setForeground(Color.BLUE);
            console.setText("Déconnexion réussie");
            setStatusRS232(false);
            btnConnexion.setEnabled(true);
            btnDeconnexion.setEnabled(false);
            connexionRS232Active = false;
            inhibBtn();

        }
    }//GEN-LAST:event_btnDeconnexionActionPerformed

    private void menuConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConnexionActionPerformed

        rechercherPortsComms();

    }//GEN-LAST:event_menuConnexionActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        bleLoc.showOpenDialog(this);
        BLELocation = bleLoc.getSelectedFile();
        if (BLELocation != null) {

            String binPath = BLELocation.getPath();
            String[] extension = binPath.split("\\.");
            if (!extension[1].equals("hex") && !extension[1].equals("bin")) {

                montrerError("Le fichier binaire n'est pas valide", "Fichier binaire invalide");
                return;
            }
            console.setText("Binaire location: " + BLELocation.getPath());
            bleLocation = BLELocation.getPath();

        }

        testParamsProg();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menuPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuPortActionPerformed

    private void btnLancerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLancerActionPerformed

        if (!confirmationParams) {

            boolean confirmation = confirmeParams();
            if (!confirmation) {

                return;

            } else {

                confirmationParams = true;
            }
        }

        inhibBtn();
        console.setText("Programmation en cours");
        programmationActive = true;
        progBarre.setVisible(true);
        testBarre.setVisible(true);
        voyant.setBackground(Color.YELLOW);

        Thread t = new Thread() {
            public void run() {

                try {
                    int comm = connecteur.program(hexLocation, bleLocation, envVariable, progLocation, true);

                    if (comm == -1) {

                        alerteRS232();

                    }

                    if (comm == -2) {

                        console.setText("Erreur de programmation");
                        voyant.setBackground(Color.red);
                        connecteur.envoyerData(Constants.ERR_PROG);
                        activerBtnAttenteACQ();
                        programmationActive = true;

                    }

                    if (comm == 1) {

                        console.setText("Programmation terminée!");
                        connecteur.envoyerData(Constants.END_PROG);
                        Constants.tempo(1000);
                        programmationActive = true;

                        comm = connecteur.envoyerData(Constants.AQC);
                        Constants.tempo(1000);
                        comm = connecteur.envoyerData(Constants.START);
                        activerBtnTestEnCours();
                        testBarre.setStringPainted(true);
                        testBarre.setString("Test en cours...");

                    }

                } catch (IOException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();


    }//GEN-LAST:event_btnLancerActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed

        AttenteReponseOperateur = false;
        int comm = connecteur.envoyerData(Constants.OK);

        if (comm == -1) {

            alerteRS232();

        }

        console.setText("Réponse OK");

    }//GEN-LAST:event_btnOKActionPerformed

    private void btnNOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNOKActionPerformed

        AttenteReponseOperateur = false;
        int comm = connecteur.envoyerData(Constants.KO);
        if (comm == -1) {

            alerteRS232();

        }
        console.setText("Résultat non conforme. Test terminé!");
        voyant.setBackground(Color.RED);
        //activerBtnAttenteACQ();

    }//GEN-LAST:event_btnNOKActionPerformed

    private void btnACQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnACQActionPerformed

        System.out.println("Acquittement");
        int comm = connecteur.envoyerData(Constants.AQC);

        if (comm == -1) {

            alerteRS232();

        }

        testActif = false;
        console.setText("TEST ACQUITTE - PRET POUR NOUVEAU TEST!");
        //activerBtnProgrammation();
        testParamsProg();
        voyantTestEnCours(false);
        progBarre.setValue(0);
        progBarre.setString("En attente lancement de programmation");
        progBarre.setStringPainted(true);
        testBarre.setValue(0);
        testBarre.setString("Test en attente de démarrage");
        testBarre.setStringPainted(true);


    }//GEN-LAST:event_btnACQActionPerformed

    private void btnLancerSlaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLancerSlaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLancerSlaveActionPerformed

    private void btnOKSlaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKSlaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOKSlaveActionPerformed

    private void btnNOKSlaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNOKSlaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNOKSlaveActionPerformed

    private void btnACQSlaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnACQSlaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnACQSlaveActionPerformed

    private void btnProgSlaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgSlaveActionPerformed

        programmer(false);
    }//GEN-LAST:event_btnProgSlaveActionPerformed

    private void btnEffacerSlaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEffacerSlaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEffacerSlaveActionPerformed

    private void btnTesterSlaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTesterSlaveActionPerformed

        System.out.println("Démarrage");
        progBarreSlave.setVisible(true);
        if (!confirmationParams) {

            boolean confirmation = confirmeParams();
            if (!confirmation) {

                return;
            } else {

                confirmationParams = true;
            }
        }
        int comm = connecteur.envoyerData(Constants.START_SLAVE);
        if (comm == -1) {

            alerteRS232();
            return;

        }
        testActifSlave = true;
        auto = true;
        voyantTestEnCoursSlave(true);
        // activerBtnReponseOp(testActif);
        activerBtnTestEnCoursSlave();


    }//GEN-LAST:event_btnTesterSlaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Interface().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox EnvVarBox;
    private javax.swing.JLabel StatutRS232Lab;
    private javax.swing.JFrame aide;
    private javax.swing.JRadioButtonMenuItem baud115200;
    private javax.swing.JRadioButtonMenuItem baud19200;
    private javax.swing.JRadioButtonMenuItem baud38400;
    private javax.swing.JRadioButtonMenuItem baud9600;
    private javax.swing.JFileChooser binaryLoc;
    private javax.swing.JLabel binaryLocLabel;
    private javax.swing.JRadioButtonMenuItem bits6;
    private javax.swing.JRadioButtonMenuItem bits7;
    private javax.swing.JRadioButtonMenuItem bits8;
    private javax.swing.JRadioButtonMenuItem bits9;
    private javax.swing.JFileChooser bleLoc;
    private javax.swing.JLabel bleLocLabel;
    private javax.swing.JButton btnACQ;
    private javax.swing.JButton btnACQSlave;
    private javax.swing.JMenuItem btnConnexion;
    private javax.swing.JMenuItem btnDeconnexion;
    private javax.swing.JButton btnEffacer;
    private javax.swing.JButton btnEffacerSlave;
    private javax.swing.JMenuItem btnFermer;
    private javax.swing.JButton btnFermerAide;
    private javax.swing.JButton btnFermerParams;
    private javax.swing.JButton btnLancer;
    private javax.swing.JButton btnLancerSlave;
    private javax.swing.JButton btnNOK;
    private javax.swing.JButton btnNOKSlave;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnOKSlave;
    private javax.swing.JButton btnProg;
    private javax.swing.JButton btnProgSlave;
    private javax.swing.JButton btnSelectBinaryLoc;
    private javax.swing.JButton btnSelectLocationProg;
    private javax.swing.JButton btnTester;
    private javax.swing.JButton btnTesterSlave;
    private javax.swing.JLabel console;
    private javax.swing.JLabel consoleSlave;
    private javax.swing.ButtonGroup groupBaud;
    private javax.swing.ButtonGroup groupBits;
    private javax.swing.ButtonGroup groupParity;
    private javax.swing.ButtonGroup groupPorts;
    private javax.swing.ButtonGroup groupStop;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JMenu menuAide;
    private javax.swing.JMenu menuBaud;
    private javax.swing.JMenu menuBits;
    private javax.swing.JMenu menuConnexion;
    private javax.swing.JMenu menuParametres;
    private javax.swing.JMenu menuParity;
    private javax.swing.JMenu menuPort;
    private javax.swing.JMenu menuStop;
    private javax.swing.JMenuItem menuVoir;
    private javax.swing.JLabel moduleLab;
    private javax.swing.JLabel moduleLabSlave;
    private javax.swing.JMenuItem paramBinaire;
    private javax.swing.JMenuItem paramsProg;
    private javax.swing.JFrame paramsWin;
    private javax.swing.JRadioButtonMenuItem parityEven;
    private javax.swing.JRadioButtonMenuItem parityNone;
    private javax.swing.JRadioButtonMenuItem parityOdd;
    private javax.swing.JProgressBar progBarre;
    private javax.swing.JProgressBar progBarreSlave;
    private javax.swing.JLabel progLocLabel;
    private javax.swing.JFileChooser programmerLoc;
    private javax.swing.JLabel statutPGRM;
    private javax.swing.JLabel statutPRGLabel;
    private javax.swing.JLabel statutRs232;
    private javax.swing.JRadioButtonMenuItem stop1;
    private javax.swing.JRadioButtonMenuItem stop2;
    private javax.swing.JProgressBar testBarre;
    private javax.swing.JProgressBar testBarreSlave;
    private javax.swing.JLabel titre;
    private javax.swing.JLabel titreLabBLE;
    private javax.swing.JLabel titreLabHex;
    private javax.swing.JLabel titreLabProg;
    private javax.swing.JLabel titreParamsWin;
    private javax.swing.JLabel version;
    private javax.swing.JMenuItem voirAide;
    private javax.swing.JLabel voyant;
    private javax.swing.JLabel voyantSlave;
    // End of variables declaration//GEN-END:variables

    private void testParamsProg() {

        /*
        if (hexLocation == null) {

            console.setText("Le fichier binaire n'est pas défini!");
            voyant.setBackground(Color.GRAY);
            statutPGRM.setBackground(Color.RED);
            statutPGRM.setForeground(Color.RED);

        } else {

            if (bleLocation == null) {

                console.setText("Le fichier BLE n'est pas défini!");
                voyant.setBackground(Color.GRAY);
                statutPGRM.setBackground(Color.RED);
                statutPGRM.setForeground(Color.RED);

            } else {

                if (!envVariable && progLocation == null) {

                    console.setText("Repertoire programmateur non défini!");
                    statutPGRM.setBackground(Color.RED);
                    statutPGRM.setForeground(Color.RED);

                } else {

                    console.setText("Paramètres de programmation définis. Vous pouvez commencer!");
                    statutPGRM.setBackground(Color.GREEN);
                    statutPGRM.setForeground(Color.GREEN);
                    initializer.update("binaryLocation", hexLocation);
                    initializer.update("bleLocation", bleLocation);
                    initializer.update("programmerDirectory", progLocation);

                    if (envVariable) {

                        initializer.update("varEnv", "true");
                    } else {

                        initializer.update("varEnv", "false");
                    }

                }

            }

        }

        if (connexionRS232Active) {

            statutRs232.setBackground(Color.GREEN);
            //activerBtnAttenteLancement();
            activerBtnTester(true);
            activerBtnProgrammer(true);
            activerBtnACQ(false);

        } else {

            statutRs232.setBackground(Color.red);
            inhibBtn();

        }

         */
        if (hexLocation == null) {

            console.setText("Le fichier binaire n'est pas défini!");
            voyant.setBackground(Color.GRAY);

            consoleSlave.setText("Le fichier binaire n'est pas défini!");
            voyantSlave.setBackground(Color.GRAY);
            statutPGRM.setBackground(Color.RED);
            statutPGRM.setForeground(Color.RED);

        } else {

            if (bleLocation == null) {

                console.setText("Le fichier BLE n'est pas défini!");
                voyant.setBackground(Color.GRAY);

                consoleSlave.setText("Le fichier BLE n'est pas défini!");
                voyantSlave.setBackground(Color.GRAY);
                statutPGRM.setBackground(Color.RED);
                statutPGRM.setForeground(Color.RED);

            } else {

                if (!envVariable && progLocation == null) {

                    console.setText("Repertoire programmateur non défini!");
                    consoleSlave.setText("Repertoire programmateur non défini!");
                    statutPGRM.setBackground(Color.RED);
                    statutPGRM.setForeground(Color.RED);

                } else {

                    console.setText("Paramètres de programmation définis. Vous pouvez commencer!");
                    consoleSlave.setText("Paramètres de programmation définis. Vous pouvez commencer!");
                    statutPGRM.setBackground(Color.GREEN);
                    statutPGRM.setForeground(Color.GREEN);
                    initializer.update("binaryLocation", hexLocation);
                    initializer.update("bleLocation", bleLocation);
                    initializer.update("programmerDirectory", progLocation);

                    if (envVariable) {

                        initializer.update("varEnv", "true");
                    } else {

                        initializer.update("varEnv", "false");
                    }

                }

            }

        }

        if (connexionRS232Active) {

            statutRs232.setBackground(Color.GREEN);
            //activerBtnAttenteLancement();
            activerBtnTester(true);
            activerBtnProgrammer(true);
            activerBtnACQ(false);

            activerBtnTesterSlave(true);
            activerBtnProgrammerSlave(true);
            activerBtnACQSlave(false);

        } else {

            statutRs232.setBackground(Color.red);
            inhibBtn();
            inhibBtnSlave();

        }

    }

    public void montrerError(String message, String titre) {

        JOptionPane.showMessageDialog(this, message, titre, JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirmeParams() {

        int response = JOptionPane.showConfirmDialog(this, "Confirmez-vous ces paramètres?", "Paramètres de programmation définis", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION) {
            System.out.println("No button clicked");
            return false;
        }
        if (response == JOptionPane.YES_OPTION) {

            return true;

        }
        return false;

    }

    private Connecteur getConnecteur() {

        if (this.connecteur == null) {
            this.connecteur = new Connecteur();
            this.connecteur.addObserver(this);
        }
        return this.connecteur;

    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof Integer) {

            if ((Integer) arg == Constants.PROG_SUCCESS) {

                //voyant.setBackground(Color.GREEN);
                progBarre.setValue(100);
                console.setText("Programmation terminée!");
            }

            if ((Integer) arg == Constants.ERASE_SUCCESS) {

                voyant.setBackground(Color.GREEN);
                progBarre.setValue(100);
                console.setText("Effacement terminée!");
            }

            if ((Integer) arg == Constants.PROG_START) {

                voyant.setBackground(Color.YELLOW);
                progBarre.setStringPainted(true);
                progBarre.setString("Programmation en cours...");
                progBarre.setValue(0);
                console.setText("Programmation en cours");
            }

            if ((Integer) arg == Constants.PROG_SUCCESS_ETAPE1) {

                voyant.setBackground(Color.YELLOW);
                progBarre.setStringPainted(true);
                progBarre.setString("Programmation en cours...");
                progBarre.setValue(15);
                console.setText("Programmation: étape 1 terminée!");
            }

            if ((Integer) arg == Constants.PROG_SUCCESS_ETAPE2) {

                voyant.setBackground(Color.YELLOW);
                progBarre.setStringPainted(true);
                progBarre.setString("Programmation en cours...");
                progBarre.setValue(70);
                console.setText("Programmation: étape 2 terminée!");
            }

            if ((Integer) arg == Constants.PROG_SUCCESS_ETAPE3) {

                voyant.setBackground(Color.YELLOW);
                progBarre.setStringPainted(true);
                progBarre.setString("Programmation en cours...");
                progBarre.setValue(90);
                console.setText("Programmation: étape 3 terminée!");
            }

            if ((Integer) arg == Constants.PROG_SUCCESS_ETAPE4) {

                //voyant.setBackground(Color.GREEN);
                progBarre.setString("Programmation terminée!");
                progBarre.setStringPainted(true);
                progBarre.setValue(100);
                console.setText("Programmation: étape 4 terminée! - Le test est en cours");

            }

            if ((Integer) arg == Constants.PROG_UNSUCCESS_ETAPE1) {

                voyant.setBackground(Color.RED);
                progBarre.setString("Echec programmation!");
                progBarre.setStringPainted(true);
                console.setText("Programmation: échec étape 1!");
                montrerError("Vérifier positionnement carte!", "Erreur de programmation");
                activerBtnAttenteACQ();
            }

            if ((Integer) arg == Constants.PROG_UNSUCCESS_ETAPE2) {

                voyant.setBackground(Color.RED);
                progBarre.setString("Echec programmation!");
                progBarre.setStringPainted(true);
                console.setText("Programmation: échec étape 2!");
                activerBtnAttenteACQ();
            }

            if ((Integer) arg == Constants.PROG_UNSUCCESS_ETAPE3) {

                voyant.setBackground(Color.RED);
                progBarre.setString("Echec programmation!");
                progBarre.setStringPainted(true);
                console.setText("Programmation: échec étape 3!");
                activerBtnAttenteACQ();
            }

            if ((Integer) arg == Constants.PROG_UNSUCCESS_ETAPE4) {

                voyant.setBackground(Color.RED);
                progBarre.setString("Echec programmation!");
                progBarre.setStringPainted(true);
                console.setText("Programmation: échec étape 4!");
                activerBtnAttenteACQ();
            }

        } else {

            String inputLine = (String) arg;
            System.out.println(inputLine);
            if (auto) {

                //console.setText(inputLine);
            }

            processRapport(inputLine);
        }

    }

    private void setEnabledMenusConfiguration() {

    }

    private void setStatusRS232(boolean statut) {

        if (statut) {

            statutRs232.setForeground(Color.GREEN);
            statutRs232.setBackground(Color.GREEN);
        } else {
            statutRs232.setForeground(Color.RED);
            statutRs232.setBackground(Color.RED);
        }

    }

    private void processRapport(String inputLine) {

        if (auto) {

            if (inputLine.trim().startsWith("-> TEST CONFORME")) {

                messageConsole("TEST CONFORME - EN ATTENTE ACQUITTEMENT");
                activerBtnAttenteACQ();
                voyantTestOK(true);

            }

            if (inputLine.trim().startsWith("-> TEST MANUEL")) {

                messageConsole("TEST MANUEL EN COURS");
                auto = false;
                voyant.setBackground(Color.BLUE);
                btnTester.setEnabled(false);
                btnTester.setBackground(Color.GRAY);
                btnProg.setEnabled(false);
                btnProg.setBackground(Color.GRAY);
                btnEffacer.setEnabled(false);
                btnEffacer.setBackground(Color.GRAY);

            }

            if (inputLine.trim().startsWith("-> FIN TEST MANUEL")) {

                System.out.println("test manuel acquitté1");
                messageConsole("FIN TEST MANUEL");
                voyant.setBackground(Color.GRAY);
                //inhibBtn();
                activerBtnTester(true);
                activerBtnProgrammer(auto);

            }

            if (inputLine.trim().startsWith("-> ERREUR:")) {

                System.out.println("Signalisation erreur!");
                messageConsole(inputLine.trim());
                activerBtnAttenteACQ();
                voyantTestOK(false);
                System.out.println("testActif  =" + testActif);

            }

            if (inputLine.trim().startsWith("-> PROGRAMMATION TERMINEE")) {

                System.out.println("programmation terminée");
                messageConsole(inputLine.trim());
                System.out.println("testActif  =" + testActif);

            }

            if (inputLine.trim().startsWith("-> EFFACEMENT TERMINE")) {

                System.out.println("effacement terminé");
                messageConsole(inputLine.trim());
                activerBtnAttenteLancement();
                voyantTestEnCours(false);
                System.out.println("testActif  =" + testActif);

            }

            // traitement des résultats aux étapes de test
            if (inputLine.trim().startsWith("-> TEST")) {

                AttenteReponseOperateur = false;
                String[] tab = inputLine.trim().split(":");
                int etape = Integer.parseInt(tab[1]);
                boolean result = Boolean.parseBoolean(tab[2]);
                System.out.println("Etape: " + etape);
                console.setText("Etape: " + etape);

                if (etape < 18 || etape > 1) {

                    testBarre.setValue(etape * 5);

                }

                if (etape == 12 || etape == 16) {

                    AttenteReponseOperateur = true;

                    if (etape == 12) {

                        console.setText("EN ATTENTE VALIDATION LEDS");
                    }

                    if (etape > 16) {

                        console.setText("EN ATTENTE VALIDATION BLUETOOTH");
                    }
                    clignottementVoyant();

                }

                if (etape == 18) {

                    testBarre.setValue(100);
                    testBarre.setString("Test terminé!");
                    testBarre.setStringPainted(true);
                    activerBtnAttenteACQ();
                    voyantTestOK(true);

                }

                // Fin traitement message pour test maitre
                //------------------------------------------------------------------
                // Traitement message pour test escalve
                if (inputLine.trim().startsWith("-> SLAVE TEST CONFORME")) {

                    messageConsoleSlave("TEST CONFORME - EN ATTENTE ACQUITTEMENT");
                    activerBtnAttenteACQ();
                    voyantTestOK(true);

                }

                if (inputLine.trim().startsWith("-> SLAVE TEST MANUEL")) {

                    messageConsoleSlave("TEST MANUEL EN COURS");
                    auto = false;
                    voyantSlave.setBackground(Color.BLUE);
                    btnTesterSlave.setEnabled(false);
                    btnTesterSlave.setBackground(Color.GRAY);
                    btnProgSlave.setEnabled(false);
                    btnProgSlave.setBackground(Color.GRAY);
                    btnEffacerSlave.setEnabled(false);
                    btnEffacerSlave.setBackground(Color.GRAY);

                }

                if (inputLine.trim().startsWith("-> SLAVE FIN TEST MANUEL")) {

                    System.out.println("test manuel acquitté1");
                    messageConsoleSlave("FIN TEST MANUEL");
                    voyantSlave.setBackground(Color.GRAY);
                    //inhibBtn();
                    activerBtnTesterSlave(true);
                    activerBtnProgrammerSlave(auto);

                }

                if (inputLine.trim().startsWith("-> SLAVE ERREUR:")) {

                    System.out.println("Signalisation erreur!");
                    messageConsoleSlave(inputLine.trim());
                    activerBtnAttenteACQSlave();
                    voyantTestOKSlave(false);
                    System.out.println("testActif  =" + testActif);

                }

                if (inputLine.trim().startsWith("-> SLAVE PROGRAMMATION TERMINEE")) {

                    System.out.println("programmation terminée");
                    messageConsoleSlave(inputLine.trim());
                    System.out.println("testActif  =" + testActif);

                }

                if (inputLine.trim().startsWith("-> SLAVE EFFACEMENT TERMINE")) {

                    System.out.println("effacement terminé");
                    messageConsoleSlave(inputLine.trim());
                    activerBtnAttenteLancementSlave();
                    voyantTestEnCoursSlave(false);
                    System.out.println("testActif  =" + testActif);

                }

                if (inputLine.trim().startsWith("-> SLAVE RESPONSE NULL")) {

                    System.out.println("Défaut communication master/slave");
                    messageConsoleSlave(inputLine.trim());
                    activerBtnAttenteACQSlave();
                    voyantTestOKSlave(false);
                    System.out.println("testActif  =" + testActif);

                }

                // traitement des résultats aux étapes de test
                if (inputLine.trim().startsWith("-> SLAVE TEST")) {

                    AttenteReponseOperateur = false;
                    String[] tab_SLAVE = inputLine.trim().split(":");
                    int etape_SLAVE = Integer.parseInt(tab[1]);
                    boolean result_SLAVE = Boolean.parseBoolean(tab[2]);
                    System.out.println("Etape: " + etape);
                    consoleSlave.setText("Etape: " + etape);

                    if (etape < 18 || etape > 1) {

                        testBarreSlave.setValue(etape * 5);

                    }

                    if (etape_SLAVE == 12 || etape == 16) {

                        AttenteReponseOperateur = true;

                        if (etape_SLAVE == 12) {

                            consoleSlave.setText("EN ATTENTE VALIDATION LEDS");
                        }

                        if (etape_SLAVE > 16) {

                            consoleSlave.setText("EN ATTENTE VALIDATION BLUETOOTH");
                        }
                        clignottementVoyant();

                    }

                    if (etape_SLAVE == 18) {

                        testBarreSlave.setValue(100);
                        testBarreSlave.setString("Test terminé!");
                        testBarreSlave.setStringPainted(true);
                        activerBtnAttenteACQSlave();
                        voyantTestOKSlave(true);

                    }

                    // Fin traitement message pour test esclave
                    //---------------------------------------------------------------------------------------
                    if (result_SLAVE) {

                    } else {

                    }

                }

                // Fin traitement mode auto 
                //------------------------------------------------------------------------------------------------
            } else {

                if (inputLine.trim().startsWith("-> FIN TEST MANUEL")) {

                    System.out.println("test manuel acquitté2");
                    messageConsole("FIN TEST MANUEL");
                    voyant.setBackground(Color.GRAY);
                    //activerBtnAttenteLancement();
                    activerBtnTester(true);
                    activerBtnProgrammer(true);

                }

                if (inputLine.trim().startsWith("-> SLAVE FIN TEST MANUEL")) {

                    System.out.println("test manuel acquitté2");
                    messageConsoleSlave("FIN TEST MANUEL");
                    voyantSlave.setBackground(Color.GRAY);
                    //activerBtnAttenteLancement();
                    activerBtnTesterSlave(true);
                    activerBtnProgrammerSlave(true);

                }
            }

            if (inputLine.trim().startsWith("-> TEST ACQUITTE")) {

                messageConsole("TEST ACQUITTE");
                voyant.setBackground(Color.GRAY);
                //activerBtnAttenteLancement();
                activerBtnTester(true);
                activerBtnProgrammer(true);

            }

            if (inputLine.trim().startsWith("-> SLAVE TEST ACQUITTE")) {

                messageConsoleSlave("TEST ACQUITTE");
                voyantSlave.setBackground(Color.GRAY);
                //activerBtnAttenteLancement();
                activerBtnTesterSlave(true);
                activerBtnProgrammerSlave(true);

            }

        }
    }

    private void activerBtnReponseOp(boolean active) {

        if (active) {

            btnTester.setText("ACQ");
            btnTester.setEnabled(false);
            btnTester.setBackground(Color.GRAY);

            btnProg.setText("OK");
            btnProg.setBackground(new Color(163, 194, 240));
            btnProg.setEnabled(true);

            btnEffacer.setText("NON OK");
            btnEffacer.setBackground(new Color(163, 194, 240));
            btnEffacer.setEnabled(true);

        } else {

            btnTester.setText("ACQ");
            btnTester.setEnabled(false);
            btnTester.setBackground(Color.GRAY);

            btnProg.setText("OK");
            btnProg.setBackground(new Color(163, 194, 240));
            btnProg.setEnabled(false);

            btnEffacer.setText("NON OK");
            btnEffacer.setBackground(new Color(163, 194, 240));
            btnEffacer.setEnabled(false);

        }

    }

    private void activerBtnAcquittement(boolean active) {

        if (active) {

            btnTester.setText("ACQ");
            btnTester.setEnabled(true);
            btnTester.setBackground(new Color(163, 194, 240));

            btnProg.setEnabled(false);
            btnProg.setBackground(Color.GRAY);
            btnEffacer.setEnabled(false);
            btnEffacer.setBackground(Color.GRAY);

        } else {

            btnTester.setText("ACQ");
            btnTester.setEnabled(false);

        }

    }

    private void voyantTestEnCours(boolean active) {

        if (active) {
            voyant.setBackground(Color.ORANGE);
        } else {
            voyant.setBackground(Color.GRAY);
        }

    }

    private void voyantTestOK(boolean active) {

        if (active) {

            voyant.setBackground(Color.GREEN);
        } else {

            voyant.setBackground(Color.RED);
        }

    }

    private void voyantTestEnCoursSlave(boolean active) {

        if (active) {
            voyantSlave.setBackground(Color.ORANGE);
        } else {
            voyantSlave.setBackground(Color.GRAY);
        }

    }

    private void voyantTestOKSlave(boolean active) {

        if (active) {

            voyantSlave.setBackground(Color.GREEN);
        } else {

            voyantSlave.setBackground(Color.RED);
        }

    }

    private void messageConsole(String message) {

        console.setText(message);

    }

    private void messageConsoleSlave(String message) {

        consoleSlave.setText(message);

    }

    private void activerBtnProgrammation(boolean active) {

        if (active) {

            btnTester.setText("TESTER");
            btnTester.setEnabled(true);
            btnTester.setBackground(new Color(163, 194, 240));

            btnProg.setText("PROGRAMMER");
            btnProg.setEnabled(true);
            btnProg.setBackground(new Color(163, 194, 240));

            btnEffacer.setText("EFFACER");
            btnEffacer.setEnabled(true);
            btnEffacer.setBackground(new Color(163, 194, 240));
            testActif = false;
            auto = true;

        } else {

            btnTester.setText("TESTER");
            btnTester.setEnabled(false);
            btnTester.setBackground(Color.GRAY);

            btnProg.setText("PROGRAMMER");
            btnProg.setEnabled(false);
            btnProg.setBackground(Color.GRAY);

            btnEffacer.setText("EFFACER");
            btnEffacer.setEnabled(false);
            btnEffacer.setBackground(Color.GRAY);
            testActif = false;
            auto = false;

        }
    }

    private void activerBtnProgrammationSlave(boolean active) {

        if (active) {

            btnTesterSlave.setText("TESTER");
            btnTesterSlave.setEnabled(true);
            btnTesterSlave.setBackground(new Color(163, 194, 240));

            btnProgSlave.setText("PROGRAMMER");
            btnProgSlave.setEnabled(true);
            btnProgSlave.setBackground(new Color(163, 194, 240));

            btnEffacerSlave.setText("EFFACER");
            btnEffacerSlave.setEnabled(true);
            btnEffacerSlave.setBackground(new Color(163, 194, 240));
            testActif = false;
            auto = true;

        } else {

            btnTesterSlave.setText("TESTER");
            btnTesterSlave.setEnabled(false);
            btnTester.setBackground(Color.GRAY);

            btnProgSlave.setText("PROGRAMMER");
            btnProgSlave.setEnabled(false);
            btnProgSlave.setBackground(Color.GRAY);

            btnEffacerSlave.setText("EFFACER");
            btnEffacerSlave.setEnabled(false);
            btnEffacerSlave.setBackground(Color.GRAY);
            testActifSlave = false;
            auto = false;

        }
    }

    private void waitForErasing(boolean active) {

        if (active) {

            console.setText("Effacement demandé");
            voyant.setBackground(Color.YELLOW);
            System.out.println("Affichage yellow");

        } else {

            console.setText("Effacement terminé");
            voyant.setBackground(Color.GREEN);
            System.out.println("Affichage green");

        }

    }

    private void waitForProgramming(boolean active) {

        if (active) {

            console.setText("Programation en cours");
            voyant.setBackground(Color.YELLOW);
            voyant.setOpaque(true);

        } else {

            console.setText("Programation terminé");
            voyant.setBackground(Color.GREEN);
            voyant.setOpaque(true);

        }

    }

    private void alerteRS232() {

        int i = connecteur.disconnect();
        if (i == 0) {

            console.setForeground(Color.BLUE);
            console.setText("La connexion a été perdue! Vous devez la rétablir.");
            setStatusRS232(false);
            btnConnexion.setEnabled(true);
            btnDeconnexion.setEnabled(false);
            connexionRS232Active = false;
            btnTester.setEnabled(false);
            btnTester.setBackground(Color.GRAY);
            btnTester.setOpaque(true);
            setEnabledMenusConfiguration();
            testActif = false;
            auto = false;
            // activerBtnProgrammation(false);
            inhibBtn();
            voyantTestEnCours(false);

        }

        montrerError("La connexion RS-232 a été perdue!\n Vérifer la connexion RS-232 et appuyer sur OK\n L'application va redémarrer", "Défaut de connexion");
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("java -jar keypadProgrammer.jar");
            System.exit(0);

        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rechercherPortsComms() {

        menuPort.removeAll();

        listePorts.clear();
        listePortString.clear();
        listePortString = connecteur.getListPorts();

        for (String p : listePortString) {

            JRadioButtonMenuItem m = new JRadioButtonMenuItem(p);
            groupPorts.add(m);
            m.addActionListener(new PortSupplier());
            menuPort.add(m);
        }

    }

    // Fonction d'activation des boutons selon phase en cours
    // Bouton module maitre
    void activerBtnAttenteLancement() {

        activerBtnLancer(true);
        activerBtnACQ(false);
        activerBtnEffacer(false);
        activerBtnOK(false);
        activerBtnNOK(false);
        activerBtnTester(false);
        activerBtnProgrammer(false);

    }

    void activerBtnAttenteACQ() {

        activerBtnLancer(false);
        activerBtnACQ(true);
        activerBtnEffacer(false);
        activerBtnOK(false);
        activerBtnNOK(false);
        activerBtnTester(false);
        activerBtnProgrammer(false);

    }

    void activerBtnTestEnCours() {

        activerBtnLancer(false);
        activerBtnACQ(false);
        activerBtnEffacer(false);
        activerBtnOK(true);
        activerBtnNOK(true);
        activerBtnTester(false);
        activerBtnProgrammer(false);

    }

    void inhibBtn() {

        activerBtnLancer(false);
        activerBtnACQ(false);
        activerBtnEffacer(false);
        activerBtnOK(false);
        activerBtnNOK(false);
        activerBtnTester(false);
        activerBtnProgrammer(false);

    }

    // Fonctions d'activation des boutons
    void activerBtnLancer(boolean active) {

        btnLancer.setOpaque(true);
        if (active) {

            btnLancer.setEnabled(true);
            btnLancer.setBackground(new Color(163, 194, 240));

        } else {

            btnLancer.setEnabled(false);
            btnLancer.setBackground(Color.GRAY);

        }

    }

    void activerBtnOK(boolean active) {

        btnOK.setOpaque(true);
        if (active) {

            btnOK.setEnabled(true);
            btnOK.setBackground(new Color(163, 194, 240));

        } else {

            btnOK.setEnabled(false);
            btnOK.setBackground(Color.GRAY);

        }
    }

    void activerBtnNOK(boolean active) {

        btnNOK.setOpaque(true);
        if (active) {

            btnNOK.setEnabled(true);
            btnNOK.setBackground(new Color(163, 194, 240));

        } else {

            btnNOK.setEnabled(false);
            btnNOK.setBackground(Color.GRAY);

        }
    }

    void activerBtnTester(boolean active) {

        btnTester.setOpaque(true);
        if (active) {

            btnTester.setEnabled(true);
            btnTester.setBackground(new Color(163, 194, 240));

        } else {

            btnTester.setEnabled(false);
            btnTester.setBackground(Color.GRAY);

        }
    }

    void activerBtnACQ(boolean active) {

        btnACQ.setOpaque(true);
        if (active) {

            btnACQ.setEnabled(true);
            btnACQ.setBackground(new Color(163, 194, 240));

        } else {

            btnACQ.setEnabled(false);
            btnACQ.setBackground(Color.GRAY);

        }
    }

    void activerBtnProgrammer(boolean active) {

        btnProg.setOpaque(true);
        if (active) {

            btnProg.setEnabled(true);
            btnProg.setBackground(new Color(163, 194, 240));

        } else {

            btnProg.setEnabled(false);
            btnProg.setBackground(Color.GRAY);

        }
    }

    void activerBtnEffacer(boolean active) {

        btnEffacer.setOpaque(true);
        if (active) {

            btnEffacer.setEnabled(true);
            btnEffacer.setBackground(new Color(163, 194, 240));

        } else {

            btnEffacer.setEnabled(false);
            btnEffacer.setBackground(Color.GRAY);

        }
    }

    private void clignottementVoyant() {

        while (AttenteReponseOperateur) {
            voyant.setBackground(Color.MAGENTA);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
            voyant.setBackground(Color.YELLOW);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Fin pilotage des bouton du module maitre
    //-----------------------------------------------------------------------------------------------------
    // Pilotage des bouton du module esclave
    void activerBtnAttenteLancementSlave() {

        activerBtnLancerSlave(true);
        activerBtnACQSlave(false);
        activerBtnEffacerSlave(false);
        activerBtnOKSlave(false);
        activerBtnNOKSlave(false);
        activerBtnTesterSlave(false);
        activerBtnProgrammerSlave(false);

    }

    void activerBtnAttenteACQSlave() {

        activerBtnLancerSlave(false);
        activerBtnACQSlave(true);
        activerBtnEffacerSlave(false);
        activerBtnOKSlave(false);
        activerBtnNOKSlave(false);
        activerBtnTesterSlave(false);
        activerBtnProgrammerSlave(false);

    }

    void activerBtnTestEnCoursSlave() {

        activerBtnLancerSlave(false);
        activerBtnACQSlave(false);
        activerBtnEffacerSlave(false);
        activerBtnOKSlave(true);
        activerBtnNOKSlave(true);
        activerBtnTesterSlave(false);
        activerBtnProgrammerSlave(false);

    }

    void inhibBtnSlave() {

        activerBtnLancerSlave(false);
        activerBtnACQSlave(false);
        activerBtnEffacerSlave(false);
        activerBtnOKSlave(false);
        activerBtnNOKSlave(false);
        activerBtnTesterSlave(false);
        activerBtnProgrammerSlave(false);

    }

    // Fonctions d'activation des boutons
    void activerBtnLancerSlave(boolean active) {

        btnLancerSlave.setOpaque(true);
        if (active) {

            btnLancerSlave.setEnabled(true);
            btnLancerSlave.setBackground(new Color(163, 194, 240));

        } else {

            btnLancerSlave.setEnabled(false);
            btnLancerSlave.setBackground(Color.GRAY);

        }

    }

    void activerBtnOKSlave(boolean active) {

        btnOKSlave.setOpaque(true);
        if (active) {

            btnOKSlave.setEnabled(true);
            btnOKSlave.setBackground(new Color(163, 194, 240));

        } else {

            btnOKSlave.setEnabled(false);
            btnOKSlave.setBackground(Color.GRAY);

        }
    }

    void activerBtnNOKSlave(boolean active) {

        btnNOKSlave.setOpaque(true);
        if (active) {

            btnNOKSlave.setEnabled(true);
            btnNOKSlave.setBackground(new Color(163, 194, 240));

        } else {

            btnNOKSlave.setEnabled(false);
            btnNOKSlave.setBackground(Color.GRAY);

        }
    }

    void activerBtnTesterSlave(boolean active) {

        btnTesterSlave.setOpaque(true);
        if (active) {

            btnTesterSlave.setEnabled(true);
            btnTesterSlave.setBackground(new Color(163, 194, 240));

        } else {

            btnTesterSlave.setEnabled(false);
            btnTesterSlave.setBackground(Color.GRAY);

        }
    }

    void activerBtnACQSlave(boolean active) {

        btnACQSlave.setOpaque(true);
        if (active) {

            btnACQSlave.setEnabled(true);
            btnACQSlave.setBackground(new Color(163, 194, 240));

        } else {

            btnACQSlave.setEnabled(false);
            btnACQSlave.setBackground(Color.GRAY);

        }
    }

    void activerBtnProgrammerSlave(boolean active) {

        btnProgSlave.setOpaque(true);
        if (active) {

            btnProgSlave.setEnabled(true);
            btnProgSlave.setBackground(new Color(163, 194, 240));

        } else {

            btnProgSlave.setEnabled(false);
            btnProgSlave.setBackground(Color.GRAY);

        }
    }

    void activerBtnEffacerSlave(boolean active) {

        btnEffacerSlave.setOpaque(true);
        if (active) {

            btnEffacerSlave.setEnabled(true);
            btnEffacerSlave.setBackground(new Color(163, 194, 240));

        } else {

            btnEffacerSlave.setEnabled(false);
            btnEffacerSlave.setBackground(Color.GRAY);

        }
    }

    private void clignottementVoyantSlave() {

        while (AttenteReponseOperateurSlave) {
            voyantSlave.setBackground(Color.MAGENTA);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
            voyantSlave.setBackground(Color.YELLOW);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------
    // Fonction de programmation module maître
    private void programmer(Boolean master) {

        if (!testActif) {

            if (!confirmationParams) {

                boolean confirmation = confirmeParams();
                if (!confirmation) {

                    return;

                } else {

                    confirmationParams = true;
                }
            }

            console.setText("Programmation en cours");
            programmationActive = true;
            progBarre.setVisible(true);
            testBarre.setVisible(true);
            voyant.setBackground(Color.YELLOW);

            Thread t = new Thread() {
                public void run() {

                    try {
                        int comm = connecteur.program(hexLocation, bleLocation, envVariable, progLocation, master);
                        System.out.println("Retour programmation. Code reçu: " + comm);
                        if (comm == -1) {

                            alerteRS232();

                        }

                        if (comm == -2) {

                            console.setText("Erreur de programmation");
                            voyant.setBackground(Color.red);
                            connecteur.envoyerData(Constants.ERR_PROG);
                            programmationActive = true;

                        }

                        if (comm == 1) {

                            console.setText("Programmation terminée!");
                            voyant.setBackground(Color.GREEN);
                            connecteur.envoyerData(Constants.END_PROG);
                            programmationActive = true;

                        }

                    } catch (IOException ex) {
                        Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            t.start();

        } else {

            int comm = connecteur.envoyerData(Constants.OK);

            if (comm == -1) {

                alerteRS232();

            }

            console.setText("Réponse OK");

        }

    }

    //-----------------------------------------------------------------------------------------------------
    // Fonction de programmation module maître
    private void programmerSlave(Boolean master) {

        if (!testActifSlave) {

            if (!confirmationParams) {

                boolean confirmation = confirmeParams();
                if (!confirmation) {

                    return;

                } else {

                    confirmationParams = true;
                }
            }

            consoleSlave.setText("Programmation en cours");
            programmationActive = true;
            progBarreSlave.setVisible(true);
            testBarreSlave.setVisible(true);
            voyantSlave.setBackground(Color.YELLOW);

            Thread t = new Thread() {
                public void run() {

                    try {
                        int comm = connecteur.program(hexLocation, bleLocation, envVariable, progLocation, master);
                        System.out.println("Retour programmation. Code reçu: " + comm);
                        if (comm == -1) {

                            alerteRS232();

                        }

                        if (comm == -2) {

                            consoleSlave.setText("Erreur de programmation");
                            voyantSlave.setBackground(Color.red);
                            connecteur.envoyerData(Constants.ERR_PROG_SLAVE);
                            programmationActive = true;

                        }

                        if (comm == 1) {

                            consoleSlave.setText("Programmation terminée!");
                            voyantSlave.setBackground(Color.GREEN);
                            connecteur.envoyerData(Constants.END_PROG_SLAVE);
                            programmationActive = true;

                        }

                    } catch (IOException ex) {
                        Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            t.start();

        } else {

            int comm = connecteur.envoyerData(Constants.OK_SLAVE);

            if (comm == -1) {

                alerteRS232();

            }

            consoleSlave.setText("Réponse OK");

        }

    }
}

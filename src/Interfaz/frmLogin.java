package Interfaz;

import Clases.Login;
import Clases.NFC;
import Clases.Personas;
import Controlador.ConexionNFC;
import Controlador.Funciones;
//import com.sun.awt.AWTUtilities;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.Timer;
import javax.swing.JOptionPane;

public class frmLogin extends javax.swing.JFrame {

    Login login = new Login();
    Personas pers = new Personas();
    Funciones funcion = new Funciones();
    NFC nfc = new NFC();
    ConexionNFC escaner = new ConexionNFC();
    String DatoObtenido;
    private final Timer timer = new Timer();

    public frmLogin() {
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                IniciarPrograma();
            }
        }, 10, 500);

        initComponents();
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 30, 30); //Estilo redondeado
        //AWTUtilities.setWindowShape(this, forma); //Aplicar estilo redondeado
        this.setLocationRelativeTo(null); //Centrar ventana

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ICON = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jtxtNickName = new javax.swing.JTextField();
        jpasswd = new javax.swing.JPasswordField();
        jIngresar1 = new javax.swing.JLabel();
        jIngresar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCerrar = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(960, 420));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        ICON.setFont(new java.awt.Font("Lucida Console", 1, 24)); // NOI18N
        ICON.setForeground(new java.awt.Color(255, 255, 255));
        ICON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/256x256/iconfinder_user-id_285641.png"))); // NOI18N
        ICON.setText("LOGIN");
        getContentPane().add(ICON);
        ICON.setBounds(40, 130, 250, 190);

        jLabel3.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Usuario");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(340, 150, 170, 20);

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Contraseña");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(340, 220, 170, 20);

        jtxtNickName.setBackground(new java.awt.Color(204, 204, 204));
        jtxtNickName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtxtNickName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtNickName.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtxtNickName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtNickNameKeyPressed(evt);
            }
        });
        getContentPane().add(jtxtNickName);
        jtxtNickName.setBounds(340, 180, 170, 28);

        jpasswd.setBackground(new java.awt.Color(204, 204, 204));
        jpasswd.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jpasswd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jpasswd.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpasswd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpasswdKeyPressed(evt);
            }
        });
        getContentPane().add(jpasswd);
        jpasswd.setBounds(340, 250, 170, 28);

        jIngresar1.setBackground(new java.awt.Color(102, 153, 0));
        jIngresar1.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        jIngresar1.setForeground(new java.awt.Color(255, 255, 255));
        jIngresar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jIngresar1.setText("Acerque la tarjeta al sensor");
        jIngresar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jIngresar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jIngresar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jIngresar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jIngresar1MouseEntered(evt);
            }
        });
        getContentPane().add(jIngresar1);
        jIngresar1.setBounds(320, 290, 200, 30);

        jIngresar.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jIngresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/64x64/ok.png"))); // NOI18N
        jIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jIngresar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jIngresarMouseClicked(evt);
            }
        });
        getContentPane().add(jIngresar);
        jIngresar.setBounds(330, 320, 90, 70);

        jLabel2.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Control de Acceso");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 50, 750, 50);

        jCerrar.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jCerrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/64x64/cancelar.png"))); // NOI18N
        jCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(jCerrar);
        jCerrar.setBounds(420, 320, 90, 70);

        fondo.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background/fondo.jpg"))); // NOI18N
        getContentPane().add(fondo);
        fondo.setBounds(0, 0, 960, 420);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jtxtNickNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtNickNameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jpasswd.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }//GEN-LAST:event_jtxtNickNameKeyPressed

    private void jpasswdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpasswdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Ingresar();
        }
    }//GEN-LAST:event_jpasswdKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            VerificarDatos();        // TODO add your handling code here:
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al iniciar sistema. El programa se cerrará", "Codigo de Error: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowOpened

    private void jIngresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jIngresarMouseClicked
        Ingresar();
    }//GEN-LAST:event_jIngresarMouseClicked

    private void jCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCerrarMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jCerrarMouseClicked

    private void jIngresar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jIngresar1MouseClicked
        int Opcion = JOptionPane.showConfirmDialog(null, "Esta a punto de iniciar sesión a través de NFC. ¿Desea continuar?", "MENSAJE DEL SISTEMA", JOptionPane.YES_NO_OPTION);
        if (Opcion == 0) {
            if (escaner.EscanearNFCLogin() == true) {// Activa lector y espera leer una tarjeta
                if (escaner.getDato().length() > 0) {
                    Buscar(); // Recibe dato y procede a buscar informacion en base de datos
                }
            }

        }
    }//GEN-LAST:event_jIngresar1MouseClicked

    private void jIngresar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jIngresar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jIngresar1MouseEntered
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
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ICON;
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel jCerrar;
    private javax.swing.JLabel jIngresar;
    private javax.swing.JLabel jIngresar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jpasswd;
    private javax.swing.JTextField jtxtNickName;
    // End of variables declaration//GEN-END:variables

    public void Limpiar() {
        jtxtNickName.setText("");
        jpasswd.setText("");
        jtxtNickName.grabFocus();
    }

    public void VerificarDatos() throws SQLException {
        try {
            if (login.VerificarUsuarios(frmSplash.con) != true) { // Se verifica al iniciar el formulario de que no existan datos para iniciar sesion
                JOptionPane.showMessageDialog(null, "No existen datos para Iniciar Sesión. EJECUTANDO FORMULARIO PARA REGISTRO DE ADMINISTRADOR", "Error", JOptionPane.WARNING_MESSAGE);
                timer.cancel();
                timer.purge();
                escaner = null;
                System.gc();
                new frmRegistroAdmin().setVisible(true); //Abrir MENU PRINCIPAL ADMINISTRADOR
                this.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hubo un error inesperado. Consulte un administrador", "Codigo de Error: " + ex.getErrorCode(), JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Se detecto una ejecución indebida. El programa se cerrará", "ERROR", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }

    public void Ingresar() {
        if (jtxtNickName.getText().length() > 0 && jpasswd.getText().length() > 0) {

        }
        try {
            login.setUser(jtxtNickName.getText()); //captura usuario
            login.setPass(jpasswd.getText()); //captura contraseña
            if (login.Buscar(frmSplash.con) == true) { //Validar busqueda de usuario y contraseña

                pers.setIdPersonas(login.getIdPersonas()); // captura id persona
                pers.BuscarRolPersona(frmSplash.con); // se busca persona

                if (pers.getRol_idrol() == 1) { // se obtiene el ROL
                    new frmMenuAdmin().setVisible(true); //Abrir Menu admin
                    this.dispose();
                } else {
                    if (pers.getRol_idrol() == 2) {
                        new frmMenuVigilante().setVisible(true); //Abrir Vista personas
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "No tiene permisos para ingresar al sistema", "Error al Iniciar Sesión", JOptionPane.WARNING_MESSAGE);
                        Limpiar();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nombre de Usuario o Contraseña incorrectos", "Error al Iniciar Sesión", JOptionPane.WARNING_MESSAGE);
                Limpiar();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error inesperado. Consulte un administrador", "Codigo de error: " + e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }

    public void Buscar() { // Iniciar sesion por NFC
        try {
            nfc.setCodigo(escaner.getDato()); // Activa sensor, escanea y obtiene el codigo de la tarjeta
            if (nfc.BuscarNFC(frmSplash.con) == true) { //Verifica existencia o valida codigo
                pers.setNFC_id(nfc.getIdNFC()); // Capturamos id NFC asociado con el codigo
                if (pers.BuscarPersonaNFC(frmSplash.con) == true) { // Valida existencia en base de datos
                    if (pers.getRol_idrol() == 1) { // valida ROL
                        if (nfc.isActivo() == 0) { //Verifica si el NFC esta activo
                            JOptionPane.showMessageDialog(null, "Tarjeta desactivada", "ERROR", JOptionPane.WARNING_MESSAGE);
                            Limpiar();
                        } else {
                            new frmMenuAdmin().setVisible(true);
                            timer.cancel();
                            timer.purge();
                            escaner = null;
                            System.gc();
                            dispose();
                        }

                    } else {
                        if (pers.getRol_idrol() == 2) {
                            if (nfc.isActivo() == 0) { //Verifica si el NFC esta activo
                                JOptionPane.showMessageDialog(null, "Tarjeta desactivada", "ERROR", JOptionPane.WARNING_MESSAGE);
                                Limpiar();
                            } else {
                                new frmMenuVigilante().setVisible(true);
                                timer.cancel();
                                timer.purge();
                                escaner = null;
                                System.gc();

                                dispose();
                            }

                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encotraron datos", "ERROR", JOptionPane.WARNING_MESSAGE);
                    Limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encotraron datos", "ERROR", JOptionPane.WARNING_MESSAGE);
                Limpiar();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void IniciarPrograma() {
        if (escaner.EscanearNFC() == true) {// Activa lector y espera leer una tarjeta
            if (escaner.getDato().length() > 0) {
                Buscar(); // Recibe dato y procede a buscar informacion en base de datos

            }

        }

    }
}

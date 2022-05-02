/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Clases.Login;
import Clases.NFC;
import Clases.Personas;
import Controlador.ConexionNFC;
import Controlador.Funciones;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Fernando Rhenals
 */
public class frmControlAcceso extends javax.swing.JInternalFrame {

    Login log = new Login();
    Personas pers = new Personas();
    NFC nfc = new NFC();
    ConexionNFC escaner = new ConexionNFC();
    String id, rol;

    private static ServerSocket SERVER_SOCKET;
    int Activo = 0;
    int idNFC;
    private TableRowSorter trsfiltro;
    String filtro;
    String codigoNFC = "";

    Calendar c = new GregorianCalendar();

    String dia = Integer.toString(c.get(Calendar.DATE));
    String mes = Integer.toString(c.get(Calendar.MONTH));
    String annio = Integer.toString(c.get(Calendar.YEAR));

    /**
     * Creates new form frmControlAcceso
     */
    public frmControlAcceso() {
        initComponents();
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                String cadena = jTextField1.getText();
                jTextField1.setText(cadena);
                repaint();
                filtro();
            }
        });
    }

    public void Control() {
        try {
            SERVER_SOCKET = new ServerSocket(1002);

        } catch (IOException x) {
            JOptionPane.showMessageDialog(null, "Otra instancia de la aplicación se está ejecutando...", "Error", JOptionPane.WARNING_MESSAGE);
            this.dispose();
        }

    }

    public void CerrarSocket() {
        try {
            SERVER_SOCKET.close();
        } catch (IOException x) {
            JOptionPane.showMessageDialog(null, "Error al cerrar. Consulte administrador", "Error", JOptionPane.WARNING_MESSAGE);
            this.dispose();
        }

    }

    public void GuardarUsuarioNFC() {

        boolean pResult, sResult = false, tResult = false;

        try {
            nfc.setCodigo(codigoNFC);
            nfc.setActivo(1);
            nfc.setFecha_activo(dia + "/" + mes + "/" + annio);
            nfc.GuardarNFC(frmSplash.con);
            nfc.BuscarPorIDultimoRegistro(frmSplash.con);
            pers.setNFC_id(nfc.getIdNFC());
            pResult = true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Se ha presentado un error. Codigo de error: " + e.getErrorCode(), "ERROR AL GUARDAR NFC", JOptionPane.ERROR_MESSAGE);
            System.out.println("ERROR NFC");
            System.out.println(e.getMessage());
            pResult = false;
        }

        if (pResult == true) {
            Date fecha = jDateChooser1.getDate();

            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            String fecha2 = f.format(fecha);
            File ruta = new File(txtFOTO.getText());
            try {
                pers.setIdPersonas(txtCedula.getText());
                pers.setTipo_identificacion(jcbTipoIden.getSelectedItem().toString());
                pers.setNombre(txtNombre.getText());
                pers.setApellido(txtApellidos.getText());
                pers.setDireccion(txtDireccion.getText());
                pers.setEmail(txtEmail.getText());
                pers.setFecha_nacimiento(fecha2);
                pers.setTelefono(txtTelefono.getText());

                if (jRadioAdmin.isSelected() == true) {
                    pers.setRol_idrol(1);
                } else {
                    pers.setRol_idrol(2);
                }

                try {
                    byte[] icono = new byte[(int) ruta.length()];
                    InputStream input = new FileInputStream(ruta);
                    input.read(icono);
                    pers.setFoto(icono);
                } catch (IOException ex) {
                    pers.setFoto(null);
                }

                pers.GuardarPersona(frmSplash.con);
                sResult = true;

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Se ha presentado un error. Codigo de error: " + e.getMessage(), "ERROR AL GUARDAR PERSONA", JOptionPane.ERROR_MESSAGE);
                System.out.println("ERROR PERSONA");
                System.out.println(e.getMessage());
                sResult = false;
            }
        }

        if (pResult == true && sResult == true) {
            try {
                log.setUser(txtUsuario.getText());
                log.setPass(txtContraseña.getText());
                log.setIdPersonas(txtCedula.getText());
                log.GuardarUsuario(frmSplash.con);
                JOptionPane.showMessageDialog(null, "REGISTRO EXITOSO");
                RESET();
                tResult = true;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Se ha presentado un error. Codigo de error: " + e.getMessage(), "ERROR AL GUARDAR LOGIN", JOptionPane.ERROR_MESSAGE);
                System.out.println("ERROR LOGIN");
                System.out.println(e.getMessage());
                tResult = false;
            }

        }

        if (pResult == true && sResult == false) {
            try {
                nfc.EliminarUltimoRegistro(frmSplash.con);
            } catch (SQLException e) {
                System.out.println("ERROR ELIMINANDO");
                System.out.println(e.getMessage());
            }
        }

        if (pResult == true && sResult == true && tResult == false) {
            try {
                nfc.EliminarUltimoRegistro(frmSplash.con);
            } catch (SQLException e) {
                System.out.println("ERROR ELIMINANDO");
                System.out.println(e.getMessage());
            }
        }

    }

    public void GuardarUsuario() {
        Date fecha = jDateChooser1.getDate();

        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String fecha2 = f.format(fecha);

        boolean result = true;

        File ruta = new File(txtFOTO.getText());
        try {
            pers.setIdPersonas(txtCedula.getText());
            pers.setTipo_identificacion(jcbTipoIden.getSelectedItem().toString());
            pers.setNombre(txtNombre.getText());
            pers.setApellido(txtApellidos.getText());
            pers.setDireccion(txtDireccion.getText());
            pers.setEmail(txtEmail.getText());
            pers.setFecha_nacimiento(fecha2);
            pers.setTelefono(txtTelefono.getText());
            if (jRadioAdmin.isSelected() == true) {
                pers.setRol_idrol(1);
            } else {
                pers.setRol_idrol(2);
            }

            try {
                byte[] icono = new byte[(int) ruta.length()];
                InputStream input = new FileInputStream(ruta);
                input.read(icono);
                pers.setFoto(icono);
            } catch (IOException ex) {
                pers.setFoto(null);
            }

            try {
                nfc.setIdNFC(idNFC);
                nfc.setCodigo(codigoNFC);
                nfc.setActivo(1);
                nfc.setFecha_activo(dia + "/" + mes + "/" + annio);
                nfc.GuardarNFC(frmSplash.con);
                pers.setNFC_id(idNFC);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Se ha presentado un error. Codigo de error: " + e.getErrorCode(), "ERROR AL GUARDAR NFC", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
                result = false;
            }

            if (result == true) {
                pers.GuardarPersona(frmSplash.con);
                JOptionPane.showMessageDialog(rootPane, "OK");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Se ha presentado un error. Codigo de error: " + e.getErrorCode(), "ERROR AL GUARDAR PERSONA", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
            RESET();
            result = false;
        }

        if (result == true) {
            try {
                log.setUser(txtUsuario.getText());
                log.setPass(txtContraseña.getText());
                log.setIdPersonas(txtCedula.getText());
                log.GuardarUsuario(frmSplash.con);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Se ha presentado un error: ", "CODIGO DE ERROR: " + e.getErrorCode(), JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
                result = false;
            }
        }
        if (result == false) {
            try {
                nfc.EliminarUltimoRegistro(frmSplash.con);
            } catch (SQLException e) {

            }
        }

    }

    public void GenerarID_NFC() { // Genera nuevo ID para NFC a Registrar

        try {
            nfc.BuscarPorIDultimoRegistro(frmSplash.con);
            idNFC = nfc.getIdNFC() + 1;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se presento un problema al generar un ID", "CODIGO DE ERROR: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtConfirmarContraseña = new javax.swing.JPasswordField();
        txtContraseña = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jRadioAdmin = new javax.swing.JRadioButton();
        jRadioVigilante = new javax.swing.JRadioButton();
        jPanel9 = new javax.swing.JPanel();
        jcNFC = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        botonNuevo = new javax.swing.JButton();
        botonModificar = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jcbOpcion = new javax.swing.JComboBox<String>();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jcbTipoIden = new javax.swing.JComboBox<String>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        txtFOTO = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jFoto = new javax.swing.JLabel();

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(51, 49, 50));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel5.setMaximumSize(new java.awt.Dimension(800, 600));
        jPanel5.setMinimumSize(new java.awt.Dimension(800, 600));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("CONTROL DE USUARIOS");

        jPanel7.setBackground(new java.awt.Color(51, 49, 50));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos de Login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Usuario:");

        txtUsuario.setBackground(new java.awt.Color(204, 204, 204));
        txtUsuario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Contraseña:");

        txtConfirmarContraseña.setBackground(new java.awt.Color(204, 204, 204));
        txtConfirmarContraseña.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtConfirmarContraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtConfirmarContraseña.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));

        txtContraseña.setBackground(new java.awt.Color(204, 204, 204));
        txtContraseña.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtContraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtContraseña.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Confirmar:");

        jPanel8.setBackground(new java.awt.Color(51, 49, 50));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tipo de Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel8.setForeground(new java.awt.Color(255, 255, 255));

        jRadioAdmin.setBackground(new java.awt.Color(51, 49, 50));
        jRadioAdmin.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jRadioAdmin.setForeground(new java.awt.Color(255, 255, 255));
        jRadioAdmin.setText("Admin");
        jRadioAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioAdminActionPerformed(evt);
            }
        });

        jRadioVigilante.setBackground(new java.awt.Color(51, 49, 50));
        jRadioVigilante.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jRadioVigilante.setForeground(new java.awt.Color(255, 255, 255));
        jRadioVigilante.setText("Vigilante");
        jRadioVigilante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioVigilanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioAdmin)
                .addGap(10, 10, 10)
                .addComponent(jRadioVigilante))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRadioAdmin)
                .addComponent(jRadioVigilante))
        );

        jPanel9.setBackground(new java.awt.Color(51, 49, 50));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Registro de Tarjeta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N

        jcNFC.setBackground(new java.awt.Color(51, 49, 50));
        jcNFC.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jcNFC.setForeground(new java.awt.Color(255, 255, 255));
        jcNFC.setText("Activar NFC");
        jcNFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcNFCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcNFC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcNFC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtConfirmarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtConfirmarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(51, 49, 50));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        botonNuevo.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        botonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_document-new_23212.png"))); // NOI18N
        botonNuevo.setText("NUEVO");
        botonNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoActionPerformed(evt);
            }
        });

        botonModificar.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        botonModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/iconfinder_edit-find-replace_118921.png"))); // NOI18N
        botonModificar.setText("EDITAR");
        botonModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });

        botonGuardar.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        botonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_floppy_285657.png"))); // NOI18N
        botonGuardar.setText("GUARDAR");
        botonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });

        botonEliminar.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_Cancel__Red_34208.png"))); // NOI18N
        botonEliminar.setText("ELIMINAR");
        botonEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        botonSalir.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        botonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_xfce-system-exit_23651.png"))); // NOI18N
        botonSalir.setText("CERRAR");
        botonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonSalir)
                .addGap(55, 55, 55))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setBackground(new java.awt.Color(51, 49, 50));
        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.setBackground(new java.awt.Color(51, 49, 50));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jTextField1.setBackground(new java.awt.Color(204, 204, 204));
        jTextField1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jcbOpcion.setBackground(new java.awt.Color(51, 49, 50));
        jcbOpcion.setEditable(true);
        jcbOpcion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jcbOpcion.setForeground(new java.awt.Color(255, 255, 255));
        jcbOpcion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Usuario", "Id Login", "Contraseña", "Id Persona" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jcbOpcion, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbOpcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(51, 49, 50));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Identificación:");

        txtCedula.setBackground(new java.awt.Color(204, 204, 204));
        txtCedula.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtCedula.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCedula.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCedulaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tipo de Identificación");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nombres:");

        txtNombre.setBackground(new java.awt.Color(204, 204, 204));
        txtNombre.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreFocusLost(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Apellidos:");

        txtApellidos.setBackground(new java.awt.Color(204, 204, 204));
        txtApellidos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtApellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtApellidos.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        txtApellidos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtApellidosFocusLost(evt);
            }
        });
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtApellidosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Dirección:");

        txtDireccion.setBackground(new java.awt.Color(204, 204, 204));
        txtDireccion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccion.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        txtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDireccionFocusLost(evt);
            }
        });
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDireccionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Telefono:");

        txtTelefono.setBackground(new java.awt.Color(204, 204, 204));
        txtTelefono.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Email:");

        txtEmail.setBackground(new java.awt.Color(204, 204, 204));
        txtEmail.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmail.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Fecha de Nacimiento:");

        jcbTipoIden.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jcbTipoIden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cedula de Ciudadania", "Tarjeta de Identidad", "Cedula de Extranjeria", "Nit", "OTRO" }));
        jcbTipoIden.setToolTipText("");
        jcbTipoIden.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));

        jDateChooser1.setBackground(new java.awt.Color(204, 204, 204));
        jDateChooser1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        jDateChooser1.setPreferredSize(new java.awt.Dimension(4, 23));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jcbTipoIden, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtApellidos)
                                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jcbTipoIden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 49, 50));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Foto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        txtFOTO.setBackground(new java.awt.Color(204, 204, 204));
        txtFOTO.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtFOTO.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFOTO.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        txtFOTO.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFOTO.setEnabled(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/16x16/search.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jFoto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtFOTO, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFOTO, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1)
                                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Control();
        try {
            Llenarlista();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Se detecto una ejecución indebida. El programa se cerrará", "ERROR", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif");
        j.setFileFilter(fil);

        int s = j.showOpenDialog(this);
        if (s == JFileChooser.APPROVE_OPTION) {
            String ruta = j.getSelectedFile().getAbsolutePath();
            txtFOTO.setText(ruta);
            rsscalelabel.RSScaleLabel.setScaleLabel(jFoto, ruta);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped
        Funciones.CantidadDigitos(evt, txtEmail, 50);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyTyped

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        Funciones.EstadoBotonUltimo(evt, txtEmail, botonGuardar, botonModificar, Activo);       // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyReleased

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        Funciones.PasarTextABoton(evt, txtEmail, botonGuardar, botonModificar, botonEliminar, "Falta rellenar el campo de Email");      // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        Funciones.Numerico(evt);
        Funciones.CantidadDigitos(evt, txtTelefono, 12);
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtTelefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyReleased
        Funciones.EstadoBotonUltimo(evt, txtTelefono, botonGuardar, botonModificar, Activo);
    }//GEN-LAST:event_txtTelefonoKeyReleased

    private void txtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyPressed
        Funciones.PasarTextABoton(evt, txtTelefono, botonGuardar, botonModificar, botonEliminar, "Falta rellenar el campo de Telefóno");

    }//GEN-LAST:event_txtTelefonoKeyPressed

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        Funciones.CantidadDigitos(evt, txtDireccion, 50);           // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyReleased
        Funciones.EstadoBotonUltimo(evt, txtDireccion, botonGuardar, botonModificar, Activo);
    }//GEN-LAST:event_txtDireccionKeyReleased

    private void txtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyPressed
        Funciones.PasarTextABoton(evt, txtDireccion, botonGuardar, botonModificar, botonEliminar, "Falta rellenar el campo de Dirección");
    }//GEN-LAST:event_txtDireccionKeyPressed

    private void txtDireccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusLost
        if (txtDireccion.getText().length() > 0) {
            Funciones.PasarTextAPrimeraMayuscula(evt, txtDireccion);
        }
    }//GEN-LAST:event_txtDireccionFocusLost

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        Funciones.CantidadDigitos(evt, txtApellidos, 45);
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtApellidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyReleased
        Funciones.EstadoBotonUltimo(evt, txtApellidos, botonGuardar, botonModificar, Activo);
    }//GEN-LAST:event_txtApellidosKeyReleased

    private void txtApellidosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyPressed
        Funciones.PasarTextABoton(evt, txtApellidos, botonGuardar, botonModificar, botonEliminar, "Falta rellenar el campo de Apellidos");
    }//GEN-LAST:event_txtApellidosKeyPressed

    private void txtApellidosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApellidosFocusLost
        if (txtApellidos.getText().length() > 0) {
            Funciones.PasarTextAPrimeraMayuscula(evt, txtApellidos);
        }
    }//GEN-LAST:event_txtApellidosFocusLost

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        Funciones.CantidadDigitos(evt, txtNombre, 45);
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        Funciones.EstadoBotonUltimo(evt, txtNombre, botonGuardar, botonModificar, Activo);
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        Funciones.PasarTextABoton(evt, txtNombre, botonGuardar, botonModificar, botonEliminar, "Falta rellenar el campo de Nombre");
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusLost
        if (txtNombre.getText().length() > 0) {
            Funciones.PasarTextAPrimeraMayuscula(evt, txtNombre);
        }
    }//GEN-LAST:event_txtNombreFocusLost

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped

        Funciones.Numerico(evt);
        Funciones.CantidadDigitos(evt, txtCedula, 12);
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void txtCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyReleased
        Funciones.EstadoBotonUltimo(evt, txtCedula, botonGuardar, botonModificar, Activo);
    }//GEN-LAST:event_txtCedulaKeyReleased

    private void txtCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyPressed
        Funciones.PasarTextABoton(evt, txtCedula, botonGuardar, botonModificar, botonEliminar, "Falta rellenar el campo de Cédula");
    }//GEN-LAST:event_txtCedulaKeyPressed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        trsfiltro = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String fechaNac = "";
        Date fecha;

        if (jTable1.getSelectedColumn() != -1) {
            id = ((String) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            txtUsuario.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 1));
            txtContraseña.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 2));
            txtConfirmarContraseña.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 2));
            txtCedula.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 3));
            txtNombre.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 4));
            txtApellidos.setText((String) jTable1.getValueAt(jTable1.getSelectedRow(), 5));
            rol = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 6);

            if (rol == "ADMINISTRADOR") {
                jRadioAdmin.setSelected(true);
                jRadioVigilante.setSelected(false);
            } else {
                jRadioAdmin.setSelected(false);
                jRadioVigilante.setSelected(true);
            }

            try {
                log.setIdPersonas(txtCedula.getText());
                log.setNombre(txtNombre.getText());
                log.setApellido(txtApellidos.getText());
                pers.setIdPersonas(txtCedula.getText());
                pers.BuscarPersona(frmSplash.con);
                txtTelefono.setText(pers.getTelefono());
                txtDireccion.setText(pers.getDireccion());
                txtEmail.setText(pers.getEmail());
                fechaNac = pers.getFecha_nacimiento();
                try {
                    fecha = format.parse(fechaNac);
                    jDateChooser1.setDate(fecha);
                } catch (ParseException ex) {
                    System.err.println(ex.getMessage());
                }

                try {
                    byte[] bi = pers.getFoto();
                    BufferedImage image = null;
                    InputStream in = new ByteArrayInputStream(bi);
                    image = ImageIO.read(in);
                    ImageIcon imgi = new ImageIcon(image.getScaledInstance(161, 189, 0));
                    jFoto.setIcon(imgi);
                } catch (Exception ex) {
                    jFoto.setText("No imagen");

                }

                if (log.VerificarUsuariosConNFC(frmSplash.con) == true) {
                    if (log.VerificarUsuariosEstadoNFC(frmSplash.con) == true) {
                        JOptionPane.showMessageDialog(null, "El estado de la tarjeta NFC para el usuario " + log.getNombre() + " " + log.getApellido() + " se encuentra activo", "MENSAJE DEL SISTEMA", JOptionPane.INFORMATION_MESSAGE);
                        jcNFC.setSelected(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "El estado de la tarjeta NFC para el usuario " + log.getNombre() + " " + log.getApellido() + " no se encuentra activo", "MENSAJE DEL SISTEMA", JOptionPane.WARNING_MESSAGE);
                        jcNFC.setSelected(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha asignado tarjeta NFC al usuario", "MENSAJE DEL SISTEMA", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {

            }
            Activo = 1;
            Funciones.PasarGrilla(evt, jTable1, botonGuardar, botonModificar, botonEliminar, Activo);
        }
    }//GEN-LAST:event_jTable1MousePressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        CerrarSocket();
        this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        if (jcbTipoIden.getSelectedIndex() >= 0 && txtCedula.getText().length() > 0
                && txtNombre.getText().length() > 0 && txtApellidos.getText().length() > 0 && txtDireccion.getText().length() > 0
                && txtEmail.getText().length() > 0 && jDateChooser1.getDate() != null && txtUsuario.getText().length() > 0
                && txtContraseña.getPassword().length > 0 && txtConfirmarContraseña.getPassword().length > 0 && jcNFC.isSelected() == true && codigoNFC.length() > 0) {

            if (this.txtContraseña.getParent().equals(this.txtConfirmarContraseña.getParent())) {
                GuardarUsuarioNFC();
                RESET();

            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
            }
        } else {

            if (jcbTipoIden.getSelectedIndex() >= 0 && txtCedula.getText().length() > 0
                    && txtNombre.getText().length() > 0 && txtApellidos.getText().length() > 0 && txtDireccion.getText().length() > 0
                    && txtEmail.getText().length() > 0 && jDateChooser1.getDate() != null && txtUsuario.getText().length() > 0
                    && txtContraseña.getPassword().length > 0 && txtConfirmarContraseña.getPassword().length > 0 && jcNFC.isSelected() == false) {

                if (this.txtContraseña.getParent().equals(this.txtConfirmarContraseña.getParent())) {
                    GuardarUsuario();
                    RESET();
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente. Inicie Sesión", "MENSAJE DEL SISTEMA", JOptionPane.INFORMATION_MESSAGE);
                    new frmLogin().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Faltan Datos");
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_botonGuardarActionPerformed

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
        try {
            log.setIdLogin(Integer.parseInt(id));
            log.setIdPersonas(txtCedula.getText());
            log.setUser(txtUsuario.getText());
            log.setPass(txtContraseña.getText());
            try {
                if (jRadioAdmin.isSelected() == true) {
                    log.setIdRol(1);
                } else {
                    log.setIdRol(2);
                }
                log.modificar(frmSplash.con);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

            if (jcNFC.isSelected() == true) {
                pers.setIdPersonas(log.getIdPersonas());
                pers.BuscaridNFCPersona(frmSplash.con);
                log.setNfc(pers.getNFC_id());
                log.ActivarNFCUsuario(frmSplash.con);
            } else {
                pers.setIdPersonas(log.getIdPersonas());
                pers.BuscaridNFCPersona(frmSplash.con);
                log.setNfc(pers.getNFC_id());
                log.DesactivarNFCUsuario(frmSplash.con);
            }

            int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro de modificar el registro " + txtCedula.getText() + " ?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (JOptionPane.OK_OPTION == resp) {
                log.modificar(frmSplash.con);
                RESET();

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_botonModificarActionPerformed

    private void jRadioVigilanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioVigilanteActionPerformed
        jRadioAdmin.setSelected(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioVigilanteActionPerformed

    private void jRadioAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioAdminActionPerformed
        jRadioVigilante.setSelected(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioAdminActionPerformed

    private void jcNFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcNFCActionPerformed


    }//GEN-LAST:event_jcNFCActionPerformed

    private void botonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoActionPerformed
        RESET();        // TODO add your handling code here:
    }//GEN-LAST:event_botonNuevoActionPerformed

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        Funciones.PasarTextABoton(evt, txtUsuario, botonGuardar, botonModificar, botonEliminar, "Falta rellenar el campo de Usuario");
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyReleased
        Funciones.EstadoBotonUltimo(evt, txtUsuario, botonGuardar, botonModificar, Activo);        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JButton botonSalir;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jFoto;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioAdmin;
    private javax.swing.JRadioButton jRadioVigilante;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JCheckBox jcNFC;
    private javax.swing.JComboBox<String> jcbOpcion;
    private javax.swing.JComboBox<String> jcbTipoIden;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JPasswordField txtConfirmarContraseña;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFOTO;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
    private void Llenarlista() {

        String Cabecera[] = {"Id Login", "Usuario", "Contraseña", "Id Persona", "Nombres", "Apellidos", "Rol"};
        DefaultTableModel dtm = new DefaultTableModel(null, Cabecera);
        jTable1.setModel(dtm);
        try {
            LinkedList<Login> list = log.Listar(frmSplash.con);
            Iterator ilist = list.iterator();
            String fila[] = new String[7];
            while (ilist.hasNext()) {
                Login log = (Login) ilist.next();
                fila[0] = String.valueOf(log.getIdLogin());
                fila[1] = log.getUser();
                fila[2] = log.getPass();
                fila[3] = String.valueOf(log.getIdPersonas());
                fila[4] = log.getNombre();
                fila[5] = log.getApellido();
                if (log.getIdRol() == 1) {
                    fila[6] = "ADMINISTRADOR";
                } else {
                    fila[6] = "VIGILANTE";
                }

                dtm = (DefaultTableModel) jTable1.getModel();
                dtm.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        ValidarDatosTabla();

    }

    public void ValidarDatosTabla() {
        if (jTable1.getRowCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(150);
        } else {
            JOptionPane.showMessageDialog(null, "No hay registros");
            //this.dispose();
        }

    }

    public void filtro() {
        String opcion = jcbOpcion.getSelectedItem().toString();

        if (opcion == "Id Login") {
            filtro = jTextField1.getText();
            trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), 0));
        }
        if (opcion == "Usuario") {
            filtro = jTextField1.getText();
            trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), 1));
        }
        if (opcion == "Contraseña") {
            filtro = jTextField1.getText();
            trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), 2));
        }

        if (opcion == "Id Persona") {
            filtro = jTextField1.getText();
            trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), 3));
        }
    }

    public void RESET() {
        Funciones.LimpiarText(jPanel3);
        Funciones.LimpiarText(jPanel7);
        Funciones.LimpiarText(jPanel1);
        Funciones.LimpiarText(jPanel4);
        Funciones.Botones(jPanel6, false);
        botonNuevo.setEnabled(true);
        botonEliminar.setEnabled(true);
        jFoto.setIcon(null);
        jDateChooser1.setDate(null);
        jcNFC.setSelected(false);
        txtContraseña.setText("");
        txtConfirmarContraseña.setText("");
        jRadioAdmin.setSelected(false);
        jRadioVigilante.setSelected(false);
        txtCedula.grabFocus();

        Llenarlista();

    }
}

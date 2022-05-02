/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Clases.NFC;
import Clases.Personas;
import Clases.Programa;
import Controlador.ConexionNFC;
import Controlador.Funciones;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Fernando Rhenals
 */
public class frmPersonas extends javax.swing.JInternalFrame {

    Personas pers = new Personas();
    private static ServerSocket SERVER_SOCKET;
    NFC nfc = new NFC();
    Programa prog = new Programa();
    ConexionNFC escaner = new ConexionNFC();
    Calendar c = new GregorianCalendar();

    String dia = Integer.toString(c.get(Calendar.DATE));
    String mes = Integer.toString(c.get(Calendar.MONTH));
    String annio = Integer.toString(c.get(Calendar.YEAR));

    int idNFC = 0;
    String codigoNFC = "";
    int Activo = 0;

    /**
     * Creates new form frmPersonass
     */
    public frmPersonas() {
        initComponents();
        ((JTextField) this.jDNacimiento.getDateEditor()).setEditable(false);

    }

    private void LlenarComboRoles() { //Carga los Roles de la Base de Datos en el JComboBox
        try {
            LinkedList<Personas> list = pers.CargarRoles(frmSplash.con);
            jcbRol.removeAllItems();
            jcbRol.addItem("");
            for (Personas car : list) {
                jcbRol.addItem(String.valueOf(car.getRol_idrol()));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se presento un problema al cargar los roles", "CODIGO DE ERROR: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void LlenarComboProgramas() { //Carga los programas o carreras de la Base de Datos en el JComboBox
        try {
            LinkedList<Programa> list = prog.ListarProgramas(frmSplash.con);
            jcbFicha.removeAllItems();
            jcbFicha.addItem("");
            for (Programa car : list) {
                jcbFicha.addItem(String.valueOf(car.getNumero_ficha()));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se presento un problema al cargar los programas", "CODIGO DE ERROR: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void RESET() { // Limpia todos los componentes del frame
        Funciones.LimpiarText(jPanel1);
        Funciones.LimpiarText(jPanel3);
        Funciones.LimpiarText(jPanel5);
        Funciones.LimpiarFormatoText(jPanel1);
        Funciones.LimpiarFormatoText(jPanel5);
        Funciones.Botones(jPanel4, false);
        botonNuevo.setEnabled(true);
        botonSalir.setEnabled(true);
        jFoto.setIcon(null);
        jcbTipoIden.setSelectedIndex(0);
        jcbEstado.setSelectedIndex(0);
        jDNacimiento.setDate(null);
        jDFechaVencimiento.setDate(null);
        jLRol.setText("");
        jPrograma.setText("");
        jcNFC.setSelected(false);
        idNFC = 0;
        codigoNFC = "";
        Activo = 0;
        LlenarComboRoles();
        LlenarComboProgramas();
        GenerarID_NFC();
        txtCedula.grabFocus();
    }

    public void GenerarID_NFC() { // Genera nuevo ID para NFC a Registrar

        try {
            nfc.BuscarPorIDultimoRegistro(frmSplash.con);
            idNFC = nfc.getIdNFC() + 1;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se presento un problema al generar un ID", "CODIGO DE ERROR: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void GuardarConNFCAprendiz() { // Registra aprendiz y le asigna un NFC y una ficha del programa

        File ruta = new File(txtFOTO.getText());
        String fechaHoy = dia + "/" + mes + "/" + annio;
        Date fechaNac = jDNacimiento.getDate();
        Date fechaVenc = jDFechaVencimiento.getDate();
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String fecha2 = f.format(fechaNac);
        String fecha3 = f.format(fechaVenc);
        boolean pResult, sResult = false;

        try {
            nfc.setCodigo(codigoNFC);
            nfc.setActivo(jcbEstado.getSelectedIndex());
            nfc.setFecha_activo(fechaHoy);
            nfc.setFecha_inactivo(fecha3);
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

            try {
                pers.setIdPersonas(txtCedula.getText());
                pers.setTipo_identificacion(jcbTipoIden.getSelectedItem().toString());
                pers.setNombre(txtNombre.getText());
                pers.setApellido(txtApellidos.getText());
                pers.setDireccion(txtDireccion.getText());
                pers.setEmail(txtEmail.getText());
                pers.setFecha_nacimiento(fecha2);
                pers.setTelefono(txtTelefono.getText());
                pers.setNumero_ficha(jcbFicha.getSelectedItem().toString());
                pers.setRol_idrol(Integer.parseInt((String) jcbRol.getSelectedItem()));

                try {
                    byte[] icono = new byte[(int) ruta.length()];
                    InputStream input = new FileInputStream(ruta);
                    input.read(icono);
                    pers.setFoto(icono);
                } catch (IOException ex) {
                    pers.setFoto(null);
                }

                pers.GuardarAprendiz(frmSplash.con);
                sResult = true;

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Se ha presentado un error. Codigo de error: " + e.getMessage(), "ERROR AL GUARDAR PERSONA", JOptionPane.ERROR_MESSAGE);
                System.out.println("ERROR PERSONA");
                System.out.println(e.getMessage());
                sResult = false;
            }
        }

        if (pResult == true && sResult == false) {
            try {
                nfc.setIdNFC(pers.getNFC_id());
                nfc.EliminarNFC(frmSplash.con);
                RESET();
            } catch (SQLException e) {
                System.out.println("ERROR ELIMINANDO");
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "REGISTRO EXISTOSO");
            RESET();

        }
    }

//    private void GuardarSinNFCAprendiz() { // Registra aprendiz y le asigna un NFC y una ficha del programa
//
//        File ruta = new File(txtFOTO.getText());
//        Date fechaNac = jDNacimiento.getDate();
//        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
//        String fecha2 = f.format(fechaNac);
//
//        try {
//            pers.setIdPersonas(txtCedula.getText());
//            pers.setTipo_identificacion(jcbTipoIden.getSelectedItem().toString());
//            pers.setNombre(txtNombre.getText());
//            pers.setApellido(txtApellidos.getText());
//            pers.setDireccion(txtDireccion.getText());
//            pers.setEmail(txtEmail.getText());
//            pers.setTelefono(txtTelefono.getText());
//            pers.setFecha_nacimiento(fecha2);
//            pers.setNumero_ficha(jcbFicha.getSelectedItem().toString());
//            pers.setRol_idrol(Integer.parseInt((String) jcbRol.getSelectedItem()));
//
//            //VALIDA FOTO
//            try {
//                byte[] icono = new byte[(int) ruta.length()];
//                InputStream input = new FileInputStream(ruta);
//                input.read(icono);
//                pers.setFoto(icono);
//            } catch (IOException ex) {
//                pers.setFoto(null);
//            }
//            pers.GuardarAprendizSinNFC(frmSplash.con);
//            JOptionPane.showMessageDialog(null, "OK");
//            RESET();
//
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Se ha presentado un error: " +e.getErrorCode() + " " + e.getMessage(), "ERROR AL GUARDAR APRENDIZ SIN NFC" , JOptionPane.ERROR_MESSAGE);
//            RESET();
//
//        }
//
//    }
    private void GuardarConNFCPersona() { // Registra aprendiz y le asigna un NFC y una ficha del programa

        File ruta = new File(txtFOTO.getText());
        String fechaHoy = dia + "/" + mes + "/" + annio;
        Date fechaNac = jDNacimiento.getDate();
        Date fechaVenc = jDFechaVencimiento.getDate();
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String fecha2 = f.format(fechaNac);
        String fecha3 = f.format(fechaVenc);
        boolean pResult, sResult = false;

        try {
            nfc.setCodigo(codigoNFC);
            nfc.setActivo(jcbEstado.getSelectedIndex());
            nfc.setFecha_activo(fechaHoy);
            nfc.setFecha_inactivo(fecha3);
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

            try {
                pers.setIdPersonas(txtCedula.getText());
                pers.setTipo_identificacion(jcbTipoIden.getSelectedItem().toString());
                pers.setNombre(txtNombre.getText());
                pers.setApellido(txtApellidos.getText());
                pers.setDireccion(txtDireccion.getText());
                pers.setEmail(txtEmail.getText());
                pers.setFecha_nacimiento(fecha2);
                pers.setTelefono(txtTelefono.getText());
                pers.setRol_idrol(Integer.parseInt((String) jcbRol.getSelectedItem()));

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

        if (pResult == true && sResult == false) {
            try {
                nfc.setIdNFC(pers.getNFC_id());
                nfc.EliminarNFC(frmSplash.con);
                RESET();
            } catch (SQLException e) {
                System.out.println("ERROR ELIMINANDO");
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "REGISTRO EXISTOSO");
            RESET();

        }

    }

//    private void GuardarSinNFCPersona() { // Registra aprendiz y le asigna un NFC y una ficha del programa
//
//        File ruta = new File(txtFOTO.getText());
//        Date fechaNac = jDNacimiento.getDate();
//        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
//        String fecha2 = f.format(fechaNac);
//
//        try {
//            pers.setIdPersonas(txtCedula.getText());
//            pers.setTipo_identificacion(jcbTipoIden.getSelectedItem().toString());
//            pers.setNombre(txtNombre.getText());
//            pers.setApellido(txtApellidos.getText());
//            pers.setDireccion(txtDireccion.getText());
//            pers.setEmail(txtEmail.getText());
//            pers.setTelefono(txtTelefono.getText());
//            pers.setFecha_nacimiento(fecha2);
//            pers.setRol_idrol(Integer.parseInt((String) jcbRol.getSelectedItem()));
//
//            //VALIDA FOTO
//            try {
//                byte[] icono = new byte[(int) ruta.length()];
//                InputStream input = new FileInputStream(ruta);
//                input.read(icono);
//                pers.setFoto(icono);
//            } catch (IOException ex) {
//                pers.setFoto(null);
//            }
//            pers.GuardarPersonaSinNFC(frmSplash.con);
//            RESET();
//            JOptionPane.showMessageDialog(null, "OK");
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Se ha presentado un error: " +e.getErrorCode() + " " + e.getMessage(), "ERROR AL GUARDAR PERSONA SIN NFC" , JOptionPane.ERROR_MESSAGE);
//                RESET();
//        }
//
//    }
    public void Control() {
        try {
            SERVER_SOCKET = new ServerSocket(1000);

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jcbTipoIden = new javax.swing.JComboBox<String>();
        jPanel3 = new javax.swing.JPanel();
        txtFOTO = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jFoto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jcbFicha = new javax.swing.JComboBox<String>();
        jPrograma = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jcbRol = new javax.swing.JComboBox<String>();
        jLRol = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jDNacimiento = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jcbEstado = new javax.swing.JComboBox<String>();
        jLEstado = new javax.swing.JLabel();
        jcNFC = new javax.swing.JCheckBox();
        jDFechaVencimiento = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        botonGuardar = new javax.swing.JButton();
        botonNuevo = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();

        jScrollPane1.setViewportView(jEditorPane1);

        setBackground(new java.awt.Color(51, 49, 50));
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

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("REGISTRO DE PERSONAS");

        jPanel1.setBackground(new java.awt.Color(51, 49, 50));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

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

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tipo de Identificación");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nombres:");

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

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Dirección:");

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

        jPanel3.setBackground(new java.awt.Color(51, 49, 50));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Foto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        txtFOTO.setBackground(new java.awt.Color(204, 204, 204));
        txtFOTO.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtFOTO, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFOTO, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 49, 50));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Programas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Numero Ficha:");

        jcbFicha.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jcbFicha.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        jcbFicha.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        jcbFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbFichaActionPerformed(evt);
            }
        });

        jPrograma.setBackground(new java.awt.Color(51, 49, 50));
        jPrograma.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jPrograma.setForeground(new java.awt.Color(255, 255, 255));
        jPrograma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPrograma.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Nombre del Programa:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jcbFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 140, Short.MAX_VALUE))
                    .addComponent(jPrograma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jcbFicha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jPrograma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(51, 49, 50));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos de Rol", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Rol:");

        jcbRol.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jcbRol.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        jcbRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbRolActionPerformed(evt);
            }
        });

        jLRol.setBackground(new java.awt.Color(51, 49, 50));
        jLRol.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLRol.setForeground(new java.awt.Color(255, 255, 255));
        jLRol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLRol.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Tipo de Rol:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jcbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 131, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jcbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLRol, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jLabel22))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDNacimiento.setBackground(new java.awt.Color(204, 204, 204));
        jDNacimiento.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        jDNacimiento.setPreferredSize(new java.awt.Dimension(4, 23));
        jDNacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDNacimientoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jcbTipoIden, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jcbTipoIden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addComponent(jDNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(51, 49, 50));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Registro de Tarjeta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Fecha Vencimiento");

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Estado");

        jcbEstado.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jcbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1" }));
        jcbEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        jcbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEstadoActionPerformed(evt);
            }
        });

        jLEstado.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLEstado.setForeground(new java.awt.Color(255, 255, 255));
        jLEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jcNFC.setBackground(new java.awt.Color(51, 49, 50));
        jcNFC.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jcNFC.setForeground(new java.awt.Color(255, 255, 255));
        jcNFC.setText("Activar NFC");
        jcNFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcNFCActionPerformed(evt);
            }
        });

        jDFechaVencimiento.setBackground(new java.awt.Color(204, 204, 204));
        jDFechaVencimiento.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        jDFechaVencimiento.setPreferredSize(new java.awt.Dimension(4, 23));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jcNFC)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(jcNFC)))
                .addGap(10, 10, 10))
        );

        jPanel4.setBackground(new java.awt.Color(51, 49, 50));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        botonGuardar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        botonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_floppy_285657.png"))); // NOI18N
        botonGuardar.setText("GUARDAR");
        botonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });

        botonNuevo.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        botonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_document-new_23212.png"))); // NOI18N
        botonNuevo.setText("NUEVO");
        botonNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoActionPerformed(evt);
            }
        });

        botonSalir.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        botonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_Cancel__Red_34208.png"))); // NOI18N
        botonSalir.setText("CERRAR");
        botonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(botonGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonSalir)
                    .addComponent(botonNuevo)
                    .addComponent(botonGuardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped

        Funciones.Numerico(evt);
        Funciones.CantidadDigitos(evt, txtCedula, 12);
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void txtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusLost
        if (txtNombre.getText().length() > 0) {
            Funciones.PasarTextAPrimeraMayuscula(evt, txtNombre);
        }

    }//GEN-LAST:event_txtNombreFocusLost

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        Funciones.CantidadDigitos(evt, txtNombre, 45);
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApellidosFocusLost
        if (txtApellidos.getText().length() > 0) {
            Funciones.PasarTextAPrimeraMayuscula(evt, txtApellidos);
        }
    }//GEN-LAST:event_txtApellidosFocusLost

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        Funciones.CantidadDigitos(evt, txtApellidos, 45);
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtDireccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusLost
        if (txtDireccion.getText().length() > 0) {
            Funciones.PasarTextAPrimeraMayuscula(evt, txtDireccion);
        }
    }//GEN-LAST:event_txtDireccionFocusLost

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        Funciones.CantidadDigitos(evt, txtDireccion, 50);           // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        Funciones.Numerico(evt);
        Funciones.CantidadDigitos(evt, txtTelefono, 12);
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped
        Funciones.CantidadDigitos(evt, txtEmail, 50);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyTyped

    private void jcbRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbRolActionPerformed

        int seleccion = jcbRol.getSelectedIndex();

        switch (seleccion) {
            case 0:
                jLRol.setText("NINGUNO");
                jcbFicha.setEnabled(false);
                break;

            case 1:
                jLRol.setText("INSTRUCTOR");
                jcbFicha.setEnabled(false);
                break;

            case 2:
                jLRol.setText("APRENDIZ");
                jcbFicha.setEnabled(true);
                break;

            case 3:
                jLRol.setText("VISITANTE");
                jcbFicha.setEnabled(false);
                break;

        }
    }//GEN-LAST:event_jcbRolActionPerformed

    private void jcbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEstadoActionPerformed
        int seleccion = jcbEstado.getSelectedIndex();

        switch (seleccion) {
            case 0:
                jLEstado.setText("INACTIVO");
                break;
            case 1:
                jLEstado.setText("ACTIVO");
                break;
        }
    }//GEN-LAST:event_jcbEstadoActionPerformed

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

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed

        if (jcbTipoIden.getSelectedIndex() >= 0 && txtCedula.getText().length() > 0
                && txtNombre.getText().length() > 0 && txtApellidos.getText().length() > 0 && txtDireccion.getText().length() > 0
                && txtTelefono.getText().length() > 0 && txtEmail.getText().length() > 0 && jDNacimiento.getDate() != null
                && jLRol.getText() == "APRENDIZ" && jcbFicha.getSelectedIndex() > 0 && jcNFC.isSelected() == true && jDFechaVencimiento.getDate() != null) {
            GuardarConNFCAprendiz();
        } else {

            if (jcbTipoIden.getSelectedIndex() >= 0 && txtCedula.getText().length() > 0
                    && txtNombre.getText().length() > 0 && txtApellidos.getText().length() > 0 && txtDireccion.getText().length() > 0
                    && txtTelefono.getText().length() > 0 && txtEmail.getText().length() > 0 && jDNacimiento.getDate() != null
                    && jLRol.getText() != "APRENDIZ" && jLRol.getText() != "NINGUNO" && jcNFC.isSelected() == true && jDFechaVencimiento.getDate() != null) {
                GuardarConNFCPersona();

            } else {
                JOptionPane.showMessageDialog(null, "Faltan datos");
            }
        }
    }//GEN-LAST:event_botonGuardarActionPerformed

    private void botonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoActionPerformed
        RESET();        // TODO add your handling code here:
    }//GEN-LAST:event_botonNuevoActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        CerrarSocket();
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Control();
        RESET();
    }//GEN-LAST:event_formInternalFrameOpened

    private void jcbFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbFichaActionPerformed
        try {
            if (jcbFicha.getSelectedIndex() > 0) {
                prog.setNumero_ficha(jcbFicha.getSelectedItem().toString());
                prog.BuscarPrograma(frmSplash.con);
                jPrograma.setText(prog.getNombrePrograma());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al encontrar programa", "MENSAJE DEL SISTEMA", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jcbFichaActionPerformed

    private void jcNFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcNFCActionPerformed
        if (jcNFC.isSelected() == true) {

            int opcion = JOptionPane.showConfirmDialog(null, "Se iniciara el proceso de escaneo de la tarjeta NFC. ¿Desea continuar?", "MENSAJE DEL SISTEMA", JOptionPane.YES_NO_OPTION);

            if (opcion == 0) {
                if (escaner.EscanearNFCLogin() == true) {
                    codigoNFC = escaner.getDato();// TODO add your handling code here:
                    nfc.setCodigo(codigoNFC);

                    try {
                        if (nfc.BuscarNFC(frmSplash.con) == true) {
                            JOptionPane.showMessageDialog(null, "La tarjeta esta asignada a otro usuario. Pruebe con una tarjeta que no haya sido registrada", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
                            jcNFC.setSelected(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "La tarjeta fue leida correctamente", "MENSAJE DEL SISTEMA", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error en la lectura o asignacion del NFC", "MENSAJE DEL SISTEMA - Codigo: " + ex.getErrorCode(), JOptionPane.INFORMATION_MESSAGE);
                    }

                } else {
                    jcNFC.setSelected(false);
                }

            } else {
                jcNFC.setSelected(false);
            }

        }
    }//GEN-LAST:event_jcNFCActionPerformed

    private void txtCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyPressed
        Funciones.PasarTextABotonGuardar(evt, txtCedula, botonGuardar, "Digite Cedula");        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaKeyPressed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        Funciones.PasarTextABotonGuardar(evt, txtNombre, botonGuardar, "Digite nombre");        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtApellidosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyPressed
        Funciones.PasarTextABotonGuardar(evt, txtApellidos, botonGuardar, "Digite apellidos");        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidosKeyPressed

    private void txtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyPressed
        Funciones.PasarTextABotonGuardar(evt, txtDireccion, botonGuardar, "Digite dirección");        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionKeyPressed

    private void txtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyPressed
        Funciones.PasarTextABotonGuardar(evt, txtTelefono, botonGuardar, "Digite teléfono");        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        Funciones.PasarTextABotonGuardar(evt, txtEmail, botonGuardar, "Digite email");        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyPressed

    private void jDNacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDNacimientoKeyPressed
    }//GEN-LAST:event_jDNacimientoKeyPressed

    private void txtCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyReleased
        Funciones.EstadoBotonUltimoGuardar(evt, txtCedula, botonGuardar, Activo);        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaKeyReleased

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        Funciones.EstadoBotonUltimoGuardar(evt, txtNombre, botonGuardar, Activo);        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtApellidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyReleased
        Funciones.EstadoBotonUltimoGuardar(evt, txtApellidos, botonGuardar, Activo);        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidosKeyReleased

    private void txtDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyReleased
        Funciones.EstadoBotonUltimoGuardar(evt, txtDireccion, botonGuardar, Activo);        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionKeyReleased

    private void txtTelefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyReleased
        Funciones.EstadoBotonUltimoGuardar(evt, txtTelefono, botonGuardar, Activo);        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyReleased

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        Funciones.EstadoBotonUltimoGuardar(evt, txtEmail, botonGuardar, Activo); // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JButton botonSalir;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDFechaVencimiento;
    private com.toedter.calendar.JDateChooser jDNacimiento;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jFoto;
    private javax.swing.JLabel jLEstado;
    private javax.swing.JLabel jLRol;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel jPrograma;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox jcNFC;
    private javax.swing.JComboBox<String> jcbEstado;
    private javax.swing.JComboBox<String> jcbFicha;
    private javax.swing.JComboBox<String> jcbRol;
    private javax.swing.JComboBox<String> jcbTipoIden;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFOTO;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

}

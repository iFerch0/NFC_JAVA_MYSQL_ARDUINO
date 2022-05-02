/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Clases.Control_Ingresos;
import Clases.NFC;
import Clases.Personas;
import Clases.Programa;
import Controlador.ConexionNFC;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernando Rhenals
 */
public class frmEntradaSalida extends javax.swing.JInternalFrame {

    String hora, minutos, segundos, ampm, dia, mes, year;
    String DatoObtenido;
    Personas pers = new Personas();
    NFC nfc = new NFC();
    Programa prog = new Programa();
    Control_Ingresos cont = new Control_Ingresos();
    ConexionNFC escaner = new ConexionNFC();
    private static ServerSocket SERVER_SOCKET;

    private final Timer timer = new Timer();
    private final Timer timer2 = new Timer();

    /**
     * Creates new form frmEntradaSalida
     */
    public frmEntradaSalida() {
//              this.timer = new Timer (4000, new ActionListener () 
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                LimpiarCajas();
//                IniciarPrograma();
//            }
//        });

        //timer = new Timer();
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                IniciarPrograma();
            }
        }, 10, 300);

        timer2.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                LimpiarCajas();
            }
        }, 1000, 10000);

//        new java.util.Timer().schedule(new java.util.TimerTask() {
//            @Override
//            public void run() {
//                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                LimpiarCajas();
//                IniciarPrograma();
//            }
//        }, 10, 500);
        initComponents();
        setTitle("CONTROL NFC - AUTOMATICO");

    }

    public void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();
        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        if (ampm.equals("PM")) {
            int h = calendario.get(Calendar.HOUR_OF_DAY) - 12;
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        }
        minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
        dia = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
        mes = String.valueOf(calendario.get(Calendar.MONTH));
        year = String.valueOf(calendario.get(Calendar.YEAR));
    }

    public void Control() {
        try {
            SERVER_SOCKET = new ServerSocket(1005);

        } catch (IOException x) {
            JOptionPane.showMessageDialog(null, "Otra instancia de la aplicación se está ejecutando...", "Error", JOptionPane.WARNING_MESSAGE);
            this.dispose();
        }

    }

    public void Buscar() {
        try {

            if (nfc.BuscarNFC(frmSplash.con) == true) { //Valida Codigo
                pers.setNFC_id(nfc.getIdNFC());
                if (pers.BuscarPersonaNFC(frmSplash.con) == true) { // Verifica Persona
                    jIdPersona.setText(String.valueOf(pers.getIdPersonas()));
                    jApellidos.setText(pers.getApellido());
                    jNombres.setText(pers.getNombre());

                    switch (pers.getRol_idrol()) {
                        case 1:
                            jTipoUsuario.setText("ADMINISTRADOR");
                            break;
                        case 2:
                            jTipoUsuario.setText("VIGILANTE");
                            break;
                        case 3:
                            jTipoUsuario.setText("TUTOR");
                            break;
                        case 4:
                            jTipoUsuario.setText("APRENDIZ");
                            prog.setNumero_ficha(pers.getNumero_ficha());
                            prog.BuscarPrograma(frmSplash.con);
                            jPrograma.setText(prog.getNombrePrograma());

                            break;
                        case 5:
                            jTipoUsuario.setText("VISITANTE");
                            break;
                    }

                    if (nfc.isActivo() == 1) { //Verifica si el NFC esta activo
                        jNFC.setBackground(Color.GREEN);
                        jNFC.setText("ACTIVO");
                    } else {
                        jNFC.setBackground(Color.RED);
                        jNFC.setText("INACTIVO");
                    }

                    try {
                        byte[] bi = pers.getFoto();
                        BufferedImage image = null;
                        InputStream in = new ByteArrayInputStream(bi);
                        image = ImageIO.read(in);
                        ImageIcon imgi = new ImageIcon(image.getScaledInstance(182, 240, 0));
                        jLFoto.setIcon(imgi);
                    } catch (Exception ex) {
                        jLFoto.setText("No imagen");

                    }

                    RegistrarIngreso();

                } else {
                    JOptionPane.showMessageDialog(null, "No hay información");
                    LimpiarCajas();
                }
            } else {
                LimpiarCajas();
                jObservaciones.setText("No existe información");

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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

    public void LimpiarCajas() {
        jIdPersona.setText("");
        jApellidos.setText("");
        jNombres.setText("");
        jTipoUsuario.setText("");
        jNFC.setText("");
        jObservaciones.setText("");
        jEstado.setText("");
        jLFoto.setIcon(null);
        jNFC.setBackground(jLabel1.getBackground());
        jPrograma.setText("");

    }

    public void IniciarPrograma() {
        try {
            if (escaner.EscanearNFC() == true) {
                nfc.setCodigo(escaner.getDato());
                Buscar();
            }
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }

    }

    public void RegistrarIngreso() {

        cont.setIdPersonas(jIdPersona.getText());
        try {
            cont.ValidarEntrada_Salida(frmSplash.con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            if (cont.ValidarEntrada_Salida(frmSplash.con) == true) {
                cont.setEstado(1);
                cont.setHora_ingreso(hourFormat.format(date));
                cont.setFecha_ingreso(dateFormat.format(date));
                jEstado.setText("ENTRADA");

            } else {
                cont.setEstado(0);
                jEstado.setText("SALIDA");
                cont.setHora_salida(hourFormat.format(date));
                cont.setFecha_ingreso(dateFormat.format(date));
            }

            cont.setIdPersonas(jIdPersona.getText());
            cont.GuardarIngreso(frmSplash.con);
            jObservaciones.setText("OK");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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

        jLabel7 = new javax.swing.JLabel();
        jEstado = new javax.swing.JLabel();
        jIdPersona = new javax.swing.JLabel();
        jPrograma = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jNombres = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jApellidos = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTipoUsuario = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jObservaciones = new javax.swing.JLabel();
        jNFC = new javax.swing.JLabel();
        jLFoto = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 49, 50));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Identificación:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 112, -1, -1));

        jEstado.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jEstado.setForeground(new java.awt.Color(255, 255, 255));
        jEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jEstado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 287, 193, 24));

        jIdPersona.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jIdPersona.setForeground(new java.awt.Color(255, 255, 255));
        jIdPersona.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jIdPersona.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jIdPersona.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jIdPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 112, 190, 24));

        jPrograma.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPrograma.setForeground(new java.awt.Color(255, 255, 255));
        jPrograma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPrograma.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Programa", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPrograma.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jPrograma, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 356, 764, 80));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombres:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 147, 108, -1));

        jNombres.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jNombres.setForeground(new java.awt.Color(255, 255, 255));
        jNombres.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jNombres.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jNombres.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 147, 193, 24));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Apellidos:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 182, 108, -1));

        jApellidos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jApellidos.setForeground(new java.awt.Color(255, 255, 255));
        jApellidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jApellidos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jApellidos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 182, 193, 24));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tipo Usuario:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 217, -1, -1));

        jTipoUsuario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jTipoUsuario.setForeground(new java.awt.Color(255, 255, 255));
        jTipoUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTipoUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTipoUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jTipoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 217, 194, 24));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("NFC");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 252, 108, -1));

        jObservaciones.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jObservaciones.setForeground(new java.awt.Color(255, 255, 255));
        jObservaciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observaciones", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jObservaciones.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 447, 764, 80));

        jNFC.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jNFC.setForeground(new java.awt.Color(255, 255, 255));
        jNFC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jNFC.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jNFC.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jNFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 252, 170, 24));

        jLFoto.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLFoto.setForeground(new java.awt.Color(255, 255, 255));
        jLFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLFoto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLFoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 182, 240));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Estado");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 291, 108, -1));

        jLabel10.setBackground(new java.awt.Color(51, 49, 50));
        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("SERVICIO NACIONAL DE APRENDIZAJE");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 764, 88));

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setText("CERRAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 270, -1, 46));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Control();        // TODO add your handling code here:
//timer.start();
    }//GEN-LAST:event_formInternalFrameOpened

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        timer.cancel();
        timer.purge();
        escaner = null;
        System.gc();
        CerrarSocket();
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jApellidos;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jEstado;
    private javax.swing.JLabel jIdPersona;
    private javax.swing.JLabel jLFoto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jNFC;
    private javax.swing.JLabel jNombres;
    private javax.swing.JLabel jObservaciones;
    private javax.swing.JLabel jPrograma;
    private javax.swing.JLabel jTipoUsuario;
    // End of variables declaration//GEN-END:variables
}

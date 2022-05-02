package Interfaz;

import Clases.Login;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

public class frmMenuAdmin extends javax.swing.JFrame implements Runnable {

    String hora, dia, mes, year;
    Calendar calendario;
    Thread h1;
    frmRegistro reg = new frmRegistro(this, true);
    Login log = new Login();
    private static ServerSocket SERVER_SOCKET;
    int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

    public frmMenuAdmin() {
        initComponents();
        this.setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 500, 500);
        h1 = new Thread(this);
        h1.start();
        jMenuBar2.setLayout(new FlowLayout());
        setTitle("Menu Administrador");
        setExtendedState(MAXIMIZED_BOTH);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jFecha = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jReloj = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuSalir = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MENU ADMIN");
        setBackground(new java.awt.Color(153, 153, 255));
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1100, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 768));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 951, Short.MAX_VALUE)
        );

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setRollover(true);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setText("Administrador");
        jToolBar1.add(jLabel2);
        jToolBar1.add(jSeparator1);

        jFecha.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jToolBar1.add(jFecha);
        jToolBar1.add(jSeparator5);

        jReloj.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jReloj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jToolBar1.add(jReloj);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jDesktopPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenuBar2.setBackground(new java.awt.Color(204, 204, 204));
        jMenuBar2.setToolTipText("");
        jMenuBar2.setPreferredSize(new java.awt.Dimension(295, 55));

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/48x48/iconfinder_website_-_folder_3440836 (1).png"))); // NOI18N
        jMenu3.setText("Archivo");
        jMenu3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_logout_54231.png"))); // NOI18N
        jMenuItem4.setText("Cerrar Sesión");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);
        jMenu3.add(jSeparator4);

        jMenuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuSalir.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_xfce-system-exit_23651.png"))); // NOI18N
        jMenuSalir.setText("Salir");
        jMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSalirActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuSalir);

        jMenuBar2.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/48x48/iconfinder_032_95930.png"))); // NOI18N
        jMenu4.setText("Funciones");
        jMenu4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/iconfinder_file_add_48761.png"))); // NOI18N
        jMenu1.setText("Agregar");
        jMenu1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jMenuItem5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/iconfinder_3_avatar_2754579.png"))); // NOI18N
        jMenuItem5.setText("Agregar Persona");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/iconfinder_bookshelf_1055107.png"))); // NOI18N
        jMenuItem9.setText("Agregar Programa");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenu4.add(jMenu1);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_employee_user_43195.png"))); // NOI18N
        jMenu5.setText("Gestion de Usuarios");
        jMenu5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jMenuItem7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/iconfinder_user-group_285648.png"))); // NOI18N
        jMenuItem7.setText("Acceso al Sistema");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenu4.add(jMenu5);
        jMenu4.add(jSeparator2);

        jMenuItem6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/nfc.png"))); // NOI18N
        jMenuItem6.setText("Iniciar Control");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuBar2.add(jMenu4);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/48x48/iconfinder_custom-reports_63120.png"))); // NOI18N
        jMenu6.setText("Registros");
        jMenu6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenu6.add(jSeparator3);

        jMenuItem12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_list_101833.png"))); // NOI18N
        jMenuItem12.setText("Control de Ingresos");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem12);

        jMenuItem13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/if_user_285655.png"))); // NOI18N
        jMenuItem13.setText("Registros Personas");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem13);

        jMenuBar2.add(jMenu6);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/48x48/iconfinder_help-browser_118806.png"))); // NOI18N
        jMenu2.setText("Ayuda");
        jMenu2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32x32/iconfinder_Information_27854.png"))); // NOI18N
        jMenuItem1.setText("Acerca de");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar2.add(jMenu2);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        frmPersonas personas = new frmPersonas();
        jDesktopPane1.add(personas);
        personas.setLocation(jDesktopPane1.getWidth() / 2 - personas.getWidth() / 2, jDesktopPane1.getHeight() / 2 - personas.getHeight() / 2);
        personas.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
        CerrarSocket();
        System.exit(0);
    }//GEN-LAST:event_jMenuSalirActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        frmLogin log = new frmLogin();
        log.setVisible(true);
        CerrarSocket();
        System.gc();
        this.dispose();

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        frmControlAcceso acceso = new frmControlAcceso();
        jDesktopPane1.add(acceso);
        acceso.setLocation(jDesktopPane1.getWidth() / 2 - acceso.getWidth() / 2, acceso.getHeight() / 2 - acceso.getHeight() / 2);
        acceso.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Control();
        try {
            if (log.VerificarUsuarios(frmSplash.con) == false) {
                JOptionPane.showMessageDialog(null, "Ingrese datos personales para el usuario Administrador", "MENSAJE DEL SISTEMA", JOptionPane.INFORMATION_MESSAGE);
                frmPersonas pers = new frmPersonas();
                jDesktopPane1.add(pers);
                pers.setLocation(jDesktopPane1.getWidth() / 2 - pers.getWidth() / 2, pers.getHeight() / 2 - pers.getHeight() / 2);
                pers.setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed

        frmCarreras carreras = new frmCarreras();
        jDesktopPane1.add(carreras);
        carreras.setLocation(jDesktopPane1.getWidth() / 2 - carreras.getWidth() / 2, carreras.getHeight() / 2 - carreras.getHeight() / 2);
        carreras.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        if (reg != null) {//si existe una venta, la cierra.
            reg.dispose();
        }

        reg = new frmRegistro(this, true); //crea la ventana y la muestra     
        reg.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        AboutIs acerca = new AboutIs();
        jDesktopPane1.add(acerca);
        acerca.setLocation(jDesktopPane1.getWidth() / 2 - acerca.getWidth() / 2, acerca.getHeight() / 2 - acerca.getHeight() / 2);
        acerca.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        RegistroPersonas regPers = new RegistroPersonas();
        jDesktopPane1.add(regPers);
        regPers.setLocation(jDesktopPane1.getWidth() / 2 - regPers.getWidth() / 2, regPers.getHeight() / 2 - regPers.getHeight() / 2);
        regPers.setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        frmEntradaSalida e_s = new frmEntradaSalida();
        jDesktopPane1.add(e_s);
        e_s.setLocation(jDesktopPane1.getWidth() / 2 - e_s.getWidth() / 2, e_s.getHeight() / 2 - e_s.getHeight() / 2);
        e_s.setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

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
            java.util.logging.Logger.getLogger(frmMenuAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMenuAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMenuAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMenuAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMenuAdmin().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jFecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuSalir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jReloj;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == h1) {
            calcula();
            jReloj.setText(hora);
            jFecha.setText(dia + "/" + mes + "/" + year);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();
        calendario.setTime(fechaHoraActual);
        hora = String.valueOf(calendario.get(Calendar.HOUR) + ":" + calendario.get(Calendar.MINUTE) + ":" + calendario.get(Calendar.SECOND));
        dia = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
        mes = String.valueOf(calendario.get(Calendar.MONTH) + 1);
        year = String.valueOf(calendario.get(Calendar.YEAR));
    }

    public void Control() {
        try {
            SERVER_SOCKET = new ServerSocket(1334);

        } catch (IOException x) {
            JOptionPane.showMessageDialog(null, "Otra instancia de la aplicación se está ejecutando...", "Error", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

    }

    public void CerrarSocket() {
        try {
            SERVER_SOCKET.close();
        } catch (IOException x) {
            JOptionPane.showMessageDialog(null, "Error al cerrar. Consulte administrador", "Error", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

    }

}

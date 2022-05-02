package Interfaz;

import Clases.COMPROBAR_CONEXION;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class frmSplash extends javax.swing.JFrame {

    public static Connection con = null;
    COMPROBAR_CONEXION cargando = new COMPROBAR_CONEXION();

    public frmSplash() {
        initComponents();
        conexion();
        this.getContentPane().setBackground(Color.BLACK);
        this.setLocationRelativeTo(null);
        Cargando Cargando = new Cargando();
        Cargando.start();
        Cargando = null;
        CargandoDatos CargandoDatos = new CargandoDatos();
        CargandoDatos.start();
        CargandoDatos = null;
    }

    public Connection conexion() {
        try {
            if (con == null) {
                con = new Controlador.Conexion().con();
            }
        } catch (Exception e) {
        }
        return con;
    }

    class Cargando extends Thread {

        public Cargando() {
            super();
        }

        @Override
        public void run() {
            setProgresoMax(130); //120
            velocidadDeCarga();
        }
    }

    class CargandoDatos extends Thread {

        public CargandoDatos() {
            super();
        }

        @Override
        public void run() {
            velocidadDeCargaDatos();
        }
    }

    public void setProgresoMax(int maxProgress) {
        progressBar.setMaximum(maxProgress);
    }

    public void setProgreso(int progress) {
        final int progreso = progress;
        progressBar.setValue(progreso);
    }

    public void velocidadDeCarga() {
        for (int i = 0; i <= 200; i++) {
            for (long j = 0; j < 400000; ++j) { //90000
                String poop = " " + (j + i);
            }
            setProgreso(i);  // si quieres q muestre los numeros y un mensaje
        }
    }

    @SuppressWarnings("static-access")
    public void velocidadDeCargaDatos() {
        try {
            LinkedList list = cargando.listar(con);
            Iterator It_List = list.iterator();
            Thread.sleep(100); //100
            while (It_List.hasNext()) {
                COMPROBAR_CONEXION artx = (COMPROBAR_CONEXION) It_List.next();
                Thread.sleep(130); //130
                carga.setText(artx.getCm_nombre());
                Thread.sleep(200); //200
            }
            Thread.sleep(200); //200

        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage(), "No se puede iniciar programa", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR" + ex.getMessage(), "No se puede iniciar programa", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        new frmLogin().setVisible(true);
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        carga = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });
        jPanel1.setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background/100px-Sena_Colombia_logo.svg.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 250, 120);

        jLabel6.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Todos los derechos reservados");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 200, 220, 12);

        jLabel3.setFont(new java.awt.Font("Lucida Console", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Control de Ingreso");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 130, 250, 17);

        carga.setFont(new java.awt.Font("Lucida Console", 3, 12)); // NOI18N
        carga.setForeground(new java.awt.Color(255, 255, 255));
        carga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        carga.setText("Conectando...");
        carga.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(carga);
        carga.setBounds(10, 250, 224, 13);

        jLabel5.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SERVICIO NACIONAL DE APRENDIZAJE");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 180, 230, 12);

        jLabel4.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("2019");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 160, 28, 12);

        jLabel10.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("de la Universidad de Cordoba");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(10, 380, 230, 12);

        jLabel11.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Aprendices del SENA");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(10, 340, 210, 12);

        jLabel9.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Software realizado por:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(10, 320, 190, 12);

        progressBar.setBackground(new java.awt.Color(102, 255, 255));
        progressBar.setForeground(new java.awt.Color(102, 102, 102));
        progressBar.setAlignmentX(0.3F);
        progressBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        progressBar.setPreferredSize(new java.awt.Dimension(146, 9));
        jPanel1.add(progressBar);
        progressBar.setBounds(10, 270, 230, 20);

        jLabel14.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Con colaboracion con estudiantes");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(10, 360, 230, 12);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background/imagen.png"))); // NOI18N
        jLabel12.setText("jLabel12");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(0, 0, 250, 430);

        jPanel3.setLayout(null);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background/service-3274892_1920.jpg"))); // NOI18N
        jPanel3.add(jLabel13);
        jLabel13.setBounds(0, 0, 560, 430);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }               // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1KeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new frmSplash().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel carga;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}

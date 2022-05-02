package Controlador;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.TextListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Funciones {

    public static SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

    public void EstadoBoton(java.awt.event.KeyEvent evt, JTextField Actual, JButton Guardar, JButton Modificar, int Activo) {
        if (!Actual.getText().equals("")) {
            //if (Activo==0){ Guardar.setEnabled(true);  Modificar.setEnabled(false);}
            if (Activo == 1) {
                Guardar.setEnabled(false);
                Modificar.setEnabled(true);
            }
        } else {
            Modificar.setEnabled(false);
        }
    }

    public static void EstadoBotonUltimo(java.awt.event.KeyEvent evt, JTextField Actual, JButton Guardar, JButton Modificar, int Activo) {
        if (!Actual.getText().equals("")) {
            if (Activo == 0) {
                Guardar.setEnabled(true);
                Modificar.setEnabled(false);
            }
            if (Activo == 1) {
                Guardar.setEnabled(false);
                Modificar.setEnabled(true);
            }
        } else {
            Guardar.setEnabled(false);
            Modificar.setEnabled(false);
        }
    }

    public static void EstadoBotonUltimoGuardar(java.awt.event.KeyEvent evt, JTextField Actual, JButton Guardar, int Activo) {
        if (!Actual.getText().equals("")) {
            if (Activo == 0) {
                Guardar.setEnabled(true);
            }
            if (Activo == 1) {
                Guardar.setEnabled(false);
            }
        } else {
            Guardar.setEnabled(false);
        }
    }

    public static void EstadoBotonUltimo(java.awt.event.KeyEvent evt, JTextArea Actual, JButton Guardar, JButton Modificar, int Activo) {
        if (!Actual.getText().equals("")) {
            if (Activo == 0) {
                Guardar.setEnabled(true);
                Modificar.setEnabled(false);
            }
            if (Activo == 1) {
                Guardar.setEnabled(false);
                Modificar.setEnabled(true);
            }
        } else {
            Guardar.setEnabled(false);
            Modificar.setEnabled(false);
        }
    }

    public void EstadoBotonModificar(java.awt.event.KeyEvent evt, JTextField Actual, JButton Modificar, int Activo) {
        if (!Actual.getText().equals("")) {
            if (Activo == 0) {
                Modificar.setEnabled(false);
            }
            if (Activo == 1) {
                Modificar.setEnabled(true);
            }
        } else {
            Modificar.setEnabled(false);
        }
    }

    public void EstadoBotonDelNoRequerido(java.awt.event.KeyEvent evt, JTextField Actual, JButton Guardar, JButton Modificar, int Activo) {
        if (Actual.getText().equals("")) {
            if (Activo == 0) {
                Guardar.setEnabled(true);
                Modificar.setEnabled(false);
            }
            if (Activo == 1) {
                Guardar.setEnabled(false);
                Modificar.setEnabled(true);
            }
        }
    }

    public void EstadoBotonModificarDelNoRequerido(java.awt.event.KeyEvent evt, JButton Modificar, int Activo) {
        if (Activo == 0) {
            Modificar.setEnabled(false);
        }
        if (Activo == 1) {
            Modificar.setEnabled(true);
        }
    }

    public void EstadoBotonPrimariaEnter(java.awt.event.KeyEvent evt, JTextField Primaria, JButton Guardar, JButton Modificar) {
        if (Primaria.isEnabled()) {
            Guardar.setEnabled(true);
            Modificar.setEnabled(false);
        } else {
            Guardar.setEnabled(false);
            Modificar.setEnabled(true);
        }
    }

    public void EstadoBotonPrimariaClick(java.awt.event.MouseEvent evt, JTextField Primaria, JButton Guardar, JButton Modificar) {
        if (Primaria.isEnabled() == true) {
            Guardar.setEnabled(true);
            Modificar.setEnabled(false);
        } else {
            Guardar.setEnabled(false);
            Modificar.setEnabled(true);
        }
    }

    /*Esta funcion permite movernos entre las cajas de texto con la tecla enter*/
    public void PasarTextAText(java.awt.event.KeyEvent evt, JTextField Actual, JTextField Siguiente, String Mensaje) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                if (Actual.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, Mensaje);
                    Actual.grabFocus();
                } else {
                    Siguiente.setEnabled(true);
                    Siguiente.grabFocus();
                }
            }
        }
    }

    public static void TextListener(TextListener obj, JTextField Texto) {

    }

    public static void PasarTextAPrimeraMayuscula(java.awt.event.FocusEvent evt, JTextField Texto) {

        String cadena = Texto.getText();
        char[] caracteres = cadena.toCharArray();
        caracteres[0] = Character.toUpperCase(caracteres[0]);

        for (int i = 0; i < cadena.length() - 2; i++) {
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
            }
        }
        Texto.setText(new String(caracteres));

    }
    
    public static void PasarTextAPrimeraMayuscula(java.awt.event.KeyEvent evt, JTextField Texto) {

        String cadena = Texto.getText();
        char[] caracteres = cadena.toCharArray();
        caracteres[0] = Character.toUpperCase(caracteres[0]);

        for (int i = 0; i < cadena.length() - 2; i++) {
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
            }
        }
        Texto.setText(new String(caracteres));

    }

    /*Esta funcion permite movernos entre las cajas de texto con la tecla enter*/
    public void PasarFormatoTextAFormatoText(java.awt.event.KeyEvent evt, JFormattedTextField Actual, JFormattedTextField Siguiente, String Mensaje) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                if (Actual.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, Mensaje);
                    Actual.grabFocus();
                } else {
                    Siguiente.setEnabled(true);
                    Siguiente.grabFocus();
                }
            }
        }
    }

    public void PasarTextATextNoRequerido(java.awt.event.KeyEvent evt, JTextField Actual, JComponent Siguiente) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                Siguiente.setEnabled(true);
                Siguiente.grabFocus();
            }
        }
    }

    public static void PasarTextABoton(java.awt.event.KeyEvent evt, JTextField Actual, JButton Guardar, JButton Modificar, JButton Eliminar, String Mensaje) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                if (Actual.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, Mensaje);
                    Actual.grabFocus();
                } else {
                    if (Guardar.isEnabled()) {
                        Guardar.grabFocus();
                    } else if (Modificar.isEnabled()) {
                        Modificar.grabFocus();
                    } else {
                        Eliminar.grabFocus();
                    }
                }
            }
        }
    }

    public static void PasarTextABotonGuardar(java.awt.event.KeyEvent evt, JTextField Actual, JButton Guardar, String Mensaje) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                if (Actual.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, Mensaje);
                    Actual.grabFocus();
                } else {
                    if (Guardar.isEnabled()) {
                        Guardar.grabFocus();

                    }
                }
            }
        }
    }
    
    

    public void PasarTextABoton(java.awt.event.KeyEvent evt, JTextArea Actual, JButton Guardar, JButton Modificar, JButton Eliminar, String Mensaje) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                if (Actual.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, Mensaje);
                    Actual.grabFocus();
                } else {
                    if (Guardar.isEnabled()) {
                        Guardar.grabFocus();
                    } else if (Modificar.isEnabled()) {
                        Modificar.grabFocus();
                    } else {
                        Eliminar.grabFocus();
                    }
                }
            }
        }
    }

    public void PasarCheckABoton(java.awt.event.ActionEvent evt, JCheckBox Actual, JButton Guardar, JButton Modificar, int Activo) {
        if (Actual.isSelected()) {
            if (Activo == 0) {
                Guardar.setEnabled(true);
                Modificar.setEnabled(false);
            }
            if (Activo == 1) {
                Guardar.setEnabled(false);
                Modificar.setEnabled(true);
            }
        } else {
            Guardar.setEnabled(false);
            Modificar.setEnabled(false);
        }
    }

    public void PasarCheck(java.awt.event.ActionEvent evt, JTextField Actual, JButton Guardar, JButton Modificar, int Activo) {
        if (!Actual.getText().equals("")) {
            if (Activo == 0) {
                Guardar.setEnabled(true);
                Modificar.setEnabled(false);
            }
            if (Activo == 1) {
                Guardar.setEnabled(false);
                Modificar.setEnabled(true);
            }
        } else {
            Guardar.setEnabled(false);
            Modificar.setEnabled(false);
        }
    }

    public void PasarTextACombo(java.awt.event.KeyEvent evt, JTextField Actual, JComboBox Siguiente, String Mensaje) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                if (Actual.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, Mensaje);
                    Actual.grabFocus();
                } else {
                    Siguiente.setEnabled(true);
                    Siguiente.grabFocus();
                }
            }
        }
    }

    public void PasarFormatoTextACombo(java.awt.event.KeyEvent evt, JFormattedTextField Actual, JComboBox Siguiente, String Mensaje) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                if (Actual.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, Mensaje);
                    Actual.grabFocus();
                } else {
                    Siguiente.setEnabled(true);
                    Siguiente.grabFocus();
                }
            }
        }
    }

    public void PasarTextAComboNoRequerido(java.awt.event.KeyEvent evt, JTextField Actual, JComboBox Siguiente) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                Siguiente.setEnabled(true);
                Siguiente.grabFocus();
            }
        }
    }

    public void ComboAnteriorVacio(java.awt.event.FocusEvent evt, JComboBox Actual) {
        if (Actual.getSelectedItem().equals("")) {
            Actual.grabFocus();
        }
    }

    public void TextAnteriorVacio(java.awt.event.FocusEvent evt, JTextField Actual) {
        if (Actual.getText().equals("")) {
            Actual.grabFocus();
        }
    }

    public void PasarComboAText(java.awt.event.KeyEvent evt, JComboBox Actual, JTextField Siguiente, JButton Guardar, JButton Modificar, String Mensaje, int Activo) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                if (Actual.getSelectedItem().equals(" ")) {
                    JOptionPane.showMessageDialog(null, Mensaje);
                    Actual.grabFocus();
                } else {
                    Siguiente.setEnabled(true);
                    Siguiente.grabFocus();
                }
            }
        }
        if (!Actual.getSelectedItem().equals(" ")) {
            if (Activo == 0) {
                Guardar.setEnabled(true);
                Modificar.setEnabled(false);
            }
            if (Activo == 1) {
                Guardar.setEnabled(false);
                Modificar.setEnabled(true);
            }
        } else {
            Guardar.setEnabled(false);
            Modificar.setEnabled(false);
        }
    }

    public void PasarComboACombo(java.awt.event.KeyEvent evt, JComboBox Actual, JComboBox Siguiente, JButton Guardar, JButton Modificar, String Mensaje, int Activo) {
        if (evt.getSource().equals(Actual)) {
            if (evt.getKeyCode() == 10) {
                if (Actual.getSelectedItem().equals(" ")) {
                    JOptionPane.showMessageDialog(null, Mensaje);
                    Actual.grabFocus();
                } else {
                    Siguiente.setEnabled(true);
                    Siguiente.grabFocus();
                }
            }
        }
        if (!Actual.getSelectedItem().equals(" ")) {
            if (Activo == 0) {
                Guardar.setEnabled(true);
                Modificar.setEnabled(false);
            }
            if (Activo == 1) {
                Guardar.setEnabled(false);
                Modificar.setEnabled(true);
            }
        } else {
            Guardar.setEnabled(false);
            Modificar.setEnabled(false);
        }
    }

    public void IconoBoton(JButton boton, String Ruta) {
        boton.setIcon(new ImageIcon(this.getClass().getResource(Ruta)));
        boton.repaint();
    }

    public static boolean Botones(JPanel jPanel, boolean Estado) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JButton botones;
        boolean r = false;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JButton")) {
                botones = (javax.swing.JButton) componentes[i];
                botones.setEnabled(Estado);
                r = true;
            }
        }
        return r;
    }

    public static void LimpiarText(JPanel jPanel) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JTextField caja;
        for (Component componente : componentes) {
            if (componente.getClass().getName().equals("javax.swing.JTextField")) {
                caja = (javax.swing.JTextField) componente;
                caja.setText("");
            }
        }
    }

    public static void LimpiarTextLabel(JPanel jPanel) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JLabel caja;
        for (Component componente : componentes) {
            if (componente.getClass().getName().equals("javax.swing.JTextField")) {
                caja = (javax.swing.JLabel) componente;
                caja.setText("");
            }
        }
    }

    public static void LimpiarTextFrame(JFrame jFrame) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jFrame.getComponentCount()];
        componentes = jFrame.getComponents();
        javax.swing.JTextField caja;
        for (Component componente : componentes) {
            if (componente.getClass().getName().equals("javax.swing.JTextField")) {
                caja = (javax.swing.JTextField) componente;
                caja.setText("");
            }
        }
    }

    public static boolean LimpiarFormatoText(JPanel jPanel) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JFormattedTextField caja;
        boolean r = false;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JFormattedTextField")) {
                caja = (javax.swing.JFormattedTextField) componentes[i];
                caja.setText("");
                r = true;
            }
        }
        return r;
    }

    public boolean LimpiarArea(JPanel jPanel) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JTextArea caja;
        boolean r = false;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JTextArea")) {
                caja = (javax.swing.JTextArea) componentes[i];
                caja.setText("");
                r = true;
            }
        }
        return r;
    }

    public boolean LimpiarCheck(JPanel jPanel) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JCheckBox caja;
        boolean r = false;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JCheckBox")) {
                caja = (javax.swing.JCheckBox) componentes[i];
                caja.setSelected(false);
                r = true;
            }
        }

        return r;
    }

    public void EstadoCombo(JPanel jPanel, boolean Estado) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JComboBox caja;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JComboBox")) {
                caja = (javax.swing.JComboBox) componentes[i];
                caja.setEnabled(Estado);
            }
        }
    }

    public boolean EstadoCheck(JPanel jPanel, boolean Estado) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JCheckBox caja;
        boolean r = false;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JCheckBox")) {
                caja = (javax.swing.JCheckBox) componentes[i];
                caja.setEnabled(Estado);
                r = true;
            }
        }

        return r;
    }

    public boolean EstadoRadio(JPanel jPanel, boolean Estado) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JRadioButton caja;
        boolean r = false;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JRadioButton")) {
                caja = (javax.swing.JRadioButton) componentes[i];
                caja.setEnabled(Estado);
                r = true;
            }
        }

        return r;
    }

    public boolean LimpiarCombos(JPanel jPanel) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JComboBox caja;
        boolean r = false;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JComboBox")) {
                caja = (javax.swing.JComboBox) componentes[i];
                caja.setSelectedItem("");
                r = true;
            }
        }
        return r;
    }

    public static void Numerico(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        // Verificar si la tecla pulsada no es un digito
        if (!(Character.isDigit(caracter)) && (caracter != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    //CLASE
    public static void PrimeraMayusculaCadaPalabra(java.awt.event.KeyEvent evt, String cadena) {
        char caracter = evt.getKeyChar();
        if (caracter == KeyEvent.VK_SPACE) {
            cadena += String.valueOf(Character.toUpperCase(caracter));

        }

    }

    public static void CantidadDigitosFormatText(java.awt.event.KeyEvent evt, JFormattedTextField Actual, int Cantidad) {
        char caracter = evt.getKeyChar();
        if (Actual.getText().length() >= Cantidad && (caracter != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public static void CantidadDigitos(java.awt.event.KeyEvent evt, JTextField Actual, int Cantidad) {
        char caracter = evt.getKeyChar();
        if (Actual.getText().length() >= Cantidad && (caracter != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public void CantidadDigitos(java.awt.event.KeyEvent evt, JTextArea Actual, int Cantidad) {
        char caracter = evt.getKeyChar();
        if (Actual.getText().length() >= Cantidad && (caracter != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public void CantidadDigitos(java.awt.event.KeyEvent evt, JFormattedTextField Actual, int Cantidad) {
        char caracter = evt.getKeyChar();
        if (Actual.getText().length() >= Cantidad && (caracter != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public void CuentaBancaria(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        // Verificar si la tecla pulsada no es un digito
        if (!(Character.isDigit(caracter)) && (caracter != '-') && (caracter != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public void Decimales(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        // Verificar si la tecla pulsada no es un digito
        if (!(Character.isDigit(caracter)) && (caracter != '.') && (caracter != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public void Nit(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        // Verificar si la tecla pulsada no es un digito
        if (!(Character.isDigit(caracter)) && (caracter != '.') && (caracter != '-') && (caracter != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public void Email(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        // Verificar si la tecla pulsada no es un digito
        if (!(Character.isDigit(caracter)) && (caracter != '_') && (caracter != '@') && (caracter != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public void Telefonos(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        // Verificar si la tecla pulsada no es un digito
        if (!(Character.isDigit(caracter)) && (caracter != KeyEvent.VK_BACK_SPACE) && (caracter != '-')) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public static void Letras(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        if (!(Character.isLetter(caracter)) && (caracter != KeyEvent.VK_BACK_SPACE) && (caracter != KeyEvent.VK_DELETE)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }

    public void Digitos(int numeros, String Concatenar, JTextField recibe) {
        int numero = numeros;
        String num;
        if (numero < 10) {
            num = ("0" + numero);
        } else {
            num = ("" + numero);
        }

        recibe.setText(Concatenar + num);
    }

    public void DigitosGrupo(int numeros, String Concatenar, JTextField recibe) {
        int numero = numeros;
        String num;
        if (numero < 10) {
            num = ("0" + numero);
        } else {
            num = ("" + numero);
        }

        if (Concatenar.length() < 2) {
            Concatenar = "0" + Concatenar;
        }

        recibe.setText(Concatenar + num);
    }

    public void DigitosCompetencias(int numeros, String Concatenar, JTextField recibe) {
        int numero = numeros;
        String num;
        if (numero > 99) {
            num = ("" + numero);
        } else if (numero > 9) {
            num = ("0" + numero);
        } else {
            num = ("00" + numero);
        }
        recibe.setText(Concatenar + num);
    }

    /*public void pausa(int mlSeg){
     try{
     Thread.sleep(mlSeg);
     }catch(Exception e){}
     }*/
    //Calcular edad
    public int Edad(Date fechaInicial) {
        //Sacar fecha actual
        Calendar calendarioAhora = Calendar.getInstance();
        calendarioAhora.add(Calendar.HOUR, -5);

        //Convertir de String a Date
        Date fechaActual = calendarioAhora.getTime();

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaActual.getTime();

        long diferencia = fechaFinalMs - fechaInicialMs;
        double años = Math.floor((diferencia / (1000 * 60 * 60 * 24)) / 365);
        return ((int) años);
    }

    public boolean TextVacios(JPanel jPanel) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JTextField caja;
        boolean r = false;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JTextField")) {
                caja = (javax.swing.JTextField) componentes[i];
                if (caja.getText().equals("")) { // && (caja.getBorder().toString().length()<38)
                    caja.requestFocus();
                    r = true;
                    JOptionPane.showMessageDialog(null, "Existen Campos Vacios");
                    break;
                }
            }
        }
        return r;
    }

    public boolean ComboVacios(JPanel jPanel) {
        @SuppressWarnings("UnusedAssignment")
        Component[] componentes = new Component[jPanel.getComponentCount()];
        componentes = jPanel.getComponents();
        javax.swing.JComboBox caja;
        boolean r = false;
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (componentes[i].getClass().getName().equals("javax.swing.JComboBox")) {
                caja = (javax.swing.JComboBox) componentes[i];
                if (caja.getSelectedItem().equals("")) { // && (caja.getBorder().toString().length()<38)
                    caja.requestFocus();
                    r = true;
                    JOptionPane.showMessageDialog(null, "Existen Campos Vacios");
                    break;
                }
            }
        }
        return r;
    }

    public static void PasarGrilla(java.awt.event.MouseEvent evt, JTable Actual, JButton Guardar, JButton Modificar, JButton Eliminar, int Activo) {
        if (Actual.getSelectedColumn() != -1) {
            if (Activo == 1) {
                Eliminar.setEnabled(true);
                Guardar.setEnabled(false);
                Modificar.setEnabled(false);
            }
        }
    }

    public static void PasarGrillaEliminar(java.awt.event.MouseEvent evt, JTable Actual, JButton Eliminar, int Activo) {
        if (Actual.getSelectedColumn() != -1) {
            if (Activo == 1) {
                Eliminar.setEnabled(true);
            }
        }
    }

}

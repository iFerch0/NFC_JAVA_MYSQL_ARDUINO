/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import javax.swing.JOptionPane;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

/**
 *
 * @author Fernando Rhenals
 */
public final class ConexionNFC {

    private String dato;

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public boolean EscanearNFCLogin() {
        boolean estado = true;
        SerialPort serialPort = new SerialPort(ObtenerPuerto());
        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
            setDato(serialPort.readString(8, 5000));
            serialPort.closePort();
        } catch (SerialPortException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + e.getMessage(), "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            estado = false;
        } catch (SerialPortTimeoutException ex) {
            JOptionPane.showMessageDialog(null, "TIEMPO DE ESPERA SUPERADO. INTENTELO DE NUEVO", "MENSAJE DEL SISTEMA", JOptionPane.WARNING_MESSAGE);

            estado = false;

            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                System.out.println(e);
            }
        }
        return estado;

    }

    public boolean EscanearNFC() {

        boolean estado = true;

        SerialPort serialPort = new SerialPort(ObtenerPuerto());
        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
            setDato(serialPort.readString(8));
            serialPort.closePort();
        } catch (SerialPortException e) {
            estado = false;
        }
        return estado;

    }

    public String ObtenerPuerto() {

        String defport = "";
        String[] ports = SerialPortList.getPortNames();
        for (String name : ports) {
            for (int i = 0; i < ports.length; i++) {
                defport = ports[i];
            }
        }
        return defport;
    }

    public static void main(String[] args) throws Exception {
        ConexionNFC main = new ConexionNFC();

        main.EscanearNFC();
        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException ie) {
                }
            }
        };
        t.start();

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

}

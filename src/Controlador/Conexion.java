package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    String driver;
    String url;
    String user;
    String pass;
    //conexion......
    Connection con = null;

    public Conexion() {
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/nfc_proyecto";
        user = "root";
        pass = "admin";
    }
    //constructor

    public Connection con() {
        try {
            if (con == null) {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
            }
        } catch (SQLException E) {
            switch (E.getErrorCode()) {
                case 1049:
                    JOptionPane.showMessageDialog(null, "Se ha producido un error al conectar a la base de datos", "Codigo de error: " + E.getErrorCode(), JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
            }
            JOptionPane.showMessageDialog(null, "Se ha producido un error", "Codigo de error: " + E.getErrorCode(), JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } catch (ClassNotFoundException E) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        return con;
    }

    public Connection getConnection() {
        return this.con;
    }
    
    
}

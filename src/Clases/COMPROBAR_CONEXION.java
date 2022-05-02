package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class COMPROBAR_CONEXION {

    private int cm_codigo;
    private String cm_nombre;

    public int getCm_codigo() {
        return cm_codigo;
    }

    public void setCm_codigo(int cm_codigo) {
        this.cm_codigo = cm_codigo;
    }

    public String getCm_nombre() {
        return cm_nombre;
    }

    public void setCm_nombre(String cm_nombre) {
        this.cm_nombre = cm_nombre;
    }

    public LinkedList listar(Connection conn) throws SQLException {
        LinkedList searchResults = new LinkedList();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM test ORDER BY id ASC ");
        ResultSet result = stmt.executeQuery();

        while (result.next()) {
            COMPROBAR_CONEXION temp = new COMPROBAR_CONEXION();
            temp.setCm_codigo(result.getInt(1));
            temp.setCm_nombre(result.getString(2));
            searchResults.add(temp);
        }

        return (LinkedList) searchResults;
    }

}

package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Fernando Rhenals
 */
public class Programa {

    private int idPrograma;
    private String numero_ficha;
    private String nombrePrograma;

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNumero_ficha() {
        return numero_ficha;
    }

    public void setNumero_ficha(String numero_ficha) {
        this.numero_ficha = numero_ficha;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public void GuardarPrograma(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("INSERT INTO programa (idPrograma, numero_ficha, nombrePrograma)"
                + " VALUES (NULL,?,?)");
        stmt.setString(1, getNumero_ficha());
        stmt.setString(2, getNombrePrograma());
        stmt.executeUpdate();
    }

     public void EliminarPrograma(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("delete from programa where numero_ficha = ?");
        stmt.setString(1, getNumero_ficha());
        stmt.executeUpdate();
    }
    
    
    public LinkedList ListarProgramas(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM programa");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Programa prog = new Programa();
            prog.setIdPrograma(result.getInt("idPrograma"));
            prog.setNumero_ficha(result.getString("numero_ficha"));
            prog.setNombrePrograma(result.getString("nombrePrograma"));
            list.add(prog);
        }
        return list;
    }

    public void ModificarPrograma(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE programa SET numero_ficha= ?, nombrePrograma= ? WHERE idPrograma = ?");
        stmt.setInt(3, getIdPrograma());
        stmt.setString(1, getNumero_ficha());
        stmt.setString(2, getNombrePrograma());
        stmt.executeUpdate();
    }

    public boolean BuscarPrograma(Connection con) throws SQLException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM programa WHERE numero_ficha = ?");
        stmt.setString(1, getNumero_ficha());
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setIdPrograma(r.getInt("idPrograma"));
            setNombrePrograma(r.getString("nombrePrograma"));
            setNumero_ficha(r.getString("numero_ficha"));
            b = true;
        }
        return b;
    }
}

package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Login {

    private String user;
    private String pass;
    private String idPersonas;
    private int idLogin;
    private int idRol;
    private String nombre;
    private String apellido;
    private int nfc;
    private String codigo;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public boolean Buscar(Connection con) throws SQLException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM login a\n"
                + "INNER JOIN personas b\n"
                + "ON a.idPersonas=b.idPersonas\n"
                + "WHERE usuario=? AND contraseña=?");
        stmt.setString(1, getUser());
        stmt.setString(2, getPass());
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setIdPersonas(r.getString("idPersonas"));
            b = true;
        }
        return b;
    }

    public boolean BuscarParaLogin(Connection con) throws SQLException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM personas WHERE rol_id < 3 AND idPersonas = ?");
        stmt.setString(1, getIdPersonas());
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setNombre(r.getString("nombre"));
            setApellido(r.getString("apellido"));
            b = true;
        }
        return b;
    }

    public LinkedList Listar(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM login a INNER JOIN personas b on a.idPersonas = b.idPersonas");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Login log = new Login();
            log.setIdLogin(result.getInt("idLogin"));
            log.setUser(result.getString("usuario"));
            log.setPass(result.getString("contraseña"));
            log.setIdPersonas(result.getString("idPersonas"));
            log.setNombre(result.getString("nombre"));
            log.setApellido(result.getString("apellido"));
            log.setIdRol(result.getInt("Rol_id"));
            list.add(log);
        }
        return list;
    }

    public boolean VerificarUsuarios(Connection con) throws SQLException, NullPointerException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM login a INNER JOIN personas b ON a.idPersonas=b.idPersonas WHERE b.rol_id = 1");
        ResultSet r = stmt.executeQuery();

        if (r.next()) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    public boolean VerificarUsuariosConNFC(Connection con) throws SQLException, NullPointerException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT nfc_id FROM login a INNER JOIN personas b WHERE a.idPersonas = b.idPersonas AND a.idPersonas = ?");
        stmt.setString(1, getIdPersonas());
        ResultSet r = stmt.executeQuery();

        if (r.next()) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    public boolean VerificarUsuariosEstadoNFC(Connection con) throws SQLException, NullPointerException {
        boolean b = false;
        int estadoNFC = 0;
        PreparedStatement stmt = con.prepareStatement("SELECT estado FROM login a INNER JOIN personas b INNER JOIN NFC c WHERE a.idPersonas = b.idPersonas AND a.idPersonas = ?");
        stmt.setString(1, getIdPersonas());
        ResultSet r = stmt.executeQuery();

        if (r.next()) {
            estadoNFC = r.getInt("estado");
        }

        if (estadoNFC == 1) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    public boolean GenerarUltimoID(Connection con) throws SQLException, NullPointerException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM login");
        ResultSet r = stmt.executeQuery();
        setIdLogin(r.getInt("idLogin"));

        if (r.next()) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    public void GuardarUsuario(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("INSERT INTO login (usuario, contraseña, idPersonas)"
                + " VALUES (?,?,?)");
        stmt.setString(1, getUser());
        stmt.setString(2, getPass());
        stmt.setString(3, getIdPersonas());
        stmt.executeUpdate();
    }

    public void eliminar(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM login WHERE idLogin = ?");
        stmt.setInt(1, getIdLogin());
        stmt.executeUpdate();
    }

    public void eliminarUltimoRegistro(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM login WHERE idLogin = ?");
        stmt.setInt(1, getIdLogin());
        stmt.executeUpdate();
    }

    public void modificar(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE login SET usuario= ?, contraseña= ?, idPersonas= ? WHERE idLogin = ?");
        stmt.setInt(4, getIdLogin());
        stmt.setString(1, getUser());
        stmt.setString(2, getPass());
        stmt.setString(3, getIdPersonas());
        stmt.executeUpdate();
    }

    public void modificarRolUsuario(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE personas SET rol_id = ? WHERE idPersonas = ?");
        stmt.setString(2, getIdPersonas());
        stmt.setInt(1, getIdRol());
        stmt.executeUpdate();
    }

    public void ActivarNFCUsuario(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE nfc SET estado = 1 WHERE  idNFC = ? ");
        stmt.setInt(1, getNfc());
        stmt.executeUpdate();
    }

    public void DesactivarNFCUsuario(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE nfc SET estado = 0 WHERE  idNFC = ? ");
        stmt.setInt(1, getNfc());
        stmt.executeUpdate();
    }

    public String getIdPersonas() {
        return idPersonas;
    }

    public void setIdPersonas(String idPersonas) {
        this.idPersonas = idPersonas;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the idRol
     */
    public int getIdRol() {
        return idRol;
    }

    /**
     * @param idRol the idRol to set
     */
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    /**
     * @return the nfc
     */
    public int getNfc() {
        return nfc;
    }

    /**
     * @param nfc the nfc to set
     */
    public void setNfc(int nfc) {
        this.nfc = nfc;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}

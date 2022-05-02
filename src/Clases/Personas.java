/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author FERNANDO
 */
public class Personas {

    private String idPersonas;
    private String tipo_identificacion;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String email;
    private String fecha_nacimiento;
    private byte[] foto;
    private int Rol_idrol;
    private String tipRol;
    private int NFC_id;
    private String numero_ficha;
    
        public void BuscarPersonaPorFoto(Connection con) throws SQLException {

        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT foto FROM personas a INNER JOIN ingreso b on a.idPersonas = b.idPersonas WHERE b.idIngreso = ?");
        stmt.setString(1, getIdPersonas());    
        
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
//            setIdPersonas(r.getString("idPersonas"));
//            setTipo_identificacion(r.getString("tipo_identificacion"));
//            setNombre(r.getString("nombre"));
//            setApellido(r.getString("apellido"));
//            setDireccion(r.getString("direccion"));
//            setTelefono(r.getString("telefono"));
//            setEmail(r.getString("email"));
//            setRol_idrol(r.getInt("rol_id"));
//            setNFC_id(r.getInt("nfc_id"));
            setFoto(r.getBytes("foto"));
//            setFecha_nacimiento(r.getString("fecha_nacimiento"));
//            setNumero_ficha(r.getString("ficha_programa"));

        }

    }
        
       
    
    

    public void GuardarPersona(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("INSERT INTO personas (idPersonas, tipo_identificacion, nombre, apellido, direccion, telefono, email, fecha_nacimiento, foto, rol_id, nfc_id, ficha_programa)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,NULL)");
        stmt.setString(1, getIdPersonas());
        stmt.setString(2, getTipo_identificacion());
        stmt.setString(3, getNombre());
        stmt.setString(4, getApellido());
        stmt.setString(5, getDireccion());
        stmt.setString(6, getTelefono());
        stmt.setString(7, getEmail());
        stmt.setString(8, getFecha_nacimiento());
        stmt.setBytes(9, getFoto());
        stmt.setInt(10, getRol_idrol());
        stmt.setInt(11, getNFC_id());
        stmt.executeUpdate();
    }
    
    
     public void GuardarPersonaTemporal(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("INSERT INTO temp (idPersonas, tipo_identificacion, nombre, apellido, direccion, telefono, email, rol_id, fecha_nacimiento, nfc_id, ficha_programa, foto)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, getIdPersonas());
        stmt.setString(2, getTipo_identificacion());
        stmt.setString(3, getNombre());
        stmt.setString(4, getApellido());
        stmt.setString(5, getDireccion());
        stmt.setString(6, getTelefono());
        stmt.setString(7, getEmail());
        stmt.setInt(8, getRol_idrol());
        stmt.setString(9, getFecha_nacimiento());
        stmt.setInt(10, getNFC_id());
        stmt.setString(11, getNumero_ficha());
        stmt.setBytes(12, getFoto());
        stmt.executeUpdate();
    }

    public void GuardarPersonaSinNFC(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("INSERT INTO personas (idPersonas, tipo_identificacion, nombre, apellido, direccion, telefono, email, fecha_nacimiento, foto, rol_id, ficha_programa)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,NULL)");
        stmt.setString(1, getIdPersonas());
        stmt.setString(2, getTipo_identificacion());
        stmt.setString(3, getNombre());
        stmt.setString(4, getApellido());
        stmt.setString(5, getDireccion());
        stmt.setString(6, getTelefono());
        stmt.setString(7, getEmail());
        stmt.setString(8, getFecha_nacimiento());
        stmt.setBytes(9, getFoto());
        stmt.setInt(10, getRol_idrol());
        stmt.executeUpdate();
    }

    public void GuardarAprendiz(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("INSERT INTO personas (idPersonas, tipo_identificacion, nombre, apellido, direccion, telefono, email, fecha_nacimiento, foto, rol_id, nfc_id, ficha_programa)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, getIdPersonas());
        stmt.setString(2, getTipo_identificacion());
        stmt.setString(3, getNombre());
        stmt.setString(4, getApellido());
        stmt.setString(5, getDireccion());
        stmt.setString(6, getTelefono());
        stmt.setString(7, getEmail());
        stmt.setString(8, getFecha_nacimiento());
        stmt.setBytes(9, getFoto());
        stmt.setInt(10, getRol_idrol());
        stmt.setInt(11, getNFC_id());
        stmt.setString(12, getNumero_ficha());
        stmt.executeUpdate();
    }

//    public void GuardarAprendizSinNFC(Connection con) throws SQLException {
//        PreparedStatement stmt = con.prepareCall("INSERT INTO personas (idPersonas, tipo_identificacion, nombre, apellido, direccion, telefono, email, fecha_nacimiento, foto, rol_id, ficha_programa)"
//                + " VALUES (?,?,?,?,?,?,?,?,?,?,?)");
//        stmt.setString(1, getIdPersonas());
//        stmt.setString(2, getTipo_identificacion());
//        stmt.setString(3, getNombre());
//        stmt.setString(4, getApellido());
//        stmt.setString(5, getDireccion());
//        stmt.setString(6, getTelefono());
//        stmt.setString(7, getEmail());
//        stmt.setString(8, getFecha_nacimiento());
//        stmt.setBytes(9, getFoto());
//        stmt.setInt(10, getRol_idrol());
//        stmt.setString(11, getNumero_ficha());
//        stmt.executeUpdate();
//    }

    public LinkedList ListarPersonas(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM personas");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Personas pers = new Personas();
            pers.setIdPersonas(result.getString("idPersonas"));
            pers.setTipo_identificacion(result.getString("tipo_identificacion"));
            pers.setNombre(result.getString("nombre"));
            pers.setApellido(result.getString("apellido"));
            pers.setDireccion(result.getString("direccion"));
            pers.setTelefono(result.getString("telefono"));
            pers.setEmail(result.getString("email"));
            pers.setFecha_nacimiento(result.getString("fecha_nacimiento"));
            pers.setFoto(result.getBytes("foto"));
            pers.setRol_idrol(result.getInt("rol_id"));
            pers.setNFC_id(result.getInt("nfc_id"));
            pers.setNumero_ficha(result.getString("ficha_programa"));
            list.add(pers);
        }
        return list;
    }
    
    

    public LinkedList CargarRoles(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM rol where idRol > 2");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Personas pers = new Personas();
            pers.setRol_idrol(result.getInt("idRol"));
            pers.setTipRol(result.getString("tipo"));
            list.add(pers);
        }
        return list;
    }

    public LinkedList CargarNFCid(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT idNFC FROM nfc");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Personas pers = new Personas();
            pers.setNFC_id(result.getInt("idNFC"));
            list.add(pers);
        }
        return list;
    }

    public boolean BuscarPersonaNFC(Connection con) throws SQLException {

        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM personas a INNER JOIN NFC b ON a.nfc_id=b.idNFC WHERE idNFC = ?");
        stmt.setInt(1, getNFC_id());
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setIdPersonas(r.getString("idPersonas"));
            setTipo_identificacion(r.getString("tipo_identificacion"));
            setNombre(r.getString("nombre"));
            setApellido(r.getString("apellido"));
            setDireccion(r.getString("direccion"));
            setTelefono(r.getString("telefono"));
            setEmail(r.getString("email"));
            setRol_idrol(r.getInt("rol_id"));
            setNFC_id(r.getInt("nfc_id"));
            setFoto(r.getBytes("foto"));
            setFecha_nacimiento(r.getString("fecha_nacimiento"));
            setNumero_ficha(r.getString("ficha_programa"));

            b = true;
        }

        return b;
    }
    
     public void ModificarPersonas(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE personas SET nombre= ?, apellido = ?, direccion = ?, telefono = ?, email = ?, fecha_nacimiento = ? WHERE idPersonas = ?");
        stmt.setString(7, getIdPersonas());
        stmt.setString(1, getNombre());
        stmt.setString(2, getApellido());
        stmt.setString(3, getDireccion());
        stmt.setString(4, getTelefono());
        stmt.setString(5, getEmail());
        stmt.setString(6, getFecha_nacimiento());
        stmt.executeUpdate();
    }

    public void LlenarTemporal(Connection con) throws SQLException {

        PreparedStatement stmt = con.prepareStatement("SELECT * FROM temp");
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setIdPersonas(r.getString("idPersonas"));
            setTipo_identificacion(r.getString("tipo_identificacion"));
            setNombre(r.getString("nombre"));
            setApellido(r.getString("apellido"));
            setDireccion(r.getString("direccion"));
            setTelefono(r.getString("telefono"));
            setEmail(r.getString("email"));
            setRol_idrol(r.getInt("rol_id"));
            setNFC_id(r.getInt("nfc_id"));
            setFoto(r.getBytes("foto"));
            setFecha_nacimiento(r.getString("fecha_nacimiento"));
            setNumero_ficha(r.getString("ficha_programa"));

        }

    }
    
    public boolean BuscarPersona(Connection con) throws SQLException {

        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM personas WHERE idPersonas = ?");
        stmt.setString(1, getIdPersonas());    
        

        
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
                    setIdPersonas(r.getString("idPersonas"));
            setTipo_identificacion(r.getString("tipo_identificacion"));
            setNombre(r.getString("nombre"));
            setApellido(r.getString("apellido"));
            setDireccion(r.getString("direccion"));
            setTelefono(r.getString("telefono"));
            setEmail(r.getString("email"));
            setRol_idrol(r.getInt("rol_id"));
            setNFC_id(r.getInt("nfc_id"));
            setFoto(r.getBytes("foto"));
            setFecha_nacimiento(r.getString("fecha_nacimiento"));
            setNumero_ficha(r.getString("ficha_programa"));

            b = true;
        }

        return b;
    }

    
    public boolean BuscarRolPersona(Connection con) throws SQLException {

        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM personas a INNER JOIN rol b ON a.rol_id=b.idRol WHERE idPersonas= ?");
        stmt.setString(1, getIdPersonas());
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setRol_idrol(r.getInt("rol_id"));
            setTipRol(r.getString("tipo"));
            b = true;
        }

        return b;
    }

    public boolean BuscaridNFCPersona(Connection con) throws SQLException {

        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT nfc_id FROM personas a INNER JOIN nfc b ON a.nfc_id=b.idNFC WHERE idPersonas= ?");
        stmt.setString(1, getIdPersonas());
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setNFC_id(r.getInt("nfc_id"));
            b = true;
        }
        return b;
    }

    public boolean BuscarPorIDultimoRegistro(Connection con) throws SQLException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM personas ORDER BY id DESC LIMIT 1");
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setIdPersonas(r.getString("id"));
            b = true;
        }
        return b;
    }

    public void EliminarUltimoRegistro(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("delete from personas order by personas.idPersonas desc limit 1");
        stmt.executeUpdate();
    }
    
    public void EliminarPersonas(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("delete from personas where idPersonas = ?");
        stmt.setString(1, getIdPersonas());
        stmt.executeUpdate();
    }
    
    public void Limpiar(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("delete from temp");
        stmt.executeUpdate();
    }
    

    public String getTipo_identificacion() {
        return tipo_identificacion;
    }

    public void setTipo_identificacion(String tipo_identificacion) {
        this.tipo_identificacion = tipo_identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRol_idrol() {
        return Rol_idrol;
    }

    public void setRol_idrol(int Rol_idrol) {
        this.Rol_idrol = Rol_idrol;
    }

    public String getTipRol() {
        return tipRol;
    }

    public void setTipRol(String tipRol) {
        this.tipRol = tipRol;
    }

    public int getNFC_id() {
        return NFC_id;
    }

    public void setNFC_id(int NFC_id) {
        this.NFC_id = NFC_id;
    }

    public String getNumero_ficha() {
        return numero_ficha;
    }

    public void setNumero_ficha(String numero_ficha) {
        this.numero_ficha = numero_ficha;
    }

    public String getIdPersonas() {
        return idPersonas;
    }

    public void setIdPersonas(String idPersonas) {
        this.idPersonas = idPersonas;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

}

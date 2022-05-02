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
 * @author Fernando Rhenals
 */
public class Control_Ingresos {

    private int idIngreso;
    private String idPersonas;
    private String fecha_ingreso;
    private String hora_ingreso;
    private String hora_salida;
    private int estado;
    private String nombre;
    private String apellido;
    private byte[] foto;
    private int NFC_id;
String fechaM;






    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public String getIdPersonas() {
        return idPersonas;
    }

    public void setIdPersonas(String idPersonas) {
        this.idPersonas = idPersonas;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getHora_ingreso() {
        return hora_ingreso;
    }

    public void setHora_ingreso(String hora_ingreso) {
        this.hora_ingreso = hora_ingreso;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void ObtenerIdPersona(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT a.idPersonas FROM ingreso a INNER JOIN personas b ON a.idPersonas=b.idPersonas WHERE a.idIngreso = ? ");
        stmt.setInt(1, getIdIngreso());
        ResultSet r = stmt.executeQuery();
        if(r.next()){
        setIdPersonas(r.getString("idPersonas"));
        }
    }
    
     public int ContadorDeAsistencias(Connection con) throws SQLException {
        int cantidad = 0;
        PreparedStatement stmt = con.prepareStatement("SELECT COUNT(estado) FROM ingreso a INNER JOIN personas b ON a.idPersonas=b.idPersonas where estado = 1 AND a.idPersonas = ?");
        stmt.setString(1, getIdPersonas());
        ResultSet r = stmt.executeQuery();
        if(r.next()){
        cantidad = r.getInt(1);
        }
        return cantidad;
    }
    
    
    public void GuardarIngreso(Connection con) throws SQLException {
        
        PreparedStatement stmt = con.prepareCall("INSERT INTO ingreso (idPersonas, fecha_ingreso, hora_ingreso, hora_salida, estado) VALUES (?,?,?,?,?)");
        stmt.setString(1, getIdPersonas());
        stmt.setString(2, getFecha_ingreso());
        stmt.setString(3, getHora_ingreso());
        stmt.setString(4, getHora_salida());
        stmt.setInt(5, getEstado());
        
        stmt.executeUpdate();
    }

    public LinkedList ListarIngreso(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT a.*,nombre, apellido, nfc_id  FROM ingreso a INNER JOIN personas b ON a.idPersonas=b.idPersonas");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Control_Ingresos ing = new Control_Ingresos();
            ing.setIdIngreso(result.getInt("idIngreso"));
            ing.setIdPersonas(result.getString("idPersonas"));
            ing.setNombre(result.getString("nombre"));
            ing.setApellido(result.getString("apellido"));
           // ing.setFoto(result.getBytes("foto"));
            ing.setFecha_ingreso(result.getString("fecha_ingreso"));
            ing.setHora_ingreso(result.getString("hora_ingreso"));
            ing.setHora_salida(result.getString("hora_salida"));
            ing.setEstado(result.getInt("estado"));
            ing.setNFC_id(result.getInt("nfc_id"));
            list.add(ing);
        }
        return list;
    }
    
     public LinkedList ListarIngresoPorFecha(Connection con) throws SQLException {
       
        PreparedStatement stmt = con.prepareStatement(
                "SELECT a.*,nombre, apellido, nfc_id  FROM ingreso a INNER JOIN personas b ON a.idPersonas=b.idPersonas WHERE a.fecha_ingreso = ?");
        stmt.setString(1, getFecha_ingreso());
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Control_Ingresos ing = new Control_Ingresos();
            ing.setIdIngreso(result.getInt("idIngreso"));
            ing.setIdPersonas(result.getString("idPersonas"));
            ing.setNombre(result.getString("nombre"));
            ing.setApellido(result.getString("apellido"));
            ing.setFecha_ingreso(result.getString("fecha_ingreso"));
            ing.setHora_ingreso(result.getString("hora_ingreso"));
            ing.setHora_salida(result.getString("hora_salida"));
            ing.setEstado(result.getInt("estado"));
            ing.setNFC_id(result.getInt("nfc_id"));
            list.add(ing);
        }
        return list;
    }
    

    public LinkedList CargarRoles(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT idRol FROM rol");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Personas ing = new Personas();
            ing.setRol_idrol(result.getInt("idRol"));
            list.add(ing);
        }
        return list;
    }

    public LinkedList CargarNFCid(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT idNFC FROM nfc");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Personas ing = new Personas();
            ing.setNFC_id(result.getInt("idNFC"));
            list.add(ing);
        }
        return list;
    }

    /**
     * @return the NFC_id
     */
    /*public boolean BuscarIngresosPorFechas(Connection con) throws SQLException {

     boolean b = false;
     PreparedStatement stmt = con.prepareStatement("SELECT * FROM ingonas a INNER JOIN NFC b ON a.nfc_id=b.idNFC WHERE idNFC = ?");
     stmt.setString(1, getNFC_id());
     ResultSet r = stmt.executeQuery();
     if (r.next()) {
     setIdPersonas(r.getInt("idPersonas"));
     setTipo_identificacion(r.getString("tipo_identificacion"));
     setNombre(r.getString("nombre"));
     setApellido(r.getString("apellido"));
     setDireccion(r.getString("direccion"));
     setTelefono(r.getInt("telefono"));
     setEmail(r.getString("email"));
     setRol_idrol(r.getInt("rol_id"));
     setNFC_id(r.getString("nfc_id"));
     setFoto(r.getBytes("foto"));
     setFecha_nacimiento(r.getString("fecha_nacimiento"));
            
     b = true;
     }

     return b;
     }*/
    public boolean BuscarRolPersona(Connection con) throws SQLException {
        Personas ing = new Personas();
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM ingonas a INNER JOIN rol b ON a.rol_id=b.idRol WHERE idPersonas= ?");
        stmt.setString(1, getIdPersonas());
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            ing.setRol_idrol(r.getInt("rol_id"));
            b = true;
        }

        return b;
    }

    public boolean ValidarEntrada_Salida(Connection con) throws SQLException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM ingreso WHERE idPersonas = ? ORDER BY idIngreso DESC LIMIT 1");
        stmt.setString(1, getIdPersonas());
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setEstado(r.getInt("estado"));
        }
        if (getEstado() == 0) {
            b = true;
        } else {
            b = false;
        }

        return b;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getNFC_id() {
        return NFC_id;
    }

    public void setNFC_id(int NFC_id) {
        this.NFC_id = NFC_id;
    }

}

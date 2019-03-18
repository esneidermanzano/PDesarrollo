package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Clases.Sede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoSede {

	FachadaDB fachada;
	
	public DaoSede(){
		Sede sede;
        fachada= new FachadaDB();
    }//
	
	public ArrayList<String> obtenerNombresDeSedes()
	{
		ArrayList<String> nombres = new ArrayList<String>();
		String sql_select = "SELECT DISTINCT nombre FROM sedes";
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){
            	nombres.add(tabla.getString(1));
               // System.out.println(resultado_consulta);
             }
            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return nombres;
	}

	public String getId(String nombre) {
		String id = "";
		String sql_select = "SELECT id FROM sedes WHERE nombre= '" + nombre + "'";
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando sede en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
           
            while(tabla.next()){
            	id = tabla.getString(1);
               // System.out.println(resultado_consulta);
             }            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return id;
	}
	
	public String getNombre(String id) {
		
		String nombre = "";
		String sql_select = "SELECT nombre FROM sedes WHERE id = '" + id + "'";
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando sede en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
           
            while(tabla.next()){
            	nombre = tabla.getString(1);
               // System.out.println(resultado_consulta);
             }            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return nombre;
		
	}
	
	public boolean consultarId(String id) {
		String sql_select = "SELECT id FROM sedes WHERE id = '" + id + "'";
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando sede en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
           
            while(tabla.next()){
            	id = tabla.getString(1);
             }            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		if(id == ""){return false;
        }else { return true;}
	}
	
	public int crearSede(String nombre, String telefono, String direccion, String tamano, String empleados, String fecha) {
    	//0 = Success, 1 = error de usuario, 2 = id ya tomada
		int valido = 0;
		String sql_insert = "INSERT INTO sedes (nombre, telefono, direccion, tamano_sede, numero_empleados, fecha_apertura)";
		
		sql_insert += " VALUES('" + nombre + "', '" + telefono + "', '" + direccion + "', " + tamano 
				+ ", " + empleados + ", '" + fecha + "')";

        try{
            Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();

            int respuesta = sentencia.executeUpdate(sql_insert);
            System.out.println(respuesta);
            
        }
        catch(SQLException e){
            System.out.println(e); 
        	valido = -1;
        	System.out.print("que paso men");
        	return valido;
        	
            }
        catch(Exception e){ 
            System.out.println(e);
        }
		
		return valido;
	}
	
	public boolean consultarNombre(String nomUsuario){
        String sql_select;
        String resultado="";
        
        sql_select="SELECT nombre FROM sedes WHERE nombre=?";
         try{
            Connection conn = fachada.getConnetion();
            System.out.println("consultando en la bd");
            PreparedStatement sentencia = conn.prepareStatement(sql_select);
            sentencia.setString(1,nomUsuario);
            ResultSet tabla = sentencia.executeQuery();
            
            while(tabla.next()){            	
               resultado = tabla.getString(1);
              
            } 
            sentencia.close();
         }
         catch(SQLException e){ System.out.println(e); }
         catch(Exception e){ System.out.println(e); }
         
         if(resultado != ""){
        	 return true;
         } else {
        	 return false;
         }  
	}
	
	//Consulta el nombre de una sede por medio del ID y lo compara con el nombre que ingresen como parametro
		//Esto para efectos de validar el cambio de nombre de sede.
		public boolean consultarNombreID(int id,String nombre){
	        String sql_select;
	        String resultado="";
	        
	        sql_select="SELECT nombre FROM sedes WHERE id=?";
	         try{
	            Connection conn = fachada.getConnetion();
	            System.out.println("consultando en la bd");
	            PreparedStatement sentencia = conn.prepareStatement(sql_select);
	            sentencia.setInt(1,id);
	            ResultSet tabla = sentencia.executeQuery();
	            
	            while(tabla.next()){            	
	               resultado = tabla.getString(1);
	              
	            } 
	            sentencia.close();
	         }
	         catch(SQLException e){ System.out.println(e); }
	         catch(Exception e){ System.out.println(e); }

	         if(resultado.equals(nombre)){
	        	 return true;
	         } else {
	        	 return false;
	         }  
		}
	
	public String[] consultarIdentificador(String nombre, int identificador) {
        String sql_select;                                
        String[ ]   resultado = new  String[7];  
        sql_select="SELECT * FROM sedes WHERE nombre=? AND id=?";
         try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            PreparedStatement sentencia = conn.prepareStatement(sql_select);
            sentencia.setString(1,nombre);
            sentencia.setInt(2, identificador);
            ResultSet tabla = sentencia.executeQuery();
            
            while(tabla.next()){            
               resultado[0] = tabla.getString(1);
               resultado[1] =tabla.getString(2);
               resultado[2] = tabla.getString(3);
               resultado[3] = tabla.getString(4);
               resultado[4] = ""+tabla.getInt(5);
               resultado[5] = ""+tabla.getInt(6);
               resultado[6] = ""+tabla.getDate(7);
            } 
            sentencia.close();
         }
         catch(SQLException e){ System.out.println(e); }
         catch(Exception e){ System.out.println(e); }
         return resultado;
         
	}
	
	public int actualizar(int identificador,String nombre,String telefono,String direccion,int tamano,int nEmpleados){
        String sql_actualizar;
        int n=0;
        sql_actualizar="UPDATE sedes Set nombre=?,telefono=?, direccion=?,tamano_sede=?,numero_empleados=? WHERE id=?";
        
        try{
            Connection conn= fachada.getConnetion();
            PreparedStatement sentencia = conn.prepareStatement(sql_actualizar);
            sentencia.setString(1,nombre);
            sentencia.setString(2,telefono);
            sentencia.setString(3,direccion);
            sentencia.setInt(4, tamano);
            sentencia.setInt(5, nEmpleados);
            sentencia.setInt(6,identificador);

            n = sentencia.executeUpdate();            
                       
            sentencia.close();
        }
        catch(SQLException e){
            System.out.println(e); 
            }
        catch(Exception e){ 
            System.out.println(e);
        }
        return n;
    }

	public ObservableList<Sede> consultarSedes(){
		
		ObservableList<Sede> sedes = FXCollections.observableArrayList();
		
		String sql_select = "SELECT * FROM sedes";
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){
            	
            	String id = tabla.getString(1);
            	String nombre = tabla.getString(2);
            	String telefono = tabla.getString(3);
            	String direccion = tabla.getString(4);
            	String tamano = tabla.getString(5);
            	String numEmpleados = tabla.getString(6);
            	String fechaApertura = tabla.getString(7);
               
               sedes.add(new Sede(id, nombre, telefono, direccion, tamano, numEmpleados, fechaApertura));
            }
           
            
         }
         catch(SQLException e){ System.out.println(e); }
         catch(Exception e){ System.out.println(e); }
		
		return sedes;
	}

	
public void cerrarConexionBD(){
        fachada.closeConection(fachada.getConnetion());
    }	
}
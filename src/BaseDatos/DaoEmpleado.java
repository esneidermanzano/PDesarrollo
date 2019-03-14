package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Clases.Empleado;
import Clases.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoEmpleado {
	FachadaDB fachada;
	
	public DaoEmpleado(){
        fachada= new FachadaDB();
    }

	public int crearEmpleado(String nombre, String id, String password, String telefono, String correo, String cargo, String sede, String estadoCivil, String genero) {
    	//0 = Success, 1 = error de usuario, 2 = id ya tomada
		int valido = 0;
		String sql_insert = "INSERT INTO empleados";
		
		sql_insert += " VALUES('" + id + "', crypt('" + password + "', gen_salt('bf')), '" + nombre
				+ "', '" + telefono + "', B'1', '" + sede + "', '" + correo + "', '" + cargo + "', '" + estadoCivil + "', '" + genero + "')";
        try{
            Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();

            int respuesta = sentencia.executeUpdate(sql_insert);
            System.out.println(respuesta);
            
        }
        catch(SQLException e){
           // System.out.println(e); 
        	valido = -1;
        	return valido;
        	
            }
        catch(Exception e){ 
            System.out.println(e);
        }
		
		return valido;
	}
	
	public boolean existe(String idEntrada){
		
        String sql_select;
        String resultado = "";
        
        sql_select = "SELECT nombre FROM empleados WHERE id = ?";
         try{
        	 
            Connection conn = fachada.getConnetion();
            System.out.println("consultando en la bd");
            PreparedStatement sentencia = conn.prepareStatement(sql_select);
            sentencia.setString(1,idEntrada);
            ResultSet tabla = sentencia.executeQuery();
            
            while(tabla.next()){            	
               resultado = tabla.getString(1);              
            } 
            
            sentencia.close();
            
         }catch(SQLException e){
        	 System.out.println(e); 
         }catch(Exception e){ 
        	 System.out.println(e); 
         }
         
         if(resultado != ""){
        	 return true;
         }else {
        	 return false;
         }          
	}
	
public ObservableList<Empleado> consultarEmpleados(){
		
		ObservableList<Empleado> empleados = FXCollections.observableArrayList();
		
		String sql_select = "SELECT * FROM empleados WHERE perfil='Vendedor' or perfil='Jefe de taller'";
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){
            	
            	String nombre = tabla.getString(3);
            	String id = tabla.getString(1);
            	String telefono = tabla.getString(4);
            	String estado = tabla.getString(5);
            	String sede = tabla.getString(6);
            	String correo = tabla.getString(7);
            	String perfil = tabla.getString(8);
            	String estadoCivil = tabla.getString(9);
            	String genero = tabla.getString(10);
            	
              // System.out.println(resultado_consulta);
               
               empleados.add(new Empleado(nombre, id, telefono, estado, sede, correo, perfil, estadoCivil, genero));
            }
           
            
         }
         catch(SQLException e){ System.out.println(e); }
         catch(Exception e){ System.out.println(e); }
		
		return empleados;
	}
	
	public Usuario consultarUsuario(String idEntrada){
		
        String sql_select;
        String datos[] = new String[9];
        
        sql_select = "SELECT * FROM empleados WHERE id = '" + idEntrada +  "'";
        
         try{
        	 
            Connection conn = fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){            	
            	datos[0] = tabla.getString(1);
            	datos[1] = tabla.getString(3);
            	datos[2] = tabla.getString(4);
            	datos[3] = tabla.getString(5);
            	datos[4] = tabla.getString(6);
            	datos[5] = tabla.getString(7);
            	datos[6] = tabla.getString(8);
            	datos[7] = tabla.getString(9);
            	datos[8] = tabla.getString(10);
            } 
                       
         }catch(SQLException e){
        	 System.out.println(e);
         }catch(Exception e){
        	 System.out.println(e);
         }
          
         Usuario U = new Usuario(datos);
         return U;
         
	}
	
	public int actualizar(Usuario U) {
		
	    String[] datos = U.getUsuario();
	    
        String sql_actualizar = "UPDATE empleados SET ";
        sql_actualizar += "id = '" + datos[0] + "',";
        sql_actualizar += "nombre = '" + datos[1] + "',";
        sql_actualizar += "telefono = '" + datos[2] + "',";
        sql_actualizar += "activo = " + datos[3] + ",";
        sql_actualizar += "id_sede = '" + datos[4] + "',";
        sql_actualizar += "correo = '" + datos[5] + "',";
        sql_actualizar += "perfil = '" + datos[6] + "',";
        sql_actualizar += "estado_civil = '" + datos[7] + "',";
        sql_actualizar += "genero = '" + datos[8] + "' ";
        sql_actualizar += "WHERE id = '" + datos[0] + "'";
      
        int respuesta = -1;
        
        try{
        	
        	Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();
            respuesta = sentencia.executeUpdate(sql_actualizar);
            System.out.println(respuesta);
            
        }catch(SQLException e) {        	
            System.out.println(e); 
        }catch(Exception e){ 
            System.out.println(e);
        }
        
        return respuesta;
      
    }
	
	public void cerrarConexionBD(){
        fachada.closeConection(fachada.getConnetion());
    }	

}

package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Clases.Usuario;

public class DaoEmpleado {
	FachadaDB fachada;
	
	public DaoEmpleado(){
        fachada= new FachadaDB();
    }

	public void vergas() {
		System.out.println("Vergas x2");
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
	
	public Usuario consultarUsuario(String idEntrada){
        String sql_select;
        String id = "", nombre = "", telefono = "", estado = "", sede = "", correo = "", perfil = "", estadoCivil = "", genero = "";
        
        sql_select = "SELECT * FROM empleados WHERE id ='" + idEntrada +  "'";
         try{
            Connection conn = fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){            	
            	id = tabla.getString(1);
            	nombre = tabla.getString(3);
            	telefono = tabla.getString(4);
            	estado = tabla.getString(5);
            	sede = tabla.getString(6);
            	correo = tabla.getString(7);
            	perfil = tabla.getString(8);
            	estadoCivil = tabla.getString(9);
            	genero = tabla.getString(10);
            } 
         }
         catch(SQLException e){ System.out.println(e); }
         catch(Exception e){ System.out.println(e); }
          
         Usuario U = new Usuario(id, nombre, telefono, estado, sede, correo, perfil, estadoCivil, genero);
         return U;
	}
	
	public int actualizar(Usuario U) {
        String sql_actualizar;
        sql_actualizar = "UPDATE empleados SET id = '" + U.getId() + "', nombre = '" + U.getNombre() + "', telefono = '" + U.getTelefono() + "', activo = B'" + U.getEstado() + "',  id_sede = '" + U.getSede() + "', correo = '" + U.getCorreo() + "', perfil = '" + U.getPerfil() + "', estado_civil = '" + U.getEstadoCivil() + "', genero = '" + U.getGenero() + "' WHERE id = '" + U.getId() + "'";
        int respuesta =-1;
        try{
        	Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();
            respuesta = sentencia.executeUpdate(sql_actualizar);
            System.out.println(respuesta);
        }
        catch(SQLException e){
            System.out.println(e); 
            }
        catch(Exception e){ 
            System.out.println(e);
        }
        return respuesta;
      
    }

	
	public void cerrarConexionBD(){
        fachada.closeConection(fachada.getConnetion());
    }	

}

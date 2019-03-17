package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Clases.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoEmpleado {
	
	FachadaDB fachada;
	private String estadoAntes = "", estadoDespues = "";
	
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
	
	public Empleado consultarEmpleado(String idEntrada){
		
        String sql_select;
        String nombre = null, id = null, telefono = null, estado = null;
        String sede = null, correo = null, perfil = null, estadoCivil = null, genero = null;
        
        sql_select = "SELECT * FROM empleados WHERE id = '" + idEntrada +  "'";
        
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
                       
         }catch(SQLException e){
        	 System.out.println(e);
         }catch(Exception e){
        	 System.out.println(e);
         }
         
         if(estado.compareTo("1") == 0) {
        	 estadoAntes = "B'1'";
         }else {
        	 estadoAntes = "B'0'";
         }
                 
         Empleado E = new Empleado(nombre,id,telefono,estado,sede,correo,perfil,estadoCivil,genero);
         return E;
         
	}
	
	//Actualiza la cantidad de empleados de una sede si el estado de un empleado cambia:
	/*public void actualizarSede(String sede) {
		if(estadoAntes.compareTo(estadoDespues) != 0) {
			DaoSede D = new DaoSede();
			if(estadoDespues.compareTo("B'1'") == 0) {				
				D.actualizarCantidadEmpleados(sede, "+ 1");
			}else {
				D.actualizarCantidadEmpleados(sede, "- 1");
			}
		}
	}*/
	
	public int actualizar(Empleado E) {
		
		String nombre = E.getNombre(), id = E.getId(), telefono = E.getTelefono(); 
        String sede = E.getSede(), correo = E.getCorreo(), perfil = E.getPerfil();
        String estadoCivil = E.getEstadoCivil(), genero = E.getGenero(), estado = E.getEstado();
        
        String sql_actualizar = "UPDATE empleados SET ";
        sql_actualizar += "id = '" + id + "',";
        sql_actualizar += "nombre = '" + nombre + "',";
        sql_actualizar += "telefono = '" + telefono + "',";
        sql_actualizar += "activo = " + estado + ",";
        sql_actualizar += "id_sede = '" + sede + "',";
        sql_actualizar += "correo = '" + correo + "',";
        sql_actualizar += "perfil = '" + perfil + "',";
        sql_actualizar += "estado_civil = '" + estadoCivil + "',";
        sql_actualizar += "genero = '" + genero + "' ";
        sql_actualizar += "WHERE id = '" + id + "'";
      
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
        
        estadoDespues = estado;
        //actualizarSede(sede);
                
        return respuesta;
      
    }
	
	public void cerrarConexionBD(){
        fachada.closeConection(fachada.getConnetion());
    }	

}

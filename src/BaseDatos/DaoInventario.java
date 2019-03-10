package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoInventario {
	FachadaDB fachada;
	
	public DaoInventario(){
        fachada= new FachadaDB();
    }

	public String getId(String id) {
		String sql_select = "SELECT id FROM inventario WHERE nombre = '" + id + "'";
		
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
	
	public String[] consultarId(String id) {
		String sql_select = "SELECT nombre, precio_unidad, existencias FROM inventario WHERE id = '" + id + "'";
		String[]   resultado = new  String[3];
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando sede en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
           
            while(tabla.next()){
            	resultado[0] = tabla.getString(1);
            	resultado[1] = tabla.getString(2);
            	resultado[2] = tabla.getString(3);
             }            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return resultado;
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
	
	public String[] consultarIdentificador(String nombre, String identificador) {
        String sql_select;
        String[ ]   resultado = new  String[7];  
        sql_select="SELECT * FROM sedes WHERE nombre=? AND id=?";
         try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            PreparedStatement sentencia = conn.prepareStatement(sql_select);
            sentencia.setString(1,nombre);
            sentencia.setString(2, identificador);
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
	
	public int actualizarItem(String id, String nombre,int precio, int existencias){
        String sql_actualizar;
        int n=0;
        sql_actualizar="UPDATE inventario SET nombre=?,precio_unidad=?, existencias=? WHERE id=?";
        
        try{
            Connection conn= fachada.getConnetion();
            PreparedStatement sentencia = conn.prepareStatement(sql_actualizar);
            sentencia.setString(1,nombre);
            sentencia.setInt(2,precio);
            sentencia.setInt(3,existencias);
            sentencia.setString(4, id);

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

	
	public void cerrarConexionBD(){
        fachada.closeConection(fachada.getConnetion());
    }	
}

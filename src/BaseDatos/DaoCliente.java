package BaseDatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Clases.Cliente;
import Clases.Empleado;

public class DaoCliente {
	
	static FachadaDB fachada;
	
	public DaoCliente(){
		 fachada= new FachadaDB();		
	}
	
	public Cliente buscarCliente(String idEntrada){
		
		String SQL;
        String nombre = null, id = null, telefono = null;
        
        SQL = "SELECT * FROM clientes WHERE id = '" + idEntrada +  "'";
        
         try{
        	 
            Connection conn = fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(SQL);
            
            while(tabla.next()){            	
            	id = tabla.getString(1);
            	nombre = tabla.getString(2);
            	telefono = tabla.getString(3);
            } 
                       
         }catch(SQLException e){
        	 System.out.println(e);
         }catch(Exception e){
        	 System.out.println(e);
         }
            
         Cliente C = new Cliente(nombre,id,telefono);
         return C;
         
	}
	
	public int registrarCliente(Cliente C) {
		
		int salida = 0;
		String SQL;
        String nombre = C.getNombre(), id = C.getId(), telefono = C.getTelefono();
        
        SQL = "INSERT INTO clientes VALUES('" + id + "', '" + nombre + "', '" + telefono + "')";
        
         try{
        	 
            Connection conn = fachada.getConnetion();
            Statement sentencia = conn.createStatement();
            salida = sentencia.executeUpdate(SQL);
                                  
         }catch(SQLException e){
        	 System.out.println(e);
         }catch(Exception e){
        	 System.out.println(e);
         }
            
         return salida;
         
	}
	
}

package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Clases.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoInventario {
	FachadaDB fachada;
	
	public DaoInventario(){
        fachada= new FachadaDB();
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
	
	public void registrarItemEnInventaro(String nombre ) {
	
		String sqlInsert = "INSERT INTO inventario";
		
		sqlInsert += " VALUES(nextval('inventario_sequence'), '" + nombre + "')";
        try{
            Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();

            int respuesta = sentencia.executeUpdate(sqlInsert);
            System.out.println(respuesta);
	}
        catch(SQLException e) {
        	System.out.println(e);
        	}
        }
	
	public void registrarItemEnEjemplares(String  referencia,String color, String valorCompra, String valorVenta, String fechaIngreso, String idSede, String cantidad ) {
		
		String idReferencia =obtenerReferenciaxNombre(referencia);
		
		String sqlInsert = "INSERT INTO ejemplares";
		
		sqlInsert += " VALUES(nextval('ejemplares_sequence'), " + idReferencia + ", '" + color + "', "+ valorCompra + ", "+ valorVenta + ", '"+ fechaIngreso +"', "+ idSede +", "+ cantidad+")";
        
		try{
            Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();

            int respuesta = sentencia.executeUpdate(sqlInsert);
            System.out.println(respuesta);
	}
        catch(SQLException e) {
        	System.out.println(e);
        	}
        }
	
	public ArrayList<String> obtenerReferencias()
	{
		ArrayList<String> referencias = new ArrayList<String>();
		String sqlSelect = "SELECT DISTINCT nombre FROM inventario";
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlSelect);
            
            while(tabla.next()){
            	referencias.add(tabla.getString(1));
             }
            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return referencias;
	}
	
	public String obtenerReferenciaxNombre(String nombre)
	{
		String idxNombre = new String();
		String sqlSelect = "SELECT DISTINCT id FROM inventario"
				+ " where nombre='"+nombre+"'" ;
		
		
		try{
            Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlSelect);
            
            while(tabla.next()){
            	idxNombre = tabla.getString(1);
             }
            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return idxNombre;
	}

	
	public void cerrarConexionBD(){
        fachada.closeConection(fachada.getConnetion());
    }	
	
	public ObservableList<Item> obtenerItems() {
		ObservableList<Item> lista = FXCollections.observableArrayList();
		
		String sql_select = "SELECT numero_de_ejemplar, id_item, inventario.nombre, color, valor_compra, fecha_ingreso,"
				+ "sedes.nombre, cantidad FROM ejemplares, inventario, sedes WHERE ejemplares.id_item = inventario.id"
				+ " and ejemplares.id_sede = sedes.id";
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando todo el inventario");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
           
            while(tabla.next()){
            	lista.add(
            			new Item(Integer.toString(tabla.getInt(1)) +Integer.toString(tabla.getInt(2)), tabla.getString(3), tabla.getString(4), tabla.getInt(5), tabla.getDate(6),  tabla.getString(7),  tabla.getInt(8))
            			);
             }            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return lista;
	}
}

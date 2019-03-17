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

	public ObservableList<String> obtenerNombres() {
		ObservableList<String> lista = FXCollections.observableArrayList();
		String sql_select = "SELECT DISTINCT nombre FROM inventario";		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){
            	lista.add(tabla.getString(1));               
             }
            System.out.println(lista);           
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return lista;
	}

	public ObservableList<String> obtenerColores() {
		ObservableList<String> lista = FXCollections.observableArrayList();
		String sql_select = "SELECT DISTINCT color FROM inventario WHERE";		
		try{
            Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){
            	lista.add(tabla.getString(1));               
             }
            System.out.println(lista);           
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }        
		
		return lista;
	}
	
	public int actualizarItem(int id, int id2, String nombre, String color, int precio, String sede, int existencias){
        String sql_actualizar, sql_consultar;
        int n=0;
        sql_consultar = "SELECT * FROM ejemplar WHERE id_item=?, color=?, valor_compra=?, fecha_ingreso=? id_sede=?"; 
        sql_actualizar="UPDATE inventario SET nombre=?,precio_unidad=?, existencias=? WHERE id=?";
        
        try{
            Connection conn= fachada.getConnetion();
            conn.setAutoCommit(false);
            PreparedStatement sentencia = conn.prepareStatement(sql_actualizar);
            sentencia.setInt(1,id);
            sentencia.setString(2,color);
            sentencia.setInt(3,existencias);
            //sentencia.setString(4, id);

            n = sentencia.executeUpdate(); 
            conn.commit();
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
	
	public ObservableList<Item> obtenerItems() {
		ObservableList<Item> lista = FXCollections.observableArrayList();
		
		String sql_select = "SELECT id_item, numero_de_ejemplar, inventario.nombre, color, valor_venta, fecha_ingreso,"
				+ "sedes.nombre, cantidad FROM ejemplares, inventario, sedes WHERE ejemplares.id_item = inventario.id"
				+ " and ejemplares.id_sede = sedes.id";
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando todo el inventario");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
           
            while(tabla.next()){
            	lista.add(
            			new Item(Integer.toString(tabla.getInt(1)), Integer.toString(tabla.getInt(2)), 
            					tabla.getString(3), tabla.getString(4), tabla.getInt(5), tabla.getDate(6),  
            					tabla.getString(7),  tabla.getInt(8))
            			);
             }            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return lista;
	}
}

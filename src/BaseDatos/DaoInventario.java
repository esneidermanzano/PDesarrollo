package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import Clases.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoInventario {
	FachadaDB fachada;
	
	public DaoInventario(){
        fachada= new FachadaDB();
    }
/*
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
*/
	public ObservableList<String> obtenerColores() {
		ObservableList<String> lista = FXCollections.observableArrayList();
		String sql_select = "SELECT DISTINCT color FROM ejemplares";		
		try{
            Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){
            	lista.add(tabla.getString(1));               
             }
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }        
		
		return lista;
	}
	
	public int actualizarItem(int id, int id2, String color, int precio, Date fecha, int sede, int existencias){
		int valido = 0;
		java.sql.Date fechaSql = new java.sql.Date(fecha.getTime()); 		
        String sql_actualizar, sql_actualizar2, sql_consultar;
        sql_consultar = "SELECT numero_de_ejemplar, id_item FROM ejemplares WHERE id_item=? AND color=? AND valor_venta=? AND fecha_ingreso=? AND id_sede=? AND numero_de_ejemplar <> ? ";         
        int resultado = 0, resultado2 = 0;
        try{
            Connection conn= fachada.getConnetion();
            PreparedStatement sentencia = conn.prepareStatement(sql_consultar);
            sentencia.setInt(1,id);
            sentencia.setString(2,color);
            sentencia.setInt(3,precio);
            sentencia.setDate(4,fechaSql);
            sentencia.setInt(5, sede);
            sentencia.setInt(6, id2);
            //sentencia.setInt(3,precio);
            ResultSet tabla = sentencia.executeQuery(); 
            while(tabla.next()){
            	resultado = tabla.getInt(1);
            	resultado2 = tabla.getInt(2);
             } 
            sentencia.close();
        }
        catch(SQLException e){
        	 System.out.println("Error al verificar registro: " + e);
             return valido = -1;
            }
        catch(Exception e){ 
        	 System.out.println("Error al verificar registro: " + e);
             return valido = -1;
        }
        if(resultado == 0) {
        	sql_actualizar="UPDATE ejemplares SET color=?, valor_venta=?, id_sede=?, cantidad=? WHERE numero_de_ejemplar=? AND id_item=?";
        	try{
	        	Connection conn = fachada.getConnetion();
	            PreparedStatement sentencia = conn.prepareStatement(sql_actualizar);
	            sentencia.setString(1,color);
	            sentencia.setInt(2,precio);
	            sentencia.setInt(3, sede);
	            sentencia.setInt(4, existencias);            
	            sentencia.setInt(5,id2);
	            sentencia.setInt(6,id);	                        
	            valido = sentencia.executeUpdate();
	            sentencia.close();
        	 }
            catch(SQLException e){
                System.out.println("Error al actualizar el producto: " + e);
                return valido = -1;
                }
            catch(Exception e){ 
                System.out.println("Error al actualizar el producto: " + e);
                return valido = -1;
            }        	
        }else {
        	sql_actualizar="UPDATE ejemplares SET cantidad=cantidad+? WHERE numero_de_ejemplar=? AND id_item=?";
        	sql_actualizar2="UPDATE ejemplares SET cantidad=cantidad-? WHERE numero_de_ejemplar=? AND id_item=?";

        	try{
	        	Connection conn = fachada.getConnetion();
	            conn.setAutoCommit(false);
	            PreparedStatement sentencia = conn.prepareStatement(sql_actualizar);
	            sentencia.setInt(1, existencias);            
	            sentencia.setInt(2,resultado);
	            sentencia.setInt(3,resultado2);	                        
	            valido = sentencia.executeUpdate();
	
	            PreparedStatement sentencia2 = conn.prepareStatement(sql_actualizar2);
	            sentencia2.setInt(1, existencias);            
	            sentencia2.setInt(2,id2);
	            sentencia2.setInt(3,id);	                        
	            valido = sentencia2.executeUpdate();
	            conn.commit();
	            sentencia2.close();
        	}
            catch(SQLException e){
                System.out.println("Error al mover la cantidad: " + e.getMessage()); 
                return valido = -1;
                }
            catch(Exception e){ 
                System.out.println("Error al mover la cantidad: " + e.getMessage());
                return valido = -1;
            }
        }
        return valido;
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

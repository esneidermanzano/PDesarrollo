package BaseDatos;

import java.sql.*;


public class DaoLogin {
	
	FachadaDB fachada;
	
	public DaoLogin(){
        fachada= new FachadaDB();
    }//
	
	public boolean consultarUsuario(String nomUsuario){
        String sql_select;
        String resultado="";
        
        sql_select="SELECT nombre FROM gerentes WHERE nombre='" + nomUsuario +  "'";
         try{
            Connection conn = fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){            	
               resultado = tabla.getString(1);
              System.out.println(resultado);
            } 
         }
         catch(SQLException e){ System.out.println(e); }
         catch(Exception e){ System.out.println(e); }
         
         if(resultado != ""){
        	 System.out.println("true");
        	 return true;
         } else {
        	 System.out.println("false");
        	 return false;
         }  
	}
	
	public boolean consultarContrasena(String nomUsuario, String passUsuario) {
        String sql_select;
        String resultado="";
        //String contrasena="";
        
        sql_select="SELECT * FROM gerentes WHERE nombre='" + nomUsuario + "' AND password=CRYPT('" + passUsuario +  "', password)";
         try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){            
               resultado = tabla.getString(1);
            }           
         }
         catch(SQLException e){ System.out.println(e); }
         catch(Exception e){ System.out.println(e); }
         
         if(resultado != ""){
        	 System.out.println("contrasena es correcta");
        	 return true;
         } else {
        	 System.out.println("contrasena no es correcta");
        	 return false;
         }   
	}
	
	/*public int guardarUsuario(Usuario usuario){
        String sql_guardar;
        int numFilas=0;

        sql_guardar="INSERT INTO usuarios VALUES ('" +usuario.getNombre() + "', '" + usuario.getPass()  + "')";
        
        try{
            Connection conn= fachada.getConnetion();
            Statement sentencia = conn.createStatement();

            numFilas = sentencia.executeUpdate(sql_guardar);            
            System.out.println("up " + numFilas);
            return numFilas;
            
        }
        catch(SQLException e){
            System.out.println(e); 
            }
        catch(Exception e){ 
            System.out.println(e);
        }
        return -1;
    }//fin guardar
    */
	
	public void cerrarConexionBD(){
        fachada.closeConection(fachada.getConnetion());
    }
}

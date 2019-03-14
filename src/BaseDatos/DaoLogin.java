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
        
        sql_select="SELECT nombre FROM empleados WHERE nombre='" + nomUsuario +  "'";
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
	
	public String consultarContrasena(String nomUsuario, String passUsuario) {
        String sql_select;
        String resultado="";
        //String contrasena="";
        
        sql_select="SELECT perfil FROM empleados WHERE nombre='" + nomUsuario + "' AND password=CRYPT('" + passUsuario +  "', password)";
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

         return resultado;

	}
	
	public void cerrarConexionBD(){
        fachada.closeConection(fachada.getConnetion());
    }
}

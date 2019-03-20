package BaseDatos;

import java.sql.*;

public class FachadaDB {
        String url, usuario, password;
        Connection conexion = null;
        Statement instruccion;
        ResultSet tabla;
        FachadaDB(){
            url="jdbc:postgresql://localhost:5432/postgres";
            usuario="postgres";
            password="root";
        }

        public Connection conectar(){
            try {
            // Se carga el driver
            Class.forName("org.postgresql.Driver");
            //System.out.println( "Driver Cargado" );
            } catch( Exception e ) {
                System.out.println( "No se pudo cargar el driver." );
            }

            try{
                 //Crear el objeto de conexion a la base de datos
                 conexion = DriverManager.getConnection(url, usuario, password);
                 System.out.println( "Conexion Abierta" );
                 return conexion;
                 //Crear objeto Statement para realizar queries a la base de datos
             } catch( Exception e ) {
                System.out.println( "No se pudo abrir la bd." );
                return null;
             }

        }//end connectar

        public Connection getConnetion(){
            if (conexion == null) {
                return this.conectar();
            }else{
                  return conexion;      
            }  
        }
        
        public void closeConection(Connection c){
            try{
                if (conexion != null){
                    c.close();
                }                
            } catch( Exception e ) {
                System.out.println( "No se pudo cerrar." );
            }
        }
        
}//end class
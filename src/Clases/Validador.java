package Clases;

import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Validador {
	
	//Quita los espacios al principio y final, y reduce a un espacio los que estén en medio:
	public String ajustar(String S) {		
		S = " " + S + " ";
		S = S.replaceAll("\\s+", " ");
		
		if(S.compareTo(" ") != 0) {
			S = S.substring(1, S.length() - 1);
		}		
		
		return S;			
	}
	
	//Muestra un mensaje de alerta en pantalla:	
	public void mostrarMensaje(int tipo, String mensaje, String titulo) {
		
		Principal P = new Principal();
		
		Alert alerta = null;
	    	
	    switch(tipo) {
	    	case 1:
	    		alerta = new Alert(AlertType.INFORMATION);
	    		break;
	    	case 2:
	    			alerta = new Alert(AlertType.WARNING);
	    			break;
	    	case 3:
	    			alerta = new Alert(AlertType.ERROR);
	    			break;
	    }
	    	
	    alerta.setTitle(titulo);
	    alerta.setHeaderText(null);
	    
	    ((Stage)alerta.getDialogPane().getScene().getWindow()).getIcons().add(P.getIcono());
	    
	    alerta.setContentText(mensaje);
	    alerta.showAndWait();
	    	
	}
	   
	//Valida la cantidad de arrobas y su posición en un string:
    public boolean validarCorreo(String prueba, Label error) {
    	
    	boolean salida = true;
    	int arroba = 0;
    	int pos = 0;
    	
    	for(int x=0; x<prueba.length(); x++) {
    		if(prueba.charAt(x) == 64) {
    			arroba++;
    			pos = x;
    		}
    	}
    	
    	if(arroba == 1) {
    		if(pos == 0 || pos == prueba.length()-1) {
    			salida = false;
    		}
    	}else {
    		salida = false;
    	}
    	
    	if(!salida) {
    		error.setText("Ingrese una dirección válida");
    	}
    	    	
    	return salida;
    
    }
    
    //Valida que los nombres sólo contengan letras y tenga mínimo dos palabras:
    public boolean validarNombre(String prueba, Label error) {
    	
    	boolean salida = true;    	
    	String[] palabras = prueba.split(" ");
    	
    	for(int x=0; x<palabras.length; x++) {
    		if(!validarLetras(palabras[x])) {
    			salida = false;
    		}
    	}
    		
    	if(!salida) {
        	error.setText("Ingrese sólo letras en el campo");
        }
    	
    	return salida;
    	
    }
    
    //Valida que un string tenga sólo letras:
    public boolean validarLetras(String prueba) {
    	
    	boolean salida = true;
    	
    	for(int x=0; x<prueba.length(); x++) {
			if(prueba.charAt(x) < 65 || (prueba.charAt(x) > 90 && prueba.charAt(x) < 97) || prueba.charAt(x) > 122) {
            	salida = false;            	  
            }
    	}
    	
    	return salida;
    	
    }
    
    //Valida que un string tenga sólo numeros:
    public boolean validarNumeros(String prueba, Label error) {
    	
    	boolean salida = true;
    
    	for(int x=0; x<prueba.length(); x++) {
    		if(prueba.charAt(x) < 48 || prueba.charAt(x) > 57) {
               	salida = false;
    		}
    	}
    	
    	if(!salida) {
    		error.setText("Ingrese sólo datos numéricos");
    	}
    	
    	return salida;		
    	
    }
        
    //Valida que un campo no esté vacío:
    public boolean validarVacios(String prueba, Label error) {
    	
    	boolean salida = true;
    	
    	if(prueba.compareTo("") == 0 || prueba.compareTo(" ") == 0) {
    		salida = false;
    		error.setText("El campo está vacío");
    	}
    	
    	return salida;
    	
    }
    
    //Valida que un campo no exceda el límite de caracteres:
    public boolean validarLongitud(String prueba, int longitud, Label error) {
    	
    	boolean salida = true;
    	
    	if(prueba.length() > longitud) {
			salida = false;
			error.setText("Ingrese máximo " + longitud + " caracteres");
		}
    	
    	return salida;
    	
    }
    
    //Valida el contenido de un campo para que no tenga caracteres inválidos:
    public boolean validarContenido(String prueba, int tipo, Label error) {
    	
    	boolean salida = true;
    	
    	switch(tipo) {
			case 1:
				salida = validarNumeros(prueba, error);
				break;
			case 2:
				salida = validarNombre(prueba, error);
				break;
			case 3:
				salida = validarCorreo(prueba, error);
				break;
    	}
    	
    	return salida;
    	
    }
    
    //Valida la cantidad de palabras de un campo:
    public boolean validarPalabras(int tipo, String[] palabras, Label error) {
    	   	   	
    	boolean salida = true;
    	
    	if(tipo == 2) {
    		if(palabras.length < 2){
        		error.setText("Ingrese almenos un nombre y un apellido");
        		salida = false;
        	}
        }else {
        	
        	if(palabras.length > 1) {
           		error.setText("El campo contiene más de una palabra");
        		salida = false;
           	}	
        }
    	
    	return salida;
    	
    }
    
    //Valida un que un campo no exceda de tamaño, no tenga caracteres inválidos ni esté vacío:
    public boolean validarCampo(JFXTextField T, int longitud, int tipo, Label error) {
    	
    	boolean test1 = true, test2 = true, test3 = true, test4 = true;
    	
       	String prueba = T.getText();
        prueba = ajustar(prueba);
        T.setText(prueba);
                      
        test1 = validarVacios(prueba, error);
        
        String[] palabras = prueba.split(" ");
        
        test2 = validarPalabras(tipo, palabras, error);   	
    	
    	if(test1 && test2) {
    		
    		if(tipo == 2) {
    			
    			test3 = validarLongitud(prueba, longitud, error);
            	test4 = validarContenido(prueba, tipo, error);
    			
    		}else {
    			
    			test3 = validarLongitud(palabras[0], longitud, error);
            	test4 = validarContenido(palabras[0], tipo, error);
    			
    		}
    		
    	}    	
    	
    	return test1 && test2 && test3 && test4;    	
    	
    }
    
}
    	
    
    		
    	



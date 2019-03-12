package Clases;

import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Validador {
	
	//Muestra un mensaje de alerta en pantalla:	
	public void mostrarMensaje(int tipo, String mensaje, String titulo) {
		
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
	    alerta.setContentText(mensaje);
	    alerta.showAndWait();
	    	
	}
    
	//Valida la cantidad de arrobas y su posición en un string:
    public boolean validarCorreo(String prueba, String campo) {
    	
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
    		mostrarMensaje(2, "El campo " + campo + " contiene una dirección inválida", "Información inválida");
    	}
    	    	
    	return salida;
    
    }
    
    //Valida que los nombres sólo contengan letras y haya un sólo espacio en el medio:
    public boolean validarNombre(String prueba, String campo) {
    	
    	boolean salida = true;
    	int espacio = 0;
    	int pos = 0;
    	
    	for(int x=0; x<prueba.length(); x++) {
    		if(prueba.charAt(x) == 32) {
    			espacio++;
    			pos = x;
    		}
    	}
    	
    	if(espacio == 1) {
    		if(pos == 0 || pos == prueba.length()-1) {
    			salida = false;
    			mostrarMensaje(2, "El campo " + campo + " debe contener un nombre y un apellido", "Información inválida");
    		}else {
    			String[] nombres = prueba.split(" ");
    			salida = validarLetras(nombres[0], campo) && validarLetras(nombres[1], campo);
    		}
    	}else {
    		salida = false;
    		mostrarMensaje(2, "El campo " + campo + " debe contener un nombre y un apellido", "Información inválida");
    	}
    	
    	return salida;
    	
    }
    
    //Valida que un string tenga sólo letras:
    public boolean validarLetras(String prueba, String campo) {
    	
    	boolean salida = true;
    	
    	for(int x=0; x<prueba.length(); x++) {
			if(prueba.charAt(x) < 65 || (prueba.charAt(x) > 90 && prueba.charAt(x) < 97) || prueba.charAt(x) > 122) {
            	salida = false;            	  
            }
    	}
    	
    	if(!salida) {
    		mostrarMensaje(2, "El campo " + campo + " contiene caracteres inválidos", "Información inválida");
    	}
    	
    	return salida;
    	
    }
    
    //Valida que un string tenga sólo numeros:
    public boolean validarNumeros(String prueba, String campo) {
    	
    	boolean salida = true;
    
    	for(int x=0; x<prueba.length(); x++) {
    		if(prueba.charAt(x) < 48 || prueba.charAt(x) > 57) {
               	salida = false;
    		}
    	}
    	
    	if(!salida) {
    		mostrarMensaje(2, "El campo " + campo + " contiene caracteres inválidos", "Información inválida");
    	}
    	
    	return salida;		
    	
    }
    
    //Valida si una cadena está compuesta sólo por espacios:
    public boolean cadenaVacia(String prueba) {
    	
    	boolean salida = true;
    	
    	for(int x=0; x<prueba.length(); x++) {
    		if(prueba.charAt(x) != 32) {
    			salida = false;
    		}
    	}
    	
    	return salida;
    			
    }
    
    //Valida que un campo no esté vacío:
    public boolean validarVacios(String prueba, String campo) {
    	
    	boolean salida = true;
    	
    	if(prueba.compareTo("") == 0 || cadenaVacia(prueba)) {
    		salida = false;
    		mostrarMensaje(2, "El campo " + campo + " está vacío", "Información incompleta");
    	}
    	
    	return salida;
    	
    }
    
    //Valida que un campo no exceda el límite de caracteres:
    public boolean validarLongitud(String prueba, int longitud, String campo) {
    	
    	boolean salida = true;
    	
    	if(prueba.length() > longitud) {
			salida = false;
			mostrarMensaje(2, "El campo " + campo + " debe tener máximo " + longitud + " caracteres", "Información inválida");
		}
    	
    	return salida;
    	
    }
    
    //Valida el contenido de un campo para que no tenga caracteres inválidos:
    public boolean validarContenido(String prueba, int tipo, String campo) {
    	
    	boolean salida = true;
    	
    	switch(tipo) {
			case 1:
				salida = validarNumeros(prueba,campo);
				break;
			case 2:
				salida = validarNombre(prueba,campo);
				break;
			case 3:
				salida = validarCorreo(prueba,campo);
				break;
    	}
    	
    	return salida;
    	
    }
    
    //Valida un que un campo no exceda de tamaño, no tenga caracteres inválidos ni esté vacío:
    public boolean validarCampo(JFXTextField T, int longitud, String campo, int tipo) {
    	
       	String prueba = T.getText();
    	
    	boolean test1 = validarVacios(prueba, campo);
    	boolean test2 = true, test3 = true;
    	
    	if(test1) {
    		test2 = validarLongitud(prueba, longitud, campo);
        	test3 = validarContenido(prueba, tipo, campo);
    	}    	
    	
    	return test1 && test2 && test3;    	
    	
    }

    //Valida en campo de entrada para buscar el usuario a actualizar:
    public boolean validarID(JFXTextField entrada, String campo) {
    
    	String prueba = entrada.getText();
 
    	boolean test1 = validarVacios(prueba,campo);
    	boolean test2 = true;
    	
    	if(test1) {
    		for(int x=0; x<prueba.length(); x++) {
        		if((prueba.charAt(x) < 48 || prueba.charAt(x) > 57) && (prueba.charAt(x) < 65 || (prueba.charAt(x) > 90 && prueba.charAt(x) < 97) || prueba.charAt(x) > 122)) {
        			test2 = false;
        		}
        	}
    		
    		if(!test2) {
          		mostrarMensaje(2, "El campo " + campo + " contiene caracteres inválidos", "Información inválida");
          	}
    	}
      	
    	return test1 && test2;    
    	
    }
    
}
    	
    
    		
    	



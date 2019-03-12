package Clases;

public class Usuario {
	
	private String telefono, sede, id, nombre, correo, perfil, estadoCivil, genero, estado;
	
	public Usuario(String[] datos) {
		this.id = datos[0];
		this.nombre = datos[1];
		this.telefono = datos[2];
		this.estado = datos[3];
		this.sede = datos[4];
		this.correo = datos[5];
		this.perfil = datos[6];
		this.estadoCivil = datos[7];
		this.genero = datos[8];
	}
	
	public void setUsuario(String[] datos) {
		this.nombre = datos[0];
		this.telefono = datos[1];
		this.estado = datos[2];
		this.sede = datos[3];
		this.correo = datos[4];
		this.perfil = datos[5];
		this.estadoCivil = datos[6];
		this.genero = datos[7];
	}
	
	public String[] getUsuario() {
		
		String[] datos = new String[9];
		datos[0] = id;
		datos[1] = nombre;
		datos[2] = telefono;
		datos[3] = estado; 
		datos[4] = sede;
		datos[5] = correo;
		datos[6] = perfil;
		datos[7] = estadoCivil;
		datos[8] = genero;
		
		return datos;
		
	}
	
	public boolean gerente() {
		if(perfil.compareTo("Gerente") == 0) {
			return true;
		}else {
			return false;
		}
	}

}


package Clases;

public class Usuario {
	
	private String telefono, sede, id, nombre, correo, perfil, estadoCivil, genero, estado;
	
	public Usuario(String id, String nombre, String telefono, String estado, String sede, String correo, String perfil, String estadoCivil, String genero) {
		this.telefono = telefono;
		this.nombre = nombre;
		this.correo = correo;
		this.perfil = perfil;
		this.estadoCivil = estadoCivil;
		this.genero = genero;
		this.id = id;
		this.estado = estado;
		this.sede = sede;
	}
	
	public void setUsuario(String nombre, String telefono, String estado, String sede, String correo, String perfil, String estadoCivil, String genero) {
		this.telefono = telefono;
		this.nombre = nombre;
		this.correo = correo;
		this.perfil = perfil;
		this.estadoCivil = estadoCivil;
		this.genero = genero;
		this.estado = estado;
		this.sede = sede;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public String getEstadoCivil() {
		return estadoCivil;
	}
	
	public String getGenero() {
		return genero;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public String getSede() {
		return sede;
	}

}


package Clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Empleado {
	
	private StringProperty nombre;
	private StringProperty id;
	private StringProperty telefono;
	private StringProperty estado;
	private StringProperty sede;
	private StringProperty correo;
	private StringProperty perfil;
	private StringProperty estadoCivil;
	private StringProperty genero;
	
	public Empleado() {
        this(null, null, null, null, null, null, null, null, null);
    }
	
	public Empleado(String nombre, String id, String telefono, String estado, String sede, String correo, String perfil, String estadoCivil, String genero) {
		this.nombre = new SimpleStringProperty(nombre);
		this.id = new SimpleStringProperty(id);
		this.telefono = new SimpleStringProperty(telefono);
		this.estado = new SimpleStringProperty(estado);
		this.sede = new SimpleStringProperty(sede);
		this.correo = new SimpleStringProperty(correo);
		this.perfil = new SimpleStringProperty(perfil);
		this.estadoCivil = new SimpleStringProperty(estadoCivil);
		this.genero = new SimpleStringProperty(genero);
    }
	
	public String getNombre() {
		return nombre.get();
	}
	public StringProperty getNombreP() {
		return nombre;
	}
	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}
	public String getId() {
		return id.get();
	}
	public StringProperty getIdP() {
		return id;
	}
	public void setId(StringProperty id) {
		this.id = id;
	}
	public String getTelefono() {
		return telefono.get();
	}
	public void setTelefono(StringProperty telefono) {
		this.telefono = telefono;
	}
	public String getEstado() {
		return estado.get();
	}
	public void setEstado(StringProperty estado) {
		this.estado = estado;
	}
	public String getSede() {
		return sede.get();
	}
	public void setSede(StringProperty sede) {
		this.sede = sede;
	}
	public String getCorreo() {
		return correo.get();
	}
	public void setCorreo(StringProperty correo) {
		this.correo = correo;
	}
	public String getPerfil() {
		return perfil.get();
	}
	public void setPerfil(StringProperty perfil) {
		this.perfil = perfil;
	}
	public String getEstadoCivil() {
		return estadoCivil.get();
	}
	public void setEstadoCivil(StringProperty estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public String getGenero() {
		return genero.get();
	}
	public void setGenero(StringProperty genero) {
		this.genero = genero;
	}
	
	

}

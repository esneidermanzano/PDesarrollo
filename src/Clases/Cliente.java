package Clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
	
	private StringProperty nombre;
	private StringProperty id;
	private StringProperty telefono;
	
	public Cliente() {
        this(null, null, null);
    }
	
	public Cliente(String nombre, String id, String telefono) {
		this.nombre = new SimpleStringProperty(nombre);
		this.id = new SimpleStringProperty(id);
		this.telefono = new SimpleStringProperty(telefono);
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
	
}
package Clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sede {
	
	private StringProperty id;
	private StringProperty nombre;
	private StringProperty telefono;
	private StringProperty direccion;
	private StringProperty tamano;
	private StringProperty numEmpleados;
	private StringProperty fechaApertura;
	
	
    public Sede() {
    	this(null, null, null, null, null, null, null);
    }
    public Sede(String id, String nombre, String telefono, String direccion, String tamano, String numEmpleados, String fechaApertura) {
		this.id = new SimpleStringProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.telefono = new SimpleStringProperty(telefono);
		this.direccion = new SimpleStringProperty(direccion);
		this.tamano = new SimpleStringProperty(tamano);
		this.numEmpleados = new SimpleStringProperty(numEmpleados);
		this.fechaApertura = new SimpleStringProperty(fechaApertura);
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
	public String getDireccion() {
		return direccion.get();
	}
	public void setDireccion(StringProperty direccion) {
		this.direccion = direccion;
	}
	public String getTamano() {
		return tamano.get();
	}
	public void setTamano(StringProperty tamano) {
		this.tamano = tamano;
	}
	public String getNumEmpleados() {
		return numEmpleados.get();
	}
	public void setNumEmpleados(StringProperty numEmpleados) {
		this.numEmpleados = numEmpleados;
	}
	public String getFechaApertura() {
		return fechaApertura.get();
	}
	public void setFechaApertura(StringProperty fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	

}

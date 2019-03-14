package Clases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
	private String identificador;
	private String nombre;
	private String color;
	private int valorCompra;
	private String fecha;
	private int sede;
	private int cantidad;

		
	public Item(String identificador, String nombre, String color, int vCompra, Date fecha, int sede, int cantidad){
		this.identificador = identificador;
		this.nombre = nombre;
		this.color = color;
		this.valorCompra = vCompra;
		this.cantidad = cantidad;
		DateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.fecha  = formatter.format(fecha);	
		this.sede = sede;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(int valorCompra) {
		this.valorCompra = valorCompra;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int valorVenta) {
		this.cantidad = valorVenta;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getSede() {
		return sede;
	}

	public void setSede(int sede) {
		this.sede = sede;
	}

}
package Clases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
	private String id;
	private String id2;
	private String concatenado;
	private String nombre;
	private String color;
	private int valorCompra;
	private String fecha;
	private String sede;
	private int cantidad;

		
	public Item(String id, String id2, String nombre, String color, int vCompra, Date fecha, String sede, int cantidad){
		this.id = id;
		this.id2 = id2;
		concatenado = id + id2;
		this.nombre = nombre;
		this.color = color;
		this.valorCompra = vCompra;
		this.cantidad = cantidad;
		DateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.fecha  = formatter.format(fecha);	
		this.sede = sede;
	}

	public String getIdentificador() {
		return id;
	}

	public void setIdentificador(String identificador) {
		this.id = identificador;
	}
	public String getIdentificador2() {
		return id2;
	}

	public void setIdentificador2(String identificador2) {
		this.id2 = identificador2;
	}
	public String getConcatenado() {
		return concatenado;
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

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

}
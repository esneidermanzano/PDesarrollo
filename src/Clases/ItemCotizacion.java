package Clases;

public class ItemCotizacion {
	private String id;
	private String id2;
	private String identificador;
	private String descripcion;
	private int cantidad;
	private double precio;
	private double valorTotal;

	public ItemCotizacion(String id, String id2,String identificador, String descripcion, int cantidad,double precio) {
		this.id=id;
		this.id2=id2;
		this.identificador= identificador;
		this.descripcion=descripcion;
		this.cantidad= cantidad;
		this.precio= precio;
		valorTotal= precio*cantidad;
	}
	
	public String getIdentificador1() {
		return id;
	}

	public void setIdentificador(String identificador) {
		this.id = identificador;
	}
	public String getIdentificador2() {
		return id2;
	}
	public void setCantidad(int cantidad) {
		this.cantidad=cantidad;
		valorTotal=this.precio*cantidad;
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public double getValorTotal() {
		return valorTotal;
	}
	
}
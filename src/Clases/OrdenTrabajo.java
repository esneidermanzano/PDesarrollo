package Clases;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OrdenTrabajo {
	private int id;
	private String jefe;
	private int id_articulo;
	private String descripcion;
	private int cantidad;
	private String f_creacion;
	private String f_entrega;
	private String estado;
	public OrdenTrabajo(int c_id, String c_jefe, int c_id_articulo, String c_descripcion, int c_cantidad, Date c_f_creacion, Date c_f_entrega, String c_estado){
		this.id = c_id;
		this.jefe=c_jefe;
		this.id_articulo=c_id_articulo;
		this.descripcion=c_descripcion;
		this.cantidad=c_cantidad;
		DateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.f_creacion= formatter.format(c_f_creacion);
		this.f_entrega=formatter.format(c_f_entrega);
		this.estado=c_estado;
		
		
	}
	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String sta) {
		this.estado=sta;
	}
	public int getId() {
		return this.id;
		
	}
	 public void setId(int num) {
		this.id=num;
	}
	public String getJefe() {
		return this.jefe;
	}
	public void setJefe(String jef) {
		this.jefe=jef;
	}
	public int getId_articulo() {
		return this.id_articulo;
	}
	public void setId_articulo(int num){
		this.id_articulo=num;
	}	
	public String getDescripcion(){
		return this.descripcion;
	}
	public void setDescripcion(String des){
		this.descripcion=des;
	}
	public int getCantidad(){
		return this.cantidad;
	}
	public void setCantidad(int num){
		this.cantidad=num;
	}
	public String getF_creacion(){
		return this.f_creacion;
	}
	public void setF_creacion(String crea){
		this.f_creacion=crea;
	}
	public String getF_entrega(){
		return this.f_entrega;
	}
	public void setF_entrega(String entrega){
		this.f_entrega=entrega;
	}
	

}

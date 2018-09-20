package co.edu.icesi.yeye.camara;

public class Foto {

    public String id;
    private String nombre;
    private String ruta;
    private String fecha;
    private String descripcion;

    public Foto(String id, String nombre, String ruta, String fecha, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.ruta = ruta;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public Foto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

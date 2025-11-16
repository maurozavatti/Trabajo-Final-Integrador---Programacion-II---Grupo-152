package entities;

public class Vehiculo {
    private Integer idVehiculoAsegurado; // PK en BD: vehiculos_asegurados.id_vehiculo_asegurado
    private String dominio;              // patente
    private Integer idAuto;              // FK a autos.id_auto
    private boolean eliminado = false;   // baja lógica en memoria (NO se persiste)

    // Asociación 1→1 unidireccional: Vehiculo → SeguroVehicular
    private SeguroVehicular seguro;      // no se persiste en esta tabla; la FK está en polizas

    public Vehiculo() {}

    public Vehiculo(Integer idVehiculoAsegurado, String dominio, Integer idAuto) {
        this.idVehiculoAsegurado = idVehiculoAsegurado;
        this.dominio = dominio;
        this.idAuto = idAuto;
    }

    public Integer getIdVehiculoAsegurado() { return idVehiculoAsegurado; }
    public void setIdVehiculoAsegurado(Integer idVehiculoAsegurado) { this.idVehiculoAsegurado = idVehiculoAsegurado; }
    public String getDominio() { return dominio; }
    public void setDominio(String dominio) { this.dominio = dominio; }
    public Integer getIdAuto() { return idAuto; }
    public void setIdAuto(Integer idAuto) { this.idAuto = idAuto; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    public SeguroVehicular getSeguro() { return seguro; }
    public void setSeguro(SeguroVehicular seguro) { this.seguro = seguro; }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculoAsegurado=" + idVehiculoAsegurado +
                ", dominio='" + dominio + '\'' +
                ", idAuto=" + idAuto +
                ", eliminado=" + eliminado +
                '}';
    }
}

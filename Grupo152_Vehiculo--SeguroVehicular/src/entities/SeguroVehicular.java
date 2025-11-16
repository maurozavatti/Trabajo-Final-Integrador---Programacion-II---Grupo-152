package entities;

import java.time.LocalDate;

public class SeguroVehicular {
    private Integer idPoliza;                 // PK en BD: polizas.id_poliza
    private Integer idVehiculoAsegurado;      // UNIQUE FK a vehiculos_asegurados.id_vehiculo_asegurado
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double montoTotalAsegurado;
    private boolean eliminado = false;        // baja lógica en memoria (NO se persiste)

    public SeguroVehicular() {}

    public SeguroVehicular(Integer idPoliza, Integer idVehiculoAsegurado, LocalDate fechaInicio, LocalDate fechaFin, Double montoTotalAsegurado) {
        this.idPoliza = idPoliza;
        this.idVehiculoAsegurado = idVehiculoAsegurado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.montoTotalAsegurado = montoTotalAsegurado;
    }

    public Integer getIdPoliza() { return idPoliza; }
    public void setIdPoliza(Integer idPoliza) { this.idPoliza = idPoliza; }
    public Integer getIdVehiculoAsegurado() { return idVehiculoAsegurado; }
    public void setIdVehiculoAsegurado(Integer idVehiculoAsegurado) { this.idVehiculoAsegurado = idVehiculoAsegurado; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public Double getMontoTotalAsegurado() { return montoTotalAsegurado; }
    public void setMontoTotalAsegurado(Double montoTotalAsegurado) { this.montoTotalAsegurado = montoTotalAsegurado; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    @Override
    public String toString() {
        return "SeguroVehicular{" +
                "idPoliza=" + idPoliza +
                ", idVehiculoAsegurado=" + idVehiculoAsegurado +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", montoTotalAsegurado=" + montoTotalAsegurado +
                ", eliminado=" + eliminado +
                '}';
    }
}

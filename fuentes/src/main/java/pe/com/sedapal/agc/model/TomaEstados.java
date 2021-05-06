package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TomaEstados {
    private String codigoCarga;

    @NotNull(message = "1001-codigoRegistro")
    @Max(value = 9999999999L, message = "1002-codigoRegistro, 10")
    private Integer codigoRegistro;
    private String codigoOficinaComercial;
    private String anno;
    private String mes;
    private String cicloComercial;
    private String ruta;
    private String numeroIntenerario;
    private String AOL;
    private String nombreRazonSocial;
    private String nombreUrbanizacion;
    private String nombreCalle;
    private String complementoDireccion;
    private String numeroPuerta;
    private String manzanaLote;
    private String CGV;
    private String accesoPredio;
    private String accesoPuntoMedida;
    private String numeroMedidorLectura;
    private String lecturaAnterior;
    private String numeroRuedasMedidor;
    private String codigoTarifa;
    private String suministro;
    private String codigoCNAE;

    @Size(max = 10, message = "1002-numeroApaObs,10")
    private String numeroApaObs;                // NUM_APA_OBS - NUM_APA_OBS | CONTRATISTA
    
    @Size(max = 10, message = "1002-numeroApa,10")
    private String numeroApa;                    

	@Size(max = 60, message = "1002-opcional,60")
    private String opcional;                    // OBS - OBS | CONTRATISTA

    @Size(max = 10, message = "1002-lecturaMedidor,10")
    private String lecturaMedidor;              // LECT - LECT | CONTRATISTA

    @Size(max = 5, message = "1002-primerIncidente,5")
    private String primerIncidente;             // INC_1 - INC_1 | CONTRATISTA

    @Size(max = 5, message = "1002-detallePrimerIncidente,5")
    private String detallePrimerIncidente;      // DET_1 - DET_1 | CONTRATISTA

    @Size(max = 5, message = "1002-segundoIncidente,5")
    private String segundoIncidente;            // INC_2 - INC_2 | CONTRATISTA

    @Size(max = 5, message = "1002-detalleSegundoIncidente,5")
    private String detalleSegundoIncidente;     // DET_2 - DET_2 | CONTRATISTA

    @Size(max = 5, message = "1002-tercerIncidente,5")
    private String tercerIncidente;             // INC_3 - INC_3 | CONTRATISTA

    @Size(max = 5, message = "1002-detalleTercerIncidente,5")
    private String detalleTercerIncidente;      // DET_3 - DET_3 | CONTRATISTA

    @Size(max = 10, message = "1002-fechaLectura,10")
    private String fechaLectura;                // FECHA - FECHA | CONTRATISTA

    @Size(max = 10, message = "1002-horaLectura,10")
    private String horaLectura;                 // HORA - HORA | CONTRATISTA

    @Size(max = 10, message = "1002-codigoTomadorEstado,10")
    private String codigoTomadorEstado;         // COD_LECTOR - COD_LECTOR | CONTRATISTA

    @Size(max = 3, message = "1002-medio,3")
    private String medio;                       // MEDIO - MEDIO | CONTRATISTA

    private Integer csmoActual;                 // CSMO_ACTUAL - CSMO_ACTUAL | CONTRATISTA
    private String desIncidente1;               // DES_INC_1 - DES_INC_1 | CONTRATISTA
    private String desIncidente2;               // DES_INC_2 - DES_INC_2 | CONTRATISTA
    private String desIncidente3;               // DES_INC_3 - DES_INC_3 | CONTRATISTA
    private String desDetalle1;                 // DES_DET1 - DES_DET1 | CONTRATISTA
    private String desDetalle2;                 // DES_DET2 - DES_DET2 | CONTRATISTA
    private String desDetalle3;                 // DES_DET3 - DES_DET3 | CONTRATISTA
    private String lecturaOriginal;             // LECT_ORI - LECT_ORI | CONTRATISTA
    private String fechaOrigen;                 // FECHA_ORI - FECHA_ORI | CONTRATISTA
    private String horaOrigen;                  // HORA_ORI - HORA_ORI | CONTRATISTA
    private String tipoLectura;                 // TIP_LECTURA - TIP_LECTURA | CONTRATISTA
    private String cup;                         // CUP - CUP | CONTRATISTA
    private String sector;                      // SECTOR - SECTOR | CONTRATISTA
    private String csmoPromedioCalculado;       // CSMO_PROM - CSMO_PROM | CONTRATISTA
    private String observacion;      

	@Size(max = 10, message = "1002-numeroActaIncidencia,10")
    private String numeroActaIncidencia;        // NUM_ACT_INC - NUM_ACT_INC | CONTRATISTA
    private String usuario;

    public TomaEstados() {
        super();
    }

    public String getCodigoCarga() {
        return codigoCarga;
    }

    public void setCodigoCarga(String codigoCarga) {
        this.codigoCarga = codigoCarga;
    }

    public Integer getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(Integer codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public String getCodigoOficinaComercial() {
        return codigoOficinaComercial;
    }

    public void setCodigoOficinaComercial(String codigoOficinaComercial) {
        this.codigoOficinaComercial = codigoOficinaComercial;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getCicloComercial() {
        return cicloComercial;
    }

    public void setCicloComercial(String cicloComercial) {
        this.cicloComercial = cicloComercial;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNumeroIntenerario() {
        return numeroIntenerario;
    }

    public void setNumeroIntenerario(String numeroIntenerario) {
        this.numeroIntenerario = numeroIntenerario;
    }

    public String getAOL() {
        return AOL;
    }

    public void setAOL(String AOL) {
        this.AOL = AOL;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getNombreUrbanizacion() {
        return nombreUrbanizacion;
    }

    public void setNombreUrbanizacion(String nombreUrbanizacion) {
        this.nombreUrbanizacion = nombreUrbanizacion;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public String getComplementoDireccion() {
        return complementoDireccion;
    }

    public void setComplementoDireccion(String complementoDireccion) {
        this.complementoDireccion = complementoDireccion;
    }

    public String getNumeroPuerta() {
        return numeroPuerta;
    }

    public void setNumeroPuerta(String numeroPuerta) {
        this.numeroPuerta = numeroPuerta;
    }

    public String getManzanaLote() {
        return manzanaLote;
    }

    public void setManzanaLote(String manzanaLote) {
        this.manzanaLote = manzanaLote;
    }

    public String getCGV() {
        return CGV;
    }

    public void setCGV(String CGV) {
        this.CGV = CGV;
    }

    public String getAccesoPredio() {
        return accesoPredio;
    }

    public void setAccesoPredio(String accesoPredio) {
        this.accesoPredio = accesoPredio;
    }

    public String getAccesoPuntoMedida() {
        return accesoPuntoMedida;
    }

    public void setAccesoPuntoMedida(String accesoPuntoMedida) {
        this.accesoPuntoMedida = accesoPuntoMedida;
    }

    public String getNumeroMedidorLectura() {
        return numeroMedidorLectura;
    }

    public void setNumeroMedidorLectura(String numeroMedidorLectura) {
        this.numeroMedidorLectura = numeroMedidorLectura;
    }

    public String getLecturaAnterior() {
        return lecturaAnterior;
    }

    public void setLecturaAnterior(String lecturaAnterior) {
        this.lecturaAnterior = lecturaAnterior;
    }

    public String getNumeroRuedasMedidor() {
        return numeroRuedasMedidor;
    }

    public void setNumeroRuedasMedidor(String numeroRuedasMedidor) {
        this.numeroRuedasMedidor = numeroRuedasMedidor;
    }

    public String getCodigoTarifa() {
        return codigoTarifa;
    }

    public void setCodigoTarifa(String codigoTarifa) {
        this.codigoTarifa = codigoTarifa;
    }

    public String getSuministro() {
        return suministro;
    }

    public void setSuministro(String suministro) {
        this.suministro = suministro;
    }

    public String getCodigoCNAE() {
        return codigoCNAE;
    }

    public void setCodigoCNAE(String codigoCNAE) {
        this.codigoCNAE = codigoCNAE;
    }

    public String getNumeroApaObs() {
        return numeroApaObs;
    }

    public void setNumeroApaObs(String numeroApaObs) {
        this.numeroApaObs = numeroApaObs;
    }

    public String getOpcional() {
        return opcional;
    }

    public void setOpcional(String opcional) {
        this.opcional = opcional;
    }

    public String getLecturaMedidor() {
        return lecturaMedidor;
    }

    public void setLecturaMedidor(String lecturaMedidor) {
        this.lecturaMedidor = lecturaMedidor;
    }

    public String getPrimerIncidente() {
        return primerIncidente;
    }

    public void setPrimerIncidente(String primerIncidente) {
        this.primerIncidente = primerIncidente;
    }

    public String getDetallePrimerIncidente() {
        return detallePrimerIncidente;
    }

    public void setDetallePrimerIncidente(String detallePrimerIncidente) {
        this.detallePrimerIncidente = detallePrimerIncidente;
    }

    public String getSegundoIncidente() {
        return segundoIncidente;
    }

    public void setSegundoIncidente(String segundoIncidente) {
        this.segundoIncidente = segundoIncidente;
    }

    public String getDetalleSegundoIncidente() {
        return detalleSegundoIncidente;
    }

    public void setDetalleSegundoIncidente(String detalleSegundoIncidente) {
        this.detalleSegundoIncidente = detalleSegundoIncidente;
    }

    public String getTercerIncidente() {
        return tercerIncidente;
    }

    public void setTercerIncidente(String tercerIncidente) {
        this.tercerIncidente = tercerIncidente;
    }

    public String getDetalleTercerIncidente() {
        return detalleTercerIncidente;
    }

    public void setDetalleTercerIncidente(String detalleTercerIncidente) {
        this.detalleTercerIncidente = detalleTercerIncidente;
    }

    public String getFechaLectura() {
        return fechaLectura;
    }

    public void setFechaLectura(String fechaLectura) {
        this.fechaLectura = fechaLectura;
    }

    public String getHoraLectura() {
        return horaLectura;
    }

    public void setHoraLectura(String horaLectura) {
        this.horaLectura = horaLectura;
    }

    public String getCodigoTomadorEstado() {
        return codigoTomadorEstado;
    }

    public void setCodigoTomadorEstado(String codigoTomadorEstado) {
        this.codigoTomadorEstado = codigoTomadorEstado;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public Integer getCsmoActual() {
        return csmoActual;
    }

    public void setCsmoActual(Integer csmoActual) {
        this.csmoActual = csmoActual;
    }

    public String getDesIncidente1() {
        return desIncidente1;
    }

    public void setDesIncidente1(String desIncidente1) {
        this.desIncidente1 = desIncidente1;
    }

    public String getDesIncidente2() {
        return desIncidente2;
    }

    public void setDesIncidente2(String desIncidente2) {
        this.desIncidente2 = desIncidente2;
    }

    public String getDesIncidente3() {
        return desIncidente3;
    }

    public void setDesIncidente3(String desIncidente3) {
        this.desIncidente3 = desIncidente3;
    }

    public String getDesDetalle1() {
        return desDetalle1;
    }

    public void setDesDetalle1(String desDetalle1) {
        this.desDetalle1 = desDetalle1;
    }

    public String getDesDetalle2() {
        return desDetalle2;
    }

    public void setDesDetalle2(String desDetalle2) {
        this.desDetalle2 = desDetalle2;
    }

    public String getDesDetalle3() {
        return desDetalle3;
    }

    public void setDesDetalle3(String desDetalle3) {
        this.desDetalle3 = desDetalle3;
    }

    public String getLecturaOriginal() {
        return lecturaOriginal;
    }

    public void setLecturaOriginal(String lecturaOriginal) {
        this.lecturaOriginal = lecturaOriginal;
    }

    public String getFechaOrigen() {
        return fechaOrigen;
    }

    public void setFechaOrigen(String fechaOrigen) {
        this.fechaOrigen = fechaOrigen;
    }

    public String getHoraOrigen() {
        return horaOrigen;
    }

    public void setHoraOrigen(String horaOrigen) {
        this.horaOrigen = horaOrigen;
    }

    public String getTipoLectura() {
        return tipoLectura;
    }

    public void setTipoLectura(String tipoLectura) {
        this.tipoLectura = tipoLectura;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCsmoPromedioCalculado() {
        return csmoPromedioCalculado;
    }

    public void setCsmoPromedioCalculado(String csmoPromedioCalculado) {
        this.csmoPromedioCalculado = csmoPromedioCalculado;
    }

    public String getNumeroActaIncidencia() {
        return numeroActaIncidencia;
    }

    public void setNumeroActaIncidencia(String numeroActaIncidencia) {
        this.numeroActaIncidencia = numeroActaIncidencia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getNumeroApa() {
		return numeroApa;
	}

	public void setNumeroApa(String numeroApa) {
		this.numeroApa = numeroApa;
	}
	
	 public String getObservacion() {
			return observacion;
		}

		public void setObservacion(String observacion) {
			this.observacion = observacion;
		}

}

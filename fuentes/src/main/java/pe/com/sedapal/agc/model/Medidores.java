package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Medidores {
	private String codigoCarga;
	@NotNull(message = "1001-codigoRegistro")
	@Max(value = 9999999999L, message = "1002-codigoRegistro, 10")
	private Integer codigoRegistro;
	private String nroSuministro;
	private String nombreCliente;
	private String numeroOrdenServicio;
	private String nombreCalle;
	private String nroPuerta;
	private String duplicador;
	private String cgv;
	private String mz;
	private String lote;
	private String localidad;
	private String distrito;
	private String cus;
	private String direccionReferencia;
	private String medidorActual;
	private String diametro;
	private String accion;
	private String comentario;
	private String texto;
	private String tpi;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaResolucion;
//	@DateTimeFormat(iso = ISO.DATE)
//	private LocalDate fechaInspeccion;	
	private String fechaInspeccion;
	private String tipoOrdenServicio;
	private String horaInspeccion;
	@Size(max = 1, message = "1002-estadoOT,1")
	private String estadoOT; 						// DBESTADO - ESTADO DE LA O/T | CONTRATISTA

	@Max(value = 999999, message = "1002-nroRecepcion, 6")
	private Integer nroRecepcion; 					// DBNRECEP - N° DE RECEPCIÓN | CONTRATISTA

	@Size(max = 9, message = "1002-nroOT,9")
	private String nroOT; 							// DBNUMORD - N° DE O/T | CONTRATISTA

	@Size(max = 3, message = "1002-nroCompromiso,3")
	private String nroCompromiso; 					// DBNUMCOM - N° DE COMPROMISO.CODIGO ASIGNADO POR SEDAPAL AL CONTRATISTA | CONTRATISTA

	//@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaEjecucion; 					// DBFEJEC - FECHA DE EJECUCIÓN DE LA O/T | CONTRATISTA

	@Size(max = 3, message = "1002-codigoDistrito,3")
	private String codigoDistrito; 					// DBDIST - CÓDIGO DE DISTRITO | CONTRATISTA

	@Size(max = 7, message = "1002-nroSuministroCliente,7")
	private String nroSuministroCliente; 			// DBNIS - N° DE SUMINISTRO DEL CLIENTE | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada1,5")
	private String codigoActividadEjecutada1; 		// DBACT01 - ACTIVIDAD 01.CODIGO DE LA ACTIVIDAD EJECUTADA | CONTRATISTA

//	@DecimalMax(value = "999999.000", message = "1002-cantidadEjecutadaActividad1, 8")
	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad1, 8")
	private Double cantidadEjecutadaActividad1; 	// DBCAN01 - CANTIDAD 01.CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada2,5")
	private String codigoActividadEjecutada2; 		// DBACT02 - ACTIVIDAD 02.CODIGO DE LA ACTIVIDAD AJECUTADA | CONTRATISTA

	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad3, 8")
	private Double cantidadEjecutadaActividad2; 	// DBCAN02 - CANTIDAD 02.CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada3,5")
	private String codigoActividadEjecutada3; 		// DBACT03 - ACTIVIDAD 03.CODIGO DE LA ACTIVIDAD AJECUTADA | CONTRATISTA

	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad3, 8")
	private Double cantidadEjecutadaActividad3; 	// DBCAN03 - CANTIDAD 03.CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada4,5")
	private String codigoActividadEjecutada4; 		// DBACT04 - ACTIVIDAD 04.CODIGO DE LA ACTIVIDAD AJECUTADA | CONTRATISTA

	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad4, 8")
	private Double cantidadEjecutadaActividad4; 	// DBCAN04 - CANTIDAD 04.CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada5,5")
	private String codigoActividadEjecutada5; 		// DBACT05 - ACTIVIDAD 05.CODIGO DE LA ACTIVIDAD AJECUTADA | CONTRATISTA

	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad5, 8")
	private Double cantidadEjecutadaActividad5; 	// DBCAN05 - CANTIDAD 05..CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada6,5")
	private String codigoActividadEjecutada6; 		// DBACT06 - ACTIVIDAD 06.CODIGO DE LA ACTIVIDAD AJECUTADA. | CONTRATISTA

	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad6, 8")
	private Double cantidadEjecutadaActividad6; 	// DBCAN06 - CANTIDAD 06.CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada7,5")
	private String codigoActividadEjecutada7; 		// DBACT07 - ACTIVIDAD 07.CODIGO DE LA ACTIVIDAD AJECUTADA | CONTRATISTA

	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad7, 8")
	private Double cantidadEjecutadaActividad7; 	// DBCAN07 - CANTIDAD 07.CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada8,5")
	private String codigoActividadEjecutada8; 		// DBACT08 - ACTIVIDAD 08.CODIGO DE LA ACTIVIDAD AJECUTADA | CONTRATISTA

	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad8, 8")
	private Double cantidadEjecutadaActividad8; 	// DBCAN08 - CANTIDAD 08.CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada9,5")
	private String codigoActividadEjecutada9; 		// DBACT09 - ACTIVIDAD 09.CODIGO DE LA ACTIVIDAD AJECUTADA | CONTRATISTA

	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad9, 8")
	private Double cantidadEjecutadaActividad9; 	// DBCAN09 - CANTIDAD 09..CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA

	@Size(max = 5, message = "1002-codigoActividadEjecutada10,5")
	private String codigoActividadEjecutada10; 		// DBACT10 - ACTIVIDAD 10.CODIGO DE LA ACTIVIDAD AJECUTADA | CONTRATISTA

	@DecimalMax(value = "999.000", message = "1002-cantidadEjecutadaActividad10, 8")
	private Double cantidadEjecutadaActividad10; 	// DBCAN10 - CANTIDAD 10..CANTIDAD EJECUTADA RESPECTO A LA ACTIVIDAD | CONTRATISTA
	private Double largoActiComplEjec; 			// DBLARGO - LARGO.LARGO DE LA ACTIVIDAD COMPLEMENTARIA EJECUTADA | CONTRATISTA
	private Double anchoActividadComplementariaEjecutada; // DBANCHO - ANCHO DE LA ACTIVIDAD COMPLEMENTARIA EJECUTADA | CONTRATISTA
	private String estadoTapa; 						// DBESTTAPA - ESTADO DE LA TAPA.DETERMINA LA CONDICION DE LA TAPA EN LA CONEXIÓN | CONTRATISTA
	private String marTap; 							// DBMARTAP - MARCO Y TAPA RETIRADO.SI HAY HAY UN MATERIAL RETIRADO , SE ESPECIFICA EL TIPO DE MATERIAL DEL ARTICULO | CONTRATISTA
	private Integer valvulasBronce; 				// DBVALBRON - VALVULAS DE BRONCE.SE ESPECIFICA LA CANTIDAD DE VÁLVULAS DE BRONCE RETIRADOS DEL CAMPO | CONTRATISTA
	private Integer valPVC; 						// DBVALPVC - VALVULAS DE PVC.SE ESPECIFICAR LA CANTIDAD DE VÁLVULAS DE PVC RETIRADOS DEL CAMPO | CONTRATISTA
	private Double tuberiaPlomo; 					// DBTUBPLOM - TUBERÍA DE PLOMO.SE ESPECIFICA LA CANTIDAD EN METROS LINEALES DE TUBERÍA DE PLOMO RETIRADOS DE CAMPO | CONTRATISTA
	private String tipoActividadAMM; 				// DBTIPOACT - TIPO ACTIVIDAD DE AMM | CONTRATISTA
	private String motivoLevantamiento; 			// DBMOTLVTO - MOTIVO DEL LEVANTAMIENTO DEL MEDIDOR | CONTRATISTA
	private String nroMedidorRetirado; 				// DBNUMRET - N° DE MEDIDOR RETIRADO / REINSTALADO | CONTRATISTA
	private Integer lectura; 						// DBESTRET - LECTURA (M3) | CONTRATISTA
	private String tipoMatePredomina; 				// DBTIPRET - TIPO DE MATERIAL QUE PREDOMINA EN EL MEDIDOR RETIRADO | CONTRATISTA
	private String dbDiametro; 						// DBDIAM - DIÁMETRO | CONTRATISTA
	private String ubicacionCajaControl; 			// DBUBIC - UBICACIÓN DE LA CAJA DE CONTROL | CONTRATISTA
	private String evaluacionConexionFraudulenta; 	// DBCNXFRA - EVALUACIÓN DE CONEXIÓN FRAUDULENTA | CONTRATISTA
	private String otroTipoConexFraudulenta; 		// DBCNXFOTRO - OTRO TIPO DE CONEXIÓN FRAUDULENTA | CONTRATISTA
	private String tipoLecturaMedidorInstalado; 	// DBTIPLECT - TIPO DE LECTURA DEL MEDIDOR INSTALADO | CONTRATISTA
	private String nroMedidorInstalado; 			// DBNUMINST - N° DE MEDIDOR INSTALADO | CONTRATISTA
	private Integer lecturaM3; 						// DBESTINST - LECTURA (M3) | CONTRATISTA
	private String verificaMedidorInstalado; 		// DBVERMINST - VERIFICACIÓN DEL MEDIDOR INSTALADO | CONTRATISTA
	private String nroDispositivoSeguridad; 		// DBDISPSEG - N° DE DISPOSITIVO DE SEGURIDAD - ABRAZADARA - DE LA BATERIA DE MEDICIÓN | CONTRATISTA
	private String nroAbonado; 						// DBABONADO - N° DE ABONADO  | CONTRATISTA
	private Integer valvulaPuntoMedicion; 			// DBVALPMED - VALVULA CON PUNTO DE MEDICIÓN | CONTRATISTA
	private String materialValvulaPuntoMedicion; 	// DBMATVPM - MATERIAL DE LA VÁLVULA DE PUNTO DE MEDICIÓN | CONTRATISTA
	private Integer valvulaTelescopia; 				// DBVALTELE - VALVULA TELESCÓPICA | CONTRATISTA
	private String materialValvulaTelescopia; 		// DBMATVTEL - MATERIAL DE LA VÁLVULA TELESCÓPICA | CONTRATISTA
	private String dniOperarioOT; 					// DBCOD_OPE - DNI DEL OPERARIO(EJECUTOR DE LA OT) | CONTRATISTA
	private String dniSupervisorOT; 				// DBCOD_TSU - DNI DEL TÉCNICO SUPERVISOR(SUPERVISA LA OT) | CONTRATISTA
	private String dniSupervisorAsignadoSedapal; 	// DBCOD_SUP - DNI DEL SUPERVISOR ASIGNADO POR SEDAPAL | CONTRATISTA
	private String materialTuberiaConexionDomicilia; // DBTUBERIA - MATERIAL DE LA TUBERÍA DE LA CONEXIÓN DOMICILIARIA | CONTRATISTA
	private Double longitudTubeMatriz; 			// DBLONG - LONGITUD (ML) DE LA TUBERÍA DESDE LA MATRIZ | CONTRATISTA
	private String materialRed; 					// DBMATRED - MATERIAL DE LA RED | CONTRATISTA
	private String tipoConexion; 					// DBTIPCNX - TIPO DE CONEXIÓN | CONTRATISTA
	private String tipoPavimento; 					// DBTIPPAV - TIPO DE PAVIMENTO | CONTRATISTA
	private String supervisadaOT; 					// DBFUGAS - FUGAS EN LAS INSTALACIONES INTERNAS | CONTRATISTA
	private String fugasInstainternas; 				// DBTELFCLI - N° TELEFÓNICO DEL CLIENTE | CONTRATISTA
	private String nroTelefonoCliente; 				// DBSUPERV - O/T SUPERVISADA | CONTRATISTA
	private String codigoObservacion; 				// DBCODOBS - CÓDIGO DE OBSERVACIÓN | CONTRATISTA
	private String obsCampo; 						// DBOBSERV - OBSERVACIONES DE CAMPO | CONTRATISTA
	private String dispoSeguridadMedidor; 			// DBDSEGMED - DISPOSITIVO DE SEGURIDAD PARA EL MEDIDOR | CONTRATISTA
	private Integer tuercasValvula; 				// DBTUER_VAL - TUERCAS PARA VÁLVULAS | CONTRATISTA
	private Double cantAdaptadorPresion; 			// DBMAT01 - CANTIDAD DE ADAPTADOR DE PRESIÓN POLIETILENO | CONTRATISTA
	private Double cantidadCodos; 					// DBMAT02 - CANTIDAD DE CODOS | CONTRATISTA
	private Double cantidadCurvas3; 				// DBMAT03 - CANTIDAD DE CURVAS | CONTRATISTA
	private Double cantidadNiplesRemplazo; 		// DBMAT04 - CANTIDAD DE NIPLE DE REEMPLAZO DE MEDIDOR | CONTRATISTA
	private Double cantidadNipleSTD; 				// DBMAT05 - CANTIDAD NIPLE STD C/TUERCA | CONTRATISTA
	private Double cantidadPresintosSeguridad; 	// DBMAT06 - CANTIDAD DE PRESINTOS DE SEGURIDAD | CONTRATISTA
	private Double cantidadTransicion; 			// DBMAT07 - CANTIDAD TRANSICIÓN C/TUERCA | CONTRATISTA
	private Double cantidadTuberia; 				// DBMAT08 - CANTIDAD DE TUBERÍA P/AGUA | CONTRATISTA
	private Double cantidadPolietileno; 			// DBMAT09 - CANTIDAD TUBERÍA DE POLIETILENO | CONTRATISTA
	private Double cantidadValvulaSimple; 			// DBMAT10 - CANTIDAD DE VALVULA DE PASO SIMPLE | CONTRATISTA
	private Double cantidadFiltroMedidor; 			// DBMAT11 - CANTIDAD DE FILTRO DE MEDIDOR | CONTRATISTA
	private Double cantidadDispositivo; 			// DBMAT12 - CANTIDAD DE DISPOSITIVO DE SEGURIDAD | CONTRATISTA
	private Double termoplasticoMT; 				// DBMAT13 - MARCO Y TAPA CON VISOR TERMOPLÁSTICO | CONTRATISTA
	private Double valvulaTelescopicaPVC; 			// DBMAT14 - VALVULA TELESCÓPICA - PVC | CONTRATISTA
	private Double valvulaTelescopicaPmed; 		// DBMAT15 - VALVULA TELESCÓPICA - PMED | CONTRATISTA
	private Double dbMat16; 						// DBMAT16 - VALVULA DE BRONCE 40MM TELES. Y PTO. MED. (JGO.) | CONTRATISTA
	private Double dbMat17; 						// DBMAT17 - FILTRO YEE DE F°F° | CONTRATISTA
	private Double dbMat18; 						// DBMAT18 - MEDIDOR | CONTRATISTA
	private String dbMat18D; 						// DBMAT18_D - MEDIDOR - CÓDIGO INTERNO EN CASO DE SER VALORIZABLE | CONTRATISTA
	private Double dbMat19; 						// DBMAT19 - MARCO Y TAPA DE ACERO GO PARA CNX. 40MM C/SEGURO | CONTRATISTA
	private Double dbMat20; 						// DBMAT20 - BRIDA ACERO 50 -100MM | CONTRATISTA
	private Double dbMat21; 						// DBMAT21 - BUSHING HEMBRA FOGO 1" A 1/2", 3/4" | CONTRATISTA
	private Double dbMat22; 						// DBMAT22 - BUSHING MACHO FOGO 1" A 1/2", 3/4" | CONTRATISTA
	private Double dbMat23; 						// DBMAT23 - NIPLE FOGO ROSCADO 1/2", 3/4, 1" X 3" | CONTRATISTA
	private Double dbMat24; 						// DBMAT24 - VÁLVULA COMPUERTA FOFO, RESILENTE C/BRIDAS 50 -100MM | CONTRATISTA
	private Double dbMat25; 						// DBMAT25 - SISTEMA DE SALIDA A DISTANCIA  | CONTRATISTA
	private Double dbMat26; 						// DBMAT26 - REDUCCIÓN PVC SAP 3" A 2", 4" A 3", 6" A 4" | CONTRATISTA
	private Double dbMat27; 						// DBMAT27 - UNIÓN FLEXIBLE DRESSER SMITH BAR O SIMILAR 50 - 100MM | CONTRATISTA
	private Double dbMat28; 						// DBMAT28 - TAPÓN PRESIÓN 15 - 40MM | CONTRATISTA
	private Double dbMat29; 						// DBMAT29 - UNIÓN PVC PRESIÓN ROSCA 15 - 40MM | CONTRATISTA
	private Double dbMat30; 						// DBMAT30 - UNIÓN PVC PRESIÓN SIMPLE 50 -150MM | CONTRATISTA
	private String estadoCaja; 						// DBESTCAJA - ESTADO DE LA CAJA DE CONTROL | CONTRATISTA
	private String estadoValTeles; 					// DBESTVTEL - ESTADO DE LA VÁLVULA TELESCÓPICA | CONTRATISTA
	private String estadoValPunMedicion; 			// DBESTVPM - ESTADO DE LA VÁLVULA DE PUNTO DE MEDICIÓN | CONTRATISTA
	private String estadoValAbrazadera; 			// DBESTABRA - ESTADO DE LOS DISPOSITIVOS DE SEGURIDAD PARA LAS VÁLVULAS (ABRAZADERA) | CONTRATISTA
	private String estadoMedidor; 					// DBESTMED - ESTADO DEL MEDIDOR | CONTRATISTA
	private String estadoTubForro; 					// DBESTTUBF - ESTADO DE LOS TUBOS DE FORRO | CONTRATISTA
	private String selladoraRatonera; 				// DBSELLRAT - SELLADO DE RATONERA | CONTRATISTA
	private String estadoSoldado; 					// DBESTSOLAD - ESTADO DEL SOLADO | CONTRATISTA
	private Integer tuboForroRetirado; 				// DBTFRET - TUBO DE FORRO RETIRADO | CONTRATISTA
	private String dbNroDisp; 						// DBNUM_DISP - N° DE MÓDULO DE LECTURA REMOTA  | CONTRATISTA
	private String dbNroTelf; 						// DBNUM_TELF - N° DE ABONADO PARA TRANSMISION GSM (CELULAR) | CONTRATISTA
	private String motivoAct; 						// DBMOTIACT - MOTIVO DE INSTALACIÓN DEL MEDIDOR | CONTRATISTA
	private String ejecOperario; 					// DBEJEOPER - CÓDIGO INTERNO DEL OPERARIO EJECUTOR | CONTRATISTA
	private String ejecTecnico; 					// DBEJETECN - CÓDIGO INTERNO DEL TÉCNICO EJECUTOR | CONTRATISTA
	private String inspec; 							// DBINSPEC - CÓDIGO INTERNO DEL SUPERVISOR ASIGNADO POR SEDAPAL | CONTRATISTA
	@Size(max = 4, message = "1002-codigoOficina")
	private String codigoOficina; 					// DBOFICINA - CÓDIGO DE OFICINA COMERCIAL QUE GENERÓ LA O/S | CONTRATISTA
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
	public String getNroSuministro() {
		return nroSuministro;
	}
	public void setNroSuministro(String nroSuministro) {
		this.nroSuministro = nroSuministro;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getNumeroOrdenServicio() {
		return numeroOrdenServicio;
	}
	public void setNumeroOrdenServicio(String numeroOrdenServicio) {
		this.numeroOrdenServicio = numeroOrdenServicio;
	}
	public String getNombreCalle() {
		return nombreCalle;
	}
	public void setNombreCalle(String nombreCalle) {
		this.nombreCalle = nombreCalle;
	}
	public String getNroPuerta() {
		return nroPuerta;
	}
	public void setNroPuerta(String nroPuerta) {
		this.nroPuerta = nroPuerta;
	}
	public String getDuplicador() {
		return duplicador;
	}
	public void setDuplicador(String duplicador) {
		this.duplicador = duplicador;
	}
	public String getCgv() {
		return cgv;
	}
	public void setCgv(String cgv) {
		this.cgv = cgv;
	}
	public String getMz() {
		return mz;
	}
	public void setMz(String mz) {
		this.mz = mz;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public String getCus() {
		return cus;
	}
	public void setCus(String cus) {
		this.cus = cus;
	}
	public String getDireccionReferencia() {
		return direccionReferencia;
	}
	public void setDireccionReferencia(String direccionReferencia) {
		this.direccionReferencia = direccionReferencia;
	}
	public String getMedidorActual() {
		return medidorActual;
	}
	public void setMedidorActual(String medidorActual) {
		this.medidorActual = medidorActual;
	}
	public String getDiametro() {
		return diametro;
	}
	public void setDiametro(String diametro) {
		this.diametro = diametro;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getTpi() {
		return tpi;
	}
	public void setTpi(String tpi) {
		this.tpi = tpi;
	}
	public LocalDate getFechaResolucion() {
		return fechaResolucion;
	}
//	public void setFechaResolucion(LocalDate fechaResolucion) {
//		this.fechaResolucion = fechaResolucion;
//	}
	public void setFechaResolucion(Object localDate) {
		try {
			if (localDate != null) {
				if (localDate instanceof String) {
					this.fechaResolucion = LocalDate.parse((String) localDate);
				} else if (localDate instanceof LocalDate) {
					this.fechaResolucion = (LocalDate) localDate;
				} else if (localDate instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long) localDate);
					this.fechaResolucion = instant.atZone(ZoneId.systemDefault()).toLocalDate();
				} else {
					this.fechaResolucion = null;
				}
			}
		} catch (Exception e) {
			this.fechaResolucion = null;
		}
	}
	
	public String getFechaInspeccion() {
		return fechaInspeccion;
	}
	public void setFechaInspeccion(String fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}
	public String getTipoOrdenServicio() {
		return tipoOrdenServicio;
	}
	public void setTipoOrdenServicio(String tipoOrdenServicio) {
		this.tipoOrdenServicio = tipoOrdenServicio;
	}
	public String getHoraInspeccion() {
		return horaInspeccion;
	}
	public void setHoraInspeccion(String horaInspeccion) {
		this.horaInspeccion = horaInspeccion;
	}
	public String getEstadoOT() {
		return estadoOT;
	}
	public void setEstadoOT(String estadoOT) {
		this.estadoOT = estadoOT;
	}
	public Integer getNroRecepcion() {
		return nroRecepcion;
	}
	public void setNroRecepcion(Integer nroRecepcion) {
		this.nroRecepcion = nroRecepcion;
	}
	public String getNroOT() {
		return nroOT;
	}
	public void setNroOT(String nroOT) {
		this.nroOT = nroOT;
	}
	public String getNroCompromiso() {
		return nroCompromiso;
	}
	public void setNroCompromiso(String nroCompromiso) {
		this.nroCompromiso = nroCompromiso;
	}
	public LocalDate getFechaEjecucion() {
		return fechaEjecucion;
	}
//	public void setFechaEjecucion(LocalDate fechaEjecucion) {
//		this.fechaEjecucion = fechaEjecucion;
//	}
	public void setFechaEjecucion(Object localDate) {
		try {
			if (localDate != null) {
				if (localDate instanceof String) {
					this.fechaEjecucion = LocalDate.parse((String) localDate);
				} else if (localDate instanceof LocalDate) {
					this.fechaEjecucion = (LocalDate) localDate;
				} else if (localDate instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long) localDate);
					this.fechaEjecucion = instant.atZone(ZoneId.systemDefault()).toLocalDate();
				} else if (localDate instanceof java.sql.Date) {
					this.fechaEjecucion = ((java.sql.Date) localDate).toLocalDate();
				} else {
					this.fechaEjecucion = null;
				}
			}
		} catch (Exception e) {
			this.fechaEjecucion = null;
		}
	}
	
	public String getCodigoDistrito() {
		return codigoDistrito;
	}
	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}
	public String getNroSuministroCliente() {
		return nroSuministroCliente;
	}
	public void setNroSuministroCliente(String nroSuministroCliente) {
		this.nroSuministroCliente = nroSuministroCliente;
	}
	public String getCodigoActividadEjecutada1() {
		return codigoActividadEjecutada1;
	}
	public void setCodigoActividadEjecutada1(String codigoActividadEjecutada1) {
		this.codigoActividadEjecutada1 = codigoActividadEjecutada1;
	}
	public Double getCantidadEjecutadaActividad1() {
		return cantidadEjecutadaActividad1;
	}
	public void setCantidadEjecutadaActividad1(Double cantidadEjecutadaActividad1) {
		this.cantidadEjecutadaActividad1 = cantidadEjecutadaActividad1;
	}
	public String getCodigoActividadEjecutada2() {
		return codigoActividadEjecutada2;
	}
	public void setCodigoActividadEjecutada2(String codigoActividadEjecutada2) {
		this.codigoActividadEjecutada2 = codigoActividadEjecutada2;
	}
	public Double getCantidadEjecutadaActividad2() {
		return cantidadEjecutadaActividad2;
	}
	public void setCantidadEjecutadaActividad2(Double cantidadEjecutadaActividad2) {
		this.cantidadEjecutadaActividad2 = cantidadEjecutadaActividad2;
	}
	public String getCodigoActividadEjecutada3() {
		return codigoActividadEjecutada3;
	}
	public void setCodigoActividadEjecutada3(String codigoActividadEjecutada3) {
		this.codigoActividadEjecutada3 = codigoActividadEjecutada3;
	}
	public Double getCantidadEjecutadaActividad3() {
		return cantidadEjecutadaActividad3;
	}
	public void setCantidadEjecutadaActividad3(Double cantidadEjecutadaActividad3) {
		this.cantidadEjecutadaActividad3 = cantidadEjecutadaActividad3;
	}
	public String getCodigoActividadEjecutada4() {
		return codigoActividadEjecutada4;
	}
	public void setCodigoActividadEjecutada4(String codigoActividadEjecutada4) {
		this.codigoActividadEjecutada4 = codigoActividadEjecutada4;
	}
	public Double getCantidadEjecutadaActividad4() {
		return cantidadEjecutadaActividad4;
	}
	public void setCantidadEjecutadaActividad4(Double cantidadEjecutadaActividad4) {
		this.cantidadEjecutadaActividad4 = cantidadEjecutadaActividad4;
	}
	public String getCodigoActividadEjecutada5() {
		return codigoActividadEjecutada5;
	}
	public void setCodigoActividadEjecutada5(String codigoActividadEjecutada5) {
		this.codigoActividadEjecutada5 = codigoActividadEjecutada5;
	}
	public Double getCantidadEjecutadaActividad5() {
		return cantidadEjecutadaActividad5;
	}
	public void setCantidadEjecutadaActividad5(Double cantidadEjecutadaActividad5) {
		this.cantidadEjecutadaActividad5 = cantidadEjecutadaActividad5;
	}
	public String getCodigoActividadEjecutada6() {
		return codigoActividadEjecutada6;
	}
	public void setCodigoActividadEjecutada6(String codigoActividadEjecutada6) {
		this.codigoActividadEjecutada6 = codigoActividadEjecutada6;
	}
	public Double getCantidadEjecutadaActividad6() {
		return cantidadEjecutadaActividad6;
	}
	public void setCantidadEjecutadaActividad6(Double cantidadEjecutadaActividad6) {
		this.cantidadEjecutadaActividad6 = cantidadEjecutadaActividad6;
	}
	public String getCodigoActividadEjecutada7() {
		return codigoActividadEjecutada7;
	}
	public void setCodigoActividadEjecutada7(String codigoActividadEjecutada7) {
		this.codigoActividadEjecutada7 = codigoActividadEjecutada7;
	}
	public Double getCantidadEjecutadaActividad7() {
		return cantidadEjecutadaActividad7;
	}
	public void setCantidadEjecutadaActividad7(Double cantidadEjecutadaActividad7) {
		this.cantidadEjecutadaActividad7 = cantidadEjecutadaActividad7;
	}
	public String getCodigoActividadEjecutada8() {
		return codigoActividadEjecutada8;
	}
	public void setCodigoActividadEjecutada8(String codigoActividadEjecutada8) {
		this.codigoActividadEjecutada8 = codigoActividadEjecutada8;
	}
	public Double getCantidadEjecutadaActividad8() {
		return cantidadEjecutadaActividad8;
	}
	public void setCantidadEjecutadaActividad8(Double cantidadEjecutadaActividad8) {
		this.cantidadEjecutadaActividad8 = cantidadEjecutadaActividad8;
	}
	public String getCodigoActividadEjecutada9() {
		return codigoActividadEjecutada9;
	}
	public void setCodigoActividadEjecutada9(String codigoActividadEjecutada9) {
		this.codigoActividadEjecutada9 = codigoActividadEjecutada9;
	}
	public Double getCantidadEjecutadaActividad9() {
		return cantidadEjecutadaActividad9;
	}
	public void setCantidadEjecutadaActividad9(Double cantidadEjecutadaActividad9) {
		this.cantidadEjecutadaActividad9 = cantidadEjecutadaActividad9;
	}
	public String getCodigoActividadEjecutada10() {
		return codigoActividadEjecutada10;
	}
	public void setCodigoActividadEjecutada10(String codigoActividadEjecutada10) {
		this.codigoActividadEjecutada10 = codigoActividadEjecutada10;
	}
	public Double getCantidadEjecutadaActividad10() {
		return cantidadEjecutadaActividad10;
	}
	public void setCantidadEjecutadaActividad10(Double cantidadEjecutadaActividad10) {
		this.cantidadEjecutadaActividad10 = cantidadEjecutadaActividad10;
	}
	public Double getLargoActiComplEjec() {
		return largoActiComplEjec;
	}
	public void setLargoActiComplEjec(Double largoActiComplEjec) {
		this.largoActiComplEjec = largoActiComplEjec;
	}
	public Double getAnchoActividadComplementariaEjecutada() {
		return anchoActividadComplementariaEjecutada;
	}
	public void setAnchoActividadComplementariaEjecutada(Double anchoActividadComplementariaEjecutada) {
		this.anchoActividadComplementariaEjecutada = anchoActividadComplementariaEjecutada;
	}
	public String getEstadoTapa() {
		return estadoTapa;
	}
	public void setEstadoTapa(String estadoTapa) {
		this.estadoTapa = estadoTapa;
	}
	public String getMarTap() {
		return marTap;
	}
	public void setMarTap(String marTap) {
		this.marTap = marTap;
	}
	public Integer getValvulasBronce() {
		return valvulasBronce;
	}
	public void setValvulasBronce(Integer valvulasBronce) {
		this.valvulasBronce = valvulasBronce;
	}
	public Integer getValPVC() {
		return valPVC;
	}
	public void setValPVC(Integer valPVC) {
		this.valPVC = valPVC;
	}
	public Double getTuberiaPlomo() {
		return tuberiaPlomo;
	}
	public void setTuberiaPlomo(Double tuberiaPlomo) {
		this.tuberiaPlomo = tuberiaPlomo;
	}
	public String getTipoActividadAMM() {
		return tipoActividadAMM;
	}
	public void setTipoActividadAMM(String tipoActividadAMM) {
		this.tipoActividadAMM = tipoActividadAMM;
	}
	public String getMotivoLevantamiento() {
		return motivoLevantamiento;
	}
	public void setMotivoLevantamiento(String motivoLevantamiento) {
		this.motivoLevantamiento = motivoLevantamiento;
	}
	public String getNroMedidorRetirado() {
		return nroMedidorRetirado;
	}
	public void setNroMedidorRetirado(String nroMedidorRetirado) {
		this.nroMedidorRetirado = nroMedidorRetirado;
	}
	public Integer getLectura() {
		return lectura;
	}
	public void setLectura(Integer lectura) {
		this.lectura = lectura;
	}
	public String getTipoMatePredomina() {
		return tipoMatePredomina;
	}
	public void setTipoMatePredomina(String tipoMatePredomina) {
		this.tipoMatePredomina = tipoMatePredomina;
	}
	public String getDbDiametro() {
		return dbDiametro;
	}
	public void setDbDiametro(String dbDiametro) {
		this.dbDiametro = dbDiametro;
	}
	public String getUbicacionCajaControl() {
		return ubicacionCajaControl;
	}
	public void setUbicacionCajaControl(String ubicacionCajaControl) {
		this.ubicacionCajaControl = ubicacionCajaControl;
	}
	public String getEvaluacionConexionFraudulenta() {
		return evaluacionConexionFraudulenta;
	}
	public void setEvaluacionConexionFraudulenta(String evaluacionConexionFraudulenta) {
		this.evaluacionConexionFraudulenta = evaluacionConexionFraudulenta;
	}
	public String getOtroTipoConexFraudulenta() {
		return otroTipoConexFraudulenta;
	}
	public void setOtroTipoConexFraudulenta(String otroTipoConexFraudulenta) {
		this.otroTipoConexFraudulenta = otroTipoConexFraudulenta;
	}
	public String getTipoLecturaMedidorInstalado() {
		return tipoLecturaMedidorInstalado;
	}
	public void setTipoLecturaMedidorInstalado(String tipoLecturaMedidorInstalado) {
		this.tipoLecturaMedidorInstalado = tipoLecturaMedidorInstalado;
	}
	public String getNroMedidorInstalado() {
		return nroMedidorInstalado;
	}
	public void setNroMedidorInstalado(String nroMedidorInstalado) {
		this.nroMedidorInstalado = nroMedidorInstalado;
	}
	public Integer getLecturaM3() {
		return lecturaM3;
	}
	public void setLecturaM3(Integer lecturaM3) {
		this.lecturaM3 = lecturaM3;
	}
	public String getVerificaMedidorInstalado() {
		return verificaMedidorInstalado;
	}
	public void setVerificaMedidorInstalado(String verificaMedidorInstalado) {
		this.verificaMedidorInstalado = verificaMedidorInstalado;
	}
	public String getNroDispositivoSeguridad() {
		return nroDispositivoSeguridad;
	}
	public void setNroDispositivoSeguridad(String nroDispositivoSeguridad) {
		this.nroDispositivoSeguridad = nroDispositivoSeguridad;
	}
	public String getNroAbonado() {
		return nroAbonado;
	}
	public void setNroAbonado(String nroAbonado) {
		this.nroAbonado = nroAbonado;
	}
	public Integer getValvulaPuntoMedicion() {
		return valvulaPuntoMedicion;
	}
	public void setValvulaPuntoMedicion(Integer valvulaPuntoMedicion) {
		this.valvulaPuntoMedicion = valvulaPuntoMedicion;
	}
	public String getMaterialValvulaPuntoMedicion() {
		return materialValvulaPuntoMedicion;
	}
	public void setMaterialValvulaPuntoMedicion(String materialValvulaPuntoMedicion) {
		this.materialValvulaPuntoMedicion = materialValvulaPuntoMedicion;
	}
	public Integer getValvulaTelescopia() {
		return valvulaTelescopia;
	}
	public void setValvulaTelescopia(Integer valvulaTelescopia) {
		this.valvulaTelescopia = valvulaTelescopia;
	}
	public String getMaterialValvulaTelescopia() {
		return materialValvulaTelescopia;
	}
	public void setMaterialValvulaTelescopia(String materialValvulaTelescopia) {
		this.materialValvulaTelescopia = materialValvulaTelescopia;
	}
	public String getDniOperarioOT() {
		return dniOperarioOT;
	}
	public void setDniOperarioOT(String dniOperarioOT) {
		this.dniOperarioOT = dniOperarioOT;
	}
	public String getDniSupervisorOT() {
		return dniSupervisorOT;
	}
	public void setDniSupervisorOT(String dniSupervisorOT) {
		this.dniSupervisorOT = dniSupervisorOT;
	}
	public String getDniSupervisorAsignadoSedapal() {
		return dniSupervisorAsignadoSedapal;
	}
	public void setDniSupervisorAsignadoSedapal(String dniSupervisorAsignadoSedapal) {
		this.dniSupervisorAsignadoSedapal = dniSupervisorAsignadoSedapal;
	}
	public String getMaterialTuberiaConexionDomicilia() {
		return materialTuberiaConexionDomicilia;
	}
	public void setMaterialTuberiaConexionDomicilia(String materialTuberiaConexionDomicilia) {
		this.materialTuberiaConexionDomicilia = materialTuberiaConexionDomicilia;
	}
	public Double getLongitudTubeMatriz() {
		return longitudTubeMatriz;
	}
	public void setLongitudTubeMatriz(Double longitudTubeMatriz) {
		this.longitudTubeMatriz = longitudTubeMatriz;
	}
	public String getMaterialRed() {
		return materialRed;
	}
	public void setMaterialRed(String materialRed) {
		this.materialRed = materialRed;
	}
	public String getTipoConexion() {
		return tipoConexion;
	}
	public void setTipoConexion(String tipoConexion) {
		this.tipoConexion = tipoConexion;
	}
	public String getTipoPavimento() {
		return tipoPavimento;
	}
	public void setTipoPavimento(String tipoPavimento) {
		this.tipoPavimento = tipoPavimento;
	}
	public String getSupervisadaOT() {
		return supervisadaOT;
	}
	public void setSupervisadaOT(String supervisadaOT) {
		this.supervisadaOT = supervisadaOT;
	}
	public String getFugasInstainternas() {
		return fugasInstainternas;
	}
	public void setFugasInstainternas(String fugasInstainternas) {
		this.fugasInstainternas = fugasInstainternas;
	}
	public String getNroTelefonoCliente() {
		return nroTelefonoCliente;
	}
	public void setNroTelefonoCliente(String nroTelefonoCliente) {
		this.nroTelefonoCliente = nroTelefonoCliente;
	}
	public String getCodigoObservacion() {
		return codigoObservacion;
	}
	public void setCodigoObservacion(String codigoObservacion) {
		this.codigoObservacion = codigoObservacion;
	}
	public String getObsCampo() {
		return obsCampo;
	}
	public void setObsCampo(String obsCampo) {
		this.obsCampo = obsCampo;
	}
	public String getDispoSeguridadMedidor() {
		return dispoSeguridadMedidor;
	}
	public void setDispoSeguridadMedidor(String dispoSeguridadMedidor) {
		this.dispoSeguridadMedidor = dispoSeguridadMedidor;
	}
	public Integer getTuercasValvula() {
		return tuercasValvula;
	}
	public void setTuercasValvula(Integer tuercasValvula) {
		this.tuercasValvula = tuercasValvula;
	}
	public Double getCantAdaptadorPresion() {
		return cantAdaptadorPresion;
	}
	public void setCantAdaptadorPresion(Double cantAdaptadorPresion) {
		this.cantAdaptadorPresion = cantAdaptadorPresion;
	}
	public Double getCantidadCodos() {
		return cantidadCodos;
	}
	public void setCantidadCodos(Double cantidadCodos) {
		this.cantidadCodos = cantidadCodos;
	}
	public Double getCantidadCurvas3() {
		return cantidadCurvas3;
	}
	public void setCantidadCurvas3(Double cantidadCurvas3) {
		this.cantidadCurvas3 = cantidadCurvas3;
	}
	public Double getCantidadNiplesRemplazo() {
		return cantidadNiplesRemplazo;
	}
	public void setCantidadNiplesRemplazo(Double cantidadNiplesRemplazo) {
		this.cantidadNiplesRemplazo = cantidadNiplesRemplazo;
	}
	public Double getCantidadNipleSTD() {
		return cantidadNipleSTD;
	}
	public void setCantidadNipleSTD(Double cantidadNipleSTD) {
		this.cantidadNipleSTD = cantidadNipleSTD;
	}
	public Double getCantidadPresintosSeguridad() {
		return cantidadPresintosSeguridad;
	}
	public void setCantidadPresintosSeguridad(Double cantidadPresintosSeguridad) {
		this.cantidadPresintosSeguridad = cantidadPresintosSeguridad;
	}
	public Double getCantidadTransicion() {
		return cantidadTransicion;
	}
	public void setCantidadTransicion(Double cantidadTransicion) {
		this.cantidadTransicion = cantidadTransicion;
	}
	public Double getCantidadTuberia() {
		return cantidadTuberia;
	}
	public void setCantidadTuberia(Double cantidadTuberia) {
		this.cantidadTuberia = cantidadTuberia;
	}
	public Double getCantidadPolietileno() {
		return cantidadPolietileno;
	}
	public void setCantidadPolietileno(Double cantidadPolietileno) {
		this.cantidadPolietileno = cantidadPolietileno;
	}
	public Double getCantidadValvulaSimple() {
		return cantidadValvulaSimple;
	}
	public void setCantidadValvulaSimple(Double cantidadValvulaSimple) {
		this.cantidadValvulaSimple = cantidadValvulaSimple;
	}
	public Double getCantidadFiltroMedidor() {
		return cantidadFiltroMedidor;
	}
	public void setCantidadFiltroMedidor(Double cantidadFiltroMedidor) {
		this.cantidadFiltroMedidor = cantidadFiltroMedidor;
	}
	public Double getCantidadDispositivo() {
		return cantidadDispositivo;
	}
	public void setCantidadDispositivo(Double cantidadDispositivo) {
		this.cantidadDispositivo = cantidadDispositivo;
	}
	public Double getTermoplasticoMT() {
		return termoplasticoMT;
	}
	public void setTermoplasticoMT(Double termoplasticoMT) {
		this.termoplasticoMT = termoplasticoMT;
	}
	public Double getValvulaTelescopicaPVC() {
		return valvulaTelescopicaPVC;
	}
	public void setValvulaTelescopicaPVC(Double valvulaTelescopicaPVC) {
		this.valvulaTelescopicaPVC = valvulaTelescopicaPVC;
	}
	public Double getValvulaTelescopicaPmed() {
		return valvulaTelescopicaPmed;
	}
	public void setValvulaTelescopicaPmed(Double valvulaTelescopicaPmed) {
		this.valvulaTelescopicaPmed = valvulaTelescopicaPmed;
	}
	public Double getDbMat16() {
		return dbMat16;
	}
	public void setDbMat16(Double dbMat16) {
		this.dbMat16 = dbMat16;
	}
	public Double getDbMat17() {
		return dbMat17;
	}
	public void setDbMat17(Double dbMat17) {
		this.dbMat17 = dbMat17;
	}
	public Double getDbMat18() {
		return dbMat18;
	}
	public void setDbMat18(Double dbMat18) {
		this.dbMat18 = dbMat18;
	}
	public String getDbMat18D() {
		return dbMat18D;
	}
	public void setDbMat18D(String dbMat18D) {
		this.dbMat18D = dbMat18D;
	}
	public Double getDbMat19() {
		return dbMat19;
	}
	public void setDbMat19(Double dbMat19) {
		this.dbMat19 = dbMat19;
	}
	public Double getDbMat20() {
		return dbMat20;
	}
	public void setDbMat20(Double dbMat20) {
		this.dbMat20 = dbMat20;
	}
	public Double getDbMat21() {
		return dbMat21;
	}
	public void setDbMat21(Double dbMat21) {
		this.dbMat21 = dbMat21;
	}
	public Double getDbMat22() {
		return dbMat22;
	}
	public void setDbMat22(Double dbMat22) {
		this.dbMat22 = dbMat22;
	}
	public Double getDbMat23() {
		return dbMat23;
	}
	public void setDbMat23(Double dbMat23) {
		this.dbMat23 = dbMat23;
	}
	public Double getDbMat24() {
		return dbMat24;
	}
	public void setDbMat24(Double dbMat24) {
		this.dbMat24 = dbMat24;
	}
	public Double getDbMat25() {
		return dbMat25;
	}
	public void setDbMat25(Double dbMat25) {
		this.dbMat25 = dbMat25;
	}
	public Double getDbMat26() {
		return dbMat26;
	}
	public void setDbMat26(Double dbMat26) {
		this.dbMat26 = dbMat26;
	}
	public Double getDbMat27() {
		return dbMat27;
	}
	public void setDbMat27(Double dbMat27) {
		this.dbMat27 = dbMat27;
	}
	public Double getDbMat28() {
		return dbMat28;
	}
	public void setDbMat28(Double dbMat28) {
		this.dbMat28 = dbMat28;
	}
	public Double getDbMat29() {
		return dbMat29;
	}
	public void setDbMat29(Double dbMat29) {
		this.dbMat29 = dbMat29;
	}
	public Double getDbMat30() {
		return dbMat30;
	}
	public void setDbMat30(Double dbMat30) {
		this.dbMat30 = dbMat30;
	}
	public String getEstadoCaja() {
		return estadoCaja;
	}
	public void setEstadoCaja(String estadoCaja) {
		this.estadoCaja = estadoCaja;
	}
	public String getEstadoValTeles() {
		return estadoValTeles;
	}
	public void setEstadoValTeles(String estadoValTeles) {
		this.estadoValTeles = estadoValTeles;
	}
	public String getEstadoValPunMedicion() {
		return estadoValPunMedicion;
	}
	public void setEstadoValPunMedicion(String estadoValPunMedicion) {
		this.estadoValPunMedicion = estadoValPunMedicion;
	}
	public String getEstadoValAbrazadera() {
		return estadoValAbrazadera;
	}
	public void setEstadoValAbrazadera(String estadoValAbrazadera) {
		this.estadoValAbrazadera = estadoValAbrazadera;
	}
	public String getEstadoMedidor() {
		return estadoMedidor;
	}
	public void setEstadoMedidor(String estadoMedidor) {
		this.estadoMedidor = estadoMedidor;
	}
	public String getEstadoTubForro() {
		return estadoTubForro;
	}
	public void setEstadoTubForro(String estadoTubForro) {
		this.estadoTubForro = estadoTubForro;
	}
	public String getSelladoraRatonera() {
		return selladoraRatonera;
	}
	public void setSelladoraRatonera(String selladoraRatonera) {
		this.selladoraRatonera = selladoraRatonera;
	}
	public String getEstadoSoldado() {
		return estadoSoldado;
	}
	public void setEstadoSoldado(String estadoSoldado) {
		this.estadoSoldado = estadoSoldado;
	}
	public Integer getTuboForroRetirado() {
		return tuboForroRetirado;
	}
	public void setTuboForroRetirado(Integer tuboForroRetirado) {
		this.tuboForroRetirado = tuboForroRetirado;
	}
	public String getDbNroDisp() {
		return dbNroDisp;
	}
	public void setDbNroDisp(String dbNroDisp) {
		this.dbNroDisp = dbNroDisp;
	}
	public String getDbNroTelf() {
		return dbNroTelf;
	}
	public void setDbNroTelf(String dbNroTelf) {
		this.dbNroTelf = dbNroTelf;
	}
	public String getMotivoAct() {
		return motivoAct;
	}
	public void setMotivoAct(String motivoAct) {
		this.motivoAct = motivoAct;
	}
	public String getEjecOperario() {
		return ejecOperario;
	}
	public void setEjecOperario(String ejecOperario) {
		this.ejecOperario = ejecOperario;
	}
	public String getEjecTecnico() {
		return ejecTecnico;
	}
	public void setEjecTecnico(String ejecTecnico) {
		this.ejecTecnico = ejecTecnico;
	}
	public String getInspec() {
		return inspec;
	}
	public void setInspec(String inspec) {
		this.inspec = inspec;
	}
	public String getCodigoOficina() {
		return codigoOficina;
	}
	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}		
		
}

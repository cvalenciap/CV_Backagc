package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InspeccionesComerciales {
	@NotNull(message = "1001-codigoRegistro")
	@Max(value = 9999999999L, message = "1002-codigoRegistro, 10")
	private Integer codigoRegistro;
	private String codigoCarga;
	private String oficinaComercial;
	private String  generoUsuario;
	private String cus;
	private String ruta;
	private String itin;
	private String tipsum;
	private String fechaEmision;
	private String observacion;
	private String nombreRazon;
	private String leRuc;
	private String telefonoFax;
	private String codAbastecimiento;
	private String referenciaDireccion;
	private String cua ;
	private String medidor;
	private String diametroMedidor;
	private String ultLectura;


	@Size(max = 50, message = "1002-ptoMedia,50")
	private String ptoMedia;				// PTO_MED - Punto de Medida: Cota. Frente | CONTRATISTA

	@Size(max = 4, message = "1002-acom,4")
	private String acom;					// ACOM - Numero de Unidades de Uso Asociadas | CONTRATISTA
	private Integer nisRad;
	private Integer numOS ;
	private String tipOS ;
	private String fechaResolucion;

	@Size(max = 6, message = "1002-codEmpleado,6")
	private String codEmpleado;				// COD_EMP - Codigo del Empleado que Ejecuto la O/S. | CONTRATISTA

	@Size(max = 10, message = "1002-fechaVisita,10")
	private String fechaVisita;				// FEC_VIS - Fecha de Visita | CONTRATISTA

	@Size(max = 5, message = "1002-coAccejec,5")
	private String coAccejec;				// CO_ACCEJ - Accion ejecutada en la Visita | CONTRATISTA

	@Size(max = 9, message = "1002-serv,9")
	private String serv;					// SERV - Estado de la Conexion | CONTRATISTA

	@Size(max = 11, message = "1002-servDT,11")
	private String servDT ;					// SERV_DT - Estado de las Conexiones Internas | CONTRATISTA

	@Size(max = 4, message = "1002-caja,4")
	private String caja;					// CAJA - Ubicacion de la caja: Azotea, Escalera, Esquina, etc. | CONTRATISTA

	@Size(max = 17, message = "1002-caja,17")
	private String estadoCaja;				// EST_CAJA - Estado de la caja : bueno, marco/tapa mal estado, etc. | CONTRATISTA

	@Size(max = 5, message = "1002-cnxCon,5")
	private String cnxCon ;					// CNX_CON - Define si la iámetro cuenta con Medidor o Niple | CONTRATISTA

	@Size(max = 10, message = "1002-cxCDT,10")
	private String cxCDT ;					// CX_C_DT - Numero de Conexiones

	@Size(max = 8, message = "1002-cxCDT,8")
	private String lecturaMedidor;			// LEC - Lectura del Medidor | CONTRATISTA

	@Size(max = 13, message = "1002-estMedidorEncontrado1,13")
	private String estMedidorEncontrado1;	// FUN_MED1 - Estado Medidor 1 encontrado | CONTRATISTA

	@Size(max = 13, message = "1002-estMedidorEncontrado2,13")
	private String estMedidorEncontrado2;	// FUN_MED2 - Esatdo Medidor 2 encontrado | CONTRATISTA

	@Size(max = 13, message = "1002-estMedidorEncontrado3,13")
	private String estMedidorEncontrado3 ;	// FUN_MED3 - Estado Medidor 3 encontrado | CONTRATISTA

	@Size(max = 12, message = "1002-prescinto,12")
	private String prescinto ;				// PRECINT - Prescinto de Seguridad | CONTRATISTA

	@Size(max = 8, message = "1002-fugaCaja,8")
	private String fugaCaja ;				// FUGA_CAJ - Fuga en la Caja | CONTRATISTA

	@Size(max = 1, message = "1002-nroConexiones,1")
	private String nroConexiones;			// N_CONEX - Numero de conexiones | CONTRATISTA

	@Size(max = 10, message = "1002-medidorEncontrado2,10")
	private String medidorEncontrado2;		// MED2 - Medidor 2 encontrado | CONTRATISTA

	@Size(max = 10, message = "1002-medidorEncontrado3,10")
	private String medidorEncontrado3;		// MED3 - Medidor 3 encontrado | CONTRATISTA

	@Size(max = 4, message = "1002-niple2,4")
	private String niple2 ;					// NIPLE2 - Niple | CONTRATISTA

	@Size(max = 4, message = "1002-niple3,4")
	private String niple3 ;					// NIPLE3 - Niple | CONTRATISTA

	@Size(max = 6, message = "1002-valvulas,6")
	private String valvulas ;				// VALVULAS - Valvula de Entrada y Salida | CONTRATISTA

	@Size(max = 9, message = "1002-disposit,9")
	private String disposit ;				// DISPOSIT - Dispositivo de Seguridad | CONTRATISTA

	@Size(max = 1, message = "1002-clandes,1")
	private String clandes ;				// CLANDES - Verificar si tiene Clandestino | CONTRATISTA

	@Size(max = 8, message = "1002-niv_finc,8")
	private String niv_finc ;				// NIV_FINC - Numero de Niveles Finca | CONTRATISTA

	@Size(max = 5, message = "1002-estado,5")
	private String estado ;					// ESTADO - Estado del Predio: Habitado, Deshabitado | CONTRATISTA

	@Size(max = 2, message = "1002-n_habit,2")
	private String n_habit ;				// N_HABIT - Numero de Habitantes del Predio | CONTRATISTA

	@Size(max = 3, message = "1002-uu_ocup,3")
	private String uu_ocup ;				// UU_OCUP - Unidades de Uso Ocupada | CONTRATISTA

	@Size(max = 3, message = "1002-soc_ocup,3")
	private String soc_ocup ;				// SOC_OCUP - Unidades de Uso Social Ocupada | CONTRATISTA

	@Size(max = 3, message = "1002-dom_ocup,3")
	private String dom_ocup ;				// DOM_OCUP - Unidades de Uso Doméstico Ocupada | CONTRATISTA

	@Size(max = 3, message = "1002-com_ocup,3")
	private String com_ocup ;				// COM_OCUP - Unidades de Uso Comercial Ocupada | CONTRATISTA

	@Size(max = 3, message = "1002-ind_ocup,3")
	private String ind_ocup ;				// IND_OCUP - Unidades de Uso Industrial Ocupada | CONTRATISTA

	@Size(max = 3, message = "1002-est_ocup,3")
	private String est_ocup ;				// EST_OCUP - Unidades de Uso Estatal Ocupada | CONTRATISTA

	@Size(max = 3, message = "1002-uu_docup,3")
	private String uu_docup ;				// UU_DOCUP - Unidades de Uso DesOcupada | CONTRATISTA

	@Size(max = 3, message = "1002-soc_desc,3")
	private String soc_desc ;				// SOC_DESC - Unidades de Uso Social DesOcupada | CONTRATISTA

	@Size(max = 3, message = "1002-dom_desc,3")
	private String dom_desc ;				// DOM_DESC - Unidades de Uso Doméstico DesOcupada | CONTRATISTA

	@Size(max = 3, message = "1002-com_desc,3")
	private String com_desc ;				// COM_DESC - Unidades de Uso Comercial DesOcupada | CONTRATISTA

	@Size(max = 3, message = "1002-ind_desc,3")
	private String ind_desc ;				// IND_DESC - Unidades de Uso Industrial DesOcupada | CONTRATISTA

	@Size(max = 3, message = "1002-est_desc,3")
	private String est_desc ;				// EST_DESC - Unidades de Uso Estatal DesOcupada | CONTRATISTA

	@Size(max = 3, message = "1002-uso_unic,3")
	private String uso_unic ;				// USO_UNIC - Codigo de Uso de Agua | CONTRATISTA

	private String calle ;
	private String numero ;
	private String duplicad ;
	private String mza ;
	private String lote ;
	private String cgv ;
	private String urbaniza ;
	private String distrito ;

	@Size(max = 30, message = "1002-calle_c,30")
	private String calle_c ;				// CALLE_C - Calle Contratista | CONTRATISTA

	@Size(max = 6, message = "1002-num_c,6")
	private String num_c ;					// NUM_C - Numero Municipal Contratista | CONTRATISTA

	@Size(max = 10, message = "1002-dupli_c,10")
	private String dupli_c ;				// DUPLI_C - Duplicador Contratista | CONTRATISTA

	@Size(max = 5, message = "1002-mza_c,5")
	private String mza_c ;					// MZA_C - Manzana Contratista | CONTRATISTA

	@Size(max = 5, message = "1002-lote_c,5")
	private String lote_c ;					// LOTE_C - Lote Contratista | CONTRATISTA

	@Size(max = 50, message = "1002-urb_c,50")
	private String urb_c ;					// URB_C - Urbanizacion Contratista | CONTRATISTA

	@Size(max = 50, message = "1002-ref_c,50")
	private String ref_c ;					// DIST_C - Distrito Contratista | CONTRATISTA

	@Size(max = 32, message = "1002-dist_c,32")
	private String dist_c ;					// DIST_C - Distrito Contratista | CONTRATISTA

	@Size(max = 6, message = "1002-atendio,6")
	private String atendio ;				// ATENDIO - Parentesco Persona que atendio: Propietario, Inquilino, Familiar | CONTRATISTA

	@Size(max = 20, message = "1002-paterno,20")
	private String paterno;					// PATERNO - Apellido Paterno atendio | CONTRATISTA

	@Size(max = 20, message = "1002-materno,20")
	private String materno ;				// MATERNO - Apellido Materno atendio | CONTRATISTA

	@Size(max = 20, message = "1002-nombre,20")
	private String nombre ;					// NOMBRE - Nombre atendio | CONTRATISTA

	@Size(max = 70, message = "1002-razsoc,70")
	private String razsoc ;					// RAZSOC - Razon Social atendio | CONTRATISTA

	@Size(max = 10, message = "1002-dni,10")
	private String dni ;					// DNI - DNI atendio | CONTRATISTA

	@Size(max = 7, message = "1002-telef,7")
	private String telef ;					// TELEF - Telefono atendio | CONTRATISTA

	@Size(max = 8, message = "1002-fax,8")
	private String fax ;					// FAX - Fax atendio | CONTRATISTA

	@Size(max = 11, message = "1002-ruc,11")
	private String ruc ;					// RUC - Ruc Empresa Entrevistada | CONTRATISTA

	@Size(max = 3, message = "1002-inod_b,3")
	private String inod_b;					// INOD_B - Cantidad Inodoro Bueno | CONTRATISTA

	@Size(max = 3, message = "1002-inod_f,3")
	private String inod_f ;					// INOD_F - Cantidad Inodoro con Fuga | CONTRATISTA

	@Size(max = 3, message = "1002-inod_cl,3")
	private String inod_cl ;				// INOD_CL - Cantidad Inodoro Cerrado / Clausurado | CONTRATISTA

	@Size(max = 3, message = "1002-lava_b,3")
	private String lava_b ;					// LAVA_B - Cantidad Lavadero Bueno | CONTRATISTA

	@Size(max = 3, message = "1002-lava_f,3")
	private String lava_f ;					// LAVA_F - Cantidad Lavadero con Fuga | CONTRATISTA

	@Size(max = 3, message = "1002-lava_cl,3")
	private String lava_cl ;				// LAVA_CL - Cantidad Lavadero Cerrado / Clausurado | CONTRATISTA

	@Size(max = 3, message = "1002-duch_b,3")
	private String duch_b ;					// DUCH_B - Cantidad Ducha Bueno | CONTRATISTA

	@Size(max = 3, message = "1002-duch_f,3")
	private String duch_f ;					// DUCH_F - Cantidad Ducha con Fuga | CONTRATISTA

	@Size(max = 3, message = "1002-duch_cl,3")
	private String duch_cl ;				// DUCH_CL - Cantidad Ducha Cerrado / Clausurado | CONTRATISTA

	@Size(max = 3, message = "1002-urin_b,3")
	private String urin_b ;					// URIN_B - Cantidad Urinario Bueno | CONTRATISTA

	@Size(max = 3, message = "1002-urin_f,3")
	private String urin_f ;					// URIN_F - Cantidad Urinario con Fuga | CONTRATISTA

	@Size(max = 3, message = "1002-urin_cl,3")
	private String urin_cl ;				// URIN_CL - Cantidad Urinario Cerrado / Clausurado | CONTRATISTA

	@Size(max = 3, message = "1002-grifo_b,3")
	private String grifo_b ;				// GRIFO_B - Cantidad Grifo Bueno | CONTRATISTA

	@Size(max = 3, message = "1002-grifo_f,3")
	private String grifo_f ;				// GRIFO_F - Cantidad Grifo con Fuga | CONTRATISTA


	@Size(max = 3, message = "1002-grifo_cl,3")
	private String grifo_cl ;				// GRIFO_CL - Cantidad Grifo Cerrado / Clausurado | CONTRATISTA


	@Size(max = 2, message = "1002-ciste_b,2")
	private String ciste_b ;				// CISTE_B - Cantidad Cisterna Bueno | CONTRATISTA

	@Size(max = 2, message = "1002-ciste_f,2")
	private String ciste_f ;				// CISTE_F - Cantidad Cisterna con Fuga | CONTRATISTA

	@Size(max = 2, message = "1002-ciste_cl,2")
	private String ciste_cl ;				// CISTE_CL - Cantidad Cisterna Cerrado / Clausurado | CONTRATISTA

	@Size(max = 2, message = "1002-tanqu_b,2")
	private String tanqu_b ;				// TANQU_B - Cantidad Tanque Bueno | CONTRATISTA

	@Size(max = 2, message = "1002-tanqu_f,2")
	private String tanqu_f ;				// TANQU_F - Cantidad Tanque con Fuga | CONTRATISTA

	@Size(max = 2, message = "1002-tanqu_cl,2")
	private String tanqu_cl ;				// TANQU_CL - Cantidad Tanque Cerrado / Clausurado | CONTRATISTA

	@Size(max = 2, message = "1002-pisc_b,2")
	private String pisc_b ;					// PISC_B - Cantidad Piscina Bueno | CONTRATISTA

	@Size(max = 2, message = "1002-pisc_f,2")
	private String pisc_f ;					// PISC_F - Cantidad Piscina con Fuga | CONTRATISTA

	@Size(max = 2, message = "1002-pisc_cl,2")
	private String pisc_cl ;				// PISC_CL - Cantidad Piscina Cerrado / Clausurado | CONTRATISTA

	@Size(max = 2, message = "1002-bidet_b,2")
	private String bidet_b ;				// BIDET_B - Cantidad Bidet Bueno | CONTRATISTA

	@Size(max = 2, message = "1002-bidet_f,2")
	private String bidet_f ;				// BIDET_F - Cantidad Bidet con Fuga | CONTRATISTA

	@Size(max = 2, message = "1002-bidet_cl,2")
	private String bidet_cl ;				// BIDET_CL - Cantidad Bidet Cerrado / Clausurado | CONTRATISTA

	@Size(max = 1, message = "1002-abast,1")
	private String abast ;					// ABAST - Abastecimiento de Agua en la Zona:S/N | CONTRATISTA

	@Size(max = 30, message = "1002-ptome_ca,30")
	private String ptome_ca ;				// PTOME_CA - Calle del Punto de Medida | CONTRATISTA

	@Size(max = 6, message = "1002-num_p,6")
	private String num_p ;					// NUM_P - Numero del Punto de Medida | CONTRATISTA

	@Size(max = 10, message = "1002-dup_p,10")
	private String dup_p ;					// DUP_P - Duplicador del Punto de Medida | CONTRATISTA

	@Size(max = 5, message = "1002-mza_p,5")
	private String mza_p ;					// MZA_P - Manzana del Punto de Medida | CONTRATISTA

	@Size(max = 5, message = "1002-lote_p,5")
	private String lote_p ;					// LOTE_P - Lote del Punto de Medida | CONTRATISTA

	@Size(max = 250, message = "1002-observ,250")
	private String observ ;					// OBSERV - Observacion | CONTRATISTA

	@Size(max = 250, message = "1002-observm1,250")
	private String observm1 ;				// OBSERVM1 - Observacion | CONTRATISTA

	@Size(max = 250, message = "1002-observm2,250")
	private String observm2 ;				// OBSERVM2 - Observacion | CONTRATISTA

	@Size(max = 5, message = "1002-cota_sum_c,5")
	private String cota_sum_c ;				// COTA_SUM_C - Cota que reporta la contratista | CONTRATISTA

	@Size(max = 2, message = "1002-pisos_c,2")
	private String pisos_c ;				// PISOS_C - Pisos que reporta la contratista | CONTRATISTA

	@Size(max = 5, message = "1002-cod_ubic_c,5")
	private String cod_ubic_c ;				// COD_UBIC_C - Codigo de Ubicacion que reporta la contratista | CONTRATISTA

	@Size(max = 15, message = "1002-fte_conexion_c,15")
	private String fte_conexion_c ;			// FTE_CONEXION_C -  | CONTRATISTA

	@Size(max = 12, message = "1002-cup_c,12")
	private String cup_c ;					// CUP_C - Cup que reporta la contratista | CONTRATISTA

	@Size(max = 5, message = "1002-cota_sum,5")
	private String cota_sum ;				// COTA_SUM - Cota que reporta la contratista | CONTRATISTA

	@Size(max = 2, message = "1002-pisos,2")
	private String pisos ;					// PISOS - Pisos que reporta la contratista | CONTRATISTA

	@Size(max = 5, message = "1002-cod_ubic,5")
	private String cod_ubic;				// COD_UBIC - Codigo de Ubicacion que reporta la contratista | CONTRATISTA

	@Size(max = 15, message = "1002-fte_conexion,15")
	private String fte_conexion ;			// FTE_CONEXION - Frente que reporta la contratista | CONTRATISTA

	@Size(max = 2, message = "1002-cup,2")
	private String cup ;					// CUP - Cup que reporta la contratista | CONTRATISTA

	@Size(max = 1, message = "1002-devuelto,1")
	private String devuelto ;				// DEVUELTO - SI/NO | CONTRATISTA

	@Size(max = 10, message = "1002-date_d,10")
	private String date_d ;					// FECHA_D - Fecha de Devolución | CONTRATISTA

	@Size(max = 1, message = "1002-prior,1")
	private String prior ;					// PRIOR_1 -  | CONTRATISTA

	@Size(max = 1, message = "1002-actualiza,1")
	private String actualiza ;				// ACTUALIZA -  | CONTRATISTA

	@NotNull(message = "1001-date_c")
//	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date date_c ;					// FECHA_C -  | CONTRATISTA

	@Size(max = 1, message = "1002-resuelto,1")
	private String resuelto ;				// RESUELTO -  | CONTRATISTA

	@Size(max = 3, message = "1002-multi,3")
	private String multi ;					// MULTI - 0:Unifamiliar  1:Multifamiliar | CONTRATISTA

	@Size(max = 1, message = "1002-parcial,1")
	private String parcial ;				// PARCIAL -  | CONTRATISTA

	@Size(max = 15, message = "1002-fte_cnx,15")
	private String fte_cnx ;				// FTE_CNX -  | CONTRATISTA

	@Size(max = 5, message = "1002-cota_sumc,5")
	private String cota_sumc ;				// COTA_SUMC -  | CONTRATISTA

	@Size(max = 2, message = "1002-pisosc,2")
	private String pisosc ;					// PISOSC -  | CONTRATISTA

	@Size(max = 5, message = "1002-cod_ubicc,5")
	private String cod_ubicc ;				// COD_UBICC -  | CONTRATISTA

	@Size(max = 15, message = "1002-fte_cnxc,15")
	private String fte_cnxc;				// FTE_CNXC -  | CONTRATISTA

	@Size(max = 2, message = "1002-cupc,2")
	private String cupc ;					// CUPC -  | CONTRATISTA

	@Size(max = 5, message = "1002-hora_i,5")
	private String hora_i ;					// HORA_I - Hora Inicio Inspeccion | CONTRATISTA

	@Size(max = 5, message = "1002-hora_f,5")
	private String hora_f ;					// HORA_F - Hora Final Inspeccion | CONTRATISTA

	@Size(max = 5, message = "1002-hora_f,5")
	private String valv_e_tlsc ;			// VALV_E_TLSC - Valvula de Entrada Telescopica (S o N) | CONTRATISTA
	private String mat_valv_e ;				// MAT_VALV_E - Material de Valvula de Entrada | CONTRATISTA

	@Size(max = 1, message = "1002-valv_s_p,1")
	private String valv_s_p ;				// VALV_S_P - Valvula de Salida del Predio (S o N) | CONTRATISTA

	@Size(max = 5, message = "1002-valv_s_p,1")
	private String mat_valv_s ;				// MAT_VALV_S - Material de Valvula de Salida | CONTRATISTA
	private Integer sector ;
	private String cup_pred ;
	private Integer aol ;
	private String ciclo ;
	private String nreclamo ;
	private Double celular_cli ;
	private Integer fax_cli ;
	private Integer niv_predio ;
	private String nse_pre ;
	private Integer soc_ocup_cat ;
	private Integer dom_ocup_cat ;
	private Integer com_ocup_cat ;
	private Integer ind_ocup_cat ;
	private Integer est_ocup_cat ;
	private Integer soc_desoc_cat ;
	private Integer dom_desoc_cat ;
	private Integer com_desoc_cat ;
	private Integer ind_desoc_cat ;
	private Integer est_desoc_cat ;
	private String est_sum ;
	private String fte_conex ;
	private String ubic_pred ;
	private Double dia_conex ;
	private Double cota_conex ;
	private String disp_seg ;
	private String tip_lec ;
	private String hor_abasc ;
	private Integer nia ;
	private String dia_alcan ;
	private String fte_alcan ;
	private String ubic_alcan ;
	private String tip_suelo_alca;
	private String tip_descar_alc;
	private Double cotah_alcan ;
	private Double cotav_alcan ;
	private Double long_alcan ;
	private Double profund_alcan ;
	private Integer tr_grasas ;
	private String sistrat ;
	private String mat_tapa_alcan;
	private String est_tapa_alcan;
	private String mat_caja_alcan;
	private String est_caja_alcan;
	private String mat_tub_alcan ;
	private String est_tub_alcan ;

	@Size(max = 39, message = "1002-email,39")
	private String email ; 					// EMAIL - Email del cliente | CONTRATISTA

	@DecimalMax(value = "99999999999.000", message = "1002-cel_cli_c, 8")
	private Double cel_cli_c ; 				// CEL_CLI_C - Celular del cliente obtenido por el contratista | CONTRATISTA

	@Max(value = 999999999, message = "1002-telf_emp, 9")
	private Integer telf_emp ; 				// TELF_EMP - Telefono de empresa obtenido por el contratista | CONTRATISTA

	@Size(max = 6, message = "1002-piso_dpto_f,6")
	private String piso_dpto_f ; 			// PISO_DPTO_F - Piso Dpto de la finca | CONTRATISTA

	@Max(value = 999, message = "1002-telf_emp, 3")
	private Integer sector_f ; 				// SECTOR_F - sector de la finca | CONTRATISTA

	@Size(max = 15, message = "1002-fte_finca,15")
	private String fte_finca ; 				// FTE_FINCA - frente de la finca | CONTRATISTA

	@Size(max = 1, message = "1002-nse_f,1")
	private String nse_f ; 					// NSE_F - Nivel socio iámetro A B C D E  | CONTRATISTA

	@Size(max = 100, message = "1002-obs_f,100")
	private String obs_f ; 					// OBS_F - Observaciones referencia de acceso a la finca | CONTRATISTA

	@Size(max = 12, message = "1002-corr_cup,12")
	private String corr_cup ; 				// CORR_CUP - Corregir CUP | CONTRATISTA

	@Size(max = 1, message = "1002-pred_no_ubic,1")
	private String pred_no_ubic ; 			// PRED_NO_UBIC - Predio No Ubicado? (S o N) | CONTRATISTA

	@Size(max = 1, message = "1002-var_act_predio,1")
	private String var_act_predio; 			// VAR_ACT_PREDIO - Vario Actividad del Predio? (S o N) | CONTRATISTA

	@Size(max = 5, message = "1002-inspec_realiz,5")
	private String inspec_realiz ; 			// INSPEC_REALIZ - Inspección Realizada | CONTRATISTA

	@Size(max = 5, message = "1002-mot_no_ing,5")
	private String mot_no_ing ; 			// MOT_NO_ING - Motivo de No Ingreso | CONTRATISTA

	@DecimalMax(value = "999999.00", message = "1002-cel_cli_c, 7")
	private Double dia_conex_agua; 			// DIA_CONEX_AGUA - Diametro de la conexión de agua | CONTRATISTA

	@Size(max = 5, message = "1002-imposibilidad,5")
	private String imposibilidad ; 			// IMPOSIBILIDAD - Imposibilidad de lectura en la caja | CONTRATISTA

	@Size(max = 5, message = "1002-seguro_tap,5")
	private String seguro_tap ; 			// SEGURO_TAP - Tipo de seguro de tapa | CONTRATISTA

	@Size(max = 5, message = "1002-est_tapa,5")
	private String est_tapa ; 				// EST_TAPA - Estado de la tapa | CONTRATISTA

	@Size(max = 5, message = "1002-inci_medidor,5")
	private String inci_medidor ; 			// INCI_MEDIDOR - Incidencias del medidor | CONTRATISTA
	private String acceso_inmueb ;

	@Size(max = 60, message = "1002-obs_atendio,60")
	private String obs_atendio ; 			// OBS_ATENDIO - Observación del entrevistado | CONTRATISTA

	@Size(max = 1, message = "1002-cartografia,1")
	private String cartografia ; 			// CARTOGRAFIA - Verifica la existencia de cartografía (S o N) | CONTRATISTA

	@Size(max = 5, message = "1002-inci_medidor,5")
	private String mnto_cartog ; 			// MNTO_CARTOG - Contiene: Localidad, mza, Predio subdicidido, Predio unificado, Via sin nombre | CONTRATISTA

	@Size(max = 1, message = "1002-corr_cup_f,1")
	private String corr_cup_f ; 			// CORR_CUP_F - S o N | CONTRATISTA

	@Size(max = 20, message = "1002-num_med,20")
	private String num_med ; 				// NUM_MED -  Número de medidor | CONTRATISTA

	@Size(max = 5, message = "1002-dia_med,5")
	private String dia_med ; 				// DIA_MED -  | CONTRATISTA

	@Size(max = 5, message = "1002-cnx2_con,5")
	private String cnx2_con ; 				// CNX2_CON - Define si la iámetro cuenta con Medidor, Niple, SinNiple/SinMedidor | CONTRATISTA

	@Size(max = 5, message = "1002-cnx3_con,5")
	private String cnx3_con ; 				// CNX3_CON - Define si la conexion cuenta con Medidor, Niple, SinNiple/SinMedidor | CONTRATISTA

	@Size(max = 5, message = "1002-dia_med2,5")
	private String dia_med2; 				// DIA_MED2 - Dia_med2 | CONTRATISTA

	@Size(max = 5, message = "1002-dia_med3,5")
	private String dia_med3 ; 				// DIA_MED3 - Dia_med3 | CONTRATISTA

	@Max(value = 999999999, message = "1002-lec_med2, 8")
	private Integer lec_med2 ; 				// LEC_MED2 - Lectura del Medidor | CONTRATISTA

	@Max(value = 999999999, message = "1002-lec_med3, 8")
	private Integer lec_med3 ; 				// LEC_MED3 - Lectura del Medidor | CONTRATISTA

	@DecimalMax(value = "999999.00", message = "1002-cel_cli_c, 7")
	private Double cota_hor_des ; 			// COTA_HOR_DES - Cota Horizontal de la conexión de alcantarillado | CONTRATISTA

	@DecimalMax(value = "999999.00", message = "1002-cel_cli_c, 7")
	private Double cota_ver_des ; 			// COTA_VER_DES - Cota Vertical de la conexión de alcantarillado | CONTRATISTA

	@DecimalMax(value = "999999.00", message = "1002-cel_cli_c, 7")
	private Double long_cnx_des ; 			// LONG_CNX_DES - Longitud de la conexión de alcantarillado | CONTRATISTA

	@DecimalMax(value = "999999.00", message = "1002-cel_cli_c, 7")
	private Double prof_cja_des ; 			// PROF_CJA_DES - Profuncidad de la caja de la conexión de alcantarillado | CONTRATISTA

	@Size(max = 5, message = "1002-tip_suelo_des, 5")
	private String tip_suelo_des ; 			// TIP_SUELO_DES - Tipo de suelo | CONTRATISTA

	@Size(max  = 1, message = "1002-ind_tram_des, 1")
	private String ind_tram_des ; 			// IND_TRAM_DES - Indicador de tratamiento (S o N) | CONTRATISTA

	@Size(max  = 1, message = "1002-dia_conex_des, 1")
	private String dia_conex_des ; 			// DIA_CONEX_DES - diametro de la conexión de alcantarillado | CONTRATISTA

	@Size(max  = 1, message = "1002-silo, 1")
	private String silo ; 					// SILO - S o N | CONTRATISTA

	@Size(max  = 5, message = "1002-est_tapa_des, 5")
	private String est_tapa_des ; 			// EST_TAPA_DES - Estado de la tapa | CONTRATISTA

	@Size(max  = 5, message = "1002-est_caja_des, 5")
	private String est_caja_des ; 			// EST_CAJA_DES - Estado de la caja | CONTRATISTA

	@Size(max  = 5, message = "1002-est_tub_des, 5")
	private String est_tub_des ; 			// EST_TUB_DES - Estado de la tuberia | CONTRATISTA

	@Size(max  = 5, message = "1002-tip_mat_tap_d, 5")
	private String tip_mat_tap_d ; 			// TIP_MAT_TAP_D - Tipo material de tapa | CONTRATISTA

	@Size(max  = 5, message = "1002-tip_mat_caj_d, 5")
	private String tip_mat_caj_d ; 			// TIP_MAT_CAJ_D - Tipo material de caja | CONTRATISTA

	@Size(max  = 5, message = "1002-tip_mat_tub_d, 5")
	private String tip_mat_tub_d ; 			// TIP_MAT_TUB_D - Tipo material de tuberia | CONTRATISTA

	@Size(max  = 5, message = "1002-sis_trat_res, 5")
	private String sis_trat_res ; 			// SIS_TRAT_RES - Sistema de tratamiento | CONTRATISTA

	@Size(max  = 5, message = "1002-tip_des_des, 5")
	private String tip_des_des ; 			// TIP_DES_DES - Tipo de descarga | CONTRATISTA

	@Size(max  = 1, message = "1002-clandes_des, 1")
	private String clandes_des ; 			// CLANDES_DES - Verificar si tiene Clandestino (S o N) | CONTRATISTA

	@Max(value = 999999, message = "1002-cod_sup, 6")
	private Integer cod_sup ;				// COD_SU - Código del Supervisor | CONTRATISTA

	@Max(value = 999999, message = "1002-cod_digit, 6")
	private Integer cod_digit ; 			// COD_DIGIT - Código del Digitador | CONTRATISTA

	
	public InspeccionesComerciales() {
		super();
	}
	
	public InspeccionesComerciales(Integer codigoRegistro, String codigoCarga, String oficinaComercial,
			String generoUsuario, String cus, String ruta, String itin, String tipsum, String fechaEmision,
			String observacion, String nombreRazon, String leRuc, String telefonoFax, String codAbastecimiento,
			String referenciaDireccion, String cua, String medidor, String diametroMedidor, String ultLectura,
			String ptoMedia, String acom, Integer nisRad, Integer numOS, String tipOS, String fechaResolucion,
			String codEmpleado, String fechaVisita, String coAccejec, String serv, String servDT, String caja,
			String estadoCaja, String cnxCon, String cxCDT, String lecturaMedidor, String estMedidorEncontrado1,
			String estMedidorEncontrado2, String estMedidorEncontrado3, String prescinto, String fugaCaja,
			String nroConexiones, String medidorEncontrado2, String medidorEncontrado3, String niple2, String niple3,
			String valvulas, String disposit, String clandes, String niv_finc, String estado, String n_habit,
			String uu_ocup, String soc_ocup, String dom_ocup, String com_ocup, String ind_ocup, String est_ocup,
			String uu_docup, String soc_desc, String dom_desc, String com_desc, String ind_desc, String est_desc,
			String uso_unic, String calle, String numero, String duplicad, String mza, String lote, String cgv,
			String urbaniza, String distrito, String calle_c, String num_c, String dupli_c, String mza_c, String lote_c,
			String urb_c, String ref_c, String dist_c, String atendio, String paterno, String materno, String nombre,
			String razsoc, String dni, String telef, String fax, String ruc, String inod_b, String inod_f,
			String inod_cl, String lava_b, String lava_f, String lava_cl, String duch_b, String duch_f, String duch_cl,
			String urin_b, String urin_f, String urin_cl, String grifo_b, String grifo_f, String grifo_cl,
			String ciste_b, String ciste_f, String ciste_cl, String tanqu_b, String tanqu_f, String tanqu_cl,
			String pisc_b, String pisc_f, String pisc_cl, String bidet_b, String bidet_f, String bidet_cl, String abast,
			String ptome_ca, String num_p, String dup_p, String mza_p, String lote_p, String observ, String observm1,
			String observm2, String cota_sum_c, String pisos_c, String cod_ubic_c, String fte_conexion_c, String cup_c,
			String cota_sum, String pisos, String cod_ubic, String fte_conexion, String cup, String devuelto,
			String date_d, String prior, String actualiza, Date date_c, String resuelto, String multi, String parcial,
			String fte_cnx, String cota_sumc, String pisosc, String cod_ubicc, String fte_cnxc, String cupc,
			String hora_i, String hora_f, String valv_e_tlsc, String mat_valv_e, String valv_s_p, String mat_valv_s,
			Integer sector, String cup_pred, Integer aol, String ciclo, String nreclamo, Double celular_cli,
			Integer fax_cli, Integer niv_predio, String nse_pre, Integer soc_ocup_cat, Integer dom_ocup_cat,
			Integer com_ocup_cat, Integer ind_ocup_cat, Integer est_ocup_cat, Integer soc_desoc_cat,
			Integer dom_desoc_cat, Integer com_desoc_cat, Integer ind_desoc_cat, Integer est_desoc_cat, String est_sum,
			String fte_conex, String ubic_pred, Double dia_conex, Double cota_conex, String disp_seg, String tip_lec,
			String hor_abasc, Integer nia, String dia_alcan, String fte_alcan, String ubic_alcan, String tip_suelo_alca,
			String tip_descar_alc, Double cotah_alcan, Double cotav_alcan, Double long_alcan, Double profund_alcan,
			Integer tr_grasas, String sistrat, String mat_tapa_alcan, String est_tapa_alcan, String mat_caja_alcan,
			String est_caja_alcan, String mat_tub_alcan, String est_tub_alcan, String email, Double cel_cli_c,
			Integer telf_emp, String piso_dpto_f, Integer sector_f, String fte_finca, String nse_f, String obs_f,
			String corr_cup, String pred_no_ubic, String var_act_predio, String inspec_realiz, String mot_no_ing,
			Double dia_conex_agua, String imposibilidad, String seguro_tap, String est_tapa, String inci_medidor,
			String acceso_inmueb, String obs_atendio, String cartografia, String mnto_cartog, String corr_cup_f,
			String num_med, String dia_med, String cnx2_con, String cnx3_con, String dia_med2, String dia_med3,
			Integer lec_med2, Integer lec_med3, Double cota_hor_des, Double cota_ver_des, Double long_cnx_des,
			Double prof_cja_des, String tip_suelo_des, String ind_tram_des, String dia_conex_des, String silo,
			String est_tapa_des, String est_caja_des, String est_tub_des, String tip_mat_tap_d, String tip_mat_caj_d,
			String tip_mat_tub_d, String sis_trat_res, String tip_des_des, String clandes_des, Integer cod_sup,
			Integer cod_digit) {
		super();
		this.codigoRegistro = codigoRegistro;
		this.codigoCarga = codigoCarga;
		this.oficinaComercial = oficinaComercial;
		this.generoUsuario = generoUsuario;
		this.cus = cus;
		this.ruta = ruta;
		this.itin = itin;
		this.tipsum = tipsum;
		this.fechaEmision = fechaEmision;
		this.observacion = observacion;
		this.nombreRazon = nombreRazon;
		this.leRuc = leRuc;
		this.telefonoFax = telefonoFax;
		this.codAbastecimiento = codAbastecimiento;
		this.referenciaDireccion = referenciaDireccion;
		this.cua = cua;
		this.medidor = medidor;
		this.diametroMedidor = diametroMedidor;
		this.ultLectura = ultLectura;
		this.ptoMedia = ptoMedia;
		this.acom = acom;
		this.nisRad = nisRad;
		this.numOS = numOS;
		this.tipOS = tipOS;
		this.fechaResolucion = fechaResolucion;
		this.codEmpleado = codEmpleado;
		this.fechaVisita = fechaVisita;
		this.coAccejec = coAccejec;
		this.serv = serv;
		this.servDT = servDT;
		this.caja = caja;
		this.estadoCaja = estadoCaja;
		this.cnxCon = cnxCon;
		this.cxCDT = cxCDT;
		this.lecturaMedidor = lecturaMedidor;
		this.estMedidorEncontrado1 = estMedidorEncontrado1;
		this.estMedidorEncontrado2 = estMedidorEncontrado2;
		this.estMedidorEncontrado3 = estMedidorEncontrado3;
		this.prescinto = prescinto;
		this.fugaCaja = fugaCaja;
		this.nroConexiones = nroConexiones;
		this.medidorEncontrado2 = medidorEncontrado2;
		this.medidorEncontrado3 = medidorEncontrado3;
		this.niple2 = niple2;
		this.niple3 = niple3;
		this.valvulas = valvulas;
		this.disposit = disposit;
		this.clandes = clandes;
		this.niv_finc = niv_finc;
		this.estado = estado;
		this.n_habit = n_habit;
		this.uu_ocup = uu_ocup;
		this.soc_ocup = soc_ocup;
		this.dom_ocup = dom_ocup;
		this.com_ocup = com_ocup;
		this.ind_ocup = ind_ocup;
		this.est_ocup = est_ocup;
		this.uu_docup = uu_docup;
		this.soc_desc = soc_desc;
		this.dom_desc = dom_desc;
		this.com_desc = com_desc;
		this.ind_desc = ind_desc;
		this.est_desc = est_desc;
		this.uso_unic = uso_unic;
		this.calle = calle;
		this.numero = numero;
		this.duplicad = duplicad;
		this.mza = mza;
		this.lote = lote;
		this.cgv = cgv;
		this.urbaniza = urbaniza;
		this.distrito = distrito;
		this.calle_c = calle_c;
		this.num_c = num_c;
		this.dupli_c = dupli_c;
		this.mza_c = mza_c;
		this.lote_c = lote_c;
		this.urb_c = urb_c;
		this.ref_c = ref_c;
		this.dist_c = dist_c;
		this.atendio = atendio;
		this.paterno = paterno;
		this.materno = materno;
		this.nombre = nombre;
		this.razsoc = razsoc;
		this.dni = dni;
		this.telef = telef;
		this.fax = fax;
		this.ruc = ruc;
		this.inod_b = inod_b;
		this.inod_f = inod_f;
		this.inod_cl = inod_cl;
		this.lava_b = lava_b;
		this.lava_f = lava_f;
		this.lava_cl = lava_cl;
		this.duch_b = duch_b;
		this.duch_f = duch_f;
		this.duch_cl = duch_cl;
		this.urin_b = urin_b;
		this.urin_f = urin_f;
		this.urin_cl = urin_cl;
		this.grifo_b = grifo_b;
		this.grifo_f = grifo_f;
		this.grifo_cl = grifo_cl;
		this.ciste_b = ciste_b;
		this.ciste_f = ciste_f;
		this.ciste_cl = ciste_cl;
		this.tanqu_b = tanqu_b;
		this.tanqu_f = tanqu_f;
		this.tanqu_cl = tanqu_cl;
		this.pisc_b = pisc_b;
		this.pisc_f = pisc_f;
		this.pisc_cl = pisc_cl;
		this.bidet_b = bidet_b;
		this.bidet_f = bidet_f;
		this.bidet_cl = bidet_cl;
		this.abast = abast;
		this.ptome_ca = ptome_ca;
		this.num_p = num_p;
		this.dup_p = dup_p;
		this.mza_p = mza_p;
		this.lote_p = lote_p;
		this.observ = observ;
		this.observm1 = observm1;
		this.observm2 = observm2;
		this.cota_sum_c = cota_sum_c;
		this.pisos_c = pisos_c;
		this.cod_ubic_c = cod_ubic_c;
		this.fte_conexion_c = fte_conexion_c;
		this.cup_c = cup_c;
		this.cota_sum = cota_sum;
		this.pisos = pisos;
		this.cod_ubic = cod_ubic;
		this.fte_conexion = fte_conexion;
		this.cup = cup;
		this.devuelto = devuelto;
		this.date_d = date_d;
		this.prior = prior;
		this.actualiza = actualiza;
		this.date_c = date_c;
		this.resuelto = resuelto;
		this.multi = multi;
		this.parcial = parcial;
		this.fte_cnx = fte_cnx;
		this.cota_sumc = cota_sumc;
		this.pisosc = pisosc;
		this.cod_ubicc = cod_ubicc;
		this.fte_cnxc = fte_cnxc;
		this.cupc = cupc;
		this.hora_i = hora_i;
		this.hora_f = hora_f;
		this.valv_e_tlsc = valv_e_tlsc;
		this.mat_valv_e = mat_valv_e;
		this.valv_s_p = valv_s_p;
		this.mat_valv_s = mat_valv_s;
		this.sector = sector;
		this.cup_pred = cup_pred;
		this.aol = aol;
		this.ciclo = ciclo;
		this.nreclamo = nreclamo;
		this.celular_cli = celular_cli;
		this.fax_cli = fax_cli;
		this.niv_predio = niv_predio;
		this.nse_pre = nse_pre;
		this.soc_ocup_cat = soc_ocup_cat;
		this.dom_ocup_cat = dom_ocup_cat;
		this.com_ocup_cat = com_ocup_cat;
		this.ind_ocup_cat = ind_ocup_cat;
		this.est_ocup_cat = est_ocup_cat;
		this.soc_desoc_cat = soc_desoc_cat;
		this.dom_desoc_cat = dom_desoc_cat;
		this.com_desoc_cat = com_desoc_cat;
		this.ind_desoc_cat = ind_desoc_cat;
		this.est_desoc_cat = est_desoc_cat;
		this.est_sum = est_sum;
		this.fte_conex = fte_conex;
		this.ubic_pred = ubic_pred;
		this.dia_conex = dia_conex;
		this.cota_conex = cota_conex;
		this.disp_seg = disp_seg;
		this.tip_lec = tip_lec;
		this.hor_abasc = hor_abasc;
		this.nia = nia;
		this.dia_alcan = dia_alcan;
		this.fte_alcan = fte_alcan;
		this.ubic_alcan = ubic_alcan;
		this.tip_suelo_alca = tip_suelo_alca;
		this.tip_descar_alc = tip_descar_alc;
		this.cotah_alcan = cotah_alcan;
		this.cotav_alcan = cotav_alcan;
		this.long_alcan = long_alcan;
		this.profund_alcan = profund_alcan;
		this.tr_grasas = tr_grasas;
		this.sistrat = sistrat;
		this.mat_tapa_alcan = mat_tapa_alcan;
		this.est_tapa_alcan = est_tapa_alcan;
		this.mat_caja_alcan = mat_caja_alcan;
		this.est_caja_alcan = est_caja_alcan;
		this.mat_tub_alcan = mat_tub_alcan;
		this.est_tub_alcan = est_tub_alcan;
		this.email = email;
		this.cel_cli_c = cel_cli_c;
		this.telf_emp = telf_emp;
		this.piso_dpto_f = piso_dpto_f;
		this.sector_f = sector_f;
		this.fte_finca = fte_finca;
		this.nse_f = nse_f;
		this.obs_f = obs_f;
		this.corr_cup = corr_cup;
		this.pred_no_ubic = pred_no_ubic;
		this.var_act_predio = var_act_predio;
		this.inspec_realiz = inspec_realiz;
		this.mot_no_ing = mot_no_ing;
		this.dia_conex_agua = dia_conex_agua;
		this.imposibilidad = imposibilidad;
		this.seguro_tap = seguro_tap;
		this.est_tapa = est_tapa;
		this.inci_medidor = inci_medidor;
		this.acceso_inmueb = acceso_inmueb;
		this.obs_atendio = obs_atendio;
		this.cartografia = cartografia;
		this.mnto_cartog = mnto_cartog;
		this.corr_cup_f = corr_cup_f;
		this.num_med = num_med;
		this.dia_med = dia_med;
		this.cnx2_con = cnx2_con;
		this.cnx3_con = cnx3_con;
		this.dia_med2 = dia_med2;
		this.dia_med3 = dia_med3;
		this.lec_med2 = lec_med2;
		this.lec_med3 = lec_med3;
		this.cota_hor_des = cota_hor_des;
		this.cota_ver_des = cota_ver_des;
		this.long_cnx_des = long_cnx_des;
		this.prof_cja_des = prof_cja_des;
		this.tip_suelo_des = tip_suelo_des;
		this.ind_tram_des = ind_tram_des;
		this.dia_conex_des = dia_conex_des;
		this.silo = silo;
		this.est_tapa_des = est_tapa_des;
		this.est_caja_des = est_caja_des;
		this.est_tub_des = est_tub_des;
		this.tip_mat_tap_d = tip_mat_tap_d;
		this.tip_mat_caj_d = tip_mat_caj_d;
		this.tip_mat_tub_d = tip_mat_tub_d;
		this.sis_trat_res = sis_trat_res;
		this.tip_des_des = tip_des_des;
		this.clandes_des = clandes_des;
		this.cod_sup = cod_sup;
		this.cod_digit = cod_digit;
	}

	public Integer getCodigoRegistro() {
		return codigoRegistro;
	}
	public void setCodigoRegistro(Integer codigoRegistro) {
		this.codigoRegistro = codigoRegistro;
	}
	public String getCodigoCarga() {
		return codigoCarga;
	}
	public void setCodigoCarga(String codigoCarga) {
		this.codigoCarga = codigoCarga;
	}
	public String getOficinaComercial() {
		return oficinaComercial;
	}
	public void setOficinaComercial(String oficinaComercial) {
		this.oficinaComercial = oficinaComercial;
	}
	public String getGeneroUsuario() {
		return generoUsuario;
	}
	public void setGeneroUsuario(String generoUsuario) {
		this.generoUsuario = generoUsuario;
	}
	public String getCus() {
		return cus;
	}
	public void setCus(String cus) {
		this.cus = cus;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getItin() {
		return itin;
	}
	public void setItin(String itin) {
		this.itin = itin;
	}
	public String getTipsum() {
		return tipsum;
	}
	public void setTipsum(String tipsum) {
		this.tipsum = tipsum;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getNombreRazon() {
		return nombreRazon;
	}
	public void setNombreRazon(String nombreRazon) {
		this.nombreRazon = nombreRazon;
	}
	public String getLeRuc() {
		return leRuc;
	}
	public void setLeRuc(String leRuc) {
		this.leRuc = leRuc;
	}
	public String getTelefonoFax() {
		return telefonoFax;
	}
	public void setTelefonoFax(String telefonoFax) {
		this.telefonoFax = telefonoFax;
	}
	public String getCodAbastecimiento() {
		return codAbastecimiento;
	}
	public void setCodAbastecimiento(String codAbastecimiento) {
		this.codAbastecimiento = codAbastecimiento;
	}
	public String getReferenciaDireccion() {
		return referenciaDireccion;
	}
	public void setReferenciaDireccion(String referenciaDireccion) {
		this.referenciaDireccion = referenciaDireccion;
	}
	public String getCua() {
		return cua;
	}
	public void setCua(String cua) {
		this.cua = cua;
	}
	public String getMedidor() {
		return medidor;
	}
	public void setMedidor(String medidor) {
		this.medidor = medidor;
	}
	public String getDiametroMedidor() {
		return diametroMedidor;
	}
	public void setDiametroMedidor(String diametroMedidor) {
		this.diametroMedidor = diametroMedidor;
	}
	public String getUltLectura() {
		return ultLectura;
	}
	public void setUltLectura(String ultLectura) {
		this.ultLectura = ultLectura;
	}
	public String getPtoMedia() {
		return ptoMedia;
	}
	public void setPtoMedia(String ptoMedia) {
		this.ptoMedia = ptoMedia;
	}
	public String getAcom() {
		return acom;
	}
	public void setAcom(String acom) {
		this.acom = acom;
	}
	public Integer getNisRad() {
		return nisRad;
	}
	public void setNisRad(Integer nisRad) {
		this.nisRad = nisRad;
	}
	public Integer getNumOS() {
		return numOS;
	}
	public void setNumOS(Integer numOS) {
		this.numOS = numOS;
	}
	public String getTipOS() {
		return tipOS;
	}
	public void setTipOS(String tipOS) {
		this.tipOS = tipOS;
	}
	public String getFechaResolucion() {
		return fechaResolucion;
	}
	public void setFechaResolucion(String fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}
	public String getCodEmpleado() {
		return codEmpleado;
	}
	public void setCodEmpleado(String codEmpleado) {
		this.codEmpleado = codEmpleado;
	}
	public String getFechaVisita() {
		return fechaVisita;
	}
	public void setFechaVisita(String fechaVisita) {
		this.fechaVisita = fechaVisita;
	}
	public String getCoAccejec() {
		return coAccejec;
	}
	public void setCoAccejec(String coAccejec) {
		this.coAccejec = coAccejec;
	}
	public String getServ() {
		return serv;
	}
	public void setServ(String serv) {
		this.serv = serv;
	}
	public String getServDT() {
		return servDT;
	}
	public void setServDT(String servDT) {
		this.servDT = servDT;
	}
	public String getCaja() {
		return caja;
	}
	public void setCaja(String caja) {
		this.caja = caja;
	}
	public String getEstadoCaja() {
		return estadoCaja;
	}
	public void setEstadoCaja(String estadoCaja) {
		this.estadoCaja = estadoCaja;
	}
	public String getCnxCon() {
		return cnxCon;
	}
	public void setCnxCon(String cnxCon) {
		this.cnxCon = cnxCon;
	}
	public String getCxCDT() {
		return cxCDT;
	}
	public void setCxCDT(String cxCDT) {
		this.cxCDT = cxCDT;
	}
	public String getLecturaMedidor() {
		return lecturaMedidor;
	}
	public void setLecturaMedidor(String lecturaMedidor) {
		this.lecturaMedidor = lecturaMedidor;
	}
	public String getEstMedidorEncontrado1() {
		return estMedidorEncontrado1;
	}
	public void setEstMedidorEncontrado1(String estMedidorEncontrado1) {
		this.estMedidorEncontrado1 = estMedidorEncontrado1;
	}
	public String getEstMedidorEncontrado2() {
		return estMedidorEncontrado2;
	}
	public void setEstMedidorEncontrado2(String estMedidorEncontrado2) {
		this.estMedidorEncontrado2 = estMedidorEncontrado2;
	}
	public String getEstMedidorEncontrado3() {
		return estMedidorEncontrado3;
	}
	public void setEstMedidorEncontrado3(String estMedidorEncontrado3) {
		this.estMedidorEncontrado3 = estMedidorEncontrado3;
	}
	public String getPrescinto() {
		return prescinto;
	}
	public void setPrescinto(String prescinto) {
		this.prescinto = prescinto;
	}
	public String getFugaCaja() {
		return fugaCaja;
	}
	public void setFugaCaja(String fugaCaja) {
		this.fugaCaja = fugaCaja;
	}
	public String getNroConexiones() {
		return nroConexiones;
	}
	public void setNroConexiones(String nroConexiones) {
		this.nroConexiones = nroConexiones;
	}
	public String getMedidorEncontrado2() {
		return medidorEncontrado2;
	}
	public void setMedidorEncontrado2(String medidorEncontrado2) {
		this.medidorEncontrado2 = medidorEncontrado2;
	}
	public String getMedidorEncontrado3() {
		return medidorEncontrado3;
	}
	public void setMedidorEncontrado3(String medidorEncontrado3) {
		this.medidorEncontrado3 = medidorEncontrado3;
	}
	public String getNiple2() {
		return niple2;
	}
	public void setNiple2(String niple2) {
		this.niple2 = niple2;
	}
	public String getNiple3() {
		return niple3;
	}
	public void setNiple3(String niple3) {
		this.niple3 = niple3;
	}
	public String getValvulas() {
		return valvulas;
	}
	public void setValvulas(String valvulas) {
		this.valvulas = valvulas;
	}
	public String getDisposit() {
		return disposit;
	}
	public void setDisposit(String disposit) {
		this.disposit = disposit;
	}
	public String getClandes() {
		return clandes;
	}
	public void setClandes(String clandes) {
		this.clandes = clandes;
	}
	public String getNiv_finc() {
		return niv_finc;
	}
	public void setNiv_finc(String niv_finc) {
		this.niv_finc = niv_finc;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getN_habit() {
		return n_habit;
	}
	public void setN_habit(String n_habit) {
		this.n_habit = n_habit;
	}
	public String getUu_ocup() {
		return uu_ocup;
	}
	public void setUu_ocup(String uu_ocup) {
		this.uu_ocup = uu_ocup;
	}
	public String getSoc_ocup() {
		return soc_ocup;
	}
	public void setSoc_ocup(String soc_ocup) {
		this.soc_ocup = soc_ocup;
	}
	public String getDom_ocup() {
		return dom_ocup;
	}
	public void setDom_ocup(String dom_ocup) {
		this.dom_ocup = dom_ocup;
	}
	public String getCom_ocup() {
		return com_ocup;
	}
	public void setCom_ocup(String com_ocup) {
		this.com_ocup = com_ocup;
	}
	public String getInd_ocup() {
		return ind_ocup;
	}
	public void setInd_ocup(String ind_ocup) {
		this.ind_ocup = ind_ocup;
	}
	public String getEst_ocup() {
		return est_ocup;
	}
	public void setEst_ocup(String est_ocup) {
		this.est_ocup = est_ocup;
	}
	public String getUu_docup() {
		return uu_docup;
	}
	public void setUu_docup(String uu_docup) {
		this.uu_docup = uu_docup;
	}
	public String getSoc_desc() {
		return soc_desc;
	}
	public void setSoc_desc(String soc_desc) {
		this.soc_desc = soc_desc;
	}
	public String getDom_desc() {
		return dom_desc;
	}
	public void setDom_desc(String dom_desc) {
		this.dom_desc = dom_desc;
	}
	public String getCom_desc() {
		return com_desc;
	}
	public void setCom_desc(String com_desc) {
		this.com_desc = com_desc;
	}
	public String getInd_desc() {
		return ind_desc;
	}
	public void setInd_desc(String ind_desc) {
		this.ind_desc = ind_desc;
	}
	public String getEst_desc() {
		return est_desc;
	}
	public void setEst_desc(String est_desc) {
		this.est_desc = est_desc;
	}
	public String getUso_unic() {
		return uso_unic;
	}
	public void setUso_unic(String uso_unic) {
		this.uso_unic = uso_unic;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDuplicad() {
		return duplicad;
	}
	public void setDuplicad(String duplicad) {
		this.duplicad = duplicad;
	}
	public String getMza() {
		return mza;
	}
	public void setMza(String mza) {
		this.mza = mza;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getCgv() {
		return cgv;
	}
	public void setCgv(String cgv) {
		this.cgv = cgv;
	}
	public String getUrbaniza() {
		return urbaniza;
	}
	public void setUrbaniza(String urbaniza) {
		this.urbaniza = urbaniza;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public String getCalle_c() {
		return calle_c;
	}
	public void setCalle_c(String calle_c) {
		this.calle_c = calle_c;
	}
	public String getNum_c() {
		return num_c;
	}
	public void setNum_c(String num_c) {
		this.num_c = num_c;
	}
	public String getDupli_c() {
		return dupli_c;
	}
	public void setDupli_c(String dupli_c) {
		this.dupli_c = dupli_c;
	}
	public String getMza_c() {
		return mza_c;
	}
	public void setMza_c(String mza_c) {
		this.mza_c = mza_c;
	}
	public String getLote_c() {
		return lote_c;
	}
	public void setLote_c(String lote_c) {
		this.lote_c = lote_c;
	}
	public String getUrb_c() {
		return urb_c;
	}
	public void setUrb_c(String urb_c) {
		this.urb_c = urb_c;
	}
	public String getRef_c() {
		return ref_c;
	}
	public void setRef_c(String ref_c) {
		this.ref_c = ref_c;
	}
	public String getDist_c() {
		return dist_c;
	}
	public void setDist_c(String dist_c) {
		this.dist_c = dist_c;
	}
	public String getAtendio() {
		return atendio;
	}
	public void setAtendio(String atendio) {
		this.atendio = atendio;
	}
	public String getPaterno() {
		return paterno;
	}
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}
	public String getMaterno() {
		return materno;
	}
	public void setMaterno(String materno) {
		this.materno = materno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRazsoc() {
		return razsoc;
	}
	public void setRazsoc(String razsoc) {
		this.razsoc = razsoc;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getTelef() {
		return telef;
	}
	public void setTelef(String telef) {
		this.telef = telef;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getInod_b() {
		return inod_b;
	}
	public void setInod_b(String inod_b) {
		this.inod_b = inod_b;
	}
	public String getInod_f() {
		return inod_f;
	}
	public void setInod_f(String inod_f) {
		this.inod_f = inod_f;
	}
	public String getInod_cl() {
		return inod_cl;
	}
	public void setInod_cl(String inod_cl) {
		this.inod_cl = inod_cl;
	}
	public String getLava_b() {
		return lava_b;
	}
	public void setLava_b(String lava_b) {
		this.lava_b = lava_b;
	}
	public String getLava_f() {
		return lava_f;
	}
	public void setLava_f(String lava_f) {
		this.lava_f = lava_f;
	}
	public String getLava_cl() {
		return lava_cl;
	}
	public void setLava_cl(String lava_cl) {
		this.lava_cl = lava_cl;
	}
	public String getDuch_b() {
		return duch_b;
	}
	public void setDuch_b(String duch_b) {
		this.duch_b = duch_b;
	}
	public String getDuch_f() {
		return duch_f;
	}
	public void setDuch_f(String duch_f) {
		this.duch_f = duch_f;
	}
	public String getDuch_cl() {
		return duch_cl;
	}
	public void setDuch_cl(String duch_cl) {
		this.duch_cl = duch_cl;
	}
	public String getUrin_b() {
		return urin_b;
	}
	public void setUrin_b(String urin_b) {
		this.urin_b = urin_b;
	}
	public String getUrin_f() {
		return urin_f;
	}
	public void setUrin_f(String urin_f) {
		this.urin_f = urin_f;
	}
	public String getUrin_cl() {
		return urin_cl;
	}
	public void setUrin_cl(String urin_cl) {
		this.urin_cl = urin_cl;
	}
	public String getGrifo_b() {
		return grifo_b;
	}
	public void setGrifo_b(String grifo_b) {
		this.grifo_b = grifo_b;
	}
	public String getGrifo_f() {
		return grifo_f;
	}
	public void setGrifo_f(String grifo_f) {
		this.grifo_f = grifo_f;
	}
	public String getGrifo_cl() {
		return grifo_cl;
	}
	public void setGrifo_cl(String grifo_cl) {
		this.grifo_cl = grifo_cl;
	}
	public String getCiste_b() {
		return ciste_b;
	}
	public void setCiste_b(String ciste_b) {
		this.ciste_b = ciste_b;
	}
	public String getCiste_f() {
		return ciste_f;
	}
	public void setCiste_f(String ciste_f) {
		this.ciste_f = ciste_f;
	}
	public String getCiste_cl() {
		return ciste_cl;
	}
	public void setCiste_cl(String ciste_cl) {
		this.ciste_cl = ciste_cl;
	}
	public String getTanqu_b() {
		return tanqu_b;
	}
	public void setTanqu_b(String tanqu_b) {
		this.tanqu_b = tanqu_b;
	}
	public String getTanqu_f() {
		return tanqu_f;
	}
	public void setTanqu_f(String tanqu_f) {
		this.tanqu_f = tanqu_f;
	}
	public String getTanqu_cl() {
		return tanqu_cl;
	}
	public void setTanqu_cl(String tanqu_cl) {
		this.tanqu_cl = tanqu_cl;
	}
	public String getPisc_b() {
		return pisc_b;
	}
	public void setPisc_b(String pisc_b) {
		this.pisc_b = pisc_b;
	}
	public String getPisc_f() {
		return pisc_f;
	}
	public void setPisc_f(String pisc_f) {
		this.pisc_f = pisc_f;
	}
	public String getPisc_cl() {
		return pisc_cl;
	}
	public void setPisc_cl(String pisc_cl) {
		this.pisc_cl = pisc_cl;
	}
	public String getBidet_b() {
		return bidet_b;
	}
	public void setBidet_b(String bidet_b) {
		this.bidet_b = bidet_b;
	}
	public String getBidet_f() {
		return bidet_f;
	}
	public void setBidet_f(String bidet_f) {
		this.bidet_f = bidet_f;
	}
	public String getBidet_cl() {
		return bidet_cl;
	}
	public void setBidet_cl(String bidet_cl) {
		this.bidet_cl = bidet_cl;
	}
	public String getAbast() {
		return abast;
	}
	public void setAbast(String abast) {
		this.abast = abast;
	}
	public String getPtome_ca() {
		return ptome_ca;
	}
	public void setPtome_ca(String ptome_ca) {
		this.ptome_ca = ptome_ca;
	}
	public String getNum_p() {
		return num_p;
	}
	public void setNum_p(String num_p) {
		this.num_p = num_p;
	}
	public String getDup_p() {
		return dup_p;
	}
	public void setDup_p(String dup_p) {
		this.dup_p = dup_p;
	}
	public String getMza_p() {
		return mza_p;
	}
	public void setMza_p(String mza_p) {
		this.mza_p = mza_p;
	}
	public String getLote_p() {
		return lote_p;
	}
	public void setLote_p(String lote_p) {
		this.lote_p = lote_p;
	}
	public String getObserv() {
		return observ;
	}
	public void setObserv(String observ) {
		this.observ = observ;
	}
	public String getObservm1() {
		return observm1;
	}
	public void setObservm1(String observm1) {
		this.observm1 = observm1;
	}
	public String getObservm2() {
		return observm2;
	}
	public void setObservm2(String observm2) {
		this.observm2 = observm2;
	}
	public String getCota_sum_c() {
		return cota_sum_c;
	}
	public void setCota_sum_c(String cota_sum_c) {
		this.cota_sum_c = cota_sum_c;
	}
	public String getPisos_c() {
		return pisos_c;
	}
	public void setPisos_c(String pisos_c) {
		this.pisos_c = pisos_c;
	}
	public String getCod_ubic_c() {
		return cod_ubic_c;
	}
	public void setCod_ubic_c(String cod_ubic_c) {
		this.cod_ubic_c = cod_ubic_c;
	}
	public String getFte_conexion_c() {
		return fte_conexion_c;
	}
	public void setFte_conexion_c(String fte_conexion_c) {
		this.fte_conexion_c = fte_conexion_c;
	}
	public String getCup_c() {
		return cup_c;
	}
	public void setCup_c(String cup_c) {
		this.cup_c = cup_c;
	}
	public String getCota_sum() {
		return cota_sum;
	}
	public void setCota_sum(String cota_sum) {
		this.cota_sum = cota_sum;
	}
	public String getPisos() {
		return pisos;
	}
	public void setPisos(String pisos) {
		this.pisos = pisos;
	}
	public String getCod_ubic() {
		return cod_ubic;
	}
	public void setCod_ubic(String cod_ubic) {
		this.cod_ubic = cod_ubic;
	}
	public String getFte_conexion() {
		return fte_conexion;
	}
	public void setFte_conexion(String fte_conexion) {
		this.fte_conexion = fte_conexion;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getDevuelto() {
		return devuelto;
	}
	public void setDevuelto(String devuelto) {
		this.devuelto = devuelto;
	}
	public String getDate_d() {
		return date_d;
	}
	public void setDate_d(String date_d) {
		this.date_d = date_d;
	}
	public String getPrior() {
		return prior;
	}
	public void setPrior(String prior) {
		this.prior = prior;
	}
	public String getActualiza() {
		return actualiza;
	}
	public void setActualiza(String actualiza) {
		this.actualiza = actualiza;
	}
	public Date getDate_c() {
		return date_c;
	}
	public void setDate_c(Date date_c) {
		this.date_c = date_c;
	}
	public String getResuelto() {
		return resuelto;
	}
	public void setResuelto(String resuelto) {
		this.resuelto = resuelto;
	}
	public String getMulti() {
		return multi;
	}
	public void setMulti(String multi) {
		this.multi = multi;
	}
	public String getParcial() {
		return parcial;
	}
	public void setParcial(String parcial) {
		this.parcial = parcial;
	}
	public String getFte_cnx() {
		return fte_cnx;
	}
	public void setFte_cnx(String fte_cnx) {
		this.fte_cnx = fte_cnx;
	}
	public String getCota_sumc() {
		return cota_sumc;
	}
	public void setCota_sumc(String cota_sumc) {
		this.cota_sumc = cota_sumc;
	}
	public String getPisosc() {
		return pisosc;
	}
	public void setPisosc(String pisosc) {
		this.pisosc = pisosc;
	}
	public String getCod_ubicc() {
		return cod_ubicc;
	}
	public void setCod_ubicc(String cod_ubicc) {
		this.cod_ubicc = cod_ubicc;
	}
	public String getFte_cnxc() {
		return fte_cnxc;
	}
	public void setFte_cnxc(String fte_cnxc) {
		this.fte_cnxc = fte_cnxc;
	}
	public String getCupc() {
		return cupc;
	}
	public void setCupc(String cupc) {
		this.cupc = cupc;
	}
	public String getHora_i() {
		return hora_i;
	}
	public void setHora_i(String hora_i) {
		this.hora_i = hora_i;
	}
	public String getHora_f() {
		return hora_f;
	}
	public void setHora_f(String hora_f) {
		this.hora_f = hora_f;
	}
	public String getValv_e_tlsc() {
		return valv_e_tlsc;
	}
	public void setValv_e_tlsc(String valv_e_tlsc) {
		this.valv_e_tlsc = valv_e_tlsc;
	}
	public String getMat_valv_e() {
		return mat_valv_e;
	}
	public void setMat_valv_e(String mat_valv_e) {
		this.mat_valv_e = mat_valv_e;
	}
	public String getValv_s_p() {
		return valv_s_p;
	}
	public void setValv_s_p(String valv_s_p) {
		this.valv_s_p = valv_s_p;
	}
	public String getMat_valv_s() {
		return mat_valv_s;
	}
	public void setMat_valv_s(String mat_valv_s) {
		this.mat_valv_s = mat_valv_s;
	}
	public Integer getSector() {
		return sector;
	}
	public void setSector(Integer sector) {
		this.sector = sector;
	}
	public String getCup_pred() {
		return cup_pred;
	}
	public void setCup_pred(String cup_pred) {
		this.cup_pred = cup_pred;
	}
	public Integer getAol() {
		return aol;
	}
	public void setAol(Integer aol) {
		this.aol = aol;
	}
	public String getCiclo() {
		return ciclo;
	}
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	public String getNreclamo() {
		return nreclamo;
	}
	public void setNreclamo(String nreclamo) {
		this.nreclamo = nreclamo;
	}
	public Double getCelular_cli() {
		return celular_cli;
	}
	public void setCelular_cli(Double celular_cli) {
		this.celular_cli = celular_cli;
	}
	public Integer getFax_cli() {
		return fax_cli;
	}
	public void setFax_cli(Integer fax_cli) {
		this.fax_cli = fax_cli;
	}
	public Integer getNiv_predio() {
		return niv_predio;
	}
	public void setNiv_predio(Integer niv_predio) {
		this.niv_predio = niv_predio;
	}
	public String getNse_pre() {
		return nse_pre;
	}
	public void setNse_pre(String nse_pre) {
		this.nse_pre = nse_pre;
	}
	public Integer getSoc_ocup_cat() {
		return soc_ocup_cat;
	}
	public void setSoc_ocup_cat(Integer soc_ocup_cat) {
		this.soc_ocup_cat = soc_ocup_cat;
	}
	public Integer getDom_ocup_cat() {
		return dom_ocup_cat;
	}
	public void setDom_ocup_cat(Integer dom_ocup_cat) {
		this.dom_ocup_cat = dom_ocup_cat;
	}
	public Integer getCom_ocup_cat() {
		return com_ocup_cat;
	}
	public void setCom_ocup_cat(Integer com_ocup_cat) {
		this.com_ocup_cat = com_ocup_cat;
	}
	public Integer getInd_ocup_cat() {
		return ind_ocup_cat;
	}
	public void setInd_ocup_cat(Integer ind_ocup_cat) {
		this.ind_ocup_cat = ind_ocup_cat;
	}
	public Integer getEst_ocup_cat() {
		return est_ocup_cat;
	}
	public void setEst_ocup_cat(Integer est_ocup_cat) {
		this.est_ocup_cat = est_ocup_cat;
	}
	public Integer getSoc_desoc_cat() {
		return soc_desoc_cat;
	}
	public void setSoc_desoc_cat(Integer soc_desoc_cat) {
		this.soc_desoc_cat = soc_desoc_cat;
	}
	public Integer getDom_desoc_cat() {
		return dom_desoc_cat;
	}
	public void setDom_desoc_cat(Integer dom_desoc_cat) {
		this.dom_desoc_cat = dom_desoc_cat;
	}
	public Integer getCom_desoc_cat() {
		return com_desoc_cat;
	}
	public void setCom_desoc_cat(Integer com_desoc_cat) {
		this.com_desoc_cat = com_desoc_cat;
	}
	public Integer getInd_desoc_cat() {
		return ind_desoc_cat;
	}
	public void setInd_desoc_cat(Integer ind_desoc_cat) {
		this.ind_desoc_cat = ind_desoc_cat;
	}
	public Integer getEst_desoc_cat() {
		return est_desoc_cat;
	}
	public void setEst_desoc_cat(Integer est_desoc_cat) {
		this.est_desoc_cat = est_desoc_cat;
	}
	public String getEst_sum() {
		return est_sum;
	}
	public void setEst_sum(String est_sum) {
		this.est_sum = est_sum;
	}
	public String getFte_conex() {
		return fte_conex;
	}
	public void setFte_conex(String fte_conex) {
		this.fte_conex = fte_conex;
	}
	public String getUbic_pred() {
		return ubic_pred;
	}
	public void setUbic_pred(String ubic_pred) {
		this.ubic_pred = ubic_pred;
	}
	public Double getDia_conex() {
		return dia_conex;
	}
	public void setDia_conex(Double dia_conex) {
		this.dia_conex = dia_conex;
	}
	public Double getCota_conex() {
		return cota_conex;
	}
	public void setCota_conex(Double cota_conex) {
		this.cota_conex = cota_conex;
	}
	public String getDisp_seg() {
		return disp_seg;
	}
	public void setDisp_seg(String disp_seg) {
		this.disp_seg = disp_seg;
	}
	public String getTip_lec() {
		return tip_lec;
	}
	public void setTip_lec(String tip_lec) {
		this.tip_lec = tip_lec;
	}
	public String getHor_abasc() {
		return hor_abasc;
	}
	public void setHor_abasc(String hor_abasc) {
		this.hor_abasc = hor_abasc;
	}
	public Integer getNia() {
		return nia;
	}
	public void setNia(Integer nia) {
		this.nia = nia;
	}
	public String getDia_alcan() {
		return dia_alcan;
	}
	public void setDia_alcan(String dia_alcan) {
		this.dia_alcan = dia_alcan;
	}
	public String getFte_alcan() {
		return fte_alcan;
	}
	public void setFte_alcan(String fte_alcan) {
		this.fte_alcan = fte_alcan;
	}
	public String getUbic_alcan() {
		return ubic_alcan;
	}
	public void setUbic_alcan(String ubic_alcan) {
		this.ubic_alcan = ubic_alcan;
	}
	public String getTip_suelo_alca() {
		return tip_suelo_alca;
	}
	public void setTip_suelo_alca(String tip_suelo_alca) {
		this.tip_suelo_alca = tip_suelo_alca;
	}
	public String getTip_descar_alc() {
		return tip_descar_alc;
	}
	public void setTip_descar_alc(String tip_descar_alc) {
		this.tip_descar_alc = tip_descar_alc;
	}
	public Double getCotah_alcan() {
		return cotah_alcan;
	}
	public void setCotah_alcan(Double cotah_alcan) {
		this.cotah_alcan = cotah_alcan;
	}
	public Double getCotav_alcan() {
		return cotav_alcan;
	}
	public void setCotav_alcan(Double cotav_alcan) {
		this.cotav_alcan = cotav_alcan;
	}
	public Double getLong_alcan() {
		return long_alcan;
	}
	public void setLong_alcan(Double long_alcan) {
		this.long_alcan = long_alcan;
	}
	public Double getProfund_alcan() {
		return profund_alcan;
	}
	public void setProfund_alcan(Double profund_alcan) {
		this.profund_alcan = profund_alcan;
	}
	public Integer getTr_grasas() {
		return tr_grasas;
	}
	public void setTr_grasas(Integer tr_grasas) {
		this.tr_grasas = tr_grasas;
	}
	public String getSistrat() {
		return sistrat;
	}
	public void setSistrat(String sistrat) {
		this.sistrat = sistrat;
	}
	public String getMat_tapa_alcan() {
		return mat_tapa_alcan;
	}
	public void setMat_tapa_alcan(String mat_tapa_alcan) {
		this.mat_tapa_alcan = mat_tapa_alcan;
	}
	public String getEst_tapa_alcan() {
		return est_tapa_alcan;
	}
	public void setEst_tapa_alcan(String est_tapa_alcan) {
		this.est_tapa_alcan = est_tapa_alcan;
	}
	public String getMat_caja_alcan() {
		return mat_caja_alcan;
	}
	public void setMat_caja_alcan(String mat_caja_alcan) {
		this.mat_caja_alcan = mat_caja_alcan;
	}
	public String getEst_caja_alcan() {
		return est_caja_alcan;
	}
	public void setEst_caja_alcan(String est_caja_alcan) {
		this.est_caja_alcan = est_caja_alcan;
	}
	public String getMat_tub_alcan() {
		return mat_tub_alcan;
	}
	public void setMat_tub_alcan(String mat_tub_alcan) {
		this.mat_tub_alcan = mat_tub_alcan;
	}
	public String getEst_tub_alcan() {
		return est_tub_alcan;
	}
	public void setEst_tub_alcan(String est_tub_alcan) {
		this.est_tub_alcan = est_tub_alcan;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Double getCel_cli_c() {
		return cel_cli_c;
	}
	public void setCel_cli_c(Double cel_cli_c) {
		this.cel_cli_c = cel_cli_c;
	}
	public Integer getTelf_emp() {
		return telf_emp;
	}
	public void setTelf_emp(Integer telf_emp) {
		this.telf_emp = telf_emp;
	}
	public String getPiso_dpto_f() {
		return piso_dpto_f;
	}
	public void setPiso_dpto_f(String piso_dpto_f) {
		this.piso_dpto_f = piso_dpto_f;
	}
	public Integer getSector_f() {
		return sector_f;
	}
	public void setSector_f(Integer sector_f) {
		this.sector_f = sector_f;
	}
	public String getFte_finca() {
		return fte_finca;
	}
	public void setFte_finca(String fte_finca) {
		this.fte_finca = fte_finca;
	}
	public String getNse_f() {
		return nse_f;
	}
	public void setNse_f(String nse_f) {
		this.nse_f = nse_f;
	}
	public String getObs_f() {
		return obs_f;
	}
	public void setObs_f(String obs_f) {
		this.obs_f = obs_f;
	}
	public String getCorr_cup() {
		return corr_cup;
	}
	public void setCorr_cup(String corr_cup) {
		this.corr_cup = corr_cup;
	}
	public String getPred_no_ubic() {
		return pred_no_ubic;
	}
	public void setPred_no_ubic(String pred_no_ubic) {
		this.pred_no_ubic = pred_no_ubic;
	}
	public String getVar_act_predio() {
		return var_act_predio;
	}
	public void setVar_act_predio(String var_act_predio) {
		this.var_act_predio = var_act_predio;
	}
	public String getInspec_realiz() {
		return inspec_realiz;
	}
	public void setInspec_realiz(String inspec_realiz) {
		this.inspec_realiz = inspec_realiz;
	}
	public String getMot_no_ing() {
		return mot_no_ing;
	}
	public void setMot_no_ing(String mot_no_ing) {
		this.mot_no_ing = mot_no_ing;
	}
	public Double getDia_conex_agua() {
		return dia_conex_agua;
	}
	public void setDia_conex_agua(Double dia_conex_agua) {
		this.dia_conex_agua = dia_conex_agua;
	}
	public String getImposibilidad() {
		return imposibilidad;
	}
	public void setImposibilidad(String imposibilidad) {
		this.imposibilidad = imposibilidad;
	}
	public String getSeguro_tap() {
		return seguro_tap;
	}
	public void setSeguro_tap(String seguro_tap) {
		this.seguro_tap = seguro_tap;
	}
	public String getEst_tapa() {
		return est_tapa;
	}
	public void setEst_tapa(String est_tapa) {
		this.est_tapa = est_tapa;
	}
	public String getInci_medidor() {
		return inci_medidor;
	}
	public void setInci_medidor(String inci_medidor) {
		this.inci_medidor = inci_medidor;
	}
	public String getAcceso_inmueb() {
		return acceso_inmueb;
	}
	public void setAcceso_inmueb(String acceso_inmueb) {
		this.acceso_inmueb = acceso_inmueb;
	}
	public String getObs_atendio() {
		return obs_atendio;
	}
	public void setObs_atendio(String obs_atendio) {
		this.obs_atendio = obs_atendio;
	}
	public String getCartografia() {
		return cartografia;
	}
	public void setCartografia(String cartografia) {
		this.cartografia = cartografia;
	}
	public String getMnto_cartog() {
		return mnto_cartog;
	}
	public void setMnto_cartog(String mnto_cartog) {
		this.mnto_cartog = mnto_cartog;
	}
	public String getCorr_cup_f() {
		return corr_cup_f;
	}
	public void setCorr_cup_f(String corr_cup_f) {
		this.corr_cup_f = corr_cup_f;
	}
	public String getNum_med() {
		return num_med;
	}
	public void setNum_med(String num_med) {
		this.num_med = num_med;
	}
	public String getDia_med() {
		return dia_med;
	}
	public void setDia_med(String dia_med) {
		this.dia_med = dia_med;
	}
	public String getCnx2_con() {
		return cnx2_con;
	}
	public void setCnx2_con(String cnx2_con) {
		this.cnx2_con = cnx2_con;
	}
	public String getCnx3_con() {
		return cnx3_con;
	}
	public void setCnx3_con(String cnx3_con) {
		this.cnx3_con = cnx3_con;
	}
	public String getDia_med2() {
		return dia_med2;
	}
	public void setDia_med2(String dia_med2) {
		this.dia_med2 = dia_med2;
	}
	public String getDia_med3() {
		return dia_med3;
	}
	public void setDia_med3(String dia_med3) {
		this.dia_med3 = dia_med3;
	}
	public Integer getLec_med2() {
		return lec_med2;
	}
	public void setLec_med2(Integer lec_med2) {
		this.lec_med2 = lec_med2;
	}
	public Integer getLec_med3() {
		return lec_med3;
	}
	public void setLec_med3(Integer lec_med3) {
		this.lec_med3 = lec_med3;
	}
	public Double getCota_hor_des() {
		return cota_hor_des;
	}
	public void setCota_hor_des(Double cota_hor_des) {
		this.cota_hor_des = cota_hor_des;
	}
	public Double getCota_ver_des() {
		return cota_ver_des;
	}
	public void setCota_ver_des(Double cota_ver_des) {
		this.cota_ver_des = cota_ver_des;
	}
	public Double getLong_cnx_des() {
		return long_cnx_des;
	}
	public void setLong_cnx_des(Double long_cnx_des) {
		this.long_cnx_des = long_cnx_des;
	}
	public Double getProf_cja_des() {
		return prof_cja_des;
	}
	public void setProf_cja_des(Double prof_cja_des) {
		this.prof_cja_des = prof_cja_des;
	}
	public String getTip_suelo_des() {
		return tip_suelo_des;
	}
	public void setTip_suelo_des(String tip_suelo_des) {
		this.tip_suelo_des = tip_suelo_des;
	}
	public String getInd_tram_des() {
		return ind_tram_des;
	}
	public void setInd_tram_des(String ind_tram_des) {
		this.ind_tram_des = ind_tram_des;
	}
	public String getDia_conex_des() {
		return dia_conex_des;
	}
	public void setDia_conex_des(String dia_conex_des) {
		this.dia_conex_des = dia_conex_des;
	}
	public String getSilo() {
		return silo;
	}
	public void setSilo(String silo) {
		this.silo = silo;
	}
	public String getEst_tapa_des() {
		return est_tapa_des;
	}
	public void setEst_tapa_des(String est_tapa_des) {
		this.est_tapa_des = est_tapa_des;
	}
	public String getEst_caja_des() {
		return est_caja_des;
	}
	public void setEst_caja_des(String est_caja_des) {
		this.est_caja_des = est_caja_des;
	}
	public String getEst_tub_des() {
		return est_tub_des;
	}
	public void setEst_tub_des(String est_tub_des) {
		this.est_tub_des = est_tub_des;
	}
	public String getTip_mat_tap_d() {
		return tip_mat_tap_d;
	}
	public void setTip_mat_tap_d(String tip_mat_tap_d) {
		this.tip_mat_tap_d = tip_mat_tap_d;
	}
	public String getTip_mat_caj_d() {
		return tip_mat_caj_d;
	}
	public void setTip_mat_caj_d(String tip_mat_caj_d) {
		this.tip_mat_caj_d = tip_mat_caj_d;
	}
	public String getTip_mat_tub_d() {
		return tip_mat_tub_d;
	}
	public void setTip_mat_tub_d(String tip_mat_tub_d) {
		this.tip_mat_tub_d = tip_mat_tub_d;
	}
	public String getSis_trat_res() {
		return sis_trat_res;
	}
	public void setSis_trat_res(String sis_trat_res) {
		this.sis_trat_res = sis_trat_res;
	}
	public String getTip_des_des() {
		return tip_des_des;
	}
	public void setTip_des_des(String tip_des_des) {
		this.tip_des_des = tip_des_des;
	}
	public String getClandes_des() {
		return clandes_des;
	}
	public void setClandes_des(String clandes_des) {
		this.clandes_des = clandes_des;
	}
	public Integer getCod_sup() {
		return cod_sup;
	}
	public void setCod_sup(Integer cod_sup) {
		this.cod_sup = cod_sup;
	}
	public Integer getCod_digit() {
		return cod_digit;
	}
	public void setCod_digit(Integer cod_digit) {
		this.cod_digit = cod_digit;
	}

}

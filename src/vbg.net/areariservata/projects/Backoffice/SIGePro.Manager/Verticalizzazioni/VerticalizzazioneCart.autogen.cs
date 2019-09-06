
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione CART il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Abilita la comunicazione con il sistema di cooperazione applicativa regione toscana
				/// </summary>
				public partial class VerticalizzazioneCart : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "CART";

                    public VerticalizzazioneCart()
                    {

                    }

					public VerticalizzazioneCart(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Codice dell’amministrazione da inserire nel caso di inventario procedimenti di tipo 2.
					/// </summary>
					public string Endo2Inventarioproc_ammin
					{
						get{ return GetString("ENDO2_INVENTARIOPROC.AMMIN");}
						set{ SetString("ENDO2_INVENTARIOPROC.AMMIN" , value); }
					}
					
					/// <summary>
					/// Valori ammessi sono S o N. Se il valore è S allora verrà creato un endo-procedimento per ogni procedimento amministrativo (di TIPO2) presente nel dizionario di regione Toscana (alberoproc). Gli endo-procedimenti vengono inseriti nella tabella INVENTARIPROCEDIMENTI e vengono collegati alla rispettiva voce dell'albero.
					/// </summary>
					public string Endo2Inventarioprocedimenti
					{
						get{ return GetString("ENDO2_INVENTARIOPROCEDIMENTI");}
						set{ SetString("ENDO2_INVENTARIOPROCEDIMENTI" , value); }
					}
					
					/// <summary>
					/// Codice della tipologia di endoprocedimento da usare per la creazione degli endo-procedimenti di tipo amministrativo generati in base alle foglie dell'albero degli interventi (scarico del dizionario CART endo di tipo 2)
					/// </summary>
					public string Endo2Tipifamiglieendo_codice
					{
						get{ return GetString("ENDO2_TIPIFAMIGLIEENDO.CODICE");}
						set{ SetString("ENDO2_TIPIFAMIGLIEENDO.CODICE" , value); }
					}
					
					/// <summary>
					/// Codice dell’azione che va inserito nel campo fkazid della tabella ALBEROPROC_ENDO.
					/// </summary>
					public string EndoAlberoprocEndo_fkazid
					{
						get{ return GetString("ENDO_ALBEROPROC_ENDO.FKAZID");}
						set{ SetString("ENDO_ALBEROPROC_ENDO.FKAZID" , value); }
					}
					
					/// <summary>
					/// Codice natura da inserire nel campo CODICENATURA per tutti gli inventariprocedimento (sia di tipo 1 che di tipo 2 )
					/// </summary>
					public string EndoInventarioproc_codnatura
					{
						get{ return GetString("ENDO_INVENTARIOPROC.CODNATURA");}
						set{ SetString("ENDO_INVENTARIOPROC.CODNATURA" , value); }
					}
					
					/// <summary>
					/// Codice del tipo di movimento da inserire nell’inventarioprocedimento (sia di tipo 1 che di tipo 2 ).  Il parametro è non obbligatorio.
					/// </summary>
					public string EndoInventarioproc_tipomov
					{
						get{ return GetString("ENDO_INVENTARIOPROC.TIPOMOV");}
						set{ SetString("ENDO_INVENTARIOPROC.TIPOMOV" , value); }
					}
					
					/// <summary>
					/// Valori ammessi sono S o N. Se il valore è S allora sarà attivata la funzione 'Genera allegati per notifica CART' nella pagina del movimento.
					/// </summary>
					public string GeneraAllegatiNotifica
					{
						get{ return GetString("GENERA_ALLEGATI_NOTIFICA");}
						set{ SetString("GENERA_ALLEGATI_NOTIFICA" , value); }
					}
					
					/// <summary>
					/// E' utilizzato nell'elaborazione dei messaggi di presentazione domanda ricevuti dal front-office per specificare l'id del tipo di procedura che dovrà essere assegnato alle nuove istanze che vengono crerate.
					/// </summary>
					public string NuovaDomanda_idTipoprocedura
					{
						get{ return GetString("NUOVA_DOMANDA.ID_TIPOPROCEDURA");}
						set{ SetString("NUOVA_DOMANDA.ID_TIPOPROCEDURA" , value); }
					}
					
					/// <summary>
					/// Nella elaborazione dei messaggi di invio dizionario rappresenta l'identificativo della tabella alberoproc considerata come foglia radice dalla quale far partire le ramificazioni del Dizionario di regione toscana (es: 144)
					/// </summary>
					public string RootAlberoprocId
					{
						get{ return GetString("ROOT_ALBEROPROC_ID");}
						set{ SetString("ROOT_ALBEROPROC_ID" , value); }
					}
					
					/// <summary>
					/// E' l'identificativo del SUAP che andrà a popolare i campi SUAP della localizzazione delle schede di spiegazione
					/// </summary>
					public string SuapId
					{
						get{ return GetString("SUAP_ID");}
						set{ SetString("SUAP_ID" , value); }
					}
					
					/// <summary>
					/// Rappresenta l'id della tempificazione di default da associare agli endo procedimenti creati dal dizionario
					/// </summary>
					public string Tempificazione
					{
						get{ return GetString("TEMPIFICAZIONE");}
						set{ SetString("TEMPIFICAZIONE" , value); }
					}
					
					/// <summary>
					/// Indicare con un valore intero il timeout per la chiamata ai WS di CART.INTEGRATIONMANAGER. Con il valore viene impostata la chiamata a port.setTimeout(valore). Se non impostato prende il valore di timeout predefinito nella libreria CART (120.000 ms)
					/// </summary>
					public string WscallTimeout
					{
						get{ return GetString("WSCALL_TIMEOUT");}
						set{ SetString("WSCALL_TIMEOUT" , value); }
					}
					
					
				}
			}
			
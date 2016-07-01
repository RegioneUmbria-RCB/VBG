
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_FOLIUM il 26/08/2014 17.28.00
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivato, consente di eseguire la protocollazione con il protocollo FOLIUM, questo protocollo è gestito dalla ditta DedaGroup ed è presente al Comune di Carmignano, non è stato fornito alcun ambiente di test, ma, modificando il registro (parametro registro) si può accedere ad un eventuale ambiente di test creato dal cliente.
				/// </summary>
				public partial class VerticalizzazioneProtocolloFolium : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_FOLIUM";

                    public VerticalizzazioneProtocolloFolium()
                    {

                    }

					public VerticalizzazioneProtocolloFolium(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// (Obbligatorio) Codice Aoo, questo parametro fa parte dell'autenticazione che viene invocata ad ogni metodo del web service del protocollo FOLIUM. Ogni metodo del web service infatti richiede come parametro fisso anche la classe WSAuthentication nella quale deve essere specificata la proprietà aoo che corrisponderà a questo parametro.
					/// </summary>
					public string Aoo
					{
						get{ return GetString("AOO");}
						set{ SetString("AOO" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Applicazione chiamante al web service, questo parametro fa parte dell'autenticazione che viene invocata ad ogni metodo del web service del protocollo FOLIUM. Ogni metodo del web service infatti richiede come parametro fisso anche la classe WSAuthentication nella quale deve essere specificata la proprietà applicazione che corrisponderà a questo parametro.
					/// </summary>
					public string Applicazione
					{
						get{ return GetString("APPLICAZIONE");}
						set{ SetString("APPLICAZIONE" , value); }
					}
					
					/// <summary>
					/// Indicare in questo parametro il nome del binding che il componente client deve usare per creare la connessione al web service, se non non valorizzato verrà usato quello di default (defaultHttpBinding)
					/// </summary>
					public string Binding
					{
						get{ return GetString("BINDING");}
						set{ SetString("BINDING" , value); }
					}
					
					/// <summary>
					/// Indica il codice per conoscenza da utilizzare in fase di protocollazione. Sulla maschera di protocollazione è presente un campo a tendina denominato -Trasmesso per- legato all'anagrafica (Mittente / Destinatario), questo campo viene popolato dai valori presenti nella tabella PROTOCOLLO_MODALITAINVIO, indicando il codice in questo campo, quando l'operatore selezionerà nella tendina -Trasmesso per- il valore con lo stesso codice, l'anagrafica selezionata sarà considerata per conoscenza. Se non valorizzato il destinatario (mittente) del protocollo non sarà mai per conoscenza.
					/// </summary>
					public string CodiceCc
					{
						get{ return GetString("CODICE_CC");}
						set{ SetString("CODICE_CC" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Codice Ente di autenticazione ad ogni metodo del web service del protocollo FOLIUM. Ogni metodo del web service infatti richiede come parametro fisso anche la classe WSAuthentication nella quale deve essere specificata la proprietà codiceente che corrisponderà a questo parametro.
					/// </summary>
					public string CodiceEnte
					{
						get{ return GetString("CODICE_ENTE");}
						set{ SetString("CODICE_ENTE" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Password di autenticazione ad ogni metodo del web service del protocollo FOLIUM. Ogni metodo del web service infatti richiede come parametro fisso anche la classe WSAuthentication nella quale deve essere specificata la proprietà password che corrisponderà a questo parametro.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// Inserire il valore del registro da utilizzare in fase di inserimento protocollo e in fase di lettura protocollo, il valore inserito può discriminare un ambiente di test da quello di produzione, se non specificato automaticamente sarà utilizzato l'ambiente di produzione.
					/// </summary>
					public string Registro
					{
						get{ return GetString("REGISTRO");}
						set{ SetString("REGISTRO" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Indicare qui l'indirizzo del web service di protocollazione.
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// Indica se per il recupero delle classifiche, sull'albero degli interventi, debba essere usato l'apposito metodo messo a disposizione dal web service (valore = 1) oppure no (valore 0, vuoto o non presente).
					/// </summary>
					public string UsaWsclassifiche
					{
						get{ return GetString("USA_WSCLASSIFICHE");}
						set{ SetString("USA_WSCLASSIFICHE" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Username di autenticazione ad ogni metodo del web service del protocollo FOLIUM. Ogni metodo del web service infatti richiede come parametro fisso anche la classe WSAuthentication nella quale deve essere specificata la proprietà username che corrisponderà a questo parametro.
					/// </summary>
					public string Username
					{
						get{ return GetString("USERNAME");}
						set{ SetString("USERNAME" , value); }
					}
					
					
				}
			}
			
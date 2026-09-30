
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_SICRAWEB il 26/08/2014 17.28.00
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitato permette usare le funzionalità del protocollo SICRAWEB. Questo protocollo è stato sviluppato da Maggioli ma viene fornito, oltre che da Maggioli stesso, anche da Studio K, il quale ha rilasciato proprio una versione di test del web service raggiungibile all'indirizzo http://www.studiok.it:9091/ws_sici/ProWSApi.asmx?wsdl che è pubblico
				/// </summary>
				public partial class VerticalizzazioneProtocolloSicraweb : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_SICRAWEB";

                    public VerticalizzazioneProtocolloSicraweb()
                    {

                    }

					public VerticalizzazioneProtocolloSicraweb(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// Indicare in questo parametro il valore del codice dell'amministrazione dell'ente a cui si intende protocollare, questo tipo di dato è obbligatorio e deve essere fornito dall'amministratore del sistema di protocollo, non ha infatti niente a che vedere con le amministrazioni di del Backoffice.
					/// </summary>
					public string Codiceamministrazione
					{
						get{ return GetString("CODICEAMMINISTRAZIONE");}
						set{ SetString("CODICEAMMINISTRAZIONE" , value); }
					}
					
					/// <summary>
					/// Codice IPA dell’AOO mittente del protocollo in uscita, ovvero dell’ente stesso.
					/// </summary>
					public string Codiceaoo
					{
						get{ return GetString("CODICEAOO");}
						set{ SetString("CODICEAOO" , value); }
					}
					
					/// <summary>
					/// E' un parametro che deve essere fornito dagli amministratori del protocollo in collaborazione con il fornitore, riguarda l'identificativo delle credenziali di connessione.
					/// </summary>
					public string Connectionstring
					{
						get{ return GetString("CONNECTIONSTRING");}
						set{ SetString("CONNECTIONSTRING" , value); }
					}
					
					/// <summary>
					/// Se valorizzato a 1 consente, in fase di protocollazione istanza o movimento da backoffice, di allegare il file di profilazione (segnatura.xml) che sarebbe il file che viene inviato al web service per eseguire la protocollazione. Questa funzionalità viene usata nel momento in cui non sia presente nessun allegato da inviare al protocollo e solo se questo parametro è valorizzato a 1.
					/// </summary>
					public string InviaSegnatura
					{
						get{ return GetString("INVIA_SEGNATURA");}
						set{ SetString("INVIA_SEGNATURA" , value); }
					}
					
					/// <summary>
					/// Inserire qui l'url dove raggiungere il web service sicraweb, questo web service espone le funzionalità di registrazione protocollo e lettura protocollo. Sono stati forniti degli ambienti di test sia da Maggioli: http://www.cedaf.it/client/services/ProWSApi?wsdl 
/// che da studio k: http://www.studiok.it:9091/ws_sici/ProWSApi.asmx?wsdl
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// Indicare qui l'url dove sono esposti i servizi web per la lettura degli allegati, il protocollo sicraweb infatti, dispone di un web service diverso da quello di protocollazione per la lettura dei documenti.
					/// </summary>
					public string UrlWsAllegati
					{
						get{ return GetString("URL_WS_ALLEGATI");}
						set{ SetString("URL_WS_ALLEGATI" , value); }
					}
					
					/// <summary>
					/// Indica se deve rispettare il vincolo presenta anche sul protocollo docarea e relativo al fatto che non può inserire più di un mittente / destinatario nella registrazione di un protocollo. Valorizzando questo parametro a 1 allora il sistema inserirà solamente il primo mittente / destinatario nell'elemento Mittente o Destinatario altrimenti inserirà tutti i mittenti / destinatari dentro l'array dell'elemento MittentiMulti e DestinataroMulti.
					/// </summary>
					public string UsaMonoMittdest
					{
						get{ return GetString("USA_MONO_MITTDEST");}
						set{ SetString("USA_MONO_MITTDEST" , value); }
					}
					
					
				}
			}
			
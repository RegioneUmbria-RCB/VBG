
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_APSYSTEMS il 26/08/2014 17.28.01
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Protocollo ApSystems è un sistema di protocollo che espone web service per eseguire le sue funzionalità, l'azienda proprietaria è A.P. SYSTEMS di Magenta. Il protocollo è presente ad esempio al Comune di Viareggio. Vanno valorizzati necessariamente i parametri URL, USERNAME e PASSWORD.
				/// </summary>
				public partial class VerticalizzazioneProtocolloApsystems : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_APSYSTEMS";
					
					public VerticalizzazioneProtocolloApsystems(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// (Facoltativo) Se selezionato consente di ignorare i dati di classifica inviati dal backoffice al componente che gestisce il protocollo Insiel, questo in quanto il protocollo considera la classifica come facoltativa mentre, lato backoffice, è un dato obbligatorio che deve necessariamente essere valorizzato per essere validato, il componente .net che fa da tramite tra le due applicazioni si prenderà carico di gestire (se = 0 o assente) o meno (se valorizzato a 1) la classifica in base al valore di questo parametro.
					/// </summary>
					public string EscludiClassifica
					{
						get{ return GetString("ESCLUDI_CLASSIFICA");}
						set{ SetString("ESCLUDI_CLASSIFICA" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Credenziale di accesso al web service, indica la password con cui autenticarsi, va in coppia con il parametro USERNAME di questa verticalizzazione, se non utilizzato non è possibile invocare le funzionalità di protocollazione. In ambiente di test è stato utilizzato il valore "TestInAPS"
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Indica l'url del web service di Ap Systems da utilizzare nell'ambiente scelto (Test o Produzione). Se si desidera utilizzare un ambiente di test inserire il valore http://test.hypersic.net/wsprotocollo/serviceprotocollo.asmx <http://test.hypersic.net/wsprotocollo/serviceprotocollo.asmx> , è un ambiente di sviluppo creato dai programmatori di ApSystems appositamente per fare le prove.
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Credenziale di accesso al web service, indica lo username con cui autenticarsi, se non utilizzato non è possibile invocare le funzionalità di protocollazione. In ambiente di test è stato utilizzato il valore "APS"
					/// </summary>
					public string Username
					{
						get{ return GetString("USERNAME");}
						set{ SetString("USERNAME" , value); }
					}
					
					
				}
			}
			
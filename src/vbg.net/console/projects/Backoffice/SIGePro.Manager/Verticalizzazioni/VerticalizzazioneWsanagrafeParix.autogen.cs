
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione WSANAGRAFE_PARIX il 26/08/2014 17.23.44
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivato permette di recuperare un'anagrafe persona giuridica dai servizi web di PARIX, la funzionalità viene attivata nelle anagrafiche richiedenti e tecnici sia in inserimento che per controlli successivi e durante la presentazione della dom,anda on-line. Questo modulo va abilitato contestualmente ad un altro modulo WSANAGRAFE_XXX (modulo "principale") dove XXX è CESENA,PIACENZA,... a condizione che il modulo "principale" sia sviluppato tenendo conto della compatibilità con l'integrazione PARIX. Le indicazioni se un modulo "principale" è compatibile con PARIX saranno scritte nella descrizione del modulo "principale".
				/// </summary>
				public partial class VerticalizzazioneWsanagrafeParix : Verticalizzazione
				{
					private static class Constants
					{
						public const string NomeVerticalizzazione = "WSANAGRAFE_PARIX";
						public const string ParametroCercaSoloCf = "CERCA_SOLO_CF";
					}

                    public VerticalizzazioneWsanagrafeParix()
                    {

                    }

					public VerticalizzazioneWsanagrafeParix(string idComuneAlias, string software ) : base(idComuneAlias, Constants.NomeVerticalizzazione , software ){}
					
					
					/// <summary>
					/// Password da utilizzare per l'autenticazione quando il servizio viene chiamato attraverso le PDD del CART
					/// </summary>
					public string BasicAuthPassword
					{
						get{ return GetString("BASIC_AUTH_PASSWORD");}
						set{ SetString("BASIC_AUTH_PASSWORD" , value); }
					}
					
					/// <summary>
					/// Username da utilizzare per l'autenticazione quando il servizio viene chiamato attraverso le PDD del CART
					/// </summary>
					public string BasicAuthUser
					{
						get{ return GetString("BASIC_AUTH_USER");}
						set{ SetString("BASIC_AUTH_USER" , value); }
					}
					
					/// <summary>
					/// Password dell'utente abilitato alla ricerca
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// Url nel formato http[s]://INDIRIZZO:PORTA da utilizzare per effettuare le chiamate al web service di parix
					/// </summary>
					public string ProxyAddress
					{
						get{ return GetString("PROXY_ADDRESS");}
						set{ SetString("PROXY_ADDRESS" , value); }
					}
					
					/// <summary>
					/// Parametro che stabilisce se effettuare la ricerca sul servizio nazionale o meno ( "diretto": accesso diretto alò servizio nazionale, "no" non passa mai dal servizio nazionale, ""/" ":passa dal servizio locale ed eventualmente da quello nazionale
					/// </summary>
					public string Switchcontrol
					{
						get{ return GetString("SWITCHCONTROL");}
						set{ SetString("SWITCHCONTROL" , value); }
					}
					
					/// <summary>
					/// Url del web service (wsdl) da invocare per ricavare i dati anagrafici. 
/// Es. ER: https://servizicner.regione.emilia-romagna.it/parixgate/services/gate 
/// Es. CART tramite PDD: https://nal-test:8443/cart/PD/SPCCfirenze/SPCRegioneToscana/SPCParixgate/RicercaImpresePerCodiceFiscale 
/// Nel browser del server application aggiungendo alla URL '?wsdl' si può verificare se il server raggiunge il servizio.
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// E ' l'utente per accedere ai servizi PARIX, deve essere fornito dal cliente.
					/// </summary>
					public string User
					{
						get{ return GetString("USER");}
						set{ SetString("USER" , value); }
					}
					
					/// <summary>
					/// Percorso degli xsd utili per la validazione dei risultati restituiti dai ws di Parix
					/// </summary>
					public string Xsd
					{
						get{ return GetString("XSD");}
						set{ SetString("XSD" , value); }
					}
					
					public bool CercaSoloCf
					{
						get { return GetBool(Constants.ParametroCercaSoloCf); }
					}
				}
			}
			
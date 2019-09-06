
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_DELTA il 26/08/2014 17.28.01
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivato consente di eseguire la protocollazione con il protocollo proprietario di Delta Informatica, con questo tipo di protocollo abbiamo la possibilità di eseguire delle prove in ambiente di test fornito proprio dalla Società Delta Informatica, in particolare l'applicativo si trova all'indirizzo: http://crm.deltainf.it/protocollo/, NB.: tutte le chiamate al web service hanno come argomenti fissi username e password, che coincidono con un utente già registrato nel sistema, indicare questi valori nei parametri USERNAME e PASSWORD della verticalizzazione PROTOCOLLO_DELTA.
				/// </summary>
				public partial class VerticalizzazioneProtocolloDelta : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_DELTA";
                    public VerticalizzazioneProtocolloDelta()
                    {

                    }
					public VerticalizzazioneProtocolloDelta(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// Indica il codice per conoscenza da utilizzare in fase di protocollazione. Sulla maschera di protocollazione è presente un campo a tendina denominato -Trasmesso per- legato all'anagrafica (Mittente / Destinatario), questo campo viene popolato dai valori presenti nella tabella PROTOCOLLO_MODALITAINVIO, indicando il codice in questo campo, quando l'operatore selezionerà nella tendina -Trasmesso per- il valore con lo stesso codice, l'anagrafica selezionata sarà considerata per conoscenza.
					/// </summary>
					public string CodiceCc
					{
						get{ return GetString("CODICE_CC");}
						set{ SetString("CODICE_CC" , value); }
					}
					
					/// <summary>
					/// Indica la password dell'utente che protocolla, e fa parte dell'autenticazione dell'operatore già registrato nell'applicativo di protocollo; ogni chiamata al web service necessita come argomento il valore di questo parametro.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// Indica il registro (o AOO è la stessa cosa) da usare durante la fase di protocollazione, è un dato alfanumerico di tre caratteri, ad esempio in ambiente di test il valore è 011
					/// </summary>
					public string Registro
					{
						get{ return GetString("REGISTRO");}
						set{ SetString("REGISTRO" , value); }
					}
					
					/// <summary>
					/// Specificare in questo parametro l'end point del web service di protocollazione
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// Indica lo username dell'utente che protocolla, e fa parte dell'autenticazione dell'operatore già registrato nell'applicativo di protocollo; ogni chiamata al web service necessita come argomento il valore di questo parametro.
					/// </summary>
					public string Username
					{
						get{ return GetString("USERNAME");}
						set{ SetString("USERNAME" , value); }
					}
					
					
				}
			}
			
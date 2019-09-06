
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione WSANAGRAFE_TERNI il 26/08/2014 17.23.44
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se il parametro ANAGRAFE_SEARCHER della verticalizzazione WSANAGRAFE è impostato su TERNI questa verticalizzazione contiene i parametri di configurazione per invocare il web service di CIVILIA
				/// </summary>
				public partial class VerticalizzazioneWsanagrafeTerni : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "WSANAGRAFE_TERNI";

                    public VerticalizzazioneWsanagrafeTerni()
                    {

                    }

					public VerticalizzazioneWsanagrafeTerni(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// (obbligatorio) Ente utilizzato per effettuare il login al web service, di default è ente0
					/// </summary>
					public string Ente
					{
						get{ return GetString("ENTE");}
						set{ SetString("ENTE" , value); }
					}
					
					/// <summary>
					/// (obbligatorio) Password per effettuare il login al web service, di default è password7
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// Se impostato a 1 nel caso in cui il web service non trovasse l'anagrafica cercata verrà effettuata una ricerca anche tra le anagrafiche di Sigepro
					/// </summary>
					public string UsaAnagrafeSigepro
					{
						get{ return GetString("USA_ANAGRAFE_SIGEPRO");}
						set{ SetString("USA_ANAGRAFE_SIGEPRO" , value); }
					}
					
					/// <summary>
					/// (obbligatorio) Username per effettuare il login al web service, di default è Administrator
					/// </summary>
					public string Username
					{
						get{ return GetString("USERNAME");}
						set{ SetString("USERNAME" , value); }
					}
					
					/// <summary>
					/// (obbligatorio) Url del web service esposto da CIVILIA, di solito è http://civiliaweb.core.it/suap_web/services/WsAnagrafe?wsdl
					/// </summary>
					public string WsUrl
					{
						get{ return GetString("WS_URL");}
						set{ SetString("WS_URL" , value); }
					}
					
					
				}
			}
			
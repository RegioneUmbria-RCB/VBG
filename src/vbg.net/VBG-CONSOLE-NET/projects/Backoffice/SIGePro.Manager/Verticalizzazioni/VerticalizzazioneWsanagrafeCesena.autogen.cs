
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione WSANAGRAFE_CESENA il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Parametri di configurazione dell'oggetto responsabile per le ricerche anagrafiche del comune di CESENA
				/// </summary>
				public partial class VerticalizzazioneWsanagrafeCesena : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "WSANAGRAFE_CESENA";

                    public VerticalizzazioneWsanagrafeCesena()
                    {

                    }

					public VerticalizzazioneWsanagrafeCesena(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Stringa di connessione al database
					/// </summary>
					public string Connectionstring
					{
						get{ return GetString("CONNECTIONSTRING");}
						set{ SetString("CONNECTIONSTRING" , value); }
					}
					
					/// <summary>
					/// Owner della vista da interrogare
					/// </summary>
					public string Owner
					{
						get{ return GetString("OWNER");}
						set{ SetString("OWNER" , value); }
					}
					
					/// <summary>
					/// Nome del provider da utilizzare nella personalLib per creare l'oggetto database
					/// </summary>
					public string Provider
					{
						get{ return GetString("PROVIDER");}
						set{ SetString("PROVIDER" , value); }
					}
					
					/// <summary>
					/// Vista da interrogare
					/// </summary>
					public string View
					{
						get{ return GetString("VIEW");}
						set{ SetString("VIEW" , value); }
					}
					
					
				}
			}
			
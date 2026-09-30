
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione ALLINEAMENTO_ANAGRAFE_CESENA il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che è attivo l'allineamento dell'anagrafe di Cesena
				/// </summary>
				public partial class VerticalizzazioneAllineamentoAnagrafeCesena : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "ALLINEAMENTO_ANAGRAFE_CESENA";

                    public VerticalizzazioneAllineamentoAnagrafeCesena()
                    {

                    }

					public VerticalizzazioneAllineamentoAnagrafeCesena(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Stringa di connessione della vista da interrogare
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
					/// Provider della vista da interrogare
					/// </summary>
					public string Provider
					{
						get{ return GetString("PROVIDER");}
						set{ SetString("PROVIDER" , value); }
					}
					
					/// <summary>
					/// Nome della vista da interrogare
					/// </summary>
					public string View
					{
						get{ return GetString("VIEW");}
						set{ SetString("VIEW" , value); }
					}
					
					
				}
			}
			
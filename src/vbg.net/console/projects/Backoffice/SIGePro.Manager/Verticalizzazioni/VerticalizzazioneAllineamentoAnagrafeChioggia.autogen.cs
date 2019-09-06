
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione ALLINEAMENTO_ANAGRAFE_CHIOGGIA il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che è attivo l'allineamento dell'anagrafe di Chioggia
				/// </summary>
				public partial class VerticalizzazioneAllineamentoAnagrafeChioggia : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "ALLINEAMENTO_ANAGRAFE_CHIOGGIA";

                    public VerticalizzazioneAllineamentoAnagrafeChioggia()
                    {

                    }

					public VerticalizzazioneAllineamentoAnagrafeChioggia(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// E' il percorso completo del file dat
					/// </summary>
					public string FullPathDat
					{
						get{ return GetString("FULL_PATH_DAT");}
						set{ SetString("FULL_PATH_DAT" , value); }
					}
					
					/// <summary>
					/// E' l'intestazione del file dat
					/// </summary>
					public string Header
					{
						get{ return GetString("HEADER");}
						set{ SetString("HEADER" , value); }
					}
					
					
				}
			}
			
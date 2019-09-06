
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione HELPBASE il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se = 1 verrà abilitata la possibilità di scrivere l'help di base nelle varie pagine di SIGePro utilizzando il tasto Help verde posto a destra delle pagine. Attenzione!!! Questa funzionalità va attivata solo da personale In.I.T.
				/// </summary>
				public partial class VerticalizzazioneHelpbase : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "HELPBASE";

                    public VerticalizzazioneHelpbase()
                    {

                    }

					public VerticalizzazioneHelpbase(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// E' il codice dell'operatore di SIGePro che può modificare l'help di base.
					/// </summary>
					public string Codiceoperatore
					{
						get{ return GetString("CODICEOPERATORE");}
						set{ SetString("CODICEOPERATORE" , value); }
					}
					
					
				}
			}
			
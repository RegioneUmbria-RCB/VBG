
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione GEST_CANCELLAZIONI il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Permette di gestire la cancellazione dei dati sensibili di SIGePro impostando alcune password sulle funzionalità di cancellazione. ( ad esempio la cancellazione di un'istanza ).
/// Se non attivata, al momento della cancellazione di questi dati, non verrà richiesta nessuna password di sicurezza.
				/// </summary>
				public partial class VerticalizzazioneGestCancellazioni : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "GEST_CANCELLAZIONI";

                    public VerticalizzazioneGestCancellazioni()
                    {

                    }

					public VerticalizzazioneGestCancellazioni(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Se attiva la verticalizzazione, contiene la password da usare in caso si voglia sbloccare una PEC in carico ad un'altro operatore.
					/// </summary>
					public string PasswordSbloccoPec
					{
						get{ return GetString("PASSWORD_SBLOCCO_PEC");}
						set{ SetString("PASSWORD_SBLOCCO_PEC" , value); }
					}
					
					/// <summary>
					/// Se attiva la verticalizzazione, contiene la password per poter cancellare un' istanza e i relativi dati collegati.
					/// </summary>
					public string PwdIstanze
					{
						get{ return GetString("PWD_ISTANZE");}
						set{ SetString("PWD_ISTANZE" , value); }
					}
					
					/// <summary>
					/// Se attiva la verticalizzazione, contiene la password per poter cancellare un sorteggio.
					/// </summary>
					public string PwdSorteggi
					{
						get{ return GetString("PWD_SORTEGGI");}
						set{ SetString("PWD_SORTEGGI" , value); }
					}
					
					
				}
			}
			
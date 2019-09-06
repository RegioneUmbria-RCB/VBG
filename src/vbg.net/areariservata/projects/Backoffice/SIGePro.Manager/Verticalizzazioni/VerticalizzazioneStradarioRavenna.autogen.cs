
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione STRADARIO_RAVENNA il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitato aggiunge un nuovo bottone a fianco dell'indirizzo nella scheda dell'istanza. Questi permette di vedere la circoscrizione e frazione dalle tabelle di Ravenna. Lo stesso viene estrapolato in fase di stampa documenti tipo nel campo LOC_CIRCOSCRIZIONE.
				/// </summary>
				public partial class VerticalizzazioneStradarioRavenna : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "STRADARIO_RAVENNA";

                    public VerticalizzazioneStradarioRavenna()
                    {

                    }

					public VerticalizzazioneStradarioRavenna(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Stringa di connessione per il collegamento alle tabelle della toponomastica
					/// </summary>
					public string Connectionstring
					{
						get{ return GetString("CONNECTIONSTRING");}
						set{ SetString("CONNECTIONSTRING" , value); }
					}
					
					
				}
			}
			
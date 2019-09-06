
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROT_ASSEGNAZIONI_MULTIPLE il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitata la regola PROT_CLASSIFICAZIONI_MULTIPLE allora sarà possibile, flaggando questa regola, abilitare l'assegnazione al momento dell'inserimento di una nuova classificazione multipla.
				/// </summary>
				public partial class VerticalizzazioneProtAssegnazioniMultiple : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROT_ASSEGNAZIONI_MULTIPLE";

                    public VerticalizzazioneProtAssegnazioniMultiple()
                    {

                    }

					public VerticalizzazioneProtAssegnazioniMultiple(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			
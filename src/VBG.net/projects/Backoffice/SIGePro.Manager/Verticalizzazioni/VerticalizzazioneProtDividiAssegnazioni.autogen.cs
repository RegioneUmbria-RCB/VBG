
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROT_DIVIDI_ASSEGNAZIONI il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Consente, in fase di visualizzazione delle assegnazioni, di dividere le assegnazioni in base alle classificazioni, quindi se per un protocollo sono state inserite più classificazioni allora le assegnazioni saranno divise in base ad esse.
				/// </summary>
				public partial class VerticalizzazioneProtDividiAssegnazioni : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROT_DIVIDI_ASSEGNAZIONI";

                    public VerticalizzazioneProtDividiAssegnazioni()
                    {

                    }

					public VerticalizzazioneProtDividiAssegnazioni(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			
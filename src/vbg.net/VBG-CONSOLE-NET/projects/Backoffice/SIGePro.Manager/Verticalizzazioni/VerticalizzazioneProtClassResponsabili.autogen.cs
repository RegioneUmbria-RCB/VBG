
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROT_CLASS_RESPONSABILI il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitato consente, al PROTOCOLLO GENERALE, di visualizzare in fase di classificazione solamente i responsabili di servizio, e quindi solo il primo livello dell'albero della classificazione.
				/// </summary>
				public partial class VerticalizzazioneProtClassResponsabili : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROT_CLASS_RESPONSABILI";

                    public VerticalizzazioneProtClassResponsabili()
                    {

                    }

					public VerticalizzazioneProtClassResponsabili(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			
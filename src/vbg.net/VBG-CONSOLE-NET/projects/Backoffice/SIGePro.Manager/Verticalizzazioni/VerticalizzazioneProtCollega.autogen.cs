
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROT_COLLEGA il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Abilita il Protocollo Generale a collegare i protocolli.
				/// </summary>
				public partial class VerticalizzazioneProtCollega : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROT_COLLEGA";

                    public VerticalizzazioneProtCollega() : base()
                    {

                    }

					public VerticalizzazioneProtCollega(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			
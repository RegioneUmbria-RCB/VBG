
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROT_COLLEGA_PROPRI il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Abilita l'utente loggato a collegare i protocolli, questo sarà possibile dai propri protocolli.
				/// </summary>
				public partial class VerticalizzazioneProtCollegaPropri : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROT_COLLEGA_PROPRI";

                    public VerticalizzazioneProtCollegaPropri()
                    {

                    }

					public VerticalizzazioneProtCollegaPropri(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			
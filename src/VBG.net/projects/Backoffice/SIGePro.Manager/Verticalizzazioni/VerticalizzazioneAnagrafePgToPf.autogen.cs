
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione ANAGRAFE_PG_TO_PF il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva nella gestione delle anagrafiche compare un pulsante che permette di trasformare una persona fisica in giuridica e viceversa.
				/// </summary>
				public partial class VerticalizzazioneAnagrafePgToPf : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "ANAGRAFE_PG_TO_PF";

                    public VerticalizzazioneAnagrafePgToPf()
                    {

                    }

					public VerticalizzazioneAnagrafePgToPf(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			
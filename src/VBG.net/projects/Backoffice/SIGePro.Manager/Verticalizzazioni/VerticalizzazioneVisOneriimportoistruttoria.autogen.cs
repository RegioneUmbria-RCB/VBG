
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione VIS_ONERIIMPORTOISTRUTTORIA il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Forza la visualizzazione del campo ISTANZEONERI.PREZZOISTRUTTORIA anche per le causali oneri che non sono legate agli endoprocedimenti. Per default il prezzo istruttoria viene visualizzato solo se si tratta di una causale che prevede l'associazione ad un endoprocedimento. A Ravenna Edilizia è 1.
				/// </summary>
				public partial class VerticalizzazioneVisOneriimportoistruttoria : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "VIS_ONERIIMPORTOISTRUTTORIA";

                    public VerticalizzazioneVisOneriimportoistruttoria()
                    {

                    }

					public VerticalizzazioneVisOneriimportoistruttoria(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			

			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione TECNICO_RICHIEDENTE il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se la verticalizzazione è attiva, nelle ricerche dei richiedenti compariranno anche i tecnici
				/// </summary>
				public partial class VerticalizzazioneTecnicoRichiedente : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "TECNICO_RICHIEDENTE";

                    public VerticalizzazioneTecnicoRichiedente()
                    {

                    }

					public VerticalizzazioneTecnicoRichiedente(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			
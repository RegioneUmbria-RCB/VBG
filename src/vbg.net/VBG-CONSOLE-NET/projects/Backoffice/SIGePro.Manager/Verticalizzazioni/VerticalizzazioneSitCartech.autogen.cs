
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SIT_CARTECH il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitato viene richiesto nelle istanza il codice Edificio, il codice poligono ed il codice punto utilizzato dal SIT per interrogare SIGePro tramite una vista. E' stato abilitatoper NovaMilanese.
				/// </summary>
				public partial class VerticalizzazioneSitCartech : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SIT_CARTECH";

                    public VerticalizzazioneSitCartech()
                    {

                    }

					public VerticalizzazioneSitCartech(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			
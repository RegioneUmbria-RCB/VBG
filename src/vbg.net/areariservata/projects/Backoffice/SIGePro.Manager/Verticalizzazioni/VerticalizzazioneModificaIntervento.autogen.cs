
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione MODIFICA_INTERVENTO il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva permette la modifica dell'intervento ( intervento - procedura - mov. avvio )
/// delle istanze escludendo le regole attuali che ne consentono la modifica solamente se:
///  - Non esistono altri movimenti ad eccezione di quello di avvio
///  - Non esistono oneri collegati alla pratica
///  - Non esistono endoprocedimenti attivati
///  - Non esistono documenti allegati
///  - Non esistono dat dinamici valorizzati
				/// </summary>
				public partial class VerticalizzazioneModificaIntervento : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "MODIFICA_INTERVENTO";

                    public VerticalizzazioneModificaIntervento() : base()
                    {

                    }

					public VerticalizzazioneModificaIntervento(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			

			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione AIDA il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva abilita la ricezione di pratiche dal sistema AIDA (necessita della verticalizzazione STC)
				/// </summary>
				public partial class VerticalizzazioneAida : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "AIDA";

                    public VerticalizzazioneAida()
                    {

                    }

					public VerticalizzazioneAida(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Pattern del parametro gg/MM/yyyy. Indicare la data dalla quale il nodo NLA_AIDA inizierà a processare i movimenti che saranno inviati al metodo aggiornaStato presente nei WS esposti dal sistema AIDA. Questo valore verrà aggiornato con la data dell’ultimo invio dei dati ad AIDA. Se un movimento verrà modificato successivamente all’inserimento le modifiche non verranno inviate (tale parametro viene confrontato con MOVIMENTI.DATAINSERIMENTO).
					/// </summary>
					public string AggstatoDataSchedulazione
					{
						get{ return GetString("AGG-STATO_DATA_SCHEDULAZIONE");}
						set{ SetString("AGG-STATO_DATA_SCHEDULAZIONE" , value); }
					}
					
					/// <summary>
					/// Invia o meno le informazioni relative agli allegati al metodo aggironaStato esposto dai WS di AIDA. Non invia l’allegato fisico. Può assumere due valori . 1 : invia la lista degli allegati, 0: non invia la lista degli allegati
					/// </summary>
					public string AggstatoInvioAllegati
					{
						get{ return GetString("AGG-STATO_INVIO_ALLEGATI");}
						set{ SetString("AGG-STATO_INVIO_ALLEGATI" , value); }
					}
					
					
				}
			}
			
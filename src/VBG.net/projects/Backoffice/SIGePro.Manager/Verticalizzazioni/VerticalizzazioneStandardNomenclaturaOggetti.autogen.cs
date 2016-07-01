
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione STANDARD_NOMENCLATURA_OGGETTI il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitato al momento dell'upload del file il nome del file caricato sarà modificato in IDCOMUNE_I<CODICEISTANZA>_O<CODICEOGGETTO>_<nomefilecaricato>. La sezione I<CODICEISTANZA> comparirà solamente se relativi a file provenienti da istanze, ossia se sulla servlet che carica i file è specificato il parametro codiceistanza.  La seguente regola non è valida se attiva la verticalizzazione FILESYSTEM
				/// </summary>
				public partial class VerticalizzazioneStandardNomenclaturaOggetti : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "STANDARD_NOMENCLATURA_OGGETTI";

                    public VerticalizzazioneStandardNomenclaturaOggetti()
                    {

                    }

					public VerticalizzazioneStandardNomenclaturaOggetti(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			
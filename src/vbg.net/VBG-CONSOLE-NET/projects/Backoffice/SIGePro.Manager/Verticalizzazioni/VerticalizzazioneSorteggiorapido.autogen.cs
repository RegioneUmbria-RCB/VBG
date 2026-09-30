
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SORTEGGIORAPIDO il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva consente all'istanza di accedere alle funzionalità di SORTEGGIO RAPIDO, contiene dei parametri. La funzionalità nasconde tutti i pulsanti dell'istanza (tranne SALVA, SCHEDE, ELIMINA e CHIUDI) fino a che il valore del campo dinamico specificato nel parametro CAMPO_DYN_SORTEGGIATA non è diverso da "". Es. a Perugia è stata creata una scheda dinamica che al salvataggio, in maniera random, scrive un valore di "Sorteggiata" o "Non sorteggiata" nel campo dinamico.
				/// </summary>
				public partial class VerticalizzazioneSorteggiorapido : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SORTEGGIORAPIDO";

                    public VerticalizzazioneSorteggiorapido()
                    {

                    }

					public VerticalizzazioneSorteggiorapido(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Indica il valore del campo NOMECAMPO presente dentro la tabella DYN2_CAMPI che fa riferimento al SORTEGGIORAPIDO
					/// </summary>
					public string CampoDynSorteggiata
					{
						get{ return GetString("CAMPO_DYN_SORTEGGIATA");}
						set{ SetString("CAMPO_DYN_SORTEGGIATA" , value); }
					}
					
					/// <summary>
					/// Se 0 allora il pulsante SCHEDE è visualizzato solo dopo la protocollazione dell'istanza, altrimenti il pulsante SCHEDE è sempre visibile.
					/// </summary>
					public string MostraSchedeProt
					{
						get{ return GetString("MOSTRA_SCHEDE_PROT");}
						set{ SetString("MOSTRA_SCHEDE_PROT" , value); }
					}
					
					
				}
			}
			
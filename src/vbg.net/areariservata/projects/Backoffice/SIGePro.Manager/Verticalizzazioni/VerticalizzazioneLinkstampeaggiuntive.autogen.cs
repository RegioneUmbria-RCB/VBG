
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione LINKSTAMPEAGGIUNTIVE il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se la verticalizzazione è attivata compariranno altre opzioni di stampa ( configurabili tramite i parametri di questa verticalizzazione ) dopo il click del bottone "STAMPA" presente nella scheda principale delle istanze.
				/// </summary>
				public partial class VerticalizzazioneLinkstampeaggiuntive : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "LINKSTAMPEAGGIUNTIVE";

                    public VerticalizzazioneLinkstampeaggiuntive()
                    {

                    }

					public VerticalizzazioneLinkstampeaggiuntive(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Può contenere una lista di link ( separati da ";" ) che verranno caricati a seconda del tipo di stampa selezionato.
/// ATTENZIONE: La lista dei link deve contenere lo stesso numero di elementi del parametro LINKSTAMPEAGGIUNTIVE.TESTOLINK.
/// E' possibile inserire la variabile &CODICEISTANZA nel link che verrà poi sostituito con il codice univoco dell'istanza dal quale si effettua la stampa, attenzione, tutti i parametri &CODICEISTANZA scritti in maiuscolo verranno sostituiti con l'effettivo codiceistanza..
					/// </summary>
					public string Link
					{
						get{ return GetString("LINK");}
						set{ SetString("LINK" , value); }
					}
					
					/// <summary>
					/// Può contenere  una lista di testi ( separati da ";" ) che compariranno come ulteriori opzioni durante la scelta del tipo di stampa.
/// ATTENZIONE: La lista deve contenere lo stesso numero di elementi del parametro LINKSTAMPEAGGIUNTIVE.LINK.
					/// </summary>
					public string Testolink
					{
						get{ return GetString("TESTOLINK");}
						set{ SetString("TESTOLINK" , value); }
					}
					
					
				}
			}
			
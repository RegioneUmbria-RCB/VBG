
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione CENTER_PAGE il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitata mostra una pagina di centro diversa da quella di default. Se l'utente che entra è abilitato a più software allora ha effetto solo la verticalizzazione per software TT altrimenti quella per il software abilitato.
				/// </summary>
				public partial class VerticalizzazioneCenterPage : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "CENTER_PAGE";

                    public VerticalizzazioneCenterPage()
                    {

                    }

					public VerticalizzazioneCenterPage(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Rappresenta l'url della pagina da visualizzare al posto di quella di default: 'welcome/start.htm?software=TT'
					/// </summary>
					public string Centerpage
					{
						get{ return GetString("CENTERPAGE");}
						set{ SetString("CENTERPAGE" , value); }
					}
					
					
				}
			}
			
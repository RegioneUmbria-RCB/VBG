
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SISTEMAPAGAMENTI_ATTIVO_FO il 26/08/2014 17.23.44
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Indica se nel frontoffice è attivato un sistema di pagamento per permettere all'utente collegato di poter effettuare pagamenti telematici. (es: PAGAMENTI_MIP_RPCSUAP)
				/// </summary>
				public partial class VerticalizzazioneSistemapagamentiAttivoFo : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SISTEMAPAGAMENTI_ATTIVO_FO";

                    public VerticalizzazioneSistemapagamentiAttivoFo()
                    {

                    }

					public VerticalizzazioneSistemapagamentiAttivoFo(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Indicare quale sistema di pagamento è attivo (es: PAGAMENTI_MIP_RPCSUAP)
					/// </summary>
					public string Tiposistema
					{
						get{ return GetString("TIPOSISTEMA");}
						set{ SetString("TIPOSISTEMA" , value); }
					}
					
					
				}
			}
			
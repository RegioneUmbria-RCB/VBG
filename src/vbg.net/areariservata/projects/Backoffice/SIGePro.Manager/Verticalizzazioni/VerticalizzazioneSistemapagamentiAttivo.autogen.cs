
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SISTEMAPAGAMENTI_ATTIVO il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che c'è un sistema di pagamenti attivato ( REGULUS ).
/// Commessa: C-1562-2009. Documento per l'installazione: C-1562-2009-Integrazione_Sistema_di_Pagamenti_Regulus.doc
				/// </summary>
				public partial class VerticalizzazioneSistemapagamentiAttivo : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SISTEMAPAGAMENTI_ATTIVO";

                    public VerticalizzazioneSistemapagamentiAttivo()
                    {

                    }

					public VerticalizzazioneSistemapagamentiAttivo(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Indica la regola con la quale viene generato il numero univoco da passare a REGULUS.
/// Esempio di numerazione: PG[NUMEROPROTOCOLLO]/[FKIDTIPOCAUSALE]
/// E' possibile utilizzare i seguenti campi nelle parentesi quadrate:
/// DATAPROTOCOLLO -> Data di protocollo dell'istanza nella forma DD/MM/YYYY
/// DATA -> Data di presentazione dell'istanza nella forma DD/MM/YYYY
/// CODICEISTANZA -> Codice progressivo dell'istanza
/// NUMEROPROTOCOLLO -> Numero di protocollo dell'istanza
/// NUMEROISTANZA -> Numero dell'istanza
/// SOFTWARE -> Software dell'istanza
/// CODICECOMUNE -> Codice del comune dell'istanza ( installazioni multicomune )
/// FKIDTIPOCAUSALE -> Codice della causale d'onere
					/// </summary>
					public string Numerodocumento
					{
						get{ return GetString("NUMERODOCUMENTO");}
						set{ SetString("NUMERODOCUMENTO" , value); }
					}
					
					/// <summary>
					/// E' il sistema di pagamento attivato e può assumere i seguenti valori:
/// REGULUS
					/// </summary>
					public string Tipopagamento
					{
						get{ return GetString("TIPOPAGAMENTO");}
						set{ SetString("TIPOPAGAMENTO" , value); }
					}
					
					
				}
			}
			
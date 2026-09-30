
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PAYER_ADAPTER il 26/08/2014 17.23.44
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Contiene i parametri usati dal componente PAYER_ADAPTER per l'integrazione di Back Office con PayER (Sistema dei pagamenti Regione Emilia Romagna)
				/// </summary>
				public partial class VerticalizzazionePayerAdapter : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PAYER_ADAPTER";
					
					public VerticalizzazionePayerAdapter(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Indica il valore che viene inviato da PayER nel tag Request_ResponseBase.codiceUfficio. Serve per recuperare il modulo software per filtrare gli oneri (se questo valore non è specificato (oppure è uguale a TT) allora il software viene recuperato da PAYER_ADAPTER.CODICE_UTENTE e se questo è nullo o uguale a TT allora le scadenze vengono estratte per tutti i moduli)
					/// </summary>
					public string CodiceUfficio
					{
						get{ return GetString("CODICE_UFFICIO");}
						set{ SetString("CODICE_UFFICIO" , value); }
					}
					
					/// <summary>
					/// Indica il valore che viene inviato da PayER nel tag Request_ResponseBase.codiceUtente. Serve per recuperare il modulo software per filtrare gli oneri (nel caso non venga specificato anche il parametro di verticalizzazione PAYER_ADAPTER.CODICE_UFFICIO)
					/// </summary>
					public string CodiceUtente
					{
						get{ return GetString("CODICE_UTENTE");}
						set{ SetString("CODICE_UTENTE" , value); }
					}
					
					/// <summary>
					/// Contiene il valore che viene inviato dal PAYER_ADAPTER alla richiesta di RecuperaDatiBollettinoRequest. Rappresenta il valore del Conto Corrente di accreditamento che verrà presentato al cittadino. Il valore viene inserito in RecuperaDatiBollettinoResponse.numeroCCAccreditoPagamento. Se configurato un valore per software allora il software sarà recuperato secondo la logica di quanto scritto in PAYER_ADAPTER.CODICE_UFFICIO altrimenti sarà preso il valore del software TT. In caso di mancata valorizzazione di questo parametro PAYER_ADAPTER lancera' un errore alla richiesta da parte di PayER
					/// </summary>
					public string NumCcaccreditopagamento
					{
						get{ return GetString("NUM_CCACCREDITOPAGAMENTO");}
						set{ SetString("NUM_CCACCREDITOPAGAMENTO" , value); }
					}
					
					/// <summary>
					/// Indica il valore che viene inviato da PayER nel tag RequestResponseLista.tipologiaServizio e RecuperaDatiBollettinoResponse.tipologiaServizio. 
///     Il valore deve essere concordato con Lepida e deve essere configurato obbligatoriamente.
					/// </summary>
					public string TipologiaServizio
					{
						get{ return GetString("TIPOLOGIA_SERVIZIO");}
						set{ SetString("TIPOLOGIA_SERVIZIO" , value); }
					}
					
					/// <summary>
					/// Valori possibili S o N. Se S allora il codice fiscale passato, utilizzato per recuperare le scadenze di pagamento, viene ricercato sia nel campo del richiedente (ISTANZE.CODICERICHIEDENTE) che in quello del tecnico dell'istanza (ISTANZE.CODICEPROFESSIONISTA). Di default (se valore vuoto o non presente) il codice fiscale viene ricercato solamente in quello del richiedente (ISTANZE.CODICERICHIEDENTE) dell'istanza.
					/// </summary>
					public string UsaCodicefiscaleTecnico
					{
						get{ return GetString("USA_CODICEFISCALE_TECNICO");}
						set{ SetString("USA_CODICEFISCALE_TECNICO" , value); }
					}
					
					
				}
			}
			

			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_GEPROT il 26/08/2014 17.28.01
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// GEPROT è il protocollo di Sistematica utilizzato nei Comuni Umbri
				/// </summary>
				public partial class VerticalizzazioneProtocolloGeprot : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_GEPROT";

                    public VerticalizzazioneProtocolloGeprot()
                    {

                    }

					public VerticalizzazioneProtocolloGeprot(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// Codice dell'amministrazione
					/// </summary>
					public string Codiceamministrazione
					{
						get{ return GetString("CODICEAMMINISTRAZIONE");}
						set{ SetString("CODICEAMMINISTRAZIONE" , value); }
					}
					
					/// <summary>
					/// Codice dell'AOO
					/// </summary>
					public string Codiceaoo
					{
						get{ return GetString("CODICEAOO");}
						set{ SetString("CODICEAOO" , value); }
					}
					
					/// <summary>
					/// Denominazione dell'amministrazione
					/// </summary>
					public string Denominazioneamministrazione
					{
						get{ return GetString("DENOMINAZIONEAMMINISTRAZIONE");}
						set{ SetString("DENOMINAZIONEAMMINISTRAZIONE" , value); }
					}
					
					/// <summary>
					/// Denominazione dell'AOO
					/// </summary>
					public string Denominazioneaoo
					{
						get{ return GetString("DENOMINAZIONEAOO");}
						set{ SetString("DENOMINAZIONEAOO" , value); }
					}
					
					/// <summary>
					/// Indirizzo telematico dell'amministrazione
					/// </summary>
					public string Indirizzotelematico
					{
						get{ return GetString("INDIRIZZOTELEMATICO");}
						set{ SetString("INDIRIZZOTELEMATICO" , value); }
					}
					
					/// <summary>
					/// E' l'operatore da utilizzare per protocollare con GEPROT, deve essere fornito dall'amministratore del protocollo GEPROT.
					/// </summary>
					public string Operatore
					{
						get{ return GetString("OPERATORE");}
						set{ SetString("OPERATORE" , value); }
					}
					
					/// <summary>
					/// E' la password per protocollare in GEPROT, deve essere fornita dall'amministratore del protocollo GEPROT.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// Doctype di validazione della segnatura inviata per la protocollazione
					/// </summary>
					public string Protdoctype
					{
						get{ return GetString("PROTDOCTYPE");}
						set{ SetString("PROTDOCTYPE" , value); }
					}
					
					/// <summary>
					/// E' l'URL per invocare il protocollo. Non devono essere specificati i metodi.
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
			
                    /// <summary>
                    /// Parametro che indica se deve essere attivata la funzionalità di invio pec per le protocollazioni in partenza, valorizzare a 1 se si desidera fare in modo che il sistema invii una pec per conto del sistema di protocollo, valorizzare con qualsiasi altro valore (meglio se 0 o non valorizzato) se si desidera che il sistema non invii una pec. Nel caso in cui sia attivo questo parametro (valore 1) il componente andrà ad invocare il metodo inviaEMail del web service di protocollo messo a disposizione da Sistematica.
					/// </summary>
					public string InvioPec
					{
                        get { return GetString("INVIO_PEC"); }
                        set { SetString("INVIO_PEC", value); }
					}
				}
			}
			

			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_KIBERNETES il 05/05/2015 17.32.00
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivo consente la protocollazione tramite il componente che si integra con il protocollo della ditta KIBERNETES.
				/// </summary>
				public partial class VerticalizzazioneProtocolloKibernetes : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_KIBERNETES";

                    public VerticalizzazioneProtocolloKibernetes()
                    {

                    }

                    public VerticalizzazioneProtocolloKibernetes(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

                    /// <summary>
                    /// Indicare l'ufficio UO che si occupa di protocollare, da non confondere con l'ufficio assegnatario che viene valorizzato con l'UO e RUOLO delle amministrazioni, anche se gli archivi da dove recuperare questo dato sono sempre gli stessi, ed è possibile interrogarli tramite il metodo search4UO.
                    /// </summary>
                    public string UfficioProtocollante
                    {
                        get { return GetString("UFFICIO_PROTOCOLLANTE"); }
                        set { SetString("UFFICIO_PROTOCOLLANTE", value); }
                    }

                    /// <summary>
                    /// Indicare l'indirizzo endpoint dove è installato il web service Kibernetes.
                    /// </summary>
                    public string Url
                    {
                        get { return GetString("URL"); }
                        set { SetString("URL", value); }
                    }

                    /// <summary>
                    /// Indicare in questo parametro lo username di accesso al web service, tenendo presente che i web service necessitano di autenticazione preemptive.
                    /// </summary>
                    public string Username
                    {
                        get { return GetString("USERNAME"); }
                        set { SetString("USERNAME", value); }
                    }

                    /// <summary>
                    /// Indicare in questo parametro la password di accesso al web service (strettamente legata al parametro USERNAME chiaramente), tenendo presente che i web service necessitano di autenticazione preemptive.
                    /// </summary>
                    public string Password
                    {
                        get { return GetString("PASSWORD"); }
                        set { SetString("PASSWORD", value); }
                    }


                    /// <summary>
                    /// E' il codice con cui viene identificato l'ente sul sistema di protocollo e va indicato da tutti i metodi del web service, in genere viene valorizzato proprio con il codice istat del comune interessato, tuttavia si può ricavare invocando il metodo GetElencoIstat del web service.
                    /// </summary>
                    public string CodiceIstat
                    {
                        get { return GetString("CODICEISTAT"); }
                        set { SetString("CODICEISTAT", value); }
                    }
                }
			}
			
using RestSharp;
using System.Text.Json;
using System.Text.RegularExpressions;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services
{
    public class ProtocolloService : BaseService
    {
        private static class Constants
        {
            public const string INSERISCI_PROTOCOLLO = "inserisci-protocollo";
            public const string RIPROTOCOLLA = "riprotocolla";
            public const string ANNULLA_PROTOCOLLO = "annulla-protocollo";
            public const string DETTAGLIO_PROTOCOLLO = "dettaglio-protocollo";
            public const string INTERROGA_PROTOCOLLO = "interroga-protocollo";
            public const string AGGIORNA_PROTOCOLLO = "aggiorna-protocollo";
            public const string DOWNLOAD_DOCUMENTO = "download-documento";
            public const string RICEVUTE_PEC = "ricevute-pec";
            public const string DOWNLOAD_RICEVUTA_PEC = "download-ricevuta-pec";
            public const string DOWNLOAD_BUSTA_PEC = "download-busta-pec";
            public const string TIPI_DOCUMENTO = "tipi-doc";
            public const string INFO_CLASSIFICA = "info-classifica";
            public const string INTERROGA_ANAGRAFICA = "interroga-anagrafica";
            public const string AGGIORNA_ANAGRAFICA = "aggiorna-anagrafica";
            public const string NUOVA_ANAGRAFICA = "nuova-anagrafica";
            public const string PREDISPONI_ANAGRAFICA = "predisponi-anagrafica";
            public const string ABIL_APERTURA_FASCICOLO = "abil-apertura-fascicolo";
            public const string APERTURA_FASCICOLO = "apertura-fascicolo";
            public const string RIPROTOCOLLA_FASCICOLO = "riprotocolla-fascicolo";
            public const string INTERROGA_FASCICOLI = "interroga-fascicoli";
            public const string DETTAGLIO_FASCICOLO = "dettaglio-fascicolo";
            public const string AGGIORNA_FASCICOLO = "aggiorna-fascicolo";
            public const string REGISTRI_CLASS_FASC = "registri-class-fasc";
            public const string REGISTRI_RICERCA = "registri-ricerca";
            public const string ETICHETTA = "etichetta";
        }

        private readonly Utente _utente;

        public ProtocolloService(ParametriRegoleInfo par, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(par, logs, Protocollazione.Enum.ContentType.json)
        {
            this._utente = par.Utente;
        }

        internal void AggiornaAnagrafica(AggiornamentoAnagraficaRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.AGGIORNA_ANAGRAFICA, request, RestSharp.Method.Put);

                this.Logs.Info($"Chiamata al web method {Constants.AGGIORNA_ANAGRAFICA} del web service di Protocollazione");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    if (response.Content == null)
                    {
                        this.Logs.Warn("LA RISPOSTA A INSERIMENTO NUOVA ANAGRAFICA E' NULL");
                        return;
                    }

                    this.Logs.InfoFormat("AGGIORNAMENTO ANAGRAFICA {0} ({1}) AVVENUTO CON SUCCESSO", request.IdAnagrafica.DescrizioneAnagrafica, request.IdAnagrafica.CodiceAnagrafica);
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.AGGIORNA_ANAGRAFICA} HA RESTITUITO UN ERRORE PER L'ANAGRAFICA {request.DatiAnagrafica.DescrizioneAnagrafica} (codice {request.DatiAnagrafica.CodiceAnagrafica}). status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AGGIORNAMENTO DELL'ANAGRAFICA {0} ({1}), ERRORE: {2}",
                    request.IdAnagrafica.DescrizioneAnagrafica,
                    request.IdAnagrafica.CodiceAnagrafica,
                    ex.Message), ex);
            }
        }

        internal InterrogaAnagraficaResponse LeggiAnagrafiche(InterrogaAnagraficaRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.INTERROGA_ANAGRAFICA, request);

                this.Logs.Info($"Chiamata al web method {Constants.INTERROGA_ANAGRAFICA} del web service di Protocollazione");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    return JsonSerializer.Deserialize<InterrogaAnagraficaResponse>(response.Content);
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.INTERROGA_ANAGRAFICA} HA RESTITUITO UN ERRORE PER L'ANAGRAFICA {request.DescrizioneAnagrafica} (codice {request.CodiceAnagrafica}). status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA RICERCA DELL'ANAGRAFICA {0}, ERRORE: {1}", request.DescrizioneAnagrafica, ex.Message), ex);
            }
        }

        internal void InserisciAnagrafica(NuovaAnagraficaRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.NUOVA_ANAGRAFICA, request);

                this.Logs.Info($"Chiamata al web method {Constants.NUOVA_ANAGRAFICA} del web service di Protocollazione");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    if (response.Content == null)
                    {
                        this.Logs.Warn("LA RISPOSTA A INSERIMENTO NUOVA ANAGRAFICA E' NULL");
                        return;
                    }

                    this.Logs.InfoFormat("INSERIMENTO ANAGRAFICA {0} ({1}) AVVENUTO CON SUCCESSO", request.DatiAnagrafica.DescrizioneAnagrafica, request.DatiAnagrafica.CodiceAnagrafica);
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.NUOVA_ANAGRAFICA} HA RESTITUITO UN ERRORE PER L'ANAGRAFICA {request.DatiAnagrafica.DescrizioneAnagrafica} (codice {request.DatiAnagrafica.CodiceAnagrafica}). status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DELL'ANAGRAFICA {0}, ERRORE: {1}", request.DatiAnagrafica.DescrizioneAnagrafica, ex.Message), ex);
            }
        }

        internal PredisponiAnagraficaResponse PredisponiAnagrafica(PredisponiAnagraficaRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.PREDISPONI_ANAGRAFICA, request);

                this.Logs.Info($"Chiamata al web method {Constants.PREDISPONI_ANAGRAFICA} del web service di Protocollazione");
                this.Logs.Info($"parametri della request: {Utility.NameValueCollectionToString(rr.Parameters.ToList())}");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var predisponiAnagraficaResponse = JsonSerializer.Deserialize<PredisponiAnagraficaResponse>(response.Content);
                    this.Logs.InfoFormat($"IL METODO {Constants.PREDISPONI_ANAGRAFICA} É STATO ESEGUITO CON SUCCESSO");

                    return predisponiAnagraficaResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.PREDISPONI_ANAGRAFICA} HA RESTITUITO UN ERRORE PER L'ANAGRAFICA {request.Anagrafica.Denominazione} (codice fiscale {request.Anagrafica.CodiceFiscale}). status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format($"ERRORE GENERATO DURANTE L'ESECUZIONE DEL METODO {Constants.PREDISPONI_ANAGRAFICA} DELL'ANAGRAFICA {request.Anagrafica.Denominazione}, ERRORE: {ex.Message}"), ex);
            }
        }

        internal InserimentoProtocolloResponse Protocolla(InserimentoProtocolloRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.INSERISCI_PROTOCOLLO, request);

                this.Logs.Info("Chiamata al web method inserisciProtocollo del web service di Protocollazione");
                this.Logs.Info($"Request: {Utility.NameValueCollectionToString(rr.Parameters.ToList())}");
                var response = this.ExecuteRequest(rr);
                this.Logs.Info($"Response: {response.Content}");

                if (response.IsSuccessful)
                {
                    var inserimentoProtocolloResponse = JsonSerializer.Deserialize<InserimentoProtocolloResponse>(response.Content);

                    this.Logs.Info("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE");
                    return inserimentoProtocolloResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.INSERISCI_PROTOCOLLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE, {0}", ex.Message), ex);
            }
        }

        //TODO da implementare : questa chiamata per ora non è usata
        internal DettagliRegistrazioneProtocollo Riprotocolla(RiprotocollaRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.RIPROTOCOLLA, request);

                this.Logs.Info($"Chiamata al ws {Constants.RIPROTOCOLLA}");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var riprotocollaResponse = JsonSerializer.Deserialize<DettagliRegistrazioneProtocollo>(response.Content);

                    this.Logs.Info("RIPROTOCOLLAZIONE AVVENUTA CORRETTAMENTE");
                    return riprotocollaResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.RIPROTOCOLLA} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format($"IL WEB SERVICE {Constants.RIPROTOCOLLA} HA RESTITUITO IL SEGUENTE ERRORE, {0}", ex.Message), ex);
            }
        }

        //TODO da implementare : questa chiamata per ora non è usata
        internal bool AnnullaProtocollo(AnnullamentoProtocolloRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.ANNULLA_PROTOCOLLO, request);

                this.Logs.Info($"Chiamata al ws {Constants.ANNULLA_PROTOCOLLO}");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    this.Logs.Info("ANNULLAMENTO DEL PROTOCOLLO AVVENUTO CORRETTAMENTE");
                    return true;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.ANNULLA_PROTOCOLLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format($"IL WS {Constants.ANNULLA_PROTOCOLLO} HA RESTITUITO IL SEGUENTE ERRORE, {0}", ex.Message), ex);
            }
        }

        internal DettaglioProtocolloResponse LeggiProtocollo(DettaglioProtocolloRequest request, bool sollevaErrore)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.DETTAGLIO_PROTOCOLLO, request);

                this.Logs.Info($"CHIAMATA AL WEB SERVICE {Constants.DETTAGLIO_PROTOCOLLO}");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var leggiProtocolloResponse = JsonSerializer.Deserialize<DettaglioProtocolloResponse>(response.Content);

                    this.Logs.Info("LETTURA DEL PROTOCOLLO AVVENUTA CORRETTAMENTE");
                    return leggiProtocolloResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);

                    if (err.detail == "impossibile individuare la registrazione" && !sollevaErrore)
                        return null;

                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.DETTAGLIO_PROTOCOLLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE LA LETTURA DEL PROTOCOLLO: {0}", ex.Message), ex);
            }
        }

        //TODO da implementare : questa chiamata per ora non è usata
        internal InterrogaProtocolloResponse InterrogaProtocollo(InterrogaProtocolloRequest request, bool sollevaErrore)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.INTERROGA_PROTOCOLLO, request);

                this.Logs.Info($"CHIAMATA AL WEB SERVICE {Constants.INTERROGA_PROTOCOLLO}");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var leggiProtocolloResponse = JsonSerializer.Deserialize<InterrogaProtocolloResponse>(response.Content);

                    this.Logs.Info("INTERROGAZIONE DEL PROTOCOLLO AVVENUTA CORRETTAMENTE");
                    return leggiProtocolloResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.INTERROGA_PROTOCOLLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE L'INTERROGAZIONE DEL PROTOCOLLO: {0}", ex.Message), ex);
            }
        }

        internal bool AggiornaProtocollo(AggiornamentoProtocolloRequest request, bool sollevaErrore)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.AGGIORNA_PROTOCOLLO, request);
                this.Logs.Info($"Request: {Utility.NameValueCollectionToString(rr.Parameters.ToList())}");
                var response = this.ExecuteRequest(rr);
                this.Logs.Info($"Response: {response.Content}");

                if (response.IsSuccessful)
                {
                    //var infoClassificaResponse = JsonSerializer.Deserialize<InfoClassificaResponse>(response.Content);

                    this.Logs.Info("AGGIORNAMENTO PROTOCOLLO ESEGUITO CON SUCCESSO");
                    return true;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);

                    if (err.detail == "impossibile individuare la registrazione" && !sollevaErrore)
                        return false;

                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.AGGIORNA_PROTOCOLLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO L'AGGIORNAMENTO DEL PROTOCOLLO CON I DATI DI FASCICOLAZIONE, {ex.Message}", ex);
            }
        }

        //TODO da implementare: questa chiamata al momento non è mai usata
        internal RicevutePecResponse GetRicevutePec(RicevutePecRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.RICEVUTE_PEC, request);

                this.Logs.Info($"CHIAMATA AL WEB SERVICE {Constants.RICEVUTE_PEC}");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var ricevutePecResponse = JsonSerializer.Deserialize<RicevutePecResponse>(response.Content);

                    this.Logs.Info($"CHIAMATA AL WS {Constants.RICEVUTE_PEC} AVVENUTA CORRETTAMENTE");
                    return ricevutePecResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.RICEVUTE_PEC} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format($"IL WEB SERVICE {Constants.RICEVUTE_PEC} HA RESTITUITO IL SEGUENTE ERRORE: {0}", ex.Message), ex);
            }
        }

        //TODO da implementare: questa chiamata al momento non è mai usata
        internal DownloadRicevutaPecResponse DownloadRicevutaPec(DownloadRicevutaPecRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.DOWNLOAD_RICEVUTA_PEC, request);

                this.Logs.Info($"CHIAMATA AL WEB SERVICE {Constants.DOWNLOAD_RICEVUTA_PEC}");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var downloadRicevutaPecResponse = JsonSerializer.Deserialize<DownloadRicevutaPecResponse>(response.Content);

                    this.Logs.Info($"CHIAMATA AL WS {Constants.DOWNLOAD_RICEVUTA_PEC} AVVENUTA CORRETTAMENTE");
                    return downloadRicevutaPecResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.DOWNLOAD_RICEVUTA_PEC} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format($"IL WEB SERVICE {Constants.DOWNLOAD_RICEVUTA_PEC} HA RESTITUITO IL SEGUENTE ERRORE: {0}", ex.Message), ex);
            }
        }

        //TODO da implementare: questa chiamata al momento non è mai usata
        internal DownloadBustaPecResponse DownloadBustaPec(DownloadBustaPecRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.DOWNLOAD_BUSTA_PEC, request);

                this.Logs.Info($"CHIAMATA AL WEB SERVICE {Constants.DOWNLOAD_BUSTA_PEC}");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var downloadBustaPecResponse = JsonSerializer.Deserialize<DownloadBustaPecResponse>(response.Content);

                    this.Logs.Info($"CHIAMATA AL WS {Constants.DOWNLOAD_BUSTA_PEC} AVVENUTA CORRETTAMENTE");
                    return downloadBustaPecResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.DOWNLOAD_BUSTA_PEC} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format($"IL WEB SERVICE {Constants.DOWNLOAD_BUSTA_PEC} HA RESTITUITO IL SEGUENTE ERRORE: {0}", ex.Message), ex);
            }
        }

        internal DownloadDocumentoResponse DownloadDocumento(DownloadDocumentoRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.DOWNLOAD_DOCUMENTO, request);
                rr.AddOrUpdateParameter("Accept", "application/octet-stream", ParameterType.HttpHeader);

                this.Logs.InfoFormat("CHIAMATA A DOWNLOAD DOCUMENTO DEL PROTOCOLLO. Utente (codice:codiceFiscale) {0}:{1} ID (progDoc:progMovi) {2}:{3}, DOCUMENTO ID {4}", request.Utente.Codice, request.Utente.CodiceFiscale, request.Registrazione.Id.ProgressivoDocumento, request.Registrazione.Id.ProgressivoMovimento, request.IdDoc);

                var response = this.ExecuteRequest(rr, false);

                if (response.IsSuccessful)
                {
                    var file = response.RawBytes;
                    var md5Header = response.Headers.First(h => h.Name == "X-Impronta-MD5").ToString();
                    var filename = response.Headers.FirstOrDefault(h => h.Name == "Content-Disposition")?.ToString() ?? md5Header;

                    if (filename.Contains("filename"))
                    {
                        var pattern = @"filename=\""([^""]+)\""";
                        Match match = Regex.Match(filename, pattern);

                        if (match.Success)
                        {
                            filename = match.Groups[1].Value;
                        }
                    }

                    this.Logs.InfoFormat("LETTURA DEL DOCUMENTO AVVENUTA CORRETTAMENTE");
                    return new DownloadDocumentoResponse
                    {
                        File = file,
                        ImprontaMd5 = md5Header,
                        NomeFile = filename
                    };
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.DOWNLOAD_DOCUMENTO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO AL DOWNLOAD DEL DOCUMENTO: {0}", ex.Message), ex);
            }
        }

        internal TipiDocResponse GetTipiDocumento(TipiDocRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.TIPI_DOCUMENTO, request);

                this.Logs.Info("Chiamata al web service gettipidocumento");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var tipiDocResponse = JsonSerializer.Deserialize<TipiDocResponse>(response.Content);

                    this.Logs.Info("LETTURA DEI TIPI DOCUMENTO AVVENUTA CORRETTAMENTE");

                    return tipiDocResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.TIPI_DOCUMENTO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE IL RECUPERO DELLE TIPOLOGIE DI DOCUMENTO, {0}", ex.Message), ex);
            }
        }

        internal DettaglioFascicoloResponse GetFascicolo(DettaglioFascicoloRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.DETTAGLIO_FASCICOLO, request);

                this.Logs.Info($"RECUPERO DEL FASCICOLO, CHIAMATA A {Constants.DETTAGLIO_FASCICOLO}");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var dettagliFascicoloResponse = JsonSerializer.Deserialize<DettaglioFascicoloResponse>(response.Content);

                    this.Logs.Info($"RECUPERO DEL FASCICOLO, {Constants.DETTAGLIO_FASCICOLO} AVVENUTA CON SUCCESSO");
                    return dettagliFascicoloResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.DETTAGLIO_FASCICOLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format($"ERRORE GENERATO DURANTE IL RECUPERO DEL FASCICOLO, {ex.Message}", ex));
            }
        }

        internal bool VerificaAbilitazioneFascicolazione(AbilitazioneAperturaFascicoloRequest request)
        {
            try
            {
                var abilAperturaFascicoloResponse = new AbilitazioneAperturaFascicoloResponse();
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.ABIL_APERTURA_FASCICOLO, request);

                this.Logs.InfoFormat($"VERIFICA ABILITAZIONI PER APERTURA FASCICOLO, CHIAMATA A {Constants.ABIL_APERTURA_FASCICOLO}, request: {0}", rr);

                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    abilAperturaFascicoloResponse = JsonSerializer.Deserialize<AbilitazioneAperturaFascicoloResponse>(response.Content);
                    this.Logs.Info("LETTURA ABILITAZIONE APERTURA FASCICOLO ESEGUITA CON SUCCESSO");
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.ABIL_APERTURA_FASCICOLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }

                return abilAperturaFascicoloResponse.Abilitato;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE L'ESECUZIONE DEL WS {Constants.ABIL_APERTURA_FASCICOLO}: {ex.Message}");
            }
        }

        internal AperturaFascicoloResponse CreaFascicolo(AperturaFascicoloRequest request)
        {
            try
            {
                request.Utente = this._utente;

                var rr = this.InitRequest(Constants.APERTURA_FASCICOLO, request);

                this.Logs.InfoFormat("CREAZIONE DEL FASCICOLO, CHIAMATA A {0}, request: {1}", Constants.APERTURA_FASCICOLO, Utility.NameValueCollectionToString(rr.Parameters.ToList()));
                var response = this.ExecuteRequest(rr);
                this.Logs.Info($"Response: {response.Content}");

                if (response.IsSuccessful)
                {
                    var aperturaFascicoloResponse = JsonSerializer.Deserialize<AperturaFascicoloResponse>(response.Content);
                    this.Logs.InfoFormat("CREAZIONE DEL FASCICOLO AVVENUTA CON SUCCESSO, NUMERO FASCICOLO: {0}, SUBNUMERO: {1}, ANNO FASCICOLO: {2}, PROGDOC: {3}, PROGMOVI: {4}, DATA APERTURA: {5}, ANNO: {6}, CODICE REGISTRO: {7}, CODICE UFFICIO: {8}", aperturaFascicoloResponse.Numero, aperturaFascicoloResponse.SubNumero, aperturaFascicoloResponse.Anno, aperturaFascicoloResponse.ProgDoc, aperturaFascicoloResponse.ProgMovi, aperturaFascicoloResponse.DataApertura?.ToString("dd/MM/yyyy"), aperturaFascicoloResponse.Anno, aperturaFascicoloResponse.CodiceRegistro, aperturaFascicoloResponse.CodiceUfficio);

                    return aperturaFascicoloResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.APERTURA_FASCICOLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }

            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA CREAZIONE DEL FASCICOLO, {ex.Message}", ex);
            }
        }

        //TODO da implementare: questa chiamata al momento non è mai usata
        internal AperturaFascicoloResponse RiprotocollaFascicolo(RiprotocollaFascicoloRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.RIPROTOCOLLA_FASCICOLO, request);

                this.Logs.InfoFormat("RIPROTOCOLLAZIONE DEL FASCICOLO, CHIAMATA A {0}, request: {1}", Constants.RIPROTOCOLLA_FASCICOLO, request);

                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var riprotocollaFascicoloResponse = JsonSerializer.Deserialize<AperturaFascicoloResponse>(response.Content);
                    this.Logs.InfoFormat("RIPROTOCOLLAZIONE DEL FASCICOLO AVVENUTA CON SUCCESSO, NUMERO FASCICOLO: {0}, SUBNUMERO: {1}, ANNO FASCICOLO: {2}, PROGDOC: {3}, PROGMOVI: {4}, DATA APERTURA: {5}, ANNO: {6}, CODICE REGISTRO: {7}, CODICE UFFICIO: {8}", riprotocollaFascicoloResponse.Numero, riprotocollaFascicoloResponse.SubNumero, riprotocollaFascicoloResponse.Anno, riprotocollaFascicoloResponse.ProgDoc, riprotocollaFascicoloResponse.ProgMovi, riprotocollaFascicoloResponse.DataApertura?.ToString("dd/MM/yyyy"), riprotocollaFascicoloResponse.Anno, riprotocollaFascicoloResponse.CodiceRegistro, riprotocollaFascicoloResponse.CodiceUfficio);

                    return riprotocollaFascicoloResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.RIPROTOCOLLA_FASCICOLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }

            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA RIPROTOCOLAZIONE DEL FASCICOLO, {ex.Message}", ex);
            }
        }

        internal DettagliFascicolo InterrogaFascicoli(InterrogaFascicoliRequest request)
        {
            try
            {
                var interrogaFascicoliResponse = new InterrogaFascicoliResponse();
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.INTERROGA_FASCICOLI, request);

                this.Logs.InfoFormat($"INTERROGAZIONE DEL FASCICOLO, CHIAMATA A {Constants.INTERROGA_FASCICOLI}, request: {0}", Utility.NameValueCollectionToString(rr.Parameters.ToList()));
                var response = this.ExecuteRequest(rr);
                this.Logs.Info($"Response: {response.Content}");

                if (response.IsSuccessful)
                {
                    interrogaFascicoliResponse = JsonSerializer.Deserialize<InterrogaFascicoliResponse>(response.Content);

                    this.Logs.Info($"INTERROGAZIONE DEL FASCICOLO, CHIAMATA A {Constants.INTERROGA_FASCICOLI} AVVENUTA CON SUCCESSO");

                    if (Convert.ToInt32(interrogaFascicoliResponse.NumFasc) == 0)
                    {
                        throw new Exception("NON E' STATO TROVATO ALCUN FASCICOLO CON IL CRITERIO SELEZIONATO");
                    }

                    if (interrogaFascicoliResponse.Fascicoli.Length > 1)
                    {
                        this.Logs.Warn("ATTENZIONE, SONO STATI RESTITUITI PIU' FASCICOLI DALLA RICERCA");
                    }

                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.INTERROGA_FASCICOLI} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }

                if (interrogaFascicoliResponse.Fascicoli != null)
                    return interrogaFascicoliResponse.Fascicoli[0];
                else
                    return null;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE L'INTERROGAZIONE DEL FASCICOLO, {ex.Message}", ex);
            }

        }

        //TODO da implementare: questa chiamata al momento non è mai usata
        internal AggiornaFascicoloResponse AggiornaFascicolo(AggiornaFascicoloRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.AGGIORNA_FASCICOLO, request);

                this.Logs.InfoFormat("CHIAMATA AL WS {0}, request: {1}", Constants.AGGIORNA_FASCICOLO, request);

                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var aggiornaFascicoloResponse = JsonSerializer.Deserialize<AggiornaFascicoloResponse>(response.Content);
                    this.Logs.InfoFormat("AGGIORNAMENTO DEL FASCICOLO AVVENUTO CON SUCCESSO");

                    return aggiornaFascicoloResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.AGGIORNA_FASCICOLO} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }

            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE L'AGGIORNAMENTO DEL FASCICOLO, {ex.Message}", ex);
            }
        }

        internal InfoClassificaResponse GetClassifiche(InfoClassificaRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.INFO_CLASSIFICA, request);

                this.Logs.Info("Chiamata al web service getClassifiche");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var infoClassificaResponse = JsonSerializer.Deserialize<InfoClassificaResponse>(response.Content);

                    this.Logs.Info("LETTURA DELLE CLASSIFICHE AVVENUTA CORRETTAMENTE");
                    return infoClassificaResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.INFO_CLASSIFICA} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE IL RECUPERO DELLE CLASSIFICHE, {0}", ex.Message), ex);
            }
        }

        internal RegistriDaClassificaFascicoliResponse GetRegistri(RegistriDaClassificaFascicoliRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.REGISTRI_CLASS_FASC, request);

                this.Logs.Info("Chiamata al web service registri-class-fasc");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var registriDaClassificaFascicoliResponse = JsonSerializer.Deserialize<RegistriDaClassificaFascicoliResponse>(response.Content);

                    this.Logs.Info("LETTURA DEI REGISTRI AVVENUTA CORRETTAMENTE");
                    return registriDaClassificaFascicoliResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.REGISTRI_CLASS_FASC} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE IL RECUPERO DEI REGISTRI, {0}", ex.Message), ex);
            }
        }

        internal RicercaRegistriResponse RicercaRegistri(RicercaRegistriRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.REGISTRI_RICERCA, request);

                this.Logs.Info("Chiamata al web service registri-ricerca");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var ricercaRegistriResponse = JsonSerializer.Deserialize<RicercaRegistriResponse>(response.Content);

                    this.Logs.Info("RICERCA DEI REGISTRI AVVENUTA CORRETTAMENTE");
                    return ricercaRegistriResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.REGISTRI_RICERCA} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE LA RICERCA DEI REGISTRI, {0}", ex.Message), ex);
            }
        }

        internal EtichettaResponse GetEtichetta(EtichettaRequest request)
        {
            try
            {
                request.Utente = this._utente;
                var rr = this.InitRequest(Constants.ETICHETTA, request);

                this.Logs.Info("Chiamata al web service etichetta");
                var response = this.ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var etichettaResponse = new EtichettaResponse() { EtichettaPdf = Convert.FromBase64String(response.Content) };

                    this.Logs.Info("LETTURA DELL'ETICHETTA AVVENUTA CORRETTAMENTE");
                    return etichettaResponse;
                }
                else
                {
                    var err = JsonSerializer.Deserialize<BasicBadRestResponse>(response.Content);
                    throw new Exception($"LA RISPOSTA DEL WEB SERVICE {Constants.ETICHETTA} HA RESTITUITO UN ERRORE. status {err.status}, instance {err.instance}, title {err.title}, details {err.detail}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("IL WEB SERVICE DI GetEtichetta HA RESTITUITO IL SEGUENTE ERRORE DURANTE IL RECUPERO DELL'ETICHETTA, {0}", ex.Message), ex);
            }
        }
    }
}
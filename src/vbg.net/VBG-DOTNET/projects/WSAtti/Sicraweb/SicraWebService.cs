using Newtonsoft.Json;
using SicrawebServiceReference;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.ServiceModel;
using WSAtti.Sicraweb.AggiungiAllegatiString;
using WSAtti.Sicraweb.ElencoFirmatari;
using WSAtti.Sicraweb.InserisciDeterminaString;
using WSAtti.Sicraweb.LeggiAttoString;
using WSAtti.Sicraweb.NumeraDeterminaString;
using WSAtti.Utils;

namespace WSAtti.Sicraweb
{
    public class SicraWebService : IWSAttiService
    {
        private readonly WSAttiConfigurazione _configurazione;
        private readonly string _tipoRegistroProposta = "PR";
        public SicraWebService(WSAttiConfigurazione configurazione)
        {
            this._configurazione = configurazione;
        }

        public static string Nome => "SICRAWEB";
        public WSAttiAggiungiAllegatoResponse AggiungiAllegato(WSAttiAggiungiAllegatoRequest request)
        {
            try
            {
                var ws = this.CreateService(this._configurazione.Url);

                var wsRequest = AggiungiAllegatiStringWSRequest.FromWSAttiAggiungiAllegatoRequest(request);

                var responseString = ws.AggiungiAllegatiString(wsRequest.NuoviAllegatiStr, this._configurazione.CodiceAmministrazione, null);

                var response = AggiungiAllegatiStringWSResponse.FromXMLAggiungiAllegatiString(responseString);

                if (!String.IsNullOrEmpty(response.AllegatiInseriti.Errore))
                {
                    throw new Exception($"Errore tornato dal WS: {response.AllegatiInseriti.Errore}; Messaggio: {response.AllegatiInseriti.Messaggio}");
                }

                return new WSAttiAggiungiAllegatoResponse
                {
                    Esito = WSEsito.FromOK(),
                    Id = response.AllegatiInseriti.IdDocumento,
                    IdAllegato = response.AllegatiInseriti.Allegati.Any() ? response.AllegatiInseriti.Allegati[0].Serial : null
                };
            }
            catch (Exception ex)
            {
                return new WSAttiAggiungiAllegatoResponse
                {
                    Esito = WSEsito.FromKO(ex.Message)
                };
            }
        }

        public WsAttiElencoFirmatariResponse ElencoFirmatari()
        {
            var client = new RestClient
            {
                EndPoint = this._configurazione.UrlFirmatari,
                Method = HttpVerb.GET,
                ContentType = "application/json"
            };

            var jsonResponse = client.MakeRequest();

            var response = JsonConvert.DeserializeObject<ElencoFirmatariResponse>(jsonResponse);

            return WsAttiElencoFirmatariResponse.fromElencoFirmatariResponse(response);
        }

        public WSAttiInserisciDeterminaResponse InserisciDetermina(WSAttiInserisciDeterminaRequest request)
        {
            try
            {
                var ws = this.CreateService(this._configurazione.Url);

                var wsRequest = InserisciDeterminaStringWSRequest.FromInserisciDeterminaRequest(request);

                var responseString = ws.InserisciDeterminaString(wsRequest.DeterminaInStr, wsRequest.CodiceAmministrazione, wsRequest.CodiceAOO);

                var response = InserisciDeterminaStringWSResponse.FromXMLInserisciDeterminaString(responseString);

                if (!String.IsNullOrEmpty(response.AttoInserito.Errore))
                {
                    throw new Exception($"Eccezione durante la chiamata al servizio di gestione degli atti. Errore: {response.AttoInserito.Errore}, Messaggio: {response.AttoInserito.Messaggio}");
                }

                return new WSAttiInserisciDeterminaResponse
                {
                    Esito = WSEsito.FromOK(),
                    Id = response.AttoInserito.IdDocumento,
                    Numero = response.AttoInserito.Numero,
                    Anno = response.AttoInserito.Anno,
                };
            }
            catch (Exception ex)
            {
                return new WSAttiInserisciDeterminaResponse
                {
                    Esito = WSEsito.FromKO(ex.Message)
                };
            }
        }

        public WSAttiLeggiDeterminaResponse LeggiDetermina(WSAttiLeggiDeterminaRequest request)
        {
            try
            {
                var ws = this.CreateService(this._configurazione.Url);

                var wsRequest = LeggiAttoStringWSRequest.FromWSAttiLeggiAttoRequest(request);

                var responseString = ws.LeggiAttoString(wsRequest.IdDocumento, null, null, wsRequest.Anno, wsRequest.Numero, this._configurazione.Utente, this._configurazione.Ruolo, this._configurazione.CodiceAmministrazione, null);

                var response = LeggiAttoStringWSResponse.FromXMLLeggiAttoString(responseString);

                if (!String.IsNullOrEmpty(response.Atto.Errore))
                {
                    throw new Exception($"Eccezione durante la chiamata al servizio di lettura degli atti. Errore: {response.Atto.Errore}, Messaggio: {response.Atto.Messaggio}");
                }

                var determina = new WSAttiLeggiDeterminaResponse
                {
                    Esito = WSEsito.FromOK(),
                    Id = response.Atto.IdDocumento,
                    CodiceClassifica = response.Atto.Classifica,
                    Classifica = response.Atto.Classifica_Descrizione,
                    NumeroProposta = response.Atto.Registri.Registri.First(x => x.TipoRegistro == this._tipoRegistroProposta).NumeroRegistro.ToString(),
                    AnnoProposta = response.Atto.Registri.Registri.First(x => x.TipoRegistro == this._tipoRegistroProposta).DataRegistro.Value.Year,
                    UfficioProponente = response.Atto.Determina.Proponente_Descrizione,
                    StrutturaProponente = response.Atto.Determina.Dirigente_Descrizione,
                    Dirigente = response.Atto.Determina.Dirigente_Descrizione,
                    Oggetto = response.Atto.Oggetto,
                    NumeroAtto = response.Atto.Determina.Numero.ToString(),
                    DataAtto = response.Atto.Determina.Data,
                    AnnoAtto = response.Atto.Determina.Anno,
                };

                if (response.Atto.Allegati == null || response.Atto.Allegati.Allegati == null)
                {
                    return determina;
                }

                //1. Riconciliazione file principale

                var allegatoPrincipaleInviato = response
                                                    .Atto
                                                    .Allegati
                                                    .Allegati
                                                    .First(x => x.Principale && x.CodiceOggetto != null);

                var allegatoPrincipaleFirmato = response
                                    .Atto
                                    .Allegati
                                    .Allegati
                                    .Find(x => x.Principale && x.CodiceOggetto == null);

                var allegati = new List<WSAllegatoAtto>();

                if (allegatoPrincipaleFirmato != null)
                {
                    allegati.Add
                    (

                        new WSAllegatoAtto
                        {
                            IdEsterno = allegatoPrincipaleFirmato.Serial,
                            IdVBG = allegatoPrincipaleInviato.Commento, //nel commento viene passato il codice oggetto in fase di inserimento allegati per poterli riagganciare,
                            Image = allegatoPrincipaleFirmato.Image,
                            Nome = allegatoPrincipaleFirmato.NomeAllegato
                        }
                    );
                }
                //2. Aggiunta allegati secondari
                allegati.AddRange(response
                                    .Atto
                                    .Allegati
                                    .Allegati
                                    .Where(x => !x.Principale)
                                    .Select(x => new WSAllegatoAtto
                                    {
                                        IdEsterno = x.Serial,
                                        IdVBG = (x.CodiceOggetto?.ToString()),
                                        Image = x.Image,
                                        Nome = x.NomeAllegato
                                    })
                                    );
                determina.Allegati = allegati;

                return determina;
            }
            catch (Exception ex)
            {
                return new WSAttiLeggiDeterminaResponse
                {
                    Esito = WSEsito.FromKO(ex.Message)
                };
            }
        }

        public WSAttiNumeraDeterminaResponse NumeraDetermina(WSAttiNumeraDeterminaRequest request)
        {
            try
            {
                var ws = this.CreateService(this._configurazione.Url);

                var wsRequest = NumeraDeterminaStringWSRequest.FromWSAttiNumeraDeterminaRequest(request);

                var responseString = ws.NumeraDeterminaString(wsRequest.IdDocumento, this._configurazione.Utente, this._configurazione.Ruolo, this._configurazione.CodiceAmministrazione, null, null);

                var response = NumeraDeterminaStringWSResponse.FromXMLNumeraDeterminaString(responseString);

                if (!String.IsNullOrEmpty(response.Numerazione.Errore))
                {
                    throw new Exception($"Eccezione durante la chiamata al servizio di gestione degli atti. Errore: {response.Numerazione.Errore}, Messaggio: {response.Numerazione.Messaggio}");
                }

                return new WSAttiNumeraDeterminaResponse
                {
                    Esito = WSEsito.FromOK(),
                    Id = response.Numerazione.IdDocumento,
                    Numero = response.Numerazione.Numero,
                    Anno = response.Numerazione.Anno,
                    Data = response.Numerazione.Data
                };
            }
            catch (Exception ex)
            {
                return new WSAttiNumeraDeterminaResponse
                {
                    Esito = WSEsito.FromKO(ex.Message)
                };
            }
        }

        private WSattiSoapClient CreateService(string url)
        {
            var endPoint = new EndpointAddress(url);
#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif
            ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
            if (url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            return new WSattiSoapClient(binding, endPoint);
        }
    }
}

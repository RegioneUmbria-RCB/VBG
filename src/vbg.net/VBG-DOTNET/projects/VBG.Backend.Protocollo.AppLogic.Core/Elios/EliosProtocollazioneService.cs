using EliosWSProtocollazioneSoapClient;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class EliosProtocollazioneService
    {
        private readonly Parametri _parametri;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloLogs _logs;
        private readonly IBindingFactory _bindingFactory;
        private readonly ClientProtocollazioneServiceCreator _clientProtocollazioneServiceCreator;

        public EliosProtocollazioneService(Parametri parametri, ProtocolloSerializer serializer, ProtocolloLogs logs, IBindingFactory bindingFactory)
        {
            this._parametri = parametri;
            this._serializer = serializer;
            this._logs = logs;
            this._bindingFactory = bindingFactory;
            this._clientProtocollazioneServiceCreator = new ClientProtocollazioneServiceCreator(logs, bindingFactory, this._parametri.UrlProtocollazione);
        }

        public InsertResponse Insert(InsertRequest request)
        {
            try
            {
                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    var wsRequest = new wReProtocollo
                    {
                        Anagrafiche = request
                                        .Anagrafiche
                                        .Select(x => new wReAnagrafica
                                        {
                                            CodiceFiscale = x.CodiceFiscale,
                                            Cognome = x.Cognome,
                                            Email = x.Email,
                                            Nome = x.Nome,
                                            PartitaIva = x.PartitaIVA,
                                            RagioneSociale = x.RagioneSociale,
                                            Tipologia = x.TipoPersona
                                        })
                                        .ToArray(),
                        AreaOmogenea = this._parametri.AreaOmogenea,
                        Categoria = request.Titolario.Categoria,
                        Classe = request.Titolario.Classe,
                        FascicoloSpecified = false,
                        Oggetto = request.Oggetto,
                        Sottoclasse = request.Titolario.Sottoclasse,
                        Tipo = request.Tipo,
                        Uffici = request
                                    .Uffici
                                    .Select(x => new wReUfficio
                                    {
                                        Id = x.Id,
                                        Tipo = x.Tipo
                                    })
                                    .ToArray()
                    };

                    this._serializer.LogAndValidate("wReProtocolloRequest.xml", wsRequest);

                    var response = ws.Service.Insert(this._parametri.Token, this._parametri.Ente, wsRequest);

                    this._serializer.LogAndValidate("wReProtocolloResponse.xml", response);

                    return InsertResponse.FromWSResponse(response);
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public void AggiungiAllegato(AggiungiAllegatoRequest request)
        {
            try
            {
                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    var response = ws.Service.AggiungiAllegato(this._parametri.Token, this._parametri.Ente, request.Anno, request.Numero, this._parametri.AreaOmogenea, new wReAllegato
                    {
                        Contenuto = Convert.ToBase64String(request.Contenuto),
                        Estensione = request.Estensione,
                        Primario = request.Primario ? 1 : 0,
                        Nome = request.NomeFile
                    });

                    if (response == null)
                    {
                        throw new Exception("La risposta ottenuta dal WS è nulla");
                    }

                    if (response.Esito != 0)
                    {
                        throw new Exception($"{response.MessaggioEsito} Codice: {response.Esito}");
                    }
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public void Fascicola(FascicolaRequest request)
        {

            try
            {
                var id = request.Id;

                if (!id.HasValue)
                {
                    var response = new EliosFascicolazioneService(this._parametri, this._serializer, this._bindingFactory,this._logs).Fascicola(request);
                    id = response.Id;
                }
                //aggancio del protocollo al fascicolo

                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {

                    var wsRequest = new wReFascicolo
                    {
                        Id = id.Value,
                    };

                    this._serializer.LogAndValidate("FascicolaProtocolloRequest.xml", wsRequest);

                    var responseFascicola = ws.Service.Fascicola(
                                                this._parametri.Token,
                                                this._parametri.Ente,
                                                request.AnnoProtocollo,
                                                request.NumeroProtocollo,
                                                this._parametri.AreaOmogenea,
                                                wsRequest
                                            );

                    if (responseFascicola == null)
                    {
                        throw new Exception("La risposta ottenuta dal WS è nulla");
                    }

                    if (responseFascicola.Esito != 0)
                    {
                        throw new Exception($"{responseFascicola.MessaggioEsito} Codice: {responseFascicola.Esito}");
                    }
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public GetResponse Get(GetRequest request)
        {
            try
            {
                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    var response = ws.Service.Get(this._parametri.Token, this._parametri.Ente, request.Anno, request.Numero, this._parametri.AreaOmogenea, request.ModalitaRecuperoAllegati);

                    return GetResponse.FromWSResponse(response);
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public GetAllegatoResponse GetAllegato(GetAllegatoRequest request)
        {
            try
            {
                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    var response = ws.Service.GetAllegato(this._parametri.Token, this._parametri.Ente, request.Key);

                    return GetAllegatoResponse.FromWSResponse(response);
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public void SendMail(SendMailRequest request)
        {
            try
            {
                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    var response = ws.Service.SendMail(this._parametri.Token, this._parametri.Ente, request.Anno, request.Numero, this._parametri.AreaOmogenea);

                    if (response == null)
                    {
                        throw new Exception("La risposta ottenuta dal WS è nulla");
                    }

                    if (response.Esito != 0)
                    {
                        throw new Exception($"{response.MessaggioEsito} Codice: {response.Esito}");
                    }
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        internal void CambiaFascicolo(CambiaFascicoloRequest request)
        {
            var searchRequest = new CercaFascicoloRequest
            {
                Anno = request.Anno,
                Numero = request.Numero,
                Classifica = request.Classifica,
                Livello = request.Livello
            };

            var searchResponse = new EliosFascicolazioneService(this._parametri, this._serializer, this._bindingFactory, this._logs).Search(searchRequest);

            if (searchResponse.Fascicoli == null || searchResponse.Fascicoli.Count() != 1)
            {
                throw new Exception("Impossibile identificare univocamente un fascicolo");
            }

            var fascicolo = searchResponse.Fascicoli.First();

            using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
            {
                var wsRequest = new wReFascicolo
                {
                    Id = fascicolo.Id
                };

                this._serializer.LogAndValidate("FascicolaProtocolloRequest.xml", wsRequest);

                var responseFascicola = ws.Service.Fascicola(
                                            this._parametri.Token,
                                            this._parametri.Ente,
                                            request.AnnoProtocollo,
                                            request.NumeroProtocollo,
                                            this._parametri.AreaOmogenea,
                                            wsRequest
                                        );

                if (responseFascicola == null)
                {
                    throw new Exception("La risposta ottenuta dal WS è nulla");
                }

                if (responseFascicola.Esito != 0)
                {
                    throw new Exception($"{responseFascicola.MessaggioEsito} Codice: {responseFascicola.Esito}");
                }
            }
        }

    }
}

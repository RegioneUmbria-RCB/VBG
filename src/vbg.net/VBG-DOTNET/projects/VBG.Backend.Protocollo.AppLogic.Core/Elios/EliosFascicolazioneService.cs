using EliosWSConfigurazioneSoapClient;
using EliosWSFascicolazioneSoapClient;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class EliosFascicolazioneService
    {
        private readonly Parametri _parametri;
        private readonly ProtocolloSerializer _serializer;
        private readonly ClientFascicolazioneServiceCreator _clientFascicolazioneServiceCreator;
        public EliosFascicolazioneService(Parametri parametri, ProtocolloSerializer serializer, IBindingFactory bindingFactory, ProtocolloLogs logs)
        {
            this._parametri = parametri;
            this._serializer = serializer;
            this._clientFascicolazioneServiceCreator = new ClientFascicolazioneServiceCreator(logs, bindingFactory, this._parametri.UrlFascicolazione);
        }

        public FascicolaResponse Fascicola(FascicolaRequest request)
        {
            try
            {
                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
                {
                    var wsRequest = new wReFascicolo
                    {
                        Anno = request.AnnoFascicolo,
                        AreaOmogenea = this._parametri.AreaOmogenea,
                        Categoria = request.Categoria,
                        Classe = request.Classe,
                        Sottoclasse = request.Sottoclasse,
                        Livello = request.LivelloFascicolo,
                        Descrizione = request.Descrizione,
                    };

                    if (request.IdPadre.HasValue)
                    {
                        wsRequest.IdPadre = request.IdPadre.Value;
                    }

                    this._serializer.LogAndValidate("FascicoloInsertRequest.xml", wsRequest);

                    var response = ws.Service.Insert(
                                        this._parametri.Token,
                                        this._parametri.Ente,
                                        wsRequest
                                    );

                    return FascicolaResponse.FromWReFascicolo(response);

                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        internal CercaFascicoloResponse SearchById(int id)
        {
            try
            {
                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
                {
                    var wsRequest = new wReFascicolo
                    {
                        Id = id
                    };

                    this._serializer.LogAndValidate("FascicoloSearchByIdRequest.xml", wsRequest);

                    var response = ws.Service.Search(
                                        this._parametri.Token,
                                        this._parametri.Ente,
                                        wsRequest);

                    return CercaFascicoloResponse.FromWReFascicolo(response);
                }
            }
            catch (Exception ex)
            {

                throw ex;
            }
        }
        internal CercaFascicoloResponse Search(CercaFascicoloRequest request)
        {
            try
            {
                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
                {
                    var wsRequest = new wReFascicolo
                    {
                        Descrizione = request.Descrizione,
                    };

                    if (request.IdPadre.HasValue)
                    {
                        wsRequest.IdPadre = request.IdPadre.Value;
                    }

                    if (request.Livello.HasValue)
                    {
                        wsRequest.Livello = request.Livello.Value;
                    }

                    if (request.Anno.HasValue)
                    {
                        wsRequest.Anno = request.Anno.Value;
                    }

                    if (request.Numero.HasValue)
                    {
                        wsRequest.Numero = request.Numero.Value;
                    }

                    if (!String.IsNullOrEmpty(request.Classifica))
                    {
                        var c = request.Classifica.Split('.');

                        wsRequest.Categoria = c[0];
                        wsRequest.Classe = c.Length > 1 ? c[1] : null;
                        wsRequest.Sottoclasse = c.Length > 2 ? c[2] : null;
                    }

                    this._serializer.LogAndValidate("FascicoloSearchRequest.xml", wsRequest);

                    var response = ws.Service.Search(
                                        this._parametri.Token,
                                        this._parametri.Ente,
                                        wsRequest);

                    return CercaFascicoloResponse.FromWReFascicolo(response);
                }
            }
            catch (Exception ex)
            {

                throw ex;
            }
        }
    }
}

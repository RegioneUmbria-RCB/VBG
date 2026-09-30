using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloKibernetesV2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V2
{
    public class LeggiProtocolloV2Service : ILeggiProtocolloService
    {
        private readonly ILog _logger;
        private readonly IProtocolloSerializer _serializer;
        private readonly IParametriService _parametriService;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public LeggiProtocolloV2Service(IParametriService parametriService, ILog logger, IProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _logger = logger;
            _serializer = serializer;
            _parametriService = parametriService;
            _protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, parametriService.Url);
        }

        public DatiProtocolloLettoResponseType LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, DateTime? dataProtocollo)
        {
            if (String.IsNullOrEmpty(annoProtocollo) && !dataProtocollo.HasValue)
            {
                throw new Exception("Non è possibile risalire all'anno del protocollo");
            }

            this._logger.Debug($"Inizio lettura protocollo numero {numeroProtocollo} e anno {annoProtocollo}");

            using (var ws = this._protocolloClientServiceCreator.CreateClient())
            {
                try
                {
                    var authInfo = new AuthInfo
                    {
                        NomeUtente = this._parametriService.UserName,
                        Password = this._parametriService.Password
                    };

                    var dati = new ProtocolloDatiFilterArgs
                    {
                        Anno = String.IsNullOrEmpty(annoProtocollo) ? dataProtocollo.Value.Year : Convert.ToInt32(annoProtocollo),
                        NumeroDa = Convert.ToInt32(numeroProtocollo),
                        NumeroA = Convert.ToInt32(numeroProtocollo),
                    };

                    this._serializer.LogAndValidate("AuthInfoRequest.xml", authInfo);
                    this._serializer.LogAndValidate("ProtocolloDatiFilterArgsRequest.xml", dati);

                    var response = ws.Service.GetProtocolliDati(authInfo, dati);

                    // ora non va più in errore nel caso response sia null o vuota
                    if (response == null || response.Length == 0)
                    {
                        this._logger.Debug($"La chiamata a GetProtocolliDati ha restituito null con i seguenti valori Anno:{dati.Anno}, NumeroDa:{dati.NumeroDa}, NumeroA:{dati.NumeroA}");
                        return new DatiProtocolloLettoResponseType();
                    }
                    else
                    {
                        this._serializer.LogAndValidate("GetProtocolliDatiResponse.xml", response);
                    }

                    this._logger.Debug($"Fine lettura protocollo numero {numeroProtocollo} e anno {annoProtocollo}");

                    if (response.Length > 1)
                    {
                        throw new Exception($"Impossibile individuare univocamente il protocollo da leggere. Sono stati trovati {response.Length} protocolli con il numero {numeroProtocollo} e anno {annoProtocollo}");
                    }

                    this._logger.Debug($"Inizio lettura allegati del protocollo numero {numeroProtocollo} e anno {annoProtocollo}");

                    var allegati = ws.Service.GetAllegatiProtocollo(authInfo, response[0].Id);

                    if (allegati is not null)
                    {
                        this._serializer.LogAndValidate("GetAllegatiProtocolloResponse.xml", allegati);
                    }

                    this._logger.Debug($"Fine lettura allegati del protocollo numero {numeroProtocollo} e anno {annoProtocollo}");

                    

                    return new DatiProtocolloLettoResponseType
                    {
                        IdProtocollo = response[0].Id.ToString(),
                        AnnoProtocollo = response[0].Anno.ToString(),
                        NumeroProtocollo = response[0].Numero.ToString(),
                        DataProtocollo = response[0].DataOra.ToString("dd/MM/yyyy"),
                        Allegati = allegati?.Any() ?? false
                                    ? allegati
                                        .Select((x, y) => new AllegatoResponseType
                                        {
                                            IDBase = $"{response[0].Id}-{y}",
                                            Serial = x.FileName,
                                            Commento = x.FileName,
                                            TipoFile = Path.GetExtension(x.FileName)
                                        })
                                        .ToArray()
                                    : Array.Empty<AllegatoResponseType>()
                    };

                }
                catch (Exception ex)
                {
                    throw new Exception($"Errore durante la chiamata a CreaUscita: {ex.Message}");
                }
            }
        }
    }
}

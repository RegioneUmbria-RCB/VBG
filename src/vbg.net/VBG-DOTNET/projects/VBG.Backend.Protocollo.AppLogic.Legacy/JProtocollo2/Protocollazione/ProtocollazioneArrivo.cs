using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazioneJProtocollo2
    {
        ProtocollazioneConfiguration _conf;

        public ProtocollazioneArrivo(ProtocollazioneConfiguration conf)
        {
            _conf = conf;
        }

        private inserisciArrivoRichiestaProtocollaArrivo GetRequestArrivo()
        {
            var soggettiAdapter = new ProtocollazioneSoggettiAdapter(_conf.DatiProto);
            var allegatiAdapter = new AllegatiAdapter(_conf.DatiProto.ProtoIn.RecuperaAllegati().ToList());

            var smistamenti = new smistamento[] { new smistamento { corrispondente = new corrispondente { codice = _conf.DatiProto.Uo } } }.ToList();

            if (_conf.DatiProto.AltriDestinatariInterni != null && _conf.DatiProto.AltriDestinatariInterni.Count() > 0)
            {
                var altriSmistamenti = _conf.DatiProto.AltriDestinatariInterni.Select(x => new smistamento { corrispondente = new corrispondente { codice = x.PROT_UO } });
                smistamenti.AddRange(altriSmistamenti);
            }

            var request = new inserisciArrivoRichiestaProtocollaArrivo
            {
                username = _conf.Operatore,                
                protocollaArrivo = new protocollaArrivo
                {
                    oggetto = _conf.DatiProto.ProtoIn.Oggetto,
                    soggetti = new soggetti { Items = soggettiAdapter.Adatta() },
                    smistamenti = smistamenti.ToArray(),
                    altriDati = new altriDati
                    {
                        tramite = new tramite { codice = _conf.DatiProto.ProtoIn.TipoSmistamento },
                        tipoDocumento = new tipoDocumento { codice = _conf.DatiProto.ProtoIn.TipoDocumento }
                    },
                    classificazione = new classificazione { titolario = _conf.DatiProto.ProtoIn.Classifica }
                },
                documento = allegatiAdapter.Adatta()
            };

            return request;
        }

        public DatiProtocolloResponseType Protocolla()
        {
            var request = GetRequestArrivo();
            var response = _conf.Service.ProtocollaArrivo(request);

            var adapterDocumenti = new DocumentiAdapter(_conf.Service, _conf.DatiProto.ProtoIn.RecuperaAllegati().ToList());
            adapterDocumenti.Adatta(response.segnatura.numero, response.segnatura.anno, _conf.Operatore);

            var retVal = new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.segnatura.anno,
                DataProtocollo = response.segnatura.data,
                NumeroProtocollo = response.segnatura.numero
            };

            return retVal;
        }
    }
}

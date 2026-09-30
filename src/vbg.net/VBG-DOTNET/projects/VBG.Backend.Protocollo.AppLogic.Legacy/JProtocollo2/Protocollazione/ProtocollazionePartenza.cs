using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazioneJProtocollo2
    {
        ProtocollazioneConfiguration _conf;

        public ProtocollazionePartenza(ProtocollazioneConfiguration conf)
        {
            _conf = conf;
        }

        private inserisciPartenzaRichiestaProtocollaPartenza GetRequestPartenza()
        {
            var soggettiAdapter = new ProtocollazioneSoggettiAdapter(_conf.DatiProto);
            var allegatiAdapter = new AllegatiAdapter(_conf.DatiProto.ProtoIn.RecuperaAllegati().ToList());

            var request = new inserisciPartenzaRichiestaProtocollaPartenza
            {
                username = _conf.Operatore,
                protocollaPartenza = new protocollaPartenza
                {
                    oggetto = _conf.DatiProto.ProtoIn.Oggetto,
                    mittenteInterno = new mittenteInterno{ corrispondente = new corrispondente{ codice = _conf.DatiProto.Uo } },
                    smistamenti = new smistamento[] { new smistamento { corrispondente = new corrispondente { codice = _conf.DatiProto.Uo } } },
                    soggetti = new soggetti { Items = soggettiAdapter.Adatta() },
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
            var request = GetRequestPartenza();
            var response = _conf.Service.ProtocollaPartenza(request);

            var adapterDocumenti = new DocumentiAdapter(_conf.Service, _conf.DatiProto.ProtoIn.RecuperaAllegati().ToList());
            adapterDocumenti.Adatta(response.segnatura.numero, response.segnatura.anno, _conf.Operatore);

            if (_conf.Vert.InviaPec)
            {
                var requestPec = new inviaProtocolloRichiestaInviaProtocollo
                {
                    username = _conf.Operatore,
                    riferimento = new riferimento
                    {
                        anno = response.segnatura.anno,
                        numero = response.segnatura.numero
                    }
                };

                _conf.Service.InviaPEC(requestPec);
            }

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

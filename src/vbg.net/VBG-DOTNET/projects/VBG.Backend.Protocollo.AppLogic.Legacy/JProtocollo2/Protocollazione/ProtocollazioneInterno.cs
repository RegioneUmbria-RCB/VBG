using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Protocollazione
{
    public class ProtocollazioneInterno : IProtocollazioneJProtocollo2
    {
        
        ProtocollazioneConfiguration _conf;

        public ProtocollazioneInterno(ProtocollazioneConfiguration conf)
        {
            _conf = conf;
        }

        private inserisciInternoRichiestaProtocollaInterno GetRequestInterno()
        {
            var allegatiAdapter = new AllegatiAdapter(_conf.DatiProto.ProtoIn.RecuperaAllegati().ToList());

            var smistamenti = new smistamento[] { new smistamento { corrispondente = new corrispondente { codice = _conf.DatiProto.AmministrazioniProtocollo[0].PROT_UO } } }.ToList();

            if (_conf.DatiProto.AltriDestinatariInterni != null && _conf.DatiProto.AltriDestinatariInterni.Count() > 0)
            {
                var altriSmistamenti = _conf.DatiProto.AltriDestinatariInterni.Select(x => new smistamento { corrispondente = new corrispondente { codice = x.PROT_UO } });
                smistamenti.AddRange(altriSmistamenti);
            }

            var request = new inserisciInternoRichiestaProtocollaInterno
            {
                username = _conf.Operatore,
                protocollaInterno = new protocollaInterno
                {
                    oggetto = _conf.DatiProto.ProtoIn.Oggetto,
                    mittenteInterno = new mittenteInterno { corrispondente = new corrispondente { codice = _conf.DatiProto.Amministrazione.PROT_UO } },
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
            var request = GetRequestInterno();
            var response = _conf.Service.ProtocollaInterno(request);

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

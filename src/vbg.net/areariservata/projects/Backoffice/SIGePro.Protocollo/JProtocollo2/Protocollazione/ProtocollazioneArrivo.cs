using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.JProtocollo2.Services;
using Init.SIGePro.Protocollo.JProtocollo2.Verticalizzazioni;
using Init.SIGePro.Protocollo.JProtocollo2.Proxy;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.JProtocollo2.Protocollazione
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
            var allegatiAdapter = new AllegatiAdapter(_conf.DatiProto.ProtoIn.Allegati);

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

        public DatiProtocolloRes Protocolla()
        {
            var request = GetRequestArrivo();
            var response = _conf.Service.ProtocollaArrivo(request);

            var adapterDocumenti = new DocumentiAdapter(_conf.Service, _conf.DatiProto.ProtoIn.Allegati);
            adapterDocumenti.Adatta(response.segnatura.numero, response.segnatura.anno, _conf.Operatore);

            var retVal = new DatiProtocolloRes
            {
                AnnoProtocollo = response.segnatura.anno,
                DataProtocollo = response.segnatura.data,
                NumeroProtocollo = response.segnatura.numero
            };

            return retVal;
        }
    }
}

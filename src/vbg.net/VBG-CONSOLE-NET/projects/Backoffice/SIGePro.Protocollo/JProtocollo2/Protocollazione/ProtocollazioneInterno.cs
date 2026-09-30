using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.JProtocollo2.Proxy;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.JProtocollo2.Protocollazione
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
            var allegatiAdapter = new AllegatiAdapter(_conf.DatiProto.ProtoIn.Allegati);

            var request = new inserisciInternoRichiestaProtocollaInterno
            {
                username = _conf.Operatore,
                protocollaInterno = new protocollaInterno
                {
                    oggetto = _conf.DatiProto.ProtoIn.Oggetto,
                    mittenteInterno = new mittenteInterno{ corrispondente = new corrispondente { codice = _conf.DatiProto.Amministrazione.PROT_UO } },
                    smistamenti = new smistamento[] { new smistamento { corrispondente = new corrispondente { codice = _conf.DatiProto.AmministrazioniProtocollo[0].PROT_UO } } },
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
            var request = GetRequestInterno();
            var response = _conf.Service.ProtocollaInterno(request);

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

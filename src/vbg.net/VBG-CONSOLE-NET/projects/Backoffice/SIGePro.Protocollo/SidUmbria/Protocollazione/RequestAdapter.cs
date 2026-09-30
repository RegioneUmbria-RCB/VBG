using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using System.Globalization;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class RequestAdapter
    {
        IDatiProtocollo _datiProto;
        VerticalizzazioniConfiguration _vert;
        ResolveDatiProtocollazioneService _datiIstanzaService;
        ProtocolloEnum.TipoProvenienza _provenienza;

        public RequestAdapter(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, ResolveDatiProtocollazioneService datiIstanzaService, ProtocolloEnum.TipoProvenienza provenienza)
        {
            _datiProto = datiProto;
            _vert = vert;
            _datiIstanzaService = datiIstanzaService;
            _provenienza = provenienza;
        }

        public infoProtocollo Adatta()
        {
            var protoFlusso = ProtocollazioneFlussoFactory.Create(_datiProto, _vert.DestinatarioCC);
            var protoIstMov = ProtocollazioneIstanzaMovimentoFactory.Create(_datiIstanzaService);

            var allegati = _datiProto.ProtoIn.Allegati;

            if(_provenienza == ProtocolloEnum.TipoProvenienza.ONLINE)
                allegati = _datiProto.ProtoIn.Allegati.Where(x => x.Extension.EndsWith(".p7m")).ToList();

            var res = new infoProtocollo
            {
                corrispondenti = protoFlusso.GetCorrispondenti(),
                oggetto = _datiProto.ProtoIn.Oggetto,
                idRichiesta = protoIstMov.IdentificativoRichiesta,
                tipo = protoFlusso.Flusso,
                tipologia = _datiProto.ProtoIn.TipoDocumento,
                trasmissione = _datiProto.ProtoIn.TipoSmistamento,
                data = DateTime.Now.ToString("yyyy/MM/dd hh:mm:ss", CultureInfo.InvariantCulture),
                nAllegati = allegati.Count(),
                allegati = AllegatiAdapter.Adatta(allegati)
            };

            return res;
        }
    }
}

using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.EProt;
using Init.SIGePro.Protocollo.EProt.Protocollazione;
using Init.SIGePro.Protocollo.EProt.TipiDocumento;
using Init.SIGePro.Protocollo.EProt.Titolario;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_EPROT : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloEProt(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var proto = ProtocollazioneFactory.Create(datiProto, vert, this.Anagrafiche.First());
            var metadati = proto.GetParametri();
            proto.Valida(metadati, _protocolloLogs);

            var wrapper = new ProtocollazioneService(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password);

            var response = wrapper.Protocolla(metadati, proto.Metodo);

            return new DatiProtocolloRes
            {
                AnnoProtocollo = response[2],
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy"),
                IdProtocollo = response[0],
                NumeroProtocollo = response[1]
            };
        }

        public override ListaTipiDocumento GetTipiDocumento()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloEProt(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var request = new TipiDocumentoRequest(vert);

            var wrapper = new ProtocollazioneService(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password);
            var response = wrapper.GetTipiDocumento(request.Metodo);

            return TipiDocumentoResponseAdapter.Adatta(response);
        }

        public override ListaTipiClassifica GetClassifiche()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloEProt(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            if (!vert.EscludiClassifica)
            {
                var request = new TitolarioRequest(vert);

                var wrapper = new ProtocollazioneService(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password);
                var response = wrapper.GetTitolario(request.Metodo);

                return TitolarioResponseAdapter.Adatta(response);
            }
            return base.GetClassifiche();
        }
    }
}

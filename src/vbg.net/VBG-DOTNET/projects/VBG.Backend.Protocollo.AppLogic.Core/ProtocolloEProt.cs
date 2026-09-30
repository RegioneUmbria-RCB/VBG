using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.EProt;
using VBG.Backend.Protocollo.AppLogic.Core.EProt.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.EProt.TipiDocumento;
using VBG.Backend.Protocollo.AppLogic.Core.EProt.Titolario;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_EPROT : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_EPROT(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloEProt>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var proto = ProtocollazioneFactory.Create(datiProto, vert, this.Anagrafiche.First());
            var metadati = proto.GetParametri();
            proto.Valida(metadati, _protocolloLogs);

            var wrapper = new ProtocollazioneService(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password);

            var response = wrapper.Protocolla(metadati, proto.Metodo);

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response[2],
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy"),
                IdProtocollo = response[0],
                NumeroProtocollo = response[1]
            };
        }

        public override ListaTipiDocumentoResponseType GetTipiDocumento()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloEProt>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var request = new TipiDocumentoRequest(vert);

            var wrapper = new ProtocollazioneService(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password);
            var response = wrapper.GetTipiDocumento(request.Metodo);

            return TipiDocumentoResponseAdapter.Adatta(response);
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloEProt>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
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

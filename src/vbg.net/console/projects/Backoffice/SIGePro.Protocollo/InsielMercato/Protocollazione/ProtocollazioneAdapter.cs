using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.InsielMercato.Verticalizzazioni;
using Init.SIGePro.Protocollo.InsielMercato.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.InsielMercato.Protocollazione.ProtocolliCollegati;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.InsielMercato.LeggiProtocollo;

namespace Init.SIGePro.Protocollo.InsielMercato.Protocollazione
{
    public class ProtocollazioneAdapter
    {
        IDatiProtocollo _datiProto;
        VerticalizzazioniConfiguration _vert;
        string _ufficio;
        string _codiceSequenza;
        ProtocollazioneService _wrapper;
        ResolveDatiProtocollazioneService _datiProtoService;

        public ProtocollazioneAdapter(ProtocollazioneService wrapper, IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, string ufficio, string codiceSequenza, ResolveDatiProtocollazioneService datiProtoService)
        {
            _datiProto = datiProto;
            _ufficio = ufficio;
            _vert = vert;
            _codiceSequenza = codiceSequenza;
            _wrapper = wrapper;
            _datiProtoService = datiProtoService;
        }

        public recordIdentifier Adatta()
        {
            var factory = ProtocollazioneFactory.Create(_datiProto, _wrapper, _vert, _datiProtoService.Istanza);
            var factoryProtoCollegati = ProtocolliCollegatiFactory.Create(_datiProtoService, _wrapper);

            var request = new protocolInsertRequest
            {
                direction = factory.Flusso,
                documentList = factory.GetAllegati(),
                officeCode = _ufficio,
                operatingOfficeCode = _vert.CodiceUfficioOperante,
                registerCode = _vert.Registro,
                senderList = factory.GetMittenti(),
                recipientList = factory.GetDestinatari(),
                sequenceCode = _codiceSequenza,
                subjectDocument = _datiProto.ProtoIn.Oggetto,
                user = new user { code = _vert.Username, password = _vert.Password },
                previousList = factoryProtoCollegati.GetProtocolliCollegati()
            };

            if (!_vert.EscludiClassifica)
                request.filingList = new filing[] { new filing { code = _datiProto.ProtoIn.Classifica } };

            if (factory.DataSpedizione.HasValue)
            {
                request.receptionSendingDate = factory.DataSpedizione.Value;
                request.receptionSendingDateSpecified = true;
            }

            return _wrapper.Protocolla(request);
        }
    }
}

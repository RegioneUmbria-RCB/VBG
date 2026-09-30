using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Services;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Protocollazione.ProtocolliCollegati;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using ProtocolloInsielMercatoService;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Protocollazione
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
            var factory = ProtocollazioneFactory.Create(_datiProto);
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

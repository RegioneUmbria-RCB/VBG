using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Services;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.LeggiProtocollo.Identificativo;
using ProtocolloInsielMercatoService;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Protocollazione.ProtocolliCollegati
{
    public class ProtocolliCollegatiMovimenti : IProtocolliCollegati
    {
        IRecordIdentifier _identifier;
        ProtocollazioneService _wrapper;

        public ProtocolliCollegatiMovimenti(IRecordIdentifier identifier, ProtocollazioneService wrapper)
        {
            _identifier = identifier;
            _wrapper = wrapper;
        }

        public previous[] GetProtocolliCollegati()
        {
            var response = _wrapper.LeggiProtocollo(new protocolDetailRequest { recordIdentifier = _identifier.GetRecordIdentifier() });
            return new previous[] { _identifier.GetPrevious(response.recordIdentifier.direction) };
        }
    }
}

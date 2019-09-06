using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo;
using Init.SIGePro.Protocollo.InsielMercato2.Services;
using Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo.Identificativo;

namespace Init.SIGePro.Protocollo.InsielMercato2.Protocollazione.ProtocolliCollegati
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

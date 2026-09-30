using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Allegati
{
    public class AllegatiAdapter
    {
        AllegatiService _wrapper;
        List<ProtocolloAllegati> _allegati;

        public AllegatiAdapter(AllegatiService wrapper, List<ProtocolloAllegati> allegati)
        {
            _wrapper = wrapper;
            _allegati = allegati;
        }

        public IEnumerable<DocumentoInsProto> Adatta()
        {
            var retVal = _allegati.Select((x, idx) => x.GetUploadFileResponseFromProtocolloAllegati(_wrapper, idx));
            return retVal.ToArray();
        }
    }
}

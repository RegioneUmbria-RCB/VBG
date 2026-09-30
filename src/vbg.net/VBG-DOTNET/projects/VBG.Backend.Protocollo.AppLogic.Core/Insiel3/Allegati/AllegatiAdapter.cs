using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Allegati
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

        public DocumentoInsProto[] Adatta()
        {
            var retVal = _allegati.Select((x, idx) => x.GetUploadFileResponseFromProtocolloAllegati(_wrapper, idx));
            return retVal.ToArray();
        }
    }
}

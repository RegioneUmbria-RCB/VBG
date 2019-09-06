using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloFilesInsielService;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.ProtocolloInsiel3FilesTransferService;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;

namespace Init.SIGePro.Protocollo.Insiel3.Allegati
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

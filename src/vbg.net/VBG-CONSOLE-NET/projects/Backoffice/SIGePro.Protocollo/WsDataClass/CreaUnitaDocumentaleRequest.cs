using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "CreaUnitaDocumentaleRequestType")]
    public class CreaUnitaDocumentaleRequest
    {
        [DataMember(Order = 0, IsRequired = true)]
        public string TipoDocumento = "";

        [DataMember(Order = 1, IsRequired = true)]
        public IEnumerable<Allegato> Allegati = null;
    }
}

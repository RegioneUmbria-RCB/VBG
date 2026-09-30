using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;

namespace Init.SIGePro.Protocollo.DocEr.Autenticazione
{
    public class MetadatiAclDocumentAdapter
    {
        public MetadatiAclDocumentAdapter()
        {
            
        }

        public KeyValuePair<string, string> Adatta(string codiceDocEr, bool readOnly)
        {
            var key = codiceDocEr;
            var value = readOnly ? RuoliDocErConstants.READ_ONLY_ACCESS : RuoliDocErConstants.FULL_ACCESS;

            return new KeyValuePair<string, string>(key, value);
        }
    }
}

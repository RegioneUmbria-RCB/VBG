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
        Ruoli _ruolo;

        public MetadatiAclDocumentAdapter(Ruoli ruolo)
        {
            _ruolo = ruolo;
        }

        public KeyValuePair Adatta()
        {
            return new KeyValuePair
            {
                key = _ruolo.COD_DOCER,
                value = _ruolo.READONLY == "1" ? RuoliDocErConstants.READ_ONLY_ACCESS : RuoliDocErConstants.FULL_ACCESS
            };
        }
    }
}

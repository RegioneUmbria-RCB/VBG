using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloFilesInsielService2;
using Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo;

namespace Init.SIGePro.Protocollo.Insiel2.Allegati
{
    public class DownloadDocumentoRequestAdapter
    {
        public DownloadDocumentoRequest Adatta(IdProtocolloAdapter.IdProtocollo idProtocollo, long idDocumento)
        {
            var identificatore = new IdProtocollo  
            {
                ProgDoc = idProtocollo.ProgDoc,
                ProgMovi = idProtocollo.ProgMovi
            };

            return new DownloadDocumentoRequest
            {
                Registrazione = new ProtocolloRequest { Item = identificatore },
                idDoc = idDocumento
            };
        }
    }
}

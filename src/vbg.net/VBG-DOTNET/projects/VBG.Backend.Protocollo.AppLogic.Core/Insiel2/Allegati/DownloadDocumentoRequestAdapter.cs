using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ProtocolloFilesInsielService2;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.LeggiProtocollo;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Allegati
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

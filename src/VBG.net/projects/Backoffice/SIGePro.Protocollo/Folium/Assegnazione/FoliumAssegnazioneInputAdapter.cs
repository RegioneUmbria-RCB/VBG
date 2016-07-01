using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Folium.Services;

namespace Init.SIGePro.Protocollo.Folium.Assegnazione
{
    public class FoliumAssegnazioneInputAdapter
    {
        FoliumAssegnazioneInputConfiguration _conf;
        public readonly ProtocolloFoliumService.Assegnazione Request;

        public FoliumAssegnazioneInputAdapter(FoliumAssegnazioneInputConfiguration conf)
        {
            _conf = conf;
            Request = Adatta();
        }

        private ProtocolloFoliumService.Assegnazione Adatta()
        {
            var retVal = new ProtocolloFoliumService.Assegnazione
            {
                codiceAssegnazione = _conf.CodiceAssegnazione,
                idProtocollo = _conf.IdProtocollo,
                ufficioAssegnatario = _conf.UfficioAssegnatario,
                utenteAssegnatario = _conf.UtenteAssegnatario
            };

            return retVal;
            
        }
    }
}

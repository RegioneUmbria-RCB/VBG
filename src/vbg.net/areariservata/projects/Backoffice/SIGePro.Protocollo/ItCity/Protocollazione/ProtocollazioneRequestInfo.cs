using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Protocollazione
{
    public class ProtocollazioneRequestInfo
    {
        public CoordinateArchivio CoordinateArchivioInfo { get; set; }
        public RecapitoInterno MittenteInternoInfo { get; set; }
        public RecapitoEsterno[] MittentiEsterniInfo { get; set; }
        public DestinatarioInterno[] DestinatariInterniInfo { get; set; }
        public DestinatarioEsterno[] DestinatariEsterniInfo { get; set; }
        public Allegato[] Allegati { get; set; }
        /// <summary>
        /// Vanno specificati con lo stesso ordine con cui è stata valorizzata la proprietà Allegato
        /// </summary>
        //public IEnumerable<byte[]> AllegatiBuffer { get; private set; }

        public ProtocollazioneRequestInfo(CoordinateArchivio coordinateArchivio, RecapitoInterno mittenteInterno, RecapitoEsterno[] mittentiEsterni, DestinatarioInterno[] destinatariInterni, DestinatarioEsterno[] destinatariEsterni, Allegato[] allegati)
        {
            this.CoordinateArchivioInfo = coordinateArchivio;
            this.MittenteInternoInfo = mittenteInterno;
            this.MittentiEsterniInfo = mittentiEsterni;
            this.DestinatariInterniInfo = destinatariInterni;
            this.DestinatariEsterniInfo = destinatariEsterni;
            this.Allegati = allegati;
        }

        public ProtocollazioneRequestInfo()
        {

        }
    }
}

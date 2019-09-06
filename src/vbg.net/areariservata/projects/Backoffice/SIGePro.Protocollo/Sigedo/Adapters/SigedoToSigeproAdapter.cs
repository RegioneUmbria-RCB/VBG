using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.Sigedo.Adapters
{
    public class SigedoToSigeproAdapter
    {
        string _idAllegato;
        ResolveDatiProtocollazioneService _datiProtocollazione;

        public SigedoToSigeproAdapter(ResolveDatiProtocollazioneService datiProtocollazione, string idAllegato = "")
        {
            _datiProtocollazione = datiProtocollazione;
            _idAllegato = idAllegato;
        }

        public PROTOCOLLO_SIGEPRO Adatta()
        {

            var rVal = new PROTOCOLLO_SIGEPRO
            {
                IdAllegato = _idAllegato,
                DatiProtocollo = _datiProtocollazione
            };

            return rVal;
        }
    }
}

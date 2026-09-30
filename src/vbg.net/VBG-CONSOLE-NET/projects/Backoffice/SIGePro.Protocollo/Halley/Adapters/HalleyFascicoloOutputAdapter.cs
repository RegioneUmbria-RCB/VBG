using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloHalleyDizionarioServiceProxy;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Halley.Adapters
{
    public class HalleyFascicoloOutputAdapter
    {
        FascicoliFascicolo _response;
        public readonly DatiProtocolloFascicolato DatiFascicolo;

        public HalleyFascicoloOutputAdapter(FascicoliFascicolo response)
        {
            _response = response;
            DatiFascicolo = GetDatiFascicolo();
        }

        private DatiProtocolloFascicolato GetDatiFascicolo()
        {

            var retVal = new DatiProtocolloFascicolato();

            retVal.AnnoFascicolo = _response.anno;
            retVal.Classifica = _response.CodiceTitolario;
            retVal.NumeroFascicolo = _response.id;
            retVal.Oggetto = _response.Nome;

            return retVal;

        }

    }
}

using ProtocolloHalleyDizionarioServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Adapters
{
    public class HalleyFascicoloOutputAdapter
    {
        FascicoliFascicolo _response;
        public readonly DatiProtocolloFascicolatoResponseType DatiFascicolo;

        public HalleyFascicoloOutputAdapter(FascicoliFascicolo response)
        {
            _response = response;
            DatiFascicolo = GetDatiFascicolo();
        }

        private DatiProtocolloFascicolatoResponseType GetDatiFascicolo()
        {

            var retVal = new DatiProtocolloFascicolatoResponseType();

            retVal.AnnoFascicolo = _response.anno;
            retVal.Classifica = _response.CodiceTitolario;
            retVal.NumeroFascicolo = _response.id;
            retVal.Oggetto = _response.Nome;

            return retVal;

        }

    }
}

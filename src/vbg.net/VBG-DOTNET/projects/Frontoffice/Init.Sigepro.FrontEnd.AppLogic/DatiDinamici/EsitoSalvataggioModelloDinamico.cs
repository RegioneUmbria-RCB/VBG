using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public class EsitoSalvataggioModelloDinamico
    {
        public IEnumerable<int> IdSchedeDaRicompilare { get; }

        public EsitoSalvataggioModelloDinamico(IEnumerable<int> idSchedeDaRicompilare)
        {
            this.IdSchedeDaRicompilare = idSchedeDaRicompilare;
        }
    }
}

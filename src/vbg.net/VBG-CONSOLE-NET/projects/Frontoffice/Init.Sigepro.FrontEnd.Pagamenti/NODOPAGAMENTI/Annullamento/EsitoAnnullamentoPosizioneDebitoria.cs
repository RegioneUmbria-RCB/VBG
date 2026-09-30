using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Annullamento
{
    public class EsitoAnnullamentoPosizioneDebitoria
    {
        public readonly bool OperazioneRiuscita;
        public readonly string MessaggioErrore;

        internal EsitoAnnullamentoPosizioneDebitoria(bool operazioneRiuscita, string messaggioErrore = "")
        {
            OperazioneRiuscita = operazioneRiuscita;
            MessaggioErrore = messaggioErrore;
        }
    }
}

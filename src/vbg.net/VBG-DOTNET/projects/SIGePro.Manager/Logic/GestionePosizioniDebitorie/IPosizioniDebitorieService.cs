using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie
{
    public interface IPosizioniDebitorieService
    {
        EsitoInserimentoPosizioneDebitoriaSingola InserisciPosizioneDebitoria(InserisciPosizioneDebitoriaDaOnere request);
        EsitoInserimentoPosizioneDebitoriaRateizzata InserisciPosizioneDebitoriaRateizzata(IEnumerable<InserisciPosizioneDebitoriaDaOnere> request);
    }
}

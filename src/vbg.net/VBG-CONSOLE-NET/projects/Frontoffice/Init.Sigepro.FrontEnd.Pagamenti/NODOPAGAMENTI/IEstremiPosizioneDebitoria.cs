using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI
{
    public interface IEstremiPosizioneDebitoria
    {
        string IdPosizioneDebitoria { get; }
        string UniqueId { get; }
        string IUV { get; }

        RiferimentoPosizioneDebitoriaType ToRiferimentoPosizioneDebitoriaType();
    }
}

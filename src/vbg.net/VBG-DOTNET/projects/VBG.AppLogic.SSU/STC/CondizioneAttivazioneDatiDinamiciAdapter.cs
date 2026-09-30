using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VBG.AppLogic.SSU.STC
{
    internal class CondizioneAttivazioneDatiDinamiciAdapter : ICondizioneAttivazioneDatiDinamiciAdapter
    {
        public bool Verificata(IDomandaOnlineReadInterface domanda)
        {
            return !domanda.Ssu.ModalitaSsuAttiva;
        }
    }
}

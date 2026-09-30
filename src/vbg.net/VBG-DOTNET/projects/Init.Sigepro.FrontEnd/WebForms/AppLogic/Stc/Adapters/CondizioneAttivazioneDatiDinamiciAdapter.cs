using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Stc.Adapters
{
    public class CondizioneAttivazioneDatiDinamiciAdapter : ICondizioneAttivazioneDatiDinamiciAdapter
    {
        public bool Verificata(IDomandaOnlineReadInterface domanda)
        {
            return true;
        }
    }
}
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli
{
    public interface IDatiDinamiciControlsFactory
    {
        IDatiDinamiciControl CreaControllo(CampoDinamicoBase campo);
    }
}

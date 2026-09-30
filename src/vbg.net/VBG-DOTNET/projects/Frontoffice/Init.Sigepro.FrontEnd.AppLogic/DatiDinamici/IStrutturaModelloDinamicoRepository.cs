using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public interface IStrutturaModelloDinamicoRepository
    {
        IStrutturaModelloDinamico GetStrutturaModelloDinamico(int idModello);
    }
}

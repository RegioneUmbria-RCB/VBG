using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    public interface IGeneratoreRiepilogoModelloDinamicoAsync
    {
        Task<BinaryFile> GeneraRiepilogoAsync(int idModello, string nomeRiepilogo, int indiceMolteplicita);
    }
}

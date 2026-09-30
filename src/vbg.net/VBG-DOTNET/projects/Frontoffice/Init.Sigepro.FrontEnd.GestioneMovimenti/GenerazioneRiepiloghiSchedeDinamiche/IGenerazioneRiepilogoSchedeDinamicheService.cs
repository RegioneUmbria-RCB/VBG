using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche
{
    public interface IGenerazioneRiepilogoSchedeDinamicheService
    {
        BinaryFile GeneraRiepilogoScheda(MovimentoDaEffettuare movimento, int idScheda, string forzaNomeFile = "");
        Task<BinaryFile> GeneraRiepilogoSchedaAsync(MovimentoDaEffettuare movimento, int idScheda, string forzaNomeFile = "");
    }
}

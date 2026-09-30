using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.ConfigurazioneBackoffice;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.DatiDinamici
{
    public interface IOnceOnlyDatiDinamiciService : IOnceOnlyService
    {
        Task<ValoriPrecompilabiliOnceOnly> GetValoriCampiDinamiciPrecompilabiliAsync(int idDomanda);
        Task PrecompilaCampiDinamiciAsync(int idDomanda, ValoriPrecompilabiliOnceOnly valoriPrecompilabiliOnceOnly);
        Task SalvaCampiDinamiciAsync(int idDomanda, ListaIdentificativiOnceOnly identificativiOnceOnly);
    }
}

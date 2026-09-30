using System.Collections.Generic;
using System.Threading.Tasks;
using VBG.DatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public interface IModelliDinamiciService
    {

        void EliminaModello(int idDomanda, int idModello, int indice);
        IEnumerable<int> GetIndiciScheda(int idDomanda, int idScheda);
        ModelloDinamicoIstanza GetModelloDinamico(int idDomanda, int idScheda, int indiceScheda);
        Task<ModelloDinamicoIstanza> GetModelloDinamicoAsync(int idDomanda, int idScheda, int indiceScheda);
        EsitoSalvataggioModelloDinamico Salva(int idDomanda, ModelloDinamicoBase modelloDinamico);
        void InvalidaRiepiloghi(int idDomanda, int idModello);
        void SincronizzaModelliDinamici(int idDomanda, bool ignoraSchedaCittadinoExtracomunitario);
    }
}
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.SIGePro.DatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public interface IModelliDinamiciService
    {
        void AggiungiOggettoRiepilogo(int idDomanda, int idModello, int indiceMolteplicita, BinaryFile file);
        void EliminaModello(int idDomanda, int idModello, int indice);
        void EliminaOggettoRiepilogo(int idDomanda, int idModello, int indiceMolteplicita);
        IEnumerable<int> GetIndiciScheda(int idDomanda, int idScheda);
        ModelloDinamicoIstanza GetModelloDinamico(int idDomanda, int idScheda, int indiceScheda);
        void RigeneraRiepiloghi(int idDomanda, bool generaRiepilogoSchedeCheNonRichiedonoFirma);
        void Salva(int idDomanda, ModelloDinamicoBase modelloDinamico);
        void SincronizzaModelliDinamici(int idDomanda, bool ignoraSchedaCittadinoExtracomunitario);
    }
}
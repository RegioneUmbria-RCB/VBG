using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public interface IRiepiloghiDatiDinamiciAsyncService
    {
        Task<BinaryFile> GeneraRiepilogoAsync(int idDomanda, int idModello, int indiceMolteplicita, IStrutturaModelloDinamicoRepository? strutturaModelloDinamicoRepository = null);
        Task<RiepilogoRichiesto[]> GetRiepiloghiRichiestiAsync(int idDomanda, bool generaRiepilogoSchedeCheNonRichiedonoFirma, bool ignoraObbligoFirmaDigitale);

        /// <summary>
        /// Genera i riepiloghi specificati in listaRiepiloghi e li allega alla domanda indicata da idDomanda
        /// </summary>
        /// <param name="idDomanda">Id della domanda in compilazione</param>
        /// <param name="listaRiepiloghi">lista di id di modelli di cui generare il riepilogo</param>
        /// <param name="strutturaModelloDinamicoRepository">(opzionale) repository della struttura del modello</param>
        /// <returns></returns>
        Task GeneraRiepiloghiEAllegaADomandaAsync(int idDomanda, IEnumerable<RiepilogoRichiesto> listaRiepiloghi, IStrutturaModelloDinamicoRepository? strutturaModelloDinamicoRepository = null);
        // Task RigeneraRiepiloghiAsync(int idDomanda, bool generaRiepilogoSchedeCheNonRichiedonoFirma);
        Task AggiungiOggettoRiepilogoAsync(int idDomanda, int idModello, int indiceMolteplicita, int codiceOggetto);
        Task EliminaOggettoRiepilogoAsync(int idDomanda, int idModello, int indiceMolteplicita);
    }


    public interface IRiepiloghiDatiDinamiciService
    {
        void RigeneraRiepiloghi(int idDomanda, bool generaRiepilogoSchedeCheNonRichiedonoFirma);
        void EliminaOggettoRiepilogo(int idDomanda, int idModello, int indiceMolteplicita);
        void AggiungiOggettoRiepilogo(int idDomanda, int idModello, int indiceMolteplicita, BinaryFile file, bool ignoraVerificaFirma = false);
    }
}

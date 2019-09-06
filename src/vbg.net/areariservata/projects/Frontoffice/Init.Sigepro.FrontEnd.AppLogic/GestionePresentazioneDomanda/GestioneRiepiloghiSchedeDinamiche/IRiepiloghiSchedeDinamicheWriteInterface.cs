using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche
{
	public interface IRiepiloghiSchedeDinamicheWriteInterface
	{
        //void RigeneraRiepiloghiModelli(int idDomanda, IGeneratoreRiepilogoModelloDinamico generatoreRiepilogo, bool generaRiepilogoSchedeCheNonRichiedonoFirma, IAllegatiDomandaFoRepository allegatiDomandaFoRepository);
        void AggiungiOAggiorna(int idModello, int indiceMolteplicita, string nomeScheda);
        void EliminaByIdModello(int idModello);
		void SincronizzaConModelli();
		void EliminaOggettoRiepilogo(int idModello, int indiceMolteplicita);
		void SalvaOggettoRiepilogo(int idModello, int indiceMolteplicita, int codiceOggetto, string nomeFile, bool firmatoDigitalmente);
        void SetIdAllegato(int idModello, int indiceMolteplicita, int idAllegato, string md5);
        bool HaRiepilogo(int idModello, int indiceMolteplicita);
    }
}

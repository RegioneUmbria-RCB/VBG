using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche
{
	public interface IRiepiloghiSchedeDinamicheWriteInterface
	{
		void RigeneraRiepiloghiModelli(IGeneratoreRiepilogoModelloDinamico generatoreRiepilogo, bool generaRiepilogoSchedeCheNonRichiedonoFirma);
		void EliminaByIdModello(int idModello);
		void SincronizzaConModelli();
		void EliminaOggettoRiepilogo(int idModello, int indiceMolteplicita);
		void SalvaOggettoRiepilogo(int idModello, int indiceMolteplicita, int codiceOggetto, string nomeFile, bool firmatoDigitalmente);
	}
}

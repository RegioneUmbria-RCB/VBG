using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche
{
	public interface IRiepiloghiSchedeDinamicheReadInterface
	{
		IEnumerable<RiepilogoSchedaDinamica> Riepiloghi { get; }
		IEnumerable<RiepilogoSchedaDinamica> GetByCodiceEndo(int codiceEndo);
		IEnumerable<RiepilogoSchedaDinamica> GetRiepiloghiInterventoConAllegatoUtente();
		IEnumerable<RiepilogoSchedaDinamica> GetRigheRiepilogoCittadinoExtracomunitario();

		RiepilogoSchedaDinamica GetByIdModelloIndiceMolteplicita(int idModello, int indiceMolteplicita);

		int Count { get; }
	}
}

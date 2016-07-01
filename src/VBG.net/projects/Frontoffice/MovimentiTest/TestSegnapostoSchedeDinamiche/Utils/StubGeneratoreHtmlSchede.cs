using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;

using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche.Utils
{
    public class StubGeneratoreHtmlSchede : IGeneratoreHtmlSchedeDinamiche
    {

        #region IGeneratoreHtmlSchedeDinamiche Members

		public string GeneraHtml(DomandaOnline domanda, int idScheda)
		{
			return GeneraHtml(domanda, idScheda, -1);
		}

		public string GeneraHtmlDelleSchedeDellaDomanda(DomandaOnline domanda, GenerazioneHtmlSchedeOptions options)
        {
			return String.Join("", domanda.ReadInterface.DatiDinamici.Modelli.Select(x => x.Descrizione).ToArray());
        }

        public string GeneraHtmlScheda(ModelloDinamicoBase scheda, ICampiNonVisibili campiNonVisibili = null)
        {
            throw new NotImplementedException();
        }

        #endregion


		public string GeneraHtml(DomandaOnline domanda, int idScheda, int indiceMolteplicita= -1)
		{
			var row = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.IdModello == idScheda).FirstOrDefault();

			if (row == null)
				return String.Empty;

			return row.Descrizione;
		}
	}
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche.Utils
{
    public class StubGeneratoreHtmlSchede : IGeneratoreHtmlSchedeDinamiche
    {
        public string GeneraHtml(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idScheda, int indiceMolteplicita = -1)
        {
            var row = datiDinamiciReader.GetListaModelli().Where(x => x.IdModello == idScheda).FirstOrDefault();

            if (row == null)
                return String.Empty;

            return row.Descrizione;
        }

        public string GeneraHtml(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idScheda)
        {
            return GeneraHtml(datiDinamiciReader, idScheda, -1);
        }

        public string GeneraHtmlDelleSchedeDellaDomanda(IDatiDinamiciRiepilogoReader datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return String.Join("", datiDinamiciReader.GetListaModelli().Select(x => x.Descrizione).ToArray());
        }

        public string GeneraHtmlScheda(ModelloDinamicoBase scheda, ICampiNonVisibili campiNonVisibili = null)
        {
            throw new NotImplementedException();
        }

        public string GeneraHtmlSchedaEndoprocedimento(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idEndo)
        {
            return String.Join("", datiDinamiciReader.GetListaModelliEndo(idEndo).Select(x => x.Descrizione).ToArray());
        }

        public string GeneraHtmlSchedeIntervento(IDatiDinamiciRiepilogoReader datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return String.Join("", datiDinamiciReader.GetListaModelliIntervento().Select(x => x.Descrizione).ToArray());
        }
    }
}

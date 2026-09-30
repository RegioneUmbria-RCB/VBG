using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Linq;
using System.Threading.Tasks;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche.Utils
{
    public class StubGeneratoreHtmlSchede : IGeneratoreHtmlSchedeDinamiche
    {
        public bool IgnoraCssDefault { get; set; } = false;

        public string GeneraHtml(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idScheda, int indiceMolteplicita = -1)
        {
            var row = datiDinamiciReader.GetListaModelli().FirstOrDefault(x => x.IdModello == idScheda);

            if (row == null)
                return String.Empty;

            return row.Descrizione;
        }

        public string GeneraHtml(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idScheda)
        {
            return this.GeneraHtml(datiDinamiciReader, idScheda, -1);
        }

        public Task<string> GeneraHtmlAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idScheda, int indiceMolteplicita = -1)
        {
            throw new NotImplementedException();
        }

        public string GeneraHtmlDelleSchedeDellaDomanda(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return String.Join("", datiDinamiciReader.GetListaModelli().Select(x => x.Descrizione).ToArray());
        }

        public Task<string> GeneraHtmlDelleSchedeDellaDomandaAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            throw new NotImplementedException();
        }

        public string GeneraHtmlScheda(ModelloDinamicoBase scheda, ICampiNonVisibili campiNonVisibili = null)
        {
            throw new NotImplementedException();
        }

        public string GeneraHtmlSchedaEndoprocedimento(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idEndo)
        {
            return String.Join("", datiDinamiciReader.GetListaModelliEndo(idEndo).Select(x => x.Descrizione).ToArray());
        }

        public Task<string> GeneraHtmlSchedaEndoprocedimentoAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idEndo)
        {
            throw new NotImplementedException();
        }

        public string GeneraHtmlSchedeIntervento(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return String.Join("", datiDinamiciReader.GetListaModelliIntervento().Select(x => x.Descrizione).ToArray());
        }

        public Task<string> GeneraHtmlSchedeInterventoAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            throw new NotImplementedException();
        }

        public void Inizializza()
        {
            // throw new NotImplementedException();
        }
    }
}

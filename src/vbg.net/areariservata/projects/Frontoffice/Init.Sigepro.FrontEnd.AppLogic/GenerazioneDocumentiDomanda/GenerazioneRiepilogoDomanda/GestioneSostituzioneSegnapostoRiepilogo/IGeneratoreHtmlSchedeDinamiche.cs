using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public enum GenerazioneHtmlSchedeOptions
    {
        SoloSchedeCheNonNecessitanoFirma = 0,
        TutteLeSchede = 1
    }

    public interface IGeneratoreHtmlSchedeDinamiche
    {
        string GeneraHtml(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idScheda, int indiceMolteplicita = -1);
        string GeneraHtmlSchedeIntervento(IDatiDinamiciRiepilogoReader datiDinamiciReader, GenerazioneHtmlSchedeOptions options);
        string GeneraHtmlSchedaEndoprocedimento(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idEndo);
        string GeneraHtmlDelleSchedeDellaDomanda(IDatiDinamiciRiepilogoReader datiDinamiciReader, GenerazioneHtmlSchedeOptions options);
        //string GeneraHtmlScheda(ModelloDinamicoBase scheda, ICampiNonVisibili campiNonVisibili = null);
    }
}

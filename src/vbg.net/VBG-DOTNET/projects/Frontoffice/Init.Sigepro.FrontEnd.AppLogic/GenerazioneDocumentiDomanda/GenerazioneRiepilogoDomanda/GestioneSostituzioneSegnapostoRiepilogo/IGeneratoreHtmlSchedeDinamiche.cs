using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public enum GenerazioneHtmlSchedeOptions
    {
        SoloSchedeCheNonNecessitanoFirma = 0,
        TutteLeSchede = 1
    }

    public interface IGeneratoreHtmlSchedeDinamiche
    {
        void Inizializza();
        bool IgnoraCssDefault { get; set; }
        string GeneraHtml(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idScheda, int indiceMolteplicita = -1);
        string GeneraHtmlSchedeIntervento(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options);
        string GeneraHtmlSchedaEndoprocedimento(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idEndo);
        string GeneraHtmlDelleSchedeDellaDomanda(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options);

        Task<string> GeneraHtmlAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idScheda, int indiceMolteplicita = -1);
        Task<string> GeneraHtmlSchedeInterventoAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options);
        Task<string> GeneraHtmlSchedaEndoprocedimentoAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idEndo);
        Task<string> GeneraHtmlDelleSchedeDellaDomandaAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options);
        //string GeneraHtmlScheda(ModelloDinamicoBase scheda, ICampiNonVisibili campiNonVisibili = null);
    }
}

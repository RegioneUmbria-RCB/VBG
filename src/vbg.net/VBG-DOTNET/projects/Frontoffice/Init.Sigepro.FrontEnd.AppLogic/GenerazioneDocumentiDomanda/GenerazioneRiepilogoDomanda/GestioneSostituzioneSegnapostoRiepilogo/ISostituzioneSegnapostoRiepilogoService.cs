using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public interface ISostituzioneSegnapostoRiepilogoService
    {
        string ProcessaRiepilogo(ISchedeDinamicheDomandaAlRiepilogoService domandaOnline, string templateDaProcessare);

        Task<string> ProcessaRiepilogoAsync(ISchedeDinamicheDomandaAlRiepilogoService domandaOnline, string templateDaProcessare);
    }
}

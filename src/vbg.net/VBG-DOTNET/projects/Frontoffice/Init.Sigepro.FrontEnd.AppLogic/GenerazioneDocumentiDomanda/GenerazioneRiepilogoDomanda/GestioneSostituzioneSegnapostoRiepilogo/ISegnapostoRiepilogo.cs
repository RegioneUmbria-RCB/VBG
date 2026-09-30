using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    internal interface ISegnapostoRiepilogo
    {
        string NomeTag { get; }
        string NomeArgomento { get; }

        bool SupportaOperazioniAsincrone { get; }

        string Elabora(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione);

        Task<string> ElaboraAsync(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione);
    }
}

using VisuraVbg;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public interface ISegnapostoRiepilogo
    {
        string NomeTag { get; }
        string NomeArgomento { get; }

        Task<string> ElaboraAsync(Istanze istanza, string argomento, string espressione);
    }
}

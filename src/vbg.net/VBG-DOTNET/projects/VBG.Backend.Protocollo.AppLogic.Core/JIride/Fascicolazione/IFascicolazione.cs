using FascicolazioneJIrideService;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Fascicolazione
{
    public interface IFascicolazione
    {
        EsitoOperazione FascicolaDocumento(int IDFascicolo, int IDDocumento, string AggiornaClassifica, string Utente, string Ruolo, string idProtocollo);
        FascicoloOutXml CreaFascicolo(FascicolazioneInfo info);
    }
}

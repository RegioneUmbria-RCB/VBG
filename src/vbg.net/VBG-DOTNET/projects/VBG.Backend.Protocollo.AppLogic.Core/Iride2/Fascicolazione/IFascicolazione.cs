using FascicolazioneIride2Service;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.Fascicolazione
{
    public interface IFascicolazione
    {
        FascicoloOut LeggiFascicolo(string numero, string anno, string classifica, int idFascicolo, string utente, string ruolo, string codiceAmministrazione, string codiceAoo);
    }
}

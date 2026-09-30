

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Fascicolazione
{
    public interface IFascicolazione
    {
        FascicoloOut LeggiFascicolo(string numero, string anno, string classifica, int id, string utente, string ruolo, string codiceAmministrazione, string codiceAoo);
        FascicolazioneProxy Proxy { get; }
    }
}

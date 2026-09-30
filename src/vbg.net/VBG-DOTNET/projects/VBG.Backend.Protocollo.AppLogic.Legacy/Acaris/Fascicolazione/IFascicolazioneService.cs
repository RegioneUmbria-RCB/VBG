using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione
{
    public interface IFascicolazioneService
    {
        IdFolder Fascicola(FascicolaRequest request);
    }
}

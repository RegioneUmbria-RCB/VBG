using ItCityService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Fascicolazione
{
    public interface IFascicolazioneRequest
    {
        Fascicolo GetDatiFascicoloRequest(FascicolazioneServiceWrapper fascicolazioneService, int idUnitaOperativa);
        bool CompletaRegistrazione { get; }
    }
}

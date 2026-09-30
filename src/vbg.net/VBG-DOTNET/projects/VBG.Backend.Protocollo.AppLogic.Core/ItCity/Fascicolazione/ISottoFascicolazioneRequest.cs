using ItCityService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Fascicolazione
{
    public interface ISottoFascicolazioneRequest
    {
        Fascicolo GetDatiFascicoloRequest(FascicolazioneServiceWrapper fascicolazioneService, int idUnitaOperativa);
        bool CompletaRegistrazione { get; }
    }
}

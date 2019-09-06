using Init.SIGePro.Protocollo.ProtocolloItCityService;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public interface ISottoFascicolazioneRequest
    {
        Fascicolo GetDatiFascicoloRequest(FascicolazioneServiceWrapper fascicolazioneService);
        bool CompletaRegistrazione { get; }
    }
}

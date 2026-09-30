namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public interface ILeggiAllegatoService
    {
        LeggiAllegatoResponse LeggiAllegato(int idProtocollo, int idAllegato);
    }
}
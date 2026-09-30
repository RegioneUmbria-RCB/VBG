

namespace VBG.Backend.Protocollo.Verticalizzazioni.Shared
{
    //utilizzata per identificare le verticalizzazioni PROTOCOLLO_ATTIVO e PROTOCOLLO_STORICO 
    public interface IVerticalizzazioneProtocollo
    {
        string NomeVerticalizzazione { get; }
        bool Attiva { get; }
        string Tipoprotocollo { get; }
        string ProxyAddress { get; }
    }
}

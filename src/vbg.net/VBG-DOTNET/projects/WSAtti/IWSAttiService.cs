namespace WSAtti
{
    public interface IWSAttiService
    {
        WSAttiInserisciDeterminaResponse InserisciDetermina(WSAttiInserisciDeterminaRequest request);
        WSAttiNumeraDeterminaResponse NumeraDetermina(WSAttiNumeraDeterminaRequest request);
        WSAttiAggiungiAllegatoResponse AggiungiAllegato(WSAttiAggiungiAllegatoRequest request);
        WSAttiLeggiDeterminaResponse LeggiDetermina(WSAttiLeggiDeterminaRequest request);
        WsAttiElencoFirmatariResponse ElencoFirmatari();
    }
}

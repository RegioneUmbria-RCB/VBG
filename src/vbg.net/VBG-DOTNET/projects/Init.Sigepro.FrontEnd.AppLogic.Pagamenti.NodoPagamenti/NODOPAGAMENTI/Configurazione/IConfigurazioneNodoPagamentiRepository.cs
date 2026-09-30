namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Configurazione
{
    public interface IConfigurazioneNodoPagamentiRepository
    {
        ConfigurazioneNodoPagamenti GetConfigurazione(string codiceComune);
    }
}

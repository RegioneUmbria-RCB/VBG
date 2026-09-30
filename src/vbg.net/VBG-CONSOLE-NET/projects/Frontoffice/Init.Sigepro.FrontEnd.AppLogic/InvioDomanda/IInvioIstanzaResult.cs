namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    public interface IInvioIstanzaResult
    {
        string CodiceIstanza { get; }
        TipoEsitoInvio Esito { get; }
        string NumeroIstanza { get; }
        string UuId { get; }
    }
}
namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
    public enum StatoVerificaDocumentoEnum
    {
        Valido = 0,
        NonValido = 1,
        DaVerificare
    }

    public interface IDocumentoIstanzaOggettoDiVerifica
    {
        StatoVerificaDocumentoEnum EsitoVerifica { get; }
        bool ContieneOggetto { get; }
        bool ContieneDatiSensibili { get; }
        string CodiceOggetto { get; }
    }
}

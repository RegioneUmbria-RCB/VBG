using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
    public class EsitoVerificaFirma
    {
        public StatoVerificaFirma Stato { get; set; }
        public string? Messaggio { get; set; }
        public string? NomeFirmatario { get; set; }
        public string? CodiceFiscaleFirmatario { get; set; }
        public string? DataFirma { get; set; }
    }


    public interface IFirmaDigitaleMetadataService
    {
        BinaryFile GetFileInChiaro(BinaryFile fileFirmato);
    }


}

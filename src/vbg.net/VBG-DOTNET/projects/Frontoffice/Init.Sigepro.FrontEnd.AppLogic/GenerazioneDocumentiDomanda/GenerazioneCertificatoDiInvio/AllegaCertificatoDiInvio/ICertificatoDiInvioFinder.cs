namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.AllegaCertificatoDiInvio
{
    public interface ICertificatoDiInvioFinder
    {
        bool PraticaHaCertificatoDiInvio(int idDomandaBackoffice);
        int? GetCodiceOggettoCertificatoDiInvio(int idDomandaBackoffice);
    }
}
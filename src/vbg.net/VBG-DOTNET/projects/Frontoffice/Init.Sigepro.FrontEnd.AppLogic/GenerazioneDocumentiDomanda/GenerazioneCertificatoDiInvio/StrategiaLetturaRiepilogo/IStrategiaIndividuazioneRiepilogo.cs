
namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo
{
	public interface IStrategiaIndividuazioneCertificatoInvio
	{
        bool IsCertificatoDefinito(int idDomandaBackoffice);
        int CodiceOggetto(int idDomandaBackoffice);

    }
}

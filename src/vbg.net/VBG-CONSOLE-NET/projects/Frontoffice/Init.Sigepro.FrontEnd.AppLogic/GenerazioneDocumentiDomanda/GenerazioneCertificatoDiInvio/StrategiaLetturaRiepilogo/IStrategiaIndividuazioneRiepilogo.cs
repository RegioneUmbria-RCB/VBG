
namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo
{
	public interface IStrategiaIndividuazioneCertificatoInvio
	{
		bool IsCertificatoDefinito { get; }
		int CodiceOggetto{get;}
	}
}

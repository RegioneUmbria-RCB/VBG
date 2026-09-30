using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio
{
    public class CertificatoDiInvioPdf
	{
        IHtmlToPdfFileConverter _fileConverterService;

        public CertificatoDiInvioPdf(IHtmlToPdfFileConverter fileConverterService)
		{
			this._fileConverterService = fileConverterService;
		}

		public BinaryFile DaHtml(CertificatoDiInvioHtml certificatoHtml)
		{
			return this._fileConverterService.Converti("certificato-di-invio.pdf", certificatoHtml.Html);
		}
	}
}

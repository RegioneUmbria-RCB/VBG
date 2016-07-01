using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio
{
	public class CertificatoDiInvioPdf
	{
		FileConverterService _fileConverterService;

		public CertificatoDiInvioPdf( FileConverterService fileConverterService)
		{
			this._fileConverterService = fileConverterService;
		}

		public BinaryFile DaHtml(CertificatoDiInvioHtml certificatoHtml)
		{
			return this._fileConverterService.Converti( "CertificatoDiInvio.html", certificatoHtml.RawData, "PDF");
		}
	}
}

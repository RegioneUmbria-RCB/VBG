using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF
{
	public interface IPdfUtilsService
	{
		BinaryFile PrecompilaPdf(int codiceOggettoPdf, int idDomandaOnline);
		BinaryFile PrecompilaPdf(int codiceOggettoPdf, int idDomandaOnline, IIstanzaStcAdapter adapter);

        DatiPdfCompilabile EstraiDatiPdf(DocumentoDomanda allegato);
		DatiPdfCompilabile EstraiDatiPdf(int codiceOggetto);
		DatiPdfCompilabile EstraiDatiPdf(BinaryFile oggetto);
	}

	public class PdfUtilsService : IPdfUtilsService
	{
		ILog _log = LogManager.GetLogger(typeof(PdfUtilsService));
		IPdfUtilsWsWrapper _serviceWrapper;
		IOggettiService _oggettiService;
		ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		IstanzaStcAdapter	_istanzaStcAdapter;

		internal PdfUtilsService( IPdfUtilsWsWrapper serviceWrapper, IOggettiService oggettiService, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IstanzaStcAdapter istanzaStcAdapter)
		{
			this._serviceWrapper = serviceWrapper;
			this._oggettiService = oggettiService;
			this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._istanzaStcAdapter = istanzaStcAdapter;
		}


		public BinaryFile PrecompilaPdf(int codiceOggettoPdf, int idDomandaOnline, IIstanzaStcAdapter adapter)
		{
			var domanda = this._salvataggioDomandaStrategy.GetById(idDomandaOnline);

			var domandaStc = adapter.Adatta(domanda);

			return PrecompilaPdf(codiceOggettoPdf, domandaStc);
		}


		private BinaryFile PrecompilaPdf(int codiceOggettoPdf, DettaglioPraticaType domandaStc)
		{
			var oggetto = this._oggettiService.GetById(codiceOggettoPdf);

			if (this._log.IsDebugEnabled)
			{
				this._log.DebugFormat("Invocazione del servizio di precompilazione pdf con codiceoggetto {0} e xml\r\n{1}", codiceOggettoPdf, domandaStc.ToXmlString());
			}

			var xmlFile = new BinaryFile("domandaOnline.xml", "text/xml", domandaStc.ToXmlByteArray());

			return this._serviceWrapper.PrecompilaPdf(oggetto, xmlFile);
		}

		public BinaryFile PrecompilaPdf(int codiceOggettoPdf, int idDomandaOnline)
		{			
			var domanda = this._salvataggioDomandaStrategy.GetById(idDomandaOnline);

			var domandaStc = this._istanzaStcAdapter.Adatta(domanda);

			return PrecompilaPdf(codiceOggettoPdf, domandaStc);
		}


        public DatiPdfCompilabile EstraiDatiPdf(DocumentoDomanda allegato)
        {
            if (allegato == null || allegato.AllegatoDellUtente == null)
                return null;

			return EstraiDatiPdf(allegato.AllegatoDellUtente.CodiceOggetto);
        }

		public DatiPdfCompilabile EstraiDatiPdf(int codiceOggetto)
		{
			var oggetto = this._oggettiService.GetById(codiceOggetto);

			return this._serviceWrapper.EstraiXml(oggetto);
		}

		public DatiPdfCompilabile EstraiDatiPdf(BinaryFile oggetto)
		{
			return this._serviceWrapper.EstraiXml(oggetto);
		}
    }

}

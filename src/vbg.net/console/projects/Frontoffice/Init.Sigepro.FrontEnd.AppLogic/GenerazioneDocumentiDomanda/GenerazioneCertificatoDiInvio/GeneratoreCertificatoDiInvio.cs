using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.Common;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.GestioneQrCode;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.LetturaXmlDomandaBackend;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Utils;
using log4net;
using System;
using System.Configuration;
using System.IO;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio
{
	public class GeneratoreCertificatoDiInvio
	{
		private static class Constants
		{
			public const string SegnapostoVisuraStc = "<!--VISURA_STC-->";
		}

		ILog _log = LogManager.GetLogger(typeof(GeneratoreCertificatoDiInvio));

		RiepilogoDomandaReader _riepilogoDomandaReader;
        IHtmlToPdfFileConverter _fileConverter;
        XmlDomandaBackendStrategy _visuraStrategy;
        ISostituzioneSegnapostoQrCode _sostituzioneQrCode;

        public GeneratoreCertificatoDiInvio(RiepilogoDomandaReader riepilogoDomandaReader, IHtmlToPdfFileConverter fileConverter, XmlDomandaBackendStrategy visuraStrategy, ISostituzioneSegnapostoQrCode sostituzioneQrCode)
		{
			this._riepilogoDomandaReader = riepilogoDomandaReader;
            this._fileConverter = fileConverter;
            this._visuraStrategy = visuraStrategy;
            this._sostituzioneQrCode = sostituzioneQrCode;
		}

		public BinaryFile GeneraCertificatoDiInvio(string idDomandaBackoffice, IStrategiaIndividuazioneCertificatoInvio strategiaIndividuazioneRiepilogo)
		{
			if (!strategiaIndividuazioneRiepilogo.IsCertificatoDefinito)
			{
                _log.ErrorFormat("non è stato possibile generare un certificato di invio per l'istanza {0}", idDomandaBackoffice);
				return null;
			}

            var xslTemplate = _riepilogoDomandaReader.Read(strategiaIndividuazioneRiepilogo);
            var xmlIstanza = LeggiXmlDomanda(idDomandaBackoffice, xslTemplate);

            DumpXmlIstanza(xmlIstanza);

            var htmlCertificato = new XslFile(xslTemplate).Trasforma(xmlIstanza);

            htmlCertificato = this._sostituzioneQrCode.ProcessaCertificato(Convert.ToInt32(idDomandaBackoffice), htmlCertificato);

            return this._fileConverter.Converti("certificato-di-invio.pdf", htmlCertificato);
		}

        private string LeggiXmlDomanda(string idDomandaBackoffice, string xslTemplate)
        {
            var tipoVisura = XmlDomandaBackendStrategy.TipoVisura.Vbg;

            if (xslTemplate.Contains(Constants.SegnapostoVisuraStc))
            {
                tipoVisura = XmlDomandaBackendStrategy.TipoVisura.Stc;
            }

            return this._visuraStrategy.GetXml(tipoVisura, idDomandaBackoffice);
        }
        
		private void DumpXmlIstanza(string xmlIstanza)
		{
            var dumpXmlIstanzaCaricata = ConfigurationManager.AppSettings["DumpXmlIstanzaDuranteGenerazioneCertificato"];

            if (String.IsNullOrEmpty(dumpXmlIstanzaCaricata))
                return;

			if(HttpContext.Current == null)
				return;

			var path = HttpContext.Current.Server.MapPath("~/Logs");
			path = Path.Combine(path, "dumpIstanzaCertificato_" + HttpContext.Current.Session.SessionID + ".xml");

			File.WriteAllText( path , xmlIstanza);
		}
	}
}

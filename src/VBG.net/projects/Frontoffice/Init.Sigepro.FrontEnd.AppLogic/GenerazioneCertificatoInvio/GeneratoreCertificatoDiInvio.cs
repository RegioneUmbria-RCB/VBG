using System;
using System.Text;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio.StrategiaLetturaRiepilogo;

using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Utils;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System.Configuration;
using System.Web;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio
{
	public class GeneratoreCertificatoDiInvio
	{
		private static class Constants
		{
			public const string SegnapostoVisuraStc = "<!--VISURA_STC-->";
		}

		ILog _log = LogManager.GetLogger(typeof(GeneratoreCertificatoDiInvio));

		IAliasSoftwareResolver _aliasSoftwareResolver;
		CertificatoDiInvioHtml _certificatoHtml = null;
		string _idDomandaBackoffice = String.Empty;
		string _idDomandaFrontoffice = String.Empty;
		FileConverterService _fileConverterService;
		IDettaglioPraticaRepository _visuraRepository;
		IIstanzePresentateRepository _istanzePresentateRepository;
		RiepilogoDomandaReader _riepilogoDomandaReader;
		bool _dumpXmlIstanzaCaricata = false;

		public GeneratoreCertificatoDiInvio(IAliasSoftwareResolver aliasSoftwareResolver, FileConverterService fileConverterService,
											IDettaglioPraticaRepository visuraRepository, IIstanzePresentateRepository istanzePresentateRepository, 
											RiepilogoDomandaReader riepilogoDomandaReader )
		{
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver")
					 .IsNotNull();

			Condition.Requires(fileConverterService, "fileConverterService")
					 .IsNotNull();

			Condition.Requires(visuraRepository, "visuraRepository")
					 .IsNotNull();

			Condition.Requires(istanzePresentateRepository, "istanzePresentateRepository")
					 .IsNotNull();

			Condition.Requires(riepilogoDomandaReader, "riepilogoDomandaReader")
					 .IsNotNull();

			_aliasSoftwareResolver = aliasSoftwareResolver;
			_fileConverterService = fileConverterService;
			_visuraRepository = visuraRepository;
			_istanzePresentateRepository = istanzePresentateRepository;
			_riepilogoDomandaReader = riepilogoDomandaReader;

			var strDumpDomanda = ConfigurationManager.AppSettings["DumpXmlIstanzaDuranteGenerazioneCertificato"];

			if (!String.IsNullOrEmpty(strDumpDomanda))
				this._dumpXmlIstanzaCaricata = Boolean.Parse(strDumpDomanda);
		}

		public CertificatoDiInvioHtml GetCertificatoHtml()
		{
			return this._certificatoHtml;
		}

		public void GeneraCertificatoDiInvio(DomandaOnline domanda, string idDomandaBackoffice, IStrategiaIndividuazioneCertificatoInvio strategiaIndividuazioneRiepilogo)
		{
			Condition.Requires(domanda, "domanda")
					 .IsNotNull();

			Condition.Requires(idDomandaBackoffice, "idDomandaBackoffice")
					 .IsNotNullOrEmpty();

			Condition.Requires(strategiaIndividuazioneRiepilogo, "strategiaIndividuazioneRiepilogo")
					 .IsNotNull();

			this._certificatoHtml = null;
			this._idDomandaBackoffice  = idDomandaBackoffice;
			this._idDomandaFrontoffice = domanda.DataKey.ToSerializationCode();

			if (!strategiaIndividuazioneRiepilogo.IsCertificatoDefinito)
			{
				_log.ErrorFormat("non è stato possibile generare un certificato di invio per l'istanza con id domanda {0} e codice istanza {1}", this._idDomandaFrontoffice , this._idDomandaBackoffice );
				return;
			}

			GeneraCertificatoConCodiceOggetto(_riepilogoDomandaReader.Read(strategiaIndividuazioneRiepilogo));
		}

		private void GeneraCertificatoConCodiceOggetto(string xslTemplate)
		{
			var xmlIstanza = String.Empty;

			if (xslTemplate.Contains(Constants.SegnapostoVisuraStc))
				xmlIstanza = GetXmlIstanzaDaVisuraStc();
			else
				xmlIstanza = GetXmlIstanzaDaVisuraVbg();

			if (this._dumpXmlIstanzaCaricata)
				DumpXmlIstanza(xmlIstanza);

			GeneraRiepilogoDaXmlXsl(xmlIstanza, xslTemplate);
		}

		private void DumpXmlIstanza(string xmlIstanza)
		{
			if(HttpContext.Current == null)
				return;

			var path = HttpContext.Current.Server.MapPath("~/Logs");
			path = Path.Combine(path, "dumpIstanzaCertificato_" + HttpContext.Current.Session.SessionID + ".xml");

			File.WriteAllText( path , xmlIstanza);
		}

		private void GeneraRiepilogoDaXmlXsl(string xmlIstanza, string xslTemplate)
		{
			var file = this._fileConverterService.TrasformaEConverti( xmlIstanza, xslTemplate, "html", "html");

			this._certificatoHtml = new CertificatoDiInvioHtml(file.FileContent, true);
		}

		private string GetXmlIstanzaDaVisuraVbg()
		{
			var iIdDomanda = -1;

			if (!int.TryParse(this._idDomandaBackoffice, out iIdDomanda))
			{
				_log.ErrorFormat("Si sta cercando di effettuare una visura tramite VBG ma il codice istanza passato non è un codice numerico valido. Id Istanza passato: {0}", this._idDomandaBackoffice);
				return String.Empty;
			}

			var istanza = _visuraRepository.GetById(_aliasSoftwareResolver.AliasComune, iIdDomanda, true);

			return (istanza != null) ? IstanzaSigeproAdapter.ConvertiIstanzaPerCompilazioneModello(istanza) : String.Empty;
		}

		private string GetXmlIstanzaDaVisuraStc()
		{
			var esitoVisuraStc = _istanzePresentateRepository.GetDettaglioPratica(_aliasSoftwareResolver.AliasComune, _aliasSoftwareResolver.Software, this._idDomandaBackoffice);

			if (esitoVisuraStc == null || esitoVisuraStc.dettaglioPratica == null)
				return String.Empty;

			return StreamUtils.SerializeClass(esitoVisuraStc.dettaglioPratica);
		}
	}
}

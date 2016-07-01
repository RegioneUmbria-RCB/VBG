// -----------------------------------------------------------------------
// <copyright file="IriepilogoModelloInHtml.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
	using System.Text;
	using System;
	using System.Configuration;
	using System.IO;
	using System.Web;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IRiepilogoModelloInHtml
	{
		string GetHtml();
		BinaryFile ConvertiInPdf( FileConverterService fileConverter, string nomeFile, bool wrapinHtml = true);
	}

	public class RiepilogoModelloInHtmlDaDomanda : IRiepilogoModelloInHtml
	{
		private static class Constants
		{
			// public const string DefaultEncoding = "Windows-1252";
			public const string DefaultEncoding = "utf-8";
			public const string FormatoConversioneModello = "PDF";
			public const string NomeFileModello = "Modello.html";
			public const string NomeFileModelloConvertito = "Modello.pdf";
		}

		DomandaOnline _domanda;
		int _idModello;
		int _indiceMolteplicita;
		IGeneratoreHtmlSchedeDinamiche _generatoreHtml;

		internal RiepilogoModelloInHtmlDaDomanda(IGeneratoreHtmlSchedeDinamiche generatoreHtml, DomandaOnline domanda, int idModello, int indiceMolteplicita = -1)
		{
			this._generatoreHtml = generatoreHtml;
			this._domanda = domanda;
			this._idModello = idModello;
			this._indiceMolteplicita = indiceMolteplicita;
		}

		public string GetHtml()
		{
			return this._generatoreHtml.GeneraHtml(this._domanda, this._idModello, this._indiceMolteplicita);
		}


		public BinaryFile ConvertiInPdf( FileConverterService fileConverter, string nomeFile, bool wrapinHtml = true)
		{
			var html = GetHtml();

			if (wrapinHtml)
			{
				html = String.Format(GenerazioneHtmlDomandaConstants.HtmlWrapper, EncodingUtils.GetEncoding().WebName, html);
			}

			var buffer = EncodingUtils.GetEncoding().GetBytes(html);

			var nomeFileModello = String.IsNullOrEmpty(nomeFile) ? Constants.NomeFileModelloConvertito : nomeFile;

			if (!String.IsNullOrEmpty(ConfigurationManager.AppSettings["RiepiloghiSchedeDinamiche.DumpFile"]) && HttpContext.Current != null)
			{
				var path = HttpContext.Current.Server.MapPath("~/Logs/" + nomeFileModello + ".html");
				File.WriteAllBytes(path, buffer);
			}

			var fileConvertito = fileConverter.Converti(Constants.NomeFileModello, buffer, Constants.FormatoConversioneModello);
			fileConvertito.FileName = nomeFileModello;

			return fileConvertito;
		}
	}

	public class RiepilogoModelloInHtmlFactory
	{
		ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		IGeneratoreHtmlSchedeDinamiche _generatoreHtml;

		public RiepilogoModelloInHtmlFactory(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IGeneratoreHtmlSchedeDinamiche generatoreHtml)
		{
			this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._generatoreHtml = generatoreHtml;
		}

		public IRiepilogoModelloInHtml FromDomanda(DomandaOnline domanda, int idModello, int indiceMolteplicita = -1)
		{
			return new RiepilogoModelloInHtmlDaDomanda( this._generatoreHtml,domanda, idModello, indiceMolteplicita);
		}

		public IRiepilogoModelloInHtml FromIdDomanda(int idDomanda, int idModello, int indiceMolteplicita = -1)
		{
			var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

			return this.FromDomanda( domanda, idModello, indiceMolteplicita );
		}

	}
}

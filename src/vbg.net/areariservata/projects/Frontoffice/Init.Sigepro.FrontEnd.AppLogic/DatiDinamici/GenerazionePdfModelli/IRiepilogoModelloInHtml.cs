// -----------------------------------------------------------------------
// <copyright file="IriepilogoModelloInHtml.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
    using System;
    using System.Configuration;
    using System.IO;
    using System.Web;
    using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface IRiepilogoModelloInHtml
	{
		string GetHtml();
        BinaryFile ConvertiInPdf(IHtmlToPdfFileConverter fileConverter, string nomeFile, bool wrapinHtml = true);
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
            public const string DumpHtmlAppsettingsKey = "RiepiloghiSchedeDinamiche.DumpFile";
            public const string DumpHtmlPath = "~/Logs/{0}.html";
		}

        IDatiDinamiciRiepilogoReader _datiReader;
		int _idModello;
		int _indiceMolteplicita;
		IGeneratoreHtmlSchedeDinamiche _generatoreHtml;

		internal RiepilogoModelloInHtmlDaDomanda(IGeneratoreHtmlSchedeDinamiche generatoreHtml, IDatiDinamiciRiepilogoReader datiReader, int idModello, int indiceMolteplicita = -1)
		{
			this._generatoreHtml = generatoreHtml;
			this._datiReader = datiReader;
			this._idModello = idModello;
			this._indiceMolteplicita = indiceMolteplicita;
		}

		public string GetHtml()
		{
			return this._generatoreHtml.GeneraHtml(this._datiReader, this._idModello, this._indiceMolteplicita);
		}


        public BinaryFile ConvertiInPdf(IHtmlToPdfFileConverter fileConverter, string nomeFile, bool wrapinHtml = true)
		{
			var html = GetHtml();

			if (wrapinHtml)
			{
				html = String.Format(GenerazioneHtmlDomandaConstants.HtmlWrapper, EncodingUtils.GetEncoding().WebName, html);
			}

            var nomeFileModello = String.IsNullOrEmpty(nomeFile) ? Constants.NomeFileModelloConvertito : nomeFile;

            DumpHtml(nomeFileModello, html);

            var fileConvertito = fileConverter.Converti(nomeFileModello, html);

			return fileConvertito;
		}

        private void DumpHtml(string nomeFileModello, string html)
        {
            if (!String.IsNullOrEmpty(ConfigurationManager.AppSettings[Constants.DumpHtmlAppsettingsKey]) && HttpContext.Current != null)
            {
                var path = HttpContext.Current.Server.MapPath(String.Format(Constants.DumpHtmlPath, nomeFileModello));
                File.WriteAllText(path, html);
            }
        }

	}

    
	public class RiepilogoModelloInHtmlFactory
	{
		ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		IGeneratoreHtmlSchedeDinamiche _generatoreHtml;
        IDatiDinamiciRepository _datiDinamiciRepository;

		public RiepilogoModelloInHtmlFactory(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IGeneratoreHtmlSchedeDinamiche generatoreHtml, IDatiDinamiciRepository datiDinamiciRepository)
		{
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._generatoreHtml = generatoreHtml;
            this._datiDinamiciRepository = datiDinamiciRepository;
		}

		public IRiepilogoModelloInHtml FromDomanda(IDatiDinamiciRiepilogoReader reader, int idModello, int indiceMolteplicita = -1)
		{
			return new RiepilogoModelloInHtmlDaDomanda( this._generatoreHtml,reader, idModello, indiceMolteplicita);
		}

		public IRiepilogoModelloInHtml FromIdDomandaOnline(int idDomanda, int idModello, int indiceMolteplicita = -1)
		{
			var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var reader = new DomandaOnlineDatiDinamiciReader(domanda, this._datiDinamiciRepository);


            return this.FromDomanda( reader, idModello, indiceMolteplicita );
		}

	}
}

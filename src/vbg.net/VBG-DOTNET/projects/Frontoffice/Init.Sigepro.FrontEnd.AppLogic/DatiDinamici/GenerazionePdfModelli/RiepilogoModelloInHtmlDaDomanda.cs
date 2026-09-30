// -----------------------------------------------------------------------
// <copyright file="IriepilogoModelloInHtml.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli.GestioneTemplateSingolaScheda.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using Microsoft.VisualStudio.Threading;
using System;
using System.IO;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    public class RiepilogoModelloInHtmlDaDomanda : IRiepilogoModelloInHtml
    {
        private static class Constants
        {
            public const string NomeFileModelloConvertito = "Modello.pdf";
            public const string DumpHtmlAppsettingsKey = "RiepiloghiSchedeDinamiche.DumpFile";
            public const string DumpHtmlPath = "~/Logs/{0}.html";
        }

        private readonly ISchedeDinamicheDomandaAlRiepilogoService _datiReader;
        private readonly IPathMapper _pathMapper;
        private readonly IAppConfigurationReader _appConfigurationReader;
        private readonly IConfigurazione<ParametriRiepilogoSingolaScheda> _configurazioneRiepiloghi;
        private readonly IHtmlToPdfAsyncFileConverter _fileConverter;
        private readonly int _idModello;
        private readonly int _indiceMolteplicita;
        private readonly IGeneratoreHtmlSchedeDinamiche _generatoreHtml;
        private readonly GenerazioneHtmlDomandaConstants _constants;

        internal RiepilogoModelloInHtmlDaDomanda(IGeneratoreHtmlSchedeDinamiche generatoreHtml, ISchedeDinamicheDomandaAlRiepilogoService datiReader,
            IPathMapper pathMapper, IAppConfigurationReader appConfigurationReader,
            IConfigurazione<ParametriRiepilogoSingolaScheda> configurazioneRiepiloghi, IHtmlToPdfAsyncFileConverter fileConverter,
            int idModello, int indiceMolteplicita = -1)
        {
            this._generatoreHtml = generatoreHtml;
            this._datiReader = datiReader;
            this._pathMapper = pathMapper;
            this._appConfigurationReader = appConfigurationReader;
            this._configurazioneRiepiloghi = configurazioneRiepiloghi;
            this._fileConverter = fileConverter;
            this._idModello = idModello;
            this._indiceMolteplicita = indiceMolteplicita;
            this._constants = new GenerazioneHtmlDomandaConstants(appConfigurationReader);
        }

        public async Task<BinaryFile> ConvertiInPdfAsync(string fileName, bool wrapinHtml = true)
        {
            this._generatoreHtml.IgnoraCssDefault = true;

            var html = await this._generatoreHtml.GeneraHtmlAsync(this._datiReader, this._idModello, this._indiceMolteplicita).ConfigureAwait(false);

            if (wrapinHtml)
            {
                html = this._configurazioneRiepiloghi.Parametri.TemplateRiepilogoSchedaMovimento.ApplicaAdHtmlScheda(this._constants.CssModelliDinamici, html);
            }

            var nomeFile = String.IsNullOrEmpty(fileName) ? Constants.NomeFileModelloConvertito : fileName;

            if (!String.IsNullOrEmpty(this._appConfigurationReader.GetSetting(Constants.DumpHtmlAppsettingsKey)))
            {
                this.DumpHtml(nomeFile, html);
            }

            var fileConvertito = await this._fileConverter.ConvertiAsync(nomeFile, html).ConfigureAwait(false);

            return fileConvertito;
        }


        public BinaryFile ConvertiInPdf(string fileName, bool wrapinHtml = true)
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());
            return jtf.Run(() => this.ConvertiInPdfAsync(fileName, wrapinHtml));
        }

        private void DumpHtml(string nomeFileModello, string html)
        {
            var path = this._pathMapper.MapPath(String.Format(Constants.DumpHtmlPath, nomeFileModello));
            File.WriteAllText(path, html);
        }

    }
}

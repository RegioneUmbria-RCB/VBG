using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli.GestioneTemplateSingolaScheda.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Microsoft.VisualStudio.Threading;
using System;
using System.Threading.Tasks;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche
{
    public class GenerazioneRiepilogoSchedeDinamicheService : IGenerazioneRiepilogoSchedeDinamicheService
    {

        private readonly IHtmlToPdfAsyncFileConverter _fileConverter;
        private readonly IModelloDinamicoHtmlRenderer _modelloDinamicoHtmlRenderer;
        private readonly IModelliDinamiciFactory _modelliDinamiciFactory;
        private readonly SchedeMovimentiLoaderFactory _schedeMovimentiLoaderFactory;
        private readonly IConfigurazione<ParametriRiepilogoSingolaScheda> _configurazione;
        private readonly GenerazioneHtmlDomandaConstants _constants;

        private static class Constants
        {
            public const string NomeFiledaConvertire = "riepilogoScheda.htm";
        }

        public GenerazioneRiepilogoSchedeDinamicheService(IHtmlToPdfAsyncFileConverter fileConverter,
                                                            IAppConfigurationReader appConfigurationReader,
                                                            IModelloDinamicoHtmlRenderer modelloDinamicoHtmlRenderer,
                                                            IModelliDinamiciFactory modelliDinamiciFactory,
                                                            SchedeMovimentiLoaderFactory schedeMovimentiLoaderFactory,
                                                            IConfigurazione<ParametriRiepilogoSingolaScheda> configurazione)
        {
            this._modelloDinamicoHtmlRenderer = modelloDinamicoHtmlRenderer;
            this._modelliDinamiciFactory = modelliDinamiciFactory;
            this._schedeMovimentiLoaderFactory = schedeMovimentiLoaderFactory;
            this._configurazione = configurazione;
            this._fileConverter = fileConverter;
            this._constants = new GenerazioneHtmlDomandaConstants(appConfigurationReader);
        }

        public BinaryFile GeneraRiepilogoScheda(MovimentoDaEffettuare movimento, int idScheda, string forzaNomeFile = "")
        {
            //var loader = this.CreateLoader(idScheda, movimento);
            //var scheda = this._modelliDinamiciFactory.CreaModelloIstanza(loader, idScheda, 0, false);

            //scheda.EseguiScriptCaricamento();

            //var htmlScheda = this._modelloDinamicoHtmlRenderer.GetHtml(scheda);
            //var html = String.Format(Constants.HtmlWrapper, this._constants.CssModelliDinamici, htmlScheda);

            //return this._htmlToPdf.Converti(String.IsNullOrEmpty(forzaNomeFile) ? Constants.NomeFiledaConvertire : forzaNomeFile, html);

            var jtf = new JoinableTaskFactory(new JoinableTaskContext());

            return jtf.Run(() => this.GeneraRiepilogoSchedaAsync(movimento, idScheda, forzaNomeFile));
        }



        public async Task<BinaryFile> GeneraRiepilogoSchedaAsync(MovimentoDaEffettuare movimento, int idScheda, string forzaNomeFile = "")
        {
            var loader = this._schedeMovimentiLoaderFactory.CreateLoader(idScheda, movimento);
            var scheda = this._modelliDinamiciFactory.CreaModelloIstanza(loader, idScheda, 0, false);
            scheda.EseguiScriptCaricamento();
            var listaCampiNonVisibili = scheda.GetIdCampiNonVisibiliDopoModifiche();
            var htmlScheda = await this._modelloDinamicoHtmlRenderer.GetHtmlAsync(scheda, new CampiNonVisibili(listaCampiNonVisibili));

            var html = this._configurazione.Parametri.TemplateRiepilogoSchedaMovimento.ApplicaAdHtmlScheda(this._constants.CssModelliDinamici, htmlScheda);

            return await this._fileConverter.ConvertiAsync(String.IsNullOrEmpty(forzaNomeFile) ? Constants.NomeFiledaConvertire : forzaNomeFile, html);
        }
    }
}

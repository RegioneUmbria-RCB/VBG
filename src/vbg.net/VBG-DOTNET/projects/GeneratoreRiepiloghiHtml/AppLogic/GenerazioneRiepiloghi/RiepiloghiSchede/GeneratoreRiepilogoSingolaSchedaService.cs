using GeneratoreRiepiloghiHtml.AppLogic.Authorization;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede.FileConverter;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneDatiDinamici;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneIstanze;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti;
using GeneratoreRiepiloghiHtml.AppLogic.Visura;
using System.Text.RegularExpressions;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiSchede
{
    public class GeneratoreRiepilogoSingolaSchedaService
    {
        private readonly IVisuraService _visuraService;
        private readonly IStrutturaSchedeDinamicheService _strutturaSchedeDinamicheService;
        private readonly IUserIdentityService _userIdentityService;
        private readonly IModelliDinamiciFactory _modelliDinamiciFactory;
        private readonly IModelloDinamicoHtmlRenderer _modelloDinamicoHtmlRenderer;
        private readonly IHtmlToPdfFileConverter _htmlToPdf;
        private readonly ILogger<GeneratoreRiepilogoSingolaSchedaService> _logger;

        public GeneratoreRiepilogoSingolaSchedaService(IVisuraService visuraService, IStrutturaSchedeDinamicheService strutturaSchedeDinamicheService, IUserIdentityService userIdentityService,
            IModelliDinamiciFactory modelliDinamiciFactory, IModelloDinamicoHtmlRenderer modelloDinamicoHtmlRenderer, IHtmlToPdfFileConverter htmlToPdf, ILogger<GeneratoreRiepilogoSingolaSchedaService> logger)
        {
            this._visuraService = visuraService;
            this._strutturaSchedeDinamicheService = strutturaSchedeDinamicheService;
            this._userIdentityService = userIdentityService;
            this._modelliDinamiciFactory = modelliDinamiciFactory;
            this._modelloDinamicoHtmlRenderer = modelloDinamicoHtmlRenderer;
            this._htmlToPdf = htmlToPdf;
            this._logger = logger;
        }

        public async Task<BinaryFile> GeneraRiepilogoSchedaAsync(int codiceIstanza, int idScheda, Dictionary<int, List<IValoreCampo>> valoriCampi)
        {
            var istanza = await this._visuraService.GetDettaglioPraticaAsync(codiceIstanza);

            return await this.GeneraRiepilogoSchedaAsync(istanza, idScheda, valoriCampi);
        }

        public async Task<BinaryFile> GeneraRiepilogoSchedaAsync(int codiceIstanza, int idScheda)
        {
            var istanza = await this._visuraService.GetDettaglioPraticaAsync(codiceIstanza);

            var valoriCampi = istanza.GetValoriDatiDinamici();

            return await this.GeneraRiepilogoSchedaAsync(istanza, idScheda, valoriCampi);
        }


        public async Task<BinaryFile> GeneraRiepilogoSchedaAsync(Istanze istanza, int idScheda, Dictionary<int, List<IValoreCampo>> valoriCampi)
        {
            using (var scope = this._logger.BeginScope("Generazione del riepilogo per la scheda {@idScheda} dell'istanza {@codiceIstanza}", idScheda, istanza.CODICEISTANZA))
            {
                try
                {
                    var scheda = await this.GeneraModelloSchedaAsync(istanza, idScheda, valoriCampi);

                    try
                    {
                        scheda.EseguiScriptCaricamento();
                    }
                    catch (Exception ex)
                    {
                        this._logger.LogError("Errore nello script di caricamento, la visualizzazione del file potrebbe non essere corretta: {@ex}", ex);
                    }

                    var html = await this.GetHtmlSchedaAsync(scheda, true);

                    var converted = await this._htmlToPdf.ConvertiAsync($"{SafeFileName(scheda.NomeModello)}.pdf", html);

                    return converted;
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore nella generazione della scheda  {@idScheda} dell'istanza {@codiceIstanza}: {@ex}", idScheda, istanza.CODICEISTANZA, ex);

                    throw;
                }
            }
        }

        public async Task<string> GeneraHtmlSchedaAsync(Istanze istanza, int idScheda, bool wrapHtml)
        {
            var valoriCampi = istanza.GetValoriDatiDinamici();
            var scheda = await this.GeneraModelloSchedaAsync(istanza, idScheda, valoriCampi);
            try
            {
                scheda.EseguiScriptCaricamento();
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore nello script di caricamento, la visualizzazione del file potrebbe non essere corretta: {@ex}", ex);
            }

            return await this.GetHtmlSchedaAsync(scheda, wrapHtml);
        }

        private async Task<string> GetHtmlSchedaAsync(ModelloDinamicoIstanza modello, bool wrapHtml)
        {
            var htmlScheda = $"<h3 class=\"titolo-scheda\">{modello.NomeModello}</h3>{(await this._modelloDinamicoHtmlRenderer.GetHtmlAsync(modello))}";
            return wrapHtml ? SingolaSchedaHtmlWrapper.Wrap(htmlScheda) : htmlScheda;
        }

        private async Task<ModelloDinamicoIstanza> GeneraModelloSchedaAsync(Istanze istanza, int idScheda, Dictionary<int, List<IValoreCampo>> valoriCampi)
        {
            // Parametri condivisi tra tutte le schede
            var classLoader = new ClassLoader(istanza);
            var queryLocalizzazioni = new QueryLocalizzazioniFactory(istanza);
            var repository = new DatiDinamiciReadonlyRepository(valoriCampi);

            var currentUser = this._userIdentityService.GetUserClaims()!;

            var struttura = await this._strutturaSchedeDinamicheService.GetStrutturaModelloAsync(idScheda);
            var builder = new ModelloDinamicoLoaderBuilder();

            var loader = builder.UsaStruttura(struttura)
                                  .UsaLoaderClasseContesto(classLoader)
                                  .UsaQueryLocalizzazioni(queryLocalizzazioni)
                                  .UsaRepository(repository)
                                  .UsaToken(currentUser.Token)
                                  .Build(currentUser.Alias, ContestoScriptEnum.GenerazioneRiepiloghi);

            var scheda = this._modelliDinamiciFactory.CreaModelloIstanza(loader, idScheda, 0, false);
            return scheda;
        }

        private static string SafeFileName(string input)
        {
            if (string.IsNullOrEmpty(input))
                return input;

            // Pattern che trova tutti i caratteri che non sono lettere o numeri
            var pattern = @"[^a-zA-Z0-9]";

            // Sostituisce tutti i caratteri non alfanumerici con underscore
            var result = Regex.Replace(input, pattern, "_");

            return result;
        }
    }
}

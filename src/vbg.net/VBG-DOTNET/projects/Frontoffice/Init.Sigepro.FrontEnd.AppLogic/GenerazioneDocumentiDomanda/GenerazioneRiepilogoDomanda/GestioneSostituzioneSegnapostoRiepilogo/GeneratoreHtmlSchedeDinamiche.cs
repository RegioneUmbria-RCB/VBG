using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using VBG.DatiDinamici;
using VBG.DatiDinamici.VisibilitaCampi;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class GeneratoreHtmlSchedeDinamiche : IGeneratoreHtmlSchedeDinamiche
    {
        private static class Constants
        {
            public const string TitoloSchedaWrapper = "<div class='titoloSchedaDinamica'>{0}</div>";
            public const string SeparatoreSchede = "<br /><hr /><br />";
        }

        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IModelloDinamicoHtmlRenderer _modelloDinamicoHtmlRenderer;
        private readonly IModelliDinamiciFactory _modelliDinamiciFactory;
        private bool _primoSegnapostoInserito = false;
        private readonly GenerazioneHtmlDomandaConstants _generazioneHtmlDomandaConstants;

        public bool IgnoraCssDefault { get; set; } = false;

        public GeneratoreHtmlSchedeDinamiche(
            ITokenApplicazioneService tokenApplicazioneService,
            IAppConfigurationReader appConfigurationReader, IModelloDinamicoHtmlRenderer modelloDinamicoHtmlRenderer,
            IModelliDinamiciFactory modelliDinamiciFactory)
        {
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._modelloDinamicoHtmlRenderer = modelloDinamicoHtmlRenderer;
            this._modelliDinamiciFactory = modelliDinamiciFactory;
            this._generazioneHtmlDomandaConstants = new GenerazioneHtmlDomandaConstants(appConfigurationReader);
        }

        #region IGeneratoreHtmlSchedeDinamiche Members

        public void Inizializza()
        {
            this._primoSegnapostoInserito = false;
        }

        /// <summary>
        /// Genera l'html di una scheda dinamica ottenibile dal reader dato l'id della scheda e l'indice molteplicità.
        /// </summary>
        /// <param name="datiDinamiciReader">reader che permette di astrarre la base dati in cui sono contenute le informazioni della scheda</param>
        /// <param name="idScheda">Id della scheda da renderizzare</param>
        /// <param name="indiceMolteplicita">Indice moltiplità di cui stampare la scheda. Se != da -1 genera l'intera scheda anche se questa cintiene più blocchi multipli</param>
        /// <returns></returns>
        public string GeneraHtml(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idScheda, int indiceMolteplicita = -1)
        {
            var temp = datiDinamiciReader.GetListaModelli().Where(x => x.IdModello == idScheda).Select(x => x.IdModello);
            var idmodello = temp.FirstOrDefault();

            if (!temp.Any())
            {
                if (datiDinamiciReader.PuoCaricareSchedeNonPresenti)
                {
                    idmodello = idScheda;
                }
                else
                {
                    return String.Empty;

                }
            }

            //if (!modello.Compilato)
            //   return String.Empty;
            var html = this.WriteHtml(datiDinamiciReader, idmodello, indiceMolteplicita);

            return this.GetStringaCss() + String.Format(this._generazioneHtmlDomandaConstants.WrappingDivHtml, html);
        }

        public async Task<string> GeneraHtmlAsync(ISchedeDinamicheDomandaAlRiepilogoService schedeDinamicheReader, int idScheda, int indiceMolteplicita = -1)
        {
            var temp = schedeDinamicheReader.GetListaModelli().Where(x => x.IdModello == idScheda).Select(x => x.IdModello);
            var idmodello = temp.FirstOrDefault();

            if (!temp.Any())
            {
                if (schedeDinamicheReader.PuoCaricareSchedeNonPresenti)
                {
                    idmodello = idScheda;
                }
                else
                {
                    return String.Empty;

                }
            }

            var html = await this.WriteHtmlAsync(schedeDinamicheReader, idmodello, indiceMolteplicita);

            return this.GetStringaCss() + String.Format(this._generazioneHtmlDomandaConstants.WrappingDivHtml, html);
        }

        // Genera l'html di tutte le schede dell'istanza
        public string GeneraHtmlDelleSchedeDellaDomanda(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return this.HtmlSchedeDaSelettore(datiDinamiciReader.GetListaModelli, datiDinamiciReader, options);
        }

        public async Task<string> GeneraHtmlDelleSchedeDellaDomandaAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return await this.HtmlSchedeDaSelettoreAsync(datiDinamiciReader.GetListaModelli, datiDinamiciReader, options);
        }

        // Genera l'html di tutte le schede dell'istanza
        public string GeneraHtmlSchedeIntervento(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return this.HtmlSchedeDaSelettore(datiDinamiciReader.GetListaModelliIntervento, datiDinamiciReader, options);
        }

        public async Task<string> GeneraHtmlSchedeInterventoAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return await this.HtmlSchedeDaSelettoreAsync(datiDinamiciReader.GetListaModelliIntervento, datiDinamiciReader, options);
        }

        public string GeneraHtmlSchedaEndoprocedimento(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idEndo)
        {
            var sb = new StringBuilder();

            var listaIdModelliEndo = datiDinamiciReader.GetListaModelliEndo(idEndo)
                                                        .Where(modello => modello.Compilato).Select(x => x.IdModello);

            foreach (var idModello in listaIdModelliEndo)
            {
                sb.Append(this.WriteHtml(datiDinamiciReader, idModello));
            }

            return sb.ToString();
        }

        public async Task<string> GeneraHtmlSchedaEndoprocedimentoAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idEndo)
        {
            var sb = new StringBuilder();

            var listaIdModelliEndo = datiDinamiciReader.GetListaModelliEndo(idEndo)
                                                        .Where(modello => modello.Compilato).Select(x => x.IdModello);

            foreach (var idModello in listaIdModelliEndo)
            {
                sb.Append(await this.WriteHtmlAsync(datiDinamiciReader, idModello));
            }

            return sb.ToString();
        }

        private string HtmlSchedeDaSelettore(Func<IEnumerable<IModelloDinamicoRiepilogo>> selettoreSchede, ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            var htmlSchede = selettoreSchede()
                                    .Where(modello => modello != null && modello.Compilato)
                                    .Where(modello => !(options == GenerazioneHtmlSchedeOptions.SoloSchedeCheNonNecessitanoFirma && modello.TipoFirma != 0))
                                    .Select(modello => this.WriteHtml(datiDinamiciReader, modello.IdModello));

            var html = String.Join(Environment.NewLine, htmlSchede.ToArray());

            return this.GetStringaCss() + html;
        }

        private async Task<string> HtmlSchedeDaSelettoreAsync(Func<IEnumerable<IModelloDinamicoRiepilogo>> selettoreSchede, ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {

            var htmlSchede = new List<string>();
            var modelli = selettoreSchede()
                                    .Where(modello => modello != null && modello.Compilato)
                                    .Where(modello => !(options == GenerazioneHtmlSchedeOptions.SoloSchedeCheNonNecessitanoFirma && modello.TipoFirma != 0))
                                    .ToArray();

            foreach (var modello in modelli)
            {
                htmlSchede.Add(await this.WriteHtmlAsync(datiDinamiciReader, modello.IdModello));
            }

            var html = String.Join(Environment.NewLine, htmlSchede.ToArray());

            return this.GetStringaCss() + html;
        }

        #endregion

        private class SchedaRenderizzabile
        {
            public ModelloDinamicoIstanza Modello { get; set; }
            public CampiNonVisibili CampiNascosti { get; set; }
        }

        private IEnumerable<SchedaRenderizzabile> GetSchedeRenderizzabili(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idModello, int indiceMolteplicita = -1)
        {
            var indiciSchede = datiDinamiciReader.GetIndiciSchede(idModello);

            return indiciSchede.Select(indiceScheda =>
            {
                var scheda = this.GeneraScheda(datiDinamiciReader, idModello, indiceScheda, indiceMolteplicita);
                var campiNascosti = LeggiCampiNascostiScheda(datiDinamiciReader, idModello, scheda);

                if (indiceMolteplicita != -1)
                {
                    // Nella stampa a singoli blocchi i campi con molteplicità vengono trattati come se fossero tutti all'indice 0
                    // Per questo motivo, se stiamo generando l'html di un singolo blocco, dobbiamo ricalcolare i campi nascosti in
                    // modo da nascondere solo quelli che sono effettivamente non visibili per quel blocco
                    var campiDaNascondereRicalcolati = campiNascosti.GetIdCampiNonVisibili()
                                                                    .Where(x => x.IndiceMolteplicita == indiceMolteplicita)
                                                                    .Select(x => new IdValoreCampo(x.Id, 0)); // <-- Forzo l'indice molteplicità del campo nascosto a 0, in modo che venga nascosto anche nella stampa a singolo blocco

                    campiNascosti = new CampiNonVisibili(campiDaNascondereRicalcolati);
                }

                return new SchedaRenderizzabile
                {
                    Modello = scheda,
                    CampiNascosti = campiNascosti
                };
            });
        }

        private static CampiNonVisibili LeggiCampiNascostiScheda(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idModello, ModelloDinamicoIstanza scheda)
        {
            if (datiDinamiciReader.SupportaCachingCampiNonVisibili)
            {
                return datiDinamiciReader.GetCampiNonVisibili(idModello);
            }
            else
            {
                scheda.EseguiScriptCaricamento();
                return new CampiNonVisibili(scheda.GetIdCampiNonVisibiliDopoModifiche());
            }
        }

        private string WriteHtml(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idModello, int indiceMolteplicita = -1)
        {
            var htmlSchedeConIndici = this.GetSchedeRenderizzabili(datiDinamiciReader, idModello, indiceMolteplicita)
                                            .Select(x => this._modelloDinamicoHtmlRenderer.GetHtml(x.Modello, x.CampiNascosti));

            return string.Join(Constants.SeparatoreSchede, htmlSchedeConIndici.ToArray());
        }

        private async Task<string> WriteHtmlAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idModello, int indiceMolteplicita = -1)
        {
            var schedeRenderizzabili = this.GetSchedeRenderizzabili(datiDinamiciReader, idModello, indiceMolteplicita);
            var htmlSchedeConIndici = new List<string>();

            foreach (var scheda in schedeRenderizzabili)
            {
                var html = await this._modelloDinamicoHtmlRenderer.GetHtmlAsync(scheda.Modello, scheda.CampiNascosti);

                htmlSchedeConIndici.Add(html);
            }

            return string.Join(Constants.SeparatoreSchede, htmlSchedeConIndici.ToArray());
        }

        private ModelloDinamicoIstanza GeneraScheda(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idScheda, int indiceScheda, int indiceMolteplicita)
        {
            var loader = datiDinamiciReader.CreateLoader(idScheda, indiceMolteplicita, this._tokenApplicazioneService);

            return this._modelliDinamiciFactory.CreaModelloIstanza(loader, idScheda, indiceScheda, false);
        }

        private string GetStringaCss()
        {
            if (this.IgnoraCssDefault || this._primoSegnapostoInserito)
            {
                return String.Empty;
            }

            this._primoSegnapostoInserito = true;

            return this._generazioneHtmlDomandaConstants.CssModelliDinamici;
        }
    }
}

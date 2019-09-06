using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Text;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class GeneratoreHtmlSchedeDinamiche : IGeneratoreHtmlSchedeDinamiche
    {
        private static class Constants
        {
            public const string TitoloSchedaWrapper = "<div class='titoloSchedaDinamica'>{0}</div>";
            public const string SeparatoreSchede = "<br /><hr /><br />";
        }

        ITokenApplicazioneService _tokenApplicazioneService;
        IStrutturaModelloReader _strutturaReader;
        bool _primoSegnapostoTrovato = true;

        public GeneratoreHtmlSchedeDinamiche(ITokenApplicazioneService tokenApplicazioneService, IStrutturaModelloReader strutturaReader)
        {
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._strutturaReader = strutturaReader;
        }

        #region IGeneratoreHtmlSchedeDinamiche Members

        public string GeneraHtml(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idScheda, int indiceMolteplicita)
        {
            //var modello = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.IdModello == idScheda).FirstOrDefault();
            var modello = datiDinamiciReader.GetListaModelli().Where(x => x.IdModello == idScheda).FirstOrDefault();

            if (modello == null)
                return String.Empty;

            if (!modello.Compilato)
                return String.Empty;

            return GetStringaCss() + WrapHtml(GenerazioneHtmlDomandaConstants.WrappingDivHtml, () =>
            {
                return WriteHtml(datiDinamiciReader, modello, indiceMolteplicita);
            });
        }

        // Genera l'html di una sola scheda dell'istanza
        public string GeneraHtml(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idScheda)
        {
            return GeneraHtml(datiDinamiciReader, idScheda, -1);
        }

        // Genera l'html di tutte le schede dell'istanza
        public string GeneraHtmlDelleSchedeDellaDomanda(IDatiDinamiciRiepilogoReader datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return GetStringaCss() + WrapHtml(GenerazioneHtmlDomandaConstants.WrappingDivHtml, () =>
            {
                var sb = new StringBuilder();

                //var modelli = domanda
                //				.ReadInterface
                //				.DatiDinamici
                //				.Modelli
                //				.Where(m => m.Compilato)
                //				.Select(x => x.EstraiOrdine(domanda.ReadInterface.DatiDinamici))
                //				.OrderBy(m => m.Ordine)
                //				.Select(m => m.Modello);				

                var modelli = datiDinamiciReader.GetListaModelli();

                foreach (var modello in modelli)
                {
                    if (!modello.Compilato)
                        continue;

                    // Devo riportare solamente i modelli dinamici che non richiedono la firma
                    if (options == GenerazioneHtmlSchedeOptions.SoloSchedeCheNonNecessitanoFirma && modello.TipoFirma != 0)
                        continue;

                    sb.Append(WriteHtml(datiDinamiciReader, modello));
                }

                return sb.ToString();
            });
        }

        // Genera l'html di tutte le schede dell'istanza
        public string GeneraHtmlSchedeIntervento(IDatiDinamiciRiepilogoReader datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return GetStringaCss() + WrapHtml(GenerazioneHtmlDomandaConstants.WrappingDivHtml, () =>
            {
                var sb = new StringBuilder();

                var modelli = datiDinamiciReader.GetListaModelliIntervento();

                foreach (var modello in modelli)
                {
                    if (!modello.Compilato)
                        continue;

                    // Devo riportare solamente i modelli dinamici che non richiedono la firma
                    if (options == GenerazioneHtmlSchedeOptions.SoloSchedeCheNonNecessitanoFirma && modello.TipoFirma != 0)
                        continue;

                    sb.Append(WriteHtml(datiDinamiciReader, modello));
                }

                return sb.ToString();
            });
        }

        public string GeneraHtmlScheda(ModelloDinamicoBase scheda, ICampiNonVisibili campiNonVisibili = null)
        {
            if (campiNonVisibili == null)
                campiNonVisibili = CampiNonVisibili.TuttiICampiVisibili;

            var stringaCss = _primoSegnapostoTrovato ? GenerazioneHtmlDomandaConstants.CssModelliDinamici : String.Empty;

            var renderer = new ModelloDinamicoRenderer
            {
                ID = "renderer",
                ReadOnly = true,
                DataSource = scheda,
                CampiNascosti = campiNonVisibili
            };

            renderer.DataBind();

            var stringWriter = new StringWriter();
            var tw = new HtmlTextWriter(stringWriter);

            renderer.RenderControl(tw);

            var htmlScheda = stringWriter.ToString();

            if (ConfigurationManager.AppSettings["RiepiloghiSchedeDinamiche.MostraTitoliSchede"]?.ToUpper()== "TRUE")
            {
                htmlScheda = $"<h2 class='titolo-scheda'>{scheda.NomeModello}</h2>" + htmlScheda;
            }

            return htmlScheda;

        }
        #endregion

        private string WriteHtml(IDatiDinamiciRiepilogoReader datiDinamiciReader, IModelloDinamicoRiepilogo modello, int indiceMolteplicita = -1)
        {
            var sb = new StringBuilder();
            var idModello = modello.IdModello;

            foreach (var indiceScheda in datiDinamiciReader.GetIndiciSchede(idModello, this._strutturaReader))
            {
                var scheda = GeneraScheda(datiDinamiciReader, idModello, indiceScheda, indiceMolteplicita);
                // var campiNascosti = domanda.ReadInterface.DatiDinamici.GetCampiNonVisibili(idModello);
                var campiNascosti = datiDinamiciReader.GetCampiNonVisibili(idModello);

                sb.Append(GeneraHtmlScheda(scheda, new CampiNonVisibili(campiNascosti)));
                sb.Append(Constants.SeparatoreSchede);
            }

            return sb.ToString();
        }

        private string WrapHtml(string wrapper, Func<string> function)
        {
            return String.Format(wrapper, function());
        }

        private ModelloDinamicoIstanza GeneraScheda(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idScheda, int indiceScheda, int indiceMolteplicita)
        {
            var dap = (indiceMolteplicita == -1) ? 
                datiDinamiciReader.CreateDataAccessProvider(idScheda, _tokenApplicazioneService) :
                datiDinamiciReader.CreateDataAccessProviderStampaMolteplicita(idScheda, indiceMolteplicita, _tokenApplicazioneService);

            var loader = new ModelloDinamicoLoader(dap, datiDinamiciReader.GetIdComune(), ModelloDinamicoLoader.TipoModelloDinamicoEnum.Frontoffice);

            return new ModelloDinamicoIstanza(loader, idScheda, datiDinamiciReader.GetCodiceIstanza(), indiceScheda, false);
        }

        private string GetStringaCss()
        {
            var stringaCss = this._primoSegnapostoTrovato ? GenerazioneHtmlDomandaConstants.CssModelliDinamici : String.Empty;

            //this._primoSegnapostoTrovato = false;

            return stringaCss;
        }

        public string GeneraHtmlSchedaEndoprocedimento(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idEndo)
        {
            var sb = new StringBuilder();

            var modelli = datiDinamiciReader.GetListaModelliEndo(idEndo);

            foreach (var modello in modelli)
            {
                if (!modello.Compilato)
                    continue;

                sb.Append(WriteHtml(datiDinamiciReader, modello));
            }

            return sb.ToString();
        }
    }
}

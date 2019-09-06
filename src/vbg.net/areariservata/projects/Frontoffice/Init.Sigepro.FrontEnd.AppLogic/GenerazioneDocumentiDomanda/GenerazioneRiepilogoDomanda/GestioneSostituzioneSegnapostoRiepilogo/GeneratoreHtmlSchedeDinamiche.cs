using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.SIGePro.DatiDinamici;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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
        bool _primoSegnapostoInserito = false;

        public GeneratoreHtmlSchedeDinamiche(ITokenApplicazioneService tokenApplicazioneService, IStrutturaModelloReader strutturaReader)
        {
            _tokenApplicazioneService = tokenApplicazioneService;
            _strutturaReader = strutturaReader;
        }

        #region IGeneratoreHtmlSchedeDinamiche Members

        /// <summary>
        /// Genera l'html di una scheda dinamica ottenibile dal reader dato l'id della scheda e l'indice molteplicità.
        /// </summary>
        /// <param name="datiDinamiciReader">reader che permette di astrarre la base dati in cui sono contenute le informazioni della scheda</param>
        /// <param name="idScheda">Id della scheda da renderizzare</param>
        /// <param name="indiceMolteplicita">Indice moltiplità di cui stampare la scheda. Se != da -1 genera l'intera scheda anche se questa cintiene più blocchi multipli</param>
        /// <returns></returns>
        public string GeneraHtml(IDatiDinamiciRiepilogoReader datiDinamiciReader, int idScheda, int indiceMolteplicita=-1)
        {
            var modello = datiDinamiciReader.GetListaModelli().Where(x => x.IdModello == idScheda).FirstOrDefault();

            if (modello == null)
            {
                if  (datiDinamiciReader.PuoCaricareSchedeNonPresenti)
                {
                    modello = datiDinamiciReader.CaricaSchedaNonPresenteDaId(idScheda);
                }
                else
                {
                    return String.Empty;

                }
            }

            //if (!modello.Compilato)
            //   return String.Empty;
            var html = WriteHtml(datiDinamiciReader, modello, indiceMolteplicita);

            return GetStringaCss() + String.Format(GenerazioneHtmlDomandaConstants.WrappingDivHtml, html);
        }

        // Genera l'html di tutte le schede dell'istanza
        public string GeneraHtmlDelleSchedeDellaDomanda(IDatiDinamiciRiepilogoReader datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return HtmlSchedeDaSelettore(datiDinamiciReader.GetListaModelli, datiDinamiciReader, options);

            /*
            return GetStringaCss() + WrapHtml(GenerazioneHtmlDomandaConstants.WrappingDivHtml, () =>
            {
                //var sb = new StringBuilder();

                var htmlSchede = datiDinamiciReader.GetListaModelli()
                                                  .Where(modello => modello.Compilato)
                                                  .Where(modello => !(options == GenerazioneHtmlSchedeOptions.SoloSchedeCheNonNecessitanoFirma && modello.TipoFirma != 0))
                                                  .Select(modello => WriteHtml(datiDinamiciReader, modello));

                return String.Join(Environment.NewLine, htmlSchede.ToArray());

                //var modelli = datiDinamiciReader.GetListaModelli();

                //foreach (var modello in modelli)
                //{
                //    if (!modello.Compilato)
                //        continue;

                //    // Devo riportare solamente i modelli dinamici che non richiedono la firma
                //    if (options == GenerazioneHtmlSchedeOptions.SoloSchedeCheNonNecessitanoFirma && modello.TipoFirma != 0)
                //        continue;

                //    sb.Append(WriteHtml(datiDinamiciReader, modello));
                //}

                //return sb.ToString();
            });
            */
        }

        // Genera l'html di tutte le schede dell'istanza
        public string GeneraHtmlSchedeIntervento(IDatiDinamiciRiepilogoReader datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            return HtmlSchedeDaSelettore(datiDinamiciReader.GetListaModelliIntervento, datiDinamiciReader, options);
            /*
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
            */
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

        private string HtmlSchedeDaSelettore(Func<IEnumerable<IModelloDinamicoRiepilogo>> selettoreSchede, IDatiDinamiciRiepilogoReader datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            var htmlSchede = selettoreSchede()
                                    .Where(modello => modello.Compilato)
                                    .Where(modello => !(options == GenerazioneHtmlSchedeOptions.SoloSchedeCheNonNecessitanoFirma && modello.TipoFirma != 0))
                                    .Select(modello => WriteHtml(datiDinamiciReader, modello));

            var html = String.Join(Environment.NewLine, htmlSchede.ToArray());

            return GetStringaCss() + html;
        }

        #endregion



        private string WriteHtml(IDatiDinamiciRiepilogoReader datiDinamiciReader, IModelloDinamicoRiepilogo modello, int indiceMolteplicita = -1)
        {
            var idModello = modello.IdModello;

            var htmlSchedeConIndici = datiDinamiciReader.GetIndiciSchede(idModello, _strutturaReader)
                                                        .Select(indiceScheda =>
                                                        {
                                                            var scheda = GeneraScheda(datiDinamiciReader, idModello, indiceScheda, indiceMolteplicita);
                                                            var campiNascosti = datiDinamiciReader.GetCampiNonVisibili(idModello);

                                                            return new ModelloDinamicoHtmlRenderer(scheda).GetHtml(campiNascosti);
                                                        });

            return string.Join(Constants.SeparatoreSchede, htmlSchedeConIndici.ToArray());

            //var sb = new StringBuilder();

            //foreach (var indiceScheda in datiDinamiciReader.GetIndiciSchede(idModello, this._strutturaReader))
            //{
            //    var scheda = GeneraScheda(datiDinamiciReader, idModello, indiceScheda, indiceMolteplicita);
            //    // var campiNascosti = domanda.ReadInterface.DatiDinamici.GetCampiNonVisibili(idModello);
            //    var campiNascosti = datiDinamiciReader.GetCampiNonVisibili(idModello);

            //    sb.Append(new ModelloDinamicoHtmlRenderer(scheda).GetHtml(campiNascosti));
            //    sb.Append(Constants.SeparatoreSchede);
            //}
            //return sb.ToString();
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
            var stringaCss = _primoSegnapostoInserito ? String.Empty : GenerazioneHtmlDomandaConstants.CssModelliDinamici;

            this._primoSegnapostoInserito = true;

            return stringaCss;
        }
    }
}

using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{


    public class SostituzioneSegnapostoRiepilogoService : ISostituzioneSegnapostoRiepilogoService
    {
        private readonly IGeneratoreHtmlSchedeDinamiche _generatoreHtmlSchedeDinamiche;
        private readonly ListaSegnapostoRiepilogoDomanda _segnaposto;

        public SostituzioneSegnapostoRiepilogoService(IGeneratoreHtmlSchedeDinamiche generatoreHtmlSchedeDinamiche, ListaSegnapostoRiepilogoDomanda listaSegnaposto)
        {
            this._segnaposto = listaSegnaposto;
            this._generatoreHtmlSchedeDinamiche = generatoreHtmlSchedeDinamiche;
        }

        public string ProcessaRiepilogo(ISchedeDinamicheDomandaAlRiepilogoService domandaOnline, string templateDaProcessare)
        {
            this._generatoreHtmlSchedeDinamiche.Inizializza();

            // Gestione delle note a pie di pagina della domanda
            var matchSegnapostoNote = this.TrovaSegnaposto(this._segnaposto.SegnapostoNoteCampi, templateDaProcessare);

            if (matchSegnapostoNote != null)
            {
                this._segnaposto.SegnapostoNoteCampi.Inizializza();
            }

            // Tutti gli altri segnaposto
            foreach (var segnaposto in this._segnaposto.Items)
            {
                var matchSegnaposto = this.TrovaSegnaposto(segnaposto, templateDaProcessare);

                if (matchSegnaposto == null)
                    continue;

                templateDaProcessare = this.RisolviSegnaposto(domandaOnline, matchSegnaposto, templateDaProcessare);
            }

            if (matchSegnapostoNote != null)
            {
                templateDaProcessare = this.RisolviSegnaposto(domandaOnline, matchSegnapostoNote, templateDaProcessare);
            }

            return templateDaProcessare;
        }

        private string RisolviSegnaposto(ISchedeDinamicheDomandaAlRiepilogoService reader, MatchSegnaposto matchSegnaposto, string templateDaProcessare)
        {
            return matchSegnaposto.Processa(reader, templateDaProcessare);
        }

        private MatchSegnaposto? TrovaSegnaposto(ISegnapostoRiepilogo segnaposto, string templateDaProcessare)
        {
            var patternsConArgomento = new string[]{
                "<" + segnaposto.NomeTag + "\\s+" + segnaposto.NomeArgomento + "=(?:\"|')(\\w+?)(?:\"|')\\s?/>",
                "<" + segnaposto.NomeTag + "\\s+" + segnaposto.NomeArgomento + "=(?:\"|')(\\w+?)(?:\"|')\\s?></" + segnaposto.NomeTag + "\\s?>"
            };

            var patternsSenzaArgomento = new string[] {
                "<" + segnaposto.NomeTag + "\\s?/>",
                "<" + segnaposto.NomeTag + "\\s?></" + segnaposto.NomeTag + "\\s?>"
            };

            var patternsDaUsare = String.IsNullOrEmpty(segnaposto.NomeArgomento) ? patternsSenzaArgomento : patternsConArgomento;

            for (var i = 0; i < patternsDaUsare.Length; i++)
            {
                var pattern = patternsDaUsare[i];
                var matches = Regex.Matches(templateDaProcessare, pattern);

                if (matches.Count > 0)
                    return new MatchSegnaposto(segnaposto, matches);
            }

            return null;
        }

        public async Task<string> ProcessaRiepilogoAsync(ISchedeDinamicheDomandaAlRiepilogoService domandaOnline, string templateDaProcessare)
        {
            this._generatoreHtmlSchedeDinamiche.Inizializza();  // Serve a resettare la presenza del segnaposto per i css delle schede dinamiche

            // Gestione delle note a pie di pagina della domanda
            var matchSegnapostoNote = this.TrovaSegnaposto(this._segnaposto.SegnapostoNoteCampi, templateDaProcessare);

            if (matchSegnapostoNote != null)
            {
                this._segnaposto.SegnapostoNoteCampi.Inizializza();
            }

            // Tutti gli altri segnaposto
            foreach (var segnaposto in this._segnaposto.Items)
            {
                var matchSegnaposto = this.TrovaSegnaposto(segnaposto, templateDaProcessare);

                if (matchSegnaposto == null)
                    continue;

                templateDaProcessare = await this.RisolviSegnapostoAsync(domandaOnline, matchSegnaposto, templateDaProcessare);
            }

            if (matchSegnapostoNote != null)
            {
                templateDaProcessare = await this.RisolviSegnapostoAsync(domandaOnline, matchSegnapostoNote, templateDaProcessare);
            }

            return templateDaProcessare;
        }

        private async Task<string> RisolviSegnapostoAsync(ISchedeDinamicheDomandaAlRiepilogoService reader, MatchSegnaposto matchSegnaposto, string templateDaProcessare)
        {
            return await matchSegnaposto.ProcessaAsync(reader, templateDaProcessare);
        }
    }
}

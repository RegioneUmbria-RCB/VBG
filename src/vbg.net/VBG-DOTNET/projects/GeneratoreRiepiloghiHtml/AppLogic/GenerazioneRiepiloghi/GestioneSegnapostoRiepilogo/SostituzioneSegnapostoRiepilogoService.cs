using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using System.Text.RegularExpressions;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.GestioneSegnapostoRiepilogo
{

    public class SostituzioneSegnapostoRiepilogoService
    {
        //private readonly GeneratoreRiepilogoSingolaSchedaService _generatoreHtmlSchedeDinamiche;
        private readonly SegnapostoNoteCampi _segnapostoNoteCampi;
        private readonly List<ISegnapostoRiepilogo> _segnaposto;

        public SostituzioneSegnapostoRiepilogoService(/*GeneratoreRiepilogoSingolaSchedaService generatoreHtmlSchedeDinamiche,*/ SegnapostoNoteCampi segnapostoNoteCampi, List<ISegnapostoRiepilogo> segnaposto)
        {
            //this._generatoreHtmlSchedeDinamiche = generatoreHtmlSchedeDinamiche;
            this._segnapostoNoteCampi = segnapostoNoteCampi;
            this._segnaposto = segnaposto;
        }

        public async Task<string> ProcessaRiepilogoAsync(string templateDaProcessare, Istanze istanza)
        {
            // this._generatoreHtmlSchedeDinamiche.Inizializza();

            // Gestione delle note a pie di pagina della domanda
            var matchSegnapostoNote = this.TrovaSegnaposto(this._segnapostoNoteCampi, templateDaProcessare);

            if (matchSegnapostoNote != null)
            {
                this._segnapostoNoteCampi.Inizializza();
            }

            var primoSegnaposto = true;

            // Tutti gli altri segnaposto
            foreach (var segnaposto in this._segnaposto)
            {
                var matchSegnaposto = this.TrovaSegnaposto(segnaposto, templateDaProcessare);

                if (matchSegnaposto == null)
                    continue;

                if (primoSegnaposto)
                {
                    templateDaProcessare = this.IniettaCssSchedeDinamiche(matchSegnaposto, templateDaProcessare);
                    primoSegnaposto = false;
                }

                templateDaProcessare = await this.RisolviSegnapostoAsync(istanza, matchSegnaposto, templateDaProcessare);
            }

            if (matchSegnapostoNote != null)
            {
                templateDaProcessare = await this.RisolviSegnapostoAsync(istanza, matchSegnapostoNote, templateDaProcessare);
            }

            return templateDaProcessare;
        }

        private string IniettaCssSchedeDinamiche(MatchSegnaposto matchSegnaposto, string templateDaProcessare)
        {
            return matchSegnaposto.IniettaCssSchedeDinamiche(templateDaProcessare);
        }

        private async Task<string> RisolviSegnapostoAsync(Istanze istanza, MatchSegnaposto matchSegnaposto, string templateDaProcessare)
        {
            return await matchSegnaposto.ProcessaAsync(istanza, templateDaProcessare);
        }


        private MatchSegnaposto? TrovaSegnaposto(ISegnapostoRiepilogo segnaposto, string templateDaProcessare)
        {
            var patternsConArgomento = new string[]{
                $"<{segnaposto.NomeTag}\\s+{segnaposto.NomeArgomento}=(?:\"|')(\\w+?)(?:\"|')\\s?/>",
                $"<{segnaposto.NomeTag}\\s+{segnaposto.NomeArgomento}=(?:\"|')(\\w+?)(?:\"|')\\s?></{segnaposto.NomeTag}\\s?>"
            };

            var patternsSenzaArgomento = new string[] {
                $"<{segnaposto.NomeTag}\\s?/>",
                $"<{segnaposto.NomeTag}\\s?></{segnaposto.NomeTag}\\s?>"
            };

            var patternsDaUsare = String.IsNullOrEmpty(segnaposto.NomeArgomento) ? patternsSenzaArgomento : patternsConArgomento;

            for (int i = 0; i < patternsDaUsare.Length; i++)
            {
                var pattern = patternsDaUsare[i];
                var matches = Regex.Matches(templateDaProcessare, pattern);

                if (matches.Count > 0)
                    return new MatchSegnaposto(segnaposto, matches);
            }

            return null;
        }
    }
}

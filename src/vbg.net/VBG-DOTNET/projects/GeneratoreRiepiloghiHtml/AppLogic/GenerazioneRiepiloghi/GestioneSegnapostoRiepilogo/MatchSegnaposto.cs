using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using System.Text.RegularExpressions;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.GestioneSegnapostoRiepilogo
{
    internal class MatchSegnaposto
    {
        private readonly ISegnapostoRiepilogo _segnaposto;
        private readonly MatchCollection _matches;

        public MatchSegnaposto(ISegnapostoRiepilogo segnaposto, MatchCollection matches)
        {
            if (segnaposto == null)
                throw new System.ArgumentNullException(nameof(segnaposto));

            if (matches == null)
                throw new System.ArgumentNullException(nameof(matches));

            this._segnaposto = segnaposto;
            this._matches = matches;
        }

        internal async Task<string> ProcessaAsync(Istanze istanza, string templateDaProcessare)
        {
            for (int i = 0; i < this._matches.Count; i++)
            {
                var match = this._matches[i];
                var testoMatch = match.Groups[0].Value;
                var valoreArgomento = match.Groups[1].Value;

                var valoreDaSostituire = await this._segnaposto.ElaboraAsync(istanza, valoreArgomento, testoMatch);

                templateDaProcessare = templateDaProcessare.Replace(testoMatch, valoreDaSostituire);
            }

            return templateDaProcessare;
        }

        internal string IniettaCssSchedeDinamiche(string templateDaProcessare)
        {
            if (!this._matches.Any())
            {
                return templateDaProcessare;
            }

            var testoMatch = this._matches[0].Groups[0].Value;

            return templateDaProcessare.Replace(testoMatch, WrapperStiliSchedeDinamiche.Wrap(testoMatch));
        }
    }
}

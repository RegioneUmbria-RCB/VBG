
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
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
            //Condition.Requires(segnaposto, "segnaposto").IsNotNull();
            //Condition.Requires(matches.Count, "matches.Count").IsGreaterThan(0);


            this._segnaposto = segnaposto;
            this._matches = matches;
        }

        internal string Processa(ISchedeDinamicheDomandaAlRiepilogoService reader, string templateDaProcessare)
        {
            for (int i = 0; i < this._matches.Count; i++)
            {
                var match = this._matches[i];
                var testoMatch = match.Groups[0].Value;
                var valoreArgomento = match.Groups[1].Value;

                var valoreDaSostituire = this._segnaposto.Elabora(reader, valoreArgomento, testoMatch);

                templateDaProcessare = templateDaProcessare.Replace(testoMatch, valoreDaSostituire);
            }

            return templateDaProcessare;
        }

        internal async Task<string> ProcessaAsync(ISchedeDinamicheDomandaAlRiepilogoService reader, string templateDaProcessare)
        {
            for (int i = 0; i < this._matches.Count; i++)
            {
                var match = this._matches[i];
                var testoMatch = match.Groups[0].Value;
                var valoreArgomento = match.Groups[1].Value;

#pragma warning disable VSTHRD103 // Call async methods when in an async method
                var valoreDaSostituire = this._segnaposto.SupportaOperazioniAsincrone ?
                    await this._segnaposto.ElaboraAsync(reader, valoreArgomento, testoMatch) :
                    this._segnaposto.Elabora(reader, valoreArgomento, testoMatch);
#pragma warning restore VSTHRD103 // Call async methods when in an async method

                templateDaProcessare = templateDaProcessare.Replace(testoMatch, valoreDaSostituire);
            }

            return templateDaProcessare;
        }
    }
}

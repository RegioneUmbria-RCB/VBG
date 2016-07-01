using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Text.RegularExpressions;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	public class SostituzioneSegnapostoRiepilogoService : ISostituzioneSegnapostoRiepilogoService
	{
		List<ISegnapostoRiepilogo> _segnaposto = new List<ISegnapostoRiepilogo>();

		internal SostituzioneSegnapostoRiepilogoService(IGeneratoreHtmlSchedeDinamiche generatoreHtmlSchedeDinamiche)
		{
			this._segnaposto.Add(new SegnapostoDatoDinamico());
			this._segnaposto.Add(new SegnapostoSchedaDinamica(generatoreHtmlSchedeDinamiche));
            this._segnaposto.Add(new SegnapostoSchedeDinamiche(generatoreHtmlSchedeDinamiche));
		}

		public string ProcessaRiepilogo(DomandaOnline domandaOnline, string templateDaProcessare)
		{
			foreach (var segnaposto in this._segnaposto)
			{
				var matchSegnaposto = TrovaSegnaposto(segnaposto, templateDaProcessare);

				if (matchSegnaposto == null)
					continue;

				templateDaProcessare = RisolviSegnaposto(domandaOnline, matchSegnaposto, templateDaProcessare);
			}

			return templateDaProcessare;
		}

		private string RisolviSegnaposto(DomandaOnline domandaOnline, MatchSegnaposto matchSegnaposto, string templateDaProcessare)
		{
			return matchSegnaposto.Processa(domandaOnline, templateDaProcessare);
		}

		private MatchSegnaposto TrovaSegnaposto(ISegnapostoRiepilogo segnaposto, string templateDaProcessare)
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

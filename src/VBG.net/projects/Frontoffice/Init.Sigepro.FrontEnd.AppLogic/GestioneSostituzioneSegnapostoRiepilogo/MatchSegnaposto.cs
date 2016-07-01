using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	class MatchSegnaposto
	{
		private readonly ISegnapostoRiepilogo _segnaposto;
		private readonly MatchCollection _matches;

		public MatchSegnaposto(ISegnapostoRiepilogo segnaposto, MatchCollection matches)
		{
			Condition.Requires(segnaposto, "segnaposto").IsNotNull();
			Condition.Requires(matches.Count, "matches.Count").IsGreaterThan(0);


			this._segnaposto = segnaposto;
			this._matches = matches;
		}

		internal string Processa(DomandaOnline domanda, string templateDaProcessare)
		{
			for (int i = 0; i < this._matches.Count; i++)
			{
				var match = this._matches[i];
				var testoMatch = match.Groups[0].Value;
				var valoreArgomento = match.Groups[1].Value;

				var valoreDaSostituire = this._segnaposto.Elabora(domanda, valoreArgomento, testoMatch);

				templateDaProcessare = templateDaProcessare.Replace(testoMatch, valoreDaSostituire);
			}

			return templateDaProcessare;
		}
	}
}

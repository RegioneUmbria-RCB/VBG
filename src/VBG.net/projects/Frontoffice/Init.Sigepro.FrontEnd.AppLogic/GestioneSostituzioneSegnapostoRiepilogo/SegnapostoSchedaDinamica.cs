using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	internal class SegnapostoSchedaDinamica : ISegnapostoRiepilogo
	{
		IGeneratoreHtmlSchedeDinamiche _generatoreHtml;

		internal SegnapostoSchedaDinamica(IGeneratoreHtmlSchedeDinamiche generatoreHtml)
		{
			this._generatoreHtml = generatoreHtml;
		}

		#region ISegnapostoRiepilogo Members

		public string NomeTag
		{
			get { return "schedaDinamica"; }
		}

		public string NomeArgomento
		{
			get { return "id"; }
		}

		public string Elabora(DomandaOnline domanda, string argomento, string espressione)
		{
			Condition.Requires(domanda, "domanda").IsNotNull();

			int idScheda = -1;

			if (!int.TryParse(argomento, out idScheda))
				throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Scheda, espressione);

			return this._generatoreHtml.GeneraHtml(domanda, idScheda);
		}

		#endregion
	}
}

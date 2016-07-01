using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	internal class SegnapostoSchedeDinamiche: ISegnapostoRiepilogo
	{
		IGeneratoreHtmlSchedeDinamiche _generatoreHtml;

		internal SegnapostoSchedeDinamiche(IGeneratoreHtmlSchedeDinamiche generatoreHtml)
		{
			this._generatoreHtml = generatoreHtml;
		}

		#region ISegnapostoRiepilogo Members

		public string NomeTag
		{
			get { return "schedeDinamiche"; }
		}

		public string NomeArgomento
		{
			get { return String.Empty; }
		}

		public string Elabora(DomandaOnline domanda, string argomento, string espressione)
		{
            Condition.Requires(domanda, "domanda").IsNotNull();

			return this._generatoreHtml.GeneraHtmlDelleSchedeDellaDomanda(domanda, GenerazioneHtmlSchedeOptions.SoloSchedeCheNonNecessitanoFirma);
		}

		#endregion
	}
}

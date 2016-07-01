using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	internal class SegnapostoDatoDinamico : ISegnapostoRiepilogo
	{
		#region ISegnapostoRiepilogo Members

		public string NomeTag
		{
			get { return "campoDinamico"; }
		}

		public string NomeArgomento
		{
			get { return "id"; }
		}

		public string Elabora(DomandaOnline domanda, string argomento, string espressione)
		{
			Condition.Requires(domanda, "domanda").IsNotNull();

			int idCampoDinamico = -1;

			if( !int.TryParse( argomento , out idCampoDinamico) )
				throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Campo, espressione);

			var campo = domanda.ReadInterface
								.DatiDinamici
								.DatiDinamici
								.Where(x => x.IdCampo == idCampoDinamico && x.IndiceMolteplicita == 0)
								.FirstOrDefault();
								

			if (campo == null)
				return string.Empty;

			return campo.ValoreDecodificato;
		}

		#endregion
	}
}

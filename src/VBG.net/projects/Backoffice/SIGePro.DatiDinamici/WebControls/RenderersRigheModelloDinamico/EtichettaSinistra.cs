using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class EtichettaSinistra : EtichettaCampo
	{
		string _testo;

		public EtichettaSinistra(string testo)
		{
			if (!String.IsNullOrEmpty(testo) && !testo.EndsWith(":"))
				testo += ":";

			this._testo = testo;
		}

		internal override string Valore
		{
			get { return _testo; }
		}
	}
}

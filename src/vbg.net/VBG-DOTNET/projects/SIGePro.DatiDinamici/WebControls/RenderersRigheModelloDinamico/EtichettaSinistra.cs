using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class EtichettaSinistra : EtichettaCampo
	{
		string _testo;

		public EtichettaSinistra(string testo, int? idRiferimentoNote)
		{
			if (!String.IsNullOrEmpty(testo) && !testo.EndsWith(":"))
				testo += ":";

			this._testo = testo;
            this.IdRiferimentoNote = idRiferimentoNote;
		}

		internal override string Valore
		{
			get { return _testo; }
		}
	}
}

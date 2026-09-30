using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class EtichettaDestra : EtichettaCampo
	{
		string _valore;

		public EtichettaDestra(string testo, int? idRiferimentoNote)
		{
			if(!String.IsNullOrEmpty(testo) && testo.EndsWith(":"))
			{
				testo = testo.Substring(0, testo.Length - 1);
			}

			this._valore = testo;
            this.IdRiferimentoNote = idRiferimentoNote;
		}

		internal override string Valore
		{
			get { return this._valore; }
		}
	}
}

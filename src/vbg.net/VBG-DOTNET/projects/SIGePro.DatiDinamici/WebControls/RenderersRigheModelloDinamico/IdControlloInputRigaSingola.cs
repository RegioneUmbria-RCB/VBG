using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class IdControlloInputRigaSingola : IdControlloInput
	{
		int _idCampo;
		int _indiceMolteplicitaValore;
		int _indiceRiga;
		int _indiceColonna;

		public int IndiceMolteplicitaValore
		{
			get { return this._indiceMolteplicitaValore; }
		}

		public int IndiceRiga
		{
			get { return this._indiceRiga; }
		}

		public IdControlloInputRigaSingola(int idCampo, int indiceMolteplicitaValore, int indiceRiga, int indiceColonna)
		{
			this._idCampo = idCampo;
			this._indiceMolteplicitaValore = indiceMolteplicitaValore;
			this._indiceRiga = indiceRiga;
			this._indiceColonna = indiceColonna;
		}

		public string AsString()
		{
			if (this._idCampo == -1)
				return String.Format("{0}_{1}_I{2}_i", (-1 * this._indiceRiga), (this._indiceColonna + 1), this._indiceMolteplicitaValore);

			return String.Format("{0}_I{1}_i", this._idCampo, this._indiceMolteplicitaValore);
		}

		public override string ToString()
		{
			return String.Format("Tipo: IdControlloInputRigaSingola, idCampo: {0}, indiceMolteplicitaValore: {1}, indiceRiga: {2}, indiceColonna: {3}", _idCampo, _indiceMolteplicitaValore, _indiceRiga, _indiceColonna);
		}
	}
}

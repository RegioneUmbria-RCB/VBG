using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class IdControlloInputRigaMultipla: IdControlloInput
	{
		int _idControllo;
		int _numeroRiga;
		int _indiceMolteplicita;

		public IdControlloInputRigaMultipla(int idControllo, int numeroRiga, int indiceMolteplicita)
		{
			this._idControllo = idControllo;
			this._numeroRiga = numeroRiga;
			this._indiceMolteplicita = indiceMolteplicita;
		}

		public string AsString()
		{
			return String.Format("campo{0}_r{1}_m{2}", this._idControllo, this._numeroRiga, this._indiceMolteplicita);
		}

		public int IndiceMolteplicitaValore
		{
			get { return this._indiceMolteplicita; }
		}

		public int IndiceRiga
		{
			get { return this._numeroRiga; }
		}

		public override string ToString()
		{
			return String.Format("Tipo: IdcontrolloInputRigaMultipla, idCampo: {0}, indiceMolteplicitaValore: {1}, indiceRiga: {2}", _idControllo, _indiceMolteplicita, _numeroRiga);
		}
	}
}

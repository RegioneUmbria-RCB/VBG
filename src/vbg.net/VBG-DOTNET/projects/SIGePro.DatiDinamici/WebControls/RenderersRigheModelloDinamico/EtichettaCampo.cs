using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal abstract class EtichettaCampo
	{
		internal abstract string Valore { get; }
        public int? IdRiferimentoNote { get; protected set; }
	}
}

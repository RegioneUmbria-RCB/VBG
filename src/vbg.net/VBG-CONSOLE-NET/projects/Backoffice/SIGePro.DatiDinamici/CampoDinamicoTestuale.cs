using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.SIGePro.DatiDinamici
{
	public partial class CampoDinamicoTestuale : CampoDinamicoBase
	{
		public CampoDinamicoTestuale(ModelloDinamicoBase modello, IDyn2TestoModello campoTesto)
			: base(modello)
		{
			Inizializza(campoTesto);
		}

		public string TestoStatico
		{
			get;
			private set;
		}

		private void Inizializza(IDyn2TestoModello campo)
		{
			this.Id = -1 * campo.IdNelModello.Value;

			this.ScriptCaricamento = null;
			this.ScriptModifica = null;
			this.ScriptSalvataggio = null;

			this.NomeCampo = String.Empty;
			this.Etichetta = String.Empty;
			this.Descrizione = String.Empty;
			this.Obbligatorio = false;

			this.TipoCampo = campo.IdTipoTesto == "TI" ? TipoControlloEnum.Titolo : TipoControlloEnum.Label;

			if (ListaValori.Count == 0)
				ListaValori.IncrementaMolteplicita();

			this.ListaValori[0].Valore = campo.Testo;
			this.TestoStatico = campo.Testo;
		}
	}
}

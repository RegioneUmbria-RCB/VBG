using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione
{
	internal class ModelloDinamicoInterventoInUsoDto
	{
		public readonly int IdIntervento;
		public readonly int IdModello;
		public readonly int Ordine;

		public ModelloDinamicoInterventoInUsoDto(int idIntervento, int idModello, int ordine)
		{
			this.IdIntervento = idIntervento;
			this.IdModello = idModello;
			this.Ordine = ordine;
		}
	}

	internal class ModelloDinamicoEndoprocedimentoInUsoDto
	{
		public readonly int IdEndoprocedimento;
		public readonly int IdModello;
		public readonly int Ordine;

		public ModelloDinamicoEndoprocedimentoInUsoDto(int idEndoprocedimento, int idModello, int ordine)
		{
			this.IdEndoprocedimento = idEndoprocedimento;
			this.IdModello = idModello;
			this.Ordine = ordine;
		}
	}

	internal class ModelloDinamicoCittadinoExtracomunitarioInUsoDto
	{
		public int IdModello { get; set; }
	}

	internal class ReimpostaModelliDinamiciInUsoCommand
	{
		public IEnumerable<ModelloDinamicoInterventoInUsoDto> ModelliIntervento { get; private set; }
		public IEnumerable<ModelloDinamicoEndoprocedimentoInUsoDto> ModelliEndoprocedimento { get; private set; }

		public ModelloDinamicoCittadinoExtracomunitarioInUsoDto ModelloCittadinoExtracomunitario { get; private set; }

		public ReimpostaModelliDinamiciInUsoCommand(IEnumerable<ModelloDinamicoInterventoInUsoDto> modelliIntervento, IEnumerable<ModelloDinamicoEndoprocedimentoInUsoDto> modelliEndoprocedimento, ModelloDinamicoCittadinoExtracomunitarioInUsoDto modelloCittadinoExtracomunitario)
		{
			this.ModelliIntervento = modelliIntervento;
			this.ModelliEndoprocedimento = modelliEndoprocedimento;
			this.ModelloCittadinoExtracomunitario = modelloCittadinoExtracomunitario;
		}
	}
}

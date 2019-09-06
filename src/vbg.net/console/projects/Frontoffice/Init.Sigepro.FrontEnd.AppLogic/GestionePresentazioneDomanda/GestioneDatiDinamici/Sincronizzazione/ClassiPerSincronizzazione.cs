using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione
{
	public class ModelloDinamicoDaSincronizzare
	{
		public ModelloDinamicoDaSincronizzare(int codice, string descrizione, TipoFirmaEnum tipoFirma, bool facoltativa, int ordine)
		{
			this.Codice = codice;
			this.Descrizione = descrizione;
			this.TipoFirma = tipoFirma;
			this.Facoltativa = facoltativa;
			this.Ordine = ordine;
		}

		public readonly int Codice;
		public readonly string Descrizione;
		public readonly TipoFirmaEnum TipoFirma;
		public readonly bool Facoltativa;
		public readonly int Ordine;
	}

	public class ModelloDinamicoInterventoDaSincronizzare : ModelloDinamicoDaSincronizzare
	{
		public readonly int IdIntervento;

		public ModelloDinamicoInterventoDaSincronizzare(int idIntervento, int codice, string descrizione, TipoFirmaEnum tipoFirma, bool facoltativa, int ordine)
			:base(codice, descrizione, tipoFirma, facoltativa, ordine)
		{
			this.IdIntervento = idIntervento;
		}

		internal ModelloDinamicoInterventoInUsoDto ToModelloDinamicoInterventoInUsoDto()
		{
			return new ModelloDinamicoInterventoInUsoDto(this.IdIntervento, this.Codice, this.Ordine);
		}
	}

	public class ModelloDinamicoEndoprocedimentoDaSincronizzare : ModelloDinamicoDaSincronizzare
	{
		public int IdEndoprocedimento { get; set; }

		public ModelloDinamicoEndoprocedimentoDaSincronizzare(int idEndoprocedimento, int codice, string descrizione, TipoFirmaEnum tipoFirma, bool facoltativa, int ordine)
			:base(codice, descrizione, tipoFirma, facoltativa, ordine)
		{
			this.IdEndoprocedimento = idEndoprocedimento;
		}

		internal ModelloDinamicoEndoprocedimentoInUsoDto ToModelloDinamicoEndoprocedimentoInUsoDto()
		{
			return new ModelloDinamicoEndoprocedimentoInUsoDto(this.IdEndoprocedimento, this.Codice, this.Ordine);
		}
	}

	public class ModelloDinamicoPerCittadiniExtracomunitariDaSincronizzare : ModelloDinamicoDaSincronizzare
	{
		public ModelloDinamicoPerCittadiniExtracomunitariDaSincronizzare( int codice, string descrizione, TipoFirmaEnum tipoFirma, bool facoltativa)
			:base(codice, descrizione, tipoFirma, facoltativa, 9999)
		{
		}
	}

	public class SincronizzaModelliDinamiciCommand
	{
		public IEnumerable<ModelloDinamicoInterventoDaSincronizzare> ModelliIntervento { get; private set; }
		public IEnumerable<ModelloDinamicoEndoprocedimentoDaSincronizzare> ModelliEndoprocedimento { get; private set; }
		public ModelloDinamicoPerCittadiniExtracomunitariDaSincronizzare ModelloCittadiniExtracomunitari { get; private set; }

		public SincronizzaModelliDinamiciCommand(IEnumerable<ModelloDinamicoInterventoDaSincronizzare> modelliIntervento,
												 IEnumerable<ModelloDinamicoEndoprocedimentoDaSincronizzare> modelliEndoprocedimento,
												 ModelloDinamicoPerCittadiniExtracomunitariDaSincronizzare modelloCittadiniExtracomunitari)
		{
			this.ModelliIntervento = modelliIntervento == null ? new List<ModelloDinamicoInterventoDaSincronizzare>() : modelliIntervento;
			this.ModelliEndoprocedimento = modelliEndoprocedimento == null ? new List<ModelloDinamicoEndoprocedimentoDaSincronizzare>() : modelliEndoprocedimento;
			this.ModelloCittadiniExtracomunitari = modelloCittadiniExtracomunitari;
		}
	}
}

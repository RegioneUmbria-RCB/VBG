// -----------------------------------------------------------------------
// <copyright file="TipoSoggetto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class TipoSoggetto
	{
		public int Codice { private set; get; }
		public string Descrizione { private set; get; }
		public string FlagTipoDato { private set; get; }
		public bool RichiedeAnagraficaCollegata { private set; get; }
		public bool FlagLegaleRappresentante { private set; get; }
		public bool RichiedeDescrizioneEstesa { private set; get; }
		public bool RichiedeDatiAlbo{ private set; get; }
		public bool PuoGestirePratica{ private set; get; }

        public TipoSoggetto()
		{
		}

		public TipoSoggetto(TipiSoggetto tipoSoggettoWs)
		{
			this.Codice = Convert.ToInt32(tipoSoggettoWs.CODICETIPOSOGGETTO);
			this.Descrizione = tipoSoggettoWs.TIPOSOGGETTO;
			this.FlagTipoDato = tipoSoggettoWs.TIPODATO;
			this.RichiedeAnagraficaCollegata = tipoSoggettoWs.RICHIEDIANAGRAFECOLL == "1";
			this.FlagLegaleRappresentante = tipoSoggettoWs.FlgLegaleRapp.GetValueOrDefault(0) == 1;
			this.RichiedeDescrizioneEstesa = tipoSoggettoWs.FLG_SPECIFICADESCRIZIONE == "1";
			this.RichiedeDatiAlbo = tipoSoggettoWs.FLG_DATIALBO == "1";
            this.PuoGestirePratica = tipoSoggettoWs.FlagFoModPratica.GetValueOrDefault(0) == 1;
		}

		public bool IsRichiedente()
		{
			return this.FlagTipoDato == "R";
		}

		public bool IsTecnico()
		{
			return this.FlagTipoDato == "T";
		}
	}
}

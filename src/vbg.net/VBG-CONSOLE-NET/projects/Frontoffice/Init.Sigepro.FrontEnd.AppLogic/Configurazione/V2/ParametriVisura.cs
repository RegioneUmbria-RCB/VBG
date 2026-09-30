using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriVisura : IParametriConfigurazione
	{
		public class ParametriRicerca
		{
			public readonly bool CercaComeTecnico;
			public readonly bool CercaComeRichiedente;
			public readonly bool CercaComeAzienda;
			public readonly bool CercaPartitaIva;
			public readonly bool CercaSoggettiCollegati;

			internal ParametriRicerca(bool cercaComeTecnico, bool cercaComeRichiedente, bool cercaComeAzienda, bool cercaPartitaIva, bool cercaSoggettiCollegati)
			{
				this.CercaComeTecnico = cercaComeTecnico;
				this.CercaComeRichiedente = cercaComeRichiedente;
				this.CercaComeAzienda = cercaComeAzienda;
				this.CercaPartitaIva = cercaPartitaIva;
				this.CercaSoggettiCollegati = cercaSoggettiCollegati;
			}
		}

		public class ParametriVisuramobile
		{
			public readonly string UrlServizi;
			public readonly string AliasSportello;

			public ParametriVisuramobile(string urlServizi, string aliasSportello)
			{
				this.UrlServizi = urlServizi;
				this.AliasSportello = aliasSportello;
			}
		}



		public readonly int LimiteRecordsArchivioPratiche;
		public readonly string MessaggioIntestazioneVisura;
		public readonly ParametriRicerca RicercaTecnico;
		public readonly ParametriRicerca RicercaNonTecnico;
		public readonly ParametriRicerca FiltroRichiedente;
		public readonly ParametriVisuramobile VisuraMobile;


		internal ParametriVisura(int limiteRecordsArchivioPratiche, string messaggioIntestazioneVisura, ParametriRicerca ricercaTecnico, ParametriRicerca ricercaNonTecnico, ParametriRicerca filtroRichiedente, ParametriVisuramobile visuraMobile)
		{
			this.LimiteRecordsArchivioPratiche = limiteRecordsArchivioPratiche;
			this.MessaggioIntestazioneVisura = messaggioIntestazioneVisura;
			this.RicercaTecnico = ricercaTecnico;
			this.RicercaNonTecnico = ricercaNonTecnico;
			this.FiltroRichiedente = filtroRichiedente;
			this.VisuraMobile = visuraMobile;
		}
	}
}

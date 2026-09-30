using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriScadenzario : IParametriConfigurazione
	{
		public readonly bool CercaComeTecnico;
		public readonly bool CercaComeRichiedente;
		public readonly bool CercaComeAzienda;
		public readonly bool CercaPartitaIva;
		public readonly bool CercaSoggettiCollegati;

		internal ParametriScadenzario(bool cercaComeTecnico, bool cercaComeRichiedente, bool cercaComeAzienda, bool cercaPartitaIva, bool cercaSoggettiCollegati)
		{
			this.CercaComeTecnico = cercaComeTecnico;
			this.CercaComeRichiedente = cercaComeRichiedente;
			this.CercaComeAzienda = cercaComeAzienda;
			this.CercaPartitaIva = cercaPartitaIva;
			this.CercaSoggettiCollegati = cercaSoggettiCollegati;

		}
	}
}

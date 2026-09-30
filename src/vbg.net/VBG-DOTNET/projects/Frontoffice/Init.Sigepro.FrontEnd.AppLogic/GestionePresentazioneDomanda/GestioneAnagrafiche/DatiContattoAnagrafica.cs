using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public class DatiContattoAnagrafica
	{
		public string Telefono { get; private set; }
		public string TelefonoCellulare { get; private set; }
		public string Fax { get; private set; }
		public string Email { get; private set; }
		public string Pec { get; private set; }

		public DatiContattoAnagrafica(string telefono, string cellulare, string fax, string email, string pec)
		{
			this.Telefono = telefono;
			this.TelefonoCellulare = cellulare;
			this.Fax = fax;
			this.Email = email;
			this.Pec = pec;
		}
	}
}

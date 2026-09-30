using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Events
{
	public class AllegatoFirmatoDigitalmente : Event
	{
		public string IdComune { get; set; }
		public int IdMovimento { get; set; }
		public int CodiceOggetto { get; set; }
		public string NomeFile { get; set; }
	}

}

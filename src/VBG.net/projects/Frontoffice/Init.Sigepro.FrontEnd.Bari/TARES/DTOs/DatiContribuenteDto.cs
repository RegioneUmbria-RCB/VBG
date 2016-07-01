using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Bari.TARES.DTOs
{
	public class DatiContribuenteDto
	{
		public int IdentificativoContribuente { get; set; }
		public DatiAnagraficiDto DatiAnagraficiContribuente { get; set; }
		public DatiIndirizzoDto DatiResidenzaContribuente { get; set; }
		public DatiUtenzaDto[] ElencoUtenzeAttive { get; set; }
	}
}

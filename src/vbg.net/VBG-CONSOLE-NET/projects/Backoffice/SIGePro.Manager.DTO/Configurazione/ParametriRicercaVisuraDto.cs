using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
	public class ParametriRicercaVisuraDto
	{
		public bool CercaComeTecnico { get; set; }
		public bool CercaComeRichiedente { get; set; }
		public bool CercaComeAzienda { get; set; }
		public bool CercaPartitaIva { get; set; }
		public bool CercaSoggettiCollegati { get; set; }
	}
}

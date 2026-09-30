using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
	public class CodiceAccreditamentoDto
	{
		[XmlElement(Order = 0)]
		public string NomeComune { get; set; }

		[XmlElement(Order = 1)]
		public string CodiceAccreditamento { get; set; }
	}


	public class ConfigurazioneContenutiDto
	{
		[XmlElement(Order=0)]
		public string NomeRegione { get; set; }

		[XmlElement(Order = 1)]
		public string NomeComune { get; set; }

		[XmlElement(Order = 2)]
		public string NomeComuneSottotitolo { get; set; }

		[XmlElement(Order = 3)]
		public string ResponsabileSportello { get; set; }

		[XmlElement(Order = 4)]
		public string IndirizzoPec { get; set; }

		[XmlElement(Order = 5)]
		public string IndirizzoEmail { get; set; }

		[XmlElement(Order = 6)]
		public string Telefono { get; set; }

		[XmlElement(Order = 7)]
		public int? CodiceOggettoLogo { get; set; }

		[XmlElement(Order = 8)]
		public bool AreaRiservataAttiva { get; set; }

		[XmlElement(Order = 9)]
		public CodiceAccreditamentoDto[] ListaCodiciAccreditamento { get; set; }
	}
}

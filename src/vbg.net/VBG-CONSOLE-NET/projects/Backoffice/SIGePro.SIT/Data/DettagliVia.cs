// -----------------------------------------------------------------------
// <copyright file="Via.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Sit.Data
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Xml.Serialization;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[Serializable]
	public class DettagliVia
	{
		[XmlElement(Order = 0)]
		public string CodiceViario { get; set; }
		
		[XmlElement(Order = 1)]
		public string Toponimo { get; set; }
		
		[XmlElement(Order = 2)]
		public string Denominazione { get; set; }
		
		[XmlElement(Order = 3)]
		public string Localita { get; set; }
		
		[XmlElement(Order = 4)]
		public string CodiceComune { get; set; }

		[XmlElement(Order = 5)]
		public DateTime? DataFineValidita { get; set; }
	}
}

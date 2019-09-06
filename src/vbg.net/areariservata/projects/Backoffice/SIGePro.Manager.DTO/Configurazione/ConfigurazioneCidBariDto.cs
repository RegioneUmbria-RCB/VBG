// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneCidBariDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.DTO.Configurazione
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
	public class ConfigurazioneCidBariDto
	{
		[XmlElement(Order = 0)]
		public string UrlServizio { get; set; }

		[XmlElement(Order = 1)]
		public string IdentificativoUtente { get; set; }

		[XmlElement(Order = 2)]
		public string Password { get; set; }
	}
}

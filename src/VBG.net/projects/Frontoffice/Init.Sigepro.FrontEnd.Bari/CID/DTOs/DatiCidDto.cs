// -----------------------------------------------------------------------
// <copyright file="DatiCidDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.CID.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiCidDto
	{
		public string CodiceFiscale { get; set; }
		public string Nominativo { get; set; }
		public string DataNascita { get; set; }
		public string Pin { get; set; }
		public string Cid { get; set; }

		public DatiCidDto()
		{
		}

		public DatiCidDto(string codiceFiscale, string nominativo, string dataNascita, string pin, string cid)
		{
			this.CodiceFiscale = codiceFiscale;
			this.Nominativo = nominativo;
			this.DataNascita = dataNascita;
			this.Pin = pin;
			this.Cid = cid;
		}				  
	}
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
	[Serializable]
	[DataContract]
	public class AllegatoDto
	{
		[DataMember]
		public int Codice { get; set; }

		[DataMember]
		public string Descrizione { get; set; }

		[DataMember]
		public int? CodiceOggetto { get; set; }

		[DataMember]
		public string Link { get; set; }

		[DataMember]
		public string FormatiDownload { get; set; }
	}
}

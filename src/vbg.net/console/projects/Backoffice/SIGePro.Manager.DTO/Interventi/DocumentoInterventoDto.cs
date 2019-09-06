using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Interventi
{
	public class DocumentoInterventoDto: BaseDto<int,string>
	{
		public int? CodiceOggetto { get; set; }
		public bool Obbligatorio { get; set; }
		public bool RichiedeFirma { get; set; }
		public string TipoDownload { get; set; }
		public bool DomandaFo { get; set; }
		public string NomeFile { get; set; }
	}
}

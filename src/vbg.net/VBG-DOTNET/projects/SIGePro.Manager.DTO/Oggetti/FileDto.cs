using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Oggetti
{
	public class FileDto
	{
		public string NomeFile { get; set; }
		public string MimeType { get; set; }
		public byte[] Dati { get; set; }
	}
}

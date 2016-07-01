using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using SIGePro.Net.WebServices.WsSIGePro;
using System.Web.Services;
using Init.SIGePro.Manager.DTO.Oggetti;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public FileDto GetOggetto(string token, string codiceOggetto)
		{
			var file = new Oggetti().GetOggetto(token, codiceOggetto);

			if (file == null)
				return null;

			var rVal = new FileDto
			{
				NomeFile = file.FileName,
				MimeType = file.MimeType,
				Dati = file.BinaryData
			};

			return rVal;
		}

		[WebMethod]
		public string GetNomeFileOggetto(string token, int codiceOggetto)
		{
			return new Oggetti().GetNomeFile(token, codiceOggetto); 
		}

		[WebMethod]
		public FileDto GetRisorsaFrontoffice(string token, string idRisorsaOggetto)
		{
			var file = new Oggetti().GetRisorsaFrontoffice(token, idRisorsaOggetto);

			if (file == null)
				return null;

			var rVal = new FileDto
			{
				NomeFile = file.FileName,
				MimeType = file.MimeType,
				Dati = file.BinaryData
			};

			return rVal;
		}
	}
}

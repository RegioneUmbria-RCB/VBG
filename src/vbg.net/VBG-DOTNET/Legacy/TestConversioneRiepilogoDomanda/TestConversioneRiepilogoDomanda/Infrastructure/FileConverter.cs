using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TestConversioneRiepilogoDomanda.CertificatoDiInvio;
using TestConversioneRiepilogoDomanda.FileConverterService;

namespace TestConversioneRiepilogoDomanda.Infrastructure
{
	public class FileConverter : IFileConverterService
	{
		string _token;

		public FileConverter(string token)
		{
			this._token = token;
		}

		#region IFileConverterService Members

		public BinaryFile Converti(byte[] datiOrigine, string estensioneOrigine, string estensioneDestinazione)
		{
			if (estensioneOrigine.ToUpperInvariant() == estensioneDestinazione.ToUpperInvariant())
				return new BinaryFile
				{
					FileName = "file." + estensioneOrigine,
					FileContent = datiOrigine,
					MimeType = String.Empty
				};


			using (var ws = new fileconverterClient())
			{
				var tmpBuffer = new Byte[datiOrigine.Length - 3];

				Array.Copy(datiOrigine, 3, tmpBuffer, 0, datiOrigine.Length - 3);

				datiOrigine = tmpBuffer;
				
				var req = new ConvertBinaryRequest{
					binaryData = datiOrigine,
					conversionType = estensioneDestinazione,
					contentType = estensioneOrigine,
					token = this._token					
				};

				var res = ws.ConvertBinary(req);

				return new BinaryFile
				{
					FileContent = res.binaryData,
					FileName = res.fileName,
					MimeType = res.mimeType
				};
			}
		}

	
		#endregion
	}
}

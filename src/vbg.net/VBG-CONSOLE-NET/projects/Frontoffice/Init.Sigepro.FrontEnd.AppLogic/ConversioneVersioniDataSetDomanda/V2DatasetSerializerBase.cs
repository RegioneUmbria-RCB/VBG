using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda
{
	internal abstract class V2DatasetSerializerBase
	{
		public byte[] Serialize(PresentazioneIstanzaDbV2 dataSet)
		{
			using (var ms = new MemoryStream())
			{
				var header = Encoding.Default.GetBytes(GetVersionHeader());
				ms.Write(header, 0, header.Length);

				dataSet.WriteXml(ms);

				return ms.ToArray();
			}
		}

		public PresentazioneIstanzaDbV2 Deserialize(byte[] dati)
		{
			//var lunghezzaHeader = VersionInformationsHelper.DatiDomandaHeader.V2Header.Length;
			var versionHeader = GetVersionHeader();
			var lunghezzaHeader = versionHeader.Length;

			var lunghezzaDatiSenzaHeader = dati.Length - lunghezzaHeader;

			var datiSenzaHeader = new byte[lunghezzaDatiSenzaHeader];

			Array.Copy(dati, lunghezzaHeader, datiSenzaHeader, 0, lunghezzaDatiSenzaHeader);

			var ds = new PresentazioneIstanzaDbV2();

			using (var ms = new MemoryStream(datiSenzaHeader))
			{
				ds.ReadXml(ms);
			}

			return ds;
		}

		protected abstract string GetVersionHeader();
	}
}

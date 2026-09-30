
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;
using System.IO;
using Init.Utils;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    public partial class IstanzeFrontOffice
    {
		public Istanze GetIstanza()
		{
			if (Xmldomanda == null) return null;
			
			Istanze istanza = null;

			using (MemoryStream ms = StreamUtils.FileToStream( Xmldomanda) )
			{
				XmlSerializer xs = new XmlSerializer(typeof(Istanze));
				istanza = (Istanze)xs.Deserialize(ms);
			}

			return istanza;
		}

		public void SetIstanza(Istanze istanza)
		{

			using (MemoryStream ms = new MemoryStream())
			{
				XmlSerializer xs = new XmlSerializer(istanza.GetType());
				xs.Serialize(ms, istanza);

				this.Xmldomanda = StreamUtils.StreamToBytes(ms);
			}

		}


		public void SetErrori(string errString)
		{
			this.Errori  = Encoding.UTF8.GetBytes(errString);
		}

		public string GetErrori()
		{
			if (Errori == null || Errori.Length == 0) return String.Empty;

			return Encoding.UTF8.GetString(this.Errori);			
		}
	}
}
				
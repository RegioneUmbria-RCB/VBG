using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("TITOLI")]
	[Serializable]
	public class Titoli : BaseDataClass
	{

		#region Key Fields

		[useSequence]
		[KeyField("CODICETITOLO", Type = DbType.Decimal)]
		[XmlElement(Order=0)]
		public string CODICETITOLO { get; set; }

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		[XmlElement(Order = 1)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string titolo=null;
		[DataField("TITOLO",Size=30, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 2)]
		public string TITOLO
		{
			get { return titolo; }
			set { titolo = value; }
		}

	}
}
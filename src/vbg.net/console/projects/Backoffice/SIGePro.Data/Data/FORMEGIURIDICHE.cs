using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("FORMEGIURIDICHE")]
	[Serializable]
	public class FormeGiuridiche : BaseDataClass
	{
		
		#region Key Fields
			
		string codiceformagiuridica=null;
		[useSequence]
		[KeyField("CODICEFORMAGIURIDICA", Type=DbType.Decimal)]
        [XmlElement(Order=0)]
		public string CODICEFORMAGIURIDICA
		{
			get { return codiceformagiuridica; }
			set { codiceformagiuridica = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
        [XmlElement(Order = 1)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string formagiuridica=null;
		[DataField("FORMAGIURIDICA",Size=30, Type=DbType.String, CaseSensitive=false)]
        [XmlElement(Order = 2)]
		public string FORMAGIURIDICA
		{
			get { return formagiuridica; }
			set { formagiuridica = value; }
		}

		string codicecciaa=null;
		[DataField("CODICECCIAA",Size=2, Type=DbType.String, CaseSensitive=false)]
        [XmlElement(Order = 3)]
		public string CODICECCIAA
		{
			get { return codicecciaa; }
			set { codicecciaa = value; }
		}

	}
}
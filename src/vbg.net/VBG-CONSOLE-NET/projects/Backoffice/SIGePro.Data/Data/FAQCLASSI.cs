using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("FAQCLASSI")]
	public class FaqClassi : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicefaqclasse=null;
		[KeyField("CODICEFAQCLASSE", Type=DbType.Decimal)]
		public string CODICEFAQCLASSE
		{
			get { return codicefaqclasse; }
			set { codicefaqclasse = value; }
		}

		string faqclasse=null;
		[DataField("FAQCLASSE",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FAQCLASSE
		{
			get { return faqclasse; }
			set { faqclasse = value; }
		}
	}
}
using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEAFFISSIONIASSEGNAZIONI")]
	public class IstanzeAffissioniAssegnazioni : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string fk_istanzeaffissioniid=null;
		[KeyField("FK_ISTANZEAFFISSIONIID", Type=DbType.Decimal)]
		public string FK_ISTANZEAFFISSIONIID
		{
			get { return fk_istanzeaffissioniid; }
			set { fk_istanzeaffissioniid = value; }
		}

		string fk_impiantopubblicitario=null;
		[KeyField("FK_IMPIANTOPUBBLICITARIO", Type=DbType.Decimal)]
		public string FK_IMPIANTOPUBBLICITARIO
		{
			get { return fk_impiantopubblicitario; }
			set { fk_impiantopubblicitario = value; }
		}

		string fk_impiantopubblicitarioiddett=null;
		[KeyField("FK_IMPIANTOPUBBLICITARIOIDDETT", Type=DbType.Decimal)]
		public string FK_IMPIANTOPUBBLICITARIOIDDETT
		{
			get { return fk_impiantopubblicitarioiddett; }
			set { fk_impiantopubblicitarioiddett = value; }
		}

		string numero=null;
		[DataField("NUMERO", Type=DbType.Decimal)]
		public string NUMERO
		{
			get { return numero; }
			set { numero = value; }
		}

	}
}
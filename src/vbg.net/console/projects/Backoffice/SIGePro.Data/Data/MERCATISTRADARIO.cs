using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MERCATISTRADARIO")]
	public class MercatiStradario : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

        int? fkcodicemercato = null;
		[KeyField("FKCODICEMERCATO", Type=DbType.Decimal)]
		public int? FKCODICEMERCATO
		{
			get { return fkcodicemercato; }
			set { fkcodicemercato = value; }
		}

		string fkcodicestradario=null;
		[KeyField("FKCODICESTRADARIO", Type=DbType.Decimal)]
		public string FKCODICESTRADARIO
		{
			get { return fkcodicestradario; }
			set { fkcodicestradario = value; }
		}

		#region Arraylist per gli inserimenti nelle tabelle collegate

		Stradario _Stradario = null;
		public Stradario Stradario
		{
			get { return _Stradario; }
			set { _Stradario = value; }
		}

		#endregion

	}
}
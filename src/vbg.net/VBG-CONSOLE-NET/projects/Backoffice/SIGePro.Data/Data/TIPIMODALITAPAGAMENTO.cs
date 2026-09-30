using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIMODALITAPAGAMENTO")]
	[Serializable]
	public class TipiModalitaPagamento : BaseDataClass
	{

		#region Key Fields

		string mp_id=null;
		[useSequence]
		[KeyField("MP_ID", Type=DbType.Decimal)]
		public string MP_ID
		{
			get { return mp_id; }
			set { mp_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string mp_descrestesa=null;
		[DataField("MP_DESCRESTESA",Size=100, Type=DbType.String, CaseSensitive=false)]
		public string MP_DESCRESTESA
		{
			get { return mp_descrestesa; }
			set { mp_descrestesa = value; }
		}

	}
}
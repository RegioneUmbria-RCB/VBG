using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPOLOGIAREGISTRI")]
	[Serializable]
	public class TipologiaRegistri : BaseDataClass
	{

		#region Key Fields

		string tr_id=null;
		[useSequence]
		[KeyField("TR_ID", Type=DbType.Decimal)]
		public string TR_ID
		{
			get { return tr_id; }
			set { tr_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string tr_descrizione=null;
		[DataField("TR_DESCRIZIONE",Size=80, Type=DbType.String, CaseSensitive=false)]
		public string TR_DESCRIZIONE
		{
			get { return tr_descrizione; }
			set { tr_descrizione = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string tr_progressivo=null;
		[DataField("TR_PROGRESSIVO",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string TR_PROGRESSIVO
		{
			get { return tr_progressivo; }
			set { tr_progressivo = value; }
		}

		string tr_flagprotocollo=null;
		[DataField("TR_FLAGPROTOCOLLO", Type=DbType.Decimal)]
		public string TR_FLAGPROTOCOLLO
		{
			get { return tr_flagprotocollo; }
			set { tr_flagprotocollo = value; }
		}
	}
}
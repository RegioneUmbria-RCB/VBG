using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("RUOLI")]
	[Serializable]
	public class Ruoli : BaseDataClass
	{

		#region Key Fields

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		#region Data Fields
		string ruolo=null;
		[isRequired]
		[DataField("RUOLO", Size=30, Type=DbType.String, CaseSensitive=false)]
		public string RUOLO
		{
			get { return ruolo; }
			set { ruolo = value; }
		}

		string p_readonly=null;
		[isRequired]
		[DataField("READONLY", Type=DbType.Decimal)]
		public string READONLY
		{
			get { return p_readonly; }
			set { p_readonly = value; }
		}

        [DataField("COD_DOCER", Type = DbType.Decimal)]
        public string COD_DOCER { get; set; }
		
        #endregion
	}
}
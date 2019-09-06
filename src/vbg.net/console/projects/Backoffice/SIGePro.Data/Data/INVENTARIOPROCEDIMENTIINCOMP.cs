using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("INVENTARIOPROCEDIMENTIINCOMP")]
	[Serializable]
	public class InventarioProcedimentiInComp : BaseDataClass
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

		string codiceinventario=null;
		[DataField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string codiceincompatibile=null;
		[DataField("CODICEINCOMPATIBILE", Type=DbType.Decimal)]
		public string CODICEINCOMPATIBILE
		{
			get { return codiceincompatibile; }
			set { codiceincompatibile = value; }
		}
	}
}
using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	/// <summary>
	/// Descrizione di riepilogo per ContentTypes.
	/// </summary>
	[DataTable("CONTENTTYPES")]
	[Serializable]
	public class ContentTypes : BaseDataClass
	{
		public ContentTypes()
		{
		}

		string ct_mimetype=null;
		[DataField("ct_mimetype", Type=DbType.String , CaseSensitive=false)]
		public string CT_MIMETYPE
		{
			get { return ct_mimetype; }
			set { ct_mimetype = value; }
		}

		string ct_extension=null;
		[DataField("ct_extension", Type=DbType.String , CaseSensitive=false)]
		public string CT_EXTENSION
		{
			get { return ct_extension; }
			set { ct_extension = value; }
		}
	}
}

using System;
using System.Data;
using Init.SIGeProExport.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGeProExport.Data
{
	[DataTable("PARAMETRIESPORTAZIONE")]
	[Serializable]
	public class PARAMETRIESPORTAZIONE : BaseDataClass
	{
		#region Key Fields
        string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String, CaseSensitive = false)]
        public string IDCOMUNE
        {
            get { return idcomune; }
            set { idcomune = value; }
        }

		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}
		#endregion
		
		string nome=null;
		[DataField("NOME",Size=25, Type=DbType.String, CaseSensitive=false)]
		public string NOME
		{
			get { return nome; }
			set { nome = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string fk_esp_id=null;
		[DataField("FK_ESP_ID", Type=DbType.Decimal)]
		public string FK_ESP_ID
		{
			get { return fk_esp_id; }
			set { fk_esp_id = value; }
		}

		ESPORTAZIONI fk_esp_id_002=null;
        [ForeignKey(/*typeof(ESPORTAZIONI),*/ "IDCOMUNE,FK_ESP_ID", "IDCOMUNE,ID")]
		public ESPORTAZIONI FK_ESP_ID_002
		{
			get { return fk_esp_id_002; }
			set { fk_esp_id_002 = value; }
		}


	}
}
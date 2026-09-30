using System;
using System.Data;
using Init.SIGeProExport.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGeProExport.Data
{
	[DataTable("TRACCIATIDETTAGLIO")]
	[Serializable]
	public class TRACCIATIDETTAGLIO : BaseDataClass
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

		string fk_tracciati_id=null;
		[DataField("FK_TRACCIATI_ID", Type=DbType.Decimal)]
		public string FK_TRACCIATI_ID
		{
			get { return fk_tracciati_id; }
			set { fk_tracciati_id = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=70, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string obbligatorio=null;
		[DataField("OBBLIGATORIO", Type=DbType.Decimal)]
		public string OBBLIGATORIO
		{
			get { return obbligatorio; }
			set { obbligatorio = value; }
		}

		string out_ordine=null;
		[DataField("OUT_ORDINE", Type=DbType.Decimal)]
		public string OUT_ORDINE
		{
			get { return out_ordine; }
			set { out_ordine = value; }
		}

		string lunghezza=null;
		[DataField("LUNGHEZZA", Type=DbType.Decimal)]
		public string LUNGHEZZA
		{
			get { return lunghezza; }
			set { lunghezza = value; }
		}

		string out_xmltag=null;
		[DataField("OUT_XMLTAG",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OUT_XMLTAG
		{
			get { return out_xmltag; }
			set { out_xmltag = value; }
		}

		string note=null;
		[DataField("NOTE",Size=500, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

        string query = null;
        [DataField("QUERY", Size = 4000, Type = DbType.String, CaseSensitive = false)]
        public string QUERY
        {
            get { return query; }
            set { query = value; }
        }

        string valore = null;
        [DataField("VALORE", Size = 4000, Type = DbType.String, CaseSensitive = false)]
        public string VALORE
        {
            get { return valore; }
            set { valore = value; }
        }

        string campotesto = null;
        [DataField("CAMPOTESTO", Type = DbType.Decimal)]
        public string CAMPOTESTO
        {
            get { return campotesto; }
            set { campotesto = value; }
        }

        

		TRACCIATI fk_tracciati_id_001=null;
        [ForeignKey(/*typeof(TRACCIATI),*/ "IDCOMUNE,FK_TRACCIATI_ID", "IDCOMUNE,ID")]
		public TRACCIATI FK_TRACCIATI_ID_001
		{
			get { return fk_tracciati_id_001; }
			set { fk_tracciati_id_001 = value; }
		}

	}
}
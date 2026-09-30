using System;
using System.Data;
using Init.SIGeProExport.Attributes;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;
using System.Collections.Generic;

namespace Init.SIGeProExport.Data
{
	[DataTable("TRACCIATI")]
	[Serializable]
	public class TRACCIATI : BaseDataClass
	{
		public TRACCIATI()
		{
		}

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

		string fk_esp_id=null;
		[DataField("FK_ESP_ID", Type=DbType.Decimal)]
		public string FK_ESP_ID
		{
			get { return fk_esp_id; }
			set { fk_esp_id = value; }
		}

		string descr_breve=null;
		[DataField("DESCR_BREVE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string DESCR_BREVE
		{
			get { return descr_breve; }
			set { descr_breve = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string out_ordine=null;
		[DataField("OUT_ORDINE", Type=DbType.Decimal)]
		public string OUT_ORDINE
		{
			get { return out_ordine; }
			set { out_ordine = value; }
		}

		string out_nomefile=null;
		[DataField("OUT_NOMEFILE",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OUT_NOMEFILE
		{
			get { return out_nomefile; }
			set { out_nomefile = value; }
		}

		string out_xmltag=null;
		[DataField("OUT_XMLTAG",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OUT_XMLTAG
		{
			get { return out_xmltag; }
			set { out_xmltag = value; }
		}

        string query = null;
        [DataField("QUERY", Size = 4000, Type = DbType.String, CaseSensitive = false)]
        public string QUERY
        {
            get { return query; }
            set { query = value; }
        }

        public string titolo_pagina
        {
            get { return id + " -> " + descrizione; }
        }
        List<TRACCIATIDETTAGLIO> tracciatidettagli = new List<TRACCIATIDETTAGLIO>();
        public List<TRACCIATIDETTAGLIO> TracciatiDettagli
        {
            get { return tracciatidettagli; }
            set { tracciatidettagli = value; }
        }

        ESPORTAZIONI fk_esp_id_001=null;
        [ForeignKey(/*typeof(ESPORTAZIONI),*/ "IDCOMUNE,FK_ESP_ID", "IDCOMUNE,ID")]
		public ESPORTAZIONI FK_ESP_ID_001
		{
			get { return fk_esp_id_001; }
			set { fk_esp_id_001 = value; }
		}

		string fk_tipitracciato_codice=null;
		[DataField("FK_TIPITRACCIATO_CODICE",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string FK_TIPITRACCIATO_CODICE
		{
			get { return fk_tipitracciato_codice; }
			set { fk_tipitracciato_codice = value; }
		}

		TIPITRACCIATI fk_tipitracciato_codice_001=null;
		[ForeignKey(/*typeof(TIPITRACCIATI),*/ "FK_TIPITRACCIATO_CODICE", "CODICETIPO")]
		public TIPITRACCIATI FK_TIPITRACCIATO_CODICE_001
		{
			get { return fk_tipitracciato_codice_001; }
			set { fk_tipitracciato_codice_001 = value; }
		}

	}
}
using System;
using System.Data;
using Init.SIGeProExport.Attributes;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;
using System.Collections.Generic;
using System.IO;
using System.Xml.Serialization;
using System.Text;

namespace Init.SIGeProExport.Data
{
	[DataTable("ESPORTAZIONI")]
	[Serializable]
	public class ESPORTAZIONI : BaseDataClass
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

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string input_xsd=null;
		[DataField("INPUT_XSD",Size=2000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INPUT_XSD
		{
			get { return input_xsd; }
			set { input_xsd = value; }
		}

		string out_nomefile=null;
		[DataField("OUT_NOMEFILE",Size=40, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OUT_NOMEFILE
		{
			get { return out_nomefile; }
			set { out_nomefile = value; }
		}

		string fk_tipiesportazione_codice=null;
		[DataField("FK_TIPIESPORTAZIONE_CODICE",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string FK_TIPIESPORTAZIONE_CODICE
		{
			get { return fk_tipiesportazione_codice; }
			set { fk_tipiesportazione_codice = value; }
		}

        string fk_tipicontestoesp_codice = null;
        [DataField("FK_TIPICONTESTOESP_CODICE", Size = 15, Type = DbType.String, CaseSensitive = false)]
        public string FK_TIPICONTESTOESP_CODICE
        {
            get { return fk_tipicontestoesp_codice; }
            set { fk_tipicontestoesp_codice = value; }
        }

		string out_xmltag=null;
		[DataField("OUT_XMLTAG",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OUT_XMLTAG
		{
			get { return out_xmltag; }
			set { out_xmltag = value; }
		}

		string annulla_dati=null;
		[DataField("ANNULLA_DATI", Type=DbType.Decimal)]
		public string ANNULLA_DATI
		{
			get { return annulla_dati; }
			set { annulla_dati = value; }
		}

		string inserisci_nulli=null;
		[DataField("INSERISCI_NULLI", Type=DbType.Decimal)]
		public string INSERISCI_NULLI
		{
			get { return inserisci_nulli; }
			set { inserisci_nulli = value; }
		}

		string flg_abilitata=null;
        [DataField("FLG_ABILITATA", Type = DbType.Decimal)]
        public string FLG_ABILITATA
		{
            get { return flg_abilitata; }
            set { flg_abilitata = value; }
		}

        public string desc_estesa 
        {
            get { return idcomune + " -> " + descrizione; }
        }

        public string chiave_primaria
        {
            get { return idcomune + "-" + id; }
        }

        public string titolo_pagina
        {
            get { return id + " -> " + descrizione; }
        }

        List<PARAMETRIESPORTAZIONE> parametri = null;
        public List<PARAMETRIESPORTAZIONE> Parametri
        {
            get { return parametri; }
            set { parametri = value; }
        }

        List<TRACCIATI> tracciati = null;
        public List<TRACCIATI> Tracciati
        {
            get { return tracciati; }
            set { tracciati = value; }
        }
        
		TIPIESPORTAZIONE fk_tipiesportazione_codice_001=null;
		[ForeignKey("FK_TIPIESPORTAZIONE_CODICE", "CODICETIPO")]
		public TIPIESPORTAZIONE FK_TIPIESPORTAZIONE_CODICE_001
		{
			get { return fk_tipiesportazione_codice_001; }
			set { fk_tipiesportazione_codice_001 = value; }
		}

        TIPICONTESTOESPORTAZIONE fk_tipicontestoesportazione_codice_001 = null;
        [ForeignKey("FK_TIPICONTESTOESP_CODICE", "CODICE")]
        public TIPICONTESTOESPORTAZIONE FK_TIPICONTESTOESP_CODICE_001
        {
            get { return fk_tipicontestoesportazione_codice_001; }
            set { fk_tipicontestoesportazione_codice_001 = value; }
        }

        public byte[] Serialize() 
        {
            using (MemoryStream ms = new MemoryStream())
            {
                var xs = new XmlSerializer(this.GetType());
                xs.Serialize(ms, this);
                return ms.ToArray();
            }
        }

        public static ESPORTAZIONI Deserialize( byte[] xmlContent )
        {
            ESPORTAZIONI exp = null;

            using (MemoryStream ms = new MemoryStream( xmlContent))
            {
                var xs = new XmlSerializer(typeof(ESPORTAZIONI));
                exp = (ESPORTAZIONI)xs.Deserialize(ms);
            }

            return exp;
        }
	}
}
using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ALBEROPROC_DOCUMENTI")]
	[Serializable]
	public class AlberoProcDocumenti : BaseDataClass
	{
		
		#region Key Fields

		string sm_id=null;
		[useSequence]
		[KeyField("SM_ID", Type=DbType.Decimal)]
		public string SM_ID
		{
			get { return sm_id; }
			set { sm_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string sm_fkscid=null;
		[DataField("SM_FKSCID", Type=DbType.Decimal)]
		public string SM_FKSCID
		{
			get { return sm_fkscid; }
			set { sm_fkscid = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=500, Type=DbType.String, CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string note=null;
		[DataField("NOTE",Size=1000, Type=DbType.String, CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string pubblica=null;
		[DataField("PUBBLICA", Type=DbType.Decimal)]
		public string PUBBLICA
		{
			get { return pubblica; }
			set { pubblica = value; }
		}

		string richiesto=null;
		[DataField("RICHIESTO", Type=DbType.Decimal)]
		public string RICHIESTO
		{
			get { return richiesto; }
			set { richiesto = value; }
		}

        int? fo_richiedifirma = null;
        [DataField("FO_RICHIEDEFIRMA", Type = DbType.Decimal)]
        public int? FO_RICHIEDEFIRMA
        {
            get { return fo_richiedifirma; }
            set { fo_richiedifirma = value; }
        }

        string fo_tipodownload = null;
        [DataField("FO_TIPODOWNLOAD", Type = DbType.String, CaseSensitive = false, Size = 30)]
        public string FO_TIPODOWNLOAD
        {
            get { return fo_tipodownload; }
            set { fo_tipodownload = value; }
        }

        int? fkidcategoria = null;
        [DataField("FKIDCATEGORIA", Type = DbType.Decimal)]
        public int? FKIDCATEGORIA
        {
            get { return fkidcategoria; }
            set { fkidcategoria = value; }
        }

		int? flg_domandafo = null;
		[DataField("FLG_DOMANDAFO", Type = DbType.Decimal)]
		public int? FLG_DOMANDAFO
		{
			get { return flg_domandafo; }
			set { flg_domandafo = value; }
		}

		int? ordine = null;
		[DataField("ORDINE", Type = DbType.Decimal)]
		public int? ORDINE
		{
			get { return ordine; }
			set { ordine = value; }
		}

        protected AlberoProcDocumentiCat categoria = null;
		[ForeignKey("IDCOMUNE,FKIDCATEGORIA", "Idcomune,Id")]
        public AlberoProcDocumentiCat Categoria
        {
            get { return categoria; }
            set { categoria = value; }
        }

		[DataField("NOTE_FRONTEND", Type = DbType.String, CaseSensitive = false, Size = 4000)]
		public string NoteFrontend
		{
			get;
			set;
		}
	}
}
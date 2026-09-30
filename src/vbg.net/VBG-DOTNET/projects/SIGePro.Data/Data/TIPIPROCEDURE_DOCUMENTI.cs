using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIPROCEDURE_DOCUMENTI")]
	[Serializable]
	public class TipiProcedureDocumenti : BaseDataClass
	{

		#region Key Fields

		string tp_id=null;
		[useSequence]
		[KeyField("TP_ID", Type=DbType.Decimal)]
		public string TP_ID
		{
			get { return tp_id; }
			set { tp_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string tp_fkprocedura=null;
		[DataField("TP_FKPROCEDURA", Type=DbType.Decimal)]
		public string TP_FKPROCEDURA
		{
			get { return tp_fkprocedura; }
			set { tp_fkprocedura = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=80, Type=DbType.String, CaseSensitive=false)]
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

		[DataField("PUBBLICA", Type = DbType.Decimal)]
		public int? Pubblica
		{
			get;
			set;
		}

		[DataField("RICHIESTO", Type = DbType.Decimal)]
		public int? Richiesto
		{
			get;
			set;
		}

		[DataField("FO_RICHIEDEFIRMA", Type = DbType.Decimal)]
		public int? FoRichiedefirma
		{
			get;
			set;
		}

		[DataField("FO_TIPODOWNLOAD", Type = DbType.String, CaseSensitive = false, Size = 30)]
		public string FoTipodownload
		{
			get;
			set;
		}

		[DataField("NOTE_FRONTEND", Type = DbType.String, CaseSensitive = false, Size = 4000)]
		public string NoteFrontend
		{
			get;
			set;
		}
	}
}
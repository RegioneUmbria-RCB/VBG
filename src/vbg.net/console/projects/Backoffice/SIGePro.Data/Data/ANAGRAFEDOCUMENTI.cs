using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ANAGRAFEDOCUMENTI")]
	[Serializable]
	public class AnagrafeDocumenti : BaseDataClass
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

		string codiceanagrafe=null;
		[isRequired]
		[DataField("CODICEANAGRAFE", Type=DbType.Decimal)]
		public string CODICEANAGRAFE
		{
			get { return codiceanagrafe; }
			set { codiceanagrafe = value; }
		}

		string idtipodocumento=null;
		[isRequired]
		[DataField("IDTIPODOCUMENTO", Type=DbType.Decimal)]
		public string IDTIPODOCUMENTO
		{
			get { return idtipodocumento; }
			set { idtipodocumento = value; }
		}

        DateTime? dataregistrazione = null;
		[isRequired]
		[DataField("DATAREGISTRAZIONE", Type=DbType.DateTime)]
		public DateTime? DATAREGISTRAZIONE
		{
			get { return dataregistrazione; }
            set { dataregistrazione = VerificaDataLocale(value); }
		}

		string codiceistanza=null;
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

        DateTime? datainiziovalidita = null;
		[DataField("DATAINIZIOVALIDITA", Type=DbType.DateTime)]
		public DateTime? DATAINIZIOVALIDITA
		{
			get { return datainiziovalidita; }
            set { datainiziovalidita = VerificaDataLocale(value); }
		}

		string rifdocumento=null;
		[DataField("RIFDOCUMENTO",Size=80, Type=DbType.String, CaseSensitive=false)]
		public string RIFDOCUMENTO
		{
			get { return rifdocumento; }
			set { rifdocumento = value; }
		}

        DateTime? datafinevalidita = null;
		[DataField("DATAFINEVALIDITA", Type=DbType.DateTime)]
		public DateTime? DATAFINEVALIDITA
		{
			get { return datafinevalidita; }
            set { datafinevalidita = VerificaDataLocale(value); }
		}

		string annotazioni=null;
		[DataField("ANNOTAZIONI",Size=500, Type=DbType.String, CaseSensitive=false)]
		public string ANNOTAZIONI
		{
			get { return annotazioni; }
			set { annotazioni = value; }
		}
		
		Oggetti _Oggetto = null;
		public Oggetti Oggetto
		{
			get { return _Oggetto; }
			set { _Oggetto = value; }
		}
	}
}
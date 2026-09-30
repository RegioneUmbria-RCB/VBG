using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("MOVIMENTIALLEGATI")]
	[Serializable]
	public class MovimentiAllegati : BaseDataClass
	{

		#region Key Fields
		string idcomune = null;
		[KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
		[XmlElement(Order=0)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id = null;
		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		[XmlElement(Order = 1)]
		public string Id
		{
			get { return id; }
			set { id = value; }
		}
		#endregion



		string idallegato = null;
		[DataField("IDALLEGATO", Type = DbType.Decimal)]
		[XmlElement(Order = 2)]
		public string IDALLEGATO
		{
			get { return idallegato; }
			set { idallegato = value; }
		}

		string codicemovimento = null;
		[DataField("CODICEMOVIMENTO", Type = DbType.Decimal)]
		[XmlElement(Order = 3)]
		public string CODICEMOVIMENTO
		{
			get { return codicemovimento; }
			set { codicemovimento = value; }
		}

		string descrizione=null;
		[isRequired]
		[DataField("DESCRIZIONE",Size=100, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 4)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string note=null;
		[DataField("NOTE",Size=500, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 5)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		[XmlElement(Order = 6)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

        DateTime? dataregistrazione = null;
		[DataField("DATAREGISTRAZIONE", Type=DbType.DateTime)]
		[XmlElement(Order = 7)]
		public DateTime? DATAREGISTRAZIONE
		{
			get { return dataregistrazione; }
            set { dataregistrazione = VerificaDataLocale(value); }
		}

        string stc_iddocumento = null;
        [DataField("STC_IDDOCUMENTO", Size = 20, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 8)]
        public string STC_IDDOCUMENTO
        {
            get { return stc_iddocumento; }
            set { stc_iddocumento = value; }
        }

        string stc_idallegato = null;
        [DataField("STC_IDALLEGATO", Size = 20, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 9)]
        public string STC_IDALLEGATO
        {
            get { return stc_idallegato; }
            set { stc_idallegato = value; }
        }


		int? m_flag_pubblica = null;
		[DataField("FLAG_PUBBLICA", Type = DbType.Decimal)]
		[XmlElement(Order = 10)]
		public int? FlagPubblica
		{
			get { return m_flag_pubblica; }
			set { m_flag_pubblica = value; }
		}

		#region Arraylist per gli inserimenti nelle tabelle collegate

        [ForeignKey("IDCOMUNE, CODICEOGGETTO", "IDCOMUNE, CODICEOGGETTO")]
		[XmlElement(Order = 11)]
		public MovimentiAllegatiOggetti Oggetto {get;set;}
		#endregion

	}


	[DataTable("OGGETTI")]
	[Serializable]
	public class MovimentiAllegatiOggetti : BaseDataClass
	{
		#region Key Fields
		string codiceoggetto = null;
		[useSequence]
		[KeyField("CODICEOGGETTO", Type = DbType.Int16)]
		[XmlElement(Order = 0)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string idcomune = null;
		[KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
		[XmlElement(Order = 1)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string nomefile = null;
		[isRequired]
		[DataField("NOMEFILE", Size = 128, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 3)]
		public string NOMEFILE
		{
			get { return nomefile; }
			set { nomefile = value; }
		}
	}
}
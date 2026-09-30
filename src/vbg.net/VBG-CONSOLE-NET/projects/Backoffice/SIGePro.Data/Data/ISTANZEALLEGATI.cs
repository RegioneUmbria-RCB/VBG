using System;
using System.Data;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Attributes;
using System.Xml.Serialization;
using System.Collections.Generic;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEALLEGATI")]
	[Serializable]
	public class IstanzeAllegati : BaseDataClass
	{

		#region Key Fields

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		[XmlElement(Order=1)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id = null;
		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		[XmlElement(Order = 2)]
		public string Id
		{
			get { return id; }
			set { id = value; }
		}

		#endregion

		string codiceinventario = null;
		[DataField("CODICEINVENTARIO", Type = DbType.Decimal)]
		[XmlElement(Order = 3)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string numeroallegato = null;
		[DataField("NUMEROALLEGATO", Type = DbType.Decimal)]
		[XmlElement(Order = 4)]
		public string NUMEROALLEGATO
		{
			get { return numeroallegato; }
			set { numeroallegato = value; }
		}

		string codiceistanza = null;
		[DataField("CODICEISTANZA", Type = DbType.Decimal)]
		[XmlElement(Order = 5)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string verificato=null;
		[DataField("VERIFICATO", Type=DbType.Decimal)]
		[XmlElement(Order = 6)]
		public string VERIFICATO
		{
			get { return verificato; }
			set { verificato = value; }
		}

		string controllook=null;
		[DataField("CONTROLLOOK", Type=DbType.Decimal)]
		[XmlElement(Order = 7)]
		public string CONTROLLOOK
		{
			get { return controllook; }
			set { controllook = value; }
		}

		string note=null;
		[DataField("NOTE",Size=500, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 8)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

        double? prezzo = null;
		[DataField("PREZZO", Type=DbType.Decimal)]
		[XmlElement(Order = 9)]
		public double? PREZZO
		{
			get { return prezzo; }
			set { prezzo = value; }
		}

		string presente=null;
		[DataField("PRESENTE", Type=DbType.Decimal)]
		[XmlElement(Order = 10)]
		public string PRESENTE
		{
			get { return presente; }
			set { presente = value; }
		}

		string seendo=null;
		[DataField("SEENDO", Type=DbType.Decimal)]
		[XmlElement(Order = 11)]
		public string SEENDO
		{
			get { return seendo; }
			set { seendo = value; }
		}

		string selezionato=null;
		[DataField("SELEZIONATO", Type=DbType.Decimal)]
		[XmlElement(Order = 12)]
		public string SELEZIONATO
		{
			get { return selezionato; }
			set { selezionato = value; }
		}

		string allegatoextra=null;
		[DataField("ALLEGATOEXTRA",Size=512, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 13)]
		public string ALLEGATOEXTRA
		{
			get { return allegatoextra; }
			set { allegatoextra = value; }
		}

        double? costopresunto = null;
		[DataField("COSTOPRESUNTO", Type=DbType.Decimal)]
		[XmlElement(Order = 14)]
		public double? COSTOPRESUNTO
		{
			get { return costopresunto; }
			set { costopresunto = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		[XmlElement(Order = 15)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

        string stc_iddocumento = null;
        [DataField("STC_IDDOCUMENTO", Size = 20, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 16)]
        public string STC_IDDOCUMENTO
        {
            get { return stc_iddocumento; }
            set { stc_iddocumento = value; }
        }

        string stc_idallegato = null;
        [DataField("STC_IDALLEGATO", Size = 20, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 17)]
        public string STC_IDALLEGATO
        {
            get { return stc_idallegato; }
            set { stc_idallegato = value; }
        }


		#region Arraylist per gli inserimenti nelle tabelle collegate
		IstanzeAllegatiOggetto _oggetto;
        [ForeignKey("IDCOMUNE, CODICEOGGETTO", "IDCOMUNE, CODICEOGGETTO")]
		[XmlElement(Order = 18)]
		public IstanzeAllegatiOggetto Oggetto
        {
            get { return _oggetto; }
            set { _oggetto = value; }
        }
		#endregion
	}

	[DataTable("OGGETTI")]
	[Serializable]
	public class IstanzeAllegatiOggetto : BaseDataClass
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
		[XmlElement(Order = 2)]
		public string NOMEFILE
		{
			get { return nomefile; }
			set { nomefile = value; }
		}

		List<OggettiMetadati> _metadati = new List<OggettiMetadati>();
		[ForeignKey("IDCOMUNE,CODICEOGGETTO", "Idcomune,Codiceoggetto")]
		[XmlElement(Order = 3)]
		public List<OggettiMetadati> Metadati
		{
			get { return this._metadati; }
			set { this._metadati = value; }
		}

	}
}
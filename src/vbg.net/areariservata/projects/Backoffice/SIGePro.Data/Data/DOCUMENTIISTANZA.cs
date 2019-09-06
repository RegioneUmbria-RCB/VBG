using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;
using System.Collections.Generic;

namespace Init.SIGePro.Data
{
	[DataTable("DOCUMENTIISTANZA")]
	[Serializable]
	public class DocumentiIstanza : BaseDataClass
	{
		
		#region Key Fields
		/// <summary>
		/// Id univoco del documento,fa chiave insieme ad Id
		/// </summary>
		[KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
		[XmlElement(Order=0)]
		public string IDCOMUNE { get; set; }

		/// <summary>
		/// Id univoco del documento,fa chiave insieme ad IDCOMUNE
		/// </summary>
		[useSequence]
		[KeyField("ID", Type = DbType.Decimal)]
		[XmlElement(Order = 1)]
		public int? Id { get; set; }

		#endregion
		/// <summary>
		/// Istanza a cui il documento è collegato (fk su ISTANZE.CODICEISTANZA insieme a IDCOMUNE)
		/// </summary>
		[DataField("CODICEISTANZA", Type = DbType.Decimal)]
		[XmlElement(Order = 2)]
		public string CODICEISTANZA{get;set;}

		/// <summary>
		/// Dismesso
		/// </summary>
		[DataField("CODICEDOCUMENTO", Type = DbType.Decimal)]
		[XmlElement(Order = 3)]
		public string CODICEDOCUMENTO{get;set;}

		/// <summary>
		/// Data di inserimento del documento
		/// </summary>
        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		[XmlElement(Order = 4)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}


		/// <summary>
		/// Descrizione del documento
		/// </summary>
		[isRequired]
		[DataField("DOCUMENTO",Size=4000, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 5)]
		public string DOCUMENTO{get;set;}

		/// <summary>
		/// Id dell'aoggetto binario associato al documento (fk su OGGETTI.CODICEOGGETTO insieme a IDCOMUNE)
		/// </summary>
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		[XmlElement(Order = 6)]
		public string CODICEOGGETTO{get;set;}

		/// <summary>
		/// Note del documento
		/// </summary>
		[DataField("NOTE",Size=1000, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 7)]
		public string NOTE{get;set;}

		/// <summary>
		/// Flag documento obbligatorio
		/// </summary>
		[DataField("NECESSARIO", Type=DbType.Decimal)]
		[XmlElement(Order = 8)]
		public string NECESSARIO{get;set;}

		/// <summary>
		/// FLag documento presente
		/// </summary>
		[DataField("PRESENTE", Type=DbType.Decimal)]
		[XmlElement(Order = 9)]
		public string PRESENTE{get;set;}

		/// <summary>
		/// Campo dismesso
		/// </summary>
		[Obsolete("Campo dismesso", true)]
		[DataField("NOMEDOCUMENTO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		[XmlElement(Order = 10)]
		public string NOMEDOCUMENTO{get;set;}

        /// <summary>
        /// Riferimento del documento se inserito tramite STC
        /// </summary>
		[DataField("STC_IDDOCUMENTO",Size=20, Type=DbType.String, Compare="like", CaseSensitive=false)]
		[XmlElement(Order = 11)]
		public string STC_IDDOCUMENTO{get;set;}

		/// <summary>
		/// Riferimento dell'idallegato se inserito tramite STC
		/// </summary>
        [DataField("STC_IDALLEGATO", Size = 20, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 12)]
        public string STC_IDALLEGATO{get;set;}

        /// <summary>
        /// Id categoria del documento (fk su ALBEROPROC_DOCUMENTICAT.ID insieme a IDCOMUNE)
        /// </summary>
        [DataField("FKIDCATEGORIA", Type = DbType.Decimal)]
		[XmlElement(Order = 13)]
        public int? FKIDCATEGORIA{get;set;}

        [DataField("CONTROLLOOK", Type = DbType.Decimal)]
        [XmlElement(Order = 17)]
        public int? ControlloOk { get; set; }

		#region Arraylist per gli inserimenti nelle tabelle collegate

        /// <summary>
        /// Risoluzione FK: Oggetto binario dell'allegato
        /// </summary>
        [ForeignKey("IDCOMUNE, CODICEOGGETTO", "IDCOMUNE, CODICEOGGETTO")]
		[XmlElement(Order = 14)]
		public DocumentiIstanzaOggetti Oggetto { get; set; }


        /// <summary>
        /// Risoluzione FK: Categoria del documento
        /// </summary>
        [ForeignKey("IDCOMUNE,FKIDCATEGORIA", "Idcomune,Id")]
		[XmlElement(Order = 15)]
        public AlberoProcDocumentiCat Categoria{get;set;}


		/// <summary>
		/// Id categoria del documento (fk su ALBEROPROC_DOCUMENTICAT.ID insieme a IDCOMUNE)
		/// </summary>
		[DataField("FLG_DA_MODELLO_DINAMICO", Type = DbType.Decimal)]
		[XmlElement(Order = 16)]
		public int? FlgDaModelloDinamico { get; set; }
		#endregion
	}

	[DataTable("OGGETTI")]
	[Serializable]
	public class DocumentiIstanzaOggetti : BaseDataClass
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
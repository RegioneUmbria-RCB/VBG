using System;
using System.Data;
using System.Xml.Serialization;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("AUTORIZZAZIONI")]
	[Serializable]
	public class Autorizzazioni : BaseDataClass
	{

		#region Key Fields

		/// <summary>
		/// Id univoco dell'autorizzazione insieme a IDCOMUNE
		/// </summary>
		[useSequence]
		[KeyField("ID", Type = DbType.Decimal)]
        [XmlElement(Order = 1)]
		public string ID{get;set;}

		/// <summary>
		/// Id univoco dell'autorizzazione insieme a ID
		/// </summary>
		[KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [XmlElement(Order = 2)]
        public string IDCOMUNE{get;set;}

		#endregion

		/// <summary>
		/// Id registro, FK su TIPIREGISTRI.TR_ID insieme a IDCOMUNE
		/// </summary>
		[isRequired]
		[DataField("FKIDREGISTRO", Type = DbType.Decimal)]
        [XmlElement(Order = 3)]
        public string FKIDREGISTRO{get;set;}

		/// <summary>
		/// Istanza a cui fa riferimento l'autorizzazione (fk su ISTANZE.CODICEISTANZA insieme a IDCOMUNE)
		/// </summary>
		[DataField("FKIDISTANZA", Type = DbType.Decimal)]
        [XmlElement(Order = 4)]
        public string FKIDISTANZA{get;set;}

		/// <summary>
		/// numero autorizzazione
		/// </summary>
		[isRequired]
		[DataField("AUTORIZNUMERO", Size = 25, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 5)]
        public string AUTORIZNUMERO{get;set;}

		/// <summary>
		/// Data autorizzazione
		/// </summary>
		DateTime? autorizdata = null;
		[DataField("AUTORIZDATA", Type = DbType.DateTime)]
        [XmlElement(Order = 6)]
        public DateTime? AUTORIZDATA
		{
			get { return autorizdata; }
			set { autorizdata = VerificaDataLocale(value); }
		}


		/// <summary>
		/// Data registrazione autorizzazione
		/// </summary>
		DateTime? autorizdataregistr;
		[DataField("AUTORIZDATAREGISTR", Type = DbType.DateTime)]
        [XmlElement(Order = 7)]
        public DateTime? AUTORIZDATAREGISTR
		{
			get { return autorizdataregistr; }
			set { autorizdataregistr = VerificaDataLocale(value); }
		}

		string autorizresponsabile = null;
		[DataField("AUTORIZRESPONSABILE", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 8)]
        public string AUTORIZRESPONSABILE
		{
			get { return autorizresponsabile; }
			set { autorizresponsabile = value; }
		}

		string codicemovimento = null;
		[DataField("CODICEMOVIMENTO", Type = DbType.Decimal)]
        [XmlElement(Order = 9)]
        public string CODICEMOVIMENTO
		{
			get { return codicemovimento; }
			set { codicemovimento = value; }
		}

		int? flagAttiva = null;
		[isRequired]
		[DataField("FLAG_ATTIVA", Type = DbType.Decimal)]
        [XmlElement(Order = 10)]
        public int? FlagAttiva
		{
			get { return flagAttiva; }
			set { flagAttiva = value; }
		}

		DateTime? dataStorico = null;
		[DataField("DATA_STORICO", Type = DbType.DateTime)]
        [XmlElement(Order = 11)]
        public DateTime? DataStorico
		{
			get { return dataStorico; }
			set { dataStorico = VerificaDataLocale(value); }
		}

		int? fkCodiceAnagrafe = null;
		[DataField("FK_CODICEANAGRAFE", Type = DbType.Decimal)]
        [XmlElement(Order = 12)]
        public int? FKCodiceAnagrafe
		{
			get { return fkCodiceAnagrafe; }
			set { fkCodiceAnagrafe = value; }
		}

        [DataField("AUTORIZCOMUNE", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 13)]
        public string AUTORIZCOMUNE { get; set; }

		/// <summary>
		/// Risoluzione FK: Tipo registro
		/// </summary>
		[ForeignKey("IDCOMUNE,FKIDREGISTRO", "IDCOMUNE,TR_ID")]
        [XmlElement(Order = 14)]
        public TipologiaRegistri Registro{get;set;}

		/// <summary>
		/// Risoluzione fk: Anagrafe
		/// </summary>
		[ForeignKey("IDCOMUNE,FKCodiceAnagrafe", "IDCOMUNE,CODICEANAGRAFE")]
        [XmlElement(Order = 15)]
        public Anagrafe Anagrafe{get;set;}

	}
}
using System;
using System.Data;
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
		public string ID{get;set;}

		/// <summary>
		/// Id univoco dell'autorizzazione insieme a ID
		/// </summary>
		[KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
		public string IDCOMUNE{get;set;}

		#endregion

		/// <summary>
		/// Id registro, FK su TIPIREGISTRI.TR_ID insieme a IDCOMUNE
		/// </summary>
		[isRequired]
		[DataField("FKIDREGISTRO", Type = DbType.Decimal)]
		public string FKIDREGISTRO{get;set;}

		/// <summary>
		/// Istanza a cui fa riferimento l'autorizzazione (fk su ISTANZE.CODICEISTANZA insieme a IDCOMUNE)
		/// </summary>
		[DataField("FKIDISTANZA", Type = DbType.Decimal)]
		public string FKIDISTANZA{get;set;}

		/// <summary>
		/// numero autorizzazione
		/// </summary>
		[isRequired]
		[DataField("AUTORIZNUMERO", Size = 25, Type = DbType.String, CaseSensitive = false)]
		public string AUTORIZNUMERO{get;set;}

		/// <summary>
		/// Data autorizzazione
		/// </summary>
		DateTime? autorizdata = null;
		[DataField("AUTORIZDATA", Type = DbType.DateTime)]
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
		public DateTime? AUTORIZDATAREGISTR
		{
			get { return autorizdataregistr; }
			set { autorizdataregistr = VerificaDataLocale(value); }
		}

		string autorizresponsabile = null;
		[DataField("AUTORIZRESPONSABILE", Size = 80, Type = DbType.String, CaseSensitive = false)]
		public string AUTORIZRESPONSABILE
		{
			get { return autorizresponsabile; }
			set { autorizresponsabile = value; }
		}

		string codicemovimento = null;
		[DataField("CODICEMOVIMENTO", Type = DbType.Decimal)]
		public string CODICEMOVIMENTO
		{
			get { return codicemovimento; }
			set { codicemovimento = value; }
		}

		int? flagAttiva = null;
		[isRequired]
		[DataField("FLAG_ATTIVA", Type = DbType.Decimal)]
		public int? FlagAttiva
		{
			get { return flagAttiva; }
			set { flagAttiva = value; }
		}

		DateTime? dataStorico = null;
		[DataField("DATA_STORICO", Type = DbType.DateTime)]
		public DateTime? DataStorico
		{
			get { return dataStorico; }
			set { dataStorico = VerificaDataLocale(value); }
		}

		int? fkCodiceAnagrafe = null;
		[DataField("FK_CODICEANAGRAFE", Type = DbType.Decimal)]
		public int? FKCodiceAnagrafe
		{
			get { return fkCodiceAnagrafe; }
			set { fkCodiceAnagrafe = value; }
		}

        [DataField("AUTORIZCOMUNE", Size = 5, Type = DbType.String, CaseSensitive = false)]
        public string AUTORIZCOMUNE { get; set; }

		/// <summary>
		/// Risoluzione FK: Tipo registro
		/// </summary>
		[ForeignKey("IDCOMUNE,FKIDREGISTRO", "IDCOMUNE,TR_ID")]
		public TipologiaRegistri Registro{get;set;}

		/// <summary>
		/// Risoluzione fk: Anagrafe
		/// </summary>
		[ForeignKey("IDCOMUNE,FKCodiceAnagrafe", "IDCOMUNE,CODICEANAGRAFE")]
		public Anagrafe Anagrafe{get;set;}

	}
}
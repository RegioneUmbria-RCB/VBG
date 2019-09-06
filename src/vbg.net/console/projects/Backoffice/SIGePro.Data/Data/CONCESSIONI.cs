using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CONCESSIONI")]
	public class Concessioni : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string numero=null;
		[isRequired]
		[DataField("NUMERO",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string NUMERO
		{
			get { return numero; }
			set { numero = value; }
		}

		DateTime? datarilascio = null;
		[isRequired]
		[DataField("DATARILASCIO", Type=DbType.DateTime)]
		public DateTime? DATARILASCIO
		{
			get { return datarilascio; }
            set { datarilascio = VerificaDataLocale(value); }
		}

        int? fk_codicemercato = null;
		[isRequired]
		[DataField("FK_CODICEMERCATO", Type=DbType.Decimal)]
        public int? FK_CODICEMERCATO
		{
			get { return fk_codicemercato; }
			set { fk_codicemercato = value; }
		}

        int? fk_idmercatiuso = null;
		[isRequired]
		[DataField("FK_IDMERCATIUSO", Type=DbType.Decimal)]
        public int? FK_IDMERCATIUSO
		{
			get { return fk_idmercatiuso; }
			set { fk_idmercatiuso = value; }
		}

        int? fk_idposteggio = null;
		[isRequired]
		[DataField("FK_IDPOSTEGGIO", Type=DbType.Decimal)]
		public int? FK_IDPOSTEGGIO
		{
			get { return fk_idposteggio; }
			set { fk_idposteggio = value; }
		}

		string fk_tipoconcessione=null;
		[isRequired]
        [DataField("FK_TIPOCONCESSIONE", Size = 2, Type = DbType.String, CaseSensitive = false)]
		public string FK_TIPOCONCESSIONE
		{
			get { return fk_tipoconcessione; }
			set { fk_tipoconcessione = value; }
		}

		string stagionaleda=null;
		[DataField("STAGIONALEDA", Type=DbType.Decimal)]
		public string STAGIONALEDA
		{
			get { return stagionaleda; }
			set { stagionaleda = value; }
		}

		string stagionalea=null;
		[DataField("STAGIONALEA", Type=DbType.Decimal)]
		public string STAGIONALEA
		{
			get { return stagionalea; }
			set { stagionalea = value; }
		}

        DateTime? datascadenza = null;
		[DataField("DATASCADENZA", Type=DbType.DateTime)]
		public DateTime? DATASCADENZA
		{
			get { return datascadenza; }
            set { datascadenza = VerificaDataLocale(value); }
		}

        int? attiva = null;
		[isRequired]
		[DataField("ATTIVA", Type=DbType.Decimal)]
		public int? Attiva
		{
			get { return attiva; }
			set { attiva = value; }
		}

		string codiceanagrafe=null;
		[isRequired]
		[DataField("CODICEANAGRAFE", Type=DbType.Decimal)]
		public string CODICEANAGRAFE
		{
			get { return codiceanagrafe; }
			set { codiceanagrafe = value; }
		}


		#region Classi 
		Mercati_D _Posteggio = null;
		public Mercati_D Posteggio
		{
			get { return _Posteggio; }
			set { _Posteggio = value; }
		}

		Mercati_Uso _Uso = null;
		public Mercati_Uso Uso
		{
			get { return _Uso; }
			set { _Uso = value; }
		}

        Anagrafe m_titolare;
        [ForeignKey("IDCOMUNE, CODICEANAGRAFE", "IDCOMUNE, CODICEANAGRAFE")]
        public Anagrafe Titolare
        {
            get { return m_titolare; }
            set { m_titolare = value; }
        }

		#endregion

	}
}
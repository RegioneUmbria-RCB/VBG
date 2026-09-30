using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZECONCESSIONI")]
	public class IstanzeConcessioni : BaseDataClass
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

		string fkcodiceistanza=null;
		[isRequired]
		[DataField("FKCODICEISTANZA", Type=DbType.Decimal)]
		public string FKCODICEISTANZA
		{
			get { return fkcodiceistanza; }
			set { fkcodiceistanza = value; }
		}

		string fkidconcessione=null;
		[isRequired]
		[DataField("FKIDCONCESSIONE", Type=DbType.Decimal)]
		public string FKIDCONCESSIONE
		{
			get { return fkidconcessione; }
			set { fkidconcessione = value; }
		}

		string fkcodicecausale=null;
		[isRequired]
		[DataField("FKCODICECAUSALE", Type=DbType.Decimal)]
		public string FKCODICECAUSALE
		{
			get { return fkcodicecausale; }
			set { fkcodicecausale = value; }
		}

		string fkcodicecausalestorico=null;
		[DataField("FKCODICECAUSALESTORICO", Type=DbType.Decimal)]
		public string FKCODICECAUSALESTORICO
		{
			get { return fkcodicecausalestorico; }
			set { fkcodicecausalestorico = value; }
		}

		string datastorico=null;
		[DataField("DATASTORICO", Type=DbType.DateTime)]
		public string DATASTORICO
		{
			get { return datastorico; }
			set { datastorico = value; }
		}

		string fk_idautorizzazione=null;
		[DataField("FK_IDAUTORIZZAZIONE", Type=DbType.Decimal)]
		public string FK_IDAUTORIZZAZIONE
		{
			get { return fk_idautorizzazione; }
			set { fk_idautorizzazione = value; }
		}

		string idprecedente=null;
		[DataField("IDPRECEDENTE", Type=DbType.Decimal)]
		public string IDPRECEDENTE
		{
			get { return idprecedente; }
			set { idprecedente = value; }
		}

        Concessioni m_concessioni;
        [ForeignKey("IDCOMUNE, FKIDCONCESSIONE", "IDCOMUNE, ID")]
        public Concessioni Concessione
        {
            get { return m_concessioni; }
            set { m_concessioni = value; }
        }

        Autorizzazioni m_autorizzazioni;
        [ForeignKey("IDCOMUNE, FK_IDAUTORIZZAZIONE", "IDCOMUNE, ID")]
        public Autorizzazioni Autorizzazione
        {
            get { return m_autorizzazioni; }
            set { m_autorizzazioni = value; }
        }
	}
}
using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("AMMINISTRAZIONI")]
	[Serializable]
	public partial class Amministrazioni : BaseDataClass
	{
	
		#region Key Fields

		string codiceamministrazione=null;
		[useSequence]
		[KeyField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string amministrazione=null;
		[DataField("AMMINISTRAZIONE",Size=80, Type=DbType.String, CaseSensitive=false)]
		public string AMMINISTRAZIONE
		{
			get { return amministrazione; }
			set { amministrazione = value; }
		}

		string ufficio=null;
		[DataField("UFFICIO",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string UFFICIO
		{
			get { return ufficio; }
			set { ufficio = value; }
		}

		string referente=null;
		[DataField("REFERENTE",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string REFERENTE
		{
			get { return referente; }
			set { referente = value; }
		}

		string indirizzo=null;
		[DataField("INDIRIZZO",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string INDIRIZZO
		{
			get { return indirizzo; }
			set { indirizzo = value; }
		}

		string citta=null;
		[DataField("CITTA",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string CITTA
		{
			get { return citta; }
			set { citta = value; }
		}

		string cap=null;
		[DataField("CAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CAP
		{
			get { return cap; }
			set { cap = value; }
		}

		string provincia=null;
		[DataField("PROVINCIA",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string PROVINCIA
		{
			get { return provincia; }
			set { provincia = value; }
		}

		string partitaiva=null;
		[DataField("PARTITAIVA",Size=16, Type=DbType.String, CaseSensitive=false)]
		public string PARTITAIVA
		{
			get { return partitaiva; }
			set { partitaiva = value; }
		}

		string telefono1=null;
		[DataField("TELEFONO1",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string TELEFONO1
		{
			get { return telefono1; }
			set { telefono1 = value; }
		}

		string telefono2=null;
		[DataField("TELEFONO2",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string TELEFONO2
		{
			get { return telefono2; }
			set { telefono2 = value; }
		}

		string fax=null;
		[DataField("FAX",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string FAX
		{
			get { return fax; }
			set { fax = value; }
		}

		string email=null;
		[DataField("EMAIL",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string EMAIL
		{
			get { return email; }
			set { email = value; }
		}

		string password=null;
		[DataField("PASSWORD",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string PASSWORD
		{
			get { return password; }
			set { password = value; }
		}

		string web=null;
		[DataField("WEB",Size=150, Type=DbType.String, CaseSensitive=false)]
		public string WEB
		{
			get { return web; }
			set { web = value; }
		}

		string flag_silenziodiniego=null;
		[DataField("FLAG_SILENZIODINIEGO", Type=DbType.Decimal)]
		public string FLAG_SILENZIODINIEGO
		{
			get { return flag_silenziodiniego; }
			set { flag_silenziodiniego = value; }
		}

		string codiceancitel=null;
		[DataField("CODICEANCITEL",Size=100, Type=DbType.String, CaseSensitive=false)]
		public string CODICEANCITEL
		{
			get { return codiceancitel; }
			set { codiceancitel = value; }
		}

		string progressivoexport=null;
		[DataField("PROGRESSIVOEXPORT",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string PROGRESSIVOEXPORT
		{
			get { return progressivoexport; }
			set { progressivoexport = value; }
		}

		string flag_amministrazioneinterna=null;
		[DataField("FLAG_AMMINISTRAZIONEINTERNA", Type=DbType.Decimal)]
		public string FLAG_AMMINISTRAZIONEINTERNA
		{
			get { return flag_amministrazioneinterna; }
			set { flag_amministrazioneinterna = value; }
		}

        string stc_idente = null;
        [DataField("STC_IDENTE",Size=4, Type=DbType.String, CaseSensitive=false)]
        public string STC_IDENTE
        {
            get { return stc_idente; }
            set { stc_idente = value; }
        }

        string stc_idsportello = null;
        [DataField("STC_IDSPORTELLO", Size = 10, Type = DbType.String, CaseSensitive = false)]
        public string STC_IDSPORTELLO
        {
            get { return stc_idsportello; }
            set { stc_idsportello = value; }
        }

        string pec = null;
        [DataField("PEC", Size = 10, Type = DbType.String, CaseSensitive = false)]
        public string PEC
        {
            get { return pec; }
            set { pec = value; }
        }
        
	}
}
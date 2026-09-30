using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("AMMINISTRAZIONIREFERENTI")]
	[Serializable]
	public class AmministrazioniReferenti : BaseDataClass
	{
		#region Key Fields

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Int32)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		#endregion

		string codiceamministrazione=null;
		[DataField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

		string ufficio=null;
		[DataField("UFFICIO", Size=80, Type=DbType.String)]
		public string UFFICIO
		{
			get { return ufficio; }
			set { ufficio = value; }
		}

		string referente=null;
		[DataField("REFERENTE", Size=40, Type=DbType.String)]
		public string REFERENTE
		{
			get { return referente; }
			set { referente = value; }
		}

		string email=null;
		[DataField("EMAIL", Size=50, Type=DbType.String)]
		public string EMAIL
		{
			get { return email; }
			set { email = value; }
		}

		string indirizzo=null;
		[DataField("INDIRIZZO", Size=50, Type=DbType.String)]
		public string INDIRIZZO
		{
			get { return indirizzo; }
			set { indirizzo = value; }
		}

		string citta=null;
		[DataField("CITTA", Size=50, Type=DbType.String)]
		public string CITTA
		{
			get { return citta; }
			set { citta = value; }
		}

		string cap=null;
		[DataField("CAP", Size=5, Type=DbType.String)]
		public string CAP
		{
			get { return cap; }
			set { cap = value; }
		}

		string provincia=null;
		[DataField("PROVINCIA", Size=2, Type=DbType.String)]
		public string PROVINCIA
		{
			get { return provincia; }
			set { provincia = value; }
		}

		string telefono1=null;
		[DataField("TELEFONO1", Size=15, Type=DbType.String)]
		public string TELEFONO1
		{
			get { return telefono1; }
			set { telefono1 = value; }
		}

		string telefono2=null;
		[DataField("TELEFONO2", Size=15, Type=DbType.String)]
		public string TELEFONO2
		{
			get { return telefono2; }
			set { telefono2 = value; }
		}

		string fax=null;
		[DataField("FAX", Size=15, Type=DbType.String)]
		public string FAX
		{
			get { return fax; }
			set { fax = value; }
		}
	}
}

using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PARERI")]
	public class Pareri : BaseDataClass
	{
        string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String, CaseSensitive = false)]
        public string IDCOMUNE
        {
            get { return idcomune; }
            set { idcomune = value; }
        }

        string movimento = null;
        [KeyField("MOVIMENTO", Type = DbType.Decimal)]
        public string MOVIMENTO
        {
            get { return movimento; }
            set { movimento = value; }
        }

		string istanza=null;
		[DataField("ISTANZA", Type=DbType.Decimal)]
		public string ISTANZA
		{
			get { return istanza; }
			set { istanza = value; }
		}

		string amministrazione=null;
		[DataField("AMMINISTRAZIONE", Type=DbType.Decimal)]
		public string AMMINISTRAZIONE
		{
			get { return amministrazione; }
			set { amministrazione = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string esito=null;
		[DataField("ESITO", Type=DbType.Decimal)]
		public string ESITO
		{
			get { return esito; }
			set { esito = value; }
		}

		string parere=null;
		[DataField("PARERE",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PARERE
		{
			get { return parere; }
			set { parere = value; }
		}

	}
}
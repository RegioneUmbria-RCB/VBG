using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEAFFISSIONI")]
	public class IstanzeAffissioni : BaseDataClass
	{
		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string titolo=null;
		[DataField("TITOLO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TITOLO
		{
			get { return titolo; }
			set { titolo = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string durata=null;
		[DataField("DURATA", Type=DbType.Decimal)]
		public string DURATA
		{
			get { return durata; }
			set { durata = value; }
		}

		string numero=null;
		[DataField("NUMERO", Type=DbType.Decimal)]
		public string NUMERO
		{
			get { return numero; }
			set { numero = value; }
		}

		string fk_idformato=null;
		[DataField("FK_IDFORMATO", Type=DbType.Decimal)]
		public string FK_IDFORMATO
		{
			get { return fk_idformato; }
			set { fk_idformato = value; }
		}

		string _base=null;
		[DataField("BASE", Type=DbType.Decimal)]
		public string BASE
		{
			get { return _base; }
			set { _base = value; }
		}

		string altezza=null;
		[DataField("ALTEZZA", Type=DbType.Decimal)]
		public string ALTEZZA
		{
			get { return altezza; }
			set { altezza = value; }
		}

		string facce=null;
		[DataField("FACCE", Type=DbType.Decimal)]
		public string FACCE
		{
			get { return facce; }
			set { facce = value; }
		}

		string fk_tipoaffissione=null;
		[DataField("FK_TIPOAFFISSIONE", Type=DbType.Decimal)]
		public string FK_TIPOAFFISSIONE
		{
			get { return fk_tipoaffissione; }
			set { fk_tipoaffissione = value; }
		}
	}
}
using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_DOMANDE")]
	public class F_Domande : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string dm_id=null;
		[KeyField("DM_ID", Type=DbType.Decimal)]
		public string DM_ID
		{
			get { return dm_id; }
			set { dm_id = value; }
		}

		string dm_fkid_f_fiere=null;
		[DataField("DM_FKID_F_FIERE", Type=DbType.Decimal)]
		public string DM_FKID_F_FIERE
		{
			get { return dm_fkid_f_fiere; }
			set { dm_fkid_f_fiere = value; }
		}

		string dm_fkid_f_giorni_fiera=null;
		[DataField("DM_FKID_F_GIORNI_FIERA", Type=DbType.Decimal)]
		public string DM_FKID_F_GIORNI_FIERA
		{
			get { return dm_fkid_f_giorni_fiera; }
			set { dm_fkid_f_giorni_fiera = value; }
		}

		string dm_fkid_f_categorie=null;
		[DataField("DM_FKID_F_CATEGORIE", Type=DbType.Decimal)]
		public string DM_FKID_F_CATEGORIE
		{
			get { return dm_fkid_f_categorie; }
			set { dm_fkid_f_categorie = value; }
		}

		string dm_fkid_anagrafe=null;
		[DataField("DM_FKID_ANAGRAFE", Type=DbType.Decimal)]
		public string DM_FKID_ANAGRAFE
		{
			get { return dm_fkid_anagrafe; }
			set { dm_fkid_anagrafe = value; }
		}

		string dm_data_presentazione=null;
		[DataField("DM_DATA_PRESENTAZIONE",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string DM_DATA_PRESENTAZIONE
		{
			get { return dm_data_presentazione; }
			set { dm_data_presentazione = value; }
		}

		string dm_firma_richiedente=null;
		[DataField("DM_FIRMA_RICHIEDENTE", Type=DbType.Decimal)]
		public string DM_FIRMA_RICHIEDENTE
		{
			get { return dm_firma_richiedente; }
			set { dm_firma_richiedente = value; }
		}

		string dm_allegato_societa=null;
		[DataField("DM_ALLEGATO_SOCIETA",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string DM_ALLEGATO_SOCIETA
		{
			get { return dm_allegato_societa; }
			set { dm_allegato_societa = value; }
		}

		string dm_setitolarelegale=null;
		[DataField("DM_SETITOLARELEGALE", Type=DbType.Decimal)]
		public string DM_SETITOLARELEGALE
		{
			get { return dm_setitolarelegale; }
			set { dm_setitolarelegale = value; }
		}

		string dm_fkid_societa=null;
		[DataField("DM_FKID_SOCIETA", Type=DbType.Decimal)]
		public string DM_FKID_SOCIETA
		{
			get { return dm_fkid_societa; }
			set { dm_fkid_societa = value; }
		}

		string dm_note=null;
		[DataField("DM_NOTE",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DM_NOTE
		{
			get { return dm_note; }
			set { dm_note = value; }
		}

		string dm_data_inserimento=null;
		[DataField("DM_DATA_INSERIMENTO",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string DM_DATA_INSERIMENTO
		{
			get { return dm_data_inserimento; }
			set { dm_data_inserimento = value; }
		}

		string dm_cittacorrispondenza=null;
		[DataField("DM_CITTACORRISPONDENZA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DM_CITTACORRISPONDENZA
		{
			get { return dm_cittacorrispondenza; }
			set { dm_cittacorrispondenza = value; }
		}

		string dm_indirizzocorrispondenza=null;
		[DataField("DM_INDIRIZZOCORRISPONDENZA",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DM_INDIRIZZOCORRISPONDENZA
		{
			get { return dm_indirizzocorrispondenza; }
			set { dm_indirizzocorrispondenza = value; }
		}

		string dm_comunecorrispondenza=null;
		[DataField("DM_COMUNECORRISPONDENZA",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string DM_COMUNECORRISPONDENZA
		{
			get { return dm_comunecorrispondenza; }
			set { dm_comunecorrispondenza = value; }
		}

		string dm_capcorrispondenza=null;
		[DataField("DM_CAPCORRISPONDENZA",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string DM_CAPCORRISPONDENZA
		{
			get { return dm_capcorrispondenza; }
			set { dm_capcorrispondenza = value; }
		}

		string dm_fkid_subentro=null;
		[DataField("DM_FKID_SUBENTRO", Type=DbType.Decimal)]
		public string DM_FKID_SUBENTRO
		{
			get { return dm_fkid_subentro; }
			set { dm_fkid_subentro = value; }
		}

		string dm_stato=null;
		[DataField("DM_STATO", Type=DbType.Decimal)]
		public string DM_STATO
		{
			get { return dm_stato; }
			set { dm_stato = value; }
		}

		string dm_motiviesclusione=null;
		[DataField("DM_MOTIVIESCLUSIONE",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DM_MOTIVIESCLUSIONE
		{
			get { return dm_motiviesclusione; }
			set { dm_motiviesclusione = value; }
		}

		string dm_presenza=null;
		[DataField("DM_PRESENZA", Type=DbType.Decimal)]
		public string DM_PRESENZA
		{
			get { return dm_presenza; }
			set { dm_presenza = value; }
		}

	}
}
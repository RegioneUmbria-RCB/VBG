using System;
using System.Data;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Collection;

namespace Init.SIGePro.Data
{
	[DataTable("COMUNISECURITY")]
	[Serializable]
	public class ComuniSecurity : BaseDataClass
	{
		string cs_codiceistat=null;
		[KeyField("CS_CODICEISTAT",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string CS_CODICEISTAT
		{
			get { return cs_codiceistat; }
			set { cs_codiceistat = value; }
		}

		string cs_dbuser=null;
		[DataField("CS_DBUSER",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CS_DBUSER
		{
			get { return cs_dbuser; }
			set { cs_dbuser = value; }
		}

		string cs_dbpwd=null;
		[DataField("CS_DBPWD",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CS_DBPWD
		{
			get { return cs_dbpwd; }
			set { cs_dbpwd = value; }
		}

		string cs_connectionstring=null;
		[DataField("CS_CONNECTIONSTRING",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CS_CONNECTIONSTRING
		{
			get { return cs_connectionstring; }
			set { cs_connectionstring = value; }
		}

		string cs_attivo=null;
		[DataField("CS_ATTIVO", Type=DbType.Decimal)]
		public string CS_ATTIVO
		{
			get { return cs_attivo; }
			set { cs_attivo = value; }
		}

		string cs_crdsn=null;
		[DataField("CS_CRDSN",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string CS_CRDSN
		{
			get { return cs_crdsn; }
			set { cs_crdsn = value; }
		}

		string cs_owner=null;
		[DataField("CS_OWNER",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CS_OWNER
		{
			get { return cs_owner; }
			set { cs_owner = value; }
		}

		string cs_softwareattivi=null;
		[DataField("CS_SOFTWAREATTIVI",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CS_SOFTWAREATTIVI
		{
			get { return cs_softwareattivi; }
			set { cs_softwareattivi = value; }
		}

		string cs_ogg_terr=null;
		[DataField("CS_OGG_TERR", Type=DbType.Decimal)]
		public string CS_OGG_TERR
		{
			get { return cs_ogg_terr; }
			set { cs_ogg_terr = value; }
		}

		string cs_showreport=null;
		[DataField("CS_SHOWREPORT",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CS_SHOWREPORT
		{
			get { return cs_showreport; }
			set { cs_showreport = value; }
		}

		string cs_idcomune=null;
		[DataField("CS_IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string CS_IDCOMUNE
		{
			get 
            {
                return cs_idcomune; 
			}
			set { cs_idcomune = value; }
		}

		string cs_softwareattivifo=null;
		[DataField("CS_SOFTWAREATTIVIFO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CS_SOFTWAREATTIVIFO
		{
			get { return cs_softwareattivifo; }
			set { cs_softwareattivifo = value; }
		}

		string cs_dbaggiornato=null;
		[DataField("CS_DBAGGIORNATO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string CS_DBAGGIORNATO
		{
			get { return cs_dbaggiornato; }
			set { cs_dbaggiornato = value; }
		}

		
		CSConnectionStringCollection _CSConnectionStringColl = new CSConnectionStringCollection();
		public CSConnectionStringCollection CSConnectionStringColl
		{
			get { return _CSConnectionStringColl; }
			set { _CSConnectionStringColl = value; }
		} 

		CSProviderCollection _CSProviderColl = new CSProviderCollection();
		public CSProviderCollection CSProviderColl
		{
			get { return _CSProviderColl; }
			set { _CSProviderColl = value; }
		}

		CSAmbienteCollection _CSAmbienteColl = new CSAmbienteCollection();
		public CSAmbienteCollection CSAmbienteColl
		{
			get { return _CSAmbienteColl; }
			set { _CSAmbienteColl = value; }
		}


		public string GetIdComune()
		{
			if (String.IsNullOrEmpty(CS_IDCOMUNE))
				return CS_CODICEISTAT;

			return CS_IDCOMUNE;
		}

	}
}
using System;
using System.Data;
using System.Diagnostics;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;

namespace SIGePro.Net.Data
{
	/// <summary>
	/// Descrizione di riepilogo per IMPR_IMPRESA.
	/// </summary>
	/// 
	[DataTable("T_IMPR_IMPRESA")]
	public class IMPR_IMPRESA : DataClass
	{
		public IMPR_IMPRESA()
		{
		}

		private string provinciaccia = null;

		[KeyField("PROVINCIACCIA", Size=2, Type=DbType.String)]
		public string PROVINCIACCIA
		{
			get { return provinciaccia; }
			set { provinciaccia = value; }
		}

		private string numiscrizionerea = null;

		[KeyField("NUMISCRIZIONEREA", Size=9, Type=DbType.Int16)]
		public string NUMISCRIZIONEREA
		{
			get { return numiscrizionerea; }
			set { numiscrizionerea = value; }
		}

		private string codicefiscale = null;

		[DataField("CODICEFISCALE", Size=16, Type=DbType.String)]
		public string CODICEFISCALE
		{
			get { return codicefiscale; }
			set { codicefiscale = value; }
		}

		private string numeroregistro = null;

		[DataField("NUMEROREGISTRO", Size=21, Type=DbType.String)]
		public string NUMEROREGISTRO
		{
			get { return numeroregistro; }
			set { numeroregistro = value; }
		}

		private DateTime dataiscrizioneregistro;

		[DataField("DATAISCRIZIONEREGISTRO", Type=DbType.DateTime)]
		public DateTime DATAISCRIZIONEREGISTRO
		{
			get { return dataiscrizioneregistro; }
			set { dataiscrizioneregistro = value; }
		}

		private DateTime datacaricamentoarch;

		[DataField("DATACARICAMENTOARCH", Type=DbType.DateTime)]
		public DateTime DATACARICAMENTOARCH
		{
			get { return datacaricamentoarch; }
			set { datacaricamentoarch = value; }
		}

		private string ragionesociale = null;

		[DataField("RAGIONESOCIALE", Size=305, Type=DbType.String)]
		public string RAGIONESOCIALE
		{
			get { return ragionesociale; }
			set { ragionesociale = value; }
		}

		private string partitaiva = null;

		[TextFileInfo(585, 11)]
		[DataField("PARTITAIVA", Size=11, Type=DbType.String)]
		public string PARTITAIVA
		{
			get { return partitaiva; }
			set { partitaiva = value; }
		}

		private string codng = null;

		[DataField("CODNG", Size=2, Type=DbType.String)]
		public string CODNG
		{
			get { return codng; }
			set { codng = value; }
		}

/*
		string statoattivita=null;
		[DataField("STATOATTIVITA",Size=1,Type=DbType.String)]
		public string STATOATTIVITA
		{
			get{ return statoattivita; }
			set{ statoattivita = value; }
		}
*/
		private string oggettosociale = null;

		[DataField("OGGETTOSOCIALE", Size=320, Type=DbType.String)]
		public string OGGETTOSOCIALE
		{
			get { return oggettosociale; }
			set { oggettosociale = value; }
		}

/*
		string flagogsoc=null;
		[DataField("FLAGOGGSOC",Size=1,Type=DbType.String)]
		public string FLAGOGGSOC
		{
			get{ return flagogsoc; }
			set{ flagogsoc = value; }
		}

		string codstato=null;
		[DataField("CODSTATO",Size=3,Type=DbType.String)]
		public string CODSTATO
		{
			get{ return codstato; }
			set{ codstato = value; }
		}
*/
		private string codvia = null;

		[DataField("CODVIA", Size=5, Type=DbType.Int16)]
		public string CODVIA
		{
			get { return codvia; }
			set { codvia = value; }
		}

		private string descrcomune = null;

		[DataField("DESCRCOMUNE", Size=30, Type=DbType.String)]
		public string DESCRCOMUNE
		{
			get
			{
				int index = descrcomune.IndexOf(" - ");

				if (index != -1)
				{
					return descrcomune.Substring(0, index);

				}
				return descrcomune;
			}
			set { descrcomune = value; }
		}

		private string descrlocalita = null;

		[DataField("DESCRLOCALITA", Size=30, Type=DbType.String)]
		public string DESCRLOCALITA
		{
			get { return descrlocalita; }
			set { descrlocalita = value; }
		}

/*
		string codtoponimo=null;
		[DataField("CODTOPONIMO",Size=3,Type=DbType.String)]
		public string CODTOPONIMO
		{
			get{ return codtoponimo; }
			set{ codtoponimo = value; }
		}
*/
		private string descrvia = null;

		[DataField("DESCRVIA", Size=30, Type=DbType.String)]
		public string DESCRVIA
		{
			get { return descrvia; }
			set { descrvia = value; }
		}

/*
		string numcivico=null;
		[DataField("NUMCIVICO",Size=6,Type=DbType.String)]
		public string NUMCIVICO
		{
			get{ return numcivico; }
			set{ numcivico = value; }
		}
*/
		private string cap = null;

		[DataField("CAP", Size=5, Type=DbType.String)]
		public string CAP
		{
			get { return cap; }
			set { cap = value; }
		}

/*
		string codviaccia=null;
		[DataField("CODVIACCIA",Size=5,Type=DbType.String)]
		public string CODVIACCIA
		{
			get{ return codviaccia; }
			set{ codviaccia = value; }
		}

		string progul=null;
		[TextFileInfo(44,5)]
		[DataField("PROGUL",Size=5,Type=DbType.Int16)]
		public string PROGUL
		{
			get{ return progul; }
			set{ progul = value; }
		}

		string numeroiscrizionerea=null;
		[DataField("NUMEROISCRIZIONEREA",Size=9,Type=DbType.Int16)]
		public string NUMEROISCRIZIONEREA
		{
			get{ return numeroiscrizionerea; }
			set{ numeroiscrizionerea = value; }
		}

		string codalbo=null;
		[DataField("CODALBO",Size=2,Type=DbType.String)]
		public string CODALBO
		{
			get{ return codalbo; }
			set{ codalbo = value; }
		}
*/
		private string codsezione = null;

		[DataField("CODSEZIONE", Size=1, Type=DbType.String)]
		public string CODSEZIONE
		{
			get { return codsezione; }
			set { codsezione = value; }
		}

		private DateTime datainizio;

		[DataField("DATAINIZIO", Type=DbType.DateTime)]
		public DateTime DATAINIZIO
		{
			get { return datainizio; }
			set { datainizio = value; }
		}

		/*
		string progps=null;
		[DataField("PROGPS",Size=5,Type=DbType.Int16)]
		public string PROGPS
		{
			get{ return progps; }
			set{ progps = value; }
		}

		string provcciasede=null;
		[DataField("PROVCCIASEDE",Size=2,Type=DbType.String)]
		public string PROVCCIASEDE
		{
			get{ return provcciasede; }
			set{ provcciasede = value; }
		}
*/
		//Attenzione, questa proprietà non viene popolata dal metodo PopolaClasse( OracleDataReader p_dr , Type classType)
		//ma da una funzionalità apposita.

		private string codistatcom = null;

		[DataField("CODISTATCOM", Size=6, Type=DbType.String)]
		public string CODISTATCOM
		{
			get { return codistatcom; }
			set { codistatcom = value; }
		}

		protected string m_connectionString = null;

		public string CONNECTIONSTRING
		{
			get { return m_connectionString; }
			set { m_connectionString = value; }
		}

		protected ProviderType m_provider;

		public ProviderType PROVIDER
		{
			get { return m_provider; }
			set { m_provider = value; }
		}

		private string GetIstat(string p_descr)
		{
			DataBase db = new DataBase(m_connectionString, m_provider);
			db.Connection.Open();

			string Sql = "SELECT CODICEISTAT FROM COMUNI WHERE COMUNE = '" + p_descr.Replace("'", "''") + "'";
			string retVal = String.Empty;

			IDbCommand cmd = db.CreateCommand(Sql);

			IDataReader dr = cmd.ExecuteReader();
			if (dr.Read())
			{
				retVal = dr["CODICEISTAT"].ToString();
			}

			db.Connection.Close();
			db.Dispose();

			return retVal;
		}

		private string GetComune(string p_comune)
		{
			string retVal = p_comune;
			int index = p_comune.IndexOf(" - ");

			if (index != -1)
			{
				retVal = p_comune.Substring(0, index);

			}
			return retVal;
		}

		public int Insert(DataBase p_db)
		{
			DataBase db = new DataBase(m_connectionString, m_provider);
			db.Connection.Open();

			DataProviderFactory dpf = new DataProviderFactory(db.Connection);

			IDbCommand dbCmd = db.CreateCommand();
			dbCmd.CommandText = @"SELECT PV,N_REA,C_FISCALE,N_REG_IMP,to_date(DT_ISCR_RI,'dd/MM/yyyy'),to_date(DT_ISCR_AA,'dd/MM/yyyy'),DENOMINAZIONE,PARTITA_IVA,NG,ATTIVITA,STRAD,COMUNE,FRAZIONE,INDIRIZZO,CAP," + dpf.Specifics.SubstrFunction("SEZ_REG_IMP", 1, 1) + ",to_date(DT_INI_AT,'dd/MM/yyyy') FROM TMP_IMPORT WHERE UL_SEDE = 'SEDE' ORDER BY PRG ASC";

			IDataReader sourceReader = dbCmd.ExecuteReader();

			DataTableReader dtr = new DataTableReader();

			int rec = 0;
			string tComune = "";

			while (sourceReader.Read())
			{
				IMPR_IMPRESA imp = new IMPR_IMPRESA();
				imp = (IMPR_IMPRESA) dtr.PopolaClasse(sourceReader, typeof (IMPR_IMPRESA));

				imp.CONNECTIONSTRING = m_connectionString;
				imp.PROVIDER = m_provider;
				tComune = GetComune(sourceReader["COMUNE"].ToString());
				if (! StringChecker.IsStringEmpty(tComune))
					imp.CODISTATCOM = imp.GetIstat(tComune);

				try
				{
					rec += p_db.Insert(imp);
				}
				catch (Exception Ex)
				{
					EventLog.WriteEntry("CCIAAImport [" + imp.PROVINCIACCIA + "-" + imp.NUMISCRIZIONEREA + "]", Ex.Message);
				}
			}

			dbCmd.Connection.Close();
			dbCmd.Dispose();
			sourceReader.Close();
			sourceReader.Dispose();
			db.Connection.Close();
			db.Dispose();

			return rec;
		}
	}
}
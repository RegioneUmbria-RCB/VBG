using System;
using System.Data;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;

namespace SIGePro.Net.Data
{
	[DataTable("T_UNLO_UNITALOCALE")]
	public class UNLO_UNITALOCALE : DataClass
	{
		private string provinciaccia = null;

		[KeyField("PROVINCIACCIA", Size=2, Type=DbType.String)]
		public string PROVINCIACCIA
		{
			get { return provinciaccia; }
			set { provinciaccia = value; }
		}

		private string numiscrizionerea = null;

		[KeyField("NUMISCRIZIONEREA", Type=DbType.Decimal)]
		public string NUMISCRIZIONEREA
		{
			get { return numiscrizionerea; }
			set { numiscrizionerea = value; }
		}

		private string progul = null;

		[KeyField("PROGUL", Type=DbType.Decimal)]
		public string PROGUL
		{
			get
			{
				if (StringChecker.IsNumeric(progul))
					return progul;
				else
					return "0";
			}
			set { progul = value; }
		}

/*
		string codtipo=null;
		[DataField("CODTIPO",Size=3, Type=DbType.String)]
		public string CODTIPO
		{
			get { return codtipo; }
			set { codtipo = value; }
		}

		string tipolocalizzazione=null;
		[DataField("TIPOLOCALIZZAZIONE",Size=2, Type=DbType.String)]
		public string TIPOLOCALIZZAZIONE
		{
			get { return tipolocalizzazione; }
			set { tipolocalizzazione = value; }
		}
*/
		private string denominazione = null;

		[DataField("DENOMINAZIONE", Size=305, Type=DbType.String, Compare="like")]
		public string DENOMINAZIONE
		{
			get { return denominazione; }
			set { denominazione = value; }
		}

/*
		string insegna=null;
		[DataField("INSEGNA",Size=50, Type=DbType.String, Compare="like")]
		public string INSEGNA
		{
			get { return insegna; }
			set { insegna = value; }
		}
*/
		private DateTime dataapertura;

		[DataField("DATAAPERTURA", Type=DbType.DateTime)]
		public DateTime DATAAPERTURA
		{
			get { return dataapertura; }
			set { dataapertura = value; }
		}

/*
		string codstato=null;
		[DataField("CODSTATO",Size=3, Type=DbType.String)]
		public string CODSTATO
		{
			get { return codstato; }
			set { codstato = value; }
		}

		string codistatcom=null;
		[DataField("CODISTATCOM",Size=3, Type=DbType.String)]
		public string CODISTATCOM
		{
			get { return codistatcom; }
			set { codistatcom = value; }
		}
*/
		private string codvia = null;

		[DataField("CODVIA", Type=DbType.Decimal)]
		public string CODVIA
		{
			get { return codvia; }
			set { codvia = value; }
		}

		private string descrcomune = null;

		[DataField("DESCRCOMUNE", Size=30, Type=DbType.String, Compare="like")]
		public string DESCRCOMUNE
		{
			get { return descrcomune; }
			set { descrcomune = value; }
		}

		private string descrlocalita = null;

		[DataField("DESCRLOCALITA", Size=30, Type=DbType.String, Compare="like")]
		public string DESCRLOCALITA
		{
			get { return descrlocalita; }
			set { descrlocalita = value; }
		}

/*
		string codtoponimo=null;
		[DataField("CODTOPONIMO",Size=3, Type=DbType.String)]
		public string CODTOPONIMO
		{
			get { return codtoponimo; }
			set { codtoponimo = value; }
		}
*/
		private string descrvia = null;

		[DataField("DESCRVIA", Size=30, Type=DbType.String, Compare="like")]
		public string DESCRVIA
		{
			get { return descrvia; }
			set { descrvia = value; }
		}

/*
		string numcivico=null;
		[DataField("NUMCIVICO",Size=6, Type=DbType.String)]
		public string NUMCIVICO
		{
			get { return numcivico; }
			set { numcivico = value; }
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
		[DataField("CODVIACCIA",Size=5, Type=DbType.String)]
		public string CODVIACCIA
		{
			get { return codviaccia; }
			set { codviaccia = value; }
		}

		string statoattivita=null;
		[DataField("STATOATTIVITA",Size=1, Type=DbType.String)]
		public string STATOATTIVITA
		{
			get { return statoattivita; }
			set { statoattivita = value; }
		}

		string codatt=null;
		[DataField("CODATT",Size=6, Type=DbType.String)]
		public string CODATT
		{
			get { return codatt; }
			set { codatt = value; }
		}

		string cocausacess=null;
		[DataField("COCAUSACESS",Size=2, Type=DbType.String)]
		public string COCAUSACESS
		{
			get { return cocausacess; }
			set { cocausacess = value; }
		}
*/
		private DateTime datacessazione;

		[DataField("DATACESSAZIONE", Type=DbType.DateTime)]
		public DateTime DATACESSAZIONE
		{
			get { return datacessazione; }
			set { datacessazione = value; }
		}

		private string datadencess = null;

		[DataField("DATADENCESS", Type=DbType.String)]
		public string DATADENCESS
		{
			get { return datadencess; }
			set { datadencess = value; }
		}

/*
		private string GetIstat( string p_descr )
		{
			DataBase db = new DataBase( m_connectionString , m_provider );
			db.Connection.Open();
			string Sql = "SELECT CODISTATCOM FROM T_COMU_COMUNI WHERE DESCRIZIONE = '" + p_descr.Replace("'","''") + "'";
			IDbCommand cmd = db.CreateCommand( Sql );
			string retVal = cmd.ExecuteScalar().ToString();
			
			db.Connection.Close();
			db.Dispose();

			return retVal;
		}

		private string GetComune( string p_comune )
		{
			string retVal = p_comune;
			int index = p_comune.IndexOf(" - ");

			if(index != -1 )
			{
				retVal = p_comune.Substring( 0 , index );

			}				
			return retVal; 
		}
*/

		public int Insert(string p_connectionString, DataBase p_db)
		{
			DataBase db = new DataBase(p_connectionString, ProviderType.OleDb);
			db.Connection.Open();

			DataProviderFactory dpf = new DataProviderFactory(db.Connection);
			IDbCommand dbCmd = db.CreateCommand();

			dbCmd.CommandText = @"SELECT PV,N_REA," + dpf.Specifics.SubstrFunction("UL_SEDE", 4, 1) + " AS PROGUL,DENOMINAZIONE,to_date(DT_APER_UL,'dd/MM/yyyy'),STRAD,COMUNE,FRAZIONE,INDIRIZZO,CAP,to_date(DT_CESSAZ,'dd/MM/yyyy'),to_date(DT_CES_AT,'dd/MM/yyyy') FROM TMP_IMPORT WHERE UL_SEDE <> 'SEDE'";
			IDataReader sourceReader = dbCmd.ExecuteReader();

			DataTableReader dtr = new DataTableReader();
			int rec = 0;

			while (sourceReader.Read())
			{
				UNLO_UNITALOCALE ul = new UNLO_UNITALOCALE();
				ul = (UNLO_UNITALOCALE) dtr.PopolaClasse(sourceReader, typeof (UNLO_UNITALOCALE));
				rec += p_db.Insert(ul);
			}

			dbCmd.Connection.Close();
			dbCmd.Dispose();
			sourceReader.Close();
			sourceReader.Dispose();
			db.Connection.Close();
			db.Dispose();

			return rec;

/*
			OracleConnection sourceCnn = new OracleConnection( ConfigurationSettings.AppSettings["OraConnectionString"] );
			OracleConnection destCnn = new OracleConnection( ConfigurationSettings.AppSettings["OraConnectionString"] );
			
			sourceCnn.Open();
			destCnn.Open();
			
			OracleCommand sourceCmd = sourceCnn.CreateCommand();
			OracleCommand destCmd = destCnn.CreateCommand();

			sourceCmd.CommandText = destCmd.CommandText = "alter session set nls_date_format = 'dd/mm/yyyy'";

			sourceCmd.ExecuteNonQuery();
			destCmd.ExecuteNonQuery();

			sourceCmd.CommandText = @"SELECT PV,N_REA,substr(UL_SEDE,4,1) AS PROGUL,DENOMINAZIONE,DT_APER_UL,STRAD,COMUNE,FRAZIONE,INDIRIZZO,CAP,DT_CESSAZ,DT_CES_AT FROM TMP_IMPORT WHERE UL_SEDE <> 'SEDE'";
			OracleDataReader sourceReader = sourceCmd.ExecuteReader();

			int rec = 0;
			int i = 0;

			PersonalLib2.Sql.  se = new SqlEngine(ConfigurationSettings.AppSettings["ConnectionString"]);
			CCIAAImport.Data.DataTableReader dtr = new DataTableReader();			
			
			p_progBar.Minimum = 0;
			p_progBar.Maximum = dtr.recAffected( "SELECT COUNT(*) AS CONTA FROM TMP_IMPORT WHERE UL_SEDE <> 'SEDE'" );


			while( sourceReader.Read() )
			{
				Application.DoEvents();
				i++;
				p_progBar.Value = i;
				UNLO_UNITALOCALE ul = new UNLO_UNITALOCALE();
				ul = ( UNLO_UNITALOCALE ) dtr.PopolaClasse( sourceReader , typeof( UNLO_UNITALOCALE ) );
				destCmd.CommandText = se.buildInsert( ul );
				rec = destCmd.ExecuteNonQuery();
			}
*/
		}
	}
}
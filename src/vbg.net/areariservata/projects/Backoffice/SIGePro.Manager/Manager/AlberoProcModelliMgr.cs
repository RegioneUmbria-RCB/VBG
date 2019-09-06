using System;
using System.Collections;
using System.Data;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per AlberoProcModelliMgr.\n	/// </summary>
	public class AlberoProcModelliMgr: BaseManager
{


		public AlberoProcModelliMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public AlberoProcModelli GetById(String pSM_ID, String pIDCOMUNE)
		{
			AlberoProcModelli retVal = new AlberoProcModelli();
			retVal.SM_ID = pSM_ID;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as AlberoProcModelli;
			
			return null;
		}

		public AlberoProcModelli GetByClass( AlberoProcModelli pClass )
		{
			AlberoProcModelli retVal = ( pClass.Clone() as AlberoProcModelli );
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);

			if (mydc.Count!=0)
				return (mydc[0]) as AlberoProcModelli;
			
			return null;
		}

        public List<AlberoProcModelli> GetByIntervento( string idcomune, string codiceintervento )
        {
            AlberoProcModelli apm = new AlberoProcModelli();
            apm.IDCOMUNE = idcomune;
            apm.SM_FKSCID = codiceintervento;

			return db.GetClassList(apm, false, false).ToList < AlberoProcModelli>();
        } 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(AlberoProcModelli p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(AlberoProcModelli p_class, AlberoProcModelli p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

	#endregion

		public string SM_FKMOID( string pSM_FKSCID, string pIDCOMUNE )
		{
			string retVal = String.Empty;

			AlberoProcModelli p_alberoprocmodelli = new AlberoProcModelli();

			p_alberoprocmodelli.SM_FKSCID = pSM_FKSCID;
			p_alberoprocmodelli.IDCOMUNE = pIDCOMUNE;

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				db.Connection.Open();
			}

			try
			{
				using (IDataReader p_reader = db.CreateCommand(p_alberoprocmodelli).ExecuteReader())
				{
					if (p_reader.Read())
						retVal = (p_reader["SM_FKMOID"] == DBNull.Value) ? p_reader["SM_FKMOID"].ToString() : String.Empty;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
			
			return retVal;

		}

        public List<AlberoProcModelli> GetListByAlberoProc( string idcomune, string software, string scCodiceAlbero )
        {
            string codici = string.Empty;

            AlberoProcModelli cls = new AlberoProcModelli();
            cls.IDCOMUNE = idcomune;
            
            for (int i = 2; i <= scCodiceAlbero.Length; i+=2)
			{
			     codici = codici + "'" + scCodiceAlbero.Substring(0,i) + "',";
			}

            codici = codici.Substring(0, codici.Length -1 );

            cls.OthersTables.Add("ALBEROPROC");
            cls.OthersWhereClause.Add("ALBEROPROC.IDCOMUNE = ALBEROPROC_MODELLI.IDCOMUNE");
            cls.OthersWhereClause.Add("ALBEROPROC.SC_ID = ALBEROPROC_MODELLI.SM_FKSCID");
            cls.OthersWhereClause.Add("ALBEROPROC.IDCOMUNE = '" + idcomune + "'");
            cls.OthersWhereClause.Add("ALBEROPROC.SOFTWARE = '" + software + "'");
            cls.OthersWhereClause.Add("ALBEROPROC.SC_CODICE IN (" + codici + ")");
            cls.OrderBy = "SC_CODICE DESC";

            return db.GetClassList(cls).ToList<AlberoProcModelli>();
        }
	}
}
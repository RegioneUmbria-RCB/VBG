using System;
using System.Collections;
using System.Data;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using Init.Utils;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Configuration;

namespace Init.SIGeProExport.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per EsportazioniMgr.
	/// </summary>
	public class TracciatiDettMgr : BaseManager
	{
		public TracciatiDettMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public TRACCIATIDETTAGLIO GetById(String pIDCOMUNE, String pID)
		{
			TRACCIATIDETTAGLIO retVal = new TRACCIATIDETTAGLIO();
            retVal.IDCOMUNE = pIDCOMUNE;
			retVal.ID = pID;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TRACCIATIDETTAGLIO;
			
			return null;
		}

        public TRACCIATIDETTAGLIO GetDefault(String sID)
        {
            return GetById( ConfigurationSettings.AppSettings["IDCOMUNE_DEFAULT"].ToString(), sID);
        }

        public TRACCIATIDETTAGLIO GetEnteOrDefault(String sIDCOMUNE, String sID)
        {
            TRACCIATIDETTAGLIO retVal = GetById(sIDCOMUNE, sID);

            if ( retVal == null )
                retVal = GetById(ConfigurationSettings.AppSettings["IDCOMUNE_DEFAULT"].ToString(), sID);

            return retVal;
        }

		public TRACCIATIDETTAGLIO GetByClass( TRACCIATIDETTAGLIO p_class )
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(p_class,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TRACCIATIDETTAGLIO;

			return null;
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<TRACCIATIDETTAGLIO> GetList(TRACCIATIDETTAGLIO p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public List<TRACCIATIDETTAGLIO> GetList(TRACCIATIDETTAGLIO p_class, TRACCIATIDETTAGLIO p_cmpClass )
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<TRACCIATIDETTAGLIO>();
		}
		

		public TRACCIATIDETTAGLIO Insert( TRACCIATIDETTAGLIO p_class )
		{
            if (string.IsNullOrEmpty(p_class.ID))
                p_class.ID = (findMax("TRACCIATIDETTAGLIO", "ID", "IDCOMUNE = '" + p_class.IDCOMUNE + "'") + 1).ToString();

			db.Insert( p_class );
			return p_class;
		}

		public void Delete( TRACCIATIDETTAGLIO p_class )
		{
			db.Delete( p_class );
		}

        public void Delete(String IdComune, String IdTracciato)
        {
            if (String.IsNullOrEmpty(IdComune)) { throw new Exception("Alla funzione TracciatiDettMgr.Delete non è stato passato IDCOMUNE"); }
            if (String.IsNullOrEmpty(IdTracciato)) { throw new Exception("Alla funzione TracciatiDettMgr.Delete non è stato passato FK_TRACCIATI_ID"); }
            
            TRACCIATIDETTAGLIO td = new TRACCIATIDETTAGLIO();
            td.IDCOMUNE = IdComune;
            td.FK_TRACCIATI_ID = IdTracciato;

            List<TRACCIATIDETTAGLIO> listDettagli = GetList(td);
            foreach (TRACCIATIDETTAGLIO item in listDettagli)
                Delete(item);

        }

		public TRACCIATIDETTAGLIO Update( TRACCIATIDETTAGLIO p_class )
		{		
			db.Update( p_class );
			return p_class;
		}

        public StringCollection getQueries(string IdEsportazione, string IdTracciato, string IdComune, int DettOrdineMax)
        {
            //throw new Exception("Il metodo getQueries di TracciatiDettMgr è da rivedere");

            StringCollection retVal = new StringCollection();

            string cmdText = String.Empty;

            try
            {
                cmdText = "SELECT ID, QUERY FROM TRACCIATI WHERE IDCOMUNE = '" + IdComune + "' AND FK_ESP_ID = " + IdEsportazione;
                
                if( ! String.IsNullOrEmpty(IdTracciato) )
                    cmdText +=" AND OUT_ORDINE <= ( SELECT OUT_ORDINE FROM TRACCIATI WHERE IDCOMUNE = '" + IdComune + "' AND ID = " + IdTracciato + " )";

                IDbCommand cmd = db.CreateCommand(cmdText);

                DataProviderFactory dpf = new DataProviderFactory(db.Connection);
                IDbDataAdapter adapter = dpf.CreateDataAdapter(cmd);
                DataSet ds = new DataSet();

                adapter.Fill(ds);

                foreach (DataRow dr in ds.Tables[0].Rows)
                {
                    string pIdTracciato = dr["ID"].ToString();

                    //per ogni tracciato trovato prendo la query di testata ....
                    string queryTesta = (dr["QUERY"] != DBNull.Value) ? dr["QUERY"].ToString() : String.Empty;
                    if (!StringChecker.IsStringEmpty(queryTesta))
                        retVal.Add(queryTesta);

                    //... e il dettaglio ...
                    StringCollection queryDett = new StringCollection();

                    if (IdTracciato == pIdTracciato)
                        queryDett = getQueriesDett(pIdTracciato, IdComune, DettOrdineMax);
                    else
                        queryDett = getQueriesDett(pIdTracciato, IdComune, -1);


                    //... aggiungo il dettaglio trovato alla collection di query da ritornare
                    foreach (string q in queryDett)
                        retVal.Add(q);

                }


            }
            catch (System.Exception Ex)
            {
                throw Ex;
            }

            return retVal;
        }


        protected StringCollection getQueriesDett(string IdTracciato, string IdComune, int OrdineMax)
        {
            StringCollection retVal = new StringCollection();
            bool internalOpen = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    internalOpen = true;
                }

                string cmdText = "select " +
                                        "tracciatidettaglio.id,tracciatidettaglio.query " +
                                    "from " +
                                        "tracciatidettaglio " +
                                    "where " +
                                        "tracciatidettaglio.idcomune = '" + IdComune + "' and " +
                                        "tracciatidettaglio.fk_tracciati_id=" + IdTracciato + " and " +
                                        "tracciatidettaglio.query is not null ";
                if (OrdineMax > -1)
                    cmdText += "and tracciatidettaglio.out_ordine <= " + OrdineMax.ToString() + " ";

                using (IDbCommand cmd = db.CreateCommand(cmdText))
                {
                    using (IDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            retVal.Add(reader["query"].ToString());
                        }
                    }
                }
            }
            catch (System.Exception Ex)
            {
                throw Ex;
            }
            finally
            {
                if (internalOpen)
                    db.Connection.Close();
            }

            return retVal;
        }


		#endregion
	}
}

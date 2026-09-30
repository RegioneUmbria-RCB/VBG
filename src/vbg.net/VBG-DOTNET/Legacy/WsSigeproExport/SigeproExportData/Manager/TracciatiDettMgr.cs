using Init.SIGeProExport.Data;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Data.V2.Legacy;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Configuration;
using System.Data;

namespace Init.SIGeProExport.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per EsportazioniMgr.
    /// </summary>
    public class TracciatiDettMgr : BaseManager
    {
        public TracciatiDettMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TRACCIATIDETTAGLIO GetById(String pIDCOMUNE, String pID)
        {
            TRACCIATIDETTAGLIO retVal = new TRACCIATIDETTAGLIO
            {
                IDCOMUNE = pIDCOMUNE,
                ID = pID
            };

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public TRACCIATIDETTAGLIO GetDefault(String sID)
        {
            return this.GetById(ConfigurationManager.AppSettings["IDCOMUNE_DEFAULT"].ToString(), sID);
        }

        public TRACCIATIDETTAGLIO GetEnteOrDefault(String sIDCOMUNE, String sID)
        {
            return this.GetById(sIDCOMUNE, sID) ?? this.GetById(ConfigurationManager.AppSettings["IDCOMUNE_DEFAULT"].ToString(), sID);
        }

        public TRACCIATIDETTAGLIO GetByClass(TRACCIATIDETTAGLIO p_class)
        {
            var mydc = this.db.GetClassList(p_class, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<TRACCIATIDETTAGLIO> GetList(TRACCIATIDETTAGLIO p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<TRACCIATIDETTAGLIO> GetList(TRACCIATIDETTAGLIO p_class, TRACCIATIDETTAGLIO p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass);
        }


        public TRACCIATIDETTAGLIO Insert(TRACCIATIDETTAGLIO p_class)
        {
            if (string.IsNullOrEmpty(p_class.ID))
                p_class.ID = (this.findMax("TRACCIATIDETTAGLIO", "ID", "IDCOMUNE = '" + p_class.IDCOMUNE + "'") + 1).ToString();

            this.db.Insert(p_class);
            return p_class;
        }

        public void Delete(TRACCIATIDETTAGLIO p_class)
        {
            this.db.Delete(p_class);
        }

        public void Delete(String IdComune, String IdTracciato)
        {
            if (String.IsNullOrEmpty(IdComune)) { throw new Exception("Alla funzione TracciatiDettMgr.Delete non è stato passato IDCOMUNE"); }
            if (String.IsNullOrEmpty(IdTracciato)) { throw new Exception("Alla funzione TracciatiDettMgr.Delete non è stato passato FK_TRACCIATI_ID"); }

            TRACCIATIDETTAGLIO td = new TRACCIATIDETTAGLIO
            {
                IDCOMUNE = IdComune,
                FK_TRACCIATI_ID = IdTracciato
            };

            List<TRACCIATIDETTAGLIO> listDettagli = this.GetList(td);
            foreach (TRACCIATIDETTAGLIO item in listDettagli)
                this.Delete(item);

        }

        public TRACCIATIDETTAGLIO Update(TRACCIATIDETTAGLIO p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }

        public StringCollection GetQueries(string IdEsportazione, string IdTracciato, string IdComune, int DettOrdineMax)
        {
            StringCollection retVal = new StringCollection();

            try
            {
                var cmdText = "SELECT ID, QUERY FROM TRACCIATI WHERE IDCOMUNE = '" + IdComune + "' AND FK_ESP_ID = " + IdEsportazione;

                if (!String.IsNullOrEmpty(IdTracciato))
                    cmdText += " AND OUT_ORDINE <= ( SELECT OUT_ORDINE FROM TRACCIATI WHERE IDCOMUNE = '" + IdComune + "' AND ID = " + IdTracciato + " )";

                IDbCommand cmd = this.db.CreateCommand(cmdText);

                DataProviderFactory dpf = new DataProviderFactory(this.db.Connection);
                IDbDataAdapter adapter = dpf.CreateDataAdapter(cmd);
                DataSet ds = new DataSet();

                adapter.Fill(ds);

                foreach (DataRow dr in ds.Tables[0].Rows)
                {
                    string pIdTracciato = dr["ID"].ToString();

                    //per ogni tracciato trovato prendo la query di testata ....
                    string queryTesta = (dr["QUERY"] != DBNull.Value) ? dr["QUERY"].ToString() : String.Empty;
                    if (!String.IsNullOrEmpty(queryTesta))
                        retVal.Add(queryTesta);

                    //... e il dettaglio ...
                    StringCollection queryDett = new StringCollection();

                    if (IdTracciato == pIdTracciato)
                        queryDett = this.GetQueriesDett(pIdTracciato, IdComune, DettOrdineMax);
                    else
                        queryDett = this.GetQueriesDett(pIdTracciato, IdComune, -1);


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


        protected StringCollection GetQueriesDett(string IdTracciato, string IdComune, int OrdineMax)
        {
            StringCollection retVal = new StringCollection();
            bool internalOpen = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
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

                using (IDbCommand cmd = this.db.CreateCommand(cmdText))
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
                    this.db.Connection.Close();
            }

            return retVal;
        }


        #endregion
    }
}

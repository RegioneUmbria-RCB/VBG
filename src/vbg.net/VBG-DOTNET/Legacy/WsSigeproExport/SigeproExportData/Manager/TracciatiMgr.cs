using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;

namespace Init.SIGeProExport.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per EsportazioniMgr.
    /// </summary>
    public class TracciatiMgr : BaseManager
    {
        public TracciatiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TRACCIATI GetById(String pIDCOMUNE, String pID)
        {
            TRACCIATI retVal = new TRACCIATI
            {
                IDCOMUNE = pIDCOMUNE,
                ID = pID
            };

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public TRACCIATI GetDefault(String sID)
        {
            return this.GetById(ConfigurationManager.AppSettings["IDCOMUNE_DEFAULT"].ToString(), sID);
        }

        public TRACCIATI GetByClass(TRACCIATI p_class)
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
        public List<TRACCIATI> GetList(TRACCIATI p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<TRACCIATI> GetList(TRACCIATI p_class, bool AddChild)
        {
            return this.GetList(p_class, null, AddChild);
        }

        public List<TRACCIATI> GetList(TRACCIATI p_class, TRACCIATI p_cmpClass)
        {
            return this.GetList(p_class, p_cmpClass, false);
        }

        public List<TRACCIATI> GetList(TRACCIATI p_class, TRACCIATI p_cmpClass, bool AddChild)
        {
            List<TRACCIATI> retVal = this.db.GetClassList(p_class, p_cmpClass);

            if (retVal != null && AddChild)
            {
                foreach (TRACCIATI t in retVal)
                {
                    TRACCIATIDETTAGLIO td = new TRACCIATIDETTAGLIO
                    {
                        IDCOMUNE = t.IDCOMUNE,
                        FK_TRACCIATI_ID = t.ID
                    };

                    t.TracciatiDettagli = new TracciatiDettMgr(this.db).GetList(td);
                }
            }

            return retVal;
        }


        public TRACCIATI Insert(TRACCIATI p_class)
        {
            if (string.IsNullOrEmpty(p_class.ID))
                p_class.ID = (this.findMax("TRACCIATI", "ID", "IDCOMUNE = '" + p_class.IDCOMUNE + "'") + 1).ToString();

            this.db.Insert(p_class);
            return p_class;
        }

        public TRACCIATI InsertAll(TRACCIATI p_class)
        {
            p_class = this.Insert(p_class);

            foreach (TRACCIATIDETTAGLIO td in p_class.TracciatiDettagli)
            {
                td.IDCOMUNE = p_class.IDCOMUNE;
                td.FK_TRACCIATI_ID = p_class.ID;

                new TracciatiDettMgr(this.db).Insert(td);
            }

            return p_class;
        }

        public TRACCIATI Delete(TRACCIATI p_class)
        {
            bool internalOpen = false;
            if (this.db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                this.db.BeginTransaction();
            }

            try
            {
                TracciatiDettMgr mgr = new TracciatiDettMgr(this.db);
                mgr.Delete(p_class.IDCOMUNE, p_class.ID);

                this.db.Delete(p_class);
            }
            catch (EvaluateException e)
            {
                this.db.RollbackTransaction();
                throw e;
            }

            if (internalOpen)
            {
                this.db.CommitTransaction();
                this.db.Connection.Close();
            }

            return p_class;
        }

        public TRACCIATI Update(TRACCIATI p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }

        #endregion
    }
}

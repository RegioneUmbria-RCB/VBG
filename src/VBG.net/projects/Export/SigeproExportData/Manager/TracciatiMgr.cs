using System;
using System.Collections;
using System.Data;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using Init.Utils;
using System.Configuration;
using System.Collections.Generic;

namespace Init.SIGeProExport.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per EsportazioniMgr.
	/// </summary>
	public class TracciatiMgr : BaseManager
	{
		public TracciatiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public TRACCIATI GetById(String pIDCOMUNE, String pID)
		{
			TRACCIATI retVal = new TRACCIATI();
            retVal.IDCOMUNE = pIDCOMUNE;
			retVal.ID = pID;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TRACCIATI;
			
			return null;
		}

        public TRACCIATI GetDefault(String sID) 
        {
            return GetById(ConfigurationSettings.AppSettings["IDCOMUNE_DEFAULT"].ToString(), sID);
        }

		public TRACCIATI GetByClass( TRACCIATI p_class )
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(p_class,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TRACCIATI;

			return null;
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<TRACCIATI> GetList(TRACCIATI p_class)
		{
			return this.GetList(p_class,null);
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
            List<TRACCIATI> retVal = db.GetClassList(p_class, p_cmpClass, false, false).ToList<TRACCIATI>();

            if (retVal != null && AddChild)
            {
                foreach (TRACCIATI t in retVal)
                {
                    TRACCIATIDETTAGLIO td = new TRACCIATIDETTAGLIO();
                    td.IDCOMUNE = t.IDCOMUNE;
                    td.FK_TRACCIATI_ID = t.ID;

                    t.TracciatiDettagli = new TracciatiDettMgr(db).GetList(td);
                }
            }

            return retVal;
        }
		

		public TRACCIATI Insert( TRACCIATI p_class )
		{
            if (string.IsNullOrEmpty(p_class.ID))
                p_class.ID = (findMax("TRACCIATI", "ID", "IDCOMUNE = '" + p_class.IDCOMUNE + "'") + 1).ToString();

			db.Insert( p_class );
			return p_class;
		}

        public TRACCIATI InsertAll(TRACCIATI p_class)
        {
            p_class = Insert(p_class);

            foreach (TRACCIATIDETTAGLIO td in p_class.TracciatiDettagli)
            {
                td.IDCOMUNE = p_class.IDCOMUNE;
                td.FK_TRACCIATI_ID = p_class.ID;

                new TracciatiDettMgr(db).Insert(td);
            }
            
            return p_class;
        }

		public TRACCIATI Delete( TRACCIATI p_class )
		{
            bool internalOpen = false;
            if (db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                db.BeginTransaction();
            }

            try
            {
                TracciatiDettMgr mgr = new TracciatiDettMgr(db);
                mgr.Delete(p_class.IDCOMUNE, p_class.ID);

                db.Delete(p_class);
            }
            catch (EvaluateException e)
            {
                db.RollbackTransaction();
                throw (e);
            }

            if (internalOpen)
            {
                db.CommitTransaction();
                db.Connection.Close();
            }

			return p_class;
		}

		public TRACCIATI Update( TRACCIATI p_class )
		{		
			db.Update( p_class );
			return p_class;
		}

		#endregion
	}
}

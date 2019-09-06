using System;
using System.Collections;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.Utils;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{ 	///<summary>
    /// Descrizione di riepilogo per ScadenzeMgr.\n	/// </summary>
    public class ScadenzeMgr : BaseManager
    {
        public ScadenzeMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public Scadenze GetById(String pIDCOMUNE, String pCODICESCADENZA)
        {
            Scadenze retVal = new Scadenze();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.CODICESCADENZA = pCODICESCADENZA;

            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as Scadenze;

            return null;
        }

        public List<Scadenze> GetList(Scadenze p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<Scadenze> GetList(Scadenze p_class, Scadenze p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<Scadenze>();
        }

        public void Delete(Scadenze p_class)
        {
            db.Delete(p_class);
        }

        #endregion
    }
}
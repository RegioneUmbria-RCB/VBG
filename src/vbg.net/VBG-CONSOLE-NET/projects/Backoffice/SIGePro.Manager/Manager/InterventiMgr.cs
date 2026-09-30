using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per InterventiMgr.\n	/// </summary>
    public class InterventiMgr : BaseManager
    {

        public InterventiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public Interventi GetById(int pCODICEINTERVENTO, String pIDCOMUNE)
        {
            Interventi retVal = new Interventi();
            retVal.CodiceIntervento = pCODICEINTERVENTO;
            retVal.Idcomune = pIDCOMUNE;

            List<Interventi> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Interventi;

            return null;
        }

        public Interventi GetByClass(Interventi p_class)
        {
            return (this.db.GetClassList(p_class, true)[0]) as Interventi;
        }

        #endregion
    }
}
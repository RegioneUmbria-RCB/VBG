using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiArchivioIstanzeMgr.\n	/// </summary>
    public class TipiArchivioIstanzeMgr : BaseManager
    {

        public TipiArchivioIstanzeMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TipiArchivioIstanze GetById(String pCODICEARCHIVIO, String pIDCOMUNE)
        {
            TipiArchivioIstanze retVal = new TipiArchivioIstanze();
            retVal.CODICEARCHIVIO = pCODICEARCHIVIO;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<TipiArchivioIstanze> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiArchivioIstanze;

            return null;
        }
        #endregion
    }
}


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
    /// Descrizione di riepilogo per PermCdSInvitatiMgr.\n	/// </summary>
    public class PermCdSInvitatiMgr : BaseManager
    {
        public PermCdSInvitatiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public PermCdSInvitati GetById(String pIDCOMUNE, String pCODICECDS, String pCODICEINVITATO)
        {
            PermCdSInvitati retVal = new PermCdSInvitati();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.CODICECDS = pCODICECDS;
            retVal.CODICEINVITATO = pCODICEINVITATO;

            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as PermCdSInvitati;

            return null;
        }

        public List<PermCdSInvitati> GetList(PermCdSInvitati p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<PermCdSInvitati> GetList(PermCdSInvitati p_class, PermCdSInvitati p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<PermCdSInvitati>();
        }

        public void Delete(PermCdSInvitati p_class)
        {
            db.Delete(p_class);
        }

        #endregion
    }
}
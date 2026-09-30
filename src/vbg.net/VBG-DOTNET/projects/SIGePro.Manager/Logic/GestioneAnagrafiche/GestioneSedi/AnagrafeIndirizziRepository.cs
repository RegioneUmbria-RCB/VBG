using Init.SIGePro.Manager.Manager;
using PersonalLib2.Data;
using SIGePro.Data.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.GestioneAnagrafiche.GestioneSedi
{
    // utilizzato dal protocollo Acaris
    public class AnagrafeIndirizziRepository
    {
        private readonly string _idComune;
        private readonly AnagrafeIndirizziMgr _mgr;

        public AnagrafeIndirizziRepository(DataBase db, string idComune)
        {
            this._idComune = idComune;
            this._mgr = new AnagrafeIndirizziMgr(db);
        }

        public List<AnagrafeIndirizzi> GetIndirizzi(int codiceAnagrafe)
        {
           return this._mgr.GetByCodiceAnagrafe(this._idComune, codiceAnagrafe);
        }

        public AnagrafeIndirizzi GetById(int id)
        {
            return this._mgr.GetById(this._idComune, id);
        }

        public AnagrafeIndirizzi Insert(AnagrafeIndirizzi p_class)
        {
            return this._mgr.Insert(p_class);
        }
    }
}

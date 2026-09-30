using PersonalLib2.Data;
using SIGePro.Data.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Manager
{
    public class AnagrafeIndirizziMgr : BaseManager
    {
        public AnagrafeIndirizziMgr(DataBase dataBase) : base(dataBase)
        {
        }

        public List<AnagrafeIndirizzi> GetByCodiceAnagrafe(string idComune, int codiceAnagrafe)
        {
            AnagrafeIndirizzi filtro = new AnagrafeIndirizzi
            {
                IdComune = idComune,
                CodiceAnagrafe = codiceAnagrafe
            };

            return this.db.GetClassList(filtro);
        }

        public AnagrafeIndirizzi GetById(string idComune, int id)
        {
            AnagrafeIndirizzi filtro = new AnagrafeIndirizzi
            {
                IdComune = idComune,
                Id = id
            };

            return this.db.GetClass(filtro);
        }

        public AnagrafeIndirizzi Insert(AnagrafeIndirizzi p_class)
        {
            var retVal = (AnagrafeIndirizzi)p_class.Clone();

            this.db.Insert(retVal);

            return retVal;
        }


    }
}

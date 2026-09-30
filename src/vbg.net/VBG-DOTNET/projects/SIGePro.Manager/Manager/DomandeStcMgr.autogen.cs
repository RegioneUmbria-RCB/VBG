using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{
    public partial class DomandeStcMgr : BaseManager
    {
        public DomandeStcMgr(DataBase dataBase) : base(dataBase)
        {

        }

        public DomandeStc GetById(string idComune, int id)
        {
            var domanda = new DomandeStc { IdComune = idComune, Id = id };
            return (DomandeStc)this.db.GetClass(domanda);
        }

        public IEnumerable<DomandeStc> GetDomandeByIstanza(string idComune, int codiceIstanza)
        {
            var domanda = new DomandeStc { IdComune = idComune, CodiceIstanza = codiceIstanza };

            var domande = this.db.GetClassList(domanda).ToList<DomandeStc>();

            if (domande == null || domande.Count() == 0)
            {
                domanda.CodiceIstanza = (int?)null;
                domanda.CodiceIstanzaPrenotata = codiceIstanza;

                domande = this.db.GetClassList(domanda).ToList<DomandeStc>();

            }

            return domande ?? Enumerable.Empty<DomandeStc>();
        }
    }
}

using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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
            return (DomandeStc)db.GetClass(domanda);
        }

        public IEnumerable<DomandeStc> GetDomandeByIstanza(string idComune, int codiceIstanza)
        {
            var domanda = new DomandeStc { IdComune = idComune, CodiceIstanza = codiceIstanza };
            
            var domande = db.GetClassList(domanda).ToList<DomandeStc>();
            return domande;
        }
    }
}

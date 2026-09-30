using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
    class ParicelleCatastaliResult : Ravenna2Result
    {
        public ParicelleCatastaliResult(IDataReader dr) : base(true)
        {
            this.Sezione = GetVal(dr, Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoSezione);
            this.Foglio = GetVal(dr, Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoFoglio);
            this.Particella = GetVal(dr, Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoNumero);
        }
    }
}

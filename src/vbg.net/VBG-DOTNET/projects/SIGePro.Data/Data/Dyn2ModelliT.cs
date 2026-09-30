
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
    public partial class Dyn2ModelliT : IDyn2Modello
    {
        public override string ToString()
        {
            return this.Descrizione;
        }
	}
}
				
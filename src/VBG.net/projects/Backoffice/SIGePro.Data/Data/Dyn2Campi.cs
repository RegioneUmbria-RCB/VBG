
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
    public partial class Dyn2Campi : IDyn2Campo
    {
		public override string ToString()
		{
			return this.Nomecampo;
		}
	}
}
				
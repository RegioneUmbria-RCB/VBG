using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	public partial class TipiCausaliOneri
	{
        public override string ToString()
        {
            return CoDescrizione;
        }
	}
}
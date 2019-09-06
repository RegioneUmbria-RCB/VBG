
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;

namespace Init.SIGePro.Data
{
    public partial class TipiUnitaMisura
    {
        public override string ToString()
        {
            return this.UmDescrbreve;
        }
    }
}

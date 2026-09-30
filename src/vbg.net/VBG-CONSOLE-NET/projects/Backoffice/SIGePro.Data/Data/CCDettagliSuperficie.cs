using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class CCDettagliSuperficie
    {
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        CCTipiSuperficie m_tipoSuperficie;
        [ForeignKey("Idcomune,FkCcTsId", "Idcomune,Id")]
        public CCTipiSuperficie TipoSuperficie
        {
            get { return m_tipoSuperficie; }
            set { m_tipoSuperficie = value; }
        }

        public override string ToString()
        {
            return this.Descrizione;
        }
    }
}

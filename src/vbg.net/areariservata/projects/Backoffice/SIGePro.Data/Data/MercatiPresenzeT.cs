using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class MercatiPresenzeT
    {
        private int? m_id = null;
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        private Mercati m_mercato;
        [ForeignKey(/*typeof(Mercati),*/ "Idcomune,Fkcodicemercato", "IdComune,CodiceMercato")]
        public Mercati Mercato
        {
            get { return m_mercato; }
            set { m_mercato = value; }
        }

        private Mercati_Uso m_mercatiuso;
        [ForeignKey(/*typeof(Mercati_Uso),*/ "Idcomune,Fkidmercatiuso", "IdComune,Id")]
        public Mercati_Uso MercatiUso
        {
            get { return m_mercatiuso; }
            set { m_mercatiuso = value; }
        }
    }
}

using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class PertinenzeCoefficienti
    {
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        #region Foreign
        CanoniRiduzioniOMI m_riduzioneomi;
        [ForeignKey("Idcomune,FkCrid", "Idcomune,Id")]
        public CanoniRiduzioniOMI RiduzioneOMI
        {
            get { return m_riduzioneomi; }
            set { m_riduzioneomi = value; }
        }
        #endregion
    }
}

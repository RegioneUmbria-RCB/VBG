using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class ODestinazioni
    {
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        #region Foreign

        OCCBaseDestinazioni m_destinazioneBase = null;
        [ForeignKey("FkOccbdeId", "Id")]
        public OCCBaseDestinazioni DestinazioneBase
        {
            get { return m_destinazioneBase; }
            set { m_destinazioneBase = value; }
        }

        TipiUnitaMisura m_tipoUnitaMisura = null;
        [ForeignKey("Idcomune,FkTumUmid", "Idcomune,UmId")]
        public TipiUnitaMisura TipoUnitaMisura
        {
            get { return m_tipoUnitaMisura; }
            set { m_tipoUnitaMisura = value; }
        }

        #endregion

    }
}

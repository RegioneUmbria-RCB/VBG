using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class CCDestinazioni
    {
        #region Key fields

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        [isRequired]
        [DataField("DESTINAZIONE", Type = DbType.String, CaseSensitive=false, Size = 200, Compare="like")]
        public string Destinazione
        {
            get { return m_destinazione; }
            set { m_destinazione = value; }
        }

		OCCBaseDestinazioni m_destinazioneBase = null;
		[ForeignKey(/*typeof(OCCBaseDestinazioni),*/ "FkOccbdeId", "Id")]
		public OCCBaseDestinazioni DestinazioneBase
		{
			get { return m_destinazioneBase; }
			set { m_destinazioneBase = value; }
		}

        #endregion
    }
}

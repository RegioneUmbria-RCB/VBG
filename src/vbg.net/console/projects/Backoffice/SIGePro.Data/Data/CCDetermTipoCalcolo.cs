using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class CCDetermTipoCalcolo
    {
        #region Key fields

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        #endregion

		#region Foreign keys
		OCCBaseTipoIntervento m_baseTipoIntervento;
		[ForeignKey(/*typeof(OCCBaseTipoIntervento),*/ "FkOccbtiId", "Id")]
		public OCCBaseTipoIntervento BaseTipoIntervento
		{
			get { return m_baseTipoIntervento; }
			set { m_baseTipoIntervento = value; }
		}

		OCCBaseDestinazioni m_baseTipoDestinazione;
		[ForeignKey(/*typeof(OCCBaseDestinazioni),*/ "FkOccbdeId", "Id")]
		public OCCBaseDestinazioni BaseTipoDestinazione
		{
			get { return m_baseTipoDestinazione; }
			set { m_baseTipoDestinazione = value; }
		}


		CCBaseTipoCalcolo m_baseTipoCalcolo;
		[ForeignKey(/*typeof(CCBaseTipoCalcolo),*/ "FkCcbtcId", "Id")]
		public CCBaseTipoCalcolo BaseTipoCalcolo
		{
			get { return m_baseTipoCalcolo; }
			set { m_baseTipoCalcolo = value; }
		}

		#endregion
	}
}

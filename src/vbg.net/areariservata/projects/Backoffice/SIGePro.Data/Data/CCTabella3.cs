using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class CCTabella3
    {
        #region Membri privati

        private int? m_rapporto_su_snr_da = null;

        private int? m_rapporto_su_snr_a = null;

        #endregion

        #region Key fields
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        #endregion

        #region Data Fields

        [isRequired]
        [DataField("RAPPORTO_SU_SNR_DA", Type = DbType.Decimal)]
        public int? RapportoSuSnrDa
        {
            get { return m_rapporto_su_snr_da; }
            set { m_rapporto_su_snr_da = value; }
        }

        [isRequired]
        [DataField("RAPPORTO_SU_SNR_A", Type = DbType.Decimal)]
        public int? RapportoSuSnrA
        {
            get { return m_rapporto_su_snr_a; }
            set { m_rapporto_su_snr_a = value; }
        }

        private CCDettagliSuperficie m_DettagliSuperficie;

        [ForeignKey(/*typeof(CCDettagliSuperficie),*/ "Idcomune,FkCcDsId", "Idcomune,Id")]
	    public CCDettagliSuperficie DettagliSuperficie
	    {
		    get { return m_DettagliSuperficie;}
		    set { m_DettagliSuperficie = value;}
	    }

        #endregion

		#region foreign keys
		CCDettagliSuperficie m_dettaglioSuperficie;

		[ForeignKey(/*typeof(CCDettagliSuperficie),*/ "Idcomune,FkCcDsId", "Idcomune,Id")]
		public CCDettagliSuperficie DettaglioSuperficie
		{
			get { return m_dettaglioSuperficie; }
			set { m_dettaglioSuperficie = value; }
		}


		#endregion
	}
}

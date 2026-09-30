using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class CanoniConfigurazione
    {
        [DataField("PERCADDIZCOMUNALE", Type = DbType.Decimal)]
        public double? PercAddizComunale
        {
            get { return m_percaddizcomunale; }
            set { m_percaddizcomunale = value; }
        }

        [DataField("FK_COID_ADDIZCOMUNALE", Type = DbType.Decimal)]
        public int? FkCoIdAddizComunale
        {
            get { return m_fk_coid_addizcomunale; }
            set { m_fk_coid_addizcomunale = value; }
        }

        [DataField("VALOREBASE_OMI", Type = DbType.Decimal)]
        public double? ValoreBaseOMI
        {
            get { return m_valorebase_omi; }
            set { m_valorebase_omi = value; }
        }

        [DataField("COEFFICIENTE_OMI", Type = DbType.Decimal)]
        public double? CoefficienteOMI
        {
            get { return m_coefficiente_omi; }
            set { m_coefficiente_omi = value; }
        }

        [isRequired]
        [DataField("FK_COID_TOTALE", Type = DbType.Decimal)]
        public int? FkCoIdTotale
        {
            get { return m_fk_coid_totale; }
            set { m_fk_coid_totale = value; }
        }

        [isRequired]
        [DataField("TIPORIPARTIZIONE_OMI", Type = DbType.Decimal)]
        public int? TipoRipartizioneOMI
        {
            get { return m_tiporipartizione_omi; }
            set { m_tiporipartizione_omi = value; }
        }

        [DataField("FK_COID", Type = DbType.Decimal)]
        public int? FkCoId
        {
            get { return m_fk_coid; }
            set { m_fk_coid = value; }
        }

        [DataField("PERCADDIZREGIONALE", Type = DbType.Decimal)]
        public double? PercAddizRegionale
        {
            get { return m_percaddizregionale; }
            set { m_percaddizregionale = value; }
        }


        #region Foreign keys
        TipiCausaliOneri m_causaleaddizionaleregionale;
		[ForeignKey("Idcomune,FkCoId", "Idcomune,CoId")]
        public TipiCausaliOneri CausaleAddizionaleRegionale
        {
            get { return m_causaleaddizionaleregionale; }
            set { m_causaleaddizionaleregionale = value; }
        }

        TipiCausaliOneri m_causaleaddizionalecomunale;
		[ForeignKey("Idcomune,FkCoIdAddizComunale", "Idcomune,CoId")]
        public TipiCausaliOneri CausaleAddizionaleComunale
        {
            get { return m_causaleaddizionalecomunale; }
            set { m_causaleaddizionalecomunale = value; }
        }

        TipiCausaliOneri m_causaletotalecalcolo;
		[ForeignKey("Idcomune,FkCoIdTotale", "Idcomune,CoId")]
        public TipiCausaliOneri CausaleTotaleCalcolo
        {
            get { return m_causaletotalecalcolo; }
            set { m_causaletotalecalcolo = value; }
        }
        #endregion
    }
}

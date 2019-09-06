using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class Mercati_DConti
    {
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        #region Data fields
        [isRequired]
        [DataField("FK_MDID", Type = DbType.Decimal)]
        public int? FkMdId
        {
            get { return m_fk_mdid; }
            set { m_fk_mdid = value; }
        }

        [isRequired]
        [DataField("FK_COID", Type = DbType.Decimal)]
        public int? FkCoId
        {
            get { return m_fk_coid; }
            set { m_fk_coid = value; }
        }

        [isRequired]
        [DataField("ANNO", Type = DbType.Decimal)]
        public int? Anno
        {
            get { return m_anno; }
            set { m_anno = value; }
        }

        [isRequired]
        [DataField("VALORE", Type = DbType.Decimal)]
        public double? Valore
        {
            get { return m_valore; }
            set { m_valore = value; }
        }

        [isRequired]
        [DataField("FLAG_CANONE", Type = DbType.Decimal)]
        public int? FlagCanone
        {
            get { return m_flag_canone; }
            set { m_flag_canone = value; }
        }

        [isRequired]
        [DataField("FLAG_VALORE", Type = DbType.Decimal)]
        public int? FlagValore
        {
            get { return m_flag_valore; }
            set { m_flag_valore = value; }
        }

        [isRequired]
        [DataField("CONTESTO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        public string Contesto
        {
            get { return m_contesto; }
            set { m_contesto = value; }
        }

        #endregion

        private Mercati_D m_posteggio;
        [ForeignKey("Idcomune,FkMdId", "IDCOMUNE,IDPOSTEGGIO")]
        public Mercati_D Posteggio
        {
            get { return m_posteggio; }
            set { m_posteggio = value; }
        }

        private Conti m_conto;
        [ForeignKey("Idcomune,FkCoId", "Idcomune,Id")]
        public Conti Conto
        {
            get { return m_conto; }
            set { m_conto = value; }
        }
    }
}

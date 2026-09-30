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
            get { return this.m_id; }
            set { this.m_id = value; }
        }

        #region Data fields
        [isRequired]
        [DataField("FK_MDID", Type = DbType.Decimal)]
        public int? FkMdId
        {
            get { return this.m_fk_mdid; }
            set { this.m_fk_mdid = value; }
        }

        [isRequired]
        [DataField("FK_COID", Type = DbType.Decimal)]
        public int? FkCoId
        {
            get { return this.m_fk_coid; }
            set { this.m_fk_coid = value; }
        }

        [isRequired]
        [DataField("ANNO", Type = DbType.Decimal)]
        public int? Anno
        {
            get { return this.m_anno; }
            set { this.m_anno = value; }
        }

        [isRequired]
        [DataField("VALORE", Type = DbType.Decimal)]
        public double? Valore
        {
            get { return this.m_valore; }
            set { this.m_valore = value; }
        }

        [isRequired]
        [DataField("FLAG_CANONE", Type = DbType.Decimal)]
        public int? FlagCanone
        {
            get { return this.m_flag_canone; }
            set { this.m_flag_canone = value; }
        }

        [isRequired]
        [DataField("FLAG_VALORE", Type = DbType.Decimal)]
        public int? FlagValore
        {
            get { return this.m_flag_valore; }
            set { this.m_flag_valore = value; }
        }

        [isRequired]
        [DataField("CONTESTO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        public string Contesto
        {
            get { return this.m_contesto; }
            set { this.m_contesto = value; }
        }

        #endregion

        private Mercati_D m_posteggio;
        [ForeignKey("Idcomune,FkMdId", "IdComune,IdPosteggio")]
        public Mercati_D Posteggio
        {
            get { return this.m_posteggio; }
            set { this.m_posteggio = value; }
        }

        private Conti m_conto;
        [ForeignKey("Idcomune,FkCoId", "Idcomune,Id")]
        public Conti Conto
        {
            get { return this.m_conto; }
            set { this.m_conto = value; }
        }
    }
}

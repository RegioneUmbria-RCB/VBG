using PersonalLib2.Sql.Attributes;
using System.Collections.Generic;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    //[DataTable("INVENTARIOPROCEDIMENTI")]
    //[Serializable]
    public partial class InventarioProcedimenti //: BaseDataClass
    {
        #region foreign keys
        private TipiMovimento m_movimento;
        [ForeignKey("Idcomune, Tipomovimento", "Idcomune,Tipomovimento")]
        [XmlElement(Order = 25)]
        public TipiMovimento Movimento
        {
            get { return this.m_movimento; }
            set { this.m_movimento = value; }
        }

        private List<Allegati> m_allegati = new List<Allegati>();
        [ForeignKey("Idcomune, Codiceinventario", "Idcomune,Codiceinventario")]
        [XmlElement(Order = 26)]
        public List<Allegati> Allegati
        {
            get { return this.m_allegati; }
            set { this.m_allegati = value; }
        }

        private NaturaEndo m_natura = null;
        [ForeignKey("Idcomune, Codicenatura", "IDCOMUNE,CODICENATURA")]
        [XmlElement(Order = 27)]
        public NaturaEndo Natura
        {
            get { return this.m_natura; }
            set { this.m_natura = value; }
        }

        #endregion

        public override string ToString()
        {
            return this.Procedimento;
        }
    }
}
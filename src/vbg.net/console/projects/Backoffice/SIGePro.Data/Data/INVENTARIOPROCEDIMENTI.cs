using System;
using System.Data;
using Init.SIGePro.Attributes;
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
		TipiMovimento m_movimento;
		[ForeignKey("Idcomune, Tipomovimento", "Idcomune,Tipomovimento")]
		[XmlElement(Order = 25)]
		public TipiMovimento Movimento
		{
			get { return m_movimento; }
			set { m_movimento = value; }
		}

		private List<Allegati> m_allegati = new List<Allegati>();
		[ForeignKey("Idcomune, Codiceinventario", "Idcomune,Codiceinventario")]
		[XmlElement(Order = 26)]
		public List<Allegati> Allegati
		{
			get { return m_allegati; }
			set { m_allegati = value; }
		}

        private NaturaEndo m_natura = null;
        [ForeignKey("Idcomune, Codicenatura", "IDCOMUNE,CODICENATURA")]
		[XmlElement(Order = 27)]
        public NaturaEndo Natura
        {
            get { return m_natura; }
            set { m_natura = value; }
        }

		#endregion

		public override string ToString()
		{
			return Procedimento;
		}
	}
}
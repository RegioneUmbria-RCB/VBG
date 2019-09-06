
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    public partial class FoDomande
    {
		private Oggetti m_oggetto;

		// Non è impostato il flag useSequence perchè l'id viene impostato direttamente dall'area riservata
		[KeyField("ID", Type = DbType.Decimal)]
		[XmlElement(Order = 0)]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}


		[ForeignKey("Idcomune,Codiceoggetto", "IDCOMUNE,CODICEOGGETTO")]
		[XmlElement(Order = 1)]
		public Oggetti Oggetto
		{
			get { return m_oggetto; }
			set { m_oggetto = value; }
		}


		private Anagrafe m_richiedente;
		[ForeignKey("Idcomune,Codiceanagrafe", "IDCOMUNE,CODICEANAGRAFE")]
		[XmlElement(Order = 2)]
		public Anagrafe Richiedente
		{
			get { return m_richiedente; }
			set { m_richiedente = value; }
		}

        [DataField("CODICEISTANZA_ORIGINE", Type = DbType.Decimal)]
        [XmlElement(Order = 14)]
        public int? CodiceIstanzaOrigine { get; set; }
    }
}
				
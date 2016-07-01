using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	/// <summary>
	/// Descrizione di riepilogo per ProtocolloAnagrafe.
	/// </summary>
	public class ProtocolloAnagrafe : Anagrafe
	{
		public ProtocolloAnagrafe()
		{

		}

        public void SetPec(Istanze istanza)
        { 
            if(String.IsNullOrEmpty(istanza.DOMICILIO_ELETTRONICO))
                return;

            if (this.CODICEANAGRAFE == istanza.CODICERICHIEDENTE || this.CODICEANAGRAFE == istanza.CODICETITOLARELEGALE)
                this.Pec = istanza.DOMICILIO_ELETTRONICO;
        }

        [XmlElement(Order=65)]
        public string CodiceIstatComRes { get; set; }

        [XmlElement(Order=66)]
        public string CodiceIstatComNasc{ get; set; }

        [XmlElement(Order=67)]
        public string CodiceStatoEsteroRes{ get; set; }

        [XmlElement(Order=68)]
        public string CodiceStatoEsteroNasc{ get; set; }

        [XmlElement(Order=69)]
        public string Mezzo { get; set; }

        [XmlElement(Order=70)]
        public string ModalitaTrasmissione { get; set; }


    }
}

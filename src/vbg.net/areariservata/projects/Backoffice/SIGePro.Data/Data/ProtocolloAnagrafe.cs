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
        public enum TipoGestionePecEnum { DEFAULT = 0, DA_DATI_ANAGRAFICI = 1, DA_DATI_ANAGRAFICI_DOMICILIO = 2, DA_DATI_ANAGRAFICI_DOMICILIO_TECNICO = 3, DA_DATI_ANAGRAFICI_AZIENDA_TECNICO = 4 };

		public ProtocolloAnagrafe()
		{

		}

        public void SetPec(Istanze istanza, TipoGestionePecEnum tipoGestionePec)
        {
            if (tipoGestionePec == TipoGestionePecEnum.DA_DATI_ANAGRAFICI)
            {
                return;
            }
            else if (tipoGestionePec == TipoGestionePecEnum.DA_DATI_ANAGRAFICI_DOMICILIO)
            {
                if (String.IsNullOrEmpty(this.Pec))
                {
                    if (this.CODICEANAGRAFE == istanza.CODICERICHIEDENTE || this.CODICEANAGRAFE == istanza.CODICETITOLARELEGALE)
                    {
                        this.Pec = istanza.DOMICILIO_ELETTRONICO;
                    }
                }
            }
            else if (tipoGestionePec == TipoGestionePecEnum.DA_DATI_ANAGRAFICI_DOMICILIO_TECNICO)
            {
                if (String.IsNullOrEmpty(this.Pec))
                {
                    if (this.CODICEANAGRAFE == istanza.CODICETITOLARELEGALE)
                    {
                        this.Pec = istanza.DOMICILIO_ELETTRONICO;
                    }
                }
            }
            else if (tipoGestionePec == TipoGestionePecEnum.DA_DATI_ANAGRAFICI_AZIENDA_TECNICO)
            {
                if ((this.CODICEANAGRAFE == istanza.CODICEPROFESSIONISTA) || (this.CODICEANAGRAFE == istanza.CODICERICHIEDENTE && String.IsNullOrEmpty(istanza.CODICEPROFESSIONISTA)))
                {
                    this.Pec = istanza.DOMICILIO_ELETTRONICO;
                }

                if (this.CODICEANAGRAFE == istanza.CODICETITOLARELEGALE && String.IsNullOrEmpty(istanza.CODICEPROFESSIONISTA))
                {
                    if(!String.IsNullOrEmpty(istanza.DOMICILIO_ELETTRONICO))
                    {
                        this.Pec = istanza.DOMICILIO_ELETTRONICO;
                    }
                }
            }
            else
            {
                if (String.IsNullOrEmpty(istanza.DOMICILIO_ELETTRONICO))
                    return;

                if (this.CODICEANAGRAFE == istanza.CODICERICHIEDENTE || this.CODICEANAGRAFE == istanza.CODICETITOLARELEGALE)
                    this.Pec = istanza.DOMICILIO_ELETTRONICO;
            }
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

        [XmlElement(Order = 71)]
        public string PecAnagrafica { get; set; }

    }
}

using System;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Maggioli
{
    [XmlRoot(Namespace = "", ElementName = "LeggiAnagraficaSikuelRisposta", IsNullable = false)]
    public class LeggiAnagraficaSikuelRisposta
    {
        [XmlElement("Anagrafica")]
        public AnagraficaType[] Anagrafica { get; set; }

        [XmlElement("esito")]
        public EsitoType Esito { get; set; }

        public class AnagraficaType
        {
            public string idAnagrafe { get; set; }
            public string CodiceFiscale { get; set; }
            public string RagioneSociale { get; set; }
            public string Cognome { get; set; }
            public string Nome { get; set; }
            public bool PersonaGiuridica { get; set; }
            public string Sesso { get; set; }
            public string DescrizioneComuneDiNascita { get; set; }
            public string CapComuneDiNascita { get; set; }
            public string PartitaIva { get; set; }

            [XmlElement("pec")]
            public string Pec { get; set; }

            public string Email { get; set; }

            [XmlElement("DataDiNascita")]
            public string _dataDiNascita { get; set; }

            [XmlIgnore()]
            public DateTime? DataDiNascita
            {
                get
                {
                    if (!String.IsNullOrEmpty(this._dataDiNascita))
                    {
                        return DateTime.Parse(this._dataDiNascita);
                    }

                    return null;
                }
            }

            public string Nazionalita { get; set; }
            public string DescrizioneNazionalita { get; set; }
            public string DataDiIscrizioneAnagrafica { get; set; }
            public string DescrizioneComuneDiResidenza { get; set; }
            public string codCatastoComuneDiResidenza { get; set; }
            public string DescrizioneProvinciaDiResidenza { get; set; }
            public string SiglaProvinciaDiResidenza { get; set; }
            public string DescrizioneStatoDiResidenza { get; set; }
            public string SiglaStatoDiResidenza { get; set; }
            public string CapComuneDiResidenza { get; set; }
            public string Toponimo { get; set; }
            public string Via { get; set; }
            public string Civico { get; set; }
            public string Barrato { get; set; }
            public string DataUltimoCambioIndirizzo { get; set; }
            public string FlagResidenza { get; set; }

            [XmlElement("PrecedentiIndirizzi")]
            public PrecedentiIndirizziType PrecedentiIndirizzi { get; set; }
        }

        public class PrecedentiIndirizziType
        {
            [XmlElement("Indirizzo")]
            public IndirizzoType[] Indirizzo { get; set; }
        }

        public class IndirizzoType
        {
            public string DataDiInizioResidenza { get; set; }
            public string DescrizioneComuneDiResidenza { get; set; }
            public string codCatastoComuneDiResidenza { get; set; }
            public string DescrizioneProvinciaDiResidenza { get; set; }
            public string SiglaProvinciaDiResidenza { get; set; }
            public string DescrizioneStatoDiResidenza { get; set; }
            public string SiglaStatoDiResidenza { get; set; }
            public string CapComuneDiResidenza { get; set; }
            public string Toponimo { get; set; }
            public string Via { get; set; }
            public string Civico { get; set; }
            public string Barrato { get; set; }
        }

        public class EsitoType
        {
            public string cod { get; set; }
            public string messaggio { get; set; }
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Visura
{
    public class VisuraListItemDto
    {
        [XmlElement(Order = 0)]
        public string Codicecomune { get; set; }

        [XmlElement(Order = 1)]
        public string Idcomune { get; set; }

        [XmlElement(Order = 2)]
        public string Software { get; set; }

        [XmlElement(Order = 3)]
        public string Descsoftware { get; set; }

        [XmlElement(Order = 4)]
        public int Idpratica { get; set; }

        [XmlElement(Order = 5)]
        public string Numeropratica { get; set; }

        [XmlElement(Order = 6)]
        public DateTime Datapresentazione { get; set; }

        [XmlElement(Order = 7)]
        public int Annopresentazione { get; set; }

        [XmlElement(Order = 8)]
        public int Mesepresentazione { get; set; }

        [XmlElement(Order = 9)]
        public string Numeroprotocollo { get; set; }

        [XmlElement(Order = 10)]
        public DateTime? Dataprotocollo { get; set; }

        [XmlElement(Order = 11)]
        public int Codiceintervento { get; set; }

        [XmlElement(Order = 12)]
        public string Descrizioneintervento { get; set; }

        [XmlElement(Order = 13)]
        public string Codiceprocedura { get; set; }

        [XmlElement(Order = 14)]
        public string Procedura { get; set; }

        [XmlElement(Order = 15)]
        public string Oggetto { get; set; }

        [XmlElement(Order = 16)]
        public string Oggettou { get; set; }

        [XmlElement(Order = 17)]
        public string Codstatopratica { get; set; }

        [XmlElement(Order = 18)]
        public string Statopratica { get; set; }

        [XmlElement(Order = 19)]
        public string Responsabile { get; set; }

        [XmlElement(Order = 20)]
        public string Responsabile_telefono { get; set; }

        //[XmlElement(Order = 21)]
        //public string Pr_codviario { get; set; }

        [XmlElement(Order = 22)]
        public string Pr_indirizzo { get; set; }

        [XmlElement(Order = 23)]
        public string Pr_codcivico { get; set; }

        [XmlElement(Order = 24)]
        public string Pr_civico { get; set; }

        [XmlElement(Order = 25)]
        public string Codicezonizzazione { get; set; }

        [XmlElement(Order = 26)]
        public string Zonizzazione { get; set; }

        [XmlElement(Order = 27)]
        public int Codicerichiedente { get; set; }

        [XmlElement(Order = 28)]
        public string Codicefiscale { get; set; }

        [XmlElement(Order = 29)]
        public string Partitaiva { get; set; }

        [XmlElement(Order = 30)]
        public string Nominativo { get; set; }

        [XmlElement(Order = 31)]
        public string Indirizzo { get; set; }

        [XmlElement(Order = 32)]
        public string Cap { get; set; }

        [XmlElement(Order = 33)]
        public string Localita { get; set; }

        [XmlElement(Order = 34)]
        public string Citta { get; set; }

        [XmlElement(Order = 35)]
        public string Provincia { get; set; }

        [XmlElement(Order = 36)]
        public string Tiporapporto { get; set; }

        [XmlElement(Order = 37)]
        public string Tipocatasto { get; set; }

        [XmlElement(Order = 38)]
        public string Foglio { get; set; }

        [XmlElement(Order = 39)]
        public string Particella { get; set; }

        [XmlElement(Order = 40)]
        public string Sub { get; set; }

        [XmlElement(Order = 41)]
        public int? Tec_codice { get; set; }

        [XmlElement(Order = 42)]
        public string Tec_nominativo { get; set; }

        [XmlElement(Order = 43)]
        public string Tec_codicefiscale { get; set; }

        [XmlElement(Order = 44)]
        public string Tec_partitaiva { get; set; }

        [XmlElement(Order = 45)]
        public int? Az_codice { get; set; }

        [XmlElement(Order = 46)]
        public string Az_nominativo { get; set; }

        [XmlElement(Order = 47)]
        public string Az_codicefiscale { get; set; }

        [XmlElement(Order = 48)]
        public string Az_partitaiva { get; set; }

        [XmlElement(Order = 49)]
        public string Istruttore { get; set; }

        [XmlElement(Order = 50)]
        public string Istruttore_telefono { get; set; }

        [XmlElement(Order = 51)]
        public string Operatore { get; set; }

        [XmlElement(Order = 52)]
        public string Operatore_telefono { get; set; }

        [XmlElement(Order = 53)]
        public string Domicilio_elettronico { get; set; }

        [XmlElement(Order = 54)]
        public string Id_sportellomitt { get; set; }

        [XmlElement(Order = 55)]
        public string Id_domandamitt { get; set; }

        [XmlElement(Order = 56)]
        public string Uuid { get; set; }
    }
}
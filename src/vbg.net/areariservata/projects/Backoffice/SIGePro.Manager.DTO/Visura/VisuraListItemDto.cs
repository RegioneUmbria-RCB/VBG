using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Visura
{
    [DataTable("VW_LISTAPRATICHE")]
    public class VisuraListItemDto : DataClass
    {
        [DataField("codicecomune", System.Data.DbType.String)]
        [XmlElement(Order=0)]
        public string Codicecomune { get; set; }

        [DataField("idcomune", System.Data.DbType.String)]
        [XmlElement(Order = 1)]
        public string Idcomune { get; set; }

        [DataField("software", System.Data.DbType.String)]
        [XmlElement(Order = 2)]
        public string Software { get; set; }

        [DataField("descsoftware", System.Data.DbType.String)]
        [XmlElement(Order = 3)]
        public string Descsoftware { get; set; }

        [DataField("Idpratica", System.Data.DbType.Decimal)]
        [XmlElement(Order = 4)]
        public int Idpratica { get; set; }

        [DataField("numeropratica", System.Data.DbType.String)]
        [XmlElement(Order = 5)]
        public string Numeropratica { get; set; }

        [DataField("Datapresentazione", System.Data.DbType.DateTime)]
        [XmlElement(Order = 6)]
        public DateTime Datapresentazione { get; set; }

        [DataField("Annopresentazione", System.Data.DbType.Decimal)]
        [XmlElement(Order = 7)]
        public int Annopresentazione { get; set; }

        [DataField("Mesepresentazione", System.Data.DbType.Decimal)]
        [XmlElement(Order = 8)]
        public int Mesepresentazione { get; set; }

        [DataField("Numeroprotocollo", System.Data.DbType.String)]
        [XmlElement(Order = 9)]
        public string Numeroprotocollo { get; set; }

        [DataField("Dataprotocollo", System.Data.DbType.DateTime)]
        [XmlElement(Order = 10)]
        public DateTime? Dataprotocollo { get; set; }

        [DataField("Codiceintervento", System.Data.DbType.Decimal)]
        [XmlElement(Order = 11)]
        public int Codiceintervento { get; set; }

        [DataField("Descrizioneintervento", System.Data.DbType.String)]
        [XmlElement(Order = 12)]
        public string Descrizioneintervento { get; set; }

        [DataField("Codiceprocedura", System.Data.DbType.String)]
        [XmlElement(Order = 13)]
        public string Codiceprocedura { get; set; }

        [DataField("Procedura", System.Data.DbType.String)]
        [XmlElement(Order = 14)]
        public string Procedura { get; set; }

        [DataField("Oggetto", System.Data.DbType.String)]
        [XmlElement(Order = 15)]
        public string Oggetto { get; set; }

        [DataField("Oggettou", System.Data.DbType.String)]
        [XmlElement(Order = 16)]
        public string Oggettou { get; set; }

        [DataField("Codstatopratica", System.Data.DbType.String)]
        [XmlElement(Order = 17)]
        public string Codstatopratica { get; set; }

        [DataField("Statopratica", System.Data.DbType.String)]
        [XmlElement(Order = 18)]
        public string Statopratica { get; set; }

        [DataField("Responsabile", System.Data.DbType.String)]
        [XmlElement(Order = 19)]
        public string Responsabile { get; set; }

        [DataField("Responsabile_telefono", System.Data.DbType.String)]
        [XmlElement(Order = 20)]
        public string Responsabile_telefono { get; set; }

        //[DataField("Pr_codviario", System.Data.DbType.String)]
        //[XmlElement(Order = 21)]
        //public string Pr_codviario { get; set; }

        [DataField("Pr_indirizzo", System.Data.DbType.String)]
        [XmlElement(Order = 22)]
        public string Pr_indirizzo { get; set; }

        [DataField("Pr_codcivico", System.Data.DbType.String)]
        [XmlElement(Order = 23)]
        public string Pr_codcivico { get; set; }

        [DataField("Pr_civico", System.Data.DbType.String)]
        [XmlElement(Order = 24)]
        public string Pr_civico { get; set; }

        [DataField("Codicezonizzazione", System.Data.DbType.String)]
        [XmlElement(Order = 25)]
        public string Codicezonizzazione { get; set; }

        [DataField("Zonizzazione", System.Data.DbType.String)]
        [XmlElement(Order = 26)]
        public string Zonizzazione { get; set; }

        [DataField("Codicerichiedente", System.Data.DbType.Decimal)]
        [XmlElement(Order = 27)]
        public int Codicerichiedente { get; set; }

        [DataField("Codicefiscale", System.Data.DbType.String)]
        [XmlElement(Order = 28)]
        public string Codicefiscale { get; set; }

        [DataField("Partitaiva", System.Data.DbType.String)]
        [XmlElement(Order = 29)]
        public string Partitaiva { get; set; }

        [DataField("Nominativo", System.Data.DbType.String)]
        [XmlElement(Order = 30)]
        public string Nominativo { get; set; }

        [DataField("Indirizzo", System.Data.DbType.String)]
        [XmlElement(Order = 31)]
        public string Indirizzo { get; set; }

        [DataField("Cap", System.Data.DbType.String)]
        [XmlElement(Order = 32)]
        public string Cap { get; set; }

        [DataField("Localita", System.Data.DbType.String)]
        [XmlElement(Order = 33)]
        public string Localita { get; set; }

        [DataField("Citta", System.Data.DbType.String)]
        [XmlElement(Order = 34)]
        public string Citta { get; set; }

        [DataField("Provincia", System.Data.DbType.String)]
        [XmlElement(Order = 35)]
        public string Provincia { get; set; }

        [DataField("Tiporapporto", System.Data.DbType.String)]
        [XmlElement(Order = 36)]
        public string Tiporapporto { get; set; }

        [DataField("Tipocatasto", System.Data.DbType.String)]
        [XmlElement(Order = 37)]
        public string Tipocatasto { get; set; }

        [DataField("Foglio", System.Data.DbType.String)]
        [XmlElement(Order = 38)]
        public string Foglio { get; set; }

        [DataField("Particella", System.Data.DbType.String)]
        [XmlElement(Order = 39)]
        public string Particella { get; set; }

        [DataField("Sub", System.Data.DbType.String)]
        [XmlElement(Order = 40)]
        public string Sub { get; set; }

        [DataField("Tec_codice", System.Data.DbType.Decimal)]
        [XmlElement(Order = 41)]
        public int? Tec_codice { get; set; }

        [DataField("Tec_nominativo", System.Data.DbType.String)]
        [XmlElement(Order = 42)]
        public string Tec_nominativo { get; set; }

        [DataField("Tec_codicefiscale", System.Data.DbType.String)]
        [XmlElement(Order = 43)]
        public string Tec_codicefiscale { get; set; }

        [DataField("Tec_partitaiva", System.Data.DbType.String)]
        [XmlElement(Order = 44)]
        public string Tec_partitaiva { get; set; }

        [DataField("Az_codice", System.Data.DbType.Decimal)]
        [XmlElement(Order = 45)]
        public int? Az_codice { get; set; }

        [DataField("Az_nominativo", System.Data.DbType.String)]
        [XmlElement(Order = 46)]
        public string Az_nominativo { get; set; }

        [DataField("Az_codicefiscale", System.Data.DbType.String)]
        [XmlElement(Order = 47)]
        public string Az_codicefiscale { get; set; }

        [DataField("Az_partitaiva", System.Data.DbType.String)]
        [XmlElement(Order = 48)]
        public string Az_partitaiva { get; set; }

        [DataField("Istruttore", System.Data.DbType.String)]
        [XmlElement(Order = 49)]
        public string Istruttore { get; set; }

        [DataField("Istruttore_telefono", System.Data.DbType.String)]
        [XmlElement(Order = 50)]
        public string Istruttore_telefono { get; set; }

        [DataField("Operatore", System.Data.DbType.String)]
        [XmlElement(Order = 51)]
        public string Operatore { get; set; }

        [DataField("Operatore_telefono", System.Data.DbType.String)]
        [XmlElement(Order = 52)]
        public string Operatore_telefono { get; set; }

        [DataField("Domicilio_elettronico", System.Data.DbType.String)]
        [XmlElement(Order = 53)]
        public string Domicilio_elettronico { get; set; }

        [DataField("Id_sportellomitt", System.Data.DbType.String)]
        [XmlElement(Order = 54)]
        public string Id_sportellomitt { get; set; }

        [DataField("Id_domandamitt", System.Data.DbType.String)]
        [XmlElement(Order = 55)]
        public string Id_domandamitt { get; set; }

        [DataField("Uuid", System.Data.DbType.String)]
        [XmlElement(Order = 56)]
        public string Uuid { get; set; }
    }
}

using Init.SIGePro.Attributes;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("FO_DOMANDE")]
    [Serializable]
    public partial class FoDomande : DataClass
    {
        // Non è impostato il flag useSequence perchè l'id viene impostato direttamente dall'area riservata
        [KeyField("ID", Type = DbType.Decimal)]
        [XmlElement(Order = 0)]
        public int? Id { get; set; }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [XmlElement(Order = 3)]
        public string Idcomune { get; set; }

        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        [XmlElement(Order = 4)]
        public string Software { get; set; }

        [isRequired]
        [DataField("CODICEANAGRAFE", Type = DbType.Decimal)]
        [XmlElement(Order = 5)]
        public int? Codiceanagrafe { get; set; }

        [isRequired]
        [DataField("FLG_PRESENTATA", Type = DbType.Decimal)]
        [XmlElement(Order = 6)]
        public int? FlgPresentata { get; set; }

        [isRequired]
        [DataField("FLG_TRASFERITA", Type = DbType.Decimal)]
        [XmlElement(Order = 7)]
        public int? FlgTrasferita { get; set; }

        [isRequired]
        [DataField("FLG_ELIMINATA", Type = DbType.Decimal)]
        [XmlElement(Order = 8)]
        public int? FlgEliminata { get; set; }

        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        [XmlElement(Order = 9)]
        public int? Codiceoggetto { get; set; }

        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [XmlElement(Order = 10)]
        public int? Codiceistanza { get; set; }

        [DataField("DATAINVIO", Type = DbType.DateTime)]
        [XmlElement(Order = 11)]
        public DateTime? Datainvio { get; set; }

        [DataField("DATA_ULTIMA_MODIFICA", Type = DbType.DateTime)]
        [XmlElement(Order = 12)]
        public DateTime? DataUltimaModifica { get; set; }

        [DataField("IDENTIFICATIVODOMANDA", Type = DbType.String, CaseSensitive = false, Size = 50)]
        [XmlElement(Order = 13)]
        public string Identificativodomanda { get; set; }

        [DataField("CODICEISTANZA_ORIGINE", Type = DbType.Decimal)]
        [XmlElement(Order = 14)]
        public int? CodiceIstanzaOrigine { get; set; }

        [DataField("RICHIEDENTE", Type = DbType.String, CaseSensitive = false, Size = 128)]
        [XmlElement(Order = 15)]
        public string Richiedente { get; set; }

        [DataField("INTERVENTO", Type = DbType.String, CaseSensitive = false, Size = 320)]
        [XmlElement(Order = 16)]
        public string Intervento { get; set; }

        [DataField("OGGETTO", Type = DbType.String, CaseSensitive = false, Size = 320)]
        [XmlElement(Order = 17)]
        public string Oggetto { get; set; }

        [DataField("BOOKMARK", Type = DbType.String, CaseSensitive = false, Size = 128)]
        [XmlElement(Order = 18)]
        public string Bookmark { get; set; }

        [DataField("PAGAMENTO_AVVIATO", Type = DbType.Decimal)]
        [XmlElement(Order = 19)]
        public int? PagamentoAvviato { get; set; }

        [DataField("PAGAMENTO_COMPLETATO", Type = DbType.Decimal)]
        [XmlElement(Order = 20)]
        public int? PagamentoCompletato { get; set; }

        [DataField("UPGRADE_COMPLETO", Type = DbType.Decimal)]
        [XmlElement(Order = 21)]
        public int? UpgradeCompleto { get; set; }

        [DataField("CODICEINTERVENTO", Type = DbType.Decimal)]
        [XmlElement(Order = 22)]
        public int? CodiceIntervento { get; set; }

        [DataField("PROVENIENZA", Type = DbType.String, CaseSensitive = false, Size = 64)]
        [XmlElement(Order = 24)]
        public string Provenienza { get; set; } = "";
    }
}

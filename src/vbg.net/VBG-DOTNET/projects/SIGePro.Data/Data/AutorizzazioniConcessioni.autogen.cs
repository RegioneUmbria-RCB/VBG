using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
    [DataTable("AUTORIZZAZIONI_CONCESSIONI")]
    [Serializable]
    [DataContract]
    public partial class AutorizzazioniConcessioni : BaseDataClass, IClasseContestoModelloDinamico
    {
        #region Membri privati
        private DateTime? _datascadenza = null;
        #endregion

        #region properties

        #region Key Fields
        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [XmlElement(Order = 10)]
        [DataMember]
        public int? Id { get; set; }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [XmlElement(Order = 20)]
        [DataMember]
        public string Idcomune { get; set; }

        [isRequired]
        [DataField("FK_CODICEMERCATO", Type = DbType.Decimal)]
        [XmlElement(Order = 30)]
        [DataMember]
        public int? FkCodiceMercato { get; set; }

        [isRequired]
        [DataField("FK_IDMERCATIUSO", Type = DbType.Decimal)]
        [XmlElement(Order = 40)]
        [DataMember]
        public int? FkIdMercatiUso { get; set; }

        [isRequired]
        [DataField("FK_IDPOSTEGGIO", Type = DbType.Decimal)]
        [XmlElement(Order = 50)]
        [DataMember]
        public int? FkIdPosteggio { get; set; }

        [isRequired]
        [DataField("FK_TIPOCONCESSIONE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        [XmlElement(Order = 60)]
        [DataMember]
        public string FkTipoConcessione { get; set; }

        [DataField("STAGIONALEDA", Type = DbType.String, CaseSensitive = false, Size = 4)]
        [XmlElement(Order = 70)]
        [DataMember]
        public string StagionaleDa { get; set; }

        [DataField("STAGIONALEA", Type = DbType.String, CaseSensitive = false, Size = 4)]
        [XmlElement(Order = 80)]
        [DataMember]
        public string StagionaleA { get; set; }

        [DataField("DATASCADENZA", Type = DbType.DateTime)]
        [XmlElement(Order = 90)]
        [DataMember]
        public DateTime? DataScadenza
        {
            get { return this._datascadenza; }
            set { this._datascadenza = this.VerificaDataLocale(value); }
        }

        [isRequired]
        [DataField("FK_IDAUT_ATTUALE", Type = DbType.Decimal)]
        [XmlElement(Order = 100)]
        [DataMember]
        public int? FkIdAutAttuale { get; set; }

        [DataField("FK_IDAUT_COLLEGATA", Type = DbType.Decimal)]
        [XmlElement(Order = 110)]
        [DataMember]
        public int? FkIdAutCollegata { get; set; }

        [ForeignKey("Idcomune,FkIdPosteggio", "IdComune,IdPosteggio")]
        [XmlElement(Order = 14)]
        [DataMember]
        public Mercati_D Posteggio { get; set; }

        #endregion
        #endregion
    }
}

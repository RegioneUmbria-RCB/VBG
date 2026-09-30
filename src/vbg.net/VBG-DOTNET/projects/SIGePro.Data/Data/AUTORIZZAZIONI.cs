using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("AUTORIZZAZIONI")]
    [Serializable]
    [DataContract]
    public class Autorizzazioni : BaseDataClass
    {
        public Autorizzazioni()
        {
            this.Concessioni = new List<AutorizzazioniConcessioni>();
        }

        #region Key Fields

        /// <summary>
        /// Id univoco dell'autorizzazione insieme a IDCOMUNE
        /// </summary>
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string ID { get; set; }

        /// <summary>
        /// Id univoco dell'autorizzazione insieme a ID
        /// </summary>
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string IDCOMUNE { get; set; }

        #endregion

        /// <summary>
        /// Id registro, FK su TIPIREGISTRI.TR_ID insieme a IDCOMUNE
        /// </summary>
        [isRequired]
        [DataField("FKIDREGISTRO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string FKIDREGISTRO { get; set; }

        /// <summary>
        /// Istanza a cui fa riferimento l'autorizzazione (fk su ISTANZE.CODICEISTANZA insieme a IDCOMUNE)
        /// </summary>
        [DataField("FKIDISTANZA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string FKIDISTANZA { get; set; }

        /// <summary>
        /// numero autorizzazione
        /// </summary>
        [isRequired]
        [DataField("AUTORIZNUMERO", Size = 25, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 5)]
        public string AUTORIZNUMERO { get; set; }

        /// <summary>
        /// Data autorizzazione
        /// </summary>
        private DateTime? _autorizdata = null;
        [DataField("AUTORIZDATA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 6)]
        public DateTime? AUTORIZDATA
        {
            get { return this._autorizdata; }
            set { this._autorizdata = this.VerificaDataLocale(value); }
        }

        /// <summary>
        /// Data registrazione autorizzazione
        /// </summary>
        private DateTime? _autorizdataregistr;
        [DataField("AUTORIZDATAREGISTR", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 7)]
        public DateTime? AUTORIZDATAREGISTR
        {
            get { return this._autorizdataregistr; }
            set { this._autorizdataregistr = this.VerificaDataLocale(value); }
        }

        [DataField("AUTORIZRESPONSABILE", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string AUTORIZRESPONSABILE { get; set; }

        [DataField("CODICEMOVIMENTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string CODICEMOVIMENTO { get; set; }

        [isRequired]
        [DataField("FLAG_ATTIVA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 10)]
        public int? FlagAttiva { get; set; }

        private DateTime? _dataStorico = null;
        [DataField("DATA_STORICO", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 11)]
        public DateTime? DataStorico
        {
            get { return this._dataStorico; }
            set { this._dataStorico = this.VerificaDataLocale(value); }
        }
        [DataField("FK_CODICEANAGRAFE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 12)]
        public int? FKCodiceAnagrafe { get; set; }

        [DataField("AUTORIZCOMUNE", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 13)]
        public string AUTORIZCOMUNE { get; set; }

        /// <summary>
        /// Risoluzione FK: Tipo registro
        /// </summary>
        [ForeignKey("IDCOMUNE,FKIDREGISTRO", "IDCOMUNE,TR_ID")]
        [DataMember]
        [XmlElement(Order = 14)]
        public TipologiaRegistri Registro { get; set; }

        /// <summary>
        /// Risoluzione fk: Anagrafe
        /// </summary>
        [ForeignKey("IDCOMUNE,FKCodiceAnagrafe", "IDCOMUNE,CODICEANAGRAFE")]
        // [DataMember]
        [XmlElement(Order = 15)]
        public Anagrafe Anagrafe { get; set; }

        [DataField("DATA_CESSAZIONE", Size = 5, Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 16)]
        public DateTime? DataCessazione { get; set; }

        [DataField("DATASCADENZA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 17)]
        public DateTime? DataScadenza { get; set; }

        [DataField("FK_CAUSALE_CESSAZIONE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 18)]
        public int? FkCausaleCessazione { get; set; }

        [DataField("FKIDPROTOCOLLO", Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 19)]
        public string FkIdProtocollo { get; set; }

        [ForeignKey("IDCOMUNE, ID", "Idcomune, FkIdAutAttuale")]
        // [DataMember]
        [XmlElement(Order = 20)]
        public List<AutorizzazioniConcessioni> Concessioni { get; set; }
    }
}
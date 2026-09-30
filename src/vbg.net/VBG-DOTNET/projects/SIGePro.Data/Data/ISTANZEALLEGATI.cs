using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZEALLEGATI")]
    [Serializable]
    [DataContract]
    public class IstanzeAllegati : BaseDataClass
    {

        #region Key Fields

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        private string id = null;
        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        [XmlElement(Order = 2)]
        public string Id
        {
            get { return this.id; }
            set { this.id = value; }
        }

        #endregion

        private string codiceinventario = null;
        [DataField("CODICEINVENTARIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string CODICEINVENTARIO
        {
            get { return this.codiceinventario; }
            set { this.codiceinventario = value; }
        }

        private string numeroallegato = null;
        [DataField("NUMEROALLEGATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string NUMEROALLEGATO
        {
            get { return this.numeroallegato; }
            set { this.numeroallegato = value; }
        }

        private string codiceistanza = null;
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 5)]
        public string CODICEISTANZA
        {
            get { return this.codiceistanza; }
            set { this.codiceistanza = value; }
        }

        private string verificato = null;
        [DataField("VERIFICATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string VERIFICATO
        {
            get { return this.verificato; }
            set { this.verificato = value; }
        }

        private string controllook = null;
        [DataField("CONTROLLOOK", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 7)]
        public string CONTROLLOOK
        {
            get { return this.controllook; }
            set { this.controllook = value; }
        }

        private string note = null;
        [DataField("NOTE", Size = 500, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string NOTE
        {
            get { return this.note; }
            set { this.note = value; }
        }

        private double? prezzo = null;
        [DataField("PREZZO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 9)]
        public double? PREZZO
        {
            get { return this.prezzo; }
            set { this.prezzo = value; }
        }

        private string presente = null;
        [DataField("PRESENTE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 10)]
        public string PRESENTE
        {
            get { return this.presente; }
            set { this.presente = value; }
        }

        private string seendo = null;
        [DataField("SEENDO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 11)]
        public string SEENDO
        {
            get { return this.seendo; }
            set { this.seendo = value; }
        }

        private string selezionato = null;
        [DataField("SELEZIONATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 12)]
        public string SELEZIONATO
        {
            get { return this.selezionato; }
            set { this.selezionato = value; }
        }

        private string allegatoextra = null;
        [DataField("ALLEGATOEXTRA", Size = 512, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 13)]
        public string ALLEGATOEXTRA
        {
            get { return this.allegatoextra; }
            set { this.allegatoextra = value; }
        }

        private double? costopresunto = null;
        [DataField("COSTOPRESUNTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 14)]
        public double? COSTOPRESUNTO
        {
            get { return this.costopresunto; }
            set { this.costopresunto = value; }
        }

        private string codiceoggetto = null;
        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 15)]
        public string CODICEOGGETTO
        {
            get { return this.codiceoggetto; }
            set { this.codiceoggetto = value; }
        }

        private string stc_iddocumento = null;
        [DataField("STC_IDDOCUMENTO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 16)]
        public string STC_IDDOCUMENTO
        {
            get { return this.stc_iddocumento; }
            set { this.stc_iddocumento = value; }
        }

        private string stc_idallegato = null;
        [DataField("STC_IDALLEGATO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 17)]
        public string STC_IDALLEGATO
        {
            get { return this.stc_idallegato; }
            set { this.stc_idallegato = value; }
        }


        #region Arraylist per gli inserimenti nelle tabelle collegate
        private IstanzeAllegatiOggetto _oggetto;
        [ForeignKey("IDCOMUNE, CODICEOGGETTO", "IDCOMUNE, CODICEOGGETTO")]
        [DataMember]
        [XmlElement(Order = 18)]
        public IstanzeAllegatiOggetto Oggetto
        {
            get { return this._oggetto; }
            set { this._oggetto = value; }
        }
        #endregion
    }

    [DataTable("OGGETTI")]
    [Serializable]
    [DataContract]
    public class IstanzeAllegatiOggetto : BaseDataClass
    {
        #region Key Fields
        private string codiceoggetto = null;
        [useSequence]
        [KeyField("CODICEOGGETTO", Type = DbType.Int16)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string CODICEOGGETTO
        {
            get { return this.codiceoggetto; }
            set { this.codiceoggetto = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        private string nomefile = null;
        [isRequired]
        [DataField("NOMEFILE", Size = 128, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string NOMEFILE
        {
            get { return this.nomefile; }
            set { this.nomefile = value; }
        }

        private List<OggettiMetadati> _metadati = new List<OggettiMetadati>();
        [ForeignKey("IDCOMUNE,CODICEOGGETTO", "Idcomune,Codiceoggetto")]
        [DataMember]
        [XmlElement(Order = 3)]
        public List<OggettiMetadati> Metadati
        {
            get { return this._metadati; }
            set { this._metadati = value; }
        }

    }
}
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZERICHIEDENTI")]
    [Serializable]
    [DataContract]
    public class IstanzeRichiedenti : BaseDataClass
    {

        #region Key Fields

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        private string codiceinvitato = null;
        [useSequence]
        [KeyField("CODICEINVITATO", Type = DbType.Decimal, KeyIdentity = true)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string CODICEINVITATO
        {
            get { return this.codiceinvitato; }
            set { this.codiceinvitato = value; }
        }

        #endregion

        private string codiceistanza = null;
        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string CODICEISTANZA
        {
            get { return this.codiceistanza; }
            set { this.codiceistanza = value; }
        }

        private string codicerichiedente = null;
        [isRequired]
        [DataField("CODICERICHIEDENTE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string CODICERICHIEDENTE
        {
            get { return this.codicerichiedente; }
            set { this.codicerichiedente = value; }
        }

        private string codicetiposoggetto = null;
        [isRequired]
        [DataField("CODICETIPOSOGGETTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string CODICETIPOSOGGETTO
        {
            get { return this.codicetiposoggetto; }
            set { this.codicetiposoggetto = value; }
        }

        private string codiceanagrafecoll = null;
        [DataField("CODICEANAGRAFECOLL", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 5)]
        public string CODICEANAGRAFECOLL
        {
            get { return this.codiceanagrafecoll; }
            set { this.codiceanagrafecoll = value; }
        }

        private string descrsoggetto = null;
        [DataField("DESCRSOGGETTO", Type = DbType.String, Size = 128, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string DESCRSOGGETTO
        {
            get { return this.descrsoggetto; }
            set { this.descrsoggetto = value; }
        }

        private int? m_codiceProcuratore = null;
        [DataField("CODICEPROCURATORE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 7)]
        public int? Codiceprocuratore
        {
            get { return this.m_codiceProcuratore; }
            set { this.m_codiceProcuratore = value; }
        }

        [DataField("CODICEOGGETTO_PROCURA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 8)]
        public int? CodiceoggettoProcura
        {
            get;
            set;
        }



        #region Arraylist per gli inserimenti nelle tabelle collegate
        private TipiSoggetto m_tipoSoggetto;

        [ForeignKey("IDCOMUNE, CODICETIPOSOGGETTO", "IDCOMUNE, CODICETIPOSOGGETTO")]
        [DataMember]
        [XmlElement(Order = 9)]
        public TipiSoggetto TipoSoggetto
        {
            get { return this.m_tipoSoggetto; }
            set { this.m_tipoSoggetto = value; }
        }


        private Anagrafe m_procuratore;
        [ForeignKey("IDCOMUNE, Codiceprocuratore", "IDCOMUNE, CODICEANAGRAFE")]
        [DataMember]
        [XmlElement(Order = 10)]
        public Anagrafe Procuratore
        {
            get { return this.m_procuratore; }
            set { this.m_procuratore = value; }
        }

        private Anagrafe m_richiedente;
        [ForeignKey("IDCOMUNE, CODICERICHIEDENTE", "IDCOMUNE, CODICEANAGRAFE")]
        [DataMember]
        [XmlElement(Order = 11)]
        public Anagrafe Richiedente
        {
            get { return this.m_richiedente; }
            set { this.m_richiedente = value; }
        }
        [DataMember]
        [XmlElement(Order = 12)]
        public List<AnagrafeDocumenti> AnagrafeDocumenti { get; set; } = new List<AnagrafeDocumenti>();

        private Anagrafe m_anagrafecollegata;
        [ForeignKey("IDCOMUNE, CODICEANAGRAFECOLL", "IDCOMUNE, CODICEANAGRAFE")]
        [DataMember]
        [XmlElement(Order = 13)]
        public Anagrafe AnagrafeCollegata
        {
            get { return this.m_anagrafecollegata; }
            set { this.m_anagrafecollegata = value; }
        }
        #endregion

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order = 14)]
        [ForeignKey("IDCOMUNE,CodiceoggettoProcura", "IDCOMUNE,CODICEOGGETTO")]
        [DataMember]
        public IstanzeRichiedentiOggetto OggettoProcura
        {
            get;
            set;
        }
    }

    [DataTable("OGGETTI")]
    [Serializable]
    [DataContract]
    public class IstanzeRichiedentiOggetto : BaseDataClass
    {
        #region Key Fields
        [useSequence]
        [KeyField("CODICEOGGETTO", Type = DbType.Int16)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string CODICEOGGETTO { get; set; }

        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE { get; set; }

        #endregion

        [isRequired]
        [DataField("NOMEFILE", Size = 128, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string NOMEFILE { get; set; }
        /*
        private List<OggettiMetadati> _metadati = new List<OggettiMetadati>();
        [ForeignKey("IDCOMUNE,CODICEOGGETTO", "Idcomune,Codiceoggetto")]
        [DataMember]
        [XmlElement(Order = 3)]
        public List<OggettiMetadati> Metadati
        {
            get { return this._metadati; }
            set { this._metadati = value; }
        }
        */
    }
}
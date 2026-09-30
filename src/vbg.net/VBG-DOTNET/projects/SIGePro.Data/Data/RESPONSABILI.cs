using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("RESPONSABILI")]
    [Serializable]
    [DataContract]
    public class Responsabili : BaseDataClass
    {

        #region Key Fields

        /// <summary>
        /// Chiave primaria della tabella insieme a IDCOMUNE
        /// </summary>
        [useSequence]
        [KeyField("CODICERESPONSABILE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string CODICERESPONSABILE { get; set; }

        /// <summary>
        /// Chiave primaria della tabella insieme a CODICERESPONSABILE
        /// </summary>
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE { get; set; }

        #endregion

        /// <summary>
        /// Nominativo del responsabile 
        /// </summary>
        [DataField("RESPONSABILE", Size = 60, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string RESPONSABILE { get; set; }

        /// <summary>
        /// Titolo del responsabile (sig., dott. etc etc)
        /// </summary>
        [DataField("TITOLO", Size = 6, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string TITOLO { get; set; }


        [DataField("QUALIFICA", Size = 50, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string QUALIFICA { get; set; }

        /// <summary>
        /// Data di nascita
        /// </summary>
        private DateTime? datanascita = null;
        [DataField("DATANASCITA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 5)]
        public DateTime? DATANASCITA
        {
            get { return this.datanascita; }
            set { this.datanascita = this.VerificaDataLocale(value); }
        }

        /// <summary>
        /// Codice fiscale
        /// </summary>
        [DataField("CODICEFISCALE", Size = 16, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string CODICEFISCALE { get; set; }


        /// <summary>
        /// Indirizzo
        /// </summary>
        [DataField("INDIRIZZO", Size = 50, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 7)]
        public string INDIRIZZO { get; set; }

        /// <summary>
        /// Città
        /// </summary>
        [DataField("CITTA", Size = 50, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string CITTA { get; set; }

        /// <summary>
        /// CAP
        /// </summary>
        [DataField("CAP", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string CAP { get; set; }

        /// <summary>
        /// Provincia
        /// </summary>
        [DataField("PROVINCIA", Size = 2, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 10)]
        public string PROVINCIA { get; set; }

        /// <summary>
        /// Telefono ufficio
        /// </summary>
        [DataField("TELEFONOLAVORO", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 11)]
        public string TELEFONOLAVORO { get; set; }

        /// <summary>
        /// Telefono privato
        /// </summary>
        [DataField("TELEFONOABITAZIONE", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 12)]
        public string TELEFONOABITAZIONE { get; set; }

        /// <summary>
        /// Cellulare
        /// </summary>
        [DataField("TELEFONOCELLULARE", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 13)]
        public string TELEFONOCELLULARE { get; set; }

        /// <summary>
        /// Fax
        /// </summary>
        [DataField("FAX", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 14)]
        public string FAX { get; set; }

        /// <summary>
        /// Flag che identifica se l'utente è amministratore
        /// </summary>
        [DataField("AMMINISTRATORE", Size = 1, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 15)]
        public string AMMINISTRATORE { get; set; }

        /// <summary>
        /// Password (hash)
        /// </summary>
        [DataField("PASSWORD", Size = 10, Type = DbType.String, CaseSensitive = true)]
        [DataMember]
        [XmlElement(Order = 16)]
        public string PASSWORD { get; set; }

        private string scadenzario = null;
        [DataField("SCADENZARIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 17)]
        public string SCADENZARIO
        {
            get { return this.scadenzario; }
            set { this.scadenzario = value; }
        }

        private string numggscadenz = null;
        [DataField("NUMGGSCADENZ", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 18)]
        public string NUMGGSCADENZ
        {
            get { return this.numggscadenz; }
            set { this.numggscadenz = value; }
        }

        private string scadenzarioprec = null;
        [DataField("SCADENZARIOPREC", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 19)]
        public string SCADENZARIOPREC
        {
            get { return this.scadenzarioprec; }
            set { this.scadenzarioprec = value; }
        }

        private string mps_codiceoperatore = null;
        [DataField("MPS_CODICEOPERATORE", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 20)]
        public string MPS_CODICEOPERATORE
        {
            get { return this.mps_codiceoperatore; }
            set { this.mps_codiceoperatore = value; }
        }

        private string userId = null;
        [DataField("USERID", Size = 15, Type = DbType.String, CaseSensitive = true)]
        [DataMember]
        [XmlElement(Order = 21)]
        public string USERID
        {
            get { return this.userId; }
            set { this.userId = value; }
        }

        private string email = null;
        [DataField("EMAIL", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 22)]
        public string EMAIL
        {
            get { return this.email; }
            set { this.email = value; }
        }

        private string filtrooperatorescadenz = null;
        [DataField("FILTROOPERATORESCADENZ", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 23)]
        public string FILTROOPERATORESCADENZ
        {
            get { return this.filtrooperatorescadenz; }
            set { this.filtrooperatorescadenz = value; }
        }

        private string amministratoresoftware = null;
        [DataField("AMMINISTRATORESOFTWARE", Size = 1, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 24)]
        public string AMMINISTRATORESOFTWARE
        {
            get { return this.amministratoresoftware; }
            set { this.amministratoresoftware = value; }
        }

        private string updatehelp = null;
        [DataField("UPDATEHELP", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 25)]
        public string UPDATEHELP
        {
            get { return this.updatehelp; }
            set
            {
                //imposto un valore di default sul set della proprietà se value è null.
                //non lo faccio sul get perchè in update potrei aver bisogno di sapere che è null
                this.updatehelp = value == null ? "0" : value;
            }
        }

        private string fkidprofiloassegnazione = null;
        [DataField("FKIDPROFILOASSEGNAZIONE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 26)]
        public string FKIDPROFILOASSEGNAZIONE
        {
            get { return this.fkidprofiloassegnazione; }
            set { this.fkidprofiloassegnazione = value; }
        }

        private string flag_notificaassegnazprot = null;
        [DataField("FLAG_NOTIFICAASSEGNAZPROT", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 27)]
        public string FLAG_NOTIFICAASSEGNAZPROT
        {
            get { return this.flag_notificaassegnazprot; }
            set { this.flag_notificaassegnazprot = value; }
        }

        private string fkidtiporesponsabile = null;
        [DataField("FKIDTIPORESPONSABILE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 28)]
        public string FKIDTIPORESPONSABILE
        {
            get { return this.fkidtiporesponsabile; }
            set { this.fkidtiporesponsabile = value; }
        }

        private string flag_gestione_oneri = null;
        [DataField("FLAG_GESTIONE_ONERI", Type = DbType.Int16)]
        [DataMember]
        [XmlElement(Order = 29)]
        public string FLAG_GESTIONE_ONERI
        {
            get { return this.flag_gestione_oneri; }
            set { this.flag_gestione_oneri = value; }
        }

        private string _readonly = null;
        [DataField("READONLY", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 30)]
        public string READONLY
        {
            get { return this._readonly; }
            set { this._readonly = value; }
        }

        private string matricola = null;
        [DataField("MATRICOLA", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 31)]
        public string MATRICOLA
        {
            get { return this.matricola; }
            set { this.matricola = value; }
        }

        /// <summary>
		/// Flag utente disabilitato
		/// </summary>
		[DataField("DISABILITATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 32)]
        public string DISABILITATO { get; set; }

        private string flag_modifica_numist = null;
        [DataField("FLAG_MODIFICA_NUMIST", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 33)]
        public string FLAG_MODIFICA_NUMIST
        {
            get { return this.flag_modifica_numist; }
            set { this.flag_modifica_numist = value; }
        }

        [DataField("COD_UTE_DOCER", Size = 30, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 34)]
        public string COD_UTE_DOCER { get; set; }

        [DataField("PASSWORD_UTE_DOCER", Size = 30, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 35)]
        public string PASSWORD_UTE_DOCER { get; set; }

        public override string ToString()
        {
            return this.RESPONSABILE;
        }
    }
}
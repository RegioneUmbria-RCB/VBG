using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Reflection;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
    [DataTable("ANAGRAFE")]
    [Serializable]
    [DataContract]
    public class Anagrafe : BaseDataClass, IClasseContestoModelloDinamico
    {
        #region Key Fields

        [DataMember]
        [XmlElement(Order = 0)]
        [useSequence]
        [KeyField("CODICEANAGRAFE", Type = DbType.Decimal)]
        public string CODICEANAGRAFE { get; set; }

        [DataMember]
        [XmlElement(Order = 1)]
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string IDCOMUNE { get; set; }

        #endregion

        #region Data Fields
        /// <summary>
        /// Cognome o ragione sociale
        /// </summary>
        [DataMember]
        [XmlElement(Order = 2)]
        [isRequired]
        [DataField("NOMINATIVO", Size = 220, Type = DbType.String, CaseSensitive = false)]
        public string NOMINATIVO { get; set; }

        /// <summary>
        /// Dismesso
        /// </summary>
        [DataMember]
        [XmlElement(Order = 3)]
        [Obsolete("Campo dismesso")]
        [DataField("REFERENTE", Size = 80, Type = DbType.String, CaseSensitive = false)]
        public string REFERENTE { get; set; }

        /// <summary>
        /// Codice forma giuridica (fk su FORMEGIURIDICHE.CODICEFORMAGIURIDICA insieme a IDCOMUNE)
        /// </summary>
        [DataField("FORMAGIURIDICA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string FORMAGIURIDICA { get; set; }

        /// <summary>
        /// 0= Richiedente, 1= Tecnico
        /// </summary>
        [DataField("TIPOLOGIA", Type = DbType.Decimal)]
        [isRequired]
        [DataMember]
        [XmlElement(Order = 5)]
        public string TIPOLOGIA { get; set; }

        /// <summary>
        /// Indirizzo di residenza
        /// </summary>
        [DataField("INDIRIZZO", Size = 100, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string INDIRIZZO { get; set; }

        /// <summary>
        /// Città residenza
        /// </summary>
        [DataField("CITTA", Size = 50, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 7)]
        public string CITTA { get; set; }

        /// <summary>
        /// CAP residenza
        /// </summary>
        [DataField("CAP", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string CAP { get; set; }

        /// <summary>
        /// Provincia residenza
        /// </summary>
        [DataField("PROVINCIA", Size = 2, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string PROVINCIA { get; set; }

        /// <summary>
        /// Telefono
        /// </summary>
        [DataField("TELEFONO", Size = 50, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 10)]
        public string TELEFONO { get; set; }

        /// <summary>
        /// Cellulare
        /// </summary>
        [DataField("TELEFONOCELLULARE", Size = 50, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 11)]
        public string TELEFONOCELLULARE { get; set; }

        /// <summary>
        /// Fax
        /// </summary>
        [DataField("FAX", Size = 50, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 12)]
        public string FAX { get; set; }

        private string _partitaiva = null;
        /// <summary>
        /// Partita IVA
        /// </summary>
        [DataField("PARTITAIVA", Size = 32, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 13)]
        public string PARTITAIVA
        {
            get { return this._partitaiva; }
            set
            {
                this._partitaiva = value;
                if (!String.IsNullOrEmpty(this._partitaiva))
                    this._partitaiva = this._partitaiva.ToUpper();
            }
        }

        private string _codicefiscale = null;
        /// <summary>
        /// Codice fiscale
        /// </summary>
        [DataField("CODICEFISCALE", Size = 32, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 14)]
        public string CODICEFISCALE
        {
            get { return this._codicefiscale; }
            set
            {
                this._codicefiscale = value;
                if (!String.IsNullOrEmpty(this._codicefiscale))
                    this._codicefiscale = this._codicefiscale.ToUpper();
            }
        }

        /// <summary>
        /// Note dell'anagrafica
        /// </summary>
        [DataField("NOTE", Size = 4000, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 15)]
        public string NOTE { get; set; }

        /// <summary>
        /// Indirizzo Email
        /// </summary>
        [DataField("EMAIL", Size = 320, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 16)]
        public string EMAIL { get; set; }

        /// <summary>
        /// Numero di registrazione alla camera di Commercio
        /// </summary>
        [DataField("REGDITTE", Size = 25, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 17)]
        public string REGDITTE { get; set; } = null;

        /// <summary>
        /// Numero di registrazione al Reg.Trib. (solo per persone giuridiche)
        /// </summary>
        [DataField("REGTRIB", Size = 25, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 18)]
        public string REGTRIB { get; set; } = null;

        /// <summary>
        /// Comune di registrazione alla Camera di Comemercio, fk su COMUNI.CODICECOMUNE (solo per persone giuridiche)
        /// </summary>
        [DataField("CODCOMREGDITTE", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 19)]
        public string CODCOMREGDITTE { get; set; } = null;

        /// <summary>
        /// Comune di registrazione al Reg.Trib., fk su COMUNI.CODICECOMUNE (solo per persone giuridiche)
        /// </summary>
        [DataField("CODCOMREGTRIB", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 20)]
        public string CODCOMREGTRIB { get; set; } = null;

        /// <summary>
        /// Codice del comune di nascita (fk su COMUNI.CODICECOMUNE)
        /// </summary>
        [DataField("CODCOMNASCITA", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 21)]
        public string CODCOMNASCITA { get; set; } = null;

        private DateTime? _datanascita = null;
        /// <summary>
        /// Data di nascita
        /// </summary>
        [DataField("DATANASCITA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 22)]
        public DateTime? DATANASCITA
        {
            get { return this._datanascita; }
            set { this._datanascita = this.VerificaDataLocale(value); }
        }

        private DateTime? _dataregditte = null;
        /// <summary>
        /// Data di registrazione alla camera di commercio
        /// </summary>
        [DataField("DATAREGDITTE", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 23)]
        public DateTime? DATAREGDITTE
        {
            get { return this._dataregditte; }
            set { this._dataregditte = this.VerificaDataLocale(value); }
        }

        private DateTime? _dataregtrib = null;
        /// <summary>
        /// Data di registrazione al Reg.Trib.
        /// </summary>
        [DataField("DATAREGTRIB", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 24)]
        public DateTime? DATAREGTRIB
        {
            get { return this._dataregtrib; }
            set { this._dataregtrib = this.VerificaDataLocale(value); }
        }

        /// <summary>
        /// Flag che identifica se l'anagrafica deve ricevere comunicazione relative all'istanza
        /// </summary>
        [DataField("INVIOEMAIL", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 25)]
        public string INVIOEMAIL { get; set; } = null;

        /// <summary>
        /// Sesso: M o F
        /// </summary>
        [DataField("SESSO", Size = 1, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 26)]
        public string SESSO { get; set; } = null;

        /// <summary>
        /// Nome (solo per persone fisiche)
        /// </summary>
        [DataField("NOME", Size = 60, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 27)]
        public string NOME { get; set; } = null;

        /// <summary>
        /// Id del titolo (fk su TITOLI.CODICETITOLO insieme a IDCOMUNE)
        /// </summary>
        [DataField("TITOLO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 28)]
        public string TITOLO { get; set; } = null;

        /// <summary>
        /// Specifica se l'anagrafica è una persona fisica (F) o giuridica (G)
        /// </summary>
        [DataField("TIPOANAGRAFE", Size = 1, Type = DbType.String, CaseSensitive = false)]
        [isRequired]
        [DataMember]
        [XmlElement(Order = 29)]
        public string TIPOANAGRAFE { get; set; } = null;

        private DateTime? _datanominativo = null;
        /// <summary>
        /// Data di costituzione (solo per persone giuridiche)
        /// </summary>
        [DataField("DATANOMINATIVO", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 30)]
        public DateTime? DATANOMINATIVO
        {
            get { return this._datanominativo; }
            set { this._datanominativo = this.VerificaDataLocale(value); }
        }

        /// <summary>
        /// Flag che identifica se l'anagrafica deve ricevere comunicazione relative all'istanza (valido solo se tecnico)
        /// </summary>
        [DataField("INVIOEMAILTEC", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 31)]
        public string INVIOEMAILTEC { get; set; } = null;

        /// <summary>
        /// Codice della cittadinanza (fk su CITTADINANZA.CODICE)
        /// </summary>
        [DataField("CODICECITTADINANZA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 32)]
        public string CODICECITTADINANZA { get; set; } = null;

        /// <summary>
        /// Codice del comune di residenza (fk su COMUNI.CODICECOMUNE)
        /// </summary>
        [DataField("COMUNERESIDENZA", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 33)]
        public string COMUNERESIDENZA { get; set; } = null;

        /// <summary>
        /// Hash della password dell'anagrafica
        /// </summary>
        [DataField("PASSWORD", Size = 128, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 34)]
        public string PASSWORD { get; set; } = null;

        /// <summary>
        /// Indirizzo di corrispondenza
        /// </summary>
        [DataField("INDIRIZZOCORRISPONDENZA", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 35)]
        public string INDIRIZZOCORRISPONDENZA { get; set; } = null;

        /// <summary>
        /// Città dell'indirizzo di corrispondenza
        /// </summary>
        [DataField("CITTACORRISPONDENZA", Size = 50, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 36)]
        public string CITTACORRISPONDENZA { get; set; } = null;

        /// <summary>
        /// Cap dell'indirizzo di corrispondenza
        /// </summary>
        [DataField("CAPCORRISPONDENZA", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 37)]
        public string CAPCORRISPONDENZA { get; set; } = null;

        /// <summary>
        /// Sigla della provincia dell'indirizzo di corrispondenza (fk su COMUNI.SIGLAPROVINCIA)
        /// </summary>
        [DataField("PROVINCIACORRISPONDENZA", Size = 2, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 38)]
        public string PROVINCIACORRISPONDENZA { get; set; } = null;

        /// <summary>
        /// Codice del comune dell indirizzo di corrispondenza (fk su COMUNI.CODICECOMUNE)
        /// </summary>
        [DataField("COMUNECORRISPONDENZA", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 39)]
        public string COMUNECORRISPONDENZA { get; set; } = null;

        /// <summary>
        /// Profincia di iscrizione al REA
        /// </summary>
        [DataField("PROVINCIAREA", Size = 2, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 40)]
        public string PROVINCIAREA { get; set; } = null;

        /// <summary>
        /// Numero di iscrizione al REA
        /// </summary>
        [DataField("NUMISCRREA", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 41)]
        public string NUMISCRREA { get; set; } = null;

        private DateTime? _dataiscrrea = null;
        /// <summary>
        /// Data di iscrizione al REA
        /// </summary>
        [DataField("DATAISCRREA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 42)]
        public DateTime? DATAISCRREA
        {
            get { return this._dataiscrrea; }
            set { this._dataiscrrea = this.VerificaDataLocale(value); }
        }

        /// <summary>
        /// Dismesso (identifica de l'aziena è un ente no profit)
        /// </summary>
        [DataField("FLAG_NOPROFIT", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 43)]
        public string FLAG_NOPROFIT { get; set; } = null;

        /// <summary>
        /// Flag che identifica se l'anagrafica è disabilitata (0 = non disabilitata, 1 = disabilitata)
        /// </summary>
        [DataField("FLAG_DISABILITATO", Type = DbType.Decimal)]
        [isRequired]
        [DataMember]
        [XmlElement(Order = 44)]
        public string FLAG_DISABILITATO { get; set; } = null;

        private DateTime? _data_disabilitato = null;
        /// <summary>
        /// Data in cui l'anagrafica è stata disabilitata (se FLAG_DISABILITATO == 1)
        /// </summary>
        [DataField("DATA_DISABILITATO", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 45)]
        public DateTime? DATA_DISABILITATO
        {
            get { return this._data_disabilitato; }
            set { this._data_disabilitato = this.VerificaDataLocale(value); }
        }

        /// <summary>
        /// Nel caso in cui venga usato un sistema di SSO o l'accesso con CIE è l'username su cui viene effettuata l'autenticazione
        /// </summary>
        [DataField("STRONG_AUTH_ID", Size = 250, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 46)]
        public string Username { get; set; }

        /// <summary>
        /// Numero di iscrizione all'albo professionale (solo per tecnici)
        /// </summary>
        [DataField("CODICEELENCOPRO", Type = DbType.Int64)]
        [DataMember]
        [XmlElement(Order = 47)]
        public string CODICEELENCOPRO { get; set; } = null;

        /// <summary>
        /// Numero di registrazione all'albo professionale
        /// </summary>
        [DataField("NUMEROELENCOPRO", Size = 10, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 48)]
        public string NUMEROELENCOPRO { get; set; } = null;

        /// <summary>
        /// Provincia di registrazione all'albo professionale
        /// </summary>
        [DataField("PROVINCIAELENCOPRO", Size = 2, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 49)]
        public string PROVINCIAELENCOPRO { get; set; } = null;

        /// <summary>
        /// INdirizzo PEC per le comunicazioni
        /// </summary>
        [DataField("PEC", Size = 320, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 50)]
        public string Pec { get; set; }

        /// <summary>
        /// Flag che identifica se l'utente può visualizzare l'intero albero degli interventi nel FO
        /// </summary>
        [DataField("FO_UTENTETESTER", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 51)]
        public int? FoUtenteTester
        {
            get;
            set;
        }
        #endregion

        /// <summary>
        /// Risoluzione della foreign key TITOLO
        /// </summary>
        [ForeignKey("IDCOMUNE,TITOLO", "IDCOMUNE,CODICETITOLO")]
        [DataMember]
        [XmlElement(Order = 52)]
        public Titoli TitoloClass { get; set; } = null;

        /// <summary>
        /// Risoluzione della foreign key CODICEELENCOPRO (albo professionale)
        /// </summary>
        [ForeignKey("CODICEELENCOPRO", "EpId")]
        [DataMember]
        [XmlElement(Order = 53)]
        public ElenchiProfessionaliBase ElencoProfessionale { get; set; } = null;

        /// <summary>
        /// Risoluzione della foreign key FORMAGIURIDICA (tipo di forma giuridica)
        /// </summary>
        [ForeignKey("IDCOMUNE,FORMAGIURIDICA", "IDCOMUNE,CODICEFORMAGIURIDICA")]
        [DataMember]
        [XmlElement(Order = 54)]
        public FormeGiuridiche FormaGiuridicaClass { get; set; } = null;

        #region Arraylist per gli inserimenti nelle tabelle collegate

        /// <summary>
        /// Utilizzato internamente (documenti dell'anagrafica)
        /// </summary>
        [DataMember]
        [XmlElement(Order = 55)]
        public List<AnagrafeDocumenti> AnagrafeDocumenti { get; set; } = new List<AnagrafeDocumenti>();

        /// <summary>
        /// Modelli dinamici associati all'anagrafica (uso interno)
        /// </summary>
        [ForeignKey("IDCOMUNE,CODICEANAGRAFE", "Idcomune,Codiceanagrafe")]
        [DataMember]
        [XmlElement(Order = 56)]
        public List<AnagrafeDyn2ModelliT> AnagrafeDyn2ModelliT { get; set; } = new List<AnagrafeDyn2ModelliT>();

        /// <summary>
        /// Dati dinamici associati all'anagrafica (uso interno)
        /// </summary>
        [ForeignKey("IDCOMUNE,CODICEANAGRAFE", "Idcomune,Codiceanagrafe")]
        [DataMember]
        [XmlElement(Order = 57)]
        public List<AnagrafeDyn2Dati> AnagrafeDyn2Dati { get; set; } = new List<AnagrafeDyn2Dati>();

        /// <summary>
        /// Risoluzione della FK CODCOMNASCITA (dati del comune di nascita)
        /// </summary>
        [ForeignKey("CODCOMNASCITA", "CODICECOMUNE")]
        [DataMember]
        [XmlElement(Order = 58)]
        public Comuni ComuneNascita { get; set; } = null;

        /// <summary>
        /// Uso interno
        /// </summary>
        [DataMember]
        [XmlElement(Order = 59)]
        public Comuni ComuneRegDitte { get; set; } = null;

        /// <summary>
        /// Uso interno
        /// </summary>
        [DataMember]
        [XmlElement(Order = 60)]
        public Comuni ComuneRegTrib { get; set; } = null;

        /// <summary>
        /// Uso interno
        /// </summary>
        [DataMember]
        [XmlElement(Order = 61)]
        public Comuni ComuneCorrispondenza { get; set; } = null;

        /// <summary>
        /// Risoluzione della FK COMUNERESIDENZA (dati del comune di residenza)
        /// </summary>
        [ForeignKey("COMUNERESIDENZA", "CODICECOMUNE")]
        [DataMember]
        [XmlElement(Order = 62)]
        public Comuni ComuneResidenza { get; set; } = null;

        /// <summary>
        /// Uso interno
        /// </summary>
        [DataMember]
        [XmlElement(Order = 63)]
        public Cittadinanza Cittadinanza { get; set; } = null;

        /// <summary>
        /// Uso interno (storico presenze nei mercati)
        /// </summary>
        [ForeignKey("IDCOMUNE,CODICEANAGRAFE", "Idcomune,Codiceanagrafe")]
        [DataMember]
        [XmlElement(Order = 64)]
        public List<MercatiPresenzeStorico> PresenzeStoriche { get; set; } = new List<MercatiPresenzeStorico>();

        #endregion

        #region inps e inail
        [DataField("INAIL_MATRICOLA", Size = 50, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 65)]
        public string InailMatricola { get; set; }

        [DataField("INAIL_CODICESEDE", Size = 16, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 66)]
        public string InailCodiceSede { get; set; }

        [DataField("INPS_MATRICOLA", Size = 50, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 67)]
        public string InpsMatricola { get; set; }

        [DataField("INPS_CODICESEDE", Size = 16, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 68)]
        public string InpsCodiceSede { get; set; }

        [ForeignKey("InpsCodiceSede", "Codice")]
        [DataMember]
        [XmlElement(Order = 69)]
        public ElencoInpsBase SedeInps { get; set; } = new ElencoInpsBase();

        [ForeignKey("InailCodiceSede", "Codice")]
        [DataMember]
        [XmlElement(Order = 70)]
        public ElencoInailBase SedeInail { get; set; } = new ElencoInailBase();

        #endregion

        [XmlIgnore]
        [DataField("CASSAEDILE_MATRICOLA", Size = 50, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 71)]
        public string CassaEdileMatricola { get; set; }

        [XmlIgnore]
        [DataField("CASSAEDILE_CODICESEDE", Size = 4, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 72)]
        public string CassaEdileCodiceSede { get; set; }

        [XmlIgnore]
        [DataField("FLAG_IDENTIFICATO", Size = 1, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 73)]
        public string FlagIdentificato { get; set; }

        [XmlIgnore]
        [DataField("OPER_IDENTIFICAZIONE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 74)]
        public decimal? OperIdentificazione { get; set; }

        [XmlIgnore]
        [DataField("DATA_IDENTIFICAZIONE", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 75)]
        public DateTime? DataIdentificazione { get; set; }

        [XmlIgnore]
        [DataField("DATA_INIZIO_ATTIVITA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 76)]
        public DateTime? DataInizioAttivita { get; set; }

        /// <summary>
        /// Restituisce il nome completo dell'anagrafica nel formato "NOME COGNOME [CODICEFISCALE PARTITAIVA]"
        /// </summary>
        public override string ToString()
        {
            StringBuilder sb = new StringBuilder(this.GetNomeCompleto());

            if (!String.IsNullOrEmpty(this.TIPOANAGRAFE))
            {
                sb.Append(" (P").Append(this.TIPOANAGRAFE).Append(")");
            }

            sb.Append(" - ");

            if (!String.IsNullOrEmpty(this.CODICEFISCALE))
            {
                sb.Append("C.F. ").Append(this.CODICEFISCALE.ToUpper());

                if (!String.IsNullOrEmpty(this.PARTITAIVA))
                {
                    sb.Append("/");
                }
            }

            if (!String.IsNullOrEmpty(this.PARTITAIVA))
                sb.Append("P.I. ").Append(this.PARTITAIVA.ToUpper());

            return sb.ToString();
        }

        public string ToErrorString(string propName)
        {
            StringBuilder sb = new StringBuilder();

            sb.Append("CODICEANAGRAFE=\"");
            sb.Append(this.CODICEANAGRAFE);
            sb.Append("\"; ");
            sb.Append("IDCOMUNE=\"");
            sb.Append(this.IDCOMUNE);
            sb.Append("\"; ");
            sb.Append("NOMINATIVO=\"");
            sb.Append(this.NOMINATIVO);
            sb.Append("\"; ");
            sb.Append("PARTITAIVA=\"");
            sb.Append(this.PARTITAIVA);
            sb.Append("\"; ");
            sb.Append("CODICEFISCALE=\"");
            sb.Append(this.CODICEFISCALE);
            sb.Append("\"; ");
            sb.Append("DATANASCITA=\"");
            sb.Append(this.DATANASCITA);
            sb.Append("\"; ");
            sb.Append("NOME=\"");
            sb.Append(this.NOME);
            sb.Append("\"; ");
            sb.Append("TIPOANAGRAFE=\"");
            sb.Append(this.TIPOANAGRAFE);
            sb.Append("\"; ");
            sb.Append("COMUNERESIDENZA=\"");
            sb.Append(this.COMUNERESIDENZA);
            sb.Append("\"; ");
            sb.Append("DATANOMINATIVO=\"");
            sb.Append(this.DATANOMINATIVO);
            sb.Append("\"; ");

            Type classtype = this.GetType();

            PropertyInfo pi = classtype.GetProperty(propName);

            if (pi != null)
            {
                sb.Append(pi.Name);
                sb.Append("=");

                object value = pi.GetValue(this, null);

                if (value == null)
                {
                    sb.Append("null");
                }
                else
                {
                    sb.Append("\"");
                    sb.Append(value.ToString());
                    sb.Append("\"");
                }
                sb.Append("; ");
            }

            sb.Append("\n ");

            return sb.ToString();
        }

        /// <summary>
        /// Ottiene il nome completo dell'anagrafica nella forma "COGNOME NOME"
        /// </summary>
        /// <returns></returns>
        public string GetNomeCompleto()
        {
            StringBuilder sb = new StringBuilder();

            sb.Append(this.NOMINATIVO.Trim());

            if (!String.IsNullOrEmpty(this.NOME))
                sb.Append(" ").Append(this.NOME.Trim());

            return sb.ToString();
        }
    }
}
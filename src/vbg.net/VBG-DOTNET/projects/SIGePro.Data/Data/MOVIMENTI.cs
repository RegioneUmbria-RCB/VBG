using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    public interface IMovimentoDaProtocollare
    {
        string CODICEMOVIMENTO { get; }

        string FKIDPROTOCOLLO { get; }
        string NUMEROPROTOCOLLO { get; }
        DateTime? DATAPROTOCOLLO { get; }
        DateTime? DATA { get; }
        int? CREATO_DA_STC { get; }
        string TIPOMOVIMENTO { get; }
        string MOVIMENTO { get; }
        string IDCOMUNE { get; }
        string NOTE { get; }
    }


    [DataTable("MOVIMENTI")]
    [Serializable]
    [DataContract]
    public class Movimenti : BaseDataClass, IMovimentoDaProtocollare
    {

        #region Key Fields

        private string codicemovimento = null;
        private string idcomune = null;
        /// <summary>
        /// Chiave primaria insieme a CODICEMOVIMENTO
        /// </summary>
        [DataMember]
        [XmlElement(Order = 0)]
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        /// <summary>
        /// Chiave primaria insieme a IDCOMUNE
        /// </summary>
        [useSequence]
        [KeyField("CODICEMOVIMENTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string CODICEMOVIMENTO
        {
            get { return this.codicemovimento; }
            set { this.codicemovimento = value; }
        }
        #endregion

        private string codiceistanza = null;
        /// <summary>
        /// Id dell'istanza collegata (fk su ISTANZE.CODICEISTANZA insieme a IDCOMUNE)
        /// </summary>
        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string CODICEISTANZA
        {
            get { return this.codiceistanza; }
            set { this.codiceistanza = value; }
        }

        private string tipomovimento = null;
        /// <summary>
        /// Id tipo movimento (fk su TIPIMOVIMENTO.TIPOMOVIMENTO inseime a IDCOMUNE)
        /// </summary>
        [isRequired]
        [DataField("TIPOMOVIMENTO", Size = 6, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string TIPOMOVIMENTO
        {
            get { return this.tipomovimento; }
            set { this.tipomovimento = value; }
        }

        private string movimento = null;
        /// <summary>
        /// Nome del movimento
        /// </summary>
        [isRequired]
        [DataField("MOVIMENTO", Size = 128, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string MOVIMENTO
        {
            get { return this.movimento; }
            set { this.movimento = value; }
        }

        private string codiceinventario = null;
        /// <summary>
        /// fk su INVENTARIOPROCEDIMENTI.CODICEINVENTARIO insieme a IDCOMUNE
        /// </summary>
        [DataField("CODICEINVENTARIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 5)]
        public string CODICEINVENTARIO
        {
            get { return this.codiceinventario; }
            set { this.codiceinventario = value; }
        }

        private string codiceamministrazione = null;
        /// <summary>
        /// Fk su AMMINISTRAZIONI.CODICEAMMINISTRAZIONE insieme a IDCOMUNE
        /// </summary>
        [DataField("CODICEAMMINISTRAZIONE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string CODICEAMMINISTRAZIONE
        {
            get { return this.codiceamministrazione; }
            set { this.codiceamministrazione = value; }
        }

        private string codammrichiedente = null;
        [DataField("CODAMMRICHIEDENTE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 7)]
        public string CODAMMRICHIEDENTE
        {
            get { return this.codammrichiedente; }
            set { this.codammrichiedente = value; }
        }

        private DateTime? data = null;
        /// <summary>
        /// Data del movimento o null se ilmovimento deve essere ancora eseguire
        /// </summary>
        [isRequired]
        [DataField("DATA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 8)]
        public DateTime? DATA
        {
            get { return this.data; }
            set { this.data = this.VerificaDataLocale(value); }
        }

        private string parere = null;
        /// <summary>
        /// Descrizione estesa del parere
        /// </summary>
        [DataField("PARERE", Size = 2000, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string PARERE
        {
            get { return this.parere; }
            set { this.parere = value; }
        }

        private string esito = null;
        /// <summary>
        /// Esito: 1 o -1 = esito positivo, 0 o null = esito negativo
        /// </summary>
        [isRequired]
        [DataField("ESITO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 10)]
        public string ESITO
        {
            get { return this.esito; }
            set { this.esito = value; }
        }

        private string note = null;
        /// <summary>
        /// Note del movimento
        /// </summary>
        [DataField("NOTE", Size = 255, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 11)]
        public string NOTE
        {
            get { return this.note; }
            set { this.note = value; }
        }

        private string pubblica = null;
        /// <summary>
        /// Flag che indica se il movimento deve essere pubblicato nel FO (1 o -1 = pubblica, 0 o null = non pubblicare)
        /// </summary>
        [isRequired]
        [DataField("PUBBLICA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 12)]
        public string PUBBLICA
        {
            get { return this.pubblica; }
            set { this.pubblica = value; }
        }

        private string numeroprotocollo = null;
        /// <summary>
        /// Numero protocollo
        /// </summary>
        [DataField("NUMEROPROTOCOLLO", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 13)]
        public string NUMEROPROTOCOLLO
        {
            get { return this.numeroprotocollo; }
            set { this.numeroprotocollo = value; }
        }

        private DateTime? dataprotocollo = null;
        /// <summary>
        /// Data protocollo
        /// </summary>
        [DataField("DATAPROTOCOLLO", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 14)]
        public DateTime? DATAPROTOCOLLO
        {
            get { return this.dataprotocollo; }
            set { this.dataprotocollo = this.VerificaDataLocale(value); }
        }

        private string codiceufficio = null;
        [DataField("CODICEUFFICIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 15)]
        public string CODICEUFFICIO
        {
            get { return this.codiceufficio; }
            set { this.codiceufficio = value; }
        }

        private string fkidprotocollo = null;
        [DataField("FKIDPROTOCOLLO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 16)]
        public string FKIDPROTOCOLLO
        {
            get { return this.fkidprotocollo; }
            set { this.fkidprotocollo = value; }
        }

        private string codiceresponsabile = null;
        [isRequired]
        [DataField("CODICERESPONSABILE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 17)]
        public string CODICERESPONSABILE
        {
            get { return this.codiceresponsabile; }
            set { this.codiceresponsabile = value; }
        }

        private DateTime? datainserimento = null;
        [DataField("DATAINSERIMENTO", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 18)]
        public DateTime? DATAINSERIMENTO
        {
            get { return this.datainserimento; }
            set { this.datainserimento = this.VerificaDataLocale(value); }
        }

        private string fileatto = null;
        [DataField("FILEATTO", Size = 255, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 19)]
        public string FILEATTO
        {
            get { return this.fileatto; }
            set { this.fileatto = value; }
        }

        private string codiceoggetto = null;
        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 20)]
        public string CODICEOGGETTO
        {
            get { return this.codiceoggetto; }
            set { this.codiceoggetto = value; }
        }

        private string pubblicaparere = null;
        [DataField("PUBBLICAPARERE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 21)]
        public string PUBBLICAPARERE
        {
            get { return this.pubblicaparere; }
            set { this.pubblicaparere = value; }
        }

        private int? creato_da_stc = null;
        [DataField("CREATO_DA_STC", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 22)]
        public int? CREATO_DA_STC
        {
            get { return this.creato_da_stc; }
            set { this.creato_da_stc = value; }
        }

        private int? inviato_con_stc = null;
        [DataField("INVIATO_CON_STC", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 23)]
        public int? INVIATO_CON_STC
        {
            get { return this.inviato_con_stc; }
            set { this.inviato_con_stc = value; }
        }

        private int? inviato_a_camcom = null;
        [DataField("INVIATO_A_CAMCOM", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 24)]
        public int? INVIATO_A_CAMCOM
        {
            get { return this.inviato_a_camcom; }
            set { this.inviato_a_camcom = value; }
        }

        private int? flag_da_leggere = null;
        [isRequired]
        [DataField("FLAG_DA_LEGGERE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 25)]
        public int? FLAG_DA_LEGGERE
        {
            get { return this.flag_da_leggere; }
            set { this.flag_da_leggere = value; }
        }



        #region Arraylist per gli inserimenti nelle tabelle collegate e foreign keys

        private List<MovimentiAllegati> m_movimentiAllegati = new List<MovimentiAllegati>();
        [ForeignKey("IDCOMUNE,CODICEMOVIMENTO", "IDCOMUNE,CODICEMOVIMENTO")]
        [DataMember]
        [XmlElement(Order = 26)]
        public List<MovimentiAllegati> MovimentiAllegati
        {
            get { return this.m_movimentiAllegati; }
            set { this.m_movimentiAllegati = value; }
        }

        private List<Autorizzazioni> m_autorizzazione = new List<Autorizzazioni>();
        [ForeignKey("IDCOMUNE, CODICEMOVIMENTO,CODICEISTANZA", "IDCOMUNE, CODICEMOVIMENTO,FKIDISTANZA")]
        [DataMember]
        [XmlElement(Order = 27)]
        public List<Autorizzazioni> Autorizzazione
        {
            get { return this.m_autorizzazione; }
            set { this.m_autorizzazione = value; }
        }

        private List<MovimentiDyn2ModelliT> m_movimentidyn2modellit = new List<MovimentiDyn2ModelliT>();
        [ForeignKey("IDCOMUNE, CODICEMOVIMENTO", "Idcomune,Codicemovimento")]
        [DataMember]
        [XmlElement(Order = 28)]
        public List<MovimentiDyn2ModelliT> MovimentiDyn2ModelliT
        {
            get { return this.m_movimentidyn2modellit; }
            set { this.m_movimentidyn2modellit = value; }
        }

        // Utilizzato solamente per la visura. Permette di sapere l'uid di una pratica collegata inviata via STC
        // non è mappato ad alcun campo del db
        [DataMember]
        [XmlElement(Order = 29)]
        public string UuidPraticaCollegata { get; set; }


        [ForeignKey("IDCOMUNE,TIPOMOVIMENTO", "Idcomune,Tipomovimento")]
        [DataMember]
        [XmlElement(Order = 30)]
        // Id tipo movimento(fk su TIPIMOVIMENTO.TIPOMOVIMENTO inseime a IDCOMUNE)
        public TipiMovimento Tipo
        {
            get; set;
        }
        /*
		Autorizzazioni _Autorizzazione = null;
		public Autorizzazioni Autorizzazione
		{
			get { return _Autorizzazione; }
			set { _Autorizzazione = value; }
		}
		*/
        #endregion
    }
}
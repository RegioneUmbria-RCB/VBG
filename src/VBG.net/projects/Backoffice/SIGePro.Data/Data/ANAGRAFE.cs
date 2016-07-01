using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;
using System.Text.RegularExpressions;
using System.ComponentModel;
using System.Collections.Generic;
using Init.SIGePro.DatiDinamici.Interfaces;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("ANAGRAFE")]
	[Serializable]
	public class Anagrafe : BaseDataClass, IClasseContestoModelloDinamico
	{
		#region Key Fields

		[XmlElement(Order=0)]
		[useSequence]
		[KeyField("CODICEANAGRAFE", Type=DbType.Decimal)]
        public string CODICEANAGRAFE{get;set;}

		[XmlElement(Order = 1)]
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE{get;set;}

		#endregion

        #region Data Fields
		/// <summary>
		/// Cognome o ragione sociale
		/// </summary>
		[XmlElement(Order=2)]
		[isRequired]
		[DataField("NOMINATIVO",Size=180, Type=DbType.String, CaseSensitive=false)]
		public string NOMINATIVO{get;set;}

		/// <summary>
		/// Dismesso
		/// </summary>
		[XmlElement(Order = 3)]
		[Obsolete("Campo dismesso")]
		[DataField("REFERENTE",Size=80, Type=DbType.String, CaseSensitive=false)]
		public string REFERENTE{get;set;}

		/// <summary>
		/// Codice forma giuridica (fk su FORMEGIURIDICHE.CODICEFORMAGIURIDICA insieme a IDCOMUNE)
		/// </summary>
		[DataField("FORMAGIURIDICA", Type=DbType.Decimal)]
		[XmlElement(Order = 4)]
		public string FORMAGIURIDICA{get;set;}

		/// <summary>
		/// 0= Richiedente, 1= Tecnico
		/// </summary>
		[DataField("TIPOLOGIA", Type=DbType.Decimal)]
		[isRequired]
		[XmlElement(Order = 5)]
		public string TIPOLOGIA{get;set;}

		/// <summary>
		/// Indirizzo di residenza
		/// </summary>
		[DataField("INDIRIZZO",Size=80, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 6)]
		public string INDIRIZZO{get;set;}

		/// <summary>
		/// Città residenza
		/// </summary>
		[DataField("CITTA",Size=50, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 7)]
		public string CITTA{get;set;}

		/// <summary>
		/// CAP residenza
		/// </summary>
		[DataField("CAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 8)]
		public string CAP{get;set;}

		/// <summary>
		/// Provincia residenza
		/// </summary>
		[DataField("PROVINCIA",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 9)]
		public string PROVINCIA{get;set;}

		/// <summary>
		/// Telefono
		/// </summary>
		[DataField("TELEFONO",Size=30, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 10)]
		public string TELEFONO{get;set;}

		/// <summary>
		/// Cellulare
		/// </summary>
		[DataField("TELEFONOCELLULARE",Size=30, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 11)]
		public string TELEFONOCELLULARE{get;set;}

		
		/// <summary>
		/// Fax
		/// </summary>
		[DataField("FAX",Size=30, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 12)]
		public string FAX{get;set;}


		private string partitaiva = null;
		/// <summary>
		/// Partita IVA
		/// </summary>
		[DataField("PARTITAIVA", Size = 11, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 13)]
		public string PARTITAIVA
		{
			get { return partitaiva; }
			set 
            { 
                partitaiva = value;
                if (!String.IsNullOrEmpty(partitaiva))
                    partitaiva = partitaiva.ToUpper();
            }
		}

		string codicefiscale=null;
		/// <summary>
		/// Codice fiscale
		/// </summary>
		[DataField("CODICEFISCALE",Size=16, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 14)]
		public string CODICEFISCALE
		{
			get { return codicefiscale; }
			set 
            { 
                codicefiscale = value;
                if (!String.IsNullOrEmpty(codicefiscale))
                    codicefiscale = codicefiscale.ToUpper();
            }
		}

		/// <summary>
		/// Note dell'anagrafica
		/// </summary>
		[DataField("NOTE",Size=4000, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 15)]
		public string NOTE{get;set;}

		/// <summary>
		/// Indirizzo Email
		/// </summary>
		[DataField("EMAIL",Size=70, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 16)]
		public string EMAIL{get;set;}

		string regditte=null;
		/// <summary>
		/// Numero di registrazione alla camera di Commercio
		/// </summary>
		[DataField("REGDITTE",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 17)]
		public string REGDITTE
		{
			get { return regditte; }
			set { regditte = value; }
		}

		string regtrib=null;
		/// <summary>
		/// Numero di registrazione al Reg.Trib. (solo per persone giuridiche)
		/// </summary>
		[DataField("REGTRIB",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 18)]
		public string REGTRIB
		{
			get { return regtrib; }
			set { regtrib = value; }
		}

		string codcomregditte=null;
		/// <summary>
		/// Comune di registrazione alla Camera di Comemercio, fk su COMUNI.CODICECOMUNE (solo per persone giuridiche)
		/// </summary>
		[DataField("CODCOMREGDITTE", Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 19)]
		public string CODCOMREGDITTE
		{
			get { return codcomregditte; }
			set { codcomregditte = value; }
		}

		string codcomregtrib=null;
		/// <summary>
		/// Comune di registrazione al Reg.Trib., fk su COMUNI.CODICECOMUNE (solo per persone giuridiche)
		/// </summary>
		[DataField("CODCOMREGTRIB", Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 20)]
		public string CODCOMREGTRIB
		{
			get { return codcomregtrib; }
			set { codcomregtrib = value; }
		}

		string codcomnascita=null;
		/// <summary>
		/// Codice del comune di nascita (fk su COMUNI.CODICECOMUNE)
		/// </summary>
		[DataField("CODCOMNASCITA", Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 21)]
		public string CODCOMNASCITA
		{
			get { return codcomnascita; }
			set { codcomnascita = value; }
		}

        DateTime? datanascita = null;
		/// <summary>
		/// Data di nascita
		/// </summary>
		[DataField("DATANASCITA", Type=DbType.DateTime)]
		[XmlElement(Order = 22)]
		public DateTime? DATANASCITA
		{
			get { return datanascita; }
            set { datanascita = VerificaDataLocale(value); }
		}

        DateTime? dataregditte = null;
		/// <summary>
		/// Data di registrazione alla camera di commercio
		/// </summary>
		[DataField("DATAREGDITTE", Type=DbType.DateTime)]
		[XmlElement(Order = 23)]
		public DateTime? DATAREGDITTE
		{
			get { return dataregditte; }
            set { dataregditte = VerificaDataLocale(value); }
		}

        DateTime? dataregtrib = null;
		/// <summary>
		/// Data di registrazione al Reg.Trib.
		/// </summary>
		[DataField("DATAREGTRIB", Type=DbType.DateTime)]
		[XmlElement(Order = 24)]
		public DateTime? DATAREGTRIB
		{
			get { return dataregtrib; }
            set { dataregtrib = VerificaDataLocale(value); }
		}

		string invioemail=null;
		/// <summary>
		/// Flag che identifica se l'anagrafica deve ricevere comunicazione relative all'istanza
		/// </summary>
		[DataField("INVIOEMAIL", Type=DbType.Decimal)]
		[XmlElement(Order = 25)]
		public string INVIOEMAIL
		{
			get { return invioemail; }
			set { invioemail = value; }
		}

		string sesso=null;
		/// <summary>
		/// Sesso: M o F
		/// </summary>
		[DataField("SESSO",Size=1, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 26)]
		public string SESSO
		{
			get { return sesso; }
			set { sesso = value; }
		}

		string nome=null;
		/// <summary>
		/// Nome (solo per persone fisiche)
		/// </summary>
		[DataField("NOME",Size=30, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 27)]
		public string NOME
		{
			get { return nome; }
			set { nome = value; }
		}

		string titolo=null;
		/// <summary>
		/// Id del titolo (fk su TITOLI.CODICETITOLO insieme a IDCOMUNE)
		/// </summary>
		[DataField("TITOLO", Type=DbType.Decimal)]
		[XmlElement(Order = 28)]
		public string TITOLO
		{
			get { return titolo; }
			set { titolo = value; }
		}

		string tipoanagrafe=null;
		/// <summary>
		/// Specifica se l'anagrafica è una persona fisica (F) o giuridica (G)
		/// </summary>
		[DataField("TIPOANAGRAFE",Size=1, Type=DbType.String, CaseSensitive=false)]
		[isRequired]
		[XmlElement(Order = 29)]
		public string TIPOANAGRAFE
		{
			get { return tipoanagrafe; }
			set { tipoanagrafe = value; }
		}

        DateTime? datanominativo = null;
		/// <summary>
		/// Data di costituzione (solo per persone giuridiche)
		/// </summary>
		[DataField("DATANOMINATIVO", Type=DbType.DateTime)]
		[XmlElement(Order = 30)]
		public DateTime? DATANOMINATIVO
		{
			get { return datanominativo; }
            set { datanominativo = VerificaDataLocale(value); }
		}

		string invioemailtec=null;
		/// <summary>
		/// Flag che identifica se l'anagrafica deve ricevere comunicazione relative all'istanza (valido solo se tecnico)
		/// </summary>
		[DataField("INVIOEMAILTEC", Type=DbType.Decimal)]
		[XmlElement(Order = 31)]
		public string INVIOEMAILTEC
		{
			get { return invioemailtec; }
			set { invioemailtec = value; }
		}

		string codicecittadinanza=null;
		/// <summary>
		/// Codice della cittadinanza (fk su CITTADINANZA.CODICE)
		/// </summary>
		[DataField("CODICECITTADINANZA", Type=DbType.Decimal)]
		[XmlElement(Order = 32)]
		public string CODICECITTADINANZA
		{
			get { return codicecittadinanza; }
			set { codicecittadinanza = value; }
		}

		string comuneresidenza=null;
		/// <summary>
		/// Codice del comune di residenza (fk su COMUNI.CODICECOMUNE)
		/// </summary>
		[DataField("COMUNERESIDENZA", Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 33)]
		public string COMUNERESIDENZA
		{
			get { return comuneresidenza; }
			set { comuneresidenza = value; }
		}

		string password=null;
		/// <summary>
		/// Hash della password dell'anagrafica
		/// </summary>
		[DataField("PASSWORD",Size=8, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 34)]
		public string PASSWORD
		{
			get { return password; }
			set { password = value; }
		}

		string indirizzocorrispondenza=null;
		/// <summary>
		/// Indirizzo di corrispondenza
		/// </summary>
		[DataField("INDIRIZZOCORRISPONDENZA",Size=80, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 35)]
		public string INDIRIZZOCORRISPONDENZA
		{
			get { return indirizzocorrispondenza; }
			set { indirizzocorrispondenza = value; }
		}

		string cittacorrispondenza=null;
		/// <summary>
		/// Città dell'indirizzo di corrispondenza
		/// </summary>
		[DataField("CITTACORRISPONDENZA",Size=50, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 36)]
		public string CITTACORRISPONDENZA
		{
			get { return cittacorrispondenza; }
			set { cittacorrispondenza = value; }
		}

		string capcorrispondenza=null;
		/// <summary>
		/// Cap dell'indirizzo di corrispondenza
		/// </summary>
		[DataField("CAPCORRISPONDENZA",Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 37)]
		public string CAPCORRISPONDENZA
		{
			get { return capcorrispondenza; }
			set { capcorrispondenza = value; }
		}

		string provinciacorrispondenza=null;
		/// <summary>
		/// Sigla della provincia dell'indirizzo di corrispondenza (fk su COMUNI.SIGLAPROVINCIA)
		/// </summary>
		[DataField("PROVINCIACORRISPONDENZA",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 38)]
		public string PROVINCIACORRISPONDENZA
		{
			get { return provinciacorrispondenza; }
			set { provinciacorrispondenza = value; }
		}

		string comunecorrispondenza=null;
		/// <summary>
		/// Codice del comune dell indirizzo di corrispondenza (fk su COMUNI.CODICECOMUNE)
		/// </summary>
		[DataField("COMUNECORRISPONDENZA", Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 39)]
		public string COMUNECORRISPONDENZA
		{
			get { return comunecorrispondenza; }
			set { comunecorrispondenza = value; }
		}

		string provinciarea=null;
		/// <summary>
		/// Profincia di iscrizione al REA
		/// </summary>
		[DataField("PROVINCIAREA",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 40)]
		public string PROVINCIAREA
		{
			get { return provinciarea; }
			set { provinciarea = value; }
		}

		string numiscrrea=null;
		/// <summary>
		/// Numero di iscrizione al REA
		/// </summary>
		[DataField("NUMISCRREA",Size=10, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 41)]
		public string NUMISCRREA
		{
			get { return numiscrrea; }
			set { numiscrrea = value; }
		}

        DateTime? dataiscrrea = null;
		/// <summary>
		/// Data di iscrizione al REA
		/// </summary>
		[DataField("DATAISCRREA", Type=DbType.DateTime)]
		[XmlElement(Order = 42)]
		public DateTime? DATAISCRREA
		{
			get { return dataiscrrea; }
            set { dataiscrrea = VerificaDataLocale(value); }
		}

		string flag_noprofit=null;
		/// <summary>
		/// Dismesso (identifica de l'aziena è un ente no profit)
		/// </summary>
		[DataField("FLAG_NOPROFIT", Type=DbType.Decimal)]
		[XmlElement(Order = 43)]
		public string FLAG_NOPROFIT
		{
			get { return flag_noprofit; }
			set { flag_noprofit = value; }
		}

		string flag_disabilitato=null;
		/// <summary>
		/// Flag che identifica se l'anagrafica è disabilitata (0 = non disabilitata, 1 = disabilitata)
		/// </summary>
		[DataField("FLAG_DISABILITATO", Type=DbType.Decimal)]
		[isRequired]
		[XmlElement(Order = 44)]
		public string FLAG_DISABILITATO
		{
			get { return flag_disabilitato; }
			set { flag_disabilitato = value; }
		}

        DateTime? data_disabilitato = null;
		/// <summary>
		/// Data in cui l'anagrafica è stata disabilitata (se FLAG_DISABILITATO == 1)
		/// </summary>
		[DataField("DATA_DISABILITATO", Type=DbType.DateTime)]
		[XmlElement(Order = 45)]
		public DateTime? DATA_DISABILITATO
		{
			get { return data_disabilitato; }
            set { data_disabilitato = VerificaDataLocale(value); }
		}

		/// <summary>
		/// Nel caso in cui venga usato un sistema di SSO o l'accesso con CIE è l'username su cui viene effettuata l'autenticazione
		/// </summary>
		[DataField("STRONG_AUTH_ID", Size = 250, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 46)]
		public string Username{get;set;}

		string codiceelencopro=null;
		/// <summary>
		/// Numero di iscrizione all'albo professionale (solo per tecnici)
		/// </summary>
		[DataField("CODICEELENCOPRO", Type=DbType.Int64)]
		[XmlElement(Order = 47)]
		public string CODICEELENCOPRO
		{
			get { return codiceelencopro; }
			set { codiceelencopro = value; }
		}
		string numeroelencopro=null;
		/// <summary>
		/// Numero di registrazione all'albo professionale
		/// </summary>
		[DataField("NUMEROELENCOPRO",Size=10, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 48)]
		public string NUMEROELENCOPRO
		{
			get { return numeroelencopro; }
			set { numeroelencopro = value; }
		}
		string provinciaelencopro=null;
		/// <summary>
		/// Provincia di registrazione all'albo professionale
		/// </summary>
		[DataField("PROVINCIAELENCOPRO",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 49)]
		public string PROVINCIAELENCOPRO
		{
			get { return provinciaelencopro; }
			set { provinciaelencopro = value; }
        }

		/// <summary>
		/// INdirizzo PEC per le comunicazioni
		/// </summary>
		[DataField("PEC", Size = 280, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 50)]
		public string Pec{get;set;}

		/// <summary>
		/// Flag che identifica se l'utente può visualizzare l'intero albero degli interventi nel FO
		/// </summary>
		[DataField("FO_UTENTETESTER", Type = DbType.Decimal)]
		[XmlElement(Order = 51)]
		public int? FoUtenteTester
		{
			get;
			set;
		}
        #endregion

        protected Titoli titoloclass = null;
		/// <summary>
		/// Risoluzione della foreign key TITOLO
		/// </summary>
        [ForeignKey("IDCOMUNE,TITOLO", "IDCOMUNE,CODICETITOLO")]
		[XmlElement(Order = 52)]
        public Titoli TitoloClass
        {
            get { return titoloclass; }
            set { titoloclass = value; }
        }

        protected ElenchiProfessionaliBase elencoprofessionale = null;
		/// <summary>
		/// Risoluzione della foreign key CODICEELENCOPRO (albo professionale)
		/// </summary>
		[ForeignKey("CODICEELENCOPRO", "EpId")]
		[XmlElement(Order = 53)]
        public ElenchiProfessionaliBase ElencoProfessionale
        {
            get { return elencoprofessionale; }
            set { elencoprofessionale = value; }
        }

        protected FormeGiuridiche formagiuridicaclass = null;
		/// <summary>
		/// Risoluzione della foreign key FORMAGIURIDICA (tipo di forma giuridica)
		/// </summary>
        [ForeignKey("IDCOMUNE,FORMAGIURIDICA","IDCOMUNE,CODICEFORMAGIURIDICA")]
		[XmlElement(Order = 54)]
        public FormeGiuridiche FormaGiuridicaClass
        {
            get { return formagiuridicaclass; }
            set { formagiuridicaclass = value; }
        }

        #region Arraylist per gli inserimenti nelle tabelle collegate

        List<AnagrafeDocumenti> _AnagrafeDocumenti = new List<AnagrafeDocumenti>();
		/// <summary>
		/// Utilizzato internamente (documenti dell'anagrafica)
		/// </summary>
		[XmlElement(Order = 55)]
        public List<AnagrafeDocumenti> AnagrafeDocumenti
		{
			get { return _AnagrafeDocumenti; }
			set { _AnagrafeDocumenti = value; }
		}

        List<AnagrafeDyn2ModelliT> m_AnagrafeDyn2ModelliT = new List<AnagrafeDyn2ModelliT>();
		/// <summary>
		/// Modelli dinamici associati all'anagrafica (uso interno)
		/// </summary>
        [ForeignKey("IDCOMUNE,CODICEANAGRAFE", "Idcomune,Codiceanagrafe")]
		[XmlElement(Order = 56)]
		public List<AnagrafeDyn2ModelliT> AnagrafeDyn2ModelliT
        {
            get { return m_AnagrafeDyn2ModelliT; }
            set { m_AnagrafeDyn2ModelliT = value; }
        }

        List<AnagrafeDyn2Dati> m_anagrafedyn2dati = new List<AnagrafeDyn2Dati>();
		/// <summary>
		/// Dati dinamici associati all'anagrafica (uso interno)
		/// </summary>
        [ForeignKey("IDCOMUNE,CODICEANAGRAFE", "Idcomune,Codiceanagrafe")]
		[XmlElement(Order = 57)]
        public List<AnagrafeDyn2Dati> AnagrafeDyn2Dati
        {
            get { return m_anagrafedyn2dati; }
            set { m_anagrafedyn2dati = value; }
        } 

        Comuni m_comunenascita = null;
		/// <summary>
		/// Risoluzione della FK CODCOMNASCITA (dati del comune di nascita)
		/// </summary>
		[ForeignKey("CODCOMNASCITA", "CODICECOMUNE")]
		[XmlElement(Order = 58)]
        public Comuni ComuneNascita
        {
            get { return m_comunenascita; }
            set { m_comunenascita = value; }
        }

        Comuni m_comuneregditte = null;
		/// <summary>
		/// Uso interno
		/// </summary>
		[XmlElement(Order = 59)]
        public Comuni ComuneRegDitte
        {
            get { return m_comuneregditte; }
            set { m_comuneregditte = value; }
        }

        Comuni m_comuneregtrib = null;
		/// <summary>
		/// Uso interno
		/// </summary>
		[XmlElement(Order = 60)]
        public Comuni ComuneRegTrib
        {
            get { return m_comuneregtrib; }
            set { m_comuneregtrib = value; }
        }

        Comuni m_comunecorrispondenza = null;
		/// <summary>
		/// Uso interno
		/// </summary>
		[XmlElement(Order = 61)]
		public Comuni ComuneCorrispondenza
        {
            get { return m_comunecorrispondenza; }
            set { m_comunecorrispondenza = value; }
        }

        Comuni m_comuneresidenza = null;
		/// <summary>
		/// Risoluzione della FK COMUNERESIDENZA (dati del comune di residenza)
		/// </summary>
		[ForeignKey("COMUNERESIDENZA", "CODICECOMUNE")]
		[XmlElement(Order = 62)]
        public Comuni ComuneResidenza
        {
            get { return m_comuneresidenza; }
            set { m_comuneresidenza = value; }
        }

        Cittadinanza m_cittadinanza = null;
		/// <summary>
		/// Uso interno
		/// </summary>
		[XmlElement(Order = 63)]
		public Cittadinanza Cittadinanza
        {
            get { return m_cittadinanza; }
            set { m_cittadinanza = value; }
        }

        List<MercatiPresenzeStorico> m_presenzestoriche = new List<MercatiPresenzeStorico>();
		/// <summary>
		/// Uso interno (storico presenze nei mercati)
		/// </summary>
        [ForeignKey("IDCOMUNE,CODICEANAGRAFE", "Idcomune,Codiceanagrafe")]
		[XmlElement(Order = 64)]
        public List<MercatiPresenzeStorico> PresenzeStoriche
        {
            get { return m_presenzestoriche; }
            set { m_presenzestoriche = value; }
        } 


		#endregion

        #region inps e inail
        [KeyField("INAIL_MATRICOLA", Size = 16, Type = DbType.String)]
        [XmlElement(Order = 65)]
        public string InpsMatricola { get; set; }

        [KeyField("INAIL_CODICESEDE", Size = 6, Type = DbType.String)]
        [XmlElement(Order = 66)]
        public string InpsCodiceSede { get; set; }

        [KeyField("INPS_MATRICOLA", Size = 16, Type = DbType.String)]
        [XmlElement(Order = 67)]
        public string InailMatricola { get; set; }

        [KeyField("INPS_CODICESEDE", Size = 16, Type = DbType.String)]
        [XmlElement(Order = 68)]
        public string InailCodiceSede { get; set; }

        ElencoInpsBase _sedeInps = new ElencoInpsBase();
        ElencoInailBase _sedeInail = new ElencoInailBase();

        [ForeignKey("InpsCodiceSede", "Codice")]
        [XmlElement(Order = 69)]
        public ElencoInpsBase SedeInps
        {
            get { return _sedeInps; }
            set { _sedeInps = value; }
        }

        [ForeignKey("InailCodiceSede", "Codice")]
        [XmlElement(Order = 70)]
        public ElencoInailBase SedeInail
        {
            get { return _sedeInail; }
            set { _sedeInail = value; }
        }
        #endregion

        /// <summary>
		/// Restituisce il nome completo dell'anagrafica nel formato "NOME COGNOME [CODICEFISCALE PARTITAIVA]"
		/// </summary>
		/// <returns></returns>
		public override string ToString()
		{
			StringBuilder sb = new StringBuilder( GetNomeCompleto() );

			sb.Append(" (");

			if (!String.IsNullOrEmpty(this.CODICEFISCALE))
				sb.Append(" ").Append(this.CODICEFISCALE);

			if (!String.IsNullOrEmpty(this.PARTITAIVA))
				sb.Append(" ").Append(this.PARTITAIVA);

			sb.Append(" ) - [P").Append(this.TIPOANAGRAFE).Append("]");

			return sb.ToString();
		}

		public string ToErrorString( string propName )
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

			PropertyInfo pi = classtype.GetProperty( propName );

			if ( pi != null )
			{
				sb.Append( pi.Name );
				sb.Append( "=" );

				object value = pi.GetValue( this , null );	

				if ( value == null )
					sb.Append("null");
				else
				{
					sb.Append("\"");
					sb.Append( value.ToString() );
					sb.Append("\"");
				}
				sb.Append("; ");
			}

			sb.Append( "\n " );

			return sb.ToString();
		}

		/// <summary>
		/// Ottiene il nome completo dell'anagrafica nella forma "COGNOME NOME"
		/// </summary>
		/// <returns></returns>
		public string GetNomeCompleto()
		{
			StringBuilder sb = new StringBuilder();

			sb.Append(this.NOMINATIVO);

			if (!String.IsNullOrEmpty(this.NOME))
				sb.Append(" ").Append(this.NOME);

			return sb.ToString();
		}
	}
}
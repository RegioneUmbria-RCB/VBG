using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("RESPONSABILI")]
	[Serializable]
	public class Responsabili : BaseDataClass
	{

		#region Key Fields

		/// <summary>
		/// Chiave primaria della tabella insieme a IDCOMUNE
		/// </summary>
		[useSequence]
		[KeyField("CODICERESPONSABILE", Type=DbType.Decimal)]
		[XmlElement(Order=0)]
		public string CODICERESPONSABILE{get;set;}

		/// <summary>
		/// Chiave primaria della tabella insieme a CODICERESPONSABILE
		/// </summary>
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		[XmlElement(Order = 1)]
		public string IDCOMUNE{get;set;}

		#endregion

		/// <summary>
		/// Nominativo del responsabile 
		/// </summary>
		[DataField("RESPONSABILE",Size=60, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 2)]
		public string RESPONSABILE{get;set;}

		/// <summary>
		/// Titolo del responsabile (sig., dott. etc etc)
		/// </summary>
		[DataField("TITOLO",Size=6, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 3)]
		public string TITOLO{get;set;}

		
		[DataField("QUALIFICA",Size=50, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 4)]
		public string QUALIFICA{get;set;}

		/// <summary>
		/// Data di nascita
		/// </summary>
        DateTime? datanascita = null;
		[DataField("DATANASCITA", Type=DbType.DateTime)]
		[XmlElement(Order = 5)]
		public DateTime? DATANASCITA
		{
			get { return datanascita; }
            set { datanascita = VerificaDataLocale(value); }
		}

		/// <summary>
		/// Codice fiscale
		/// </summary>
		[DataField("CODICEFISCALE",Size=16, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 6)]
		public string CODICEFISCALE{get;set;}

		
		/// <summary>
		/// Indirizzo
		/// </summary>
		[DataField("INDIRIZZO",Size=50, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 7)]
		public string INDIRIZZO{get;set;}

		/// <summary>
		/// Città
		/// </summary>
		[DataField("CITTA",Size=50, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 8)]
		public string CITTA{get;set;}

		/// <summary>
		/// CAP
		/// </summary>
		[DataField("CAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 9)]
		public string CAP{get;set;}

		/// <summary>
		/// Provincia
		/// </summary>
		[DataField("PROVINCIA",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 10)]
		public string PROVINCIA{get;set;}

		/// <summary>
		/// Telefono ufficio
		/// </summary>
		[DataField("TELEFONOLAVORO",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 11)]
		public string TELEFONOLAVORO{get;set;}

		/// <summary>
		/// Telefono privato
		/// </summary>
		[DataField("TELEFONOABITAZIONE",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 12)]
		public string TELEFONOABITAZIONE{get;set;}

		/// <summary>
		/// Cellulare
		/// </summary>
		[DataField("TELEFONOCELLULARE",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 13)]
		public string TELEFONOCELLULARE{get;set;}

		/// <summary>
		/// Fax
		/// </summary>
		[DataField("FAX",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 14)]
		public string FAX{get;set;}

		/// <summary>
		/// Flag che identifica se l'utente è amministratore
		/// </summary>
		[DataField("AMMINISTRATORE",Size=1, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 15)]
		public string AMMINISTRATORE{get;set;}

		/// <summary>
		/// Password (hash)
		/// </summary>
		[DataField("PASSWORD",Size=10, Type=DbType.String, CaseSensitive=true)]
		[XmlElement(Order = 16)]
		public string PASSWORD{get;set;}

		string scadenzario=null;
		[DataField("SCADENZARIO", Type=DbType.Decimal)]
		[XmlElement(Order = 17)]
		public string SCADENZARIO
		{
			get { return scadenzario; }
			set { scadenzario = value; }
		}

		string numggscadenz=null;
		[DataField("NUMGGSCADENZ", Type=DbType.Decimal)]
		[XmlElement(Order = 18)]
		public string NUMGGSCADENZ
		{
			get { return numggscadenz; }
			set { numggscadenz = value; }
		}

		string scadenzarioprec=null;
		[DataField("SCADENZARIOPREC", Type=DbType.Decimal)]
		[XmlElement(Order = 19)]
		public string SCADENZARIOPREC
		{
			get { return scadenzarioprec; }
			set { scadenzarioprec = value; }
		}

		string mps_codiceoperatore=null;
		[DataField("MPS_CODICEOPERATORE",Size=20, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 20)]
		public string MPS_CODICEOPERATORE
		{
			get { return mps_codiceoperatore; }
			set { mps_codiceoperatore = value; }
		}

		string userId=null;
		[DataField("USERID",Size=15, Type=DbType.String, CaseSensitive=true)]
		[XmlElement(Order = 21)]
		public string USERID
		{
			get { return userId; }
			set { userId = value; }
		}


		string email=null;
		[DataField("EMAIL",Size=80, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 22)]
		public string EMAIL
		{
			get { return email; }
			set { email = value; }
		}

		string filtrooperatorescadenz=null;
		[DataField("FILTROOPERATORESCADENZ", Type=DbType.Decimal)]
		[XmlElement(Order = 23)]
		public string FILTROOPERATORESCADENZ
		{
			get { return filtrooperatorescadenz; }
			set { filtrooperatorescadenz = value; }
		}

		string amministratoresoftware=null;
		[DataField("AMMINISTRATORESOFTWARE",Size=1, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 24)]
		public string AMMINISTRATORESOFTWARE
		{
			get { return amministratoresoftware; }
			set { amministratoresoftware = value; }
		}

		string updatehelp=null;
		[DataField("UPDATEHELP", Type=DbType.Decimal)]
		[XmlElement(Order = 25)]
		public string UPDATEHELP
		{
			get { return updatehelp; }
			set 
            { 
                //imposto un valore di default sul set della proprietà se value è null.
                //non lo faccio sul get perchè in update potrei aver bisogno di sapere che è null
                updatehelp = value==null ? "0" : value; 
            }
		}

		string fkidprofiloassegnazione=null;
		[DataField("FKIDPROFILOASSEGNAZIONE", Type=DbType.Decimal)]
		[XmlElement(Order = 26)]
		public string FKIDPROFILOASSEGNAZIONE
		{
			get { return fkidprofiloassegnazione; }
			set { fkidprofiloassegnazione = value; }
		}

		string flag_notificaassegnazprot=null;
		[DataField("FLAG_NOTIFICAASSEGNAZPROT", Type=DbType.Decimal)]
		[XmlElement(Order = 27)]
		public string FLAG_NOTIFICAASSEGNAZPROT
		{
			get { return flag_notificaassegnazprot; }
			set { flag_notificaassegnazprot = value; }
		}

		string fkidtiporesponsabile=null;
		[DataField("FKIDTIPORESPONSABILE", Type=DbType.Decimal)]
		[XmlElement(Order = 28)]
		public string FKIDTIPORESPONSABILE
		{
			get { return fkidtiporesponsabile; }
			set { fkidtiporesponsabile = value; }
		}

		string flag_gestione_oneri=null;
		[DataField("FLAG_GESTIONE_ONERI", Type=DbType.Int16)]
		[XmlElement(Order = 29)]
		public string FLAG_GESTIONE_ONERI
		{
			get { return flag_gestione_oneri; }
			set { flag_gestione_oneri = value; }
		}

		string _readonly = null;
		[DataField("READONLY", Type=DbType.Decimal)]
		[XmlElement(Order = 30)]
		public string READONLY
		{
			get { return _readonly; }
			set { _readonly = value; }
		}

		string matricola=null;
		[DataField("MATRICOLA",Size=20, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 31)]
		public string MATRICOLA
		{
			get { return matricola; }
			set { matricola = value; }
		}

        /// <summary>
		/// Flag utente disabilitato
		/// </summary>
		[DataField("DISABILITATO", Type=DbType.Decimal)]
		[XmlElement(Order = 32)]
		public string DISABILITATO{get;set;}

		string flag_modifica_numist=null;
		[DataField("FLAG_MODIFICA_NUMIST", Type=DbType.Decimal)]
		[XmlElement(Order = 33)]
		public string FLAG_MODIFICA_NUMIST
		{
			get { return flag_modifica_numist; }
			set { flag_modifica_numist = value; }
		}

        [DataField("COD_UTE_DOCER", Size = 30, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 34)]
        public string COD_UTE_DOCER { get; set; }

        [DataField("PASSWORD_UTE_DOCER", Size = 30, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 35)]
        public string PASSWORD_UTE_DOCER { get; set; }

		public override string ToString()
		{
			return RESPONSABILE;
		}
	}
}
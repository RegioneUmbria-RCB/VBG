using System;
using System.Data;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{

	// ATTENZIONE!!!
	// Le proprietà della classe vengono utilizzate tramite reflection nella classe ConfigurazioneMgr
	// dando per assunto che tutte siano di tipo string.
	// Se la classe viene rigenerata con i nuovi generatori occorre modificare il metodo 
	// GetByIdComplesso di ConfigurazioneMgr
	//
	//
	[DataTable("CONFIGURAZIONE")]
	[Serializable]
	public class Configurazione : BaseDataClass
	{

		#region Key Fields
		[XmlElement(Order=0)]
		[KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
		public string IDCOMUNE { get; set; }

		[XmlElement(Order = 1)]
		[KeyField("SOFTWARE", Size = 2, Type = DbType.String)]
		public string SOFTWARE { get; set; }

		#endregion

		[XmlElement(Order = 2)]
		[DataField("DENOMINAZIONE", Size = 200, Type = DbType.String, CaseSensitive = false)]
		public string DENOMINAZIONE { get; set; }

		[XmlElement(Order = 3)]
		[DataField("INDIRIZZO", Size = 60, Type = DbType.String, CaseSensitive = false)]
		public string INDIRIZZO { get; set; }

		[XmlElement(Order = 4)]
		[DataField("CAP", Size = 5, Type = DbType.String, CaseSensitive = false)]
		public string CAP { get; set; }

		[XmlElement(Order = 5)]
		[DataField("COMUNE", Size = 60, Type = DbType.String, CaseSensitive = false)]
		public string COMUNE { get; set; }

		[XmlElement(Order = 6)]
		[DataField("PROVINCIA", Size = 2, Type = DbType.String, CaseSensitive = false)]
		public string PROVINCIA { get; set; }
		
		[XmlElement(Order = 7)]
		[DataField("EMAIL", Size = 60, Type = DbType.String, CaseSensitive = false)]
		public string EMAIL { get; set; }

		[XmlElement(Order = 8)]
		[DataField("RESPONSABILE", Size = 60, Type = DbType.String, CaseSensitive = false)]
		public string RESPONSABILE { get; set; }

		[XmlElement(Order = 9)]
		[DataField("EMAILRESPONSABILE", Size = 60, Type = DbType.String, CaseSensitive = false)]
		public string EMAILRESPONSABILE { get; set; }

		[XmlElement(Order = 10)]
		[DataField("EMAILRESPONSABILEPEC", Size = 60, Type = DbType.String, CaseSensitive = false)]
		public string EmailResponsabilePec { get; set; }
		/*
		[DataField("BNA", Size = 100, Type = DbType.String, CaseSensitive = false)]
		public string BNA { get; set; }

		[DataField("MAILSERVER", Size = 80, Type = DbType.String, CaseSensitive = false)]
		public string MAILSERVER { get; set; }
		*/

		[XmlElement(Order = 11)]
		[DataField("ORARIO", Size = 500, Type = DbType.String, CaseSensitive = false)]
		public string ORARIO { get; set; }

		//[DataField("PASSWORDAUTOMATICA", Type = DbType.Decimal)]
		//public string PASSWORDAUTOMATICA { get; set; }

		//[DataField("INFO_OPERATORE", Type = DbType.Decimal)]
		//public string INFO_OPERATORE { get; set; }

		//[DataField("INFO_RICHIEDENTE", Type = DbType.Decimal)]
		//public string INFO_RICHIEDENTE { get; set; }

		//[DataField("INFO_DATA", Type = DbType.Decimal)]
		//public string INFO_DATA { get; set; }

		//[DataField("INFO_INTERVENTO", Type = DbType.Decimal)]
		//public string INFO_INTERVENTO { get; set; }

		//[DataField("INFO_PROCEDURA", Type = DbType.Decimal)]
		//public string INFO_PROCEDURA { get; set; }

		//[DataField("INFO_LAVORI", Type = DbType.Decimal)]
		//public string INFO_LAVORI { get; set; }

		//[DataField("INFO_FOGLIO", Type = DbType.Decimal)]
		//public string INFO_FOGLIO { get; set; }

		//[DataField("INFO_PARTICELLA", Type = DbType.Decimal)]
		//public string INFO_PARTICELLA { get; set; }

		//[DataField("INFO_SUB", Type = DbType.Decimal)]
		//public string INFO_SUB { get; set; }

		[XmlElement(Order = 12)]
		[DataField("TELEFONO", Size = 30, Type = DbType.String, CaseSensitive = false)]
		public string TELEFONO { get; set; }

		[XmlElement(Order = 13)]
		[DataField("FAX", Size = 30, Type = DbType.String, CaseSensitive = false)]
		public string FAX { get; set; }

		//[DataField("VALIDNEWS", Type = DbType.Decimal)]
		//public string VALIDNEWS { get; set; }

		//[DataField("PUBBLICAISTANZE", Type = DbType.Decimal)]
		//public string PUBBLICAISTANZE { get; set; }

		//[DataField("ADVAMM", Type = DbType.Decimal)]
		//public string ADVAMM { get; set; }

		//[DataField("INFO_STATO", Type = DbType.Decimal)]
		//public string INFO_STATO { get; set; }

		//[DataField("IDCONSMINISTRI", Size = 6, Type = DbType.String, CaseSensitive = false)]
		//public string IDCONSMINISTRI { get; set; }

		//[DataField("GGTRASMNEGATIVA", Type = DbType.Decimal)]
		//public string GGTRASMNEGATIVA { get; set; }

		//[DataField("IDCDSVPR", Size = 6, Type = DbType.String, CaseSensitive = false)]
		//public string IDCDSVPR { get; set; }

		//[DataField("IDCDSVPRCH", Size = 6, Type = DbType.String, CaseSensitive = false)]
		//public string IDCDSVPRCH { get; set; }

		[XmlElement(Order = 14)]
		[DataField("DESCRIZIONE", Size = 4000, Type = DbType.String, CaseSensitive = false)]
		public string DESCRIZIONE { get; set; }

		[XmlElement(Order = 15)]
		[DataField("CHIUSURA", Size = 4000, Type = DbType.String, CaseSensitive = false)]
		public string CHIUSURA { get; set; }

		//[DataField("PERCNONPAGATA", Type = DbType.Decimal)]
		//public double? PERCNONPAGATA { get; set; }

		//[DataField("NUMERAZIONE", Type = DbType.Decimal)]
		//public string NUMERAZIONE { get; set; }

		//[DataField("VISELAB", Type = DbType.Decimal)]
		//public string VISELAB { get; set; }

		//[DataField("GGDOPORITNEG", Type = DbType.Decimal)]
		//public string GGDOPORITNEG { get; set; }

		//[DataField("AGGAUTOENDO", Type = DbType.Decimal)]
		//public string AGGAUTOENDO { get; set; }

		//[DataField("CODFISOBBLIG", Type = DbType.Decimal)]
		//public string CODFISOBBLIG { get; set; }

		//[DataField("ATTIVACONTATORE", Size = 1, Type = DbType.String, CaseSensitive = false)]
		//public string ATTIVACONTATORE { get; set; }

		//[DataField("MAIL_AMMENDO", Type = DbType.Decimal)]
		//public string MAIL_AMMENDO { get; set; }

		//[DataField("MAIL_MOVIRIC", Type = DbType.Decimal)]
		//public string MAIL_MOVIRIC { get; set; }

		//[DataField("MAIL_MOVIAMM", Type = DbType.Decimal)]
		//public string MAIL_MOVIAMM { get; set; }

		//[DataField("MAIL_MOVINEG", Type = DbType.Decimal)]
		//public string MAIL_MOVINEG { get; set; }

		//[DataField("INFO_VPRG", Type = DbType.Decimal)]
		//public string INFO_VPRG { get; set; }

		//[DataField("INFO_VIA", Type = DbType.Decimal)]
		//public string INFO_VIA { get; set; }

		//[DataField("INFO_ISS", Type = DbType.Decimal)]
		//public string INFO_ISS { get; set; }

		//[DataField("INFO_PROGRESSIVO", Type = DbType.Decimal)]
		//public string INFO_PROGRESSIVO { get; set; }

		//[DataField("ORD_TEMPIFICAZIONE", Type = DbType.Decimal)]
		//public string ORD_TEMPIFICAZIONE { get; set; }

		//[DataField("ORD_ENDO", Type = DbType.Decimal)]
		//public string ORD_ENDO { get; set; }

		//[DataField("ORDVISENDO", Type = DbType.Decimal)]
		//public string ORDVISENDO { get; set; }

		[XmlElement(Order = 16)]
		[DataField("SCRITTAREGIONE", Size = 100, Type = DbType.String, CaseSensitive = false)]
		public string SCRITTAREGIONE { get; set; }

		[XmlElement(Order = 17)]
		[DataField("LOGOREGIONE", Size = 50, Type = DbType.String, CaseSensitive = false)]
		public string LOGOREGIONE { get; set; }

		[XmlElement(Order = 18)]
		[DataField("LOGOAZIENDA", Size = 50, Type = DbType.String, CaseSensitive = false)]
		public string LOGOAZIENDA { get; set; }

		[XmlElement(Order = 19)]
		[DataField("URLLOGOUT", Size = 100, Type = DbType.String, CaseSensitive = false)]
		public string URLLOGOUT { get; set; }

		//[DataField("INFO_CODICEISTANZA", Type = DbType.Decimal)]
		//public string INFO_CODICEISTANZA { get; set; }

		//[DataField("INFO_CODICEAREA", Type = DbType.Decimal)]
		//public string INFO_CODICEAREA { get; set; }

		[XmlElement(Order = 20)]
		[DataField("CODAMMSPORTELLOUNICO", Type = DbType.Decimal)]
		public string CODAMMSPORTELLOUNICO { get; set; }

		[XmlElement(Order = 21)]
		[DataField("CODICETUTTEAMMINISTRAZIONI", Type = DbType.Decimal)]
		public string CODICETUTTEAMMINISTRAZIONI { get; set; }

		//[DataField("ESCLUDIGG1", Type = DbType.Decimal)]
		//public string ESCLUDIGG1 { get; set; }

		//[DataField("ESCLCONTGG1", Type = DbType.Decimal)]
		//public string ESCLCONTGG1 { get; set; }

		//[DataField("ESCLUDIGG2", Type = DbType.Decimal)]
		//public string ESCLUDIGG2 { get; set; }

		//[DataField("ESCLCONTGG2", Type = DbType.Decimal)]
		//public string ESCLCONTGG2 { get; set; }

		//[DataField("NUMGGCDSVPR", Type = DbType.Decimal)]
		//public string NUMGGCDSVPR { get; set; }

		//[DataField("IDENDOVIANAZIONALE", Type = DbType.Decimal)]
		//public string IDENDOVIANAZIONALE { get; set; }

		//[DataField("IDENDOVIAREGIONALE", Type = DbType.Decimal)]
		//public string IDENDOVIAREGIONALE { get; set; }

		//[DataField("IDENDOVIACOMUNALE", Type = DbType.Decimal)]
		//public string IDENDOVIACOMUNALE { get; set; }

		//[DataField("CODICEOGGETTO2", Type = DbType.Decimal)]
		//public string CODICEOGGETTO2 { get; set; }

		//[DataField("CODICEOGGETTO4", Type = DbType.Decimal)]
		//public string CODICEOGGETTO4 { get; set; }

		//[DataField("CODICEOGGETTOSTILI", Type = DbType.Decimal)]
		//public string CODICEOGGETTOSTILI { get; set; }

		//[DataField("FO_NUMISTANZEPERPAGINA", Type = DbType.Decimal)]
		//public string FO_NUMISTANZEPERPAGINA { get; set; }

		//[DataField("INFO_FILTROISTANZE", Type = DbType.Decimal)]
		//public string INFO_FILTROISTANZE { get; set; }

		//[DataField("INFO_MOVIMENTO1", Size = 6, Type = DbType.String, CaseSensitive = false)]
		//public string INFO_MOVIMENTO1 { get; set; }

		//[DataField("INFO_MOVIMENTO2", Size = 6, Type = DbType.String, CaseSensitive = false)]
		//public string INFO_MOVIMENTO2 { get; set; }

		//[DataField("INFO_STRADARIO", Type = DbType.Decimal)]
		//public string INFO_STRADARIO { get; set; }

		[XmlElement(Order = 22)]
		[DataField("TIPO_LOGIN", Type = DbType.Decimal)]
		public string TIPO_LOGIN { get; set; }

		//[DataField("PROTGENOBBLIG", Type = DbType.Decimal)]
		//public string PROTGENOBBLIG { get; set; }

		//[DataField("CONTATORE", Type = DbType.Decimal)]
		//public string CONTATORE { get; set; }

		//[DataField("SE_FIRMADIGITALE", Size = 1, Type = DbType.String, CaseSensitive = false)]
		//public string SE_FIRMADIGITALE { get; set; }

		[XmlElement(Order = 23)]
		[DataField("VERSIONE", Size = 15, Type = DbType.String, CaseSensitive = false)]
		public string VERSIONE { get; set; }

		[XmlElement(Order = 24)]
		[DataField("CODICELASTESSAAMMINISTRAZIONE", Type = DbType.Decimal)]
		public string CODICELASTESSAAMMINISTRAZIONE { get; set; }

		[XmlElement(Order = 25)]
		[DataField("CODICEOGGETTOMODDOCTIPO", Type = DbType.Decimal)]
		public string CODICEOGGETTOMODDOCTIPO { get; set; }

		//[DataField("PROGRESSIVOISTANZE", Size = 15, Type = DbType.String, CaseSensitive = false)]
		//public string PROGRESSIVOISTANZE { get; set; }

		//[DataField("CODLETTSCADANAGRAFICA", Type = DbType.Decimal)]
		//public string CODLETTSCADANAGRAFICA { get; set; }

		//[DataField("TURBOGAS", Type = DbType.Decimal)]
		//public string TURBOGAS { get; set; }

		//[DataField("GGTURBOGAS", Type = DbType.Decimal)]
		//public string GGTURBOGAS { get; set; }

		//[DataField("IDPARERECOMMISSIONEEDILIZIA", Size = 6, Type = DbType.String, CaseSensitive = false)]
		//public string IDPARERECOMMISSIONEEDILIZIA { get; set; }

		//[DataField("FLAG_RICHIEDENTEPF", Type = DbType.Decimal)]
		//public string FLAG_RICHIEDENTEPF { get; set; }

		//[DataField("FLAG_AZIENDARAPPRESENTATA", Type = DbType.Decimal)]
		//public string FLAG_AZIENDARAPPRESENTATA { get; set; }

		//[DataField("USADOCTIPO_TT", Type = DbType.Decimal)]
		//public string USADOCTIPO_TT { get; set; }

		//[DataField("FLAG_VIETAINSSTRDAISTANZE", Type = DbType.Decimal)]
		//public string FLAG_VIETAINSSTRDAISTANZE { get; set; }

		//[DataField("USAATTIVITA_TT", Type = DbType.Decimal)]
		//public string USAATTIVITA_TT { get; set; }

		//[DataField("FLAG_DATANUOVOMOV", Type = DbType.Decimal)]
		//public string FLAG_DATANUOVOMOV { get; set; }

		[XmlElement(Order = 26)]
		[DataField("FLAG_ATTIVACONTATOREALBEROPROC", Type = DbType.Decimal)]
		public string FLAG_ATTIVACONTATOREALBEROPROC { get; set; }

		[XmlElement(Order = 27)]
		[DataField("FLAG_NO_NUMEROISTANZA", Type = DbType.Decimal)]
		public string FLAG_NO_NUMEROISTANZA { get; set; }

		[XmlElement(Order = 28)]
		[DataField("CODICERESPONSABILE", Type = DbType.Decimal)]
		public string CODICERESPONSABILE { get; set; }

		[XmlElement(Order = 29)]
		[DataField("FLAG_PROTGENMODIF", Type = DbType.Decimal)]
		public string FLAG_PROTGENMODIF { get; set; }

		[XmlElement(Order = 30)]
		[DataField("FLAG_PROTGENINSER", Type = DbType.Decimal)]
		public string FLAG_PROTGENINSER { get; set; }

		[XmlElement(Order = 31)]
		[DataField("ESTREMIBOLLOVIRTUALE", Size = 1000, Type = DbType.String, CaseSensitive = false)]
		public string ESTREMIBOLLOVIRTUALE { get; set; }

		[XmlElement(Order = 32)]
		[DataField("CODICE_ACCREDITAMENTO", Size = 30, Type = DbType.String, CaseSensitive = false)]
		public string CodiceAccreditamento { get; set; }

		[XmlElement(Order = 33)]
		[ForeignKey("IDCOMUNE,CODICERESPONSABILE", "IDCOMUNE,CODICERESPONSABILE")]
		public Responsabili ResponsabileSportello { get; set; }
	}
}
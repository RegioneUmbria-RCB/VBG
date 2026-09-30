using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("TIPISOGGETTO")]
	[Serializable]
	public class TipiSoggetto : BaseDataClass
	{

		#region Key Fields

		/// <summary>
		/// Id del tipo soggetto, fa chiave con IDCOMUNE
		/// </summary>
		[useSequence]
		[KeyField("CODICETIPOSOGGETTO", Type=DbType.Decimal)]
		[XmlElement(Order=0)]
		public string CODICETIPOSOGGETTO{get;set;}

		/// <summary>
		/// Id Comune, fa chiave con CODICETIPOSOGGETTO
		/// </summary>
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		[XmlElement(Order = 1)]
		public string IDCOMUNE{get;set;}

		#endregion

		/// <summary>
		/// Descrizione del tipo soggetto
		/// </summary>
		[DataField("TIPOSOGGETTO",Size=128, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 2)]
		public string TIPOSOGGETTO{get;set;}

		
		[DataField("FLAGQUALITA", Type=DbType.Decimal)]
		[XmlElement(Order = 3)]
		public string FLAGQUALITA{get;set;}

		/// <summary>
		/// Modulo software a cui appartiene il tipo soggetto
		/// </summary>
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		[XmlElement(Order = 4)]
		public string SOFTWARE{get;set;}

		/// <summary>
		/// Tipo di anagrafica a cui può essere assegnato il tipo sogetto (F=fisica, G=giuridica)
		/// </summary>
		[DataField("TIPOANAGRAFE",Size=1, Type=DbType.String)]
		[XmlElement(Order = 5)]
		public string TIPOANAGRAFE{get;set;}

		
		[DataField("UTILIZZO",Size=1, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 6)]
		public string UTILIZZO{get;set;}


		/// <summary>
		/// Tipo di dato che il soggetto rappresenta (T=Tecnico, R=Richiedente, A=Azienda)
		/// </summary>
		[DataField("TIPODATO",Size=1, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 7)]
		public string TIPODATO{get;set;}


		/// <summary>
		/// Flag che indica se il campo è obbligatorio quando viene usato nel FO. Se cioè nella domanda deve essere presente almeno un'anagrafica
		/// con questo tipo soggetto
		/// </summary>
		[DataField("FO_OBBLIGATORIO", Type=DbType.Decimal)]
		[XmlElement(Order = 8)]
		public string FO_OBBLIGATORIO{get;set;}

		/// <summary>
		/// Flag che specifica se all'anagrafica con questo tipo soggetto deve essere collegata un'altra anagrafica.
		/// vd. tipo soggetto "Legale rappresentante dell'azienda"
		/// </summary>
		[DataField("RICHIEDIANAGRAFECOLL", Type=DbType.Decimal)]
		[XmlElement(Order = 9)]
		public string RICHIEDIANAGRAFECOLL{get;set;}

		/// <summary>
		/// Flag che specifica se nel FO devono essere inseriti i dati dell'albo
		/// </summary>
		[DataField("FLG_DATIALBO", Type = DbType.Decimal)]
		[XmlElement(Order = 10)]
		public string FLG_DATIALBO{get;set;}

		/// <summary>
		/// Flag che specifica se nel FO deve essere inserita una descrizione estesa del soggetto (vd. "Altro (specificare...)")
		/// </summary>
		[DataField("FLG_SPECIFICADESCRIZIONE", Type = DbType.Decimal)]
		[XmlElement(Order = 11)]
		public string FLG_SPECIFICADESCRIZIONE{get;set;}

		/// <summary>
		/// Flag che identifica se il tipo soggetto rappresenta il legale rappresentante di un'azienda
		/// </summary>
		[DataField("FLG_LEGALERAP", Type = DbType.Decimal)]
		[XmlElement(Order = 12)]
		public int? FlgLegaleRapp{get;set;}

        /// <summary>
        /// Chiave esterna verso la tabella RI_CARICHE che serve per la mappatura delle cariche (in qualità di) con applicazioni terze (ad esempio per generare file xml di Impresa in un giorno verso halley).
        /// </summary>
        [DataField("FK_RICA_CODICE", Type = DbType.String)]
        [XmlElement(Order = 13)]
        public string FK_RICA_CODICE { get; set; }


		public override string ToString()
		{
			return TIPOSOGGETTO;
		}
	}
}
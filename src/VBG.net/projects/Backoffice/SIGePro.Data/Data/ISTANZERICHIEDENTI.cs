using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZERICHIEDENTI")]
	[Serializable]
	public class IstanzeRichiedenti : BaseDataClass
	{

		#region Key Fields

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		[XmlElement(Order = 0)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceinvitato=null;
		[useSequence]
		[KeyField("CODICEINVITATO", Type=DbType.Decimal, KeyIdentity=true)]
		[XmlElement(Order = 1)]
		public string CODICEINVITATO
		{
			get { return codiceinvitato; }
			set { codiceinvitato = value; }
		}

		#endregion

		string codiceistanza=null;
		[isRequired]
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		[XmlElement(Order = 2)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string codicerichiedente=null;
		[isRequired]
		[DataField("CODICERICHIEDENTE", Type=DbType.Decimal)]
		[XmlElement(Order = 3)]
		public string CODICERICHIEDENTE
		{
			get { return codicerichiedente; }
			set { codicerichiedente = value; }
		}


		string codicetiposoggetto=null;
		[isRequired]
		[DataField("CODICETIPOSOGGETTO", Type=DbType.Decimal)]
		[XmlElement(Order = 4)]
		public string CODICETIPOSOGGETTO
		{
			get { return codicetiposoggetto; }
			set { codicetiposoggetto = value; }
		}

		string codiceanagrafecoll=null;
		[DataField("CODICEANAGRAFECOLL", Type=DbType.Decimal)]
		[XmlElement(Order = 5)]
		public string CODICEANAGRAFECOLL
		{
			get { return codiceanagrafecoll; }
			set { codiceanagrafecoll = value; }
		}

		string descrsoggetto = null;
		[DataField("DESCRSOGGETTO", Type = DbType.String, Size = 128, CaseSensitive = false)]
		[XmlElement(Order = 6)]
		public string DESCRSOGGETTO
		{
			get { return descrsoggetto; }
			set { descrsoggetto = value; }
		}

		int? m_codiceProcuratore = null;
		[DataField("CODICEPROCURATORE", Type = DbType.Decimal)]
		[XmlElement(Order = 7)]
		public int? Codiceprocuratore
		{
			get { return m_codiceProcuratore; }
			set { m_codiceProcuratore = value; }
		}

		[DataField("CODICEOGGETTO_PROCURA", Type = DbType.Decimal)]
		[XmlElement(Order = 8)]
		public int? CodiceoggettoProcura
		{
			get;
			set;
		}



		#region Arraylist per gli inserimenti nelle tabelle collegate
		private TipiSoggetto m_tipoSoggetto;

		[ForeignKey("IDCOMUNE, CODICETIPOSOGGETTO", "IDCOMUNE, CODICETIPOSOGGETTO")]
		[XmlElement(Order = 9)]
		public TipiSoggetto TipoSoggetto
		{
			get { return m_tipoSoggetto; }
			set { m_tipoSoggetto = value; }
		}


		private Anagrafe m_procuratore;
		[ForeignKey("IDCOMUNE, Codiceprocuratore", "IDCOMUNE, CODICEANAGRAFE")]
		[XmlElement(Order = 10)]
		public Anagrafe Procuratore
		{
			get { return m_procuratore; }
			set { m_procuratore = value; }
		}


        Anagrafe m_richiedente;
        [ForeignKey("IDCOMUNE, CODICERICHIEDENTE", "IDCOMUNE, CODICEANAGRAFE")]
		[XmlElement(Order = 11)]
        public Anagrafe Richiedente
        {
            get { return m_richiedente; }
            set { m_richiedente = value; }
        }
        
		AnagrafeDocumentiCollection _AnagrafeDocumenti = new AnagrafeDocumentiCollection();
		[XmlElement(Order = 12)]
		public AnagrafeDocumentiCollection AnagrafeDocumenti
		{
			get { return _AnagrafeDocumenti; }
			set { _AnagrafeDocumenti = value; }
		}

        Anagrafe m_anagrafecollegata;
        [ForeignKey("IDCOMUNE, CODICEANAGRAFECOLL", "IDCOMUNE, CODICEANAGRAFE")]
		[XmlElement(Order = 13)]
        public Anagrafe AnagrafeCollegata
        {
            get { return m_anagrafecollegata; }
            set { m_anagrafecollegata = value; }
        }
		#endregion

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order = 14)]
        [ForeignKey("IDCOMUNE,CodiceoggettoProcura", "IDCOMUNE,CODICEOGGETTO")]
        public Oggetti OggettoProcura
        {
            get;
            set;
        }
	}
}
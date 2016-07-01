using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZESTRADARIO")]
	[Serializable]
	public class IstanzeStradario : BaseDataClass
	{

		#region Key Fields

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		[XmlElement(Order=0)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		[XmlElement(Order = 1)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
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

		
		string codicestradario=null;
		[isRequired]
		[DataField("CODICESTRADARIO", Type=DbType.Decimal)]
		[XmlElement(Order = 3)]
		public string CODICESTRADARIO
		{
			get { return codicestradario; }
			set { codicestradario = value; }
		}

		string civico=null;
		[DataField("CIVICO",Size=25, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 4)]
		public string CIVICO
		{
			get { return civico; }
			set { civico = value; }
		}

		string colore=null;
		[DataField("COLORE",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 5)]
		public string COLORE
		{
			get { return colore; }
			set { colore = value; }
		}

		string note=null;
		[DataField("NOTE",Size=80, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 6)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string primario=null;
		[isRequired]
		[DataField("PRIMARIO", Type=DbType.Decimal)]
		[XmlElement(Order = 7)]
		public string PRIMARIO
		{
			get { return primario; }
			set { primario = value; }
		}

		string frazione=null;
		[DataField("FRAZIONE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		[XmlElement(Order = 8)]
		public string FRAZIONE
		{
			get { return frazione; }
			set { frazione = value; }
		}

		string circoscrizione=null;
		[DataField("CIRCOSCRIZIONE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		[XmlElement(Order = 9)]
		public string CIRCOSCRIZIONE
		{
			get { return circoscrizione; }
			set { circoscrizione = value; }
		}

		string cap=null;
		[DataField("CAP",Size=6, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 10)]
		public string CAP
		{
			get { return cap; }
			set { cap = value; }
		}

        string fkidmappale = null;
        [DataField("FKIDMAPPALE", Type = DbType.Decimal)]
		[XmlElement(Order = 11)]
        public string FKIDMAPPALE
        {
            get { return fkidmappale; }
            set { fkidmappale = value; }
        }

        string esponente = null;
        [DataField("ESPONENTE", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 12)]
        public string ESPONENTE
        {
            get { return esponente; }
            set { esponente = value; }
        }

        string scala = null;
        [DataField("SCALA", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 13)]
        public string SCALA
        {
            get { return scala; }
            set { scala = value; }
        }

        string interno = null;
        [DataField("INTERNO", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 14)]
        public string INTERNO
        {
            get { return interno; }
            set { interno = value; }
        }

        string esponenteinterno = null;
        [DataField("ESPONENTEINTERNO", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 15)]
        public string ESPONENTEINTERNO
        {
            get { return esponenteinterno; }
            set { esponenteinterno = value; }
        }

        string fabbricato = null;
        [DataField("FABBRICATO", Size = 30, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 16)]
        public string FABBRICATO
        {
            get { return fabbricato; }
            set { fabbricato = value; }
        }

        string codicecivico = null;
        [DataField("CODICECIVICO", Size = 30, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 17)]
        public string CODICECIVICO
        {
            get { return codicecivico; }
            set { codicecivico = value; }
        }

		[DataField("PIANO", Size = 30, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 18)]
		public string Piano
		{
			get;
			set;
		}

		#region Foreign keys e Arraylist per gli inserimenti nelle tabelle collegate

		Stradario m_stradario;
		[ForeignKey("IDCOMUNE, CODICESTRADARIO", "IDCOMUNE, CODICESTRADARIO")]
		[XmlElement(Order = 19)]
		public Stradario Stradario
		{
			get { return m_stradario; }
			set { m_stradario = value; }
		}
		#endregion

		[DataField("ID_PUNTO_SIT", Size = 50, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 20)]
		public string IdPuntoSit { get; set; }

		[DataField("KM", Size = 10, Type = DbType.String)]
		[XmlElement(Order = 21)]
		public string Km { get; set; }

		[DataField("UUID", Size = 50, Type = DbType.String)]
		[XmlElement(Order = 22)]
		public string Uuid { get; set; }

		[KeyField("TIPOLOCALIZZAZIONE_ID", Type = DbType.Decimal)]
		[XmlElement(Order = 23)]
		public int? TipolocalizzazioneId { get; set; }

		[KeyField("LONGITUDINE", Type = DbType.String, Size=50)]
		[XmlElement(Order = 24)]
		public string Longitudine { get; set; }

		[KeyField("LATITUDINE", Type = DbType.String, Size = 50)]
		[XmlElement(Order = 25)]
		public string Latitudine { get; set; }

		[ForeignKey("IDCOMUNE, TipolocalizzazioneId", "IdComune, Id")]
		[XmlElement(Order = 26)]
		public TipiLocalizzazioni TipoLocalizzazione { get; set; }



		public override string ToString()
		{
			StringBuilder sb = new StringBuilder();

			if (Stradario == null) return base.ToString();

			if (!String.IsNullOrEmpty(Stradario.PREFISSO))
				sb.Append(Stradario.PREFISSO).Append(" ");

			sb.Append(Stradario.DESCRIZIONE).Append(" ");
			
			if (!String.IsNullOrEmpty(CIVICO))
				sb.Append( CIVICO ).Append( ", " );
			
			sb.Append(CAP).Append(" ");

			if (!String.IsNullOrEmpty(Stradario.LOCFRAZ))
				sb.Append(Stradario.LOCFRAZ);
			else
			{
				if (Stradario.Comune != null)
					sb.Append(Stradario.Comune.COMUNE);
			}

			if (Stradario.Comune != null)
				sb.Append(" ").Append(Stradario.Comune.SIGLAPROVINCIA);

			return sb.ToString();
		}
	}
}
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZESTRADARIO")]
    [Serializable]
    [DataContract]
    public class IstanzeStradario : BaseDataClass
    {

        #region Key Fields

        private string id = null;
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string ID
        {
            get { return this.id; }
            set { this.id = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
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

        private string codicestradario = null;
        [isRequired]
        [DataField("CODICESTRADARIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string CODICESTRADARIO
        {
            get { return this.codicestradario; }
            set { this.codicestradario = value; }
        }

        private string civico = null;
        [DataField("CIVICO", Size = 25, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string CIVICO
        {
            get { return this.civico; }
            set { this.civico = value; }
        }

        private string colore = null;
        [DataField("COLORE", Size = 2, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 5)]
        public string COLORE
        {
            get { return this.colore; }
            set { this.colore = value; }
        }

        private string note = null;
        [DataField("NOTE", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string NOTE
        {
            get { return this.note; }
            set { this.note = value; }
        }

        private string primario = null;
        [isRequired]
        [DataField("PRIMARIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 7)]
        public string PRIMARIO
        {
            get { return this.primario; }
            set { this.primario = value; }
        }

        private string frazione = null;
        [DataField("FRAZIONE", Size = 50, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string FRAZIONE
        {
            get { return this.frazione; }
            set { this.frazione = value; }
        }

        private string circoscrizione = null;
        [DataField("CIRCOSCRIZIONE", Size = 50, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string CIRCOSCRIZIONE
        {
            get { return this.circoscrizione; }
            set { this.circoscrizione = value; }
        }

        private string cap = null;
        [DataField("CAP", Size = 6, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 10)]
        public string CAP
        {
            get { return this.cap; }
            set { this.cap = value; }
        }

        private string fkidmappale = null;
        [DataField("FKIDMAPPALE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 11)]
        public string FKIDMAPPALE
        {
            get { return this.fkidmappale; }
            set { this.fkidmappale = value; }
        }

        private string esponente = null;
        [DataField("ESPONENTE", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 12)]
        public string ESPONENTE
        {
            get { return this.esponente; }
            set { this.esponente = value; }
        }

        private string scala = null;
        [DataField("SCALA", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 13)]
        public string SCALA
        {
            get { return this.scala; }
            set { this.scala = value; }
        }

        private string interno = null;
        [DataField("INTERNO", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 14)]
        public string INTERNO
        {
            get { return this.interno; }
            set { this.interno = value; }
        }

        private string esponenteinterno = null;
        [DataField("ESPONENTEINTERNO", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 15)]
        public string ESPONENTEINTERNO
        {
            get { return this.esponenteinterno; }
            set { this.esponenteinterno = value; }
        }

        private string fabbricato = null;
        [DataField("FABBRICATO", Size = 30, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 16)]
        public string FABBRICATO
        {
            get { return this.fabbricato; }
            set { this.fabbricato = value; }
        }

        private string codicecivico = null;
        [DataField("CODICECIVICO", Size = 30, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 17)]
        public string CODICECIVICO
        {
            get { return this.codicecivico; }
            set { this.codicecivico = value; }
        }

        [DataField("PIANO", Size = 30, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 18)]
        public string Piano
        {
            get;
            set;
        }

        #region Foreign keys e Arraylist per gli inserimenti nelle tabelle collegate

        private Stradario m_stradario;
        [ForeignKey("IDCOMUNE, CODICESTRADARIO", "IDCOMUNE, CODICESTRADARIO")]
        [DataMember]
        [XmlElement(Order = 19)]
        public Stradario Stradario
        {
            get { return this.m_stradario; }
            set { this.m_stradario = value; }
        }
        #endregion

        [DataField("ID_PUNTO_SIT", Size = 50, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 20)]
        public string IdPuntoSit { get; set; }

        [DataField("KM", Size = 10, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 21)]
        public string Km { get; set; }

        [DataField("UUID", Size = 50, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 22)]
        public string Uuid { get; set; }

        [DataField("TIPOLOCALIZZAZIONE_ID", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 23)]
        public int? TipolocalizzazioneId { get; set; }

        [DataField("LONGITUDINE", Type = DbType.String, Size = 50)]
        [DataMember]
        [XmlElement(Order = 24)]
        public string Longitudine { get; set; }

        [DataField("LATITUDINE", Type = DbType.String, Size = 50)]
        [DataMember]
        [XmlElement(Order = 25)]
        public string Latitudine { get; set; }

        [ForeignKey("IDCOMUNE, TipolocalizzazioneId", "IdComune, Id")]
        [DataMember]
        [XmlElement(Order = 26)]
        public TipiLocalizzazioni TipoLocalizzazione { get; set; }

        //[Obsolete("Usato solo per compatibilità con le formule di frontoffice. NON USARE DIRETTAMENTE!!!!")]
        [DataMember]
        [XmlElement(Order = 27)]
        public Comuni ComuneLocalizzazione { get; set; }

        public override string ToString()
        {
            StringBuilder sb = new StringBuilder();

            if (this.Stradario == null) return base.ToString();

            if (!String.IsNullOrEmpty(this.Stradario.PREFISSO))
            {
                sb.Append(this.Stradario.PREFISSO);
            }

            sb.Append(" ").Append(this.Stradario.DESCRIZIONE);

            if (!String.IsNullOrEmpty(this.CIVICO))
            {
                sb.Append(" ").Append(this.CIVICO);

                if (!String.IsNullOrEmpty(this.ESPONENTE))
                    sb.Append($"/{this.ESPONENTE}");
            }

            if (!String.IsNullOrEmpty(this.CAP))
            {
                sb.Append(" ").Append(this.CAP);
            }

            if (!String.IsNullOrEmpty(this.Stradario.LOCFRAZ))
            {
                sb.Append(" ").Append(this.Stradario.LOCFRAZ);
            }


            if (this.Stradario.Comune != null)
            {
                sb.Append(", ").Append(this.Stradario.Comune.COMUNE);

                sb.Append(" (").Append(this.Stradario.Comune.SIGLAPROVINCIA).Append(")");
            }

            return sb.ToString();
        }
    }
}
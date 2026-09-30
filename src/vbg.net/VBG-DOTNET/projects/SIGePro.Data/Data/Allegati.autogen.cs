
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella ALLEGATI il 30/11/2010 17.27.04
    ///
    ///												ATTENZIONE!!!
    ///	- Specificare manualmente in quali colonne vanno applicate eventuali sequenze		
    /// - Verificare l'applicazione di eventuali attributi di tipo "[isRequired]". In caso contrario applicarli manualmente
    ///	- Verificare che il tipo di dati assegnato alle proprietà sia corretto
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///
    [DataTable("ALLEGATI")]
    [Serializable]
    [DataContract]
    public partial class Allegati : BaseDataClass
    {
        #region Membri privati

        private int? m_codiceinventario = null;

        private int? m_numeroallegato = null;

        private string m_allegato = null;

        private int? m_amministrazione = null;

        private string m_modello = null;

        private double? m_costo = null;

        private string m_indirizzoweb = null;

        private int? m_codiceoggetto = null;

        private string m_idcomune = null;

        private int? m_pubblica = null;

        private int? m_richiesto = null;

        private int? m_ordine = null;

        private int? m_fo_richiedefirma = null;

        private string m_fo_tipodownload = null;

        private int? m_id = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        [XmlElement(Order = 1)]
        public int? Id
        {
            get { return this.m_id; }
            set { this.m_id = value; }
        }


        #endregion

        #region Data fields

        [DataField("CODICEINVENTARIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 2)]
        public int? Codiceinventario
        {
            get { return this.m_codiceinventario; }
            set { this.m_codiceinventario = value; }
        }

        [DataField("NUMEROALLEGATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 3)]
        public int? Numeroallegato
        {
            get { return this.m_numeroallegato; }
            set { this.m_numeroallegato = value; }
        }

        [DataField("ALLEGATO", Type = DbType.String, CaseSensitive = false, Size = 512)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string Allegato
        {
            get { return this.m_allegato; }
            set { this.m_allegato = value; }
        }

        [DataField("AMMINISTRAZIONE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 5)]
        public int? Amministrazione
        {
            get { return this.m_amministrazione; }
            set { this.m_amministrazione = value; }
        }

        [DataField("MODELLO", Type = DbType.String, CaseSensitive = false, Size = 255)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string Modello
        {
            get { return this.m_modello; }
            set { this.m_modello = value; }
        }

        [DataField("COSTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 7)]
        public double? Costo
        {
            get { return this.m_costo; }
            set { this.m_costo = value; }
        }

        [DataField("INDIRIZZOWEB", Type = DbType.String, CaseSensitive = false, Size = 200)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string Indirizzoweb
        {
            get { return this.m_indirizzoweb; }
            set { this.m_indirizzoweb = value; }
        }

        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 9)]
        public int? Codiceoggetto
        {
            get { return this.m_codiceoggetto; }
            set { this.m_codiceoggetto = value; }
        }

        [DataField("PUBBLICA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 10)]
        public int? Pubblica
        {
            get { return this.m_pubblica; }
            set { this.m_pubblica = value; }
        }

        [DataField("RICHIESTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 11)]
        public int? Richiesto
        {
            get { return this.m_richiesto; }
            set { this.m_richiesto = value; }
        }

        [DataField("ORDINE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 12)]
        public int? Ordine
        {
            get { return this.m_ordine; }
            set { this.m_ordine = value; }
        }

        [DataField("FO_RICHIEDEFIRMA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 13)]
        public int? FoRichiedefirma
        {
            get { return this.m_fo_richiedefirma; }
            set { this.m_fo_richiedefirma = value; }
        }

        [DataField("FO_TIPODOWNLOAD", Type = DbType.String, CaseSensitive = false, Size = 30)]
        [DataMember]
        [XmlElement(Order = 14)]
        public string FoTipodownload
        {
            get { return this.m_fo_tipodownload; }
            set { this.m_fo_tipodownload = value; }
        }

        [DataField("NOTE_FRONTEND", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        [DataMember]
        [XmlElement(Order = 15)]
        public string NoteFrontend
        {
            get;
            set;
        }
        [DataField("FO_DIMENSIONE_MASSIMA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 18)]
        public int? FoDimensioneMassima { get; set; }

        [DataField("FO_ESTENSIONI_AMMESSE", Type = DbType.String, CaseSensitive = false, Size = 256)]
        [DataMember]
        [XmlElement(Order = 19)]
        public string FoEstensioniAmmesse { get; set; }

        #endregion

        #endregion
    }
}

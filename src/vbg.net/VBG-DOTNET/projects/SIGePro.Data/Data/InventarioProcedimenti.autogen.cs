
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella INVENTARIOPROCEDIMENTI il 05/11/2008 11.16.35
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
    [DataTable("INVENTARIOPROCEDIMENTI")]
    [Serializable]
    [DataContract]
    public partial class InventarioProcedimenti : BaseDataClass
    {
        #region Membri privati

        private int? m_codiceinventario = null;

        private string m_procedimento = null;

        private string m_datigenerali = null;

        private int? m_amministrazione = null;

        private DateTime? m_dataaggiornamento = null;

        private int? m_tempificazione = null;

        private string m_campoapplicazione = null;

        private string m_normativaue = null;

        private string m_normativana = null;

        private string m_normativare = null;

        private string m_regolamenti = null;

        private string m_adempimenti = null;

        private int? m_codicetipo = null;

        private int? m_collaudo = null;

        private int? m_perprovvedimento = null;

        private int? m_disabilitato = null;

        private int? m_ordine = null;

        private double? m_dirittiistruttoria = null;

        private int? m_codiceufficio = null;

        private string m_tipomovimento = null;

        private int? m_codicenatura = null;

        private int? m_noneseguecontromovobblig = null;

        private string m_software = null;

        private string m_idcomune = null;

        private string m_codiceancitel = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("CODICEINVENTARIO", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        [XmlElement(Order = 0)]
        public int? Codiceinventario
        {
            get { return this.m_codiceinventario; }
            set { this.m_codiceinventario = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("PROCEDIMENTO", Type = DbType.String, CaseSensitive = false, Size = 255)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string Procedimento
        {
            get { return this.m_procedimento; }
            set { this.m_procedimento = value; }
        }

        [DataField("DATIGENERALI", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string Datigenerali
        {
            get { return this.m_datigenerali; }
            set { this.m_datigenerali = value; }
        }

        [DataField("AMMINISTRAZIONE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 4)]
        public int? Amministrazione
        {
            get { return this.m_amministrazione; }
            set { this.m_amministrazione = value; }
        }

        [DataField("DATAAGGIORNAMENTO", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 5)]
        public DateTime? Dataaggiornamento
        {
            get { return this.m_dataaggiornamento; }
            set { this.m_dataaggiornamento = this.VerificaDataLocale(value); }
        }

        [DataField("TEMPIFICAZIONE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 6)]
        public int? Tempificazione
        {
            get { return this.m_tempificazione; }
            set { this.m_tempificazione = value; }
        }

        [DataField("CAMPOAPPLICAZIONE", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [DataMember]
        [XmlElement(Order = 7)]
        public string Campoapplicazione
        {
            get { return this.m_campoapplicazione; }
            set { this.m_campoapplicazione = value; }
        }

        [DataField("NORMATIVAUE", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string Normativaue
        {
            get { return this.m_normativaue; }
            set { this.m_normativaue = value; }
        }

        [DataField("NORMATIVANA", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string Normativana
        {
            get { return this.m_normativana; }
            set { this.m_normativana = value; }
        }

        [DataField("NORMATIVARE", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [DataMember]
        [XmlElement(Order = 10)]
        public string Normativare
        {
            get { return this.m_normativare; }
            set { this.m_normativare = value; }
        }

        [DataField("REGOLAMENTI", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [DataMember]
        [XmlElement(Order = 11)]
        public string Regolamenti
        {
            get { return this.m_regolamenti; }
            set { this.m_regolamenti = value; }
        }

        [DataField("ADEMPIMENTI", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [DataMember]
        [XmlElement(Order = 12)]
        public string Adempimenti
        {
            get { return this.m_adempimenti; }
            set { this.m_adempimenti = value; }
        }

        [DataField("CODICETIPO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 13)]
        public int? Codicetipo
        {
            get { return this.m_codicetipo; }
            set { this.m_codicetipo = value; }
        }

        [DataField("COLLAUDO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 14)]
        public int? Collaudo
        {
            get { return this.m_collaudo; }
            set { this.m_collaudo = value; }
        }

        [DataField("PERPROVVEDIMENTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 15)]
        public int? Perprovvedimento
        {
            get { return this.m_perprovvedimento; }
            set { this.m_perprovvedimento = value; }
        }

        [DataField("DISABILITATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 16)]
        public int? Disabilitato
        {
            get { return this.m_disabilitato; }
            set { this.m_disabilitato = value; }
        }

        [DataField("ORDINE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 17)]
        public int? Ordine
        {
            get { return this.m_ordine; }
            set { this.m_ordine = value; }
        }

        [DataField("DIRITTIISTRUTTORIA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 18)]
        public double? Dirittiistruttoria
        {
            get { return this.m_dirittiistruttoria; }
            set { this.m_dirittiistruttoria = value; }
        }

        [DataField("CODICEUFFICIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 19)]
        public int? Codiceufficio
        {
            get { return this.m_codiceufficio; }
            set { this.m_codiceufficio = value; }
        }

        [DataField("TIPOMOVIMENTO", Type = DbType.String, CaseSensitive = false, Size = 8)]
        [DataMember]
        [XmlElement(Order = 20)]
        public string Tipomovimento
        {
            get { return this.m_tipomovimento; }
            set { this.m_tipomovimento = value; }
        }

        [DataField("CODICENATURA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 21)]
        public int? Codicenatura
        {
            get { return this.m_codicenatura; }
            set { this.m_codicenatura = value; }
        }

        [DataField("NONESEGUECONTROMOVOBBLIG", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 22)]
        public int? Noneseguecontromovobblig
        {
            get { return this.m_noneseguecontromovobblig; }
            set { this.m_noneseguecontromovobblig = value; }
        }

        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        [DataMember]
        [XmlElement(Order = 23)]
        public string Software
        {
            get { return this.m_software; }
            set { this.m_software = value; }
        }

        [DataField("CODICEANCITEL", Type = DbType.String, CaseSensitive = false, Size = 15)]
        [DataMember]
        [XmlElement(Order = 24)]
        public string Codiceancitel
        {
            get { return this.m_codiceancitel; }
            set { this.m_codiceancitel = value; }
        }


        [DataField("FLAGTIPITITOLO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 28)]
        public int? FlagTipiTitolo
        {
            get;
            set;
        }

        #endregion

        #endregion
    }
}


using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;
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
        [XmlElement(Order = 0)]
        public int? Codiceinventario
        {
            get { return m_codiceinventario; }
            set { m_codiceinventario = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [XmlElement(Order = 1)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("PROCEDIMENTO", Type = DbType.String, CaseSensitive = false, Size = 255)]
        [XmlElement(Order = 2)]
        public string Procedimento
        {
            get { return m_procedimento; }
            set { m_procedimento = value; }
        }

        [DataField("DATIGENERALI", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [XmlElement(Order = 3)]
        public string Datigenerali
        {
            get { return m_datigenerali; }
            set { m_datigenerali = value; }
        }

        [DataField("AMMINISTRAZIONE", Type = DbType.Decimal)]
        [XmlElement(Order = 4)]
        public int? Amministrazione
        {
            get { return m_amministrazione; }
            set { m_amministrazione = value; }
        }

        [DataField("DATAAGGIORNAMENTO", Type = DbType.DateTime)]
        [XmlElement(Order = 5)]
        public DateTime? Dataaggiornamento
        {
            get { return m_dataaggiornamento; }
            set { m_dataaggiornamento = VerificaDataLocale(value); }
        }

        [DataField("TEMPIFICAZIONE", Type = DbType.Decimal)]
        [XmlElement(Order = 6)]
        public int? Tempificazione
        {
            get { return m_tempificazione; }
            set { m_tempificazione = value; }
        }

        [DataField("CAMPOAPPLICAZIONE", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [XmlElement(Order = 7)]
        public string Campoapplicazione
        {
            get { return m_campoapplicazione; }
            set { m_campoapplicazione = value; }
        }

        [DataField("NORMATIVAUE", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [XmlElement(Order = 8)]
        public string Normativaue
        {
            get { return m_normativaue; }
            set { m_normativaue = value; }
        }

        [DataField("NORMATIVANA", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [XmlElement(Order = 9)]
        public string Normativana
        {
            get { return m_normativana; }
            set { m_normativana = value; }
        }

        [DataField("NORMATIVARE", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [XmlElement(Order = 10)]
        public string Normativare
        {
            get { return m_normativare; }
            set { m_normativare = value; }
        }

        [DataField("REGOLAMENTI", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [XmlElement(Order = 11)]
        public string Regolamenti
        {
            get { return m_regolamenti; }
            set { m_regolamenti = value; }
        }

        [DataField("ADEMPIMENTI", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [XmlElement(Order = 12)]
        public string Adempimenti
        {
            get { return m_adempimenti; }
            set { m_adempimenti = value; }
        }

        [DataField("CODICETIPO", Type = DbType.Decimal)]
        [XmlElement(Order = 13)]
        public int? Codicetipo
        {
            get { return m_codicetipo; }
            set { m_codicetipo = value; }
        }

        [DataField("COLLAUDO", Type = DbType.Decimal)]
        [XmlElement(Order = 14)]
        public int? Collaudo
        {
            get { return m_collaudo; }
            set { m_collaudo = value; }
        }

        [DataField("PERPROVVEDIMENTO", Type = DbType.Decimal)]
        [XmlElement(Order = 15)]
        public int? Perprovvedimento
        {
            get { return m_perprovvedimento; }
            set { m_perprovvedimento = value; }
        }

        [DataField("DISABILITATO", Type = DbType.Decimal)]
        [XmlElement(Order = 16)]
        public int? Disabilitato
        {
            get { return m_disabilitato; }
            set { m_disabilitato = value; }
        }

        [DataField("ORDINE", Type = DbType.Decimal)]
        [XmlElement(Order = 17)]
        public int? Ordine
        {
            get { return m_ordine; }
            set { m_ordine = value; }
        }

        [DataField("DIRITTIISTRUTTORIA", Type = DbType.Decimal)]
        [XmlElement(Order = 18)]
        public double? Dirittiistruttoria
        {
            get { return m_dirittiistruttoria; }
            set { m_dirittiistruttoria = value; }
        }

        [DataField("CODICEUFFICIO", Type = DbType.Decimal)]
        [XmlElement(Order = 19)]
        public int? Codiceufficio
        {
            get { return m_codiceufficio; }
            set { m_codiceufficio = value; }
        }

        [DataField("TIPOMOVIMENTO", Type = DbType.String, CaseSensitive = false, Size = 8)]
        [XmlElement(Order = 20)]
        public string Tipomovimento
        {
            get { return m_tipomovimento; }
            set { m_tipomovimento = value; }
        }

        [DataField("CODICENATURA", Type = DbType.Decimal)]
        [XmlElement(Order = 21)]
        public int? Codicenatura
        {
            get { return m_codicenatura; }
            set { m_codicenatura = value; }
        }

        [DataField("NONESEGUECONTROMOVOBBLIG", Type = DbType.Decimal)]
        [XmlElement(Order = 22)]
        public int? Noneseguecontromovobblig
        {
            get { return m_noneseguecontromovobblig; }
            set { m_noneseguecontromovobblig = value; }
        }

        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        [XmlElement(Order = 23)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [DataField("CODICEANCITEL", Type = DbType.String, CaseSensitive = false, Size = 15)]
        [XmlElement(Order = 24)]
        public string Codiceancitel
        {
            get { return m_codiceancitel; }
            set { m_codiceancitel = value; }
        }


        [DataField("FLAGTIPITITOLO", Type = DbType.Decimal)]
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

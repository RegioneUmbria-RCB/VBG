
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella CDS il 30/07/2008 15.50.44
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
    [DataTable("CDS")]
    [Serializable]
    public partial class Cds : BaseDataClass
    {
        #region Membri privati


        private int? m_codiceistanza = null;

        private int? m_codiceatto = null;

        private DateTime? m_dataconvocazione = null;

        private string m_oraconvocazione = null;

        private DateTime? m_dataconvocazione2 = null;

        private string m_oraconvocazione2 = null;

        private string m_odg = null;

        private string m_note = null;

        private int? m_invitorichiedente = null;

        private int? m_flagvia = null;

        private string m_idcomune = null;

        private int? m_idtestata = null;

        private int? m_codicemovimento = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("CODICEISTANZA", Type = DbType.Decimal)]
        public int? Codiceistanza
        {
            get { return m_codiceistanza; }
            set { m_codiceistanza = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("IDTESTATA", Type = DbType.Decimal)]
        public int? Idtestata
        {
            get { return m_idtestata; }
            set { m_idtestata = value; }
        }


        #endregion

        #region Data fields

        [DataField("CODICEATTO", Type = DbType.Decimal)]
        public int? Codiceatto
        {
            get { return m_codiceatto; }
            set { m_codiceatto = value; }
        }

        [DataField("DATACONVOCAZIONE", Type = DbType.DateTime)]
        public DateTime? Dataconvocazione
        {
            get { return m_dataconvocazione; }
            set { m_dataconvocazione = VerificaDataLocale(value); }
        }

        [DataField("ORACONVOCAZIONE", Type = DbType.String, CaseSensitive = false, Size = 15)]
        public string Oraconvocazione
        {
            get { return m_oraconvocazione; }
            set { m_oraconvocazione = value; }
        }

        [DataField("DATACONVOCAZIONE2", Type = DbType.DateTime)]
        public DateTime? Dataconvocazione2
        {
            get { return m_dataconvocazione2; }
            set { m_dataconvocazione2 = VerificaDataLocale(value); }
        }

        [DataField("ORACONVOCAZIONE2", Type = DbType.String, CaseSensitive = false, Size = 15)]
        public string Oraconvocazione2
        {
            get { return m_oraconvocazione2; }
            set { m_oraconvocazione2 = value; }
        }

        [DataField("ODG", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Odg
        {
            get { return m_odg; }
            set { m_odg = value; }
        }

        [DataField("NOTE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Note
        {
            get { return m_note; }
            set { m_note = value; }
        }

        [DataField("INVITORICHIEDENTE", Type = DbType.Decimal)]
        public int? Invitorichiedente
        {
            get { return m_invitorichiedente; }
            set { m_invitorichiedente = value; }
        }

        [DataField("FLAGVIA", Type = DbType.Decimal)]
        public int? Flagvia
        {
            get { return m_flagvia; }
            set { m_flagvia = value; }
        }

        [DataField("CODICEMOVIMENTO", Type = DbType.Decimal)]
        public int? Codicemovimento
        {
            get { return m_codicemovimento; }
            set { m_codicemovimento = value; }
        }

        #endregion

        #endregion
    }
}

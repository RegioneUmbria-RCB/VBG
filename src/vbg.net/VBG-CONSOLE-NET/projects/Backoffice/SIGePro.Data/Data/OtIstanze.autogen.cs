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
    /// File generato automaticamente dalla tabella OT_ISTANZE il 31/07/2008 9.24.49
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
    [DataTable("OT_ISTANZE")]
    [Serializable]
    public partial class OtIstanze : BaseDataClass
    {
        #region Membri privati


        private int? m_codiceistanza = null;

        private string m_idcomune = null;

        private int? m_codvia = null;

        private string m_descvia = null;

        private string m_civico = null;

        private string m_bis = null;

        private string m_foglio = null;

        private string m_numero = null;

        private string m_subalterno = null;

        private string m_interno = null;

        private string m_piano = null;

        private string m_scala = null;

        private string m_ot_fabb_numogg = null;

        private string m_ot_oggc_numogg = null;

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


        #endregion

        #region Data fields

        [DataField("CODVIA", Type = DbType.Decimal)]
        public int? Codvia
        {
            get { return m_codvia; }
            set { m_codvia = value; }
        }

        [DataField("DESCVIA", Type = DbType.String, CaseSensitive = false, Size = 128)]
        public string Descvia
        {
            get { return m_descvia; }
            set { m_descvia = value; }
        }

        [DataField("CIVICO", Type = DbType.String, CaseSensitive = false, Size = 25)]
        public string Civico
        {
            get { return m_civico; }
            set { m_civico = value; }
        }

        [DataField("BIS", Type = DbType.String, CaseSensitive = false, Size = 3)]
        public string Bis
        {
            get { return m_bis; }
            set { m_bis = value; }
        }

        [DataField("FOGLIO", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Foglio
        {
            get { return m_foglio; }
            set { m_foglio = value; }
        }

        [DataField("NUMERO", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Numero
        {
            get { return m_numero; }
            set { m_numero = value; }
        }

        [DataField("SUBALTERNO", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Subalterno
        {
            get { return m_subalterno; }
            set { m_subalterno = value; }
        }

        [DataField("INTERNO", Type = DbType.String, CaseSensitive = false, Size = 3)]
        public string Interno
        {
            get { return m_interno; }
            set { m_interno = value; }
        }

        [DataField("PIANO", Type = DbType.String, CaseSensitive = false, Size = 4)]
        public string Piano
        {
            get { return m_piano; }
            set { m_piano = value; }
        }

        [DataField("SCALA", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Scala
        {
            get { return m_scala; }
            set { m_scala = value; }
        }

        [DataField("OT_FABB_NUMOGG", Type = DbType.String, CaseSensitive = false, Size = 6)]
        public string OtFabbNumogg
        {
            get { return m_ot_fabb_numogg; }
            set { m_ot_fabb_numogg = value; }
        }

        [DataField("OT_OGGC_NUMOGG", Type = DbType.String, CaseSensitive = false, Size = 9)]
        public string OtOggcNumogg
        {
            get { return m_ot_oggc_numogg; }
            set { m_ot_oggc_numogg = value; }
        }

        #endregion

        #endregion
    }
}

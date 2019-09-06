
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
    /// File generato automaticamente dalla tabella CONTI il 27/03/2009 11.55.49
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
    [DataTable("CONTI")]
    [Serializable]
    public partial class Conti : BaseDataClass
    {
        #region Membri privati

        private int? m_id = null;

        private string m_codiceconto = null;

        private string m_codicesottoconto = null;

        private string m_descrizione = null;

        private string m_note = null;

        private int? m_fk_codiceamministrazione = null;

        private string m_software = null;

        private string m_idcomune = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("CODICECONTO", Type = DbType.String, CaseSensitive = false, Size = 10)]
        public string Codiceconto
        {
            get { return m_codiceconto; }
            set { m_codiceconto = value; }
        }

        [DataField("CODICESOTTOCONTO", Type = DbType.String, CaseSensitive = false, Size = 10)]
        public string Codicesottoconto
        {
            get { return m_codicesottoconto; }
            set { m_codicesottoconto = value; }
        }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 100)]
        public string Descrizione
        {
            get { return m_descrizione; }
            set { m_descrizione = value; }
        }

        [DataField("NOTE", Type = DbType.String, CaseSensitive = false, Size = 500)]
        public string Note
        {
            get { return m_note; }
            set { m_note = value; }
        }

        [DataField("FK_CODICEAMMINISTRAZIONE", Type = DbType.Decimal)]
        public int? FkCodiceamministrazione
        {
            get { return m_fk_codiceamministrazione; }
            set { m_fk_codiceamministrazione = value; }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        #endregion

        #endregion
    }
}

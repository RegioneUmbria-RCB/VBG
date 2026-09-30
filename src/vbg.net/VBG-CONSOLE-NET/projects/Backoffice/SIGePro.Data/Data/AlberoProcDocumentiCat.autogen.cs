
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
    /// File generato automaticamente dalla tabella ALBEROPROC_DOCUMENTICAT il 23/11/2009 12.44.29
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
    [DataTable("ALBEROPROC_DOCUMENTICAT")]
    [Serializable]
    public partial class AlberoProcDocumentiCat : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_id = null;

        private string m_software = null;

        private string m_descrizione = null;

        private int? m_codiceoggetto = null;

        private int? m_fo_richiedefirma = null;

        private int? m_ordine = null;

        private int? m_fo_nonpermetteupload = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Descrizione
        {
            get { return m_descrizione; }
            set { m_descrizione = value; }
        }

        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        public int? Codiceoggetto
        {
            get { return m_codiceoggetto; }
            set { m_codiceoggetto = value; }
        }

        [DataField("FO_RICHIEDEFIRMA", Type = DbType.Decimal)]
        public int? FoRichiedefirma
        {
            get { return m_fo_richiedefirma; }
            set { m_fo_richiedefirma = value; }
        }

        [DataField("ORDINE", Type = DbType.Decimal)]
        public int? Ordine
        {
            get { return m_ordine; }
            set { m_ordine = value; }
        }

        [DataField("FO_NONPERMETTEUPLOAD", Type = DbType.Decimal)]
        public int? FoNonpermetteupload
        {
            get { return m_fo_nonpermetteupload; }
            set { m_fo_nonpermetteupload = value; }
        }

        #endregion

        #endregion
    }
}

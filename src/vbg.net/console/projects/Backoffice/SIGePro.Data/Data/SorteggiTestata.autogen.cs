
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
    /// File generato automaticamente dalla tabella SORTEGGITESTATA il 26/01/2009 16.36.21
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
    [DataTable("SORTEGGITESTATA")]
    [Serializable]
    public partial class SorteggiTestata : BaseDataClass
    {
        #region Membri privati

        private int? m_st_id = null;

        private string m_st_descrizione = null;

        private string m_idcomune = null;

        private string m_software = null;

        private string m_codicecomune = null;

        private int? m_codiceoggetto = null;

        private DateTime? m_st_datasorteggio = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("ST_ID", Type = DbType.Decimal)]
        [useSequence]
        public int? StId
        {
            get { return m_st_id; }
            set { m_st_id = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("ST_DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string StDescrizione
        {
            get { return m_st_descrizione; }
            set { m_st_descrizione = value; }
        }

        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [DataField("CODICECOMUNE", Type = DbType.String, CaseSensitive = false, Size = 5)]
        public string Codicecomune
        {
            get { return m_codicecomune; }
            set { m_codicecomune = value; }
        }

        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        public int? Codiceoggetto
        {
            get { return m_codiceoggetto; }
            set { m_codiceoggetto = value; }
        }

        [DataField("ST_DATASORTEGGIO", Type = DbType.DateTime)]
        public DateTime? StDatasorteggio
        {
            get { return m_st_datasorteggio; }
            set { m_st_datasorteggio = VerificaDataLocale(value); }
        }

        #endregion

        #endregion
    }
}

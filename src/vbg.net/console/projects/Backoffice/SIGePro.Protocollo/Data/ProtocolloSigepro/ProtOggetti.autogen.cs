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
    /// File generato automaticamente dalla tabella PROT_OGGETTI il 23/08/2011 11.15.48
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
    [DataTable("PROT_OGGETTI")]
    [Serializable]
    public partial class ProtOggetti : BaseDataClass
    {
        #region Membri privati

        private int? m_codiceoggetto = null;

        private string m_nomefile = null;

        private byte[] m_oggetto = null;

        private string m_idcomune = null;

        private int? m_dimensione_file = null;

        private string m_percorso = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("CODICEOGGETTO", Type = DbType.Decimal)]
        [useSequence]
        public int? Codiceoggetto
        {
            get { return m_codiceoggetto; }
            set { m_codiceoggetto = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("NOMEFILE", Type = DbType.String, CaseSensitive = false, Size = 128)]
        public string Nomefile
        {
            get { return m_nomefile; }
            set { m_nomefile = value; }
        }

        [DataField("OGGETTO", Type = DbType.Binary)]
        public byte[] Oggetto
        {
            get { return m_oggetto; }
            set { m_oggetto = value; }
        }

        [DataField("DIMENSIONE_FILE", Type = DbType.Decimal)]
        public int? DimensioneFile
        {
            get { return m_dimensione_file; }
            set { m_dimensione_file = value; }
        }

        [DataField("PERCORSO", Type = DbType.String, CaseSensitive = false, Size = 128)]
        public string Percorso
        {
            get { return m_percorso; }
            set { m_percorso = value; }
        }

        #endregion

        #endregion
    }
}

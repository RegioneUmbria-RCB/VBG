
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella FO_SOTTOSCRIZIONI il 09/11/2009 10.52.22
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
    [DataTable("FO_SOTTOSCRIZIONI")]
    [Serializable]
    public partial class FoSottoscrizioni : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private string m_id = null;

        private string m_codicefiscale = null;

        private int? m_codicedomanda = null;

        private string m_codicefiscalesottoscrivente = null;

        private DateTime? m_datasottoscrizione = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }

        [KeyField("ID", Type = DbType.String, Size = 6)]
        [useSequence]
        public string Id
        {
            get { return this.m_id; }
            set { this.m_id = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("CODICEFISCALE", Type = DbType.String, CaseSensitive = false, Size = 16)]
        public string Codicefiscale
        {
            get { return this.m_codicefiscale; }
            set { this.m_codicefiscale = value; }
        }

        [isRequired]
        [DataField("CODICEDOMANDA", Type = DbType.Decimal)]
        public int? Codicedomanda
        {
            get { return this.m_codicedomanda; }
            set { this.m_codicedomanda = value; }
        }

        [isRequired]
        [DataField("CODICEFISCALESOTTOSCRIVENTE", Type = DbType.String, CaseSensitive = false, Size = 16)]
        public string Codicefiscalesottoscrivente
        {
            get { return this.m_codicefiscalesottoscrivente; }
            set { this.m_codicefiscalesottoscrivente = value; }
        }

        [DataField("DATASOTTOSCRIZIONE", Type = DbType.DateTime)]
        public DateTime? Datasottoscrizione
        {
            get { return this.m_datasottoscrizione; }
            set { this.m_datasottoscrizione = value; }
        }

        #endregion

        #endregion
    }
}

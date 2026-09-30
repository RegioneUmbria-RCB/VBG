
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella ALBEROPROC_ARENDO il 29/08/2011 16.45.06
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
    [DataTable("ALBEROPROC_ARENDO")]
    [Serializable]
    public partial class AlberoprocAREndo : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_id = null;

        private int? m_fk_scid = null;

        private int? m_fk_famigliaendo = null;

        private int? m_fk_categoriaendo = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }

        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return this.m_id; }
            set { this.m_id = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("FK_SCID", Type = DbType.Decimal)]
        public int? FkScid
        {
            get { return this.m_fk_scid; }
            set { this.m_fk_scid = value; }
        }

        [isRequired]
        [DataField("FK_FAMIGLIAENDO", Type = DbType.Decimal)]
        public int? FkFamigliaendo
        {
            get { return this.m_fk_famigliaendo; }
            set { this.m_fk_famigliaendo = value; }
        }

        [DataField("FK_CATEGORIAENDO", Type = DbType.Decimal)]
        public int? FkCategoriaendo
        {
            get { return this.m_fk_categoriaendo; }
            set { this.m_fk_categoriaendo = value; }
        }

        #endregion

        #endregion
    }
}

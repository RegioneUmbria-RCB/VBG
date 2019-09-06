
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
    /// File generato automaticamente dalla tabella ISTANZECALCOLOCANONI_O il 18/11/2008 11.16.32
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
    [DataTable("ISTANZECALCOLOCANONI_O")]
    [Serializable]
    public partial class IstanzeCalcoloCanoniO : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_fk_idtestata = null;

        private int? m_fk_idistoneri = null;

        private int? m_fk_idcausale = null;

        #endregion

        #region properties

        #region Key Fields

        [KeyField("IDCOMUNE", Type = DbType.String, CaseSensitive = false, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("FK_IDTESTATA", Type = DbType.Decimal)]
        public int? FkIdtestata
        {
            get { return m_fk_idtestata; }
            set { m_fk_idtestata = value; }
        }

        [KeyField("FK_IDCAUSALE", Type = DbType.Decimal)]
        public int? FkIdCausale
        {
            get { return m_fk_idcausale; }
            set { m_fk_idcausale = value; }
        }


        [KeyField("FK_IDISTONERI", Type = DbType.Decimal)]
        public int? FkIdistoneri
        {
            get { return m_fk_idistoneri; }
            set { m_fk_idistoneri = value; }
        }


        #endregion

        #endregion
    }
}

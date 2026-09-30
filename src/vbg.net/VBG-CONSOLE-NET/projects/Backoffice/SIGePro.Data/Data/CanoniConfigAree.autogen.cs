
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
    /// File generato automaticamente dalla tabella CANONI_CONFIGAREE il 17/12/2008 14.40.39
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
    [DataTable("CANONI_CONFIGAREE")]
    [Serializable]
    public partial class CanoniConfigAree : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_id = null;

        private int? m_anno = null;

        private int? m_codicearea = null;

        private double? m_coefficienteomi = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        #endregion

        #region Data fields

        [isRequired]
        [DataField("ANNO", Type = DbType.Decimal)]
        public int? Anno
        {
            get { return m_anno; }
            set { m_anno = value; }
        }

        [isRequired]
        [DataField("CODICEAREA", Type = DbType.Decimal)]
        public int? Codicearea
        {
            get { return m_codicearea; }
            set { m_codicearea = value; }
        }

        [isRequired]
        [DataField("COEFFICIENTE_OMI", Type = DbType.Decimal)]
        public double? CoefficienteOMI
        {
            get { return m_coefficienteomi; }
            set { m_coefficienteomi = value; }
        }

        #endregion

        #endregion
    }
}

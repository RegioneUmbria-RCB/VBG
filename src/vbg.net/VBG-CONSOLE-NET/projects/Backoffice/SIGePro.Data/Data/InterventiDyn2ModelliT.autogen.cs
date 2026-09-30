
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
    /// File generato automaticamente dalla tabella ALBEROPROC_DYN2MODELLIT il 05/08/2008 16.49.57
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
    [DataTable("INTERVENTIDYN2MODELLIT")]
    [Serializable]
    public partial class InterventiDyn2ModelliT : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_codiceintervento = null;

        private int? m_fk_d2mt_id = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("CODICEINTERVENTO", Type = DbType.Decimal)]
        public int? CodiceIntervento
        {
            get { return m_codiceintervento; }
            set { m_codiceintervento = value; }
        }

        [KeyField("FK_D2MT_ID", Type = DbType.Decimal)]
        public int? FkD2mtId
        {
            get { return m_fk_d2mt_id; }
            set { m_fk_d2mt_id = value; }
        }


        #endregion

        #region Data fields

        #endregion

        #endregion
    }
}


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
    /// File generato automaticamente dalla tabella ISTANZELAVORI_D il 31/07/2008 11.06.54
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
    [DataTable("ISTANZELAVORI_D")]
    [Serializable]
    public partial class IstanzeLavoriD : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_id = null;

        private int? m_fk_iltid = null;

        private int? m_fk_coid = null;

        private int? m_fk_umid = null;

        private double? m_costo_unitario_um = null;

        private double? m_quantita = null;

        private double? m_totale = null;

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
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("FK_ILTID", Type = DbType.Decimal)]
        public int? FkIltid
        {
            get { return m_fk_iltid; }
            set { m_fk_iltid = value; }
        }

        [isRequired]
        [DataField("FK_COID", Type = DbType.Decimal)]
        public int? FkCoid
        {
            get { return m_fk_coid; }
            set { m_fk_coid = value; }
        }

        [isRequired]
        [DataField("FK_UMID", Type = DbType.Decimal)]
        public int? FkUmid
        {
            get { return m_fk_umid; }
            set { m_fk_umid = value; }
        }

        [DataField("COSTO_UNITARIO_UM", Type = DbType.Decimal)]
        public double? CostoUnitarioUm
        {
            get { return m_costo_unitario_um; }
            set { m_costo_unitario_um = value; }
        }

        [DataField("QUANTITA", Type = DbType.Decimal)]
        public double? Quantita
        {
            get { return m_quantita; }
            set { m_quantita = value; }
        }

        [DataField("TOTALE", Type = DbType.Decimal)]
        public double? Totale
        {
            get { return m_totale; }
            set { m_totale = value; }
        }

        #endregion

        #endregion
    }
}

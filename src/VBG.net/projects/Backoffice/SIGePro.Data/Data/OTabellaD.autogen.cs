
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
    /// File generato automaticamente dalla tabella O_TABELLAD il 27/06/2008 13.01.36
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
    [DataTable("O_TABELLAD")]
    [Serializable]
    public partial class OTabellaD : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_id = null;

        private int? m_fk_ovc_id = null;

        private int? m_fk_ode_id = null;

        private int? m_fk_oca_id = null;

        private int? m_fk_oto_id = null;

        private int? m_fk_oin_id = null;

        private double? m_costo = null;

        private string m_software = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String,  Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("FK_OVC_ID", Type = DbType.Decimal)]
        public int? FkOvcId
        {
            get { return m_fk_ovc_id; }
            set { m_fk_ovc_id = value; }
        }

        [isRequired]
        [DataField("FK_ODE_ID", Type = DbType.Decimal)]
        public int? FkOdeId
        {
            get { return m_fk_ode_id; }
            set { m_fk_ode_id = value; }
        }

        [isRequired]
        [DataField("FK_OCA_ID", Type = DbType.Decimal)]
        public int? FkOcaId
        {
            get { return m_fk_oca_id; }
            set { m_fk_oca_id = value; }
        }

        [isRequired]
        [DataField("FK_OTO_ID", Type = DbType.Decimal)]
        public int? FkOtoId
        {
            get { return m_fk_oto_id; }
            set { m_fk_oto_id = value; }
        }

		[DataField("FK_OIN_ID", Type = DbType.Decimal)]
		public int? FkOinId
		{
			get { return m_fk_oin_id; }
			set { m_fk_oin_id = value; }
		}

        [isRequired]
        [DataField("COSTO", Type = DbType.Decimal)]
        public double? Costo
        {
            get { return m_costo; }
            set { m_costo = value; }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        #endregion

        #endregion
    }
}

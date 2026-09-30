
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
    /// File generato automaticamente dalla tabella O_INDICITERRITORIALI il 27/06/2008 13.01.36
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
    [DataTable("O_INDICITERRITORIALI")]
    [Serializable]
    public partial class OIndiciTerritoriali : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_id = null;

        private double? m_dtz = null;

        private double? m_ift = null;

        private double? m_iff = null;

        private string m_software = null;
		private string m_descrizione = null;

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

        [DataField("DTZ", Type = DbType.Decimal)]
        public double? Dtz
        {
            get { return m_dtz; }
            set { m_dtz = value; }
        }

        [DataField("IFT", Type = DbType.Decimal)]
        public double? Ift
        {
            get { return m_ift; }
            set { m_ift = value; }
        }

        [DataField("IFF", Type = DbType.Decimal)]
        public double? Iff
        {
            get { return m_iff; }
            set { m_iff = value; }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

		[isRequired]
		[DataField("DESCRIZIONE", Type = DbType.String, Size = 50)]
		public string Descrizione
		{
			get { return m_descrizione; }
			set { m_descrizione = value; }
		}

        #endregion

        #endregion
    }
}

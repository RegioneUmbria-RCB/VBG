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
    /// File generato automaticamente dalla tabella MERCATIPRESENZE_D il 29/10/2008 10.40.57
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
    [DataTable("MERCATIPRESENZE_D")]
    [Serializable]
    public partial class MercatiPresenzeD : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_fkidtestata = null;

        private int? m_fkidposteggio = null;

        private int? m_numeropresenze = null;

        private int? m_spuntista = null;

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
        [DataField("FKIDTESTATA", Type = DbType.Decimal)]
        public int? Fkidtestata
        {
            get { return m_fkidtestata; }
            set { m_fkidtestata = value; }
        }

        [DataField("FKIDPOSTEGGIO", Type = DbType.Decimal)]
        public int? Fkidposteggio
        {
            get { return m_fkidposteggio; }
            set { m_fkidposteggio = value; }
        }

        [isRequired]
        [DataField("NUMEROPRESENZE", Type = DbType.Decimal)]
        public int? Numeropresenze
        {
            get { return m_numeropresenze; }
            set { m_numeropresenze = value; }
        }



        [isRequired]
        [DataField("SPUNTISTA", Type = DbType.Decimal)]
        public int? Spuntista
        {
            get { return m_spuntista; }
            set { m_spuntista = value; }
        }

        #endregion

        #endregion
    }
}


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
    /// File generato automaticamente dalla tabella MERCATIPRESENZE_STORICO il 30/03/2009 12.43.48
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
    [DataTable("MERCATIPRESENZE_STORICO")]
    [Serializable]
    public partial class MercatiPresenzeStorico : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;



        private int? m_fkcodicemercato = null;

        private int? m_fkidmercatiuso = null;

        private int? m_codiceanagrafe = null;

        private int? m_anno = null;

        private int? m_numeropresenze = null;

        private string m_identaut = null;

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
        [DataField("FKCODICEMERCATO", Type = DbType.Decimal)]
        public int? Fkcodicemercato
        {
            get { return m_fkcodicemercato; }
            set { m_fkcodicemercato = value; }
        }

        [isRequired]
        [DataField("FKIDMERCATIUSO", Type = DbType.Decimal)]
        public int? Fkidmercatiuso
        {
            get { return m_fkidmercatiuso; }
            set { m_fkidmercatiuso = value; }
        }

        [isRequired]
        [DataField("CODICEANAGRAFE", Type = DbType.Decimal)]
        public int? Codiceanagrafe
        {
            get { return m_codiceanagrafe; }
            set { m_codiceanagrafe = value; }
        }

        [isRequired]
        [DataField("ANNO", Type = DbType.Decimal)]
        public int? Anno
        {
            get { return m_anno; }
            set { m_anno = value; }
        }

        [DataField("NUMEROPRESENZE", Type = DbType.Decimal)]
        public int? Numeropresenze
        {
            get { return m_numeropresenze; }
            set { m_numeropresenze = value; }
        }

        [DataField("IDENT_AUT", Type = DbType.String, CaseSensitive = false, Size = 250)]
        public string IdentAut
        {
            get { return m_identaut; }
            set { m_identaut = value; }
        }


        #endregion

        #endregion
    }
}

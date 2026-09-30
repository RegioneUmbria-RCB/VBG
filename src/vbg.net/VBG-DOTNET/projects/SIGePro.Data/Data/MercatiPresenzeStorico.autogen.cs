
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

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
    [DataContract]
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
        [DataMember]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }




        #endregion

        #region Data fields

        [isRequired]
        [DataMember]
        [DataField("FKCODICEMERCATO", Type = DbType.Decimal)]
        public int? Fkcodicemercato
        {
            get { return this.m_fkcodicemercato; }
            set { this.m_fkcodicemercato = value; }
        }

        [isRequired]
        [DataMember]
        [DataField("FKIDMERCATIUSO", Type = DbType.Decimal)]
        public int? Fkidmercatiuso
        {
            get { return this.m_fkidmercatiuso; }
            set { this.m_fkidmercatiuso = value; }
        }

        [isRequired]
        [DataMember]
        [DataField("CODICEANAGRAFE", Type = DbType.Decimal)]
        public int? Codiceanagrafe
        {
            get { return this.m_codiceanagrafe; }
            set { this.m_codiceanagrafe = value; }
        }

        [isRequired]
        [DataMember]
        [DataField("ANNO", Type = DbType.Decimal)]
        public int? Anno
        {
            get { return this.m_anno; }
            set { this.m_anno = value; }
        }

        [DataMember]
        [DataField("NUMEROPRESENZE", Type = DbType.Decimal)]
        public int? Numeropresenze
        {
            get { return this.m_numeropresenze; }
            set { this.m_numeropresenze = value; }
        }

        [DataMember]
        [DataField("IDENT_AUT", Type = DbType.String, CaseSensitive = false, Size = 250)]
        public string IdentAut
        {
            get { return this.m_identaut; }
            set { this.m_identaut = value; }
        }


        #endregion

        #endregion
    }
}

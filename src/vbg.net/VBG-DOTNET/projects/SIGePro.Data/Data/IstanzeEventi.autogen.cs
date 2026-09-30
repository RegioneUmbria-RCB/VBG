
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella ISTANZEEVENTI il 06/11/2009 9.50.31
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
    [DataTable("ISTANZEEVENTI")]
    [Serializable]
    [DataContract]
    public partial class IstanzeEventi : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_idevento = null;

        private int? m_codiceistanza = null;

        private string m_fkidcategoriaevento = null;

        private string m_descrizione = null;

        private DateTime? m_data = null;

        private int? m_codiceanagrafe = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }

        [KeyField("IDEVENTO", Type = DbType.Decimal)]
        [useSequence]
        public int? Idevento
        {
            get { return this.m_idevento; }
            set { this.m_idevento = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        public int? Codiceistanza
        {
            get { return this.m_codiceistanza; }
            set { this.m_codiceistanza = value; }
        }

        [isRequired]
        [DataField("FKIDCATEGORIAEVENTO", Type = DbType.String, CaseSensitive = false, Size = 16)]
        [DataMember]
        public string Fkidcategoriaevento
        {
            get { return this.m_fkidcategoriaevento; }
            set { this.m_fkidcategoriaevento = value; }
        }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        [DataMember]
        public string Descrizione
        {
            get { return this.m_descrizione; }
            set { this.m_descrizione = value; }
        }

        [isRequired]
        [DataField("DATA", Type = DbType.DateTime)]
        [DataMember]
        public DateTime? Data
        {
            get { return this.m_data; }
            set { this.m_data = value; }
        }

        [DataField("CODICEANAGRAFE", Type = DbType.Decimal)]
        [DataMember]
        public int? Codiceanagrafe
        {
            get { return this.m_codiceanagrafe; }
            set { this.m_codiceanagrafe = value; }
        }

        #endregion

        #endregion
    }
}

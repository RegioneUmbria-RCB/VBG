
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella CITTADINANZA il 15/09/2010 12.14.35
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
    [DataTable("CITTADINANZA")]
    [Serializable]
    [DataContract]
    public partial class Cittadinanza : BaseDataClass
    {
        #region Membri privati

        private int? m_codice = null;

        private string m_cittadinanza = null;

        private string m_cf = null;

        private int? m_disabilitato = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("CODICE", Type = DbType.Decimal)]
        [DataMember]
        public int? Codice
        {
            get { return this.m_codice; }
            set { this.m_codice = value; }
        }


        #endregion

        #region Data fields

        [DataMember]
        [DataField("CITTADINANZA", Type = DbType.String, CaseSensitive = false, Size = 128)]
        public string Descrizione
        {
            get { return this.m_cittadinanza; }
            set { this.m_cittadinanza = value; }
        }

        [DataMember]
        [DataField("CF", Type = DbType.String, CaseSensitive = false, Size = 5)]
        public string Cf
        {
            get { return this.m_cf; }
            set { this.m_cf = value; }
        }

        [DataMember]
        [DataField("DISABILITATO", Type = DbType.Decimal)]
        public int? Disabilitato
        {
            get { return this.m_disabilitato; }
            set { this.m_disabilitato = value; }
        }

        [DataMember]
        [DataField("FLG_PAESE_COMUNITARIO", Type = DbType.Decimal)]
        public int? FlgPaeseComunitario
        {
            get;
            set;
        }



        #endregion

        #endregion
    }
}

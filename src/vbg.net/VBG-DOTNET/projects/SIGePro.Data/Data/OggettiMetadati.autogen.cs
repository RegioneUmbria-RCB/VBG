
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella OGGETTI_METADATI il 24/05/2013 16.50.58
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
    [DataTable("OGGETTI_METADATI")]
    [Serializable]
    [DataContract]
    public partial class OggettiMetadati : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_codiceoggetto = null;

        private string m_chiave = null;

        private string m_valore = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }

        [KeyField("CODICEOGGETTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 1)]
        public int? Codiceoggetto
        {
            get { return this.m_codiceoggetto; }
            set { this.m_codiceoggetto = value; }
        }

        [KeyField("CHIAVE", Type = DbType.String, Size = 100)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string Chiave
        {
            get { return this.m_chiave; }
            set { this.m_chiave = value; }
        }


        #endregion

        #region Data fields

        [DataField("VALORE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string Valore
        {
            get { return this.m_valore; }
            set { this.m_valore = value; }
        }

        #endregion

        #endregion
    }
}

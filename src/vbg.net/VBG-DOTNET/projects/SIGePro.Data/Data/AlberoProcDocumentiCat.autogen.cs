
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella ALBEROPROC_DOCUMENTICAT il 23/11/2009 12.44.29
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
    [DataTable("ALBEROPROC_DOCUMENTICAT")]
    [Serializable]
    [DataContract]
    public partial class AlberoProcDocumentiCat : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_id = null;

        private string m_software = null;

        private string m_descrizione = null;

        private int? m_codiceoggetto = null;

        private int? m_fo_richiedefirma = null;

        private int? m_ordine = null;

        private int? m_fo_nonpermetteupload = null;

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

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        public int? Id
        {
            get { return this.m_id; }
            set { this.m_id = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        [DataMember]
        public string Software
        {
            get { return this.m_software; }
            set { this.m_software = value; }
        }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 50)]
        [DataMember]
        public string Descrizione
        {
            get { return this.m_descrizione; }
            set { this.m_descrizione = value; }
        }

        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        [DataMember]
        public int? Codiceoggetto
        {
            get { return this.m_codiceoggetto; }
            set { this.m_codiceoggetto = value; }
        }

        [DataField("FO_RICHIEDEFIRMA", Type = DbType.Decimal)]
        [DataMember]
        public int? FoRichiedefirma
        {
            get { return this.m_fo_richiedefirma; }
            set { this.m_fo_richiedefirma = value; }
        }

        [DataField("ORDINE", Type = DbType.Decimal)]
        [DataMember]
        public int? Ordine
        {
            get { return this.m_ordine; }
            set { this.m_ordine = value; }
        }

        [DataField("FO_NONPERMETTEUPLOAD", Type = DbType.Decimal)]
        [DataMember]
        public int? FoNonpermetteupload
        {
            get { return this.m_fo_nonpermetteupload; }
            set { this.m_fo_nonpermetteupload = value; }
        }

        #endregion

        #endregion
    }
}


using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data
{
    ///
    /// File generato automaticamente dalla tabella PROT_AOO il 19/01/2009 11.25.08
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
    [DataTable("PROT_AOO")]
    [Serializable]
    public partial class ProtAOO : BaseDataClass
    {
        #region Membri privati

        private int? m_ao_id = null;

        private string m_ao_descrizione = null;

        private int? m_ao_padre = null;

        private string m_ao_codice = null;

        private string m_idcomune = null;

        private int? m_ao_fkidtipologia = null;

        private string m_ao_email = null;

        private string m_ao_responsabile = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("AO_ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Ao_Id
        {
            get { return this.m_ao_id; }
            set { this.m_ao_id = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("AO_DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Ao_Descrizione
        {
            get { return this.m_ao_descrizione; }
            set { this.m_ao_descrizione = value; }
        }

        [DataField("AO_PADRE", Type = DbType.Decimal)]
        public int? Ao_Padre
        {
            get { return this.m_ao_padre; }
            set { this.m_ao_padre = value; }
        }

        [DataField("AO_CODICE", Type = DbType.String, CaseSensitive = false, Size = 25)]
        public string Ao_Codice
        {
            get { return this.m_ao_codice; }
            set { this.m_ao_codice = value; }
        }

        [isRequired]
        [DataField("AO_FKIDTIPOLOGIA", Type = DbType.Decimal)]
        public int? Ao_Fkidtipologia
        {
            get { return this.m_ao_fkidtipologia; }
            set { this.m_ao_fkidtipologia = value; }
        }

        [DataField("AO_EMAIL", Type = DbType.String, CaseSensitive = false, Size = 80)]
        public string Ao_Email
        {
            get { return this.m_ao_email; }
            set { this.m_ao_email = value; }
        }

        [DataField("AO_RESPONSABILE", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Ao_Responsabile
        {
            get { return this.m_ao_responsabile; }
            set { this.m_ao_responsabile = value; }
        }

        #endregion

        #endregion
    }
}

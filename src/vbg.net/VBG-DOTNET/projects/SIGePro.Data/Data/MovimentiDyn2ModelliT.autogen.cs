
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella MOVIMENTIDYN2MODELLIT il 08/09/2008 10.32.27
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
    [DataTable("MOVIMENTIDYN2MODELLIT")]
    [Serializable]
    [DataContract]
    public partial class MovimentiDyn2ModelliT : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_codiceistanza = null;

        private int? m_fk_d2mt_id = null;

        private int? m_codicemovimento = null;

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

        [KeyField("FK_D2MT_ID", Type = DbType.Decimal)]
        [DataMember]
        public int? FkD2mtId
        {
            get { return this.m_fk_d2mt_id; }
            set { this.m_fk_d2mt_id = value; }
        }

        [KeyField("CODICEMOVIMENTO", Type = DbType.Decimal)]
        [DataMember]
        public int? Codicemovimento
        {
            get { return this.m_codicemovimento; }
            set { this.m_codicemovimento = value; }
        }


        #endregion

        #region Data fields

        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        public int? Codiceistanza
        {
            get { return this.m_codiceistanza; }
            set { this.m_codiceistanza = value; }
        }

        #endregion

        #endregion
    }
}


using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella ELENCHIPROFESSIONALIBASE il 15/09/2010 12.28.48
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
    [DataTable("ELENCHIPROFESSIONALIBASE")]
    [Serializable]
    [DataContract]
    public partial class ElenchiProfessionaliBase : BaseDataClass
    {
        #region Membri privati

        private int? m_ep_id = null;

        private string m_ep_descrizione = null;

        private int? _ep_regionale = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("EP_ID", Type = DbType.Decimal)]
        [DataMember]
        public int? EpId
        {
            get { return this.m_ep_id; }
            set { this.m_ep_id = value; }
        }


        #endregion

        #region Data fields

        [DataField("EP_DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 30)]
        [DataMember]
        public string EpDescrizione
        {
            get { return this.m_ep_descrizione; }
            set { this.m_ep_descrizione = value; }
        }

        [DataField("FLAG_REGIONALE", Type = DbType.Decimal)]
        [DataMember]
        public int? EpRegionale
        {
            get { return this._ep_regionale; }
            set { this._ep_regionale = value; }
        }

        #endregion

        #endregion
    }
}

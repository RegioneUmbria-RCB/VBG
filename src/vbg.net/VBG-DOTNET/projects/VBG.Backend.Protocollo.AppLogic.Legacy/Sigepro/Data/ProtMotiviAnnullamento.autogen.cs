
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data
{
    ///
    /// File generato automaticamente dalla tabella PROT_MOTIVIANNULLAMENTO il 03/03/2009 12.09.14
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
    [DataTable("PROT_MOTIVIANNULLAMENTO")]
    [Serializable]
    public partial class ProtMotiviAnnullamento : BaseDataClass
    {
        #region Membri privati

        private int? m_ma_id = null;

        private string m_ma_descrizione = null;

        private string m_idcomune = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("MA_ID", Type = DbType.Decimal)]
        [useSequence]
        public int? MaId
        {
            get { return this.m_ma_id; }
            set { this.m_ma_id = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("MA_DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string MaDescrizione
        {
            get { return this.m_ma_descrizione; }
            set { this.m_ma_descrizione = value; }
        }

        #endregion

        #endregion
    }
}

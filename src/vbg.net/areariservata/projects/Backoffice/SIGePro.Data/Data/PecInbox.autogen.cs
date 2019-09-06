
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
    /// File generato automaticamente dalla tabella ALBEROPROC_DYN2MODELLIT il 05/08/2008 16.49.57
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
    [DataTable("PEC_INBOX")]
    [Serializable]
    public partial class PecInbox : BaseDataClass
    {

        #region properties

        #region Key Fields

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.String)]
        public string Id{get;set;}

        #endregion

        #region Data fields

        [DataField("PEC_SUBJECT", Type = DbType.String)]
        public string PecSubject { get; set; }
        
        [DataField("PEC_FROM", Type = DbType.String)]
        public string PecFrom { get; set; }

        [DataField("PEC_TO", Type = DbType.String)]
        public string PecTo { get; set; }

        [DataField("PEC_DATE", Type = DbType.DateTime)]
        public DateTime? PecDate { get; set; }

        [DataField("SOFTWARE", Type = DbType.String)]
        public string Software { get; set; }

        [DataField("FLAG_PROCESSATA", Type = DbType.Int16)]
        public int? FlagProcessata { get; set; }

        [DataField("CODICEISTANZA", Type = DbType.Int32)]
        public int? CodiceIstanza { get; set; }

        [DataField("CODICEMOVIMENTO", Type = DbType.Int32)]
        public int? CodiceMovimento { get; set; }

        [DataField("CODICEOPERATORE", Type = DbType.Int32)]
        public int? CodiceOperatore { get; set; }

        [DataField("IDPROTOCOLLO", Type = DbType.String)]
        public string IdProtocollo { get; set; }

        [DataField("NUMEROPROTOCOLLO", Type = DbType.String)]
        public string NumeroProtocollo { get; set; }

        [DataField("DATAPROTOCOLLO", Type = DbType.DateTime)]
        public DateTime? DataProtocollo { get; set; }

        [DataField("CODICEOGGETTOPROTOCOLLO", Type = DbType.Int16)]
        public int? CodiceOggettoProtocollo { get; set; }

        [DataField("FLAG_CANCELLATA", Type = DbType.Int16)]
        public int? FlagCancellata { get; set; }

        [DataField("FLAG_LETTA", Type = DbType.Int16)]
        public int? FlagLetta { get; set; }

        [DataField("IN_LOGINNAME", Type = DbType.String)]
        public string InLoginName { get; set; }

        [DataField("FK_RESP_EVIDENZA", Type = DbType.Int32)]
        public int? KkRespEvidenza { get; set; }

        [DataField("PEC_TOCC", Type = DbType.String)]
        public string PecTocc { get; set; }

        [DataField("SOFTWARE_PROT", Type = DbType.String)]
        public string SoftwareProt { get; set; }

        [DataField("CODICECOMUNE_PROT", Type = DbType.String)]
        public string CodiceComuneProt { get; set; }

        #endregion

        #endregion
    }
}

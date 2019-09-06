
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
    /// File generato automaticamente dalla tabella PROTOCOLLO_UFFICIREGISTRI il 26/11/2012 17.29.17
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
    [DataTable("PROTOCOLLO_UFFICIREGISTRI")]
    [Serializable]
    public partial class ProtocolloUfficiRegistri : BaseDataClassProtocollo
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal, Size = 10)]
        public int? Id { get; set; }

        [isRequired]
        [DataField("CODICEREGISTRO", Type = DbType.String, Size = 10)]
        public string Codiceregistro { get; set; }

        [isRequired]
        [DataField("CODICEUFFICIO", Type = DbType.String, CaseSensitive = false, Size = 10)]
        public string Codiceufficio { get; set; }
    }
}

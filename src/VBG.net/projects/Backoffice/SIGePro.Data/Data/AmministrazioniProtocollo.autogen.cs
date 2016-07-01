
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
    /// File generato automaticamente dalla tabella AMMINISTR_PROTOCOLLO il 07/08/2014 16.50.52
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
    [DataTable("AMMINISTR_PROTOCOLLO")]
    [Serializable]
    public partial class AmministrazioniProtocollo : BaseDataClassProtocollo
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 10)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [DataField("CODICEAMMINISTRAZIONE", Type = DbType.Decimal)]
        public int? Codiceamministrazione { get; set; }

        [DataField("PROT_UO", Type = DbType.String, CaseSensitive = false, Size = 100)]
        public string ProtUo { get; set; }

        [DataField("PROT_RUOLO", Type = DbType.String, CaseSensitive = false, Size = 100)]
        public string ProtRuolo { get; set; }
    }
}

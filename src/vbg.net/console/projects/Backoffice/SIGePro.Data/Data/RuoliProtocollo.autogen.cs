
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
    /// File generato automaticamente dalla tabella RI_CARICHE il 25/08/2014 12.34.32
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
    [DataTable("RUOLI_PROTOCOLLO")]
    [Serializable]
    public partial class RuoliProtocollo : BaseDataClassProtocollo
    {

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string IdComune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Int32, Size = 10)]
        public int? Id { get; set; }

        [DataField("IDRUOLO", Type = DbType.Int32, Size = 4)]
        public int? IdRuolo { get; set; }

        [DataField("RUOLO_EXT", Type = DbType.String, Size = 50)]
        public string RuoloExt { get; set; }

        [DataField("CODICECOMUNE", Type = DbType.String, Size = 5)]
        public string CodiceComune { get; set; }

        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }
    }
}

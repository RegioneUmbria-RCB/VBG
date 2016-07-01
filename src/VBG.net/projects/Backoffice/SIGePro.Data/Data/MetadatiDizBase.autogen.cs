
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
    /// File generato automaticamente dalla tabella METADATI_DIZ_BASE il 10/10/2014 14.56.33
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
    [DataTable("METADATI_DIZ_BASE")]
    [Serializable]
    public partial class MetadatiDizBase : BaseDataClass
    {

        [KeyField("ID", Type = DbType.String, Size = 30)]
        public string Id { get; set; }

        [isRequired]
        [DataField("LABEL", Type = DbType.String, CaseSensitive = false, Size = 100)]
        public string Label { get; set; }

        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 500)]
        public string Descrizione { get; set; }

    }
}

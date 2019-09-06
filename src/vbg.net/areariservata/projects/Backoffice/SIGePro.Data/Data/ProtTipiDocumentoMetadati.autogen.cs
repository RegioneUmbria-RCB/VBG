
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
    /// File generato automaticamente dalla tabella PROT_TIPIDOCUMENTO_METADATI il 10/10/2014 15.02.58
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
    [DataTable("PROT_TIPIDOCUMENTO_METADATI")]
    [Serializable]
    public partial class ProtTipiDocumentoMetadati : BaseDataClass
    {

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("FKIDPROTTPDOC", Type = DbType.Decimal)]
        public int? Fkidprottpdoc { get; set; }

        [KeyField("FKIDMETADATIDIZBASE", Type = DbType.String, Size = 30)]
        public string Fkidmetadatidizbase { get; set; }
    }
}

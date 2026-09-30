
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;

using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella DYN2_CAMPIPROPRIETA il 05/08/2008 16.49.58
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
    [DataTable("DYN2_CAMPIPROPRIETA")]
    [Serializable]
    [DataContract]
    public partial class Dyn2CampiProprieta : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        public string Idcomune { get; set; }

        [KeyField("FK_D2C_ID", Type = DbType.Decimal)]
        [DataMember]
        public int? FkD2cId { get; set; }

        [KeyField("PROPRIETA", Type = DbType.String, Size = 30)]
        [DataMember]
        public string Proprieta { get; set; }

        [isRequired]
        [DataField("VALORE", Type = DbType.String, CaseSensitive = false, Size = 100)]
        [DataMember]
        public string Valore { get; set; }
    }
}

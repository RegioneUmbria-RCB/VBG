
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
    /// File generato automaticamente dalla tabella DYN2_CAMPI_SCRIPT il 22/12/2008 12.25.48
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
    [DataTable("DYN2_CAMPI_SCRIPT")]
    [Serializable]
    [DataContract]
    public partial class Dyn2CampiScript : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        public string Idcomune { get; set; }

        [KeyField("FK_D2C_ID", Type = DbType.Decimal)]
        [DataMember]
        public int? FkD2cId { get; set; }

        [KeyField("EVENTO", Type = DbType.String, Size = 15)]
        [DataMember]
        public string Evento { get; set; }

        [DataField("SCRIPT", Type = DbType.Binary)]
        [DataMember]
        public byte[] Script { get; set; }
    }
}

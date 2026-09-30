using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneCalcoli
{
    [DataTable("CONFIGURAZIONE_CALCOLI")]
    [Serializable]
    [DataContract]
    public class ConfigurazioneCalcoli : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        public int? Id { get; set; }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        [DataMember]
        public string Descrizione { get; set; }

        [isRequired]
        [DataField("JSON_CONFIGURAZIONE", Type = DbType.Decimal)]
        [DataMember]
        public int? JsonConfigurazione { get; set; }

        [DataField("JSON_MATRICE", Type = DbType.Decimal)]
        [DataMember]
        public int? JsonMatrice { get; set; }

        [DataField("VERSIONE", Type = DbType.String)]
        [DataMember]
        public string Versione { get; set; }
    }
}

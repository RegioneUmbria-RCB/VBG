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
    [DataTable("PROTOCOLLO_TIPIDOC_METADATI")]
    [Serializable]
    public partial class ProtocolloTipiDocMetadati : DataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string IdComune { get; set; }

        [KeyField("FKTIPODOCUMENTO", Type = DbType.String, Size = 10)]
        public int FkTipoDocumento { get; set; }

        [KeyField("CODICE_METADATO", Type = DbType.String, Size = 50)]
        public string CodiceMetadato { get; set; }

        [isRequired]
        [DataField("DESCRIZIONE_METADATO", Type = DbType.String, CaseSensitive = false, Size = 500)]
        public string DescrizioneMetadato { get; set; }
    }
}


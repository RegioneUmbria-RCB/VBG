using Init.SIGePro.Attributes;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Data
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

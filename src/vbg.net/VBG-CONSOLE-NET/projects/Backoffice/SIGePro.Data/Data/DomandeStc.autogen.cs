using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Data
{
    [DataTable("DOMANDESTC")]
    [Serializable]
    public partial class DomandeStc : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string IdComune { get; set; }

        [KeyField("ID", Type = DbType.Int32, Size = 10)]
        public int Id { get; set; }

        [isRequired]
        [DataField("ID_ENTEMITT", Type = DbType.String, Size = 40)]
        public string IdEnteMitt { get; set; }

        [isRequired]
        [DataField("ID_SPORTELLOMITT", Type = DbType.String, Size = 20)]
        public string IdSportelloMitt { get; set; }

        [isRequired]
        [DataField("ID_DOMANDAMITT", Type = DbType.String, Size = 300)]
        public string IdDomandaMitt { get; set; }

        [DataField("RICHIEDENTE", Type = DbType.String, Size = 300)]
        public string Richiedente { get; set; }

        [DataField("CODICEFISCALE_RICHIEDENTE", Type = DbType.String, Size = 32)]
        public string CodiceFiscaleRichiedente { get; set; }

        [isRequired]
        [DataField("DATARICEZIONE", Type = DbType.DateTime, Size = 20)]
        public DateTime DataRicezione { get; set; }

        [DataField("CODICEISTANZA", Type = DbType.Int32, Size = 10)]
        public int? CodiceIstanza { get; set; }

        [isRequired]
        [DataField("NUMEROISTANZA", Type = DbType.String, Size = 60)]
        public string NumeroIstanza { get; set; }

        [isRequired]
        [DataField("CODICEOGGETTO", Type = DbType.Int32, Size = 12)]
        public int CodiceOggetto { get; set; }

        [DataField("FLAG_IMPORT", Type = DbType.Int16, Size = 1)]
        public int? FlagImport { get; set; }

        [DataField("ULTIMOERRORE", Type = DbType.String, Size = 4000)]
        public string UltimoErrore { get; set; }

        [DataField("DATA_ULTIMOERRORE", Type = DbType.DateTime, Size = 300)]
        public DateTime? DataUltimoErrore { get; set; }

        [DataField("CODICESOFTWARE", Type = DbType.String, Size = 2)]
        public string CodiceSoftware { get; set; }

        [DataField("ID_NODO", Type = DbType.String, Size = 20)]
        public DateTime IdNodo { get; set; }

        [DataField("CODICEISTANZAPRENOTATA", Type = DbType.Int32, Size = 10)]
        public int CodiceIstanzaPrenotata { get; set; }
    }
}

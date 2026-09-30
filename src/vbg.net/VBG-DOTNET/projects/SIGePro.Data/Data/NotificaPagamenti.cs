using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Data
{
    [DataTable("NOTIFICA_PAGAMENTI")]
    [Serializable]
    public class NotificaPagamenti : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string IdComune { get; set; }

        [KeyField("NUMEROOPERAZIONE", Type = DbType.String, Size = 50)]
        public string NumeroOperazione { get; set; }

        [DataField("IDDOMANDA", Type = DbType.String, Size = 50)]
        public string IdDomanda { get; set; }

        [DataField("MESSAGGIO", Type = DbType.String, Size = 4000)]
        public string Messaggio { get; set; }

        [DataField("ESITO", Type = DbType.String, Size = 2)]
        public string Esito { get; set; }

        [DataField("DATA", Type = DbType.String, Size = 20)]
        public string Data { get; set; }

        [DataField("IDORDINE", Type = DbType.String, Size = 50)]
        public string IdOrdine { get; set; }

        [DataField("IDTRANSAZIONE", Type = DbType.String, Size = 10)]
        public string IdTransazione { get; set; }

        [DataField("TIPOPAGAMENTO", Type = DbType.String, Size = 10)]
        public string TipoPagamento { get; set; }
    }
}

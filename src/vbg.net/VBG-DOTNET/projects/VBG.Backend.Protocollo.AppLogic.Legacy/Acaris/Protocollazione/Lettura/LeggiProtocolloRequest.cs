using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using VBG.Backend.Protocollo.AppLogic.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.Lettura
{
    public class LeggiProtocolloRequest
    {
        public DataBase Db { get; set; }
        public string IdComuneAlias { get; set; }
        public string IdComune { get; set; }
        public string CodiceComune { get; set; }
        public string Software { get; set; }
        public string Operatore { get; set; }
        public String IdProtocollo { get; set; }
        public String NumeroProtocollo { get; set; }
        public int? AnnoProtocollo { get; set; }

        public static LeggiProtocolloRequest FromIdProtocollo(ProtocolloBase protocollo, String idProtocollo)
        {
            return new LeggiProtocolloRequest
            {
                IdProtocollo = idProtocollo,
                Db = protocollo.DatiProtocollo.Db,
                IdComuneAlias = protocollo.DatiProtocollo.IdComuneAlias,
                IdComune = protocollo.DatiProtocollo.IdComune,
                CodiceComune = protocollo.DatiProtocollo.CodiceComune,
                Software = protocollo.DatiProtocollo.Software,
                Operatore = protocollo.Operatore
            };
        }

        public static LeggiProtocolloRequest FromEstremiProtocollo(ProtocolloBase protocollo, String numeroProtocollo, int annoProtocollo)
        {
            return new LeggiProtocolloRequest
            {
                Db = protocollo.DatiProtocollo.Db,
                IdComuneAlias = protocollo.DatiProtocollo.IdComuneAlias,
                IdComune = protocollo.DatiProtocollo.IdComune,
                CodiceComune = protocollo.DatiProtocollo.CodiceComune,
                Software = protocollo.DatiProtocollo.Software,
                Operatore = protocollo.Operatore,
                NumeroProtocollo = numeroProtocollo,
                AnnoProtocollo = annoProtocollo
            };
        }
    }
}

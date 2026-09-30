using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.Lettura;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder
{
    public class GetDatiFascicoloRequest
    {
        public DataBase Db { get; set; }
        public string IdComuneAlias { get; set; }
        public string IdComune { get; set; }
        public string CodiceComune { get; set; }
        public string Software { get; set; }
        public string Operatore { get; set; }
        public string IdProtocollo { get; set; }
        public string NumeroProtocollo { get; set; }
        public int? AnnoProtocollo { get; set; }

        internal LeggiProtocolloRequest ToLeggiProtocolloRequest()
        {
            return new LeggiProtocolloRequest
            {
                Db = this.Db,
                IdComuneAlias = this.IdComuneAlias,
                IdComune = this.IdComune,
                CodiceComune = this.CodiceComune,
                Software = this.Software,
                Operatore = this.Operatore,
                IdProtocollo = this.IdProtocollo,
                NumeroProtocollo = this.NumeroProtocollo,
                AnnoProtocollo = this.AnnoProtocollo
            };
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Data
{
    public partial class AlberoProc
    {
        public string ClassificaProtocollazione { get; set; }
        public string TipoDocumentoProtocollazione { get; set; }
        public string TestoTipoProtocollazione { get; set; }
        public string ClassificaFascicolazione { get; set; }
        public string TestoTipoFascicolazione { get; set; }
        public string ProtocollazioneAutomatica { get; set; }
        public string FascicolazioneAutomatica { get; set; }
        public int? CodiceAmministrazione { get; set; }
    }
}

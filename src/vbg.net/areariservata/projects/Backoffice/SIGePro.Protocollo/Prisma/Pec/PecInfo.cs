using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Pec
{
    public class PecInfo
    {
        public int AnnoProtocollo { get; private set; }
        public int NumeroProtocollo { get; private set; }
        public string Utente { get; set; }
        public string TipoRegistro { get; set; }
        public IEnumerable<IAnagraficaAmministrazione> Destinatari;

        public PecInfo(long annoProtocollo, long numeroProtocollo, string utente, string tipoRegistro, IEnumerable<IAnagraficaAmministrazione> destinatari)
        {
            this.AnnoProtocollo = Convert.ToInt32(annoProtocollo);
            this.NumeroProtocollo = Convert.ToInt32(numeroProtocollo);
            this.Utente = utente;
            this.TipoRegistro = tipoRegistro;
            this.Destinatari = destinatari;
        }
    }
}

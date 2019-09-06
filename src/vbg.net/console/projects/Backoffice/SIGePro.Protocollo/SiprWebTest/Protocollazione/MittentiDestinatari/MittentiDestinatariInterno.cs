using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.SiprWebTest.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariInterno : BaseMittentiDestinatari, IMittentiDestinatari
    {
        public MittentiDestinatariInterno(IDatiProtocollo datiProto) : base(datiProto)
        {

        }

        public string Mittente
        {
            get { return DatiProto.Amministrazione.PROT_UO; }
        }

        public string[] Destinatari
        {
            get { return new string[] { DatiProto.AmministrazioniProtocollo[0].PROT_UO }; }
        }


        public string[] DestinatariCC
        {
            get { return null; }
        }
    }
}

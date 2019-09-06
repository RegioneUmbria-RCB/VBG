using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Insiel2.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneInputMittentiDestinatariPartenza : ProtocollazioneMittentiDestinatariBase, IProtocollazioneMittentiDestinatari
    {
        public ProtocollazioneInputMittentiDestinatariPartenza(IDatiProtocollo datiProto, ProtocolloLogs logs) : base(datiProto, logs)
        {

        }

        public MittenteInsProto[] GetMittenti()
        {
            return null;
        }

        public DestinatarioIOPInsProto[] GetDestinatari()
        {
            var anagrafiche = DatiProto.AnagraficheProtocollo.Select(x => x.GetDestinatarioIOPFromAnagrafe());
            var amministrazione = DatiProto.AmministrazioniEsterne.Select(x => x.GetDestinatarioIOPFromAmministrazione());

            var retVal = anagrafiche.Union(amministrazione);

            return retVal.ToArray();
        }

        public verso Flusso
        {
            get { return verso.P; }
        }


        public UfficioInsProto[] GetUffici()
        {
            var retVal = DatiProto.AmministrazioniInterne.Select(x => new UfficioInsProto { codice = x.PROT_UO, giaInviato = true, giaInviatoSpecified = true });
            if (retVal.Count() == 0)
                return null;

            return retVal.ToArray();
        }
    }
}

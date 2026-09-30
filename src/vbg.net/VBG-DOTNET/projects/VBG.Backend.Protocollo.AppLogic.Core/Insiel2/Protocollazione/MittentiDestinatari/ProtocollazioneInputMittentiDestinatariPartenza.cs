using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using ProtocolloInsielService2;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Protocollazione.MittentiDestinatari
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

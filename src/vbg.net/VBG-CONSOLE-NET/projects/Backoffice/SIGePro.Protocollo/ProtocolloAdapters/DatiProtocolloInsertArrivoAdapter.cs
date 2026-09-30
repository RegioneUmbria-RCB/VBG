using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.ProtocolloAdapters
{
    public class DatiProtocolloInsertArrivoAdapter : DatiProtocolloAdapter
    {
        public DatiProtocolloInsertArrivoAdapter(DatiProtocolloIn protoIn)
        {
            if (protoIn.Destinatari.Amministrazione.Count == 0)
                throw new Exception("AMMINISTRAZIONE MITTENTE NON VALORIZZATA");

            protocolloIn = protoIn;

            flusso = ProtocolloConstants.COD_ARRIVO;

            uo = protoIn.Destinatari.Amministrazione[0].PROT_UO;
            ruolo = protoIn.Destinatari.Amministrazione[0].PROT_RUOLO;
            descrizioneAmministrazione = protoIn.Destinatari.Amministrazione[0].AMMINISTRAZIONE;
            amministrazione = protoIn.Destinatari.Amministrazione[0];

            anagraficheProtocollo = protoIn.Mittenti.Anagrafe;
            amministrazioniProtocollo = protoIn.Mittenti.Amministrazione;
            amministrazioniInterne = protoIn.Mittenti.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO)).ToList();
            amministrazioniEsterne = protoIn.Mittenti.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            isAmministrazioneInterna = protoIn.Mittenti.Amministrazione.Count(an => !String.IsNullOrEmpty(an.PROT_RUOLO) && !String.IsNullOrEmpty(an.PROT_UO)) > 0;

            altriDestinatariInterni = null;

            if (protocolloIn.Destinatari.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO)).Count() > 1)
                altriDestinatariInterni = protocolloIn.Destinatari.Amministrazione.Skip(1).ToList();
        }
    }
}

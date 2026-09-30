using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.ProtocolloAdapters
{
    public class DatiProtocolloInsertPartenzaAdapter : DatiProtocolloAdapter
    {
        public DatiProtocolloInsertPartenzaAdapter(DatiProtocolloIn protoIn)
        {
            if (protoIn.Mittenti.Amministrazione.Count == 0)
                throw new Exception("AMMINISTRAZIONE MITTENTE NON VALORIZZATA");

            protocolloIn = protoIn;

            flusso = ProtocolloConstants.COD_PARTENZA;

            uo = protoIn.Mittenti.Amministrazione[0].PROT_UO;
            ruolo = protoIn.Mittenti.Amministrazione[0].PROT_RUOLO;
            descrizioneAmministrazione = protoIn.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
            amministrazione = protoIn.Mittenti.Amministrazione[0];

            anagraficheProtocollo = protoIn.Destinatari.Anagrafe;
            amministrazioniProtocollo = protoIn.Destinatari.Amministrazione;

            amministrazioniInterne = protoIn.Destinatari.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO)).ToList();
            amministrazioniEsterne = protoIn.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            isAmministrazioneInterna = protoIn.Destinatari.Amministrazione.Count(an => !String.IsNullOrEmpty(an.PROT_RUOLO) && !String.IsNullOrEmpty(an.PROT_UO)) > 0;

            altriDestinatariInterni = null;

            if (amministrazioniInterne.Count > 1)
                altriDestinatariInterni = amministrazioniInterne;
        }
    }
}

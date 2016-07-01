using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.ProtocolloAdapters
{
    public class DatiProtocolloInsertInternoAdapter : DatiProtocolloAdapter
    {
        public DatiProtocolloInsertInternoAdapter(DatiProtocolloIn protoIn)
        {
            if (protoIn.Mittenti.Amministrazione.Count == 0)
                throw new Exception("AMMINISTRAZIONE MITTENTE NON VALORIZZATA");

            if (protoIn.Destinatari.Amministrazione.Count == 0)
                throw new Exception("AMMINISTRAZIONE DESTINATARIO NON VALORIZZATA");

            protocolloIn = protoIn;

            flusso = ProtocolloConstants.COD_INTERNO;

            uo = protoIn.Mittenti.Amministrazione[0].PROT_UO;
            ruolo = protoIn.Mittenti.Amministrazione[0].PROT_RUOLO;
            descrizioneAmministrazione = protoIn.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
            amministrazione = protoIn.Mittenti.Amministrazione[0];

            //amministrazioniProtocollo.Add(protoIn.Destinatari.Amministrazione[0]);
            amministrazioniProtocollo = new List<Amministrazioni>();
            amministrazioniInterne = new List<Amministrazioni>();
            amministrazioniEsterne = new List<Amministrazioni>();

            amministrazioniProtocollo.Add(protoIn.Destinatari.Amministrazione[0]);
            amministrazioniInterne.Add(protoIn.Destinatari.Amministrazione[0]);

        }
    }
}

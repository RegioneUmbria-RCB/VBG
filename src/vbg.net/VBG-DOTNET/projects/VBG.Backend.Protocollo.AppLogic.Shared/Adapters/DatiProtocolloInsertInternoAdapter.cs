using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Adapters
{
    public class DatiProtocolloInsertInternoAdapter : DatiProtocolloAdapter
    {
        public DatiProtocolloInsertInternoAdapter(DatiProtocolloIn protoIn)
        {
            if (protoIn.Mittenti.Amministrazione.Count == 0)
                throw new Exception("AMMINISTRAZIONE MITTENTE NON VALORIZZATA");

            if (protoIn.Destinatari.Amministrazione.Count == 0)
                throw new Exception("AMMINISTRAZIONE DESTINATARIO NON VALORIZZATA");

            this.protocolloIn = protoIn;

            this.flusso = ProtocolloConstants.COD_INTERNO;

            this.uo = protoIn.Mittenti.Amministrazione[0].PROT_UO;
            this.ruolo = protoIn.Mittenti.Amministrazione[0].PROT_RUOLO;
            this.descrizioneAmministrazione = protoIn.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
            this.amministrazione = protoIn.Mittenti.Amministrazione[0];

            //amministrazioniProtocollo.Add(protoIn.Destinatari.Amministrazione[0]);
            this.amministrazioniProtocollo = new List<ProtocolloAmministrazioni>();
            this.amministrazioniInterne = new List<ProtocolloAmministrazioni>();
            this.amministrazioniEsterne = new List<ProtocolloAmministrazioni>();

            this.amministrazioniProtocollo.Add(protoIn.Destinatari.Amministrazione[0]);
            this.amministrazioniInterne.Add(protoIn.Destinatari.Amministrazione[0]);

            if (this.protocolloIn.Destinatari.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO)).Count() > 1)
                this.altriDestinatariInterni = this.protocolloIn.Destinatari.Amministrazione.Skip(1).ToList();
        }
    }
}

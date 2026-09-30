using VBG.Backend.Protocollo.AppLogic.Core.Halley.Adapters;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Configurations
{
    public class HalleySegnaturaParamConfiguration
    {
        public readonly string Operatore;
        public readonly string Flusso;
        public readonly string Classifica;
        public readonly string TipoDocumento;
        public readonly string Oggetto;
        public readonly HalleyVerticalizzazioneParametriAdapter VertParams;
        public readonly string DomicilioElettronico;
        public readonly DateTime DataRicevimento;
        public readonly IDatiHalleySegnaturaApplicativoProtocollo DatiIndirizzoApplicativoProtocollo;

        public HalleySegnaturaParamConfiguration(HalleyVerticalizzazioneParametriAdapter vertParams, string operatore, DatiProtocolloIn datiProtoIn, 
                                                 string domicilioElettronico, DateTime dataRicevimento)
        {
            
            
            VertParams = vertParams;
            Operatore = operatore;
            Classifica = datiProtoIn.Classifica;
            TipoDocumento = datiProtoIn.TipoDocumento;
            Oggetto = datiProtoIn.Oggetto;
            DomicilioElettronico = domicilioElettronico;
            DataRicevimento = dataRicevimento;
            ListaMittDest listMittDest = null;

            if (datiProtoIn.Flusso == ProtocolloConstants.COD_ARRIVO)
            {
                Flusso = ProtocolloConstants.COD_ARRIVO_DOCAREA;
                listMittDest = datiProtoIn.Mittenti;
            }
            else if (datiProtoIn.Flusso == ProtocolloConstants.COD_PARTENZA)
            {
                Flusso = ProtocolloConstants.COD_PARTENZA_DOCAREA;
                listMittDest = datiProtoIn.Destinatari;
            }
            else if (datiProtoIn.Flusso == ProtocolloConstants.COD_INTERNO)
            {
                Flusso = ProtocolloConstants.COD_INTERNO_DOCAREA;
                listMittDest = datiProtoIn.Destinatari;
            }
            else
                throw new Exception(String.Format("FLUSSO {0} non supportato", datiProtoIn.Flusso));

            var listAmministrazioniEsterne = listMittDest.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();
            var listAmministrazioniInterne = listMittDest.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            if (listMittDest.Anagrafe.Count > 0)
                DatiIndirizzoApplicativoProtocollo = new HalleySegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder(listMittDest.Anagrafe[0]);
            else if (listAmministrazioniEsterne.Count > 0)
                DatiIndirizzoApplicativoProtocollo = new HalleySegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(listAmministrazioniEsterne[0]);
            else if (listAmministrazioniInterne.Count > 0)
                DatiIndirizzoApplicativoProtocollo = new HalleySegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(listAmministrazioniInterne[0]);

            else
                throw new Exception("NON SONO PRESENTI MITTENTI DESTINATARI");
            

        }
    }
}

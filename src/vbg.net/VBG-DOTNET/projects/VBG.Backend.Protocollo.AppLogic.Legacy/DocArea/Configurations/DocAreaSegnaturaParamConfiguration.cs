using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Adapters;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Configurations
{
    public class DocAreaSegnaturaParamConfiguration
    {
        public readonly string Operatore;
        public readonly string Flusso;
        public readonly string Classifica;
        public readonly string TipoDocumento;
        public readonly string TipoSmistamento;
        public readonly string Oggetto;
        public readonly DocAreaVerticalizzazioneParametriAdapter VertParams;
        public readonly string DomicilioElettronico;
        public readonly DateTime DataRicevimento;
        public readonly IDatiDocAreaSegnaturaApplicativoProtocollo DatiIndirizzoApplicativoProtocollo;
        public readonly TipoProvenienza Provenienza;
        public readonly Source TipoInserimento;

        public DocAreaSegnaturaParamConfiguration(DocAreaVerticalizzazioneParametriAdapter vertParams, string operatore, DatiProtocolloIn datiProtoIn, 
                                                 string domicilioElettronico, DateTime dataRicevimento, TipoProvenienza provenienza, Source tipoInserimento)
        {

            this.VertParams = vertParams;
            this.Operatore = operatore;
            this.Classifica = datiProtoIn.Classifica;
            this.TipoDocumento = datiProtoIn.TipoDocumento;
            this.TipoSmistamento = datiProtoIn.TipoSmistamento;
            this.Oggetto = datiProtoIn.Oggetto;
            this.DomicilioElettronico = domicilioElettronico;
            this.DataRicevimento = dataRicevimento;
            this.Provenienza = provenienza;
            this.TipoInserimento = tipoInserimento;
            ListaMittDest listMittDest = null;

            if (datiProtoIn.Flusso == ProtocolloConstants.COD_ARRIVO)
            {
                this.Flusso = ProtocolloConstants.COD_ARRIVO_DOCAREA;
                listMittDest = datiProtoIn.Mittenti;
            }
            else if (datiProtoIn.Flusso == ProtocolloConstants.COD_PARTENZA)
            {
                this.Flusso = ProtocolloConstants.COD_PARTENZA_DOCAREA;
                listMittDest = datiProtoIn.Destinatari;
            }
            else if (datiProtoIn.Flusso == ProtocolloConstants.COD_INTERNO)
            {
                this.Flusso = ProtocolloConstants.COD_INTERNO_DOCAREA;
                listMittDest = datiProtoIn.Destinatari;
            }
            else
                throw new Exception(String.Format("FLUSSO {0} non supportato", datiProtoIn.Flusso));

            var listAmministrazioniEsterne = listMittDest.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();
            var listAmministrazioniInterne = listMittDest.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            if (listMittDest.Anagrafe.Count > 0)
                DatiIndirizzoApplicativoProtocollo = new DocAreaSegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder(listMittDest.Anagrafe[0]);
            else if (listAmministrazioniEsterne.Count > 0)
                DatiIndirizzoApplicativoProtocollo = new DocAreaSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(listAmministrazioniEsterne[0]);
            else if (listAmministrazioniInterne.Count > 0)
                DatiIndirizzoApplicativoProtocollo = new DocAreaSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(listAmministrazioniInterne[0]);
            else
                throw new Exception("NON SONO PRESENTI MITTENTI / DESTINATARI");
            

        }
    }
}

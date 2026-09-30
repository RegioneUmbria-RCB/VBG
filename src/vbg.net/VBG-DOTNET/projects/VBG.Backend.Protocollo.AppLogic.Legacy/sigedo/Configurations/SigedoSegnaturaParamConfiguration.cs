using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Adapters;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Configurations
{
    public class SigedoSegnaturaParamConfiguration
    {
        public readonly string Operatore;
        public readonly string Flusso;
        public readonly string Classifica;
        public readonly string TipoSmistamento;
        public readonly string Oggetto;
        public readonly SigedoVerticalizzazioneParametriAdapter ParametriVerticalizzazioneSigedo;
        public readonly string UoSmistamento;
        public readonly List<ProtocolloAmministrazioni> AltriDestinatariInterni;

        public SigedoSegnaturaParamConfiguration(SigedoVerticalizzazioneParametriAdapter parametriVerticalizzazioneSigedo, string tipoSmistamento, string operatore,
                                                string classifica, string oggetto, string flusso, string uoSmistamento, List<ProtocolloAmministrazioni> altriDestinatariInterni)
        {
            this.ParametriVerticalizzazioneSigedo = parametriVerticalizzazioneSigedo;

            this.Operatore = operatore;
            this.Classifica = classifica;
            this.TipoSmistamento = tipoSmistamento;
            this.Oggetto = oggetto;
            this.UoSmistamento = uoSmistamento;
            this.AltriDestinatariInterni = altriDestinatariInterni;

            if (flusso == ProtocolloConstants.COD_ARRIVO)
                this.Flusso = ProtocolloConstants.COD_ARRIVO_DOCAREA;
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
                this.Flusso = ProtocolloConstants.COD_PARTENZA_DOCAREA;
            else
                this.Flusso = flusso;
        }
    }
}

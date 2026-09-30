using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Sigedo.Adapters;
using Init.SIGePro.Protocollo.Sigedo.Interfacce;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Sigedo.Configurations
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
        public readonly List<Amministrazioni> AltriDestinatariInterni;

        public SigedoSegnaturaParamConfiguration(SigedoVerticalizzazioneParametriAdapter parametriVerticalizzazioneSigedo, string tipoSmistamento, string operatore,
                                                string classifica, string oggetto, string flusso, string uoSmistamento, List<Amministrazioni> altriDestinatariInterni)
        {
            ParametriVerticalizzazioneSigedo = parametriVerticalizzazioneSigedo;
            
            Operatore = operatore;
            Classifica = classifica;
            TipoSmistamento = tipoSmistamento;
            Oggetto = oggetto;
            UoSmistamento = uoSmistamento;
            AltriDestinatariInterni = altriDestinatariInterni;

            if (flusso == ProtocolloConstants.COD_ARRIVO)
                Flusso = ProtocolloConstants.COD_ARRIVO_DOCAREA;
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
                Flusso = ProtocolloConstants.COD_PARTENZA_DOCAREA;
            else
                Flusso = flusso;
        }
    }
}

using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK
{
    public class VerticalizzazioniWrapper
    {
        public string Url { get; private set; }
        public string ConnectionString { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public string CodiceAoo { get; private set; }
        public bool InvioPec { get; private set; }
        public string DenominazioneEnte { get; private set; }
        public string AssegnatoDa { get; private set; }


        ProtocolloLogs _logs;
        VerticalizzazioneProtocolloStudioK _paramVert;

        public VerticalizzazioniWrapper(VerticalizzazioneProtocolloStudioK paramVert, ProtocolloLogs logs)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_STUDIOK NON E' ATTIVA");

            _logs = logs;
            _paramVert = paramVert;

            EstraiParametri();
        }

        private void EstraiParametri()
        {
            Url = _paramVert.Url;
            ConnectionString = _paramVert.ConnectionString;
            CodiceAmministrazione = _paramVert.CodiceAmministrazione;
            CodiceAoo = _paramVert.CodiceAoo;
            InvioPec = _paramVert.InvioPec == "1";
            DenominazioneEnte = _paramVert.DenominazioneEnte;
            AssegnatoDa = AssegnatoDa;
            
        }
    }
}

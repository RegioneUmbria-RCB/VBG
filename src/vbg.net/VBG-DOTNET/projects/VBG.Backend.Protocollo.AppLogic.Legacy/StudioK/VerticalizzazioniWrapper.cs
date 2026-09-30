using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.StudioK
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


        private readonly ProtocolloLogs _logs;
        private readonly VerticalizzazioneProtocolloStudioK _paramVert;

        public VerticalizzazioniWrapper(VerticalizzazioneProtocolloStudioK paramVert, ProtocolloLogs logs)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_STUDIOK NON E' ATTIVA");

            this._logs = logs;
            this._paramVert = paramVert;

            this.EstraiParametri();
        }

        private void EstraiParametri()
        {
            this.Url = this._paramVert.Url;
            this.ConnectionString = this._paramVert.ConnectionString;
            this.CodiceAmministrazione = this._paramVert.CodiceAmministrazione;
            this.CodiceAoo = this._paramVert.CodiceAoo;
            this.InvioPec = this._paramVert.InvioPec == "1";
            this.DenominazioneEnte = this._paramVert.DenominazioneEnte;
            this.AssegnatoDa = this.AssegnatoDa;

        }
    }
}

using Init.SIGePro.Data;
using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Fascicolazione
{
    public class FascicolazioneMovimento : IFascicolazioneIstanzaMovimento
    {
        private readonly DatiFascicolazioneConfiguration _datiFascicolo;
        private readonly IIstanzaDaProtocollare _istanza;
        private readonly ProtocolloLogs _logs;

        public FascicolazioneMovimento(DatiFascicolazioneConfiguration datiFascicolo, IIstanzaDaProtocollare istanza, ProtocolloLogs logs)
        {
            this._datiFascicolo = datiFascicolo;
            this._istanza = istanza;
            this._logs = logs;
        }

        public void Fascicola()
        {
            if (!this._istanza.DATAPROTOCOLLO.HasValue || String.IsNullOrEmpty(this._istanza.NUMEROPROTOCOLLO))
            {
                throw new ProtocolloException("LA DATA PROTOCOLLO O IL NUMERO PROTOCOLLO DELL'ISTANZA NON SONO VALORIZZATI");
            }
            var requestLeggi = new string[] { this._datiFascicolo.CodiceAmministrazione, this._datiFascicolo.CodiceAoo, this._istanza.DATAPROTOCOLLO.Value.Year.ToString(), this._istanza.NUMEROPROTOCOLLO };
            var responseLeggi = this._datiFascicolo.Wrapper.LeggiFascicolo(requestLeggi);
            var requestProtocollo = new string[] { this._datiFascicolo.CodiceAmministrazione, this._datiFascicolo.CodiceAoo, this._datiFascicolo.AnnoProtocollo, this._datiFascicolo.NumeroProtocollo };


            if (responseLeggi == null || responseLeggi.Length == 0)
            {
                throw new ProtocolloException(String.Format("NON SONO STATI TROVATI FASCICOLI PER IL PROTOCOLLO NUMERO: {0} ANNO: {1} NE SARA' CREATO UNO NUOVO", this._datiFascicolo.NumeroProtocollo, this._datiFascicolo.AnnoProtocollo));
            }

            if (String.IsNullOrEmpty(responseLeggi.Last()))
            {
                throw new ProtocolloException("IL VALORE DEL FASCICOLO RESTITUITO E' VUOTO");
            }
            var numeroFascicolo = this.GetNumeroFascicolo(responseLeggi.Last());
            var annoFascicolo = this.GetAnnoFascicolo(responseLeggi.Last());

            var requestFascicolo = new string[] { this._datiFascicolo.CodiceAmministrazione, this._datiFascicolo.CodiceAoo, annoFascicolo, numeroFascicolo };

            this._datiFascicolo.Wrapper.FascicolaProtocollo(requestProtocollo, requestFascicolo);
            this._logs.InfoFormat("FASCICOLAZIONE DEL PROTOCOLLO NUMERO: {0} ANNO: {1} SUL FASCICOLO: {2} AVVENUTA CON SUCCESSO", this._datiFascicolo.NumeroProtocollo, this._datiFascicolo.AnnoProtocollo, responseLeggi.Last());
        }

        public string GetNumeroFascicolo(string fascicolo)
        {
            var datiFascicoloRet = fascicolo.Split('-');

            if (datiFascicoloRet == null || datiFascicoloRet.Length < 2)
                throw new ProtocolloException(String.Format("NON E' POSSIBILE RECUPERARE IL NUMERO FASCICOLO, FASCICOLO: {0}", fascicolo));

            return datiFascicoloRet[1];

        }

        public string GetAnnoFascicolo(string fascicolo)
        {
            if (fascicolo.Length < 4)
                throw new ProtocolloException(String.Format("IL VALORE DEL FASCICOLO RESTITUITO NON HA UN FORMATO PREVISTO, VALORE FASCICOLO {0}, NON E' POSSIBILE RECUPERARE L'ANNO", fascicolo));

            return fascicolo.Substring(0, 4);

        }
    }
}

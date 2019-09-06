using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.GeProt.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.GeProt.Fascicolazione
{
    public class FascicolazioneIstanza : IFascicolazioneIstanzaMovimento
    {
        DatiFascicolazioneConfiguration _datiFascicolo;
        ProtocolloLogs _logs;
        IEnumerable<Movimenti> _movimentiProtocollati;

        public FascicolazioneIstanza(DatiFascicolazioneConfiguration datiFascicolo, ProtocolloLogs logs, IEnumerable<Movimenti> movimentiProtocollati = null)
        {
            _datiFascicolo = datiFascicolo;
            _logs = logs;
            _movimentiProtocollati = movimentiProtocollati;
        }

        public void Fascicola()
        {
            var responseCrea = this.CreaFascicolo();

            var annoFascicolo = responseCrea[2];
            var numeroFascicolo = responseCrea[3];

            var arrNumFasc = numeroFascicolo.Split('-');
            if (arrNumFasc.Length > 1)
                numeroFascicolo = arrNumFasc[1];

            _logs.InfoFormat("CREAZIONE DEL FASCICOLO NUMERO {0}, ANNO {1}", numeroFascicolo, annoFascicolo);

            var requestProtocollo = new string[] { _datiFascicolo.CodiceAmministrazione, _datiFascicolo.CodiceAoo, _datiFascicolo.AnnoProtocollo, _datiFascicolo.NumeroProtocollo };
            var requestFascicolo = new string[] { _datiFascicolo.CodiceAmministrazione, _datiFascicolo.CodiceAoo, annoFascicolo, numeroFascicolo };

            _datiFascicolo.Wrapper.FascicolaProtocollo(requestProtocollo, requestFascicolo);
            _logs.InfoFormat("FASCICOLAZIONE DEL PROTOCOLLO NUMERO: {0} ANNO: {1} SUL FASCICOLO NUMERO: {2}, ANNO: {3} AVVENUTA CON SUCCESSO", _datiFascicolo.NumeroProtocollo, _datiFascicolo.AnnoProtocollo, numeroFascicolo, annoFascicolo);
            
            FascicolaMovimentiProtocollati(requestFascicolo);
        }

        public string[] CreaFascicolo()
        {
            var request = new string[] { _datiFascicolo.CodiceAmministrazione, _datiFascicolo.CodiceAoo, _datiFascicolo.CodiceUfficio, _datiFascicolo.Classifica, DateTime.Now.Year.ToString(), _datiFascicolo.Oggetto };
            var response = _datiFascicolo.Wrapper.CreaFascicolo(request);

            if (response == null || response.Length == 0)
                throw new Exception("LA CREAZIONE DEL FASCICOLO NON HA RESTITUITO DATI");

            return response;
        }

        private void FascicolaMovimentiProtocollati(string[] requestFascicolo)
        {
            foreach (var mov in _movimentiProtocollati)
            {
                var requestProtocollo = new string[] { _datiFascicolo.CodiceAmministrazione, _datiFascicolo.CodiceAoo, mov.DATAPROTOCOLLO.Value.Year.ToString(), mov.NUMEROPROTOCOLLO };
                _datiFascicolo.Wrapper.FascicolaProtocollo(requestProtocollo, requestFascicolo);
                _logs.InfoFormat("FASCICOLAZIONE DEL MOVIMENTO {0} CON IL NUMERO {1} ANNO {2}", mov.MOVIMENTO, requestFascicolo[2], requestFascicolo[3]);
            }
        }
    }
}

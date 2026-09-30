using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.GeProt.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.GeProt.Fascicolazione
{
    public class FascicolazioneMovimento : IFascicolazioneIstanzaMovimento
    {
        DatiFascicolazioneConfiguration _datiFascicolo;
        Istanze _istanza;
        ProtocolloLogs _logs;

        public FascicolazioneMovimento(DatiFascicolazioneConfiguration datiFascicolo, Istanze istanza, ProtocolloLogs logs)
        {
            _datiFascicolo = datiFascicolo;
            _istanza = istanza;
            _logs = logs;
        }

        public void Fascicola()
        {
            if (!_istanza.DATAPROTOCOLLO.HasValue || String.IsNullOrEmpty(_istanza.NUMEROPROTOCOLLO))
                throw new Exception("LA DATA PROTOCOLLO O IL NUMERO PROTOCOLLO DELL'ISTANZA NON SONO VALORIZZATI");

            var requestLeggi = new string[] { _datiFascicolo.CodiceAmministrazione, _datiFascicolo.CodiceAoo, _istanza.DATAPROTOCOLLO.Value.Year.ToString(), _istanza.NUMEROPROTOCOLLO };
            var responseLeggi = _datiFascicolo.Wrapper.LeggiFascicolo(requestLeggi);
            var requestProtocollo = new string[] { _datiFascicolo.CodiceAmministrazione, _datiFascicolo.CodiceAoo, _datiFascicolo.AnnoProtocollo, _datiFascicolo.NumeroProtocollo };

            string numeroFascicolo = "";
            string annoFascicolo = "";

            if (responseLeggi == null || responseLeggi.Length == 0)
                throw new Exception(String.Format("NON SONO STATI TROVATI FASCICOLI PER IL PROTOCOLLO NUMERO: {0} ANNO: {1} NE SARA' CREATO UNO NUOVO", _datiFascicolo.NumeroProtocollo, _datiFascicolo.AnnoProtocollo));
            else
            {
                if (String.IsNullOrEmpty(responseLeggi.ToList().Last()))
                    throw new Exception("IL VALORE DEL FASCICOLO RESTITUITO E' VUOTO");

                numeroFascicolo = GetNumeroFascicolo(responseLeggi.ToList().Last());
                annoFascicolo = GetAnnoFascicolo(responseLeggi.ToList().Last());
            }
            
            var requestFascicolo = new string[] { _datiFascicolo.CodiceAmministrazione, _datiFascicolo.CodiceAoo, annoFascicolo, numeroFascicolo };

            _datiFascicolo.Wrapper.FascicolaProtocollo(requestProtocollo, requestFascicolo);
            _logs.InfoFormat("FASCICOLAZIONE DEL PROTOCOLLO NUMERO: {0} ANNO: {1} SUL FASCICOLO: {2} AVVENUTA CON SUCCESSO", _datiFascicolo.NumeroProtocollo, _datiFascicolo.AnnoProtocollo, responseLeggi.ToList().Last());
        }

        public string GetNumeroFascicolo(string fascicolo)
        {
            var datiFascicoloRet = fascicolo.Split('-');

            if (datiFascicoloRet == null || datiFascicoloRet.Length < 2)
                throw new Exception(String.Format("NON E' POSSIBILE RECUPERARE IL NUMERO FASCICOLO, FASCICOLO: {0}", fascicolo));

            return datiFascicoloRet[1];

        }

        public string GetAnnoFascicolo(string fascicolo)
        {
            if (fascicolo.Length < 4)
                throw new Exception(String.Format("IL VALORE DEL FASCICOLO RESTITUITO NON HA UN FORMATO PREVISTO, VALORE FASCICOLO {0}, NON E' POSSIBILE RECUPERARE L'ANNO", fascicolo));

            return fascicolo.Substring(0, 4);

        }
    }
}

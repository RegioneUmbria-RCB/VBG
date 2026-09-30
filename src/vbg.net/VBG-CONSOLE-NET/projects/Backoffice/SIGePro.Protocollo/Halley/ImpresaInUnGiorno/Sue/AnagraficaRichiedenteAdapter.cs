using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Sue
{
    public class AnagraficaRichiedenteAdapter
    {
        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;

        public AnagraficaRichiedenteAdapter(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
        }

        public AnagraficaRichiedente Adatta()
        {
            var richiedente = _datiProtocollazioneService.Istanza.Richiedente;
            var indirizzi = new IndirizziConRecapitiAdapter(_datiProtocollazioneService, _logs);

            return new AnagraficaRichiedente
            {
                codicefiscale = richiedente.CODICEFISCALE,
                cognome = richiedente.NOMINATIVO,
                indirizzo = indirizzi.Adatta(),
                nome = richiedente.NOME,
                partitaiva = richiedente.PARTITAIVA
            };
        }
    }
}

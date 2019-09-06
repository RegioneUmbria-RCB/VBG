using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.EGrammata2.Fascicolazione
{
    public class FascicolazioneMovimento : IFascicolazione
    {
        LeggiProtocolloService _wrapper;
        ResolveDatiProtocollazioneService _datiProtoService;

        string _idFascicolo;
        string _numeroFascicolo;
        string _annoFascicolo;

        public FascicolazioneMovimento(ProtocollazioneRequestConfiguration configuration)
        {
            _wrapper = configuration.LeggiProtoWrapper;
            _datiProtoService = configuration.DatiProtoService;
            SetFascicolo();
        }

        public void SetFascicolo()
        {
            if (String.IsNullOrEmpty(_datiProtoService.Istanza.NUMEROPROTOCOLLO) || !_datiProtoService.Istanza.DATAPROTOCOLLO.HasValue)
                return;

            var requestAdapter = new LeggiProtocolloRequestAdapter();
            var request = requestAdapter.Adatta(_datiProtoService.Istanza.NUMEROPROTOCOLLO, _datiProtoService.Istanza.DATAPROTOCOLLO.Value.ToString("yyyy"));

            var responseLeggi = _wrapper.LeggiProtocollo(request);

            if (responseLeggi == null || responseLeggi.Documento == null || responseLeggi.Documento.Length == 0)
                return;

            _idFascicolo = responseLeggi.Documento[0].IdFascicolo;
            _annoFascicolo = responseLeggi.Documento[0].AnnoFasc;
            _numeroFascicolo = responseLeggi.Documento[0].ProgrFasc;
        }

        public string IdFascicolo
        {
            get { return _idFascicolo; }
        }

        public string NumeroFascicolo
        {
            get { return _numeroFascicolo; }
        }

        public string AnnoFascicolo
        {
            get { return _annoFascicolo; }
        }
    }
}

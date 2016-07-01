using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.DocEr.Fascicolazione
{
    public class FascicolazioneMovimento : IFascicolazione
    {
        Fascicolo _datiFascicolo;
        FascicolazioneService _fascWrapper;
        int _idProtocollo;

        public FascicolazioneMovimento(int idProtocollo, Fascicolo datiFascicolo, FascicolazioneService fascWrapper)
        {
            _idProtocollo = idProtocollo;
            _datiFascicolo = datiFascicolo;
            _fascWrapper = fascWrapper;
        }

        public DatiFascicolo Fascicola(FascicolazioneRequestAdapter requestAdapter)
        {

            if (String.IsNullOrEmpty(_datiFascicolo.NumeroFascicolo))
                throw new Exception("NUMERO FASCICOLO NON VALORIZZATO");

            var metadata = requestAdapter.Adatta(_datiFascicolo);
            _fascWrapper.Fascicola(_idProtocollo, metadata.SegnaturaSerializzata);

            return new DatiFascicolo
            {
                AnnoFascicolo = _datiFascicolo.AnnoFascicolo.ToString(),
                DataFascicolo = _datiFascicolo.DataFascicolo,
                NumeroFascicolo = _datiFascicolo.NumeroFascicolo
            };
        }
    }
}

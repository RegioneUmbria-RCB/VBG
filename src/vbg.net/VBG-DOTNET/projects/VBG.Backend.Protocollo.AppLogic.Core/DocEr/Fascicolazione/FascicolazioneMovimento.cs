using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.Fascicolazione
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

        public DatiFascicoloResponseType Fascicola(FascicolazioneRequestAdapter requestAdapter)
        {

            if (String.IsNullOrEmpty(_datiFascicolo.NumeroFascicolo))
                throw new Exception("NUMERO FASCICOLO NON VALORIZZATO");

            var metadata = requestAdapter.Adatta(_datiFascicolo);
            _fascWrapper.Fascicola(_idProtocollo, metadata.SegnaturaSerializzata);

            return new DatiFascicoloResponseType
            {
                AnnoFascicolo = _datiFascicolo.AnnoFascicolo.ToString(),
                DataFascicolo = _datiFascicolo.DataFascicolo,
                NumeroFascicolo = _datiFascicolo.NumeroFascicolo
            };
        }
    }
}

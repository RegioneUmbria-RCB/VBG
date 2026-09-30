using FascicolazioneItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli
{
    public class ArrayParamRicercaBuilder
    {
        private List<IParametroRicerca> _parametriService;

        public ArrayParamRicercaBuilder(GetElencoFascicoliRequest request)
        {
            this._parametriService = new List<IParametroRicerca>();

            if (request.Anno.HasValue)
            {
                this._parametriService.Add(new ParametroRicercaANNO(request.Anno.Value));
            }

            if (!string.IsNullOrEmpty(request.Classifica))
            {
                this._parametriService.Add(new ParametroRicercaTITOLARIO(request.Classifica));
            }

            if (!string.IsNullOrEmpty(request.Numero))
            {
                this._parametriService.Add(new ParametroRicercaCODICE(request.Numero));
            }

            if (!string.IsNullOrEmpty(request.Oggetto))
            {
                this._parametriService.Add(new ParametroRicercaOGGETTO(request.Oggetto));
            }
        }

        public arrayParamRicerca Build()
        {
            return new arrayParamRicerca
            {
                parametroRicerca = this
                                    ._parametriService
                                    .Select(x => x.Get())
                                    .ToArray()
            };
        }
    }
}

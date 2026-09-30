using System;
using System.Collections.Generic;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti
{


    internal class WsAllegatiEndoprocedimentiRepository : IAllegatiEndoprocedimentiRepository
    {
        private readonly EndoprocedimentiServiceCreator _serviceCreator;

        public WsAllegatiEndoprocedimentiRepository(EndoprocedimentiServiceCreator serviceCreator)
        {
            _serviceCreator = serviceCreator ?? throw new ArgumentNullException(nameof(serviceCreator));
        }

        public IEnumerable<AllegatiPerEndoprocedimentoDto> GetAllegatiProcedimenti(IEnumerable<int> codiciEndoSelezionati)
        {
            using (var ws = _serviceCreator.CreateClient())
            {
                return ws.Service.GetAllegatiEndoprocedimentiAreaRiservata(ws.Token, codiciEndoSelezionati.ToArray());
            }
        }
    }
}

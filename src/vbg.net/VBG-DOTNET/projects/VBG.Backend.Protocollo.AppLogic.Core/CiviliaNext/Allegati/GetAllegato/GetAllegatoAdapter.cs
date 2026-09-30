using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.GetAllegato
{
    public class GetAllegatoAdapter
    {
        private string _codiceLivelloOrganigramma;
        private long _idOperatore;
        private long _idPratica;
        public GetAllegatoAdapter(ParametriRegoleInfo parametri, long idProtocollo)
        {
            this._codiceLivelloOrganigramma = parametri.CodiceLivelloOrganigramma;
            this._idOperatore = parametri.IdOperatore;
            this._idPratica = idProtocollo;
        }

        public GetAllegatoRequest CreaRequest(long idAllegato)
        {
            return new GetAllegatoRequest
            {
                CodiceLivelloOrganigramma = this._codiceLivelloOrganigramma,
                GetByteArray = true,
                IdAllegato = idAllegato,
                IdOperatore = this._idOperatore
            };
        }
    }
}

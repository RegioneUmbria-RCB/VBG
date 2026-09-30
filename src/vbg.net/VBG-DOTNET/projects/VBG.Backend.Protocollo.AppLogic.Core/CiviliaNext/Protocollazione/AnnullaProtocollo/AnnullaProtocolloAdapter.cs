using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.AnnullaProtocollo
{
    public class AnnullaProtocolloAdapter
    {
        private readonly long _idCodiceAOO;
        private readonly long _idOperatore;
        private readonly long _idPratica;
        private readonly long _idRegistro;
        private readonly string _codiceLivelloOrganigramma;

        public AnnullaProtocolloAdapter( ParametriRegoleInfo parametri, long idProtocollo )
        {
            this._idCodiceAOO = parametri.IdCodiceAOO;
            this._idOperatore = parametri.IdOperatore;
            this._idPratica = idProtocollo;
            this._idRegistro = parametri.IdRegistro;
            this._codiceLivelloOrganigramma = parametri.CodiceLivelloOrganigramma;
        }

        public AnnullaProtocolloRequest CreaRequest(string motivoAnnullamento)
        {
            return new AnnullaProtocolloRequest
            {
                CodiceLivelloOrganigramma =this._codiceLivelloOrganigramma,
                CodiceRegistro = null,
                DataRegistrazione = null,
                IdCodiceAOO = this._idCodiceAOO,
                IdOperatore = this._idOperatore,
                IdPratica = this._idPratica,
                IdRegistro = this._idRegistro,
                MotivoAnnullamento = motivoAnnullamento,
                NumeroRegistrazione = null
            };
        }
    }
}

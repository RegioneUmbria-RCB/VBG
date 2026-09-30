
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.InvioProtocollo
{
    public class InvioProtocolloAdapter
    {
        private ParametriRegoleInfo _parametri;
        private DatiProtocolloIn _datiProtocollazione;
        private long _idPratica;

        public InvioProtocolloAdapter(ParametriRegoleInfo parametri, DatiProtocolloIn datiProtocollazione, long idPratica)
        {
            this._parametri = parametri;
            this._datiProtocollazione = datiProtocollazione;
            this._idPratica = idPratica;
        }

        public InvioProtocolloRequest CreaRequest()
        {
            return new InvioProtocolloRequest
            {
                Body = this._datiProtocollazione.Oggetto,
                Destinatari = null,
                GeneraSegnatura = false,
                IdCasellaEmail = String.IsNullOrEmpty(this._parametri.IdCasellaEmail) ? (long?)null : Convert.ToInt32(this._parametri.IdCasellaEmail),
                IdOperatore = this._parametri.IdOperatore,
                IdPratica = this._idPratica
            };
        }
    }
}

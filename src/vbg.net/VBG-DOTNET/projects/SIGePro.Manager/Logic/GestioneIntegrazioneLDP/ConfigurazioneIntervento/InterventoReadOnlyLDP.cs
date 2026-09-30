using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.ConfigurazioneIntervento
{
    internal class InterventoReadOnlyLDP : InterventoReadOnly, IInterventoLDP
    {
        internal InterventoReadOnlyLDP(AlberoProc intervento)
            :base(intervento)
		{
		}

        public int? LdpTipoOccupazione
        {
            get { return this._intervento.LdpTipOccupazione; }
        }

        public int? LdpTipoPeriodo
        {
            get { return this._intervento.LdpTipPeriodo; }
        }

        public int? LdpTipoGeometria
        {
            get { return this._intervento.LdpTipGeometria; }
        }

        public string LdpDolQueryString
        {
            get { return this._intervento.LdpDolQString; }
        }
    }
}

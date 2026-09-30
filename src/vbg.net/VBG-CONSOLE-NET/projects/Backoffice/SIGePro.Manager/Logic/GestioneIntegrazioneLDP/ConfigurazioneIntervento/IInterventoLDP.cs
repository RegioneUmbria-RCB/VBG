using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.ConfigurazioneIntervento
{
    public interface IInterventoLDP : IIntervento
    {
        int? LdpTipoOccupazione { get; }
        int? LdpTipoPeriodo { get; }
        int? LdpTipoGeometria { get; }
    }
}

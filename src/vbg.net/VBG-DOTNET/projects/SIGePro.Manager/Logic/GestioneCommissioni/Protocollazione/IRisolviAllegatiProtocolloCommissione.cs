using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione
{
    public interface IRisolviAllegatiProtocolloCommissione
    {
        IEnumerable<AllegatoProtocolloCommissione> Allegati { get; }
    }
}
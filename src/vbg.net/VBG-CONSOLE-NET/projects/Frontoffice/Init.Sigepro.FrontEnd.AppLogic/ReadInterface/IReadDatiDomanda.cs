using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ReadInterface
{
    public interface IReadDatiDomanda
    {
        PresentazioneIstanzaDataKey DomandaDataKey { get; }
        IDomandaOnlineReadInterface Domanda { get; }
    }
}

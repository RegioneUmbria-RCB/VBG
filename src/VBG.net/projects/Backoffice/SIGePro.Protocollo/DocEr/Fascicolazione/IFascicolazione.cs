using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.DocEr.Fascicolazione
{
    public interface IFascicolazione
    {
        DatiFascicolo Fascicola(FascicolazioneRequestAdapter requestAdapter);
    }
}

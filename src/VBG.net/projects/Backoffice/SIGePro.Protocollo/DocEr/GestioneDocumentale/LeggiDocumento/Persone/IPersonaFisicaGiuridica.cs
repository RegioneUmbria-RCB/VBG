using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Persone
{
    public interface IPersonaFisicaGiuridica
    {
        MittDestOut GetPersona();
    }
}

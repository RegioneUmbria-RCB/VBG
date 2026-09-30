using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.DocArea.Interfaces
{
    public interface IDocAreaSegnaturaPersoneBuilder
    {
        List<Persona> GetMittenteDestinatario(IDatiProtocollo protoIn, bool usaDenominazionePg, string indirizzoTelematico);
    }
}

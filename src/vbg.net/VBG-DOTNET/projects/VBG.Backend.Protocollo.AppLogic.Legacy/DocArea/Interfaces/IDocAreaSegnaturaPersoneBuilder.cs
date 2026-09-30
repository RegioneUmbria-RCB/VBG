using System.Collections.Generic;

using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces
{
    public interface IDocAreaSegnaturaPersoneBuilder
    {
        List<Persona> GetMittenteDestinatario(IDatiProtocollo protoIn, bool usaDenominazionePg, string indirizzoTelematico);
    }
}

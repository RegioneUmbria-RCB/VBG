using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Sicraweb.Services;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Allegati
{
    public static class ProtocolloAllegatiExtensions
    {
        public static Documento GetDocumento(this ProtocolloAllegati allegato, ProtocolloService wrapper)
        {
            var val = new AllegatiValidation();
            val.Validate(allegato);

            var id = wrapper.InserisciDocumento(allegato);

            return new Documento { id = id, nome = allegato.NOMEFILE };
        }
    }
}

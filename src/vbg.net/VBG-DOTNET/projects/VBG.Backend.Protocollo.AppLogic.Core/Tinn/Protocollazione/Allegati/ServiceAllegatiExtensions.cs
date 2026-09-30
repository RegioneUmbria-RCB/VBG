using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Segnatura;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.Allegati
{
    public static class ServiceAllegatiExtensions
    {
        public static Documento ValorizzaeInviaDocumentoAllegato(this ProtocolloService srv, ProtocolloAllegati allegato, string tipoAllegato)
        {
            srv.InserisciAllegato(allegato);

            var rVal = new Documento
            {
                id = allegato.ID,
                DescrizioneDocumento = new DescrizioneDocumento { Text = new string[] { allegato.Descrizione } },
                nome = allegato.NOMEFILE
            };

            if (!String.IsNullOrEmpty(tipoAllegato))
                rVal.TipoDocumento = new TipoDocumento { Text = new string[] { tipoAllegato } };

            return rVal;
        }
    }
}

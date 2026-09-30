using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.AcarisDocumentServicePort;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document
{
    public interface IDocument
    {
        InfoRichiestaCreazione Documento { get; }

        List<Annotazione> Annotazioni { get; }

    }
}

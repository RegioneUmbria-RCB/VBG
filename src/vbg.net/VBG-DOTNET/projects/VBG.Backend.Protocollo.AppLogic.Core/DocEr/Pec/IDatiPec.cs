using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.Pec
{
    public interface IDatiPec
    {
        string Oggetto { get; }
        FlussoType Flusso { get; }
        IntestazioneTypeDestinatari GetDestinatari();
        DocumentoType GetDocumentoPrincipale();
        DocumentoType[] GetRelated();
        DocumentoType[] GetAnnessi();
        DocumentoType[] GetAnnotazioni();
    }
}

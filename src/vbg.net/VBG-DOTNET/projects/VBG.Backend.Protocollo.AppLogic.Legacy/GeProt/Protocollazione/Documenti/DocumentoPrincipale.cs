using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Protocollazione.Documenti
{
    public class DocumentoPrincipale : IDocumenti
    {
        ProtocolloAllegati _allegato;

        public DocumentoPrincipale(ProtocolloAllegati allegato)
        {
            _allegato = allegato;
        }

        public Documento DocPrincipale
        {
            get
            {
                return new Documento
                {
                    nome = _allegato.NOMEFILE,
                    tipoMIME = _allegato.MimeType,
                    tipoRiferimento = DocumentoTipoRiferimento.cartaceo
                };
            }
        }

        public Documento[] Allegati
        {
            get { return null; }
        }
    }
}

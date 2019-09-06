using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione.Documenti
{
    public class DocumentiAllegati : IDocumenti
    {
        IEnumerable<ProtocolloAllegati> _allegati;

        public DocumentiAllegati(IEnumerable<ProtocolloAllegati> allegati)
        {
            _allegati = allegati;
        }

        public Documento DocPrincipale
        {
            get
            {
                return new Documento
                {
                    nome = _allegati.First().NOMEFILE,
                    tipoMIME = _allegati.First().MimeType,
                    tipoRiferimento = DocumentoTipoRiferimento.cartaceo
                }; ;
            }
        }

        public Documento[] Allegati
        {
            get
            {
                return _allegati.Skip(1).Select(x => new Documento
                {
                    nome = x.NOMEFILE,
                    tipoMIME = x.MimeType,
                    tipoRiferimento = DocumentoTipoRiferimento.cartaceo
                }).ToArray();
            }
        }
    }
}

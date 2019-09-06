using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Protocollazione
{
    public class ProtocollazioneAllegatiAdapter
    {
        public ProtocollazioneAllegatiAdapter()
        {

        }

        public Descrizione Adatta(ProtocollazioneServiceWrapper protocollazioneSrv, IEnumerable<ProtocolloAllegati> allegati, string tipoDocPrincipale, string tipoDocAllegato)
        {
            if (allegati.Count() == 0)
            {
                throw new Exception("NON E' PRESENTE ALCUN ALLEGATO NELLA RICHIESTA A PROTOCOLLAZIONE, DEVE ESSERNE PRESENTE ALMENO UNO");
            }

            var docPrincipale = allegati.First();
            var idDocPrincipale = protocollazioneSrv.InserimentoAllegato(docPrincipale);

            var descrizione = new Descrizione
            {
                Documento = new Documento
                {
                    id = idDocPrincipale,
                    nome = docPrincipale.NOMEFILE,
                    DescrizioneDocumento = new DescrizioneDocumento { Text = new string[] { docPrincipale.Descrizione } }
                }
            };

            if (!String.IsNullOrEmpty(tipoDocPrincipale))
            {
                descrizione.Documento.TipoDocumento = new TipoDocumento { Text = new string[] { tipoDocPrincipale } };
            }

            if (allegati.Count() > 1)
            {
                var allegatiSecondari = allegati.Skip(1).ToList();

                descrizione.Allegati = allegatiSecondari.Select(x => 
                {
                    var id = protocollazioneSrv.InserimentoAllegato(x);

                    var doc = new Documento
                    {
                        id = id,
                        nome = x.NOMEFILE,
                        DescrizioneDocumento = new DescrizioneDocumento { Text = new string[] { x.Descrizione } }
                    };

                    if (!String.IsNullOrEmpty(tipoDocAllegato))
                    {
                        doc.TipoDocumento = new TipoDocumento { Text = new string[] { tipoDocAllegato } };
                    }

                    return doc;

                }).ToArray();
            }
            
            return descrizione;
        }
    }
}

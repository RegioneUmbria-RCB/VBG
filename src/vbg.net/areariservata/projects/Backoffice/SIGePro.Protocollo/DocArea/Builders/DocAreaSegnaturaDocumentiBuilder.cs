using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.DocArea.Builders
{
    /// <summary>
    /// La classe è stata chiamata DocAreaSegnaturaDocumentiBuilder perchè è la parte riguardante i documenti della segnatura da inviare al web service, in realtà però 
    /// l'elemento che contiene i documenti si chiama "Descrizione" ed è per questo che la proprietà che restituisce i dati si chiama SegnaturaDescrizione
    /// </summary>
    public class DocAreaSegnaturaDocumentiBuilder
    {
        List<ProtocolloAllegati> _listAllegati;
        private string _tipoDocPrincipale;
        private string _tipoDocAllegato;

        public readonly Descrizione SegnaturaDescrizione;

        public DocAreaSegnaturaDocumentiBuilder(List<ProtocolloAllegati> listAllegati, string tipoDocPrincipale, string tipoDocAllegato)
        {
            _listAllegati = listAllegati;
            _tipoDocPrincipale = tipoDocPrincipale;
            _tipoDocAllegato = tipoDocAllegato;
            SegnaturaDescrizione = GetDescrizione();
        }

        private Descrizione GetDescrizione()
        {
            var descrizione = new Descrizione();
            if (_listAllegati.Count > 0)
            {
                var docPrincipale = _listAllegati.First();
                var documento = new Documento
               {
                   id = docPrincipale.ID,
                   nome = docPrincipale.NOMEFILE,
                   DescrizioneDocumento = new DescrizioneDocumento { Text = new string[] { docPrincipale.Descrizione } }
               };

                if (!String.IsNullOrEmpty(_tipoDocPrincipale))
                    documento.TipoDocumento = new TipoDocumento { Text = new string[] { _tipoDocPrincipale } };

                descrizione.Documento = documento;

                if (_listAllegati.Count > 1)
                {
                    var allegati = _listAllegati.Skip(1).ToList();

                    var docs = new List<Documento>();
                    foreach (var all in allegati)
                    {
                        var doc = new Documento
                        {
                            id = all.ID,
                            nome = all.NOMEFILE,
                            DescrizioneDocumento = new DescrizioneDocumento { Text = new string[] { all.Descrizione } }
                        };

                        if (!String.IsNullOrEmpty(_tipoDocPrincipale))
                            doc.TipoDocumento = new TipoDocumento { Text = new string[] { _tipoDocAllegato } };

                        docs.Add(doc);
                    }

                    descrizione.Allegati = docs.ToArray();
                }
            }
            return descrizione;
        }
    }
}

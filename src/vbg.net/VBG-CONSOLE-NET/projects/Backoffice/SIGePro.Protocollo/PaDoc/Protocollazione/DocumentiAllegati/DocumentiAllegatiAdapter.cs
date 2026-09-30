using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.PaDoc.Verticalizzazioni;
using System.IO;
using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.DocumentiAllegati
{
    public class DocumentiAllegatiAdapter
    {
        List<ProtocolloAllegati> _allegati;
        VerticalizzazioniConfiguration _vert;
        string _idComuneAlias;
        string _software;

        public DocumentiAllegatiAdapter(List<ProtocolloAllegati> allegati, VerticalizzazioniConfiguration vert, string idComuneAlias, string software)
        {
            _allegati = allegati;
            _vert = vert;
            _idComuneAlias = idComuneAlias;
            _software = software;
        }

        public Descrizione Adatta()
        {

            var res = new Descrizione { Item = GetDocumentoPrincipale() };
            var allegati = GetAllegati();

            if (allegati != null)
                res.Allegati = allegati;

            return res;
        }

        private object GetDocumentoPrincipale()
        {
            if (_allegati.Count == 0)
                return new TestoDelMessaggio();

            var allegatoPrincipale = _allegati.First();
            return GetDocumento(allegatoPrincipale);
        }

        private Documento GetDocumento(ProtocolloAllegati allegato)
        {
            var mac = Md5Utils.GetMd5(String.Concat(_idComuneAlias, allegato.CODICEOGGETTO, "file", "secret"));
            var downloadUrl = String.Format("{0}/{1}/f/{2}/mac/{3}/{4}", _vert.UrlResponseService, _idComuneAlias, allegato.CODICEOGGETTO, mac, allegato.NOMEFILE);

            var vertFsCmis = new VerticalizzazioneFilesystemCmis(_idComuneAlias, _software);
            
            if (vertFsCmis.Attiva && !String.IsNullOrEmpty(allegato.Percorso) && allegato.Percorso.StartsWith("workspace://"))
                downloadUrl = allegato.Percorso;

            return new Documento
            {
                tipoRiferimento = DocumentoTipoRiferimento.telematico,
                nome = allegato.NOMEFILE,
                CollocazioneTelematica = new CollocazioneTelematica { Text = new string[] { downloadUrl } },
                TitoloDocumento = new TitoloDocumento { Text = new string[] { allegato.Descrizione } }
            };        
        }

        private Documento[] GetAllegati()
        {
            if (_allegati.Count < 2)
                return null;

            var docsAllegati = _allegati.Skip(1).Select(x => GetDocumento(x));
            return docsAllegati.ToArray();
        }
    }
}

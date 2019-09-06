using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System.IO;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.Common;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{
    public class RiepilogoDomanda
    {
        IOggettiService _oggettiService;
        IHtmlToPdfFileConverter _fileConverter;
        SostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;
        IDatiDinamiciRepository _datiDinamiciRepository;

        public RiepilogoDomanda(IOggettiService oggettiService, SostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService, 
                                IHtmlToPdfFileConverter fileConverter, IDatiDinamiciRepository datiDinamiciRepository)
        {
            this._oggettiService = oggettiService;
            this._fileConverter = fileConverter;
            this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
            this._datiDinamiciRepository = datiDinamiciRepository;
        }

        public BinaryFile GeneraRiepilogoDomandaOnline(DomandaOnline domanda, int idFileModello, bool aggiungiPdfSchedeAListaAllegati, bool dumpXml = false)
        {
            var oggetto = _oggettiService.GetById(idFileModello);
            var idDomanda = domanda.DataKey.ToString();

            if (oggetto == null)
                throw new ArgumentException("L'oggetto " + idFileModello + " non è stato trovato");

            var istanzaAdapter = new IstanzaSigeproAdapter(domanda.ReadInterface, aggiungiPdfSchedeAListaAllegati);
            var istanzaXml = istanzaAdapter.AdattaToString(idDomanda);

            if (dumpXml)
            {
                DumpDatiDomanda(idDomanda, istanzaXml);
            }

            var risultatoTrasformazione = new XslFile(oggetto.FileContent).Trasforma(istanzaXml);

            // Nel caso in cui il modello contenga il segnaposto delle schede dinamiche utilizzo il servizio
            // per leggerle in formato html
            var reader = new DomandaOnlineDatiDinamiciReader(domanda, this._datiDinamiciRepository);
            var risultatoTrasformazioneConSchede = _sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogo(reader, risultatoTrasformazione);

            var nomeFile = String.Format("modello-domanda.{0}.pdf", idDomanda);
            var pdf = this._fileConverter.Converti(nomeFile, risultatoTrasformazioneConSchede);

            return pdf;
        }
        
        private static void DumpDatiDomanda(string idDomanda, string istanzaXml)
        {
            if (HttpContext.Current != null)
            {
                var path = HttpContext.Current.Server.MapPath("~/Logs/");
                path = Path.Combine(path, $"riepilogo_{idDomanda}.xml");
                using (var fs = File.Open(path, FileMode.CreateNew))
                {
                    fs.Write(Encoding.UTF8.GetBytes(istanzaXml), 0, Encoding.UTF8.GetByteCount(istanzaXml));
                }
            }
        }
    }
}

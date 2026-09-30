using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneDocumentiDomanda
{
    public class GenerazioneDocumentoDomandaService
    {
        private readonly FileConverterService _fileConverter;
        private readonly IOggettiService _oggettiService;
        private readonly ISalvataggioDomandaStrategy _caricamentoDomandaStrategy;

        public GenerazioneDocumentoDomandaService(FileConverterService fileConverter, IOggettiService oggettiService, ISalvataggioDomandaStrategy caricamentoDomandaStrategy)
        {
            this._fileConverter = fileConverter;
            this._oggettiService = oggettiService;
            this._caricamentoDomandaStrategy = caricamentoDomandaStrategy;
        }

        public BinaryFile GeneraDocumento(int codiceOggetto, int idDomanda, FormatoConversioneEnum formatoConversione)
        {
            var domanda = _caricamentoDomandaStrategy.GetById(idDomanda);

            return GeneraDocumento(codiceOggetto, domanda, formatoConversione);
        }

        public BinaryFile GeneraDocumento(int codiceOggetto, DomandaOnline domanda, FormatoConversioneEnum formatoConversione)
        {
            var oggetto = _oggettiService.GetById(codiceOggetto);
            
            if (oggetto == null)
                throw new ArgumentException("L'oggetto " + codiceOggetto + " non è stato trovato");

            var istanzaAdapter = new IstanzaSigeproAdapter(domanda.ReadInterface);
            
            var istanzaXml = istanzaAdapter.AdattaToString(domanda.DataKey.ToString());
            
            // Converto l'xsl della domanda in formato UTF-8
            var oggettoXsl = Encoding.UTF8.GetString(oggetto.FileContent);

            var risultatoTrasformazione = Encoding.UTF8.GetString(_fileConverter.ApplicaTrasformazione(istanzaXml, oggettoXsl));

            var fileDaConvertire = Encoding.UTF8.GetBytes(risultatoTrasformazione);

            if (RimuoviBomDaRiepilogo())
            {
                var bytes = fileDaConvertire.Take(3).ToArray();

                if (bytes[0] == 0xEF &&
                     bytes[1] == 0xBB &&
                     bytes[2] == 0xBF)
                {
                    fileDaConvertire = fileDaConvertire.Skip(3).ToArray();
                }
            }

            var modelloCompilato = _fileConverter.Converti("modello." + oggetto.Estensione.ToUpper(), fileDaConvertire, formatoConversione);

            modelloCompilato.FileName = Path.GetFileNameWithoutExtension(oggetto.FileName) + Path.GetExtension(modelloCompilato.FileName);

            return modelloCompilato;
        }

        private bool RimuoviBomDaRiepilogo()
        {
            var val = ConfigurationManager.AppSettings["RimuoviBOMDaRiepilogo"];

            if (String.IsNullOrEmpty(val))
            {
                return true;
            }

            return val.ToUpperInvariant() == "TRUE";
        }
    }
}

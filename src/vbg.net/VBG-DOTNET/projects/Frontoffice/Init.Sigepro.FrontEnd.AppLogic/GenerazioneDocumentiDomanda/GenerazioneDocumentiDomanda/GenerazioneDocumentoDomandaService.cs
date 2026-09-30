using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
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
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly IAppConfigurationReader _appConfigurationReader;

        public GenerazioneDocumentoDomandaService(FileConverterService fileConverter, IOggettiService oggettiService, ISalvataggioDomandaStrategy caricamentoDomandaStrategy,
            IIstanzaSigeproAdapterService istanzaSigeproAdapterService, IAppConfigurationReader appConfigurationReader)
        {
            this._fileConverter = fileConverter;
            this._oggettiService = oggettiService;
            this._caricamentoDomandaStrategy = caricamentoDomandaStrategy;
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._appConfigurationReader = appConfigurationReader;
        }

        public BinaryFile GeneraDocumento(int codiceOggetto, int idDomanda, FormatoConversioneEnum formatoConversione)
        {
            var domanda = this._caricamentoDomandaStrategy.GetById(idDomanda);

            return this.GeneraDocumento(codiceOggetto, domanda, formatoConversione);
        }


        public BinaryFile GeneraDocumento(int codiceOggetto, DomandaOnline domanda, FormatoConversioneEnum formatoConversione)
        {
            var oggetto = this._oggettiService.GetById(codiceOggetto);

            if (oggetto == null)
                throw new ArgumentException("L'oggetto " + codiceOggetto + " non è stato trovato");

            var istanzaXml = this._istanzaSigeproAdapterService.ToIstanzaBackoffice(domanda.ReadInterface).ToXmlModelloRiepilogo(domanda.DataKey.ToString());

            // Converto l'xsl della domanda in formato UTF-8
            var oggettoXsl = Encoding.UTF8.GetString(oggetto.FileContent);

            var risultatoTrasformazione = Encoding.UTF8.GetString(this._fileConverter.ApplicaTrasformazione(istanzaXml, oggettoXsl));

            var fileDaConvertire = Encoding.UTF8.GetBytes(risultatoTrasformazione);

            if (this.RimuoviBomDaRiepilogo())
            {
                var bytes = fileDaConvertire.Take(3).ToArray();

                if (bytes[0] == 0xEF &&
                     bytes[1] == 0xBB &&
                     bytes[2] == 0xBF)
                {
                    fileDaConvertire = fileDaConvertire.Skip(3).ToArray();
                }
            }

            var modelloCompilato = this._fileConverter.Converti("modello." + oggetto.Estensione.ToUpper(), fileDaConvertire, formatoConversione);

            modelloCompilato.FileName = Path.GetFileNameWithoutExtension(oggetto.FileName) + Path.GetExtension(modelloCompilato.FileName);

            return modelloCompilato;
        }

        private bool RimuoviBomDaRiepilogo()
        {
            var val = this._appConfigurationReader.GetSetting("RimuoviBOMDaRiepilogo");

            if (String.IsNullOrEmpty(val))
            {
                return true;
            }

            return val.ToUpperInvariant() == "TRUE";
        }
    }
}

using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiIstanze;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede.FileConverter;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneInterventi;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti;
using GeneratoreRiepiloghiHtml.AppLogic.Visura;
using System.Configuration;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneCertificatoInvio
{

    public class CertificatoDiInvioService
    {
        private readonly IVisuraService _visuraService;
        private readonly IInterventiService _interventiService;
        private readonly IOggettiService _oggettiService;
        private readonly IHtmlToPdfFileConverter _fileConverter;
        private readonly ILogger<CertificatoDiInvioService> _logger;

        public CertificatoDiInvioService(IVisuraService visuraService, IInterventiService interventiService, IOggettiService oggettiService, IHtmlToPdfFileConverter fileConverter, ILogger<CertificatoDiInvioService> logger)
        {
            this._visuraService = visuraService;
            this._interventiService = interventiService;
            this._oggettiService = oggettiService;
            this._fileConverter = fileConverter;
            this._logger = logger;
        }


        public async Task<BinaryFile> GeneraCertificatoDiInvioAsync(int codiceIstanza)
        {
            var istanza = await this._visuraService.GetDettaglioPraticaAsync(codiceIstanza);
            var codiceOggetto = await this._interventiService.GetCodiceOggettoCertificatoDiInvioDaIdInterventoAsync(Convert.ToInt32(istanza.CODICEINTERVENTOPROC));

            if (codiceOggetto is null)
            {
                this._logger.LogError("Non è stato possibile trovato un template di certificato di invio per l'istanza {codiceIstanza}", codiceIstanza);

                throw new ConfigurationErrorsException($"Non è stato possibile trovato un template di certificato di invio per l'istanza {codiceIstanza}");
            }

            var fileRiepilogo = await this._oggettiService.GetByIdAsync(codiceOggetto.Value);
            var xslTemplate = UnknownEncodingToString.GetString(fileRiepilogo.FileContent);
            var xmlIstanza = istanza.ToXmlModelloRiepilogo();

            var htmlCertificato = new XslFile(xslTemplate).Trasforma(xmlIstanza);
            var nomeFile = $"certificato-invio-pratica-{istanza.CODICEISTANZA}.pdf";

            return await this._fileConverter.ConvertiAsync(nomeFile, htmlCertificato);
        }
    }
}

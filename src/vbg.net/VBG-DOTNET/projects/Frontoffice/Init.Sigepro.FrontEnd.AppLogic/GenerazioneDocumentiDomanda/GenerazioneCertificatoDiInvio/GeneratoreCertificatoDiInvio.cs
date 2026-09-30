using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.Common;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.GestioneQrCode;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.LetturaXmlDomandaBackend;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using log4net;
using System;
using System.IO;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio
{
    public class GeneratoreCertificatoDiInvio
    {
        private static class Constants
        {
            public const string SegnapostoVisuraStc = "<!--VISURA_STC-->";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(GeneratoreCertificatoDiInvio));

        private readonly RiepilogoDomandaReader _riepilogoDomandaReader;
        private readonly IHtmlToPdfFileConverter _fileConverter;
        private readonly IHtmlToPdfAsyncFileConverter _asyncFileConverter;
        private readonly XmlDomandaBackendStrategy _visuraStrategy;
        private readonly ISostituzioneSegnapostoQrCode _sostituzioneQrCode;
        private readonly IConfigurazione<ParametriGenerazioneCertificatoInvio> _configurazione;
        private readonly IStrategiaIndividuazioneCertificatoInvio _strategiaIndividuazioneRiepilogo;
        private readonly IPathMapper _pathMapper;
        private readonly IAppConfigurationReader _appConfigurationReader;

        public GeneratoreCertificatoDiInvio(RiepilogoDomandaReader riepilogoDomandaReader, IHtmlToPdfFileConverter fileConverter, IHtmlToPdfAsyncFileConverter asyncFileConverter, XmlDomandaBackendStrategy visuraStrategy,
                                            ISostituzioneSegnapostoQrCode sostituzioneQrCode, IConfigurazione<ParametriGenerazioneCertificatoInvio> configurazione,
                                            IStrategiaIndividuazioneCertificatoInvio strategiaIndividuazioneRiepilogo, IPathMapper pathMapper,
                                            IAppConfigurationReader appConfigurationReader)
        {
            this._riepilogoDomandaReader = riepilogoDomandaReader;
            this._fileConverter = fileConverter;
            this._asyncFileConverter = asyncFileConverter;
            this._visuraStrategy = visuraStrategy;
            this._sostituzioneQrCode = sostituzioneQrCode;
            this._configurazione = configurazione;
            this._strategiaIndividuazioneRiepilogo = strategiaIndividuazioneRiepilogo;
            this._pathMapper = pathMapper;
            this._appConfigurationReader = appConfigurationReader;
        }
#if NET48_OR_GREATER
        public BinaryFile? GeneraCertificatoDiInvio(int idDomandaBackoffice)
        {
            if (!this._strategiaIndividuazioneRiepilogo.IsCertificatoDefinito(idDomandaBackoffice))
            {
                this._log.Error($"non è stato possibile generare un certificato di invio per l'istanza con codice istanza {idDomandaBackoffice}");
                return null;
            }

            var xslTemplate = this._riepilogoDomandaReader.Read(idDomandaBackoffice, this._strategiaIndividuazioneRiepilogo);
            var xmlIstanza = this.LeggiXmlDomanda(idDomandaBackoffice, xslTemplate);

            this.DumpXmlIstanza(xmlIstanza);

            var htmlCertificato = new XslFile(xslTemplate).Trasforma(xmlIstanza);

            htmlCertificato = this._sostituzioneQrCode.ProcessaCertificato(Convert.ToInt32(idDomandaBackoffice), htmlCertificato);

            return this._fileConverter.Converti(this._configurazione.Parametri.NomeFile, htmlCertificato);
        }
#endif
        public async Task<BinaryFile?> GeneraCertificatoDiInvioAsync(int idDomandaBackoffice)
        {
            if (!this._strategiaIndividuazioneRiepilogo.IsCertificatoDefinito(idDomandaBackoffice))
            {
                this._log.Error($"non è stato possibile generare un certificato di invio per l'istanza con codice istanza {idDomandaBackoffice}");
                return null;
            }

            var xslTemplate = this._riepilogoDomandaReader.Read(idDomandaBackoffice, this._strategiaIndividuazioneRiepilogo);
            var xmlIstanza = this.LeggiXmlDomanda(idDomandaBackoffice, xslTemplate);

            this.DumpXmlIstanza(xmlIstanza);

            var htmlCertificato = new XslFile(xslTemplate).Trasforma(xmlIstanza);

            htmlCertificato = this._sostituzioneQrCode.ProcessaCertificato(Convert.ToInt32(idDomandaBackoffice), htmlCertificato);

            return await this._asyncFileConverter.ConvertiAsync(this._configurazione.Parametri.NomeFile, htmlCertificato);
        }

        private string LeggiXmlDomanda(int idDomandaBackoffice, string xslTemplate)
        {
            var tipoVisura = XmlDomandaBackendStrategy.TipoVisura.Vbg;

            if (xslTemplate.Contains(Constants.SegnapostoVisuraStc))
            {
                tipoVisura = XmlDomandaBackendStrategy.TipoVisura.Stc;
            }

            return this._visuraStrategy.GetXml(tipoVisura, idDomandaBackoffice);
        }

        private void DumpXmlIstanza(string xmlIstanza)
        {
            var dumpXmlIstanzaCaricata = this._appConfigurationReader.GetSetting("DumpXmlIstanzaDuranteGenerazioneCertificato");

            if (String.IsNullOrEmpty(dumpXmlIstanzaCaricata))
                return;

            if (!this._pathMapper.IsPathMappingSupported)
                return;

            var path = this._pathMapper.MapPath("~/Logs");
            path = Path.Combine(path, "dumpIstanzaCertificato_" + Guid.NewGuid().ToString() + ".xml");

            File.WriteAllText(path, xmlIstanza);
        }
    }
}

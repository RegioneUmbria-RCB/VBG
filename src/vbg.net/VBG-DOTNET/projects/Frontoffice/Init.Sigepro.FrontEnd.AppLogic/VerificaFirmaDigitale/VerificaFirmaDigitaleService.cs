//using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
//using Init.Sigepro.FrontEnd.AppLogic.WsVerificaFirmaDigitale;
//using log4net;
//using System;
//using System.Linq;
//using System.Security.Cryptography.X509Certificates;
//using System.Threading.Tasks;

//namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
//{
//    public class VerificaFirmaDigitaleService : IVerificaFirmaDigitaleService, IFirmaDigitaleMetadataService
//    {
//        private readonly ILog _log = LogManager.GetLogger(typeof(VerificaFirmaDigitaleService));
//        private readonly FirmaDigitaleServiceCreator _serviceCreator;
//        private readonly IOggettiService _oggettiService;

//        public VerificaFirmaDigitaleService(FirmaDigitaleServiceCreator serviceCreator, IOggettiService oggettiService)
//        {
//            if (serviceCreator == null)
//                throw new ArgumentNullException(nameof(serviceCreator));

//            if (oggettiService == null)
//                throw new ArgumentNullException(nameof(oggettiService));
//            //Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

//            this._serviceCreator = serviceCreator;
//            this._oggettiService = oggettiService;
//        }

//        public EsitoVerificaFirma EstraiDatiFirma(BinaryFile fileFirmato)
//        {
//            try
//            {
//                return this._serviceCreator.Call(ws =>
//                {
//                    var validationResult = ws.Service.validateDocument(new wsDocument
//                    {
//                        binary = fileFirmato.FileContent,
//                        name = fileFirmato.
//                        FileName
//                    }
//                    , null, false);

//                    return new EsitoVerificaFirma(validationResult);
//                });
//            }
//            catch (Exception ex)
//            {
//                this._log.ErrorFormat("FirmaDigitaleManager.EstraiDatiFirma -> Errore la lettura dei dati del certificato: {0}", ex.ToString());

//                return null;
//            }
//        }

//        public BinaryFile GetFileInChiaro(BinaryFile fileFirmato)
//        {
//            try
//            {
//                return this._serviceCreator.Call(ws =>
//                {
//                    var validationResult = ws.Service.validateDocument(
//                        new wsDocument
//                        {
//                            binary = fileFirmato.FileContent,
//                            name = fileFirmato.FileName
//                        }, null, true);

//                    return new BinaryFile(validationResult.content.name, "application/octet-stream", validationResult.content.binary);

//                });

//            }
//            catch (Exception ex)
//            {
//                this._log.ErrorFormat("FirmaDigitaleManager.LeggiCertificato -> Errore la lettura dei dati del certificato: {0}", ex.ToString());

//                return null;
//            }
//        }

//        public EsitoVerificaFirmaDigitale VerificaFirmaDigitale(BinaryFile file)
//        {

//            try
//            {
//                return this._serviceCreator.Call(ws =>
//                {
//                    // solleva un eccezizone se la firma non è valida
//                    var validationResult = ws.Service.validateDocument(new wsDocument
//                    {
//                        binary = file.FileContent,
//                        name = file.FileName
//                    }, null, false);

//                    if (validationResult == null)
//                        return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.Errore);

//                    if (!validationResult.IsFirmaValida())
//                        return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.FirmaNonValida);

//                    if (validationResult.IsCertificatoRevocato())
//                        return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.CertificatoRevocato);

//                    var nomiSoggettiFirmatari = validationResult.signatureInformationList
//                                                                .Where(x => x.signatureLevelAnalysis.levelBES != null && x.signatureLevelAnalysis.levelBES.signingCertificate != null)
//                                                                .Select(x => new X509Certificate2(x.signatureLevelAnalysis.levelBES.signingCertificate).Subject);

//                    return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.FirmaValida, nomiSoggettiFirmatari);
//                });
//            }
//            catch (Exception ex)
//            {
//                this._log.ErrorFormat("Errore durante la verifica della firma digitale: {0}", ex.ToString());

//                return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.Errore);
//            }
//        }

//        public Task<EsitoVerificaFirmaDigitale> VerificaFirmaDigitaleAsync(BinaryFile file)
//        {
//            try
//            {
//                return this._serviceCreator.CallAsync(async (ws) =>
//                {
//                    // solleva un eccezizone se la firma non è valida
//                    var validationResult = await ws.Service.validateDocumentAsync(new wsDocument
//                    {
//                        binary = file.FileContent,
//                        name = file.FileName
//                    }, null, false);

//                    if (validationResult == null)
//                        return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.Errore);

//                    if (!validationResult.response.IsFirmaValida())
//                        return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.FirmaNonValida);

//                    if (validationResult.response.IsCertificatoRevocato())
//                        return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.CertificatoRevocato);

//                    var nomiSoggettiFirmatari = validationResult.response.signatureInformationList
//                                                                .Where(x => x.signatureLevelAnalysis.levelBES != null && x.signatureLevelAnalysis.levelBES.signingCertificate != null)
//                                                                .Select(x => new X509Certificate2(x.signatureLevelAnalysis.levelBES.signingCertificate).Subject);

//                    return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.FirmaValida, nomiSoggettiFirmatari);
//                });
//            }
//            catch (Exception ex)
//            {
//                this._log.ErrorFormat("Errore durante la verifica della firma digitale: {0}", ex.ToString());

//                return Task.FromResult(new EsitoVerificaFirmaDigitale(StatoVerificaFirma.Errore));
//            }
//        }

//        public async Task<EsitoVerificaFirmaDigitale> VerificaFirmaDigitaleAsync(int codiceOggetto)
//        {
//            var file = await this._oggettiService.GetByIdAsync(codiceOggetto);

//            return await this.VerificaFirmaDigitaleAsync(file);
//        }

//        public EsitoVerificaFirmaDigitale VerificaFirmaDigitale(int codiceOggetto)
//        {
//            var file = this._oggettiService.GetById(codiceOggetto);

//            return this.VerificaFirmaDigitale(file);
//        }
//    }


//}

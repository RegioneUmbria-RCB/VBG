using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Utils;
using log4net;
using System;
using System.Reflection;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda
{
    public class WsAllegatiDomandaFoRepository : IAllegatiDomandaFoRepository
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsAllegatiDomandaFoRepository));
        private readonly IVerificaFirmaDigitaleService _firmaDigitaleService;
        private readonly IFirmaDigitaleMetadataService _firmaDigitaleMetadataService;
        private readonly IOggettiService _oggettiService;
        private readonly AllegatiDomandaWsClient _allegatiDomandaWsClient;

        public WsAllegatiDomandaFoRepository(IVerificaFirmaDigitaleService firmaDigitaleService, IFirmaDigitaleMetadataService firmaDigitaleMetadataService, IOggettiService oggettiService, AllegatiDomandaWsClient allegatiDomandaWsClient)
        {
            this._firmaDigitaleService = firmaDigitaleService;
            this._oggettiService = oggettiService;
            this._firmaDigitaleMetadataService = firmaDigitaleMetadataService;
            this._allegatiDomandaWsClient = allegatiDomandaWsClient;
        }

        /// <summary>
        /// Salva un allegato di una domanda e ne ritorna l'id nel database
        /// </summary>
        /// <param name="aliasComune"></param>
        /// <param name="idDomanda"></param>
        /// <param name="binaryFile"></param>
        /// <returns></returns>
        public SalvataggioAllegatoResult SalvaAllegato(int idDomanda, BinaryFile binaryFile)
        {
            return this.SalvaAllegato(idDomanda, binaryFile, false);
        }



        public async Task<SalvataggioAllegatoResult> SalvaAllegatoAsync(int idDomanda, BinaryFile binaryFile, bool richiedeFirmaDigitale)
        {
            var esitoVerifica = await this._firmaDigitaleService.VerificaFirmaDigitaleAsync(binaryFile);
            var specification = new FirmaValidaSpecification();
            var firmatoDigitalmente = await specification.IsSatisfiedByAsync(esitoVerifica);

            if (richiedeFirmaDigitale && !firmatoDigitalmente)
                throw new FirmaDigitaleNonValidaException("Si è verificato un errore durante la verifica della firma digitale: " + esitoVerifica.Errore);

            var codiceOggetto = await this._oggettiService.InserisciOggettoAsync(binaryFile.FileName, binaryFile.MimeType, binaryFile.FileContent);

            return await this._allegatiDomandaWsClient.CallAsync(async (ws) =>
            {
                await ws.Service.SalvaAllegatoDomandaAsync(ws.Token, idDomanda, codiceOggetto);

                return new SalvataggioAllegatoResult(codiceOggetto, binaryFile.FileName, firmatoDigitalmente);
            });
        }

        public async Task<SalvataggioAllegatoResult> SalvaAllegatoAsync(int idDomanda, int codiceOggetto)
        {
            var verificaFirmaSpecification = new FirmaValidaSpecification();
            var esitoVerifica = await this._firmaDigitaleService.VerificaFirmaDigitaleAsync(codiceOggetto);
            var firmatoDigitalmente = await verificaFirmaSpecification.IsSatisfiedByAsync(esitoVerifica);

            var nomeFile = await this._oggettiService.GetNomeFileAsync(codiceOggetto);

            return await this._allegatiDomandaWsClient.CallAsync(async (ws) =>
            {
                await ws.Service.SalvaAllegatoDomandaAsync(ws.Token, idDomanda, codiceOggetto);

                return new SalvataggioAllegatoResult(codiceOggetto, nomeFile, firmatoDigitalmente);
            });
        }

        public SalvataggioAllegatoResult SalvaAllegato(int idDomanda, int codiceOggetto)
        {
            var esitoVerifica = this._firmaDigitaleService.VerificaFirmaDigitale(codiceOggetto);
            var firmatoDigitalmente = new FirmaValidaSpecification().IsSatisfiedBy(esitoVerifica);

            var nomeFile = this._oggettiService.GetNomeFile(codiceOggetto);

            return this._allegatiDomandaWsClient.Call(ws =>
            {
                ws.Service.SalvaAllegatoDomanda(ws.Token, idDomanda, codiceOggetto);

                return new SalvataggioAllegatoResult(codiceOggetto, nomeFile, firmatoDigitalmente);
            });
        }

        public SalvataggioAllegatoResult SalvaAllegato(int idDomanda, BinaryFile binaryFile, bool richiedeFirmaDigitale)
        {
            //var aliasComune = this._aliasResolver.AliasComune;
            var esitoVerifica = this._firmaDigitaleService.VerificaFirmaDigitale(binaryFile);
            var firmatoDigitalmente = new FirmaValidaSpecification().IsSatisfiedBy(esitoVerifica);

            if (richiedeFirmaDigitale && !firmatoDigitalmente)
                throw new FirmaDigitaleNonValidaException("Si è verificato un errore durante la verifica della firma digitale: " + esitoVerifica.Errore);

            var codiceOggetto = this._oggettiService.InserisciOggetto(binaryFile.FileName, binaryFile.MimeType, binaryFile.FileContent);

            return this._allegatiDomandaWsClient.Call(ws =>
            {
                ws.Service.SalvaAllegatoDomanda(ws.Token, idDomanda, codiceOggetto);

                return new SalvataggioAllegatoResult(codiceOggetto, binaryFile.FileName, firmatoDigitalmente);
            });
        }

        public bool ConfrontaHash(BinaryFile file, string hashConfronto)
        {
            var fileInChiaro = this._firmaDigitaleMetadataService.GetFileInChiaro(file);

            var bytesToHash = fileInChiaro == null ? file.FileContent : fileInChiaro.FileContent;
            var hashFile = new Hasher().ComputeHash(bytesToHash);

            return hashFile.ToUpper() == hashConfronto.ToUpper();
        }

        public SalvataggioAllegatoResult SalvaAllegatoConfrontaHash(int idDomanda, BinaryFile file, string hashConfronto)
        {
            if (!this.ConfrontaHash(file, hashConfronto))
            {
                throw new HashCheckFailedException();
            }

            using (CodeProfiler.Track(MethodBase.GetCurrentMethod()))
            {
                return this.SalvaAllegato(idDomanda, file, false);
            }
        }

        /// <summary>
        /// Elimina l'allegato di una domanda
        /// </summary>
        /// <param name="aliasComune"></param>
        /// <param name="idDomanda"></param>
        /// <param name="idAllegato"></param>
        public void EliminaAllegato(int idDomanda, int idAllegato)
        {
            //var aliasComune = this._aliasResolver.AliasComune;

            this._allegatiDomandaWsClient.CallVoid(ws =>
            {
                try
                {
                    //ws.Service.EliminaAllegatoDomanda(ws.Token, idDomanda, idAllegato);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore durante l'eliminazione dell'allegato {0} della domanda {1} sull'idcomune {2}: {3}", idAllegato, idDomanda, idAllegato, ex.ToString());

                    throw;
                }
            });
        }


        /// <summary>
        /// Legge l'allegato di una domanda
        /// </summary>
        /// <param name="aliasComune"></param>
        /// <param name="idDomanda"></param>
        /// <param name="idAllegato"></param>
        /// <returns></returns>
        public BinaryFile LeggiAllegato(int idDomanda, int idAllegato)
        {
            try
            {
                //var aliasComune = this._aliasResolver.AliasComune;

                var appartieneADomanda = this._allegatiDomandaWsClient.Call(ws => ws.Service.OggettoAppartieneADomanda(ws.Token, idDomanda, idAllegato));

                if (!appartieneADomanda)
                    throw new Exception("L'oggetto con codice " + idAllegato + " non appartiene alla domanda con id " + idDomanda);

                return this._oggettiService.GetById(idAllegato);

            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in LeggiAllegato: {0}", ex.ToString());

                throw;
            }
        }

        public string LeggiChecksumAllegato(int codiceOggetto)
        {
            return this._allegatiDomandaWsClient.Call(ws => ws.Service.GetChecksumOggetto(ws.Token, codiceOggetto));
        }
    }
}

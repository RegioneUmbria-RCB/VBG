// -----------------------------------------------------------------------
// <copyright file="WsOggettiRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggettiService;
    using log4net;
    using System;
    using System.Linq;
    using System.Threading.Tasks;

    public class WsOggettiRepository : IOggettiRepository
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsOggettiRepository));
        private readonly OggettiServiceCreator _oggettiServiceCreator;

        public WsOggettiRepository(OggettiServiceCreator oggettiServiceCreator)
        {
            this._oggettiServiceCreator = oggettiServiceCreator;
        }


        #region IOggettiService Members

        public async Task<string> GetNomeFileAsync(int codiceOggetto)
        {
            try
            {
                return await this._oggettiServiceCreator.CallAsync(async ws =>
                {
                    var req = new OggettiFindNomeRequest
                    {
                        token = ws.Token,
                        id = codiceOggetto.ToString()
                    };

                    var res = await ws.Service.OggettiFindNomeAsync(req);

                    return res.OggettiFindNomeResponse?.fileName;
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.GetNomeFile: {0}", ex.ToString());
                throw;
            }
        }

        public string GetNomeFile(int codiceOggetto)
        {
            try
            {
                return this._oggettiServiceCreator.Call(ws =>
                {
                    var req = new OggettiFindNomeRequest
                    {
                        token = ws.Token,
                        id = codiceOggetto.ToString()
                    };

                    try
                    {
                        var res = ws.Service.OggettiFindNome(req);

                        return res.fileName;
                    }
                    catch (Exception exWs)
                    {
                        this._log.ErrorFormat("Errore durante la chiamata a OggettiFindNome: {0}", exWs.ToString());
                        return string.Empty;
                    }
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.GetNomeFile: {0}", ex.ToString());
                throw;
            }
        }

        public async Task<BinaryFile> GetOggettoAsync(int codiceOggetto)
        {
            try
            {
                return await this._oggettiServiceCreator.CallAsync(async (ws) =>
                {
                    var req = new OggettiFindRequest
                    {
                        token = ws.Token,
                        id = codiceOggetto.ToString()
                    };

                    var res = await ws.Service.OggettiFindAsync(req);

                    if ((res?.OggettiFindResponse) == null)
                    {
                        throw new Exception($"Impossibile caricare il file con id {codiceOggetto}");
                    }

                    return new BinaryFile(res.OggettiFindResponse.fileName, res.OggettiFindResponse.mimeType, res.OggettiFindResponse.binaryData);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.GetOggetto: {0}", ex.ToString());
                throw;
            }
        }

        public BinaryFile GetOggetto(int codiceOggetto)
        {
            try
            {
                return this._oggettiServiceCreator.Call(ws =>
                {
                    var req = new OggettiFindRequest
                    {
                        token = ws.Token,
                        id = codiceOggetto.ToString()
                    };

                    var res = ws.Service.OggettiFind(req);

                    return new BinaryFile(res.fileName, res.mimeType, res.binaryData);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.GetOggetto: {0}", ex.ToString());
                throw;
            }
        }



        public void AggiornaOggetto(int codiceOggetto, byte[] data)
        {
            try
            {
                this._oggettiServiceCreator.CallVoid(ws =>
                {
                    var req = new OggettiUpdateRequest
                    {
                        token = ws.Token,
                        id = codiceOggetto.ToString(),
                        binaryData = data
                    };

                    ws.Service.OggettiUpdate(req);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.AggiornaOggetto: {0}", ex.ToString());
                throw;
            }
        }

        public async Task<int> InserisciOggettoAsync(string nomeFile, string mimeType, byte[] data, IMetadatiOggettoProvider metadatiProvider)
        {
            try
            {
                return await this._oggettiServiceCreator.CallAsync(async (ws) =>
                {
                    var req = new OggettiInsertV2Request
                    {
                        token = ws.Token,
                        fileName = nomeFile,
                        mimeType = mimeType,
                        binaryData = data,
                        metadati = metadatiProvider.Metadati.Select(x => new MetadatoType
                        {
                            chiave = x.Chiave,
                            valore = x.Valore
                        }).ToArray()
                    };

                    var res = await ws.Service.OggettiInsertV2Async(req);

                    return Convert.ToInt32(res.OggettiInsertResponse.id);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.InserisciOggetto: {0}", ex.ToString());
                throw;
            }
        }

        public int InserisciOggetto(string nomeFile, string mimeType, byte[] data, IMetadatiOggettoProvider metadatiProvider)
        {
            try
            {
                return this._oggettiServiceCreator.Call(ws =>
                {
                    var req = new OggettiInsertV2Request
                    {
                        token = ws.Token,
                        fileName = nomeFile,
                        mimeType = mimeType,
                        binaryData = data,
                        metadati = metadatiProvider.Metadati.Select(x => new MetadatoType
                        {
                            chiave = x.Chiave,
                            valore = x.Valore
                        }).ToArray()
                    };

                    var res = ws.Service.OggettiInsertV2(req);

                    return Convert.ToInt32(res.id);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.InserisciOggetto: {0}", ex.ToString());
                throw;
            }
        }

        public string GetMetadatoOggetto(int codiceOggetto, string metadato)
        {
            if (codiceOggetto <= 0)
            {
                throw new ArgumentException("Il codice oggetto deve essere maggiore di zero", nameof(codiceOggetto));
            }
            if (string.IsNullOrEmpty(metadato))
            {
                throw new ArgumentException("Il metadato non può essere vuoto", nameof(metadato));
            }

            return this._oggettiServiceCreator.Call(ws =>
            {
                var req = new OggettiFindMetadatiRequest
                {
                    token = ws.Token,
                    codiceIstanza = codiceOggetto.ToString(),
                    metadati = new[]{
                        new MetadatoType {
                            chiave = metadato,
                            valore = string.Empty // Il valore non è necessario per la ricerca, ma è richiesto dal servizio
                        }
                    }
                };

                var res = ws.Service.OggettiFindMetadati(req);

                if (res == null)
                {
                    return String.Empty;
                }
                return res.FirstOrDefault() ?? "";
            });
        }


        public string GetMd5_non_usare_non_sempre_valorizzato(int codiceOggetto)
        {
            return this.GetMetadatoOggetto(codiceOggetto, "MD5_SUM");
        }
        #endregion
    }
}

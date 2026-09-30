using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.WsOggettiService;
using log4net;
using PersonalLib2.Data;
using System;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.OggettiLogic
{
    public class OggettiServiceProxy
    {
        private static class Constants
        {
            public const string BindingName = "OggettiMtomBinding";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(OggettiServiceProxy));
        private readonly DataBase _db;
        private readonly IBindingFactory _bindingFactory;

        public OggettiServiceProxy(DataBase db)
        {
            this._db = db;
            this._bindingFactory = StaticKernelContainer.GetService<IBindingFactory>();
        }


        private string GetToken()
        {
            return this._db.ConnectionDetails.Token;
        }


        private OggettiClient CreateClient()
        {
            this._log.DebugFormat("Creazione del client per il web service di gestione Oggetti sull'endpoint {0} utilizzando il binding {1}", ParametriConfigurazione.Get.WsOggettiServiceUrl, Constants.BindingName);

            var binding = this._bindingFactory.CreateAndConfigure(Constants.BindingName);
            var endpoint = new EndpointAddress(ParametriConfigurazione.Get.WsOggettiServiceUrl);

            binding.MessageEncoding = WSMessageEncoding.Mtom;

            var ws = new OggettiClient(binding, endpoint);

            return ws;
        }

        public int InsertOggetto(string fileName, string mimeType, byte[] fileData)
        {

            try
            {
                using (var ws = this.CreateClient())
                {
                    this._log.DebugFormat("Invocazione di InsertOggetto all'indirizzo: {0}", ws.Endpoint.Address);

                    var req = new OggettiInsertRequest
                    {
                        binaryData = fileData,
                        fileName = fileName,
                        mimeType = mimeType,
                        token = this.GetToken()
                    };

                    var res = ws.OggettiInsert(req);

                    return Convert.ToInt32(res.id);
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a InsertOggetto: {0}", ex.ToString());

                throw;
            }
        }

        public void UpdateOggetto(int id, string fileName, byte[] fileData)
        {
            try
            {
                using (var ws = this.CreateClient())
                {
                    this._log.DebugFormat("Invocazione di UpdateOggetto all'indirizzo: {0}", ws.Endpoint.Address);

                    var req = new OggettiUpdateRequest
                    {
                        binaryData = fileData,
                        id = id.ToString(),
                        fileName = fileName,
                        token = this.GetToken()
                    };

                    var res = ws.OggettiUpdate(req);
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a UpdateOggetto: {0}", ex.ToString());

                throw;
            }
        }

        public void DeleteOggetto(int id)
        {
            try
            {
                using (var ws = this.CreateClient())
                {
                    this._log.DebugFormat("Invocazione di DeleteOggetto all'indirizzo: {0}", ws.Endpoint.Address);

                    var req = new OggettiDeleteRequest
                    {
                        id = id.ToString(),
                        token = this.GetToken()
                    };

                    var res = ws.OggettiDelete(req);

                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a DeleteOggetto: {0}", ex.ToString());

                throw;
            }
        }

        public string GetFileName(int id)
        {
            try
            {
                using (var ws = this.CreateClient())
                {
                    this._log.DebugFormat("Invocazione di GetFileName all'indirizzo: {0}", ws.Endpoint.Address);

                    var req = new OggettiFindNomeRequest
                    {
                        id = id.ToString(),
                        token = this.GetToken()
                    };

                    var res = ws.OggettiFindNome(req);

                    return res.fileName;
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a GetFileName: {0}", ex.ToString());

                throw;
            }
        }

        public Init.SIGePro.Data.Oggetti GetById(int id)
        {
            var res = this.GetByIdNativo(id);

            if (res == null || String.IsNullOrEmpty(res.fileName))
                return null;

            return new Init.SIGePro.Data.Oggetti
            {
                CODICEOGGETTO = id.ToString(),
                NOMEFILE = res.fileName,
                OGGETTO = res.binaryData
            };
        }

        public OggettiFindResponse GetByIdNativo(int id)
        {
            try
            {
                using (var ws = this.CreateClient())
                {
                    this._log.DebugFormat("Invocazione di GetFileName all'indirizzo: {0}", ws.Endpoint.Address);

                    var req = new OggettiFindRequest
                    {
                        id = id.ToString(),
                        token = this.GetToken()
                    };

                    var res = ws.OggettiFind(req);

                    return res;
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a OggettiFind: {0}", ex.ToString());

                throw;
            }
        }
    }
}

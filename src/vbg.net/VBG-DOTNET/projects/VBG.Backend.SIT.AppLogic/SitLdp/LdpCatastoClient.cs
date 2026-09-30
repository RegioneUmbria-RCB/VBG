using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.SIT.AppLogic.SitLdp.ServiceReferences.Catasto;

namespace VBG.Backend.SIT.AppLogic.SitLdp
{
    internal class LdpCatastoClient : LdpClientBase<CatastoSoapClient>, ILdpCatastoClient
    {
        private static class Constants
        {
            public const string WsValoreTrue = "TRUE";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(LdpCatastoClient));

        public LdpCatastoClient(string serviceUrl, BasicSoapAuthenticationCredentials credentials)
            : base(serviceUrl, credentials, (b, e) => new CatastoSoapClient(b, e))
        {

        }

        public IEnumerable<string> GetListaSezioni()
        {
            try
            {
                return this.CallServiceMethod(x =>
                {
                    return x.getSezioni().Select(sez => sez.testo);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetListaSezioni: {0}", ex.ToString());

                throw;
            }
        }

        public IEnumerable<string> GetListaFogli(string sezione)
        {
            try
            {
                return this.CallServiceMethod(x =>
                {
                    return x.getFogliBySezione(new ComplexTypeStringa { testo = sezione }).Select(fog => fog.foglio);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetListaFogli con sezione={1}: {0}", ex.ToString(), sezione);

                throw;
            }
        }

        public IEnumerable<string> GetListaParticelle(string sezione, string foglio)
        {
            try
            {
                return this.CallServiceMethod(x =>
                {
                    return x.getParticelleBySezioneFoglio(new ComplexTypeFoglio
                    {
                        foglio = foglio,
                        sezione = sezione
                    }).Select(fog => fog.particella);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetListaParticelle con sezione={1} e foglio={2}: {0}", ex.ToString(), sezione, foglio);

                throw;
            }
        }

        public IEnumerable<string> GetListaSubalterni(string sezione, string foglio, string particella)
        {
            try
            {
                return this.CallServiceMethod(x =>
                {
                    return x.getSubalterniBySezioneFoglioParticella(new ComplexTypeParticella
                    {
                        foglio = foglio,
                        sezione = sezione,
                        particella = particella
                    }).Select(fog => fog.subalterno);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetListaSubalterni con sezione={1}, foglio={2} e particella={3}: {0}", ex.ToString(), sezione, foglio, particella);

                throw;
            }
        }

        public bool IsValidSubalterno(string sezione, string foglio, string particella, string subalterno)
        {
            try
            {
                return this.CallServiceMethod(ws =>
                {
                    var result = ws.isValidSubalterno(new isValidSubalternoRequest
                    {
                        sezione = sezione,
                        foglio = foglio,
                        particella = particella,
                        subalterno = subalterno
                    });

                    if (result == null)
                    {
                        return false;
                    }

                    return result.testo == Constants.WsValoreTrue;
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a IsValidSubalterno con sezione={1}, foglio={2}, particella={3}, subalterno={4}: {0}", ex.ToString(), sezione, foglio, particella, subalterno);

                throw;
            }
        }
    }
}

using Init.SIGePro.Sit.SitLdp.ServiceReferences.Civici;
using Init.SIGePro.Sit.Utils;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Sit.SitLdp
{
    class LdpCiviciClient : LdpClientBase<CiviciSoapClient>
    {
        private static class Constants
        {
            public const string WsValoreTrue = "TRUE";
        }

        ILog _log = LogManager.GetLogger(typeof(LdpCiviciClient));


        public LdpCiviciClient(string serviceUrl, BasicSoapAuthenticationCredentials credentials) :
            base(serviceUrl, credentials, (b, e) => new CiviciSoapClient(b, e))
        {
        }

        public IEnumerable<string> GetCiviciByToponimo(string codViario)
        {
            try
            {
                return CallServiceMethod(ws =>
                {
                    var result = ws.getCiviciByToponimo(new ComplexTypeStringa { testo = codViario });

                    return result.Select(x => x.numero);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetCiviciByToponimo con codViario={0}: {1}", codViario, ex.ToString());

                throw;
            }
        }

        public IEnumerable<string> GetEsponentiByToponimoECivco(string codViario, string civico)
        {
            try
            {
                return CallServiceMethod(ws => {
                    var result = ws.getCiviciByToponimo(new ComplexTypeStringa { testo = codViario });

                    return result.Where(x => x.numero == civico).Select(x => x.esponente);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetEsponentiByToponimoECivco con codViario={0}, civico={1}: {2}", codViario, civico, ex.ToString());

                throw;
            }

        }

        public IEnumerable<ParticellaLdp> GetParticelleByToponimoCivicoEsponente(string codViario, string civico, string esponente)
        {
            try
            {
                return CallServiceMethod(ws =>
                {
                    var result = ws.getParticelleByCivico(new ComplexTypeCivico
                    {
                        codice_strada = codViario,
                        numero = civico,
                        esponente = esponente
                    });

                    return result.Select(x => new ParticellaLdp
                    {
                        Sezione = x.sezione,
                        Foglio = x.foglio,
                        Particella = x.particella
                    });
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetParticelleByToponimoCivicoEsponente con codViario={0}, civico={1}, esponente={2}: {3}", codViario, civico, esponente, ex.ToString());

                throw;
            }
        }

        public bool IsValidCivico(string codViario, string civico, string esponente)
        {
            try
            {
                return CallServiceMethod(ws =>
                {
                    var result = ws.isValidCivico(new isValidCivicoRequest
                    {
                        codice_strada = codViario,
                        numero = civico,
                        esponente = esponente
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
                this._log.ErrorFormat("Errore durante la chiamata a IsValidCivico con codViario={0}, civico={1}, esponente={2}: {3}", codViario, civico, esponente, ex.ToString());

                throw;
            }
        }

        internal ComplexTypeToponimo[] GetListaVie()
        {
            try
            {
                return CallServiceMethod(ws =>
                {
                    return ws.getToponimiStradali();
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetListaVie: {0}", ex.ToString());

                throw;
            }
        }
    }
}

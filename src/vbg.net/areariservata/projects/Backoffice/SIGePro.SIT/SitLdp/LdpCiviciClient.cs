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
                    var result = ws.getCiviciByToponimo(new ComplexTypeStringa { testo = Convert.ToInt32(codViario).ToString() });

                    return result.GroupBy(x => x.numero).Select(x => x.Key);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetCiviciByToponimo con codViario={0}: {1}", Convert.ToInt32(codViario).ToString(), ex.ToString());

                throw;
            }
        }

        public IEnumerable<string> GetEsponentiByToponimoECivco(string codViario, string civico)
        {
            try
            {
                return CallServiceMethod(ws => {
                    var result = ws.getCiviciByToponimo(new ComplexTypeStringa { testo = Convert.ToInt32(codViario).ToString() });

                    return result.Where(x => x.numero == civico).Select(x => x.esponente);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetEsponentiByToponimoECivco con codViario={0}, civico={1}: {2}", Convert.ToInt32(codViario).ToString(), civico, ex.ToString());

                throw;
            }

        }

        public IEnumerable<string> GetAccessoTipo(string codViario, string civico, string esponente)
        {
            try
            {
                return CallServiceMethod(ws => {
                    var result = ws.getAccessiByCivico(new getAccessiByCivicoRequest
                    {
                        codice_strada = Convert.ToInt32(codViario).ToString(),
                        numero = civico,
                        esponente = esponente
                    });

                    return result.Select(x => x.p_r);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat($"Errore durante il recupero del tipo di accesso, codViario={Convert.ToInt32(codViario).ToString()}, civico={civico}, esponente={esponente}", ex.ToString());

                throw;
            }
        }

        public IEnumerable<ComplexTypeAccesso> GetAccessoNumeroByTipo(string codViario, string civico, string esponente, string tipo)
        {
            try
            {
                return CallServiceMethod(ws => {
                    var result = ws.getAccessiByCivico(new getAccessiByCivicoRequest
                    {
                        codice_strada = Convert.ToInt32(codViario).ToString(),
                        numero = civico,
                        esponente = esponente
                    });

                    return result.Where(x => x.p_r == tipo);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat($"Errore durante il recupero della tipologia di accesso, codViario={Convert.ToInt32(codViario).ToString()}, civico={civico}, esponente={esponente} e tipo={tipo}", ex.ToString());

                throw;
            }
        }

        public IEnumerable<ComplexTypeAccesso> GetAccessoNumeroByTipoeNumero(string codViario, string civico, string esponente, string tipo, string numero)
        {
            try
            {
                return CallServiceMethod(ws => {
                    var result = ws.getAccessiByCivico(new getAccessiByCivicoRequest
                    {
                        codice_strada = Convert.ToInt32(codViario).ToString(),
                        numero = civico,
                        esponente = esponente
                    });

                    return result.Where(x => x.p_r == tipo && x.id.ToString() == numero);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat($"Errore durante il recupero del numero relativo all'accesso, codViario={Convert.ToInt32(codViario).ToString()}, civico={civico}, esponente={esponente} e tipo={tipo}", ex.ToString());

                throw;
            }
        }

        public bool IsValidAccessoDescrizione(string codViario, string civico, string esponente, string tipo, string numero, string descrizione)
        {
            try
            {
                return CallServiceMethod(ws => {
                    var result = ws.getAccessiByCivico(new getAccessiByCivicoRequest
                    {
                        codice_strada = Convert.ToInt32(codViario).ToString(),
                        numero = civico,
                        esponente = esponente
                    });

                    return result.Where(x => x.p_r == tipo && x.id.ToString() == numero && x.tipo == descrizione).Count() == 1;
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat($"Errore durante il recupero del numero relativo all'accesso, codViario={Convert.ToInt32(codViario).ToString()}, civico={civico}, esponente={esponente} e tipo={tipo}", ex.ToString());

                throw;
            }
        }

        public IEnumerable<ComplexTypeAccesso> GetAccessiPassiCarraiByCivico(string codViario, string civico, string esponente)
        {
            try
            {
                return CallServiceMethod(ws =>
                {
                    return ws.getAccessiByCivico(new getAccessiByCivicoRequest
                    {
                        codice_strada = Convert.ToInt32(codViario).ToString(),
                        numero = civico,
                        esponente = esponente
                    });
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat($"Errore durante la chiamata a getAccessiByCivico con codViario: {Convert.ToInt32(codViario).ToString()}, civico: {civico}: esponente: {esponente}, {ex.ToString()}");
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
                        codice_strada = Convert.ToInt32(codViario).ToString(),
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
                this._log.ErrorFormat("Errore durante la chiamata a GetParticelleByToponimoCivicoEsponente con codViario={0}, civico={1}, esponente={2}: {3}", Convert.ToInt32(codViario).ToString(), civico, esponente, ex.ToString());

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
                        codice_strada = Convert.ToInt32(codViario).ToString(),
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
                this._log.ErrorFormat("Errore durante la chiamata a IsValidCivico con codViario={0}, civico={1}, esponente={2}: {3}", Convert.ToInt32(codViario).ToString(), civico, esponente, ex.ToString());

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

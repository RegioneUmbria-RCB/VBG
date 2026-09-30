// -----------------------------------------------------------------------
// <copyright file="AnagraficheRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Init.Utils.Extensions;
using log4net;
using System;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
    internal class WsAnagraficheRepository : IAnagraficheRepository
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsAnagraficheRepository));
        private readonly CreazioneAnagrafeServiceCreator _creazioneAnagrafeServiceCreator;
        private readonly WsAnagraficheServiceCreator _serviceCreator;
        private readonly RicercaAnagraficheServiceCreator _ricercaAnagraficheServiceCreator;
        private readonly IComuniService _comuniService;

        public WsAnagraficheRepository(CreazioneAnagrafeServiceCreator creazioneAnagrafeServiceCreator, WsAnagraficheServiceCreator serviceCreator, RicercaAnagraficheServiceCreator ricercaAnagraficheServiceCreator, IComuniService comuniService)
        {
            if (creazioneAnagrafeServiceCreator == null)
                throw new ArgumentNullException(nameof(creazioneAnagrafeServiceCreator));
            //Condition.Requires(creazioneAnagrafeServiceCreator, "serviceCreator").IsNotNull();

            this._creazioneAnagrafeServiceCreator = creazioneAnagrafeServiceCreator;
            this._serviceCreator = serviceCreator;
            this._ricercaAnagraficheServiceCreator = ricercaAnagraficheServiceCreator;
            this._comuniService = comuniService;
        }

        public Anagrafe? RicercaAnagrafica(TipoPersonaEnum tipoPersona, string codiceFiscale)
        {
            /*
            var sessionKey = String.Format(GET_BY_CODICEFISCALE_CACHE_KEY, tipoPersona == TipoPersonaEnum.Fisica ? "F" : "G", codiceFiscale);

            return this._sessionCache.GetOrAdd(sessionKey, () => this.RicercaAnagraficaInternal(tipoPersona, codiceFiscale));
            */
            return this.RicercaAnagraficaInternal(tipoPersona, codiceFiscale);
        }

        private Anagrafe? RicercaAnagraficaInternal(TipoPersonaEnum tipoPersona, string codiceFiscale)
        {
            return this._ricercaAnagraficheServiceCreator.CreateClient(tipoPersona).Call(ws =>
            {
                codiceFiscale = codiceFiscale.ToUpper();
                RicercheAnagraficheWebService.Anagrafe? persona = null;

                if (tipoPersona == TipoPersonaEnum.Fisica)
                {
                    persona = ws.Service.getPersonaFisica(ws.Token, codiceFiscale);
                }
                else
                {
                    persona = ws.Service.getPersonaGiuridica(ws.Token, codiceFiscale);
                }

                if (persona == null)
                {
                    return null;
                }

                return persona.MapUsingJson<Anagrafe>();
            });
        }

        public Anagrafe GetByUserId(string aliasComune, string userId, TipoPersonaEnum tipoPersona)
        {

            try
            {
                return this._serviceCreator.Call(ws =>
                {
                    return ws.Service.GetAnagrafeByUserId(ws.Token, userId, tipoPersona == TipoPersonaEnum.Fisica ? TipoPersona.PersonaFisica : TipoPersona.PersonaGiuridica);
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la lettura dell'anagrafica {0} nell'idcomune {1}: {2}", userId, aliasComune, ex.ToString());

                throw;
            }

        }

        public CreazioneAnagraficaResult CreaAnagrafica(RichiestaCreazioneAnagraficaDto richiesta)
        {
            return this._creazioneAnagrafeServiceCreator.Call(ws =>
            {

                var anagrafeRequest = new Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.InserimentoAnagrafeRequest
                {
                    token = ws.Token,
                    datiAnagrafici = richiesta.GetAnagrafeType(this._comuniService),
                    tipoInserimento = richiesta.AuthType,
                    tipoInserimentoSpecified = true,
                    xmlDatiAnagrafici = richiesta.GetXmlAnagrafica()
                };

                this._log.Debug($"Creazione di una nuova anagrafica:\r\n{richiesta.GetXmlAnagrafica()}");

                var response = ws.Service.InserimentoAnagrafe(anagrafeRequest);

                this._log.Debug($"Creazione nuova anagrafica terminata{response.ToXmlString()}");

                if (response.errori == null)
                {
                    return CreazioneAnagraficaResult.Success;
                }

                return CreazioneAnagraficaResult.Failed(response.errori.numeroErrore, response.errori.descrizione);

            });
        }

    }
}

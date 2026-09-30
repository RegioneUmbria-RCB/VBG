using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.DatiDomandaOnline;
using Init.SIGePro.Manager.Logic.GestioneDomandaOnLine;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel.Activation;
using Vbg.EventBus.Abstractions;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.DatiDomanda
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsDatiDomandaService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsDatiDomandaService.svc or WsDatiDomandaService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsDatiDomandaService : WcfServiceBase, IWsDatiDomandaService
    {

        [Inject]
        public IDomandaOnlineService _domandeOnlineService { get; set; }
        [Inject]
        public IEventPublisher _eventPublisher { get; set; }
        /*
		/// <summary>
		/// Crea una nuova domanda frontoffice nel database
		/// </summary>
		/// <param name="token"></param>
		/// <param name="software"></param>
		/// <param name="codiceFiscaleUtente"></param>
		/// <param name="datiDomanda"></param>
		/// <returns></returns>
		
		public int CreaDomanda(string token, string software, int idDomanda, string codiceFiscaleUtente, byte[] datiDomanda)
		{
			return new DatiDomandaFrontofficeService().CreaDomanda(token, software, idDomanda, codiceFiscaleUtente, datiDomanda);
		}
		*/

        public int GetProssimoIdDomanda(string token)
        {

            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
                return new FoDomandeMgr(db).GetProssimoIdDomanda(authInfo.IdComune);
        }

        /// <summary>
        /// Effettua il salvataggio di una domanda del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        /// <param name="datiDomanda"></param>

        public EsitoSalvataggioDomandaOnlineDto SalvaDomanda(string token, SalvaDomandaCommandDto salvaDomandaCommand)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var domMgr = new FoDomandeMgr(db);

                var result = domMgr.SalvaOAggiorna(authInfo.IdComune, salvaDomandaCommand);

                if (result == null)
                {
                    return null;
                }

                return new EsitoSalvataggioDomandaOnlineDto
                {
                    Nuova = result.Nuova,
                    PresentataModificato = result.PresentataModificato,
                    TrasferimentoAnnullato = result.TrasferimentoAnnullato,
                    TrasferitaModificato = result.TrasferitaModificato
                };
            }
        }

        /// <summary>
        /// Legge i dati di una domanda del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        /// <param name="datiDomanda"></param>

        public byte[] LeggiDomanda(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var domMgr = new FoDomandeMgr(db);

                return domMgr.LeggiDomanda(authInfo.IdComune, idDomanda);
            }
        }


        public bool DomandaEliminata(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            var domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

            return domMgr.DomandaEliminata(authInfo.IdComune, idDomanda);
        }



        public DatiDomandaOnlineDto LeggiDatiDomanda(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var domMgr = new FoDomandeMgr(db);

                return domMgr.GetDomandaInSospesoById(authInfo.IdComune, idDomanda);
            }

        }

        /// <summary>
        /// Elimina una domanda del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>

        public void EliminaDomanda(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                new EliminaDomandaService(db, authInfo.IdComune).EliminaDomanda(idDomanda, this._eventPublisher);
            }
        }

        /// <summary>
        /// Verifica se un'istanza è stata inviata
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        /// <returns></returns>

        public bool VerificaStatoInvio(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new FoDomandeMgr(db).VerificaSeInviata(authInfo.IdComune, idDomanda);
            }
        }

        /// <summary>
        /// Segna una domanda come presentata
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>

        public void MarcaDomandaComePresentata(string token, int idDomanda, int codiceIstanza)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            var mgr = new FoDomandeMgr(authInfo.CreateDatabase());

            FoDomande dom = mgr.GetById(authInfo.IdComune, idDomanda);

            if (dom.FlgPresentata.GetValueOrDefault(0) == 1)
                throw new InvalidOperationException("La domanda " + idDomanda.ToString() + " è già stata presentata");

            dom.FlgPresentata = 1;
            dom.Codiceistanza = codiceIstanza;
            dom.Datainvio = DateTime.Now;

            mgr.Update(dom);
        }


        public List<DatiDomandaOnlineDto> GetListaDomandeInSospeso(string token, string software, int codiceAnagrafe, string provenienza)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new FoDomandeMgr(db);

                return mgr.GetDomandeInSospeso(authInfo.IdComune, software, codiceAnagrafe, provenienza).ToList();
            }
        }

        public void ImpostaIdIstanzaOrigine(string token, int idDomanda, int? idDomandaOrigine)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                new FoDomandeMgr(db).ImpostaIdIstanzaOrigine(authInfo.IdComune, idDomanda, idDomandaOrigine);
            }
        }


        public void SalvaCodiceInterventoPerStatistica(string token, int idDomanda, int codiceIntervento)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            var domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

            domMgr.SalvaCodiceInterventoPerStatistica(authInfo.IdComune, idDomanda, codiceIntervento);
        }
    }
}

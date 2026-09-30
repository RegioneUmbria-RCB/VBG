using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using log4net;
using Ninject;
using Sigepro.net.WebServices.WsSIGePro;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsSIGeProAnagrafe
{
    /// <summary>
    /// Summary description for WsAnagrafe2
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [System.ComponentModel.ToolboxItem(false)]
    [System.Web.Services.WebServiceBindingAttribute(Name = "WsAnagrafe2Soap", Namespace = "http://init.sigepro.it")]
    public class WsAnagrafe2 : SigeproWebService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsAnagrafe2));

        [Inject]
        public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }
        [Inject]
        public IBindingFactory _bindingFactory { get; set; }

        [WebMethod]
        public Anagrafe getPersonaFisica(String token, String codiceFiscale)
        {
            var authInfo = this.CheckToken(token);

            try
            {
                using (var db = authInfo.CreateDatabase())
                {
                    var contestoRicerca = authInfo.Contesto == ContestoTokenEnum.Applicazione ? ContestoRicercaAnagraficaEnum.Frontoffice : ContestoRicercaAnagraficaEnum.Backoffice;
                    var ricercheService = new RicercheAnagraficheService(this._verticalizzazioniFactory, this._bindingFactory, db, authInfo.IdComune, authInfo.Alias, contestoRicerca);

                    return ricercheService.GetByCodicefiscale(codiceFiscale);
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la ricerca della persona fisica con codice fiscale {0}: {1}", codiceFiscale, ex.ToString());

                throw new RicercaAnagraficaException($"Errore nella ricerca: {ex.Message}");
            }

        }

        [WebMethod]
        public Anagrafe getPersonaGiuridica(String token, String cfImpresaPartitaIva)
        {
            var authInfo = this.CheckToken(token);

            try
            {
                using (var db = authInfo.CreateDatabase())
                {
                    var contestoRicerca = authInfo.Contesto == ContestoTokenEnum.Applicazione ? ContestoRicercaAnagraficaEnum.Frontoffice : ContestoRicercaAnagraficaEnum.Backoffice;

                    var ricercheService = new RicercheAnagraficheService(this._verticalizzazioniFactory, this._bindingFactory, db, authInfo.IdComune, authInfo.Alias, contestoRicerca);

                    return ricercheService.GetByPartitaIva(cfImpresaPartitaIva);
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la ricerca della persona giuridica con partita iva {0}: {1}", cfImpresaPartitaIva, ex.ToString());

                throw new RicercaAnagraficaException($"Errore nella ricerca: {ex.Message}");
            }

        }

        [WebMethod]
        public List<Anagrafe> getVariazioniPersoneFisiche(string token, DateTime from, DateTime to)
        {
            var authInfo = this.CheckToken(token);

            try
            {
                using (var db = authInfo.CreateDatabase())
                {
                    var contestoRicerca = authInfo.Contesto == ContestoTokenEnum.Applicazione ? ContestoRicercaAnagraficaEnum.Frontoffice : ContestoRicercaAnagraficaEnum.Backoffice;
                    var ricercheService = new RicercheAnagraficheService(this._verticalizzazioniFactory, this._bindingFactory, db, authInfo.IdComune, authInfo.Alias, contestoRicerca);
                    return ricercheService.GetVariazioniPersoneFisiche(from, to).ToList();
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la ricerca delle variazioni delle persone fisiche: {0}", ex.ToString());

                throw new RicercaAnagraficaException($"Errore nella ricerca: {ex.Message}"); ;
            }
        }
    }
}

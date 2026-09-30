using Sigepro.net.WebServices.WsSIGePro;
using System.ComponentModel;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
    /// <summary>
    /// Summary description for DatiDomandaFrontoffice
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [ToolboxItem(false)]
    public class DatiDomandaFrontofficeService : SigeproWebService
    {
        /*
        [Inject]
        public IDomandaOnlineService _domandeOnlineService { get; set; }

        /// <summary>
        /// Effettua il salvataggio di una domanda del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        /// <param name="datiDomanda"></param>
        [WebMethod]
        public FoDomandeMgr.EsitoSalvataggioDomandaOnline SalvaDomanda(string token, string software, int idDomanda, int codiceAnagrafe, byte[] datiDomanda, string identificativoDomanda, bool flagTrasferita, bool flagPresentata)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoDomandeMgr domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

            return domMgr.SalvaOAggiorna(authInfo.IdComune, token, software, idDomanda, codiceAnagrafe, datiDomanda, identificativoDomanda, flagTrasferita, flagPresentata);
        }

        /// <summary>
        /// Legge i dati di una domanda del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        /// <param name="datiDomanda"></param>
        [WebMethod]
        public byte[] LeggiDomanda(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoDomandeMgr domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

            return domMgr.LeggiDomanda(authInfo.IdComune, idDomanda);
        }

        /// <summary>
        /// Legge i dati di una domanda del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        /// <param name="datiDomanda"></param>
        [WebMethod]
        public bool DomandaEliminata(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            var domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

            return domMgr.DomandaEliminata(authInfo.IdComune, idDomanda);
        }


        [WebMethod]
        public FoDomande LeggiDatiDomanda(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoDomandeMgr domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

            return domMgr.GetById(authInfo.IdComune, idDomanda, true);
        }

        /// <summary>
        /// Elimina una domanda del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        [WebMethod]
        public void EliminaDomanda(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            this._domandeOnlineService.EliminaDomanda(idDomanda);
        }




        /// <summary>
        /// Verifica se un'istanza è stata inviata
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        [WebMethod]
        public bool VerificaStatoInvio(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new FoDomandeMgr(db).VerificaSeInviata(authInfo.IdComune, idDomanda);
            }
        }


        /// <summary>
        /// Senga una domanda come presentata
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        [WebMethod]
        public void MarcaDomandaComePresentata(string token, int idDomanda, int codiceIstanza)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoDomandeMgr mgr = new FoDomandeMgr(authInfo.CreateDatabase());

            FoDomande dom = mgr.GetById(authInfo.IdComune, idDomanda);

            if (dom.FlgPresentata.GetValueOrDefault(0) == 1)
                throw new InvalidOperationException("La domanda " + idDomanda.ToString() + " è già stata presentata");

            dom.FlgPresentata = 1;
            dom.Codiceistanza = codiceIstanza;
            dom.Datainvio = DateTime.Now;

            mgr.Update(dom);
        }

        /// <summary>
        /// Segna una domanda come trasferita
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        [WebMethod]
        public void MarcaDomandaComeTrasferita(string token, int idDomanda, List<FoSottoscrizioniMgr.DatiSottoscrizione> datiSottoscrizioni)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoDomandeMgr mgr = new FoDomandeMgr(authInfo.CreateDatabase());

            mgr.SegnaDomandaComeTrasferita(authInfo.IdComune, idDomanda, datiSottoscrizioni);
        }

        internal void ImpostaIdIstanzaOrigine(string token, int idDomanda, int? idDomandaOrigine)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                new FoDomandeMgr(db).ImpostaIdIstanzaOrigine(authInfo.IdComune, idDomanda, idDomandaOrigine);
            }

        }

        [WebMethod]
        public void AnnullaTrasferimento(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoDomandeMgr mgr = new FoDomandeMgr(authInfo.CreateDatabase());

            mgr.AnnullaTrasferimento(authInfo.IdComune, idDomanda);
        }

        [WebMethod]
        public void SottoscriviDomanda(string token, int idDomanda, string codiceFiscale)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            new FoSottoscrizioniMgr(authInfo.CreateDatabase()).SottoscriviDomanda(authInfo.IdComune, idDomanda, codiceFiscale);
        }


        [WebMethod]
        public List<FoSottoscrizioni> GetListaSottoscrizioniUtente(string token, int idDomanda, string codiceFiscaleSottoscrivente)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoSottoscrizioni filtro = new FoSottoscrizioni();
            filtro.Idcomune = authInfo.IdComune;
            filtro.Codicedomanda = idDomanda;
            filtro.Codicefiscalesottoscrivente = codiceFiscaleSottoscrivente;

            return new FoSottoscrizioniMgr(authInfo.CreateDatabase()).GetList(filtro);
        }


        [WebMethod]
        public List<FoDomande> GetListaDomandeInSospeso(string token, string software, int codiceAnagrafe)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            //var codiceAnagrafe = TrovaAnagrafeDaCodiceFiscale(authInfo, codiceFiscaleUtente);

            FoDomandeMgr mgr = new FoDomandeMgr(authInfo.CreateDatabase());
            FoDomande dom = new FoDomande();
            dom.Idcomune = authInfo.IdComune;
            dom.Software = software;
            dom.Codiceanagrafe = codiceAnagrafe;
            dom.OthersWhereClause.Add("(FLG_PRESENTATA is null or FLG_PRESENTATA = 0) and (FLG_TRASFERITA is null OR FLG_TRASFERITA = 0)");
            dom.UseForeign = useForeignEnum.Yes;
            dom.OrderBy = "DATA_ULTIMA_MODIFICA desc";

            return mgr.GetList(dom);
        }


        [WebMethod]
        public List<FoDomande> GetListaDomandeDaSottoscrivere(string token, string software, string codiceFiscaleSottoscrivente)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoSottoscrizioniMgr mgr = new FoSottoscrizioniMgr(authInfo.CreateDatabase());

            return mgr.GetListaDomandeDaSottoscrivere(authInfo.IdComune, authInfo.Alias, software, codiceFiscaleSottoscrivente);
        }

        [WebMethod]
        public List<FoSottoscrizioni> GetListaSottoscriventi(string token, int codicedomanda)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoSottoscrizioniMgr mgr = new FoSottoscrizioniMgr(authInfo.CreateDatabase());

            return mgr.GetListaSottoscriventi(authInfo.IdComune, codicedomanda);
        }

        /// <summary>
        /// Il metodo salva il codiceintervento della domanda in fase di presentazione nel campo FO_DOMANDE.CODICEINTERVENTO
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codicedomanda"></param>
        /// <param name="codiceintervento"></param>
        [WebMethod]
        public void SalvaCodiceInterventoPerStatistica(string token, int codicedomanda, int codiceintervento)
        {
            AuthenticationInfo authInfo = this.CheckToken(token);

            FoDomandeMgr domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

            domMgr.SalvaCodiceInterventoPerStatistica(authInfo.IdComune, codicedomanda, codiceintervento);
        }
        */
    }
}

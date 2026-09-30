using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Framework.Logic.SchedeIstanza;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Istanze;
using Init.SIGePro.Manager.Logic.DatiDinamici.ModelliFactory;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.Script.Services;
using System.Web.Services;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Standard.Scripts;

namespace Sigepro.net.Istanze.DatiDinamici
{
    public partial class IstanzeDyn2Modelli : BasePage
    {


        #region gestione dei Dati collegati

        protected string CodiceIstanza
        {
            get { return this.Request.QueryString["CodiceIstanza"]; }
        }

        private Init.SIGePro.Data.Istanze m_istanza;

        private Init.SIGePro.Data.Istanze Istanza
        {
            get
            {
                if (this.m_istanza == null)
                    this.m_istanza = new IstanzeMgr(this.Database).GetById(this.IdComune, Convert.ToInt32(this.CodiceIstanza));

                return this.m_istanza;
            }
        }

        public override string Software
        {
            get
            {
                return this.Istanza.SOFTWARE;
            }
        }

        protected string CodiceMovimento
        {
            get { return this.Request.QueryString["CodiceMovimento"]; }
        }

        #endregion



        #region ciclo di vita della pagina

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

                this.DataBind();
            }

            if (!String.IsNullOrEmpty(this.CodiceMovimento))
                this.ddCtrl.ExtraQueryStringFunction = () => "CodiceMovimento=" + this.CodiceMovimento;
        }

        public override void DataBind()
        {
            var istanzeMgr = new IstanzeMgr(this.Database);
            var istanza = istanzeMgr.GetById(this.IdComune, Convert.ToInt32(this.CodiceIstanza));

            // Imposto le etichette
            this.lblCodiceIstanza.Text = istanza.NUMEROISTANZA;
            this.lblRichiedente.Text = new AnagrafeMgr(this.Database).GetById(this.IdComune, Convert.ToInt32(istanza.CODICERICHIEDENTE)).ToString();

            var responsabile = new ResponsabiliMgr(this.Database).GetById(this.IdComune, this.AuthenticationInfo.CodiceResponsabile.Value);

            this.ddCtrl.SolaLettura = responsabile.READONLY == "1";

            // Verifica dei permessi dell'istanza
            //var perm = istanzeMgr.PermessiIstanza(IdComune, AuthenticationInfo.CodiceResponsabile.Value, Convert.ToInt32(CodiceIstanza));

            // if (!perm.AccessoConsentito)
            // {
            // 	Logger.LogEvent(AuthenticationInfo, "DatiDinamici", "L'utente " + AuthenticationInfo.CodiceResponsabile + " non dispone dei permessi necessari per accedere alle schede dell'istanza " + CodiceIstanza, "");
            // 
            // 	throw new ApplicationException("L'utente corrente non dispone dei permessi necessari per accedere all'istanza");
            // }
            // 
            // if (perm.SolaLettura)
            // 	ddCtrl.SolaLettura = true;

            // Popolamento dei dati del movimento
            this.datiMovimento.Visible = false;

            if (!String.IsNullOrEmpty(this.CodiceMovimento))
            {
                this.datiMovimento.Visible = true;

                var movimento = new MovimentiMgr(this.Database).GetById(this.IdComune, Convert.ToInt32(this.CodiceMovimento));
                var tipoMovimento = new TipiMovimentoMgr(this.Database).GetById(movimento.TIPOMOVIMENTO, this.IdComune);

                this.Title = String.Format("Schede del movimento \"{0}\"", tipoMovimento.Movimento);

                this.lblCodiceMovimento.Text = this.CodiceMovimento;
            }

            var isAmministratore = false;

            if (this.AuthenticationInfo.CodiceResponsabile.HasValue)
            {
                using (var db = this.AuthenticationInfo.CreateDatabase())
                {
                    var responsabiliMgr = new ResponsabiliMgr(db);
                    var idComune = this.AuthenticationInfo.IdComune;
                    var codiceResponsabile = this.AuthenticationInfo.CodiceResponsabile.Value;

                    isAmministratore = responsabiliMgr.VerificaSeAmministratore(idComune, codiceResponsabile);
                }
            }

            this.ddCtrl.UtenteAmministratore = isAmministratore;
        }

        public void lnkTornaAIstanza_Click(object sender, EventArgs e)
        {
            this.Close(sender, e);
        }

        #endregion

        #region handler degli eventi del controllo di gestione modelli

        public List<int> GetListaIndiciScheda(int idModello)
        {
            IstanzeDyn2ModelliTMgr mgr = new IstanzeDyn2ModelliTMgr(this.Database);
            return mgr.GetListaIndiciScheda(this.IdComune, Convert.ToInt32(this.CodiceIstanza), idModello);
        }

        public List<ElementoListaModelli> GetListaModelli()
        {
            List<IstanzeDyn2ModelliTMgr.ElementoListaModelliIstanza> modelli = new IstanzeDyn2ModelliTMgr(this.Database).GetModelliIstanza(this.IdComune, Convert.ToInt32(this.CodiceIstanza), this.CodiceMovimento);

            List<ElementoListaModelli> ret = new List<ElementoListaModelli>(modelli.Count);

            modelli.ForEach(delegate (IstanzeDyn2ModelliTMgr.ElementoListaModelliIstanza m)
            {
                ret.Add(m);
            });

            return ret;
        }

        //public List<Dyn2ModelliT> GetListaModelliDisponibili()
        //{
        //    return new IstanzeDyn2ModelliTMgr(Database).GetModelliNonUtilizzati(IdComune, Convert.ToInt32(CodiceIstanza),false);
        //}

        public ModelloDinamicoBase GetModelloDinamicoDaId(int idModello, int indice)
        {
            int codiceIstanza = Convert.ToInt32(this.Istanza.CODICEISTANZA);

            var dap = new IstanzeDyn2DataAccessFactory(this.Database, this.IdComune, codiceIstanza);
            var loader = new ModelloDinamicoLoader(dap, this.IdComune, ContestoScriptEnum.Backoffice);
            return new BackendModelliFactory().CreaModelloIstanza(loader, idModello, indice, false);
        }

        public void AggiungiScheda(int idModello)
        {

            if (!String.IsNullOrEmpty(this.CodiceMovimento))
            {
                MovimentiDyn2ModelliT mov = new MovimentiDyn2ModelliT();
                mov.Idcomune = this.IdComune;
                mov.FkD2mtId = idModello;
                mov.Codicemovimento = Convert.ToInt32(this.CodiceMovimento);
                mov.Codiceistanza = Convert.ToInt32(this.CodiceIstanza);

                new MovimentiDyn2ModelliTMgr(this.Database).Insert(mov);

                // Se l'istanza contiene già il modello questo non va aggiunto
                var filtro = new IstanzeDyn2ModelliT
                {
                    Idcomune = this.IdComune,
                    Codiceistanza = Convert.ToInt32(this.CodiceIstanza),
                    FkD2mtId = idModello
                };
                if (new IstanzeDyn2ModelliTMgr(this.Database).GetList(filtro).Count > 0)
                    return;
            }


            var mod = new IstanzeDyn2ModelliT
            {
                Idcomune = this.IdComune,
                Codiceistanza = Convert.ToInt32(this.CodiceIstanza),
                FkD2mtId = idModello
            };

            new SchedeIstanzaService(this.Database).Insert(mod);

        }

        public void EliminaScheda(int idModello)
        {
            // Se sto eliminando la scheda dalla gestione movimenti la scheda deve essere rimossa solo dalla 
            // tabella MovimentiDyn2ModelliT
            if (!String.IsNullOrEmpty(this.CodiceMovimento))
            {
                try
                {
                    var mgr = new MovimentiDyn2ModelliTMgr(this.Database);
                    var movimento = mgr.GetById(this.IdComune, idModello, Convert.ToInt32(this.CodiceMovimento));
                    mgr.Delete(movimento);
                }
                catch (Exception ex)
                {
                    this.Errori.Add("Errore durante la rimozione della scheda dal movimento: " + ex.Message);
                }
                return;
            }

            try
            {
                var istMgr = new IstanzeDyn2ModelliTMgr(this.Database);
                IstanzeDyn2ModelliT mod = istMgr.GetById(this.IdComune, Convert.ToInt32(this.CodiceIstanza), idModello);
                istMgr.Delete(mod);
            }
            catch (Exception ex)
            {
                this.Errori.Add("Errore durante la rimozione della scheda dall'istanza: " + ex.Message);
            }
        }

        public void Close(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }

        public string GetUrlPaginaStorico(int idModello)
        {
            string url = "~/Istanze/DatiDinamici/Storico/IstanzeDyn2Storico.aspx?Token={0}&CodiceIstanza={1}&IdModello={2}";

            return this.ResolveClientUrl(String.Format(url, this.Token,
                                                            this.CodiceIstanza,
                                                            idModello));
        }

        public bool VerificaEsistenzaStorico(int idModello)
        {
            IstanzeDyn2ModelliTStoricoMgr mgr = new IstanzeDyn2ModelliTStoricoMgr(this.Database);
            int cnt = mgr.ContaRigheStorico(this.IdComune, Convert.ToInt32(this.CodiceIstanza), idModello);

            return cnt > 0;
        }

        #endregion


        [WebMethod()]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public static object GetListaModelliDisponibili(string token, int codice, string partial, bool cercaTT, string codiceMovimento = "")
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            if (authInfo == null)
                return new object[] { new { label = -1, value = "Token non valido" } };

            using (var db = authInfo.CreateDatabase())
            {
                var idComune = authInfo.IdComune;

                if (!String.IsNullOrEmpty(codiceMovimento))
                {
                    return new MovimentiDyn2ModelliTMgr(db).GetModelliNonUtilizzati(idComune, Convert.ToInt32(codiceMovimento), partial, cercaTT)
                                                           .Select(x => new { label = x.Descrizione, value = x.Codice });
                }

                return new SchedeIstanzaService(db).GetModelliNonUtilizzati(idComune, codice, partial, cercaTT)
                                                     .Select(x => new { label = x.Descrizione, value = x.Codice });
            }
        }
    }
}

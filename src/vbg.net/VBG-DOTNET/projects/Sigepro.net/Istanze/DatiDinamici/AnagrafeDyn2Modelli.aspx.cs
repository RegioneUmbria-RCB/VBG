using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Anagrafe;
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
    public partial class AnagrafeDyn2Modelli : BasePage
    {
        #region Gestione della classe Anagrafe
        protected int CodiceAnagrafe
        {
            get { return Convert.ToInt32(this.Request.QueryString["CodiceAnagrafe"]); }
        }

        private Anagrafe m_anagrafe;

        private Anagrafe Anagrafe
        {
            get
            {
                if (this.m_anagrafe == null)
                    this.m_anagrafe = new AnagrafeMgr(this.Database).GetById(this.IdComune, this.CodiceAnagrafe);

                return this.m_anagrafe;
            }
        }

        public override string Software
        {
            get
            {
                return "TT";
            }
        }
        #endregion


        #region ciclo di vita della pagina

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
        }

        #endregion

        #region handler degli eventi del controllo di gestione modelli

        public List<ElementoListaModelli> GetListaModelli()
        {
            List<ElementoListaModelli> modelli = new AnagrafeDyn2ModelliTMgr(this.Database).GetModelliCollegati(this.IdComune, Convert.ToInt32(this.CodiceAnagrafe)); //new IstanzeDyn2ModelliTMgr(Database).GetModelliIstanza(IdComune, Convert.ToInt32(CodiceIstanza), CodiceMovimento);

            return modelli;
        }


        public ModelloDinamicoBase GetModelloDinamicoDaId(int idModello, int indice)
        {
            int codiceAnagrafe = Convert.ToInt32(this.CodiceAnagrafe);
            var dap = new AnagrafeDyn2DataAccessFactory(this.Database, this.IdComune, codiceAnagrafe);
            var loader = new ModelloDinamicoLoader(dap, this.IdComune, ContestoScriptEnum.Backoffice);
            return new BackendModelliFactory().CreaModelloAnagrafe(loader, idModello, indice, false);
        }

        public void AggiungiScheda(int idModello)
        {
            AnagrafeDyn2ModelliT mod = new AnagrafeDyn2ModelliT();
            mod.Idcomune = this.IdComune;
            mod.Codiceanagrafe = Convert.ToInt32(this.CodiceAnagrafe);
            mod.FkD2mtId = idModello;

            new AnagrafeDyn2ModelliTMgr(this.Database).Insert(mod);
        }

        public void EliminaScheda(int idModello)
        {
            AnagrafeDyn2ModelliTMgr mgr = new AnagrafeDyn2ModelliTMgr(this.Database);
            AnagrafeDyn2ModelliT mod = mgr.GetById(this.IdComune, Convert.ToInt32(this.CodiceAnagrafe), idModello);
            mgr.Delete(mod);
        }

        public void Close(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }

        public List<int> GetListaIndiciScheda(int idModello)
        {
            AnagrafeDyn2ModelliTMgr mgr = new AnagrafeDyn2ModelliTMgr(this.Database);
            return mgr.GetListaIndiciScheda(this.IdComune, Convert.ToInt32(this.CodiceAnagrafe), idModello);
        }

        #endregion

        public string GetUrlPaginaStorico(int idModello)
        {
            string url = "~/Istanze/DatiDinamici/Storico/AnagrafeDyn2Storico.aspx?Token={0}&CodiceAnagrafe={1}&IdModello={2}";

            return this.ResolveClientUrl(String.Format(url, this.Token,
                                                            this.CodiceAnagrafe,
                                                            idModello));
        }

        public bool VerificaEsistenzaStorico(int idModello)
        {
            AnagrafeDyn2ModelliTStoricoMgr mgr = new AnagrafeDyn2ModelliTStoricoMgr(this.Database);
            int cnt = mgr.ContaRigheStorico(this.IdComune, Convert.ToInt32(this.CodiceAnagrafe), idModello);

            return cnt > 0;
        }

        [WebMethod()]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public static object GetListaModelliDisponibili(string token, int codice, string partial, bool cercaTT)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            if (authInfo == null)
                return new object[] { new { label = -1, value = "Token non valido" } };

            using (var db = authInfo.CreateDatabase())
            {
                return new AnagrafeDyn2ModelliTMgr(db).GetModelliNonUtilizzati(authInfo.IdComune, codice, partial)
                                                     .Select(x => new { label = x.Descrizione, value = x.Codice });
            }
        }
    }
}

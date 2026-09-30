using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Posteggi;
using Init.SIGePro.Manager.Logic.DatiDinamici.ModelliFactory;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Script.Services;
using System.Web.Services;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Standard.Scripts;

namespace Sigepro.net.DatiDinamici.Mercati
{
    public partial class posteggi_dyn2_modelli : BasePage
    {
        public int IdPosteggio => Convert.ToInt32(this.Request.QueryString["IdPosteggio"]);

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            if (!this.IsPostBack)
            {
                this.BindTitoloPagina();
            }
        }

        private void BindTitoloPagina()
        {
            var posteggio = new Mercati_DMgr(this.Database).GetById(this.IdComune, this.IdPosteggio);
            var mercato = new MercatiMgr(this.Database).GetMercatoByIdPosteggio(this.IdComune, this.IdPosteggio);

            this.Title = $"Schede del posteggio {posteggio.CodicePosteggio} ({mercato.Descrizione})";
        }

        public List<ElementoListaModelli> GetListaModelli()
        {
            //new IstanzeDyn2ModelliTMgr(Database).GetModelliIstanza(IdComune, Convert.ToInt32(CodiceIstanza), CodiceMovimento);

            return new PosteggiDyn2ModelliService(this.Database, this.IdComune).GetModelliCollegati(this.IdPosteggio);
        }

        public ModelloDinamicoBase GetModelloDinamicoDaId(int idModello, int indice)
        {
            var dap = new PosteggiDyn2DataAccessFactory(this.Database, this.IdComune, this.IdPosteggio);
            var loader = new ModelloDinamicoLoader(dap, this.IdComune, ContestoScriptEnum.Backoffice);
            return new BackendModelliFactory().CreaModelloMercato(loader, idModello, indice, false);
        }

        public void AggiungiScheda(int idModello)
        {
            new PosteggiDyn2ModelliService(this.Database, this.IdComune).AggiungiModelloAPosteggio(this.IdPosteggio, idModello);
        }

        public void EliminaScheda(int idModello)
        {
            new PosteggiDyn2ModelliService(this.Database, this.IdComune).EliminaScheda(this.IdPosteggio, idModello);
        }

        public List<int> GetListaIndiciScheda(int idModello)
        {
            var mgr = new PosteggiDyn2ModelliService(this.Database, this.IdComune);
            return mgr.GetListaIndiciScheda(this.IdPosteggio, idModello);
        }

        [WebMethod()]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public static object GetListaModelliDisponibili(string token, int codice, string partial, bool cercaTT, string codiceMovimento = "")
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            if (authInfo == null)
                return new object[] { new { label = -1, value = "Token non valido" } };

            using (var db = authInfo.CreateDatabase())
            {
                return new PosteggiDyn2ModelliService(db, authInfo.IdComune).GetModelliNonUtilizzati(codice, partial, cercaTT)
                                                     .Select(x => new { label = x.Descrizione, value = x.Codice });
            }
        }


        public bool VerificaEsistenzaStorico(int idModello)
        {
            return false;
        }

        public void Close(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }
    }
}
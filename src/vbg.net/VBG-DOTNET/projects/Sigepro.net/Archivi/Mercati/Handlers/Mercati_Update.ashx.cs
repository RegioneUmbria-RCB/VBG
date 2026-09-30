using Init.SIGePro.Manager;
using System.Collections.Generic;
using System.Web;

namespace Sigepro.net.Archivi.Mercati.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$ 
    /// </summary>
    public class Mercati_Update : BaseHandler, IHttpHandler
    {

        protected override void DoProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            Init.SIGePro.Data.Mercati m = new MercatiAdapter(this._authenticationManager).ForUpdate();

            this.ControllaPresenze(m);

            MercatiMgr mgr = new MercatiMgr(this.Database);
            m = mgr.Update(m);

            Dictionary<string, object> dic = new Dictionary<string, object>();
            dic["IDCOMUNE"] = m.IdComune;
            dic["CODICEMERCATO"] = m.CodiceMercato;

            context.Response.Write(this.CreaResponse(dic));
        }

        private void ControllaPresenze(Init.SIGePro.Data.Mercati m)
        {
            MercatiPresenzeTMgr mp = new MercatiPresenzeTMgr(this.Database);
            if (mp.GetByCodiceMercato(this.IdComune, m.CodiceMercato.GetValueOrDefault(int.MinValue)).Count > 0)
            {
                MercatiMgr mgrOld = new MercatiMgr(this.Database);
                Init.SIGePro.Data.Mercati mOld = mgrOld.GetById(this.IdComune, m.CodiceMercato.GetValueOrDefault(int.MinValue));
                m.FlagRegContaAssenza = mOld.FlagRegContaAssenza;
                m.FlagContabilita = mOld.FlagContabilita;
            }
        }
    }
}

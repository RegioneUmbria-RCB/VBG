using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.SIGePro.Manager;
using System.Collections.Generic;

namespace Sigepro.net.Archivi.Mercati.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$ 
    /// </summary>
    public class Mercati_Update : BaseHandler, IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            Init.SIGePro.Data.Mercati m = MercatiAdapter.ForUpdate();
            
            ControllaPresenze(m);
            
            MercatiMgr mgr = new MercatiMgr(Database);
            m = mgr.Update(m);

            Dictionary<string, object> dic = new Dictionary<string, object>();
            dic["IDCOMUNE"] = m.IdComune;
            dic["CODICEMERCATO"] = m.CodiceMercato;

            context.Response.Write(CreaResponse(dic));
        }

        public bool IsReusable
        {
            get
            {
                return false;
            }
        }

        private void ControllaPresenze( Init.SIGePro.Data.Mercati m )
        {
            MercatiPresenzeTMgr mp = new MercatiPresenzeTMgr(Database);
            if (mp.GetByCodiceMercato(IdComune, m.CodiceMercato.GetValueOrDefault(int.MinValue)).Count > 0)
            {
                MercatiMgr mgrOld = new MercatiMgr(Database);
                Init.SIGePro.Data.Mercati mOld = mgrOld.GetById(IdComune, m.CodiceMercato.GetValueOrDefault(int.MinValue));
                m.FlagRegContaAssenza = mOld.FlagRegContaAssenza;
                m.FlagContabilita = mOld.FlagContabilita;
            }
        }
    }
}

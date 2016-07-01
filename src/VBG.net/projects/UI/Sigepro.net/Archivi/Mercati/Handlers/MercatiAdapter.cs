using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Globalization;

namespace Sigepro.net.Archivi.Mercati.Handlers
{
    public class MercatiAdapter : BaseAdapter 
    {
        public static Init.SIGePro.Data.Mercati ForInsert()
        {
            HttpContext context = HttpContext.Current;

            Init.SIGePro.Data.Mercati m = new Init.SIGePro.Data.Mercati();
            m.Attivo = context.Request.Form["ATTIVO"];

            m.CodiceMercato = null;
            if(!String.IsNullOrEmpty(context.Request.Form["CODICEMERCATO"]))
                m.CodiceMercato = Convert.ToInt32(context.Request.Form["CODICEMERCATO"]);

            m.Descrizione = context.Request.Form["DESCRIZIONE"];
            m.IdComune = IdComune;
            m.Note = context.Request.Form["NOTE"];
            m.Tipo_mercato = context.Request.Form["TIPO_MERCATO"];
            m.FlagRegContaAssenza = String.IsNullOrEmpty(context.Request.Form["flag_regcontassenza"]) ? "0" : context.Request.Form["flag_regcontassenza"];
            m.FlagContabilita = String.IsNullOrEmpty(context.Request.Form["FLAG_CONTABILITA"]) ? "0" : context.Request.Form["FLAG_CONTABILITA"];
            m.Software = context.Request.QueryString["SOFTWARE"];

            m.TipoManifest = null;
            if(!String.IsNullOrEmpty(context.Request.Form["TIPO_MANIFEST"]))
                m.TipoManifest = Convert.ToInt32(context.Request.Form["TIPO_MANIFEST"]);

            m.OraIngressoInizio = context.Request.Form["ORA_INGRESSO_INIZIO"];
            m.OraIngressoFine = context.Request.Form["ORA_INGRESSO_FINE"];
            m.OraVenditaInizio = context.Request.Form["ORA_VENDITA_INIZIO"];
            m.OraVenditaFine = context.Request.Form["ORA_VENDITA_FINE"];
            m.OraSgomberoInizio = context.Request.Form["ORA_SGOMBERO_INIZIO"];
            m.OraSgomberoFine = context.Request.Form["ORA_SGOMBERO_FINE"];
            m.DetDirigenzialeNumero = context.Request.Form["DET_DIRIGENZIALE_NUMERO"];
            if (!string.IsNullOrEmpty(context.Request.Form["DET_DIRIGENZIALE_DATA"]))
            {
                m.DetDirigenzialeData =DateTime.ParseExact( context.Request.Form["DET_DIRIGENZIALE_DATA"], "dd/MM/yyyy", CultureInfo.CurrentCulture );
            }
            return m;
        }

        public static Init.SIGePro.Data.Mercati ForUpdate()
        {
            Init.SIGePro.Data.Mercati m = ForInsert();
            HttpContext context = HttpContext.Current;
            m.Codiceoggetto = null;
            if(!string.IsNullOrEmpty(context.Request.Form["codiceoggetto"]))
                m.Codiceoggetto = Convert.ToInt32(context.Request.Form["codiceoggetto"]);

            return m;
        }
    }
}

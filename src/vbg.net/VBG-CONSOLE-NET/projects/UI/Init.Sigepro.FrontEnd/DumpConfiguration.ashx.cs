using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Ninject;
using System.Collections.Generic;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd
{
    /// <summary>
    /// Summary description for DumpConfiguration
    /// </summary>
    public class DumpConfiguration : Ninject.Web.HttpHandlerBase, IReadOnlySessionState
    {
        public class ProprietaParametro
        {
            public readonly string Nome;
            public readonly string Tipo;
            public readonly string Valore;

            public ProprietaParametro(string nome, string tipo, string valore)
            {
                this.Nome = nome;
                this.Tipo = tipo;
                this.Valore = valore;
            }
        }

        [Inject]
        public IConfigurazione<ParametriVbg> CfgAspetto { get; set; }
        [Inject]
        public IConfigurazione<ParametriDatiCatastali> CfgDatiCatastali { get; set; }
        [Inject]
        public IConfigurazione<ParametriInvio> CfgInvio { get; set; }
        [Inject]
        public IConfigurazione<ParametriLogin> CfgLogin { get; set; }
        [Inject]
        public IConfigurazione<ParametriMenuV2> CfgMenu { get; set; }
        [Inject]
        public IConfigurazione<ParametriStc> CfgStc { get; set; }
        [Inject]
        public IConfigurazione<ParametriVisura> CfgVisura { get; set; }
        [Inject]
        public IConfigurazione<ParametriWorkflow> CfgWorkflow { get; set; }
        [Inject]
        public IConfigurazione<ParametriScadenzario> CfgScadenzario { get; set; }
        [Inject]
        public IConfigurazione<ParametriRegistrazione> CfgRegistrazione { get; set; }
        [Inject]
        public IConfigurazione<ParametriSigeproSecurity> CfgSigeproSecurity { get; set; }
        [Inject]
        public IConfigurazione<ParametriSchedaCittadiniExtracomunitari> CfgSchedaCittadiniExtracomunitari { get; set; }
        [Inject]
        public IConfigurazione<ParametriStcConsole> CfgAidaSmart { get; set; }
        [Inject]
        public IConfigurazione<ParametriServiziAreaRiservata> CfgServiziAreaRiservata { get; set; }

        private HttpContext _context;

        protected override void DoProcessRequest(HttpContext context)
        {
            this._context = context;

            var listaParametri = new Dictionary<string, IEnumerable<ProprietaParametro>>
            {
                {"Aspetto",this.Analizza( this.CfgAspetto.Parametri )},
                {"DatiCatastali",this.Analizza( this.CfgDatiCatastali.Parametri )},
                {"Invio",this.Analizza( this.CfgInvio.Parametri )},
                {"Login",this.Analizza( this.CfgLogin.Parametri )},
                {"Menu",this.Analizza( this.CfgMenu.Parametri )},
                {"Registrazione",this.Analizza( this.CfgRegistrazione.Parametri )},
                {"Scadenzario",this.Analizza( this.CfgScadenzario.Parametri )},
                {"SchedaCittadiniExtracomunitari",this.Analizza( this.CfgSchedaCittadiniExtracomunitari.Parametri )},
                {"SigeproSecurity",this.Analizza( this.CfgSigeproSecurity.Parametri )},
                {"Stc",this.Analizza( this.CfgStc.Parametri )},
                {"Visura",this.Analizza( this.CfgVisura.Parametri )},
                {"Workflow",this.Analizza( this.CfgWorkflow.Parametri )},
                {"Servizi Area Riservata", this.Analizza(this.CfgServiziAreaRiservata.Parametri)},
                {"STC Console",this.Analizza( this.CfgAidaSmart.Parametri )}/*,
                {"Pagamenti MIP",Analizza( CfgPagamentiMIP.Parametri )},*/
			};

            this.Dump(listaParametri);


        }

        private void Dump(Dictionary<string, IEnumerable<ProprietaParametro>> listaParametri)
        {
            this._context.Response.Write("<table style='width:100%;border-collapse:collapse;border:1px solid #666'>");

            foreach (var key in listaParametri.Keys)
            {
                this.DumpGroup(key, listaParametri[key]);
            }

            this._context.Response.Write("</table>");
        }

        private IEnumerable<ProprietaParametro> Analizza(object parametri)
        {
            var fields = parametri.GetType().GetFields();

            foreach (var prop in fields)
            {
                var value = "{null}";
                var objVal = prop.GetValue(parametri);

                if (objVal != null)
                    value = objVal.ToString();

                yield return new ProprietaParametro("[F]" + prop.Name, prop.FieldType.ToString(), value);
            }

            var props = parametri.GetType().GetProperties();

            foreach (var prop in props)
            {
                var value = "{null}";
                var objVal = prop.GetValue(parametri, null);

                if (objVal != null)
                    value = objVal.ToString();

                yield return new ProprietaParametro("[P]" + prop.Name, prop.PropertyType.ToString(), value);
            }
        }

        private void DumpGroup(string keyName, IEnumerable<ProprietaParametro> parametri)
        {
            this._context.Response.Write("<tr><th colspan='3' style='background-color:#666;color:#fff'>" + keyName + "</th></tr>");



            foreach (var prop in parametri)
            {
                this._context.Response.Write("<tr>");
                this._context.Response.Write("<td><b>" + prop.Nome + "</b></td>");
                this._context.Response.Write("<td>" + prop.Tipo + "</td>");
                this._context.Response.Write("<td>" + prop.Valore + "</td>");
                this._context.Response.Write("</tr>");
            }

            this._context.Response.Write("<tr><td colspan='3'><br /><br /></td></tr>");
        }

        public override bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}
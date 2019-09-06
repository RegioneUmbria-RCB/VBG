[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.RegistrazioneRoutes), "Start")]


namespace Init.Sigepro.FrontEnd.App_Start
{

    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Web;
    using Microsoft.Web.Infrastructure.DynamicModuleHelper;
    using Init.Sigepro.FrontEnd.HttpModules;
    using System.Web.Routing;

    // 
    public static class RegistrazioneRoutes
    {
        /// <summary>
        /// Starts the application
        /// </summary>
        public static void Start()
        {
            RegisterRoutes(RouteTable.Routes);
        }

        private static void RegisterRoutes(RouteCollection routes)
        {
            routes.MapPageRoute("servizi", "servizi/{alias}/{software}/{servizio}", "~/servizi/attivaServizio.aspx");
            routes.MapPageRoute("login", "login/{alias}/{software}", "~/servizi/login.aspx");
            routes.MapPageRoute("visura", "visura/{alias}/{software}", "~/visura/attivaVisura.aspx");
            routes.MapPageRoute("visura-dettaglio", "visura/{alias}/{software}/{uuid}", "~/visura/attivaVisura.aspx");
            routes.MapPageRoute("visura-dettaglio2", "v/{alias}/{software}/{uuid}", "~/visura/attivaVisura.aspx");
            routes.MapPageRoute("rientroDaDomandaLdp", "riprendiDomandaLdp/{identificativoDomanda}/{tokenPartnerApp}", "~/riprendiDomanda/riprendiDomandaLDP.aspx");
            routes.MapPageRoute("rientroDaDomandaLdp2", "riprendiDomandaLdp/{identificativoDomanda}", "~/riprendiDomanda/riprendiDomandaLDP.aspx");
            routes.MapPageRoute("rientroDaDomandaLdpLivorno", "riprendiDomandaLdpLivorno/{identificativoDomanda}/{token}", "~/riprendiDomanda/riprendiDomandaLDPLivorno.aspx");
            routes.MapPageRoute("copia-da-domanda", "nuovo-da-copia/{alias}/{software}/{idDomanda}", "~/riprendidomanda/nuovo-come-copia.aspx");
            routes.MapPageRoute("trieste-accesso-atti", "trieste-accesso-atti/{idDomanda}/{token}", "~/riprendiDomanda/trieste-accesso-atti.aspx");

            // compilazione moduli per FVG
            routes.MapPageRoute("compilazione-moduli-fvg", "modulifvg/{alias}/{software}/compila/{istanza}/{cod-modulo}", "~/moduli-fvg/compilazione/index.aspx");
        }
    }
}
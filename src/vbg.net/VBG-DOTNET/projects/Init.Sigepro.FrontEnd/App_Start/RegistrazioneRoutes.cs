using System.Web.Routing;

[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.RegistrazioneRoutes), "Start")]

namespace Init.Sigepro.FrontEnd.App_Start
{
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
            // Convertiti
            routes.MapPageRoute("rientroDaDomandaLdp", "riprendiDomandaLdp/{identificativoDomanda}/{tokenPartnerApp}", "~/riprendiDomanda/riprendiDomandaLDP.aspx");
            routes.MapPageRoute("rientroDaDomandaLdp2", "riprendiDomandaLdp/{identificativoDomanda}", "~/riprendiDomanda/riprendiDomandaLDP.aspx");
            routes.MapPageRoute("rientroDaDomandaLdpLivorno", "riprendiDomandaLdpLivorno/{identificativoDomanda}/{token}", "~/riprendiDomanda/riprendiDomandaLDPLivorno.aspx");
            routes.MapPageRoute("trieste-accesso-atti", "trieste-accesso-atti/{idDomanda}/{token}", "~/riprendiDomanda/trieste-accesso-atti.aspx");
            routes.MapPageRoute("visura", "visura/{alias}/{software}", "~/visura/attivaVisura.aspx");
            routes.MapPageRoute("visura-dettaglio", "visura/{alias}/{software}/{uuid}", "~/visura/attivaVisura.aspx");
            routes.MapPageRoute("visura-dettaglio2", "v/{alias}/{software}/{uuid}", "~/visura/attivaVisura.aspx");
            routes.MapPageRoute("login", "login/{alias}/{software}", "~/servizi/login.aspx");
            routes.MapPageRoute("presenta-intervento-locale", "presenta-domanda-locale/{alias}/{software}/{codiceIntervento}", "~/servizi/presentaDomandaLocale.aspx");
            // Presentazione interventi regionali
            routes.MapPageRoute("presenta-intervento-regionale", "presenta-domanda-console/{alias}/{software}/{codiceIntervento}", "~/servizi/presentaDomandaConsole.aspx");

            routes.MapPageRoute("servizi", "servizi/{alias}/{software}/{servizio}", "~/servizi/attivaServizio.aspx");
            routes.MapPageRoute("copia-da-domanda", "nuovo-da-copia/{alias}/{software}/{idDomanda}", "~/riprendidomanda/nuovo-come-copia.aspx");
            // compilazione moduli per FVG
            routes.MapPageRoute("compilazione-moduli-fvg", "modulifvg/{alias}/{software}/compila/{istanza}/{cod-modulo}", "~/moduli-fvg/compilazione/index.aspx");
            // copia da istanza presentata
            routes.MapPageRoute("copia-da-istanza-presentata", "copia-da-istanza-presentata/{alias}/{software}/{uuid-istanza}", "~/riprendidomanda/nuovo-da-template-istanza-presentata.aspx");

            routes.MapPageRoute("riprendi-domanda", "{alias}/{software}/riprendi-domanda/{idDomanda}", "~/riprendidomanda/riprendi-domanda.aspx");

            //Cartografico
            routes.MapPageRoute("cartografico-return", "cartografico-return/{token}/{idDomanda}/{uuidLocalizzazione}/{idStep}", "~/riprendidomanda/cartografico-return.aspx");
            //Sit Cartografia
            routes.MapPageRoute("sit-return-lista-pratiche", "sit-return-lista-pratiche/{token}/{idcomune}/{software}", "~/reserved/sit-return-lista-pratiche.aspx");
            routes.MapPageRoute("sit-return-dettaglio-pratica", "sit-return-dettaglio-pratica/{token}/{idcomune}/{software}/{uuid-pratica}", "~/reserved/sit-return-dettaglio-pratica.aspx");
        }
    }
}
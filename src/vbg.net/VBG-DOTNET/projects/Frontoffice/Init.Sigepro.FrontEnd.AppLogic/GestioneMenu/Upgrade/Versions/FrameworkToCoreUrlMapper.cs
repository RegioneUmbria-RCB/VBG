using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions
{
    public class FrameworkToCoreUrlMapper : IFrameworkToCoreUrlMapper
    {
        private static readonly Dictionary<string, string> old2New = new Dictionary<string, string>
        {
            {"~/reserved/accesso-atti/accesso-atti-list.aspx", "{alias}/{software}/accessoattilista"},
            {"~/reserved/benvenuto.aspx", "{alias}/{software}/benvenuto"},
            {"~/reserved/commissioni/lista-commissioni.aspx", "{alias}/{software}/commissionilista"},
            {"~/reserved/istanzepresentate.aspx", "{alias}/{software}/istanzepresentate"},
            {"~/reserved/archiviopratiche.aspx", "{alias}/{software}/archiviopratiche"},
            {"~/reserved/scadenzario.aspx", "{alias}/{software}/scadenzario"},
            {"~/reserved/enti-terzi/et-lista-pratiche.aspx", "{alias}/{software}/entiterzilistapratiche"},
            {"~/reserved/enti-terzi/et-scadenzario.aspx", "{alias}/{software}/entiterziscadenzario"},
            {"~/reserved/istanzeinsospeso.aspx", "{alias}/{software}/istanze-in-sospeso"},
            {"~/reserved/inserimentoistanza/benvenuto.aspx", "{alias}/{software}/inserimento-istanza/nuova-domanda"},
            {"~/reserved/nuovaistanza.aspx", "{alias}/{software}/inserimento-istanza/nuova-domanda"},
            {"~/reserved/vbg-nuova-domanda.aspx" , "{alias}/{software}/console-nuova-domanda"},
            {"~/reserved/vbg-istanze-in-sospeso.aspx", "{alias}/{software}/console-istanze-in-sospeso" }
        };

        public string TryMapUrl(string url)
        {
            if (old2New.TryGetValue(url.ToLower(), out var newUrl))
            {
                return newUrl;
            }

            return null;
        }
    }
}

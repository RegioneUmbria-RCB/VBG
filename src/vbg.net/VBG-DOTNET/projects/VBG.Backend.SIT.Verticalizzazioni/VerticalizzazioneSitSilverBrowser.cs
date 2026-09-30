
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    public class VerticalizzazioneSitSilverBrowser : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_SILVERBROWSER";
            public const string ServiceBaseUrl = "SERVICE_BASE_URL";
            public const string MapZoomDaPunto = "MAP_ZOOM_DA_PUNTO";
            public const string MapZoomDaParticella = "MAP_ZOOM_DA_PARTICELLA";
            public const string MapZoomDaPuntoBo = "MAP_ZOOM_DA_PUNTO_BO";
            public const string MapZoomDaParticellaBo = "MAP_ZOOM_DA_PARTICELLA_BO";
            public const string MapTooltip = "MAP_URL_TOOLTIP";

        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;


        public VerticalizzazioneSitSilverBrowser()
            : base()
        {
        }

        public VerticalizzazioneSitSilverBrowser(string idComuneAlias, string software)
            : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string ServiceBaseUrl
        {
            get { return this.GetString(Constants.ServiceBaseUrl); }
            set { this.SetString(Constants.ServiceBaseUrl, value); }
        }

        public string MapZoomDaPunto
        {
            get { return this.GetString(Constants.MapZoomDaPunto); }
            set { this.SetString(Constants.MapZoomDaPunto, value); }
        }


        public string MapZoomDaParticella
        {
            get { return this.GetString(Constants.MapZoomDaParticella); }
            set { this.SetString(Constants.MapZoomDaParticella, value); }
        }

        public string MapZoomDaParticellaBo
        {
            get { return this.GetString(Constants.MapZoomDaParticellaBo); }
            set { this.SetString(Constants.MapZoomDaParticellaBo, value); }
        }

        public string MapZoomDaPuntoBo
        {
            get { return this.GetString(Constants.MapZoomDaPuntoBo); }
            set { this.SetString(Constants.MapZoomDaPuntoBo, value); }
        }
    }
}

using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLoghi
{
    public class LoghiAreaRiservataService
    {
        private static class Constants
        {
            public const string IdLogoStiliFrontoffice = "logo_suap";
        }



        private IOggettiService _oggettiService;


        public LoghiAreaRiservataService(IOggettiService oggettiService)
        {
            this._oggettiService = oggettiService;
        }




        public BinaryFile GetLogoAreaRiservata(string Software)
        {
            // Logica di lettura del logo
            // 1) Verticalizzazione AREA_RISERVATA
            // 2) Logo comune
            // 3) Stili frontoffice



            return _oggettiService.GetRisorsaFrontoffice(Constants.IdLogoStiliFrontoffice);
        }
    }
}

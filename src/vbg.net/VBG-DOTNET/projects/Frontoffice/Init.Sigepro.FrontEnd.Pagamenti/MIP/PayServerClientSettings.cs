//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//using System.Threading.Tasks;

//namespace Init.Sigepro.FrontEnd.Pagamenti.MIP
//{
//    internal class PayServerClientSettings
//    {
//        public readonly string ChiaveSegreta;
//        public readonly string UrlServizi;
//        public readonly string ProxyAddress;
//        public readonly string ComponentName;
//        public readonly int WindowMinutes;
//        public readonly string ChiaveIV;
//        public readonly string IdPortale;

//        public PayServerClientSettings(PagamentiSettings settings)
//        {
//            this.ChiaveSegreta = settings.ChiaveSegreta;
//            this.UrlServizi    = settings.UrlServizi;
//            this.ProxyAddress  = settings.ProxyAddress;
//            this.ComponentName = settings.ComponentName;
//            this.WindowMinutes = String.IsNullOrEmpty(settings.WindowMinutes) ? 20 : Convert.ToInt32(settings.WindowMinutes);
//            this.ChiaveIV = settings.IV;
//            this.IdPortale = settings.IdPortale;
//        }
//    }
//}

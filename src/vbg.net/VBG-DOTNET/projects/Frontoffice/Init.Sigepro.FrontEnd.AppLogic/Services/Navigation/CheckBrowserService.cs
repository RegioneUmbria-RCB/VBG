using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
    public class CheckBrowserService : ICheckBrowserService
    {

        public CheckBrowserService()
        {
        }


        public bool IsInternetExplorer(string userAgent)
        {
            if (userAgent.Contains("MSIE") || userAgent.Contains("Trident"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}

using Microsoft.Web.Infrastructure.DynamicModuleHelper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

[assembly: PreApplicationStartMethod(typeof(Sigepro.net.HttpModules.HttpModulesLoader), "Initialize")]

namespace Sigepro.net.HttpModules
{
    public class HttpModulesLoader
    {
        public static void Initialize()
        {
            DynamicModuleUtility.RegisterModule(typeof(AuthenticationDataInContextModule));
        }
    }
}
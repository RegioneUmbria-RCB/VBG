using Init.SIGePro.Manager.DTO.Modulistica;
using Init.SIGePro.Manager.Logic.GestioneModulistica;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsSIGePro
{
    /// <summary>
    /// Summary description for WsModulistica
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class WsModulistica : SigeproWebService
    {

        [WebMethod]
        public CategoriaModulisticaDto[] GetModulistica(string token, string software)
        {
            var authInfo = CheckToken(token);

            return new ModulisticaService(authInfo).GetModulisticaFrontoffice(software).ToArray();
        }
    }
}

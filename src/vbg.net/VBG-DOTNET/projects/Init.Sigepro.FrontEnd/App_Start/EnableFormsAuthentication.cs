[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.EnableFormsAuthentication), "PreApplicationStart")]


namespace Init.Sigepro.FrontEnd.App_Start
{
    using System.Collections.Specialized;
    using System.Web.Security;

    public class EnableFormsAuthentication
    {

        public static void PreApplicationStart()
        {
            FormsAuthentication.EnableFormsAuthentication(new NameValueCollection());
        }
    }
}
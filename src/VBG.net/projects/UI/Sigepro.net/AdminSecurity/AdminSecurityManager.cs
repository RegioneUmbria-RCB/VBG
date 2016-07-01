using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Authentication;
using System.Text;
using System.Security.Cryptography;

namespace Sigepro.net.AdminSecurity
{
	public static class AdminSecurityManager
	{
		//public static bool IsCurrentUserAdmin
		//{
		//    get
		//    {
		//        if (HttpContext.Current.Session["AdminToken"] == null || String.IsNullOrEmpty(HttpContext.Current.Session["AdminToken"].ToString()))
		//            return false;

		//        string adminToken = HttpContext.Current.Session["AdminToken"].ToString();

		//        try
		//        {
		//            return AuthenticationManager.CheckToken(adminToken).IsAdmin;
		//        }
		//        catch (NullReferenceException)
		//        {
		//            return false;
		//        }
		//    }
		//}

		public static bool LogonAsAdmin(string idComune, string username, string password)
		{
			string passMd5 = GetMd5(password);
			AuthenticationInfo authInfo = AuthenticationManager.Login(idComune, username, passMd5, true);

			if (authInfo == null)
			{
				return false;
			}

			HttpContext.Current.Session["AdminToken"] = authInfo.Token;

			return true;
		}

		private static string GetMd5(string text)
		{
			byte[] pass = Encoding.UTF8.GetBytes(text);
			MD5 md5 = new MD5CryptoServiceProvider();
			var bytes = md5.ComputeHash(pass);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bytes.Length; i++)
				sb.Append(bytes[i].ToString("X2"));

			return sb.ToString().ToLower();
		}
	}
}

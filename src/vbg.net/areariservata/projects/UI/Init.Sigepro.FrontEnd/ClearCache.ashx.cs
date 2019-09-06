using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd
{
	/// <summary>
	/// Summary description for ClearCache
	/// </summary>
	public class ClearCache : IHttpHandler, IReadOnlySessionState
	{

		public void ProcessRequest(HttpContext context)
		{
            context.Response.ContentType = "text/plain";
            context.Response.Write("---------------------------------------------\r\n");
            context.Response.Write($"> {context.Session.Count} oggetti in sessione eliminati\r\n"); 
            context.Session.Clear();
            context.Response.Write("---------------------------------------------\r\n");


            var keyList = new List<object>();

			var it = context.Cache.GetEnumerator();

			while ( it.MoveNext( ) )
				keyList.Add(it.Key);

            context.Response.Write($"> Pulizia cache ({keyList.Count} elementi):\r\n");
            context.Response.Write("---------------------------------------------\r\n");

            keyList.ForEach( x =>
            {
                context.Response.Write($"\t{x.ToString()}... ");
                context.Cache.Remove(x.ToString());
                context.Response.Write("Eliminato\r\n");
                
            });

			try
            {
                context.Response.Write("---------------------------------------------\r\n");
                context.Response.Write($">Scaricamento App domain... ");


                System.Web.HttpRuntime.UnloadAppDomain();

                context.Response.Write($"Completato\r\n");
                context.Response.Write("---------------------------------------------\r\n");
            }
			catch (Exception )
			{
				context.Response.Write("impossibile scaricare l'app domain");
			}

			context.Response.Write("\r\n\r\ncache riciclata");

            var str = @"
                                                          >*
                                                   #      >*
                                                   #  ###>***~~~~~|
                                                   ####  *****^^^#
                                              _____|       *#####
                                             | ^^^#   \/ \/ #
                                            ##^^###         |
                                             ### ##*        *
 |_                                ********~~|_____>         *
 \\|_                 ________************        #>>***    ***
 \\\\|_             __|     *************        ## >>>*  *****
 |___  |______   __|         ***********       ##>### ^^^^^^^^^^
    |____    |__|           **********       >>>>## ^<^^^^^@^^^^^
         #          ***      ********      **>>>># ^<^^@^^^@^^^^^
          #      ***********    ******     *>>>## ^<<^^^^^^^^<<<
          #      ***********    ******    **>>>## ^<<<<^^^<<<<<
         #        *********      ****   ***>>>#### ^<<<<<<<<<
         #         **********          ****>>>###### <<<<<
         ##        **********          ****>>>>##      ##
         ##         **  ***             ****>>>>        #     ##XXX
         ##**                            *******         ##>>>>#XX
          >>*                             ******         #######XXX
          >>*****                           ***         ##__
           >>*****   **** ***               **    *****     \__
           >># **    *********              *********>>>#      XXX
           ##        *********              *******>>>>>##     XXX
        |~~           ********                 *>>>>> >#######XXX
    X~~~~ ###          *********          ######>          >>>XXXX
  XXX  #>>>##          ********>>##  #######
   XXX#>      #   ##>>>>>>>>>>>>>###UUUUU^^
   XXX        #  ####>>>>>>>>>>UUUUUUUUU^^
              #  >>           UUUUUU^^^<()
             #  >              U()^<()  ()
           *#  *>               ()  ()
          **** #
            ***
            **
";
            context.Response.Write("\r\n\r");
            context.Response.Write(str);
        }

		public bool IsReusable
		{
			get
			{
				return false;
			}
		}
	}
}
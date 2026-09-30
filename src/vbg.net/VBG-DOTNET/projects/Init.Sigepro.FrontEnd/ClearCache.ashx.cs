using System;
using System.Collections.Generic;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd
{
    /// <summary>
    /// Summary description for ClearCache
    /// </summary>
    public class ClearCache : IHttpHandler, IReadOnlySessionState
    {
        private static class Constants
        {
            public const string ReloadAppDomainQs = "reloadAppDomain";
        }

        private HttpContext _context;
        public void ProcessRequest(HttpContext context)
        {
            this._context = context;
            var reloadAppDomain = context.Request.QueryString[Constants.ReloadAppDomainQs] != null && context.Request.QueryString[Constants.ReloadAppDomainQs] == "1";
            context.Response.ContentType = "text/html";
            context.Response.Write(@"
<!DOCTYPE html>
<html lang='en'>

<head>
    <meta charset='UTF-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title>Document</title>
    <style>
    body {
      font-family: 'Courier New', monospace;
    }

.app-domain {
    padding: 12px;
    border: 1px solid #faebcc;
    background-color: #fcf8e3;
}

.btn-danger {
  color: #fff;
  background-color: #d9534f;
  border-color: #d43f3a;
}
.btn {
  display: inline-block;
  margin-bottom: 0;
  font-weight: 400;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -ms-touch-action: manipulation;
  touch-action: manipulation;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  padding: 6px 12px;
  font-size: 14px;
  line-height: 1.42857143;
  border-radius: 4px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

@media (prefers-color-scheme: dark) {
    body {
        background-color: black;
        color: white;
    }

    .app-domain {
        color: black;
    }
}


    </style>
</head>

<body>");
            this.WriteLine("---------------------------------------------\r\n");
            this.WriteLine($"> {context.Application.Count} oggetti in application eliminati\r\n");
            context.Application.Clear();
            this.WriteLine("---------------------------------------------\r\n");

            this.WriteLine("---------------------------------------------\r\n");
            this.WriteLine($"> {context.Session.Count} oggetti in sessione eliminati\r\n");
            context.Session.Clear();
            this.WriteLine("---------------------------------------------\r\n");
            var keyList = new List<object>();
            var it = context.Cache.GetEnumerator();
            while (it.MoveNext())
                keyList.Add(it.Key);
            this.WriteLine($"> Pulizia cache ({keyList.Count} elementi):");
            this.WriteLine("---------------------------------------------");
            keyList.ForEach(x =>
            {
                this.Write($"&nbsp;&nbsp;&nbsp;&nbsp;{x}... ");
                context.Cache.Remove(x.ToString());
                this.WriteLine("Eliminato");
            });
            if (reloadAppDomain)
            {
                try
                {
                    this.WriteLine("---------------------------------------------");
                    this.Write($">Scaricamento App domain... ");
                    System.Web.HttpRuntime.UnloadAppDomain();
                    this.WriteLine($"Completato");
                    this.WriteLine("---------------------------------------------");
                }
                catch (Exception)
                {
                    this.WriteLine("impossibile scaricare l'app domain");
                }
            }
            else
            {
                this.WriteLine("---------------------------------------------");
                this.WriteLine($"<div class='app-domain'>App domain non scaricato, alcuni dati potrebbero essere rimasti nella cache.");
                this.WriteLine("Se le modifiche che hai fatto non hanno avuto effetto prova a scaricare l'app domain");
                this.WriteLine($"Le sessioni degli utenti connessi potrebbero perdere dati e <b>ricevere errori nelle schede dinamiche in compilazione</b><br/><br/>" + $"<a class='btn btn-danger' href='clearcache.ashx?{Constants.ReloadAppDomainQs}=1'>RIAVVIA ANCHE APP DOMAIN</a></div>");
            }

            this.WriteLine("");
            this.WriteLine("Cache riciclata");
            var str = @"<pre>
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
</pre>";
            this.WriteLine("");
            this.WriteLine(str);
            context.Response.Write(@"
</body>
</html> ");
        }

        private void WriteLine(string text)
        {
            this._context.Response.Write(text);
            this._context.Response.Write("<br/>");
        }

        private void Write(string text)
        {
            this._context.Response.Write(text);
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
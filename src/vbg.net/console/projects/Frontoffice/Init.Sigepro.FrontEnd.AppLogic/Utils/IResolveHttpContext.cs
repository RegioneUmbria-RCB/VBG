using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.Utils
{
    public interface IResolveHttpContext
    {
        HttpContext Get { get; }
    }

    public class ResolveHttpContext: IResolveHttpContext
    {
        public HttpContext Get
        {
            get { return HttpContext.Current; }
        }
    }
}

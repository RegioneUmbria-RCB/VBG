using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda
{
    public interface IIdPresentazioneResolver
    {
        int IdPresentazione { get; }
    }

    public class IdPresentazioneFromQuerystringResolver : IIdPresentazioneResolver
    {
        public int IdPresentazione => Convert.ToInt32(HttpContext.Current.Request.QueryString["IdPresentazione"]);
    }
}

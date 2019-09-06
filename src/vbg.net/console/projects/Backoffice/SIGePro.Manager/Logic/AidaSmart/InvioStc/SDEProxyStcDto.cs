using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AidaSmart.InvioStc
{
    public class SDEProxyStcDto
    {
        public class RiferimentiSportello
        {
            public int IdNodo { get; set; }
            public string IdEnte { get; set; }
            public string IdSportello { get; set; }

        }

        public string UrlStc { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public RiferimentiSportello SportelloMittente { get; set; }
        public RiferimentiSportello SportelloDestinatario { get; set; }
    }
}

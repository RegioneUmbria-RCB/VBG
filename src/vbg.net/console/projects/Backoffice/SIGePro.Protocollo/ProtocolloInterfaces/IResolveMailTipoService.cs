using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloInterfaces
{
    public interface IResolveMailTipoService
    {
        //string GetOggetto(string token, IParametriInterventoProtocolloService param);
        string Oggetto { get; set; }
        string Corpo { get; set; }

        //void SetMailTipo(string token, IParametriInterventoProtocolloService param);

    }
}

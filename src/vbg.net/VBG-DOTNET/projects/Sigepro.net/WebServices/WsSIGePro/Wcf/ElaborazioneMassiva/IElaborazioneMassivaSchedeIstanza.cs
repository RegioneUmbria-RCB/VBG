using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Sigepro.net.WebServices.WsSIGePro.Wcf.ElaborazioneMassiva
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IElaborazioneMassivaSchedeIstanza" in both code and config file together.
    [ServiceContract]
    public interface IElaborazioneMassivaSchedeIstanza
    {
        [OperationContract]
        EsitoElaborazioneMassivaSchede Elabora(string token, int idElaborazione);
    }
}

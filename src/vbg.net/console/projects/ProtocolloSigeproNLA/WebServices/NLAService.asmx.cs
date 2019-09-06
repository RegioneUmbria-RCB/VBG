using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Services.Protocols;

namespace ProtocolloSigeproNLA.WebServices
{
    /// <summary>
    /// Summary description for NLAService
    /// </summary>
    [WebService(Namespace = "http://sigepro.init.it/rte/definitions")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    [SoapDocumentService(RoutingStyle = SoapServiceRoutingStyle.RequestElement)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class NLAService : System.Web.Services.WebService, INlaSoap11
    {


        #region INlaSoap11 Members

        public RichiestaPraticheListaNLAResponse RichiestaPraticheListaNLA(RichiestaPraticheListaNLARequest RichiestaPraticheListaNLARequest)
        {
            throw new NotImplementedException();
        }

        public InserimentoPraticaNLAResponse InserimentoPraticaNLA(InserimentoPraticaNLARequest InserimentoPraticaNLARequest)
        {
            throw new NotImplementedException();
        }

        public RichiestaPraticaNLAResponse RichiestaPraticaNLA(RichiestaPraticaNLARequest RichiestaPraticaNLARequest)
        {
            throw new NotImplementedException();
        }

        public TestNLAResponse TestNLA(TestNLARequest TestNLARequest)
        {
            return new TestNLAResponse
            {
                nlaXsdVersion = XsdNlaVersion.V_1_4,
                typesXsdVersion = XsdTypesVersion.V_1_4
            };
        }

        public InserimentoAttivitaNLAResponse InserimentoAttivitaNLA(InserimentoAttivitaNLARequest InserimentoAttivitaNLARequest)
        {
            throw new NotImplementedException();
        }

        public AllegatoBinarioNLAResponse AllegatoBinarioNLA(AllegatoBinarioNLARequest AllegatoBinarioNLARequest)
        {
            throw new NotImplementedException();
        }

        #endregion
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.QuestionarioFo
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsQuestionarioFoService" in both code and config file together.
    [ServiceContract]
    public interface IWsQuestionarioFoService
    {
        [OperationContract]
        bool QuestionarioCompilato(string token, string uuidIstanza);

        [OperationContract]
        void SalvaQuestionario(string token, string uuidIstanza, int valutazione, string note);
    }
}

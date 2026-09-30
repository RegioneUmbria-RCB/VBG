using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using VerificaPagamentoOneriLivorno.WsPagamenti;

namespace VerificaPagamentoOneriLivorno
{
    public class VerificaPagamentiService
    {
        public static EsitoVerificaPagamentolivorno Verifica(string token, string strCodiceIstanza, string codicePagamento, string software="CE")
        {
            var svc = new VerificaPagamentiService(token, software);

            return svc.VerificaCodicePagamento(strCodiceIstanza, codicePagamento);
        }

        string _token;
        string _software;
        string _serviceUrl = "http://localhost/aspnet/WebServices/VerificaPagamenti/PagamentiLivornoService.asmx";

        public string Url {
            get { return this._serviceUrl; }
            set { this._serviceUrl = value; }
        }
        
        public VerificaPagamentiService(string token, string software="CE")
        {
            this._token = token;
            this._software = software;
        }

        public EsitoVerificaPagamentolivorno VerificaCodicePagamento(string strCodiceIstanza, string codicePagamento)
        {
            var binding = new BasicHttpBinding();
            var address = new EndpointAddress(this._serviceUrl);

            using(var ws = new WsPagamenti.PagamentiLivornoServiceSoapClient(binding, address))
            {
                try
                {
                    return ws.VerificaCodicePagamento(this._token, this._software, strCodiceIstanza, codicePagamento);
                }
                catch(Exception)
                {
                    ws.Abort();

                    throw;
                }
            }
        }
    }
}

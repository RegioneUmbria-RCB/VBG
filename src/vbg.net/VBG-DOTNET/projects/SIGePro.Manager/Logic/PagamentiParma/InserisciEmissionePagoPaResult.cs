using System;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class InserisciEmissionePagoPaResult
    {
        internal InserisciEmissionePagoPaResult(EsitoNotificaPagoPAReader reader)
        {
            Code = reader.Code;
            Message = reader.Message;
            Url = reader.Url;
            IdDbp = reader.IdDbp;
            GuidDbp = reader.GuidDbp;
        }

        internal InserisciEmissionePagoPaResult(int codiceRisposta, string descrizione)
        {
            Code = codiceRisposta;
            Message = descrizione;
        }
        /*
        internal InserisciEmissionePagoPaResult(string codiceRisposta, string descrizione)
        {
            Code = Convert.ToInt32(codiceRisposta);
            Message = descrizione;
        }*/

        public int Code { get; }
        public string Message { get; }
        public string Url { get; }
        public int IdDbp { get; }
        public Guid? GuidDbp { get; }
    }
}
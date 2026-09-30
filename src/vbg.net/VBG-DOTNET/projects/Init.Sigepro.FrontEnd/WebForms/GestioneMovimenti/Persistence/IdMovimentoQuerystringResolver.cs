using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.GestioneMovimenti.Persistence
{
    public class IdMovimentoQuerystringResolver : IIdMovimentoResolver
    {
        private static class Constants
        {
            public const string QuerystringParameter = "idMovimento";
        }

        public int IdMovimento
        {
            get
            {
                var idMovimento = HttpContext.Current.Request.QueryString[Constants.QuerystringParameter];

                if (String.IsNullOrEmpty(idMovimento))
                    throw new ArgumentException("Identificativo movimento non passato");

                return Convert.ToInt32(idMovimento);
            }
        }

        public void SetIdMovimento(int idMovimento)
        {
            throw new NotImplementedException();
        }
    }
}
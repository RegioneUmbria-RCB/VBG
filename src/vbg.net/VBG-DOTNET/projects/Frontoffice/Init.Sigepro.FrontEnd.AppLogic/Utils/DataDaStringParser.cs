using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Utils
{
    public class DataDaStringParser
    {
        private readonly string _dataDaForm;

        public DataDaStringParser(string dataDaForm)
        {
            _dataDaForm = dataDaForm;
        }

        public DateTime? Parse()
        {
            if (String.IsNullOrEmpty(_dataDaForm))
            {
                return null;
            }

            DateTime result = new DateTime();

            if (DateTime.TryParseExact(_dataDaForm, "dd/MM/yyyy", null, System.Globalization.DateTimeStyles.None, out result))
            {
                return result;
            }

            if (DateTime.TryParseExact(_dataDaForm, "dd-MM-yyyy", null, System.Globalization.DateTimeStyles.None, out result))
            {
                return result;
            }

            return null;
        }
    }
}

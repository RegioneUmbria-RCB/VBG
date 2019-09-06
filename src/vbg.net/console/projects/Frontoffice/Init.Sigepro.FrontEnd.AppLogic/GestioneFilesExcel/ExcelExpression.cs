using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    public class ExcelExpression
    {
        public static class Constants
        {
            public const string ExpressionIdentifier = "#XLSFO#";
            public const char Separator = '!';
        }

        public string Sheet { get; private set; }
        public string Cell { get; private set; }

        public ExcelExpression(string text)
        {
            Parse(text);
        }

        private void Parse(string text)
        {
            if (!text.ToUpperInvariant().StartsWith(Constants.ExpressionIdentifier))
            {
                throw new InvalidOperationException(String.Format("Il testo da analizzare ({0}) non è valido", text));
            }

            var parts = text.Substring(Constants.ExpressionIdentifier.Length).Split(Constants.Separator);

            if (parts.Length != 2)
            {
                throw new InvalidOperationException(String.Format("Il testo da analizzare ({0}) non contiene i riferimenti della cella", text));
            }

            this.Sheet = parts[0];
            this.Cell = parts[1];
        }
    }
}

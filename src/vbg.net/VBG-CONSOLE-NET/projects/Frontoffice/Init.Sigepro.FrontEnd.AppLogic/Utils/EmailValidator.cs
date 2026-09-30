using System;
using System.Text.RegularExpressions;

namespace Init.Sigepro.FrontEnd.AppLogic.Utils
{
    public static class EmailValidator
    {
        private static readonly Regex EmailRegex = new System.Text.RegularExpressions.Regex(
                @"^(?i)[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$",
                System.Text.RegularExpressions.RegexOptions.IgnoreCase | System.Text.RegularExpressions.RegexOptions.Compiled
            );

        public static bool ValidaEmail(string text, bool fallisciSeVuota = true)
        {
            if (String.IsNullOrEmpty(text))
            {
                if (fallisciSeVuota)
                {
                    return false;
                }

                return true;
            }

            return EmailRegex.IsMatch(text);
        }
    }
}

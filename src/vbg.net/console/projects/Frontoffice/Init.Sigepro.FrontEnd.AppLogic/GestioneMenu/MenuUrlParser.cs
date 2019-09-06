using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuUrlParser
    {
        bool _completaUrl = false;

        public MenuUrlParser(bool completaUrl)
        {
            this._completaUrl = completaUrl;
        }

        public string Completa(string url)
        {
            if (this._completaUrl && url.StartsWith("~") && url.IndexOf("?") < 0)
            {
                return url + "?idcomune={idcomune}&software={software}";
            }

            var searchStrs = new[] {
                "&token={token}",
                "?token={token}"
            };

            foreach (var s in searchStrs)
            {
                var lcase = url.ToLower();
                var startPos = lcase.IndexOf(s);

                if (startPos < 0)
                {
                    continue;
                }

                var part1 = url.Substring(0, startPos);
                var part2 = url.Substring(startPos + s.Length);

                url = part1 + part2;
            }

            return url;
        }
    }
}

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuUrlParser
    {
        bool _completaUrl = false;
        private readonly bool _permettiToken;

        public MenuUrlParser(bool completaUrl, bool permettiToken)
        {
            _completaUrl = completaUrl;
            _permettiToken = permettiToken;
        }

        public string Completa(string url)
        {
            if (_completaUrl && url.StartsWith("~") && url.IndexOf("?") < 0)
            {
                return url + "?idcomune={idcomune}&software={software}";
            }

            var searchStrs = new string[0];

            if (!_permettiToken)
            {
                searchStrs = new[]{
                    "&token={token}",
                    "?token={token}"
                };
            }


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

using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class TokenizedUrl
    {
        private readonly List<QsParts> Querystring = new List<QsParts>();
        private readonly string _basePath = "";
        private readonly bool _isEmpty = true;
        private readonly bool _contieneAlias = false;
        private readonly bool _contieneSoftware = false;
        private readonly int _indicePopolaSoftware = -1;
        private readonly int _indicePopolaToken = -1;
        private readonly int _indicePopolaAlias = -1;

        public TokenizedUrl(string url)
        {
            if (String.IsNullOrEmpty(url))
            {
                return;
            }

            if (url == "#")
            {
                this._basePath = url;

                return;
            }

            this._isEmpty = false;

            var parts = url.Split('?');

            this._basePath = parts[0];

            if (parts.Length > 1)
            {
                var unparsedQs = this.Unescape(parts[1]);

                this.Querystring = unparsedQs.Split('&').Select(x => x.Split('=')).Select(x => new QsParts
                {
                    Key = x[0],
                    Lower = x[0].ToLowerInvariant(),
                    LowerValue = x[1].ToLowerInvariant(),
                    Value = x[1]
                }).ToList();

                //foreach(var qs in Querystring)
                for (int i = 0; i < this.Querystring.Count; i++)
                {
                    var qs = this.Querystring[i];

                    if (qs.Lower == "software") { this._contieneSoftware = true; }
                    if (qs.Lower == "idcomune") { this._contieneAlias = true; }

                    if (qs.LowerValue == "{software}") { this._indicePopolaSoftware = i; }
                    if (qs.LowerValue == "{idcomune}") { this._indicePopolaAlias = i; }
                    if (qs.LowerValue == "{alias}") { this._indicePopolaAlias = i; }
                    if (qs.LowerValue == "{token}") { this._indicePopolaToken = i; }
                }
            }
        }

        private string Unescape(string qsString)
        {
            return qsString.Replace("&amp;", "&");
        }

        public string Rebuild(IUrlEncoder urlEncoder)
        {
            var url = this._basePath;

            return this._isEmpty || !this.Querystring.Any() ?
                        this._basePath :
                        $"{this._basePath}?{String.Join("&", this.Querystring.Select(x => $"{x.Key}={urlEncoder.UrlEncode(x.Value)}").ToArray())}";
        }

        internal void AggiungiSoftware(string software)
        {
            if (this._contieneSoftware)
            {
                return;
            }

            this.Querystring.Add(new QsParts
            {
                Key = "software",
                Lower = "software",
                LowerValue = software,
                Value = software
            });
        }

        internal void AggiungiAlias(string alias)
        {
            if (this._contieneAlias)
            {
                return;
            }

            this.Querystring.Add(new QsParts
            {
                Key = "idcomune",
                Lower = "idcomune",
                LowerValue = alias,
                Value = alias
            });
        }

        internal void SostituisciSoftware(string software)
        {
            if (this._indicePopolaSoftware == -1)
            {
                return;
            }

            this.Querystring[this._indicePopolaSoftware].LowerValue = software;
            this.Querystring[this._indicePopolaSoftware].Value = software;
        }

        internal void SostituisciAlias(string alias)
        {
            if (this._indicePopolaAlias == -1)
            {
                return;
            }

            this.Querystring[this._indicePopolaAlias].LowerValue = alias;
            this.Querystring[this._indicePopolaAlias].Value = alias;
        }

        internal void SostituisciToken(string token)
        {
            if (this._indicePopolaToken == -1)
            {
                return;
            }

            this.Querystring[this._indicePopolaToken].LowerValue = token;
            this.Querystring[this._indicePopolaToken].Value = token;
        }
    }

}

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.PostedFileSpecifications
{
    public class EstensioneValidaPostedFileSpecification : IValidPostedFileSpecification
    {
        private readonly string[] _listaEstensioni;

        public EstensioneValidaPostedFileSpecification(string listaEstensioni)
        {
            this._listaEstensioni = String.IsNullOrEmpty(listaEstensioni) ?
                new string[0] :
                listaEstensioni.Split(',')
                                .Select(x => x.Trim().ToLower())
                                .Select(x => x.StartsWith(".") ? x.Substring(1) : x).ToArray();
        }

        public EstensioneValidaPostedFileSpecification(IEnumerable<string> listaEstensioni)
        {
            this._listaEstensioni = new string[0];

            if (listaEstensioni != null)
            {
                this._listaEstensioni = listaEstensioni.Select(x => x.Trim().ToLower())
                                                        .Select(x => x.StartsWith(".") ? x.Substring(1) : x)
                                                        .ToArray();
            }
        }

        public string ErrorMessage => $"Il file caricato ha un'estensione non ammessa. Estensioni ammesse: {String.Join(", ", this._listaEstensioni)}";

        public bool IsSatisfiedBy(HttpPostedFile item)
        {
            if (this._listaEstensioni.Length == 0)
            {
                return true;
            }

            return this._listaEstensioni.Contains(Path.GetExtension(item.FileName).Substring(1).ToLower());
        }
    }
}

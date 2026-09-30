using Microsoft.AspNetCore.Components.Forms;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public class EstensioneValidaPostedFileSpecification : IValidPostedFileSpecification, IValidPostedFileSpecificationAsync
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

        public string ErrorMessage => $"Il file caricato ha un'estensione non ammessa. Le estensioni ammesse sono: {String.Join(", ", this._listaEstensioni)}";

        public bool IsSatisfiedBy(IBrowserFile item)
        {
            if (this._listaEstensioni.Length == 0)
            {
                return true;
            }

            return this._listaEstensioni.Contains(Path.GetExtension(item.Name).Substring(1).ToLower());
        }

        public Task<bool> IsSatisfiedByAsync(IBrowserFile item)
        {
            if (this._listaEstensioni.Length == 0)
            {
                return Task.FromResult(true);
            }

            return Task.FromResult(this._listaEstensioni.Contains(Path.GetExtension(item.Name).Substring(1).ToLower()));
        }
    }
}

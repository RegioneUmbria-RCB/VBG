using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.SitLdp
{
    class LdpCatastoByCiviciClient : ILdpCatastoClient
    {
        string _codVia;
        string _civico;
        string _esponente;
        LdpCiviciClient _client;

        public LdpCatastoByCiviciClient(string codVia, string civico, string esponente, LdpCiviciClient client)
        {
            this._codVia = codVia;
            this._civico = civico;
            this._esponente = esponente;
            this._client = client;
        }


        public IEnumerable<string> GetListaFogli(string sezione)
        {
            var items = this._client.GetParticelleByToponimoCivicoEsponente(this._codVia, this._civico, this._esponente);

            return items.Where(x => (x.Sezione ?? String.Empty) == sezione).Select(x => x.Foglio).Distinct();
        }

        public IEnumerable<string> GetListaParticelle(string sezione, string foglio)
        {
            var items = this._client.GetParticelleByToponimoCivicoEsponente(this._codVia, this._civico, this._esponente);

            return items.Where(x => (x.Sezione ?? String.Empty) == sezione && x.Foglio == foglio).Select(x => x.Particella).Distinct();
        }

        public IEnumerable<string> GetListaSezioni()
        {
            var items = this._client.GetParticelleByToponimoCivicoEsponente(this._codVia, this._civico, this._esponente);

            return items.Select(x => x.Sezione).Distinct();
        }
    }
}

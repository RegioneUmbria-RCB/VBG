using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Runtime
{
    public class ParametroDaValidare
    {
        public readonly string Nome;
        public readonly string Valore;

        public ParametroDaValidare(string nome, string valore, IUrlDecoder urlDecoder)
        {
            this.Nome = nome;
            this.Valore = urlDecoder.Decode(valore).ToLower().Trim();
        }
    }
}

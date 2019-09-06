using ServiziAnagraficiFirenze;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestServiziAnagraficiFirenze
{
    class Program
    {
        static void Main(string[] args)
        {
            var codiceFiscale = "RSSGLL51H58Z133T";
            var anagrafiche = new DatiAnagraficiService().GetDatiAnagrafici(codiceFiscale);

            foreach (var anagrafica in anagrafiche)
            {
                // TODO: ...
            }
        }
    }
}

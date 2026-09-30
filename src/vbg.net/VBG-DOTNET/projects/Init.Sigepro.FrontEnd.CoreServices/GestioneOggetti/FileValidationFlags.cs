using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public class FileValidationFlags
    {
        public bool Obbligatorio { get; set; } = false;
        public int? DimensioneMassimaBytes { get; set; } = 0;
        public bool FirmatoDigitalmente { get; set; } = false;
        public IEnumerable<string> EstensioniAmmesse { get; set; } = Enumerable.Empty<string>();
    }
}

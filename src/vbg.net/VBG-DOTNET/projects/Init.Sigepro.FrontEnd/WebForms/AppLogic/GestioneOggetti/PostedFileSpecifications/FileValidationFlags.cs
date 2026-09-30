using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications
{
    public class FileValidationFlags
    {
        public bool Obbligatorio { get; set; } = false;
        public int? DimensioneMassimaBytes { get; set; } = 0;
        public bool FirmatoDigitalmente { get; set; } = false;
        public IEnumerable<string> EstensioniAmmesse { get; set; } = Enumerable.Empty<string>();
    }
}

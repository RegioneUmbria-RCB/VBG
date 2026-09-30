using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VBG.AppLogic.SSU.DataAccess.Dto
{
    public class FoDomandaSsuMinimalDto
    {
        public required string IdComune { get; set; }
        public required int FkIdDomanda { get; set; }
        public required int Stato { get; set; }

        public StatiDomandaSsuEnum StatoAsEnum => (StatiDomandaSsuEnum)this.Stato;
    }
}

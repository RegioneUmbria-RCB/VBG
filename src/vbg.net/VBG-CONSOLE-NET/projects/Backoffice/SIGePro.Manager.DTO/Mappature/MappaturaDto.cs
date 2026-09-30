using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Mappature
{
    [Serializable]
    public class MappaturaDto
    {
        public int IdCampo { get; set; }
        public string NomeCampo { get; set; }
        public string Espressione { get; set; }
    }
}

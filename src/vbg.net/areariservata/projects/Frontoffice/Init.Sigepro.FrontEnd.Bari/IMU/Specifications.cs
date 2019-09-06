using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.Bari.IMU
{
    public class AlmenoUnaAbitazioneSelezionataSpecification : ISpecification<IEnumerable<ImmobileImuDto>>
    {
        public bool IsSatisfiedBy(IEnumerable<ImmobileImuDto> item)
        {
            var abitazione = item.Where(x => x.TipoImmobile == TipoImmobileEnum.Abitazione).FirstOrDefault();

            return abitazione != null;
        }
    }

    public class SoloUnaAbitazioneSelezionataSpecification : ISpecification<IEnumerable<ImmobileImuDto>>
    {
        public bool IsSatisfiedBy(IEnumerable<ImmobileImuDto> item)
        {
            var abitazioni = item.Where(x => x.TipoImmobile == TipoImmobileEnum.Abitazione);

            return abitazioni.Count() <= 1;
        }
    }

    public class SelezionateMaxTrePertinenzeSpecification : ISpecification<IEnumerable<ImmobileImuDto>>
    {
        public bool IsSatisfiedBy(IEnumerable<ImmobileImuDto> item)
        {
            var pertinenze = item.Where(x => x.TipoImmobile != TipoImmobileEnum.Abitazione && x.TipoImmobile != TipoImmobileEnum.Sconosciuto);

            return pertinenze.Count() <= 3;
        }
    }

    public class SelezionateMaxUnaPertinenzaPerCategoriaCatastale : ISpecification<IEnumerable<ImmobileImuDto>>
    {
        public IEnumerable<string> PertinenzeMultiple { get; private set; }

        public bool IsSatisfiedBy(IEnumerable<ImmobileImuDto> item)
        {
            var pertinenze = item.Where(x => x.TipoImmobile != TipoImmobileEnum.Abitazione && x.TipoImmobile != TipoImmobileEnum.Sconosciuto);

            this.PertinenzeMultiple = pertinenze
                                        .GroupBy(x => x.CategoriaCatastale)
                                        .Where(x => x.Count() > 1)
                                        .Select(x => x.Key);

            return this.PertinenzeMultiple.Count() == 0;
        }
    }
}

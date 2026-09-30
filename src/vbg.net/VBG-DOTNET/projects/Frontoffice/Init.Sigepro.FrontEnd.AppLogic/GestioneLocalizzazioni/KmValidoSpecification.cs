using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
    public class KmValidoSpecification : ISpecification<string>
    {
        public bool IsSatisfiedBy(string valoreKm)
        {
            return true;
            /*
            if (String.IsNullOrEmpty(valoreKm))
            {
                return true;
            }

            var valori = valoreKm.Split(',');

            if (valori.Length > 2)
            {
                return false;
            }

            //km
            if (!Int32.TryParse(valori[0], out _))
            {
                return false;
            }

            if (valori.Length == 2)
            {
                //metro
                return Int32.TryParse(valori[1], out _);
            }

            return true;
            */
        }
    }
}

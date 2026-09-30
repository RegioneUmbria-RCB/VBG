using log4net;

namespace Init.SIGePro.Sit.Ravenna2
{
    public class Ravenna3MultipleResultsFound : Ravenna3Result
    {
        public Ravenna3MultipleResultsFound(ILog log) : base(true, log)
        {
            this.PiuDiUnElementoTrovato = true;
        }
    }
}

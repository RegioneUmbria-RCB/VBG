using log4net;

namespace VBG.Backend.SIT.AppLogic.Ravenna2
{
    public class Ravenna3MultipleResultsFound : Ravenna3Result
    {
        public Ravenna3MultipleResultsFound(ILog log) : base(true, log)
        {
            this.PiuDiUnElementoTrovato = true;
        }
    }
}

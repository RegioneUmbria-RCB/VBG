namespace AreaRiservataCore.Pages.Movimenti
{
    public class LastStepService
    {
        private int _step = 0;

        public void Set(int step)
        {
            _step = step;
        }

        public int Get()
        {
            return _step;
        }
    }
}

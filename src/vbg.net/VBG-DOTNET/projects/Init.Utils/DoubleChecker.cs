namespace Init.Utils
{
    public partial class DoubleChecker
    {
        public static bool IsDoubleEmpty(double? val)
        {
            if (val == null) return true;
            return val < -1E37d;
        }

        public static bool IsEmpty(decimal? val)
        {
            if (val == null) return true;
            return val < 0.0000001m;
        }
    }
}

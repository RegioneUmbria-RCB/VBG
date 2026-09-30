namespace Init.Sigepro.FrontEnd.AppLogic.Utils
{
    public enum RuntimeEnvironmentType
    {
        AreaRiservata,
        AreaRiservataCore,
        DomandaOnLine,
    }

    public interface IAreaRiservataRuntimeEnvironment
    {
        RuntimeEnvironmentType EnvironmentType { get; }
    }

    public class AreaRiservataRuntimeEnvironment : IAreaRiservataRuntimeEnvironment
    {
        public RuntimeEnvironmentType EnvironmentType => RuntimeEnvironmentType.AreaRiservata;
    }

    public class DomandaOnLineRuntimeEnvironment : IAreaRiservataRuntimeEnvironment
    {
        public RuntimeEnvironmentType EnvironmentType => RuntimeEnvironmentType.DomandaOnLine;
    }

    public class AreaRiservataCoreRuntimeEnvironment : IAreaRiservataRuntimeEnvironment
    {
        public RuntimeEnvironmentType EnvironmentType => RuntimeEnvironmentType.AreaRiservataCore;
    }
}

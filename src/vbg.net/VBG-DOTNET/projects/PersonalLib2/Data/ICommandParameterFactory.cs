namespace PersonalLib2.Data
{
    public interface ICommandParameterFactory
    {
        void AddParameter(string name, object value);
        ICommandParameterFactory Add(string parameterName, object value);
    }
}
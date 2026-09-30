using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.Infrastructure.ServiceModel
{
    public interface IBindingFactory
    {
        BasicHttpBinding CreateAndConfigure(string bindingName);
    }
}

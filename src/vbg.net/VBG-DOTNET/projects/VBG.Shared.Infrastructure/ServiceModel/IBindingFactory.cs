using System.ServiceModel;

namespace VBG.Shared.Infrastructure.ServiceModel
{
    public interface IBindingFactory
    {
        BasicHttpBinding CreateAndConfigure(string name);
    }
}

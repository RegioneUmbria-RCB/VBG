using VBG.Shared.Infrastructure.ServiceModel;
using SIGePro.Manager.VerticalizzazioniBase;
using System.Reflection;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class TipiProtocolloRegistry
    {
        private readonly Dictionary<string, Type> _protocolloRegistry = new Dictionary<string, Type>();
        private static TipiProtocolloRegistry _istance;

        private TipiProtocolloRegistry(Assembly assembly)
        {
            foreach (var type in assembly.GetTypes())
            {
                if (typeof(ProtocolloBase).IsAssignableFrom(type))
                {
                    this._protocolloRegistry.Add(type.Name, type);
                }
            }
        }

        public static void Initialize(Assembly assembly)
        {
            if (_istance is not null)
                throw new InvalidOperationException("TipiProtocolloRegistry è già stato inizializzato");

            _istance = new TipiProtocolloRegistry(assembly);
        }

        public static TipiProtocolloRegistry Istance => _istance ?? throw new InvalidOperationException("TipiProtocolloRegistry non è stato inizializzato");

        public ProtocolloBase? GetProtocolloIstance(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory, string name)
        {
            if (string.IsNullOrEmpty(name))
                return null;

            if (this._protocolloRegistry.TryGetValue(name, out var type))
            {
                var constructor = type.GetConstructor(BindingFlags.Public | BindingFlags.Instance, null, new Type[] { typeof(IVerticalizzazioniFactory), typeof(IBindingFactory) }, null);
               
                if (constructor != null)
                {
                    return (ProtocolloBase)Activator.CreateInstance(type, verticalizzazioniFactory, bindingFactory);
                }

                return (ProtocolloBase)Activator.CreateInstance(type, verticalizzazioniFactory);
            }

            return null;
        }

        public bool IsProtocolloSupportato(TipiProtocollo tipoProtocollo)
        {
            return this._protocolloRegistry.ContainsKey(tipoProtocollo.ToString());
        }
    }
}

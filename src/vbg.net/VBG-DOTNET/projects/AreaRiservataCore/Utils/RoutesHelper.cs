using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Microsoft.AspNetCore.Components;
using System.Reflection;
using System.Text;

namespace AreaRiservataCore.Utils
{
    public static class RoutesHelper
    {
        public class StepPropertyInfo
        {
            public required string Nome { get; init; }
            public required string Descrizione { get; init; }
            public required string ValoreDefault { get; init; }
        }

        public class RouteInfo
        {
            public required string ARRoute { get; init; }
            public required string FullRoute { get; init; }
            public required Type ComponentType { get; init; }
            public List<StepPropertyInfo> Properties { get; set; } = new List<StepPropertyInfo>();

            public override string ToString()
            {
                var sb = new StringBuilder($"- {this.FullRoute}");
                sb.AppendLine();
                sb.AppendLine($"\tAR route: {this.ARRoute}");
                sb.AppendLine("\tProperties:");

                foreach (var property in this.Properties)
                {
                    sb.AppendLine($"\t- {property.Nome}");

                    if (!String.IsNullOrEmpty(property.Descrizione))
                    {
                        sb.AppendLine($"\t  {property.Descrizione}");
                    }

                    if (!String.IsNullOrEmpty(property.ValoreDefault))
                    {
                        sb.AppendLine($"\t  ({property.ValoreDefault})");
                    }
                }

                return sb.ToString();
            }
        }

        public static List<RouteInfo> GetRoutesInserimentoDomanda(Assembly assembly)
        {
            // Get all the components whose base class is ComponentBase
            var components = assembly
                .ExportedTypes
                .Where(t => t.IsSubclassOf(typeof(ComponentBase)));

            var routes = components
                .Select(component => GetRouteFromComponent(component))
                .Where(route => route is not null)
                .ToList();

            return routes!;
        }

        private static RouteInfo? GetRouteFromComponent(Type component)
        {
            var attributes = component.GetCustomAttributes(inherit: true);

            var routeAttribute = attributes.OfType<RouteAttribute>().FirstOrDefault();

            if (routeAttribute is null)
            {
                // Only map routable components
                return null;
            }

            var areaRiservataRoute = ParseAreaRiservataRoute(routeAttribute.Template);

            if (areaRiservataRoute is null)
            {
                return null;
            }

            var fullRoute = routeAttribute.Template;
            var properties = component.GetProperties()
                .Where(p => p.GetCustomAttributes(typeof(StepPropertyAttribute), inherit: true).Any())
                .Select(p => new
                {
                    Property = p,
                    Attribute = (StepPropertyAttribute)p.GetCustomAttributes(typeof(StepPropertyAttribute), inherit: true).First()
                })
                .Select(p => new StepPropertyInfo
                {
                    Nome = p.Property.Name,
                    Descrizione = p.Attribute.Descrizione,
                    ValoreDefault = p.Attribute.ValoreDefault
                })
                .ToList();

            return new RouteInfo
            {
                FullRoute = fullRoute,
                ARRoute = areaRiservataRoute,
                ComponentType = component,
                Properties = properties
            };
        }

        private static string? ParseAreaRiservataRoute(string template)
        {
            const string placeholder = "inserimento-istanza";

            if (template.IndexOf($"/{placeholder}/", StringComparison.OrdinalIgnoreCase) == -1)
            {
                return null;
            }

            var parts = template.Split("/");

            var routeParts = parts.SkipWhile(part => !part.Equals(placeholder, StringComparison.OrdinalIgnoreCase))
                                  .Skip(1)
                                  .TakeWhile(part => !part.StartsWith('{') && !part.EndsWith('}'))
                                  .ToList();

            return String.Join('/', routeParts);
        }
    }
}

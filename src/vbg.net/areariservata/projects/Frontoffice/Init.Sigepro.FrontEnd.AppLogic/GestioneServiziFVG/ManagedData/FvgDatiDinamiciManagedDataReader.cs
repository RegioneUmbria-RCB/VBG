using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.XPath;
using static Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database.FvgDatabase;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData
{
    public class FvgDatiDinamiciManagedDataReader
    {
        public class ValoreCampoDinamicoEqualityComparer : IEqualityComparer<ValoreCampoDinamico>
        {
            public bool Equals(ValoreCampoDinamico x, ValoreCampoDinamico y)
            {
                return x.IdCampo == y.IdCampo &&
                       x.NomeCampo == y.NomeCampo &&
                       x.IndiceMolteplicita == y.IndiceMolteplicita;
            }

            public int GetHashCode(ValoreCampoDinamico obj)
            {
                return obj.IdCampo.GetHashCode() ^
                        obj.NomeCampo.GetHashCode() ^
                        obj.IndiceMolteplicita.GetHashCode();
            }
        }

        private static class Constants
        {
            public const string XPathFormatExpression = "//fvgDataSet/campoDinamico/nome[text()='{0}']/parent::*";
        }

        IEnumerable<string> _espressioniXPath;
        IFVGWebServiceProxy _serviceProxy;

        public FvgDatiDinamiciManagedDataReader(IEnumerable<string> nomiCampi, IFVGWebServiceProxy serviceProxy)
        {
            this._espressioniXPath = nomiCampi.Distinct().Select(x => string.Format(Constants.XPathFormatExpression, x));
            this._serviceProxy = serviceProxy;
        }

        public IEnumerable<ValoreCampoDinamico> GetValoriDatiDinamici(long codiceIstanza)
        {
            var managedData = this._serviceProxy.GetManagedDataDaCodiceIstanza(codiceIstanza);
            var rVal = new List<ValoreCampoDinamico>();

            var navigator = managedData.CreateNavigator();


            foreach (var espressione in this._espressioniXPath)
            {
                var campiDinamici = navigator.Select(espressione);

                while(campiDinamici.MoveNext())
                {
                    var campoDinamico = campiDinamici.Current;

                    if (campoDinamico == null)
                    {
                        continue;
                    }

                    var id = campoDinamico.SelectSingleNode("id")?.ValueAsInt;
                    var nome = campoDinamico.SelectSingleNode("nome")?.Value;
                    var indiceMolteplicita = campoDinamico.SelectSingleNode("indiceMolteplicita")?.ValueAsInt;
                    var valore = campoDinamico.SelectSingleNode("valore")?.Value;
                    var valoreDecodificato = campoDinamico.SelectSingleNode("valoreDecodificato")?.Value;

                    if (string.IsNullOrEmpty(valore))
                    {
                        continue;
                    }

                    rVal.Add(new ValoreCampoDinamico(id.Value, nome, indiceMolteplicita.Value, valore, valoreDecodificato));
                }

                
            }

            return rVal.Distinct(new ValoreCampoDinamicoEqualityComparer()).ToList();
        }
    }
}

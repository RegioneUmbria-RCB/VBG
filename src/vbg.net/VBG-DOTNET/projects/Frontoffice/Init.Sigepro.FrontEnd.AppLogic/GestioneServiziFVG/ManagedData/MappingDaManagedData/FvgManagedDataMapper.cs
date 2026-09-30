using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.DebugConfiguration;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Xml;
using System.Xml.Serialization;
using System.Xml.XPath;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData.MappingDaManagedData
{
    /// <summary>
    /// Oggetto che contiene la mappatura tra i campi dinamici e le espressioni xpath che permettono di recuperarne i valori dal manageddata
    /// di FEG
    /// </summary>
    [XmlRoot(ElementName = "managed-data-mappings")]
    public partial class FvgManagedDataMapper
    {

        public class Mapping
        {
            private string _dataType = "string";
            private string _dataFormat = "";

            [XmlElement(ElementName = "campo")]
            public string Campo { get; set; }

            [XmlElement(ElementName = "blank-se-non-trovato")]
            public bool BlankSeNonTrovato { get; set; } = false;

            [XmlElement(ElementName = "xpath")]
            public string XPath { get; set; }

            [XmlElement(ElementName = "data-type")]
            public string DataType
            {
                get { return String.IsNullOrEmpty(this._dataType) ? "string" : this._dataType; }
                set { this._dataType = value; }
            }

            [XmlElement(ElementName = "data-format")]
            public string DataFormat
            {
                get { return String.IsNullOrEmpty(this._dataFormat) ? "" : this._dataFormat; }
                set { this._dataFormat = value; }
            }

            public IEnumerable<ManagedDataValue> ApplyTo(XmlDocument document, Dictionary<string, int> registroCampi)
            {
                if (!registroCampi.ContainsKey(this.Campo))
                {
                    return Enumerable.Empty<ManagedDataValue>();
                }

                var idCampo = registroCampi[this.Campo];
                var navigator = document.CreateNavigator();
                var expression = navigator.Compile(this.XPath);

                var iterator = navigator.Select(expression);

                Debug.WriteLine($"{this.Campo} ({this.XPath}) occorrenze={iterator.Count}");

                var rVal = new List<ManagedDataValue>();
                var indice = 0;

                if (iterator.Count == 0 && this.BlankSeNonTrovato)
                {
                    rVal.Add(new ManagedDataValue
                    {
                        Id = idCampo,
                        NomeCampo = this.Campo,
                        Valore = "",
                        ValoreDecodificato = "",
                        IndiceMolteplicita = indice
                    });
                }
                else
                {

                    while (iterator.MoveNext())
                    {
                        var valore = this.ReadValue(iterator.Current);

                        if (!String.IsNullOrEmpty(valore.Valore) && this.DataType == "DateTime")
                        {
                            if (DateTime.TryParseExact(valore.Valore, this.DataFormat, null, System.Globalization.DateTimeStyles.None, out DateTime dt))
                            {
                                valore = new ItemValue(dt.ToString("yyyyMMdd"), dt.ToString("dd/MM/yyyy"));
                            }
                        }

                        rVal.Add(new ManagedDataValue
                        {
                            Id = idCampo,
                            NomeCampo = this.Campo,
                            Valore = valore.Valore,
                            ValoreDecodificato = valore.ValoreDecodificato,
                            IndiceMolteplicita = indice
                        });

                        ++indice;
                    }
                }

                return rVal;
            }

            protected virtual ItemValue ReadValue(XPathNavigator navigator)
            {
                return new ItemValue(navigator.Value, navigator.Value);
            }


        }

        [XmlElement(ElementName = "map")]
        public List<Mapping> Map { get; set; }

        public FvgManagedDataMapper()
        {
            this.Map = new List<Mapping>();
        }


        /// <summary>
        /// Carica la lista di mappature dal file xml che le rappresenta
        /// </summary>
        /// <param name="pathMapper">Permette di mappare un path relativo all'applicazione in un path assoluto del FS</param>
        /// <param name="fileName">Nome del file che di default contiene le mappature. Se in configurazione di debug cerca di caricare anche il file .dev.xml</param>
        /// <param name="debugConfiguration">Parametri della configurazione di debug</param>
        /// <returns></returns>
        public static FvgManagedDataMapper LoadFrom(IPathMapper pathMapper, string fileName, IFVGDebugConfiguration debugConfiguration)
        {
            var fileNames = new List<string>
            {
                fileName
            };

            if (debugConfiguration.IsDebugEnabled)
            {
                fileNames.Add(debugConfiguration.ManagedDataMappingsDevFile);
            }

            var path = fileNames.Select(x => CheckPath(pathMapper, x)).Where(x => !String.IsNullOrEmpty(x)).FirstOrDefault();

            if (String.IsNullOrEmpty(path))
            {
                return new FvgManagedDataMapper();
            }

            var fileContent = File.ReadAllText(path);

            return fileContent.DeserializeXML<FvgManagedDataMapper>();
        }

        /// <summary>
        /// Trasforma un path relativo in assoluto e, se esiste, lo restituisce al chiamante. Se il path non esiste restituisce null
        /// </summary>
        /// <param name="pathMapper">Permette di mappare un path relativo all'applicazione in un path assoluto del FS</param>
        /// <param name="fileName">Path relativo del file che verrà trasformato in path assoluto</param>
        /// <returns>Path assoluto del file passato o null se il file non esiste</returns>
        private static string CheckPath(IPathMapper pathMapper, string fileName)
        {
            if (fileName.StartsWith("~"))
            {
                fileName = pathMapper.MapPath(fileName);
            }

            return File.Exists(fileName) ? fileName : null;
        }
    }
}

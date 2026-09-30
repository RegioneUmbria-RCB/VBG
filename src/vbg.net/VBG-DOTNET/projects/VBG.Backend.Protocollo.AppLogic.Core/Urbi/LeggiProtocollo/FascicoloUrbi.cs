using System.Xml;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.LeggiProtocollo
{
    public class FascicoloUrbi
    {
        public string Codice { get; private set; }
        public string CodiceClassificazione { get; private set; }
        public string DescBreve { get; internal set; }
        public string DescEstesa { get; private set; }
        public string DescClassificazione { get; private set; }
        public string CodRicClassificazione { get; private set; }
        public string DescClassificazioneEstesa { get; private set; }
        public string DescClassificazioneBreve { get; private set; }
        public string Anno { get; private set; }
        public string Numero { get; private set; }
        public string NumeroSottoFascicolo { get; private set; }
        public string NumeroFascicoloDelSottofascicolo { get; private set; }

        public static FascicoloUrbi FromLeggiProtocolloXml(string xml, int idx)
        {
            const string xpathBase = "/xapirest/getInterrogazioneProtocollo_Result/SEQ_Protocollo/Protocollo";
            const string anno = "Fascicolo_Anno";
            const string codice = "Fascicolo_Codice";
            const string codiceClassificazione = "Fascicolo_CodiceClassificazione";
            const string descEstesa = "Fascicolo_DescEstesa";
            const string descBreve = "Fascicolo_DescBreve";
            const string descClassificazione = "Fascicolo_DescClassificazione";
            const string descClassificazioneEstesa = "Fascicolo_DescClassificazioneEstesa";
            const string descClassificazioneBreve = "Fascicolo_DescClassificazioneBreve";
            const string codRicClassificazione = "Fascicolo_CodRicClassificazione";
            const string numero = "Fascicolo_Numero";
            const string numeroSottoFascicolo = "Fascicolo_NumeroSottoFascicolo";
            const string numeroFascicoloDelSottofascicolo = "Fascicolo_NumFascicoloDelSottoFascicolo";

            var xmldoc = new XmlDocument();
            xmldoc.LoadXml(xml);

            return new FascicoloUrbi
            {
                Anno = xmldoc
                            .SelectSingleNode($"{xpathBase}/{anno}{idx}/text()")?
                            .Value
                            .Replace("\n", ""),

                Codice = xmldoc
                            .SelectSingleNode($"{xpathBase}/{codice}{idx}/text()")?
                            .Value
                            .Replace("\n", ""),

                CodiceClassificazione = xmldoc
                                            .SelectSingleNode($"{xpathBase}/{codiceClassificazione}{idx}/text()")?
                                            .Value
                                            .Replace("\n", ""),

                CodRicClassificazione = xmldoc
                                            .SelectSingleNode($"{xpathBase}/{codRicClassificazione}{idx}/text()")?
                                            .Value
                                            .Replace("\n", ""),

                DescBreve = xmldoc
                                .SelectSingleNode($"{xpathBase}/{descBreve}{idx}/text()")?
                                .Value
                                .Replace("\n", ""),

                DescClassificazione = xmldoc
                                        .SelectSingleNode($"{xpathBase}/{descClassificazione}{idx}/text()")?
                                        .Value
                                        .Replace("\n", ""),

                DescClassificazioneBreve = xmldoc
                                        .SelectSingleNode($"{xpathBase}/{descClassificazioneBreve}{idx}/text()")?
                                        .Value
                                        .Replace("\n", ""),

                DescClassificazioneEstesa = xmldoc
                                                .SelectSingleNode($"{xpathBase}/{descClassificazioneEstesa}{idx}/text()")?
                                                .Value
                                                .Replace("\n", ""),

                DescEstesa = xmldoc
                                .SelectSingleNode($"{xpathBase}/{descEstesa}{idx}/text()")?
                                .Value
                                .Replace("\n", ""),

                Numero = xmldoc
                            .SelectSingleNode($"{xpathBase}/{numero}{idx}/text()")?
                            .Value
                            .Replace("\n", ""),

                NumeroSottoFascicolo = xmldoc
                                        .SelectSingleNode($"{xpathBase}/{numeroSottoFascicolo}{idx}/text()")?
                                        .Value
                                        .Replace("\n", ""),

                NumeroFascicoloDelSottofascicolo = xmldoc
                                        .SelectSingleNode($"{xpathBase}/{numeroFascicoloDelSottofascicolo}{idx}/text()")?
                                        .Value
                                        .Replace("\n", ""),
            };
        }
    }
}

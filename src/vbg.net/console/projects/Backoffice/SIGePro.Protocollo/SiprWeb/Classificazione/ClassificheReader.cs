using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.SiprWeb.Classificazione;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.SiprWeb.Classificazione
{
    public class ClassificheReader
    {
        ClassificheService _wrapper;
        ProtocolloSerializer _serializer;
        ProtocolloLogs _logs;

        public ClassificheReader(ClassificheService wrapper, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _wrapper = wrapper;
            _serializer = serializer;
            _logs = logs;
        }

        public List<Documento_Type> Read()
        {
            var classifiche = new List<Documento_Type>();

            var livello1 = TrovaClassifiche();
            if (livello1.esito == Esito_Type.Item0)
            {
                foreach (var el1 in livello1.Documento)
                {
                    var livello2 = TrovaClassifiche(el1.CodiceClassificazione);
                    classifiche.Add(el1);
                    if (livello2.esito == Esito_Type.Item0)
                    {
                        foreach (var el2 in livello2.Documento)
                        {
                            var livello3 = TrovaClassifiche(el1.CodiceClassificazione, el2.CodiceClassificazione);
                            el2.CodiceClassificazione = String.Format("{0}.{1}", el1.CodiceClassificazione, el2.CodiceClassificazione);
                            classifiche.Add(el2);
                            if (livello3.esito == Esito_Type.Item0)
                            {
                                foreach (var el3 in livello3.Documento)
                                {
                                    var livello4 = TrovaClassifiche(el1.CodiceClassificazione, el2.CodiceClassificazione, el3.CodiceClassificazione);
                                    el3.CodiceClassificazione = String.Format("{0}.{1}.{2}", el1.CodiceClassificazione, el2.CodiceClassificazione, el3.CodiceClassificazione);
                                    classifiche.Add(el3);
                                    if (livello4.esito == Esito_Type.Item0)
                                    {
                                        foreach (var el4 in livello4.Documento)
                                        {
                                            el4.CodiceClassificazione = String.Format("{0}.{1}.{2}.{3}", el1.CodiceClassificazione, el2.CodiceClassificazione, el3.CodiceClassificazione, el4.CodiceClassificazione, el4.Classificazione);
                                            classifiche.Add(el4);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            _logs.Info("CLASSIFICHE RECUPERATE CORRETTAMENTE");
            _serializer.Serialize(ProtocolloLogsConstants.ListaClassificheResponse, classifiche);

            return classifiche;
        }

        private VociTitolarioDocumentoResponse TrovaClassifiche(string chiave1 = "", string chiave2 = "", string chiave3 = "", string chiave4 = "")
        {
            var response = _wrapper.GetClassifica(new VociTitolarioDocumentoRequest
            {
                Chiave1 = chiave1,
                Chiave2 = chiave2,
                Chiave3 = chiave3,
                Chiave4 = chiave4
            });

            return response;
        }
    }
}

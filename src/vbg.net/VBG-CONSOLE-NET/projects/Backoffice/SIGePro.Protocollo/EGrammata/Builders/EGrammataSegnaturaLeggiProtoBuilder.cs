using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.EGrammata.Segnatura.GetProtoInput;
using System.IO;


namespace Init.SIGePro.Protocollo.EGrammata.Builders
{
    internal class EGrammataSegnaturaLeggiProtoBuilder
    {
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private string _registro;
        private string _annoProtocollo;
        private string _numeroProtocollo;
        internal readonly string SegnaturaXml64;

        public EGrammataSegnaturaLeggiProtoBuilder(ProtocolloLogs logs, ProtocolloSerializer serializer, string anno, string numero, string registro)
        {
            _logs = logs;
            _serializer = serializer;
            _registro = registro;
            _annoProtocollo = anno;
            _numeroProtocollo = numero;

            SegnaturaXml64 = CreaSegnaturaXml();
        }

        private string CreaSegnaturaXml()
        {

            try
            {
                EstremiRegNumTypeCategoriaRegistro enumRegistro;
                bool parseRegistro = Enum.TryParse(_registro, out enumRegistro);
                if (!parseRegistro)
                    throw new Exception(String.Format("IL REGISTRO HA UN VALORE NON VALIDO {0}", _registro));

                string segnatura = String.Empty;

                var request = new EstremiXIdentificazioneUDType();
                request.Item = new EstremiRegNumType
                {
                    Anno = _annoProtocollo,
                    CategoriaRegistro = enumRegistro,
                    Numero = _numeroProtocollo
                };

                string segnaturaXml = _serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, request);

                var buffer = File.ReadAllBytes(Path.Combine(_logs.Folder, ProtocolloLogsConstants.LeggiProtocolloRequestFileName));
                var segnatura64 = Convert.ToBase64String(buffer);

                return segnatura64;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DELLA SEGNATURA DEL LEGGI PROTOCOLLO", ex);
            }
        }
    }
}

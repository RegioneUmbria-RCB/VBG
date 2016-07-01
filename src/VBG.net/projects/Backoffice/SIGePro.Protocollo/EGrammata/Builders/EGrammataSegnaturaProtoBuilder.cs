using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata.Segnatura.ProtoInput;
using Init.SIGePro.Protocollo.EGrammata.Configurations;
using System.IO;

namespace Init.SIGePro.Protocollo.EGrammata.Builders
{
    public class EGrammataSegnaturaProtoBuilder
    {
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private EGrammataSegnaturaProtoInputConfiguration _conf;
        internal string SegnaturaXml64 { get; private set; }
        internal string SegnaturaXml{ get; private set; }

        public EGrammataSegnaturaProtoBuilder (ProtocolloLogs logs, ProtocolloSerializer serializer, EGrammataSegnaturaProtoInputConfiguration conf)
        {
            _logs = logs;
            _serializer = serializer;
            _conf = conf;

            CreaSegnaturaXml();

        }

        private void CreaSegnaturaXml()
        {
            string segnatura64 = String.Empty;

            var request = new NuovaUD();

            //request.AccessoEsternoDifferitoFinoAl = DateTime.Now;
            //request.AltraRegistrazioneDaDare = _conf.AltraRegistrazione;
            //request.CopiaConoscenza = _conf.CopiaConoscenza;
            //request.TipoLogico = TIPODOCUMENTO ?????

            request.TipoLogico = new OggDiTabDiSistemaType
            {
                Item = _conf.TipoDocumento,
                ItemElementName = ItemChoiceType2.CodId
            };

            request.AllegatoUD = _conf.AllegatiInput;
            request.DatiUscita = _conf.DatiUscita;
            request.OggettoUD = _conf.Oggetto;
            request.RegistrazionePrimariaDaDare = _conf.RegistrazionePrimaria;
            request.TipoProvenienza = _conf.Flusso;
            request.Item = _conf.DatiProtocollo;
            request.Originale = _conf.Originale;

            /*if (_conf.AllegatiInput != null)
                request.NroAllegati = _conf.AllegatiInput.Length.ToString();*/

            string segnaturaXml = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, request);
            SegnaturaXml = segnaturaXml;

            var buffer = File.ReadAllBytes(Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName));
            SegnaturaXml64 = Convert.ToBase64String(buffer);
        }
    }
}

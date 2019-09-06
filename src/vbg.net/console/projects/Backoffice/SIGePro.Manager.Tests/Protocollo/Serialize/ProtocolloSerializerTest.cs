using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SIGePro.Manager.Tests.Protocollo.Serialize
{
    [TestClass]
    public class ProtocolloSerializerTest
    {
        [TestMethod]
        public void Verifica_dati_ricevuti_da_leggi_protocollo_docer()
        {
            string xml = @"<?xml version=""1.0"" encoding=""UTF-16""?>
                            <Mittenti>
	                            <Mittente>
		                            <Persona id=""REGIONE EMILIA ROMAGNA - BOLOGNA"">
			                            <IndirizzoTelematico/>
			                            <Denominazione>REGIONE EMILIA ROMAGNA - BOLOGNA</Denominazione>
		                            </Persona>
	                            </Mittente>
                            </Mittenti>";

            var serializer = new ProtocolloSerializer(null);

            var mittenti2 = serializer.Deserialize<Mittenti>(xml.Replace("\\n", ""));

            var mittenti = (Mittenti)serializer.Deserialize(xml.Replace("\\n", ""), typeof(Mittenti));

            
            

            Assert.AreEqual("REGIONE EMILIA ROMAGNA - BOLOGNA", ((PersonaType)mittenti.Items[0].Items[0]).id);
            Assert.AreEqual(null, ((PersonaType)mittenti.Items[0].Items[0]).IndirizzoTelematico.note);
            Assert.AreEqual(null, ((PersonaType)mittenti.Items[0].Items[0]).IndirizzoTelematico.Text);
            Assert.AreEqual(IndirizzoTelematicoTypeTipo.smtp, ((PersonaType)mittenti.Items[0].Items[0]).IndirizzoTelematico.tipo);
            Assert.AreEqual("REGIONE EMILIA ROMAGNA - BOLOGNA", ((PersonaType)mittenti.Items[0].Items[0]).Denominazione.Text[0]);
        }
    }
}

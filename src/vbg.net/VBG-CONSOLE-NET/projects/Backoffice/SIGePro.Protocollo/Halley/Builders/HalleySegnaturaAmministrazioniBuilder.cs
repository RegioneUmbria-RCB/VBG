using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Halley.Builders
{
    public class HalleySegnaturaAmministrazioniBuilder
    {
        private Amministrazioni _amministrazioneVbg;
        private string _indirizzoTelematico = String.Empty;
        private string _codiceAmministrazione;

        public HalleySegnaturaAmministrazioniBuilder(Amministrazioni amministrazioneVbg, string codiceAmministrazione, string indirizzoTelematico)
        {
            _amministrazioneVbg = amministrazioneVbg;
            _codiceAmministrazione = codiceAmministrazione;
            _indirizzoTelematico = indirizzoTelematico;
            //SegnaturaAmministrazione = GetAmministrazione();
        }

        public Amministrazione GetAmministrazione()
        {

            var amministrazione = new Amministrazione();
            amministrazione.Denominazione = _amministrazioneVbg.AMMINISTRAZIONE;
            amministrazione.CodiceAmministrazione = _codiceAmministrazione;

            amministrazione.IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { _indirizzoTelematico } };
            amministrazione.Items = new object[] { new UnitaOrganizzativa { id = _amministrazioneVbg.PROT_UO } };
            amministrazione.ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa };

            return amministrazione;
        }
    }
}

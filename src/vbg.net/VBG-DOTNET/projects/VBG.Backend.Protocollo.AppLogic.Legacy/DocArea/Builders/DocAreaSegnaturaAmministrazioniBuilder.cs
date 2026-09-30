using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders
{
    public class DocAreaSegnaturaAmministrazioniBuilder
    {
        private readonly ProtocolloAmministrazioni _amministrazioneVbg;
        private readonly string _indirizzoTelematico = String.Empty;
        private readonly string _codiceAmministrazione;

        public readonly Amministrazione SegnaturaAmministrazione;

        public DocAreaSegnaturaAmministrazioniBuilder(ProtocolloAmministrazioni amministrazioneVbg, string codiceAmministrazione, string indirizzoTelematico)
        {
            this._amministrazioneVbg = amministrazioneVbg;
            this._codiceAmministrazione = codiceAmministrazione;
            this._indirizzoTelematico = indirizzoTelematico;
            this.SegnaturaAmministrazione = this.GetAmministrazione();
        }

        private Amministrazione GetAmministrazione()
        {

            var amministrazione = new Amministrazione();
            amministrazione.Denominazione = this._amministrazioneVbg.AMMINISTRAZIONE;
            amministrazione.CodiceAmministrazione = this._codiceAmministrazione;

            amministrazione.IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { this._indirizzoTelematico } };
            amministrazione.Items = new object[] { new UnitaOrganizzativa { id = this._amministrazioneVbg.PROT_UO } };
            amministrazione.ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa };

            return amministrazione;
        }
    }
}

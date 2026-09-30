using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using VBG.AppLogic.SSU.GestioneProcedimenti;

namespace VBG.AppLogic.SSU
{
    public class SsuExtensions
    {
        private readonly DomandaOnlineReadInterface _readInterface;

        internal SsuExtensions(DomandaOnlineReadInterface domandaOnlineReadInterface)
        {
            this._readInterface = domandaOnlineReadInterface;
        }

        public bool ModalitaSsuAttiva => this.GetProcedimentiPrincipaliInternal() is not null;

        public string CodiceEnte => this._readInterface._database.ISTANZE.Rows.Count != 1 ? "" : this._readInterface._database.ISTANZE[0].CodiceIstatComune;

        public List<SsuProcedimentoBaseDomanda>? Procedimenti => this.GetTuttiIProcedimentiInternal();
        public SsuProcedimentiDomanda? ProcedimentiPrincipali => this.GetProcedimentiPrincipaliInternal();

        private SsuProcedimentiDomanda? GetProcedimentiPrincipaliInternal()
        {
            return this._readInterface.DatiExtra.Get<SsuProcedimentiDomanda>(SsuProcedimentiDomandaService.Constants.DATO_EXTRA_PROCEDIMENTI_SSU);
        }

        private List<SsuProcedimentoBaseDomanda>? GetTuttiIProcedimentiInternal()
        {
            var procedimentiPrincipali = this._readInterface.DatiExtra.Get<SsuProcedimentiDomanda>(SsuProcedimentiDomandaService.Constants.DATO_EXTRA_PROCEDIMENTI_SSU);

            var rVal = new List<SsuProcedimentoBaseDomanda>();

            // Principali
            rVal.AddRange(procedimentiPrincipali.Procedimenti.Select(x => x.ToProcedimentoBase()));

            // Attivati tramite fattispecie secondarie
            var procedimentiSecondari = procedimentiPrincipali.Procedimenti.SelectMany(proc => proc.FattispeciePrimarie.SelectMany(primaria => primaria.FattispecieSecondarie.Select(secondaria => secondaria.Procedimento)));
            rVal.AddRange(procedimentiSecondari);

            return rVal;
        }
    }

    public static class ExtensionMethods
    {
        extension(IDomandaOnlineReadInterface readInterface)
        {
            public SsuExtensions Ssu => new((DomandaOnlineReadInterface)readInterface);
        }
    }
}

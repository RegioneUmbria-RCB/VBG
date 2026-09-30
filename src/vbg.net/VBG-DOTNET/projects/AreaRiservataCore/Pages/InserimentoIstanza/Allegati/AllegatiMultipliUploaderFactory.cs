using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Allegati
{
    public enum ProveninzaAllegatoEnum
    {
        Intervento,
        Endoprocedimento
    }

    public class AllegatiMultipliUploaderFactory
    {
        private readonly IAllegatiMultipliUploader _allegetiIntervento;
        private readonly IAllegatiMultipliUploader _allegetiEndo;

        public AllegatiMultipliUploaderFactory(int idDomanda, AllegatiInterventoService allegatiInterventoService, IAllegatiEndoprocedimentiService allegatiEndoService)
        {
            this._allegetiEndo = new AllegatiMultipliEndo(allegatiEndoService, idDomanda);
            this._allegetiIntervento = new AllegatiMultipliIntervento(allegatiInterventoService, idDomanda);
        }

        public IAllegatiMultipliUploader Get(ProveninzaAllegatoEnum provenienza)
        {
            if (provenienza == ProveninzaAllegatoEnum.Intervento)
            {
                return this._allegetiIntervento;
            }

            return this._allegetiEndo;
        }
    }
}
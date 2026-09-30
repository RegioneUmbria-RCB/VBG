using Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Auditing
{
    public interface ICommissioniAuditingService
    {
        void AccessoACommissioneNegatoAUtenteFrontend(int idCommissione, int codiceAnagrafe);
        void AccessoACommissioneDaFrontend(int idCommissione, int codiceAnagrafe);
        void AccessoAPraticaDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza);
        void AccessoAPraticaNegatoAUtenteFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza);
        void AccessoAFileDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza, int codiceOggetto);
        void AccessoAFileNegatoAUtenteFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza, int codiceOggetto);
        void VotoModificatoDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza, string descrizioneParere, int? codiceOggetto);
        void VotoInseritoDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza, string descrizioneParere, int? codiceOggetto, IEstremiProtocollazioneCommissione estremiProtocollazione = null);
        void ApprovazioneAllegatoFallita(int idCommissione, int codiceAnagrafe, int idAllegato);
        void AllegatoApprovato(int idCommissione, int codiceAnagrafe, int codiceOggetto, string hash);
        void UtenteAssociatoACommissione(int idCommissione, int codiceAnagrafe, int idAmministrazione, string pin);
        void ParereCommissioneProtocollato(int idCommissione, int codiceAnagrafe, string idProtocollo, string numeroProtocollo, string dataProtocollo);
        void AccessoAVotoNegatoDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza);
        void ErroreProtocollazioneVoto(int idCommissione, int codiceAnagrafe, string messaggioErrore);
    }
}

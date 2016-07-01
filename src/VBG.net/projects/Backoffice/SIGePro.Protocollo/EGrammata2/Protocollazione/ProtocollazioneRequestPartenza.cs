using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Anagrafiche;
using Init.SIGePro.Protocollo.EGrammata2.Fascicolazione;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public class ProtocollazioneRequestPartenza : IRequestProtocollo
    {
        ProtocollazioneRequestConfiguration _configuration;
        AnagraficheService _wrapper;

        public ProtocollazioneRequestPartenza(ProtocollazioneRequestConfiguration configuration, AnagraficheService wrapper)
        {
            _configuration = configuration;
            _wrapper = wrapper;
        }

        public DatiTipoProtIN Flusso
        {
            get { return DatiTipoProtIN.U; }
        }

        public string DataArrivo
        {
            get { return DateTime.Now.ToString("dd/MM/yyyy"); }
        }

        public UOProv UOProvenienza
        {
            get
            {
                return new UOProv
                {
                    UO = _configuration.UoStrutturaUffici
                };
            }
        }

        public UoAss UODestinatario
        {
            get { return null; }
        }

        public Firm[] GetMittentiEsterni()
        {
            return null;
        }

        public EsibDest[] GetDestinatariEsterni()
        {
            var anagraficheList = _configuration.DatiProto.AnagraficheProtocollo.Select(x => PersonaFisicaGiuridicaFactory.Create(x, _wrapper).GetEsibDest());
            var amministrazioniList = _configuration.DatiProto.AmministrazioniEsterne.Select(x => x.ToEsibDestFromAmministrazione(_wrapper));

            var retVal = anagraficheList.Union(amministrazioniList);

            return retVal.ToArray();
        }

        public Classificazione Titolario
        {
            get { return _configuration.Titolario; }
        }

        public IFascicolazione Fascicolo
        {
            get { return FascicolazioneFactory.Create(_configuration); }
        }

        public CopieArrIn[] GetCopieArrIn()
        {
            var copie = new CopieArrIn
            {
                flgorig = "N",
                flgCC = "N",
                UoAss = new UoAss
                {
                    UO = _configuration.UoStrutturaUffici
                },
                Classifica = new Classifica { Classificazione = _configuration.Titolario }
            };

            return new CopieArrIn[] { copie };
        }

        public AllegaArrIn[] GetAllegati()
        {
            var all = _configuration.Allegati.Select((x, i) => new AllegaArrIn
            {
                NomeFile = x.NOMEFILE,
                Tipo = i == 0 ? "0" : "1",
                //Value = Convert.ToBase64String(x.OGGETTO),
                NumeroAttachment = (i + 1).ToString()
            });

            return all.ToArray();
        }

    }
}

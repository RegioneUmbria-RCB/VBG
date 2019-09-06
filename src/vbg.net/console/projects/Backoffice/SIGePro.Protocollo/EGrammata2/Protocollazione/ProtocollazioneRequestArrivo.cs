using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Anagrafiche;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata2.Fascicolazione;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public class ProtocollazioneRequestArrivo : IRequestProtocollo
    {
        ProtocollazioneRequestConfiguration _configuration;
        AnagraficheService _wrapper;

        public ProtocollazioneRequestArrivo(ProtocollazioneRequestConfiguration configuration, AnagraficheService wrapper)
        {
            _configuration = configuration;
            _wrapper = wrapper;
        }

        public DatiTipoProtIN Flusso
        {
            get { return DatiTipoProtIN.E; }
        }

        public string DataArrivo
        {
            get { return DateTime.Now.ToString("dd/MM/yyyy"); }
        }

        public UOProv UOProvenienza
        {
            get { return null; }
        }

        public UoAss UODestinatario
        {
            get
            {
                return new UoAss
                {
                    UO = _configuration.UoStrutturaUffici
                };
            }
        }

        public Firm[] GetMittentiEsterni()
        {

            var anagraficheList = _configuration.DatiProto.AnagraficheProtocollo.Select(x => PersonaFisicaGiuridicaFactory.Create(x, _wrapper).GetFirm());
            var amministrazioniList = _configuration.DatiProto.AmministrazioniEsterne.Select(x => x.ToFirmFromAmministrazione(_wrapper));

            var retVal = anagraficheList.Union(amministrazioniList);
            return retVal.ToArray();
        }

        public EsibDest[] GetDestinatariEsterni(string codiceCC)
        {
            return null;
        }

        public Classificazione Titolario
        {
            get 
            {
                return null;
            }
        }

        public CopieArrIn[] GetCopieArrIn()
        {
            var copie = new CopieArrIn
            {
                flgorig = "S",
                flgCC = "N",
                UoAss = new UoAss
                {
                    UO = _configuration.UoStrutturaUffici
                },
                Classifica = null
            };

            return new CopieArrIn[] { copie };

        }

        public AllegaArrIn[] GetAllegati()
        {
            var all = _configuration.Allegati.Skip(1).Select((x, i) => new AllegaArrIn
            {
                NomeFile = x.NOMEFILE,
                Tipo = i == 0 ? "0" : "1",
                //Value = Convert.ToBase64String(x.OGGETTO),
                NumeroAttachment = (i + 1).ToString()
            });

            return all.ToArray();
        }


        public EsibDest[] GetDestinatariEsterni()
        {
            return null;
        }

        public IFascicolazione Fascicolo
        {
            get { return FascicolazioneFactory.Create(_configuration); }
        }
    }
}

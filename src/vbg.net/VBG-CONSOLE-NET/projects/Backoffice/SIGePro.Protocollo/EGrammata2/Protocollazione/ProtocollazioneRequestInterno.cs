using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata2.Fascicolazione;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public class ProtocollazioneRequestInterno : IRequestProtocollo
    {
        ProtocollazioneRequestConfiguration _configuration;

        public ProtocollazioneRequestInterno(ProtocollazioneRequestConfiguration configuration)
        {
            _configuration = configuration;
        }

        public DatiTipoProtIN Flusso
        {
            get { return DatiTipoProtIN.I; }
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
            get
            {
                return new UoAss
                {
                    UO = ProtocollazioneRequestUfficiAdapter.Adatta(_configuration.DatiProto.AmministrazioniProtocollo[0].PROT_UO)
                };
            }
        }

        public Firm[] GetMittentiEsterni()
        {
            return null;
        }

        public EsibDest[] GetDestinatariEsterni()
        {
            return null;
        }


        public new Classificazione Titolario
        {
            get { return null; }
        }

        public CopieArrIn[] GetCopieArrIn()
        {
            var copie = new CopieArrIn
            {
                flgorig = "S",
                flgCC = "N",
                UoAss = new UoAss
                {
                    UO = ProtocollazioneRequestUfficiAdapter.Adatta(_configuration.DatiProto.AmministrazioniProtocollo[0].PROT_UO)
                }
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


        public IFascicolazione Fascicolo
        {
            get { return FascicolazioneFactory.Create(_configuration); }
        }
    }
}

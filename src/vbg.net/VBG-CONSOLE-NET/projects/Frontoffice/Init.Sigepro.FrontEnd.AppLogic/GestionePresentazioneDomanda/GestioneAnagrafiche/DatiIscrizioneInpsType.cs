using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    public class DatiIscrizioneBase
    {
        public string Matricola { get; private set; }
        public string CodiceSede { get; private set; }
        public string DescrizioneSede { get; private set; }

        public DatiIscrizioneBase(string matricola, string codiceSede, string descrizioneSede)
        {
            this.Matricola = matricola;
            this.CodiceSede = codiceSede;
            this.DescrizioneSede = descrizioneSede;
        }
    }

    public class DatiIscrizioneInpsType : DatiIscrizioneBase
    {
        public static DatiIscrizioneInpsType New()
        {
            return new DatiIscrizioneInpsType(String.Empty, String.Empty, string.Empty);
        }

        public DatiIscrizioneInpsType(string matricola, string codiceSede, string descrizioneSede)
            :base(matricola, codiceSede, descrizioneSede)
        {
        }

        internal DatiInpsType ToDatiInpsType()
        {
            return new DatiInpsType
            {
                numero = this.Matricola,
                sedeIscrizione = new CodiceDescrizioneType
                {
                    codice = this.CodiceSede,
                    descrizione = this.DescrizioneSede
                }
            };
        }
    }

    public class DatiIscrizioneInailType : DatiIscrizioneBase
    {
        public static DatiIscrizioneInailType New()
        {
            return new DatiIscrizioneInailType(String.Empty, String.Empty, string.Empty);
        }

        public DatiIscrizioneInailType(string matricola, string codiceSede, string descrizioneSede)
            :base(matricola, codiceSede, descrizioneSede)
        {
        }

        internal DatiInailType ToDatiInailType()
        {
            return new DatiInailType
            {
                numero = this.Matricola,
                sedeIscrizione = new CodiceDescrizioneType
                {
                    codice = this.CodiceSede,
                    descrizione = this.DescrizioneSede
                }
            };
        }
    }
}

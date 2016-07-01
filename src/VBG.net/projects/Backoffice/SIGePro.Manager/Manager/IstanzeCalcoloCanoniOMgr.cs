using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using Init.SIGePro.Exceptions;
using System.Data;

namespace Init.SIGePro.Manager
{
    public partial class IstanzeCalcoloCanoniOMgr
    {

        public IstanzeCalcoloCanoniO Insert(IstanzeCalcoloCanoniO cls)
        {
            var filtro = new IstanzeCalcoloCanoniO{
				Idcomune = cls.Idcomune,
				FkIdCausale = cls.FkIdCausale,
				FkIdtestata = cls.FkIdtestata,
				FkIdistoneri = cls.FkIdistoneri
			};

            var l = GetList(filtro);

            if (l.Count > 0)
                throw new MoreThanOneRecordException("L'onere è gia stato registrato in precedenza");

            if (Inserting != null)
                Inserting(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            if (Inserted != null)
                Inserted(cls);

            return cls;
        }

        public void InserisciOneri( string idcomune, int fkidtestata,  IEnumerable<IstanzeOneri> list)
        {
			var oneriSenzaId = list.Where(x => String.IsNullOrEmpty(x.ID));

			if (oneriSenzaId.Count() > 0)
			{
				throw new InvalidOperationException("L'onere collegato al canone non ha un valore");
			}

            foreach (IstanzeOneri io in list)
            {
                IstanzeCalcoloCanoniO cls = new IstanzeCalcoloCanoniO();
                cls.Idcomune = idcomune;
                cls.FkIdCausale = Convert.ToInt32(io.FKIDTIPOCAUSALE);
                if (String.IsNullOrEmpty(io.ID))
                {
                    cls.FkIdistoneri = null;
                }
                else
                {
                    cls.FkIdistoneri = Convert.ToInt32(io.ID);
                }
                
                cls.FkIdtestata = fkidtestata;
				cls.FkIdistoneri = Convert.ToInt32(io.ID);
                cls.Onere = io;

                Insert(cls);
            }
        }

        //Verifica se l'onere è presente nella tabella ISTANZECALCOLOCANONI_O
        public int VerificaIstanzeCalcoloCanoniCollegatiFromOnere(string idComune, int codiceOnere)
        {
            int retVal = int.MinValue;
            IstanzeCalcoloCanoniO icco = new IstanzeCalcoloCanoniO();
            icco.Idcomune = idComune;
            icco.FkIdistoneri = codiceOnere;
            List<IstanzeCalcoloCanoniO> list = GetList(icco);
            if ((list != null) && (list.Count != 0))
                retVal = list[0].FkIdtestata.Value;

            return retVal;
        }

        //Aggiorna l'eventuale onere presente nella tabella ISTANZECALCOLOCANONI_O
        public void AggiornaIstanzeCalcoloCanoni(string idComune, int fk_idtestata, int fk_id_istoneri, int fk_idcausale)
        {
            IstanzeCalcoloCanoniO icco = new IstanzeCalcoloCanoniO();
            icco.Idcomune = idComune;
            icco.FkIdistoneri = fk_id_istoneri;
            icco.FkIdtestata = fk_idtestata;
            icco.FkIdCausale = fk_idcausale;

            this.db.Insert(icco);
        }

        public void DeleteIstanzeCalcoloCanoniCollegati(string idComune, int fk_idtestata, int fk_idcausale, int fk_idistonere)
        {
            IstanzeCalcoloCanoniO icco = new IstanzeCalcoloCanoniO();
            icco.Idcomune = idComune;
            icco.FkIdCausale = fk_idcausale;
            icco.FkIdtestata = fk_idtestata;
            icco.FkIdistoneri = fk_idistonere;
            Delete(icco);
        }
    }
}

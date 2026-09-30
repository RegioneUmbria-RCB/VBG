

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_ICALCOLI per la classe CCICalcoli il 27/06/2008 13.01.38
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///
    public partial class CCICalcoliMgr : BaseManager
    {
        public CCICalcoliMgr(DataBase dataBase) : base(dataBase) { }

        public CCICalcoli GetById(string idcomune, int id)
        {
            var c = new CCICalcoli();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCICalcoli)this.db.GetClass(c);
        }

        //public List<CCICalcoli> GetList(string idcomune, int id, int codiceistanza, decimal su, decimal snr, decimal sc, decimal st, decimal sa, decimal su_art9, decimal i1, decimal i2, decimal i3, int fk_cctce_id, decimal maggiorazione, decimal costocmq, decimal costocmq_maggiorato)
        //{
        //    var c = new CCICalcoli();
        //    if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
        //    c.Id = id;
        //    c.Codiceistanza = codiceistanza;
        //    c.Su = su;
        //    c.Snr = snr;
        //    c.Sc = sc;
        //    c.St = st;
        //    c.Sa = sa;
        //    c.SuArt9 = su_art9;
        //    c.I1 = i1;
        //    c.I2 = i2;
        //    c.I3 = i3;
        //    c.FkCctceId = fk_cctce_id;
        //    c.Maggiorazione = maggiorazione;
        //    c.Costocmq = costocmq;
        //    c.CostocmqMaggiorato = costocmq_maggiorato;


        //    return this.db.GetClassList(c).ToList<CCICalcoli>();
        //}

        public List<CCICalcoli> GetList(CCICalcoli filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCICalcoli>();
        }

        public CCICalcoli Insert(CCICalcoli cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }



        private CCICalcoli DataIntegrations(CCICalcoli cls)
        {



            return cls;
        }

        public CCICalcoli Update(CCICalcoli cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCICalcoli cls)
        {
            if (!cls.Id.HasValue)
                throw new Exception("Si sta cercando di eliminare un calcolo senza utilizzarne l'id");

            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CCICalcoli cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(CCICalcoli cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }

    }
}



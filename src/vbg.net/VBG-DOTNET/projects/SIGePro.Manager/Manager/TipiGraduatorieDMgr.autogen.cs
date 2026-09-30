using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TIPIGRADUATORIED per la classe TipiGraduatorieD il 01/04/2009 9.28.02
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
    public partial class TipiGraduatorieDMgr : BaseManager
    {
        public TipiGraduatorieDMgr(DataBase dataBase) : base(dataBase) { }

        public TipiGraduatorieD GetById(int id, string idcomune)
        {
            var c = new TipiGraduatorieD();


            c.Id = id;
            c.Idcomune = idcomune;

            return (TipiGraduatorieD)this.db.GetClass(c);
        }

        public List<TipiGraduatorieD> GetList(TipiGraduatorieD filtro)
        {
            return this.db.GetClassList(filtro).ToList<TipiGraduatorieD>();
        }

        public TipiGraduatorieD Insert(TipiGraduatorieD cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TipiGraduatorieD ChildInsert(TipiGraduatorieD cls)
        {
            return cls;
        }

        private TipiGraduatorieD DataIntegrations(TipiGraduatorieD cls)
        {
            return cls;
        }


        public TipiGraduatorieD Update(TipiGraduatorieD cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TipiGraduatorieD cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(TipiGraduatorieD cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TipiGraduatorieD cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TipiGraduatorieD cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}



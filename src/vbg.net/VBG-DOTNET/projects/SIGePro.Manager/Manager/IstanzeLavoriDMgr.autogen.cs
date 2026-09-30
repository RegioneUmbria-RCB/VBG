

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ISTANZELAVORI_D per la classe IstanzeLavoriD il 31/07/2008 11.06.54
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
    public partial class IstanzeLavoriDMgr : BaseManager
    {
        public IstanzeLavoriDMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeLavoriD GetById(string idcomune, int id)
        {
            var c = new IstanzeLavoriD();


            c.Idcomune = idcomune;
            c.Id = id;

            return (IstanzeLavoriD)this.db.GetClass(c);
        }

        public List<IstanzeLavoriD> GetList(string idcomune, int id, int fk_iltid, int fk_coid, int fk_umid, double costo_unitario_um, double quantita, double totale)
        {
            var c = new IstanzeLavoriD();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            c.FkIltid = fk_iltid;
            c.FkCoid = fk_coid;
            c.FkUmid = fk_umid;
            c.CostoUnitarioUm = costo_unitario_um;
            c.Quantita = quantita;
            c.Totale = totale;


            return this.db.GetClassList(c).ToList<IstanzeLavoriD>();
        }

        public List<IstanzeLavoriD> GetList(IstanzeLavoriD filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeLavoriD>();
        }

        public IstanzeLavoriD Insert(IstanzeLavoriD cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IstanzeLavoriD ChildInsert(IstanzeLavoriD cls)
        {
            return cls;
        }

        private IstanzeLavoriD DataIntegrations(IstanzeLavoriD cls)
        {
            return cls;
        }


        public IstanzeLavoriD Update(IstanzeLavoriD cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeLavoriD cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeLavoriD cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(IstanzeLavoriD cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(IstanzeLavoriD cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


